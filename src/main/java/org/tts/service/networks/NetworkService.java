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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tts.model.common.BiomodelsQualifier;
import org.tts.model.common.SBMLSpecies;
import org.tts.model.flat.FlatEdge;
import org.tts.model.flat.FlatSpecies;
import org.tts.service.FlatEdgeService;
import org.tts.service.FlatSpeciesService;
import org.tts.service.SimpleSBML.SBMLSpeciesService;

/**
 * 
 * Handles requests for network Mappings containing nodes and relationships.
 * It delegates the tasks to <a href="#{@link}">{@link FlatSpeciesService}</a>, 
 * <a href="#{@link}">{@link FlatEdgeService}</a> for direct actions 
 * and uses <a href="#{@link}">{@link SBMLSpeciesService}</a> 
 * to lookup missing information and enrich the flatNetworks.
 * 
 * @author Thorsten Tiede
 *
 * @since 0.1
 */
@Service
public class NetworkService {

	@Autowired
	FlatSpeciesService flatSpeciesService;
	
	@Autowired
	FlatEdgeService flatEdgeService;
	
	@Autowired
	SBMLSpeciesService sbmlSpeciesService;
	
	Logger log = LoggerFactory.getLogger(NetworkService.class);
	
	/**
	 * Retrieve <a href="#{@link}">{@link FlatSpecies}</a> entities for a list of entityUUIDs
	 * 
	 * @param nodeUUIDs List of entityUUIDs for the nodes to get
	 * @return List of <a href="#{@link}">{@link FlatSpecies}</a>
	 */
	public List<FlatSpecies> getNetworkNodes(List<String> nodeUUIDs) {
		List<FlatSpecies> species = new ArrayList<>();
		for (String nodeUUID : nodeUUIDs) {
			species.add(this.flatSpeciesService.findByEntityUUID(nodeUUID));
		}
		return species;
	}
	
	/**
	 * Returns the geneSet for nodeUUIDs in network with UUID networkEntityUUID.
	 * Searches for direct connections between pairs of <a href="#{@link}">{@link FlatSpecies}</a> in the List nodeUUIDs
	 * 
	 * @param networkEntityUUID The entityUUID of the network to search in
	 * @param nodeUUIDs List of entityUUIDs of <a href="#{@link}">{@link FlatSpecies}</a> to include
	 * @return Iterable of <a href="#{@link}">{@link FlatEdge}</a> connecting the searched <a href="#{@link}">{@link FlatSpecies}</a>
	 */
	public Iterable<FlatEdge> getGeneSet(String networkEntityUUID, List<String> nodeUUIDs) {
		return this.flatEdgeService.getGeneSet(networkEntityUUID, nodeUUIDs);
	}
	
	/**
	 * Retrieve the entityUUID of a <a href="#{@link}">{@link FlatSpecies}</a> for a symbol.
	 * Searches the nodeSymbol in the Network given. If not found directly, searches the full model
	 * for occurrences of this symbol and finds the <a href="#{@link}">{@link FlatSpecies}</a> 
	 * that is derived from it. The symbol on the <a href="#{@link}">{@link FlatSpecies}</a> might differ,
	 * but it is guaranteed, that the <a href="#{@link}">{@link FlatSpecies}</a> - entityUUID returned
	 * is derived from a <a href="#{@link}">{@link SBMLSpecies}</a> that has that symbol connected to it 
	 * through a <a href="#{@link}">{@link BiomodelsQualifier}</a> relationship.
	 * 
	 * @param networkEntityUUID The entityUUID of the network to search in
	 * @param nodeSymbol The symbol to find
	 * @return entityUUID of <a href="#{@link}">{@link FlatSpecies}</a> for this symbol
	 */
	public String getFlatSpeciesEntityUUIDOfSymbolInNetwork(String networkEntityUUID, String nodeSymbol) {
		String flatSpeciesEntityUUID = this.findEntityUUIDForSymbolInNetwork(networkEntityUUID, nodeSymbol);
		
		// 0. Check whether we have that geneSymbol directly in the network
		if (flatSpeciesEntityUUID == null) {
			// 1. Find SBMLSpecies with geneSymbol (or SBMLSpecies connected to externalResource with geneSymbol)
			SBMLSpecies geneSpecies = this.sbmlSpeciesService.findBysBaseName(nodeSymbol);
			String simpleModelGeneEntityUUID = null;
			if(geneSpecies != null) {
				simpleModelGeneEntityUUID = geneSpecies.getEntityUUID();
			} else {
				Iterable<SBMLSpecies> bqSpecies = this.sbmlSpeciesService.findByBQConnectionTo(nodeSymbol, "KEGG");
				if (bqSpecies == null) {
					// we could not find that gene
					return null;
				} else {
					Iterator<SBMLSpecies> bqSpeciesIterator = bqSpecies.iterator();
					while (bqSpeciesIterator.hasNext()) {
						SBMLSpecies current = bqSpeciesIterator.next();
						log.debug("Found Species " + current.getsBaseName() + " with uuid: " + current.getEntityUUID());
						if(geneSpecies == null) {
							log.debug("Using " + current.getsBaseName());
							geneSpecies = current;
							break;
						}
					}
					if(geneSpecies != null) {
						simpleModelGeneEntityUUID = geneSpecies.getEntityUUID();
					} else {
						return null;
					}
				}
			}
			if (simpleModelGeneEntityUUID == null) {
				return null;
			}
			// 2. Find FlatSpecies in network with networkEntityUUID to use as startPoint for context search
			//geneSymbolSpecies = this.flatSpeciesRepository.findBySimpleModelEntityUUIDInNetwork(simpleModelGeneEntityUUID, networkEntityUUID);
			FlatSpecies geneSymbolSpecies = this.flatSpeciesService.findBySimpleModelEntityUUID(networkEntityUUID, simpleModelGeneEntityUUID);
			if (geneSymbolSpecies == null) {
				//return "Could not find derived FlatSpecies to SBMLSpecies (uuid:" + simpleModelGeneEntityUUID + ") in network with uuid: " + networkEntityUUID;
				return null;
			}
			flatSpeciesEntityUUID = geneSymbolSpecies.getEntityUUID();
		}
		return flatSpeciesEntityUUID;
	}
	
	/**
	 * Finds the entityUUID of the <a href="#{@link}">{@link FlatSpecies}</a> with geneSymbol in network.
	 * Only returns a entityUUID when there is a <a href="#{@link}">{@link FlatSpecies}</a> in network that 
	 * does have the symbol geneSymbol 
	 * 
	 * @param networkEntityUUID The network to search in
	 * @param geneSymbol The symbol to search
	 * @return UUID of the <a href="#{@link}">{@link FlatSpecies}</a> with symbol geneSymbol
	 */
	public String findEntityUUIDForSymbolInNetwork(String networkEntityUUID, String geneSymbol) {
		return this.flatSpeciesService.findEntityUUIDForSymbolInNetwork(networkEntityUUID, geneSymbol);
	}

	
	
}