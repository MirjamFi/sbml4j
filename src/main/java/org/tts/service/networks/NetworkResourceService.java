/*
 * ----------------------------------------------------------------------------
	Copyright 2020 University of Tuebingen 	

	This file is part of SBML4j.

    SBML4j is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    SBML4j is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with SBML4j.  If not, see <https://www.gnu.org/licenses/>.
 * ---------------------------------------------------------------------------- 
 */

package org.tts.service.networks;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.tts.model.flat.FlatEdge;
import org.tts.model.flat.FlatSpecies;
import org.tts.service.GraphMLService;

/**
 * This service class handles requests from controllers to get Resources containing networks
 * It depends on resource-creating service (like graphMLService) and network-extracting services
 * like networkService.
 */
@Service
public class NetworkResourceService {

	@Autowired 
	GraphMLService graphMLService;
	
	@Autowired
	NetworkService networkService;
	
	Logger log = LoggerFactory.getLogger(NetworkResourceService.class);
	
	/**
	 * Delegates the extraction of network nodes and relationships and the subsequently
	 * the geneartion of a graphML-Resource contining those nodes and relationships.
	 * 
	 * @param uuid The uuid of the network to extract
	 * @param geneSet The set of node symbols (here genes-symbols) that should be extracted with their directly connecting relationships
	 * @param directed Option whether the resulting graphML-Graph should be directed (true) or undirected (false)
	 * @return ByteArrayResource containing the GraphML representation of the network
	 */
	public Resource getNetwork(String uuid, List<String> geneSet, boolean directed) {
		
		// 1. Translate the symbols in geneSet into simplemodel entityUUIDs of network with UUID uuid
		List<String> flatSpeciesUUIDs = new ArrayList<>();
		for (String geneSymbol : geneSet) {
			String speciesUUID = this.networkService.getFlatSpeciesEntityUUIDOfSymbolInNetwork(uuid, geneSymbol);
			if(speciesUUID != null) {
				flatSpeciesUUIDs.add(speciesUUID);
			} else {
				log.warn("Could not find FlatSpecies for symbol " + geneSymbol + " in network (" + uuid + ")");
			}
		}
		
		Iterable<FlatSpecies> geneSetFlatSpecies = this.networkService.getNetworkNodes(flatSpeciesUUIDs);
		Iterable<FlatEdge> geneSetFlatEdges = this.networkService.getGeneSet(uuid, flatSpeciesUUIDs);
		Set<String> seenSymbols = new HashSet<>();
		Iterator<FlatEdge> edgeIter = geneSetFlatEdges.iterator();
		List<FlatSpecies> unconnectedSpecies = new ArrayList<>();
		while(edgeIter.hasNext()) {
			FlatEdge current = edgeIter.next();
			seenSymbols.add(current.getInputFlatSpecies().getSymbol());
			seenSymbols.add(current.getOutputFlatSpecies().getSymbol());
		}
		Iterator<FlatSpecies> speciesIter = geneSetFlatSpecies.iterator();
		while (speciesIter.hasNext()) {
			FlatSpecies current = speciesIter.next();
			if(!seenSymbols.contains(current.getSymbol())) {
				unconnectedSpecies.add(current);
			}
		}
		ByteArrayOutputStream graphMLStream;
		if(unconnectedSpecies.size() == 0) {
			graphMLStream = this.graphMLService.getGraphMLForFlatEdges(geneSetFlatEdges, directed);
			
		} else {
			for (FlatSpecies species : unconnectedSpecies) {
				log.debug("Found unconnected species in geneset: " + species.getSymbol() + ": " + species.getEntityUUID());
			}
			graphMLStream = this.graphMLService.getGraphMLForFlatEdgesAndUnconnectedFlatSpecies(geneSetFlatEdges, unconnectedSpecies, directed);
			
		}
		return new ByteArrayResource(graphMLStream.toByteArray(), "geneset.graphml");
	}
	
	
}