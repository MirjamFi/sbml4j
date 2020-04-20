package org.tts.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tts.model.provenance.ProvenanceEntity;
import org.tts.model.simple.SBMLSimpleTransition;
import org.tts.model.warehouse.PathwayNode;
import org.tts.repository.warehouse.PathwayNodeRepository;
import org.tts.repository.warehouse.SBMLGraphRepository;

@Service
public class GenePathwayRelationAnalysisService {

	@Autowired
	PathwayNodeRepository pathwayNodeRepository;
	
	@Autowired
	SBMLGraphRepository sbmlGraphRepository;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public Iterable<PathwayNode> getPathwaysToGene(String geneSymbol) {
		// 1. get ProvenenaceEntity of SBMLSpecies with symbol geneSymbol
		List<ProvenanceEntity> geneProvenanceEntityList = findAllProvenanceEntitiesWithSBaseName(geneSymbol, "SBMLSpecies"); 
		
		// 2. get Iterable<PathwayNode> for this ProvenanceEntity
		Iterable<PathwayNode> pathways = this.pathwayNodeRepository.findAllPathwaysOfProvenanceEntity(geneProvenanceEntityList.get(0).getEntityUUID());
		return pathways;
	}

	/**
	 * @param geneSymbol
	 * @param entityType
	 * @return
	 */
	private List<ProvenanceEntity> findAllProvenanceEntitiesWithSBaseName(String geneSymbol, String entityType) {
		List<ProvenanceEntity> geneProvenanceEntityList;
		switch (entityType) {
		case "SBMLSpecies":
			geneProvenanceEntityList = this.sbmlGraphRepository.findAllSBMLSpeciesWithSBaseName(geneSymbol);
			break;
		case "SBMLQualSpecies":
			geneProvenanceEntityList = this.sbmlGraphRepository.findAllSBMLQualSpeciesWithSBaseName(geneSymbol);
			break;
		case "ExternalResource":
			geneProvenanceEntityList = this.sbmlGraphRepository.findAllExternalResourcesByName(geneSymbol);
			break;
		default:
			geneProvenanceEntityList = new ArrayList<>();
			break;
		}
		
		if(geneProvenanceEntityList == null) {
			logger.error("Could not get a valid return List from findAllProvenanceEntitiesWithSBaseName for geneSymbol: " + geneSymbol);
			return new ArrayList<>();
		}
		if(geneProvenanceEntityList.size() > 1) {
			// There actually should not be more than one, so report it.
			logger.info("Found more than one ProvenanceEntity to geneSymbol: " + geneSymbol);
			
		} else if (geneProvenanceEntityList.size() == 0) {
			// There is no ProvenenaceEntity with that sBaseName
			logger.info("Found no ProvenanceEntity to geneSymbol: " + geneSymbol);
		}
		return geneProvenanceEntityList;
	}
	
	public void getRelatedSpecies(String geneSymbol) {
		List<ProvenanceEntity> geneProvenanceEntityList = findAllProvenanceEntitiesWithSBaseName(geneSymbol, "SBMLQualSpecies"); 
		
		List<SBMLSimpleTransition> transitions = this.sbmlGraphRepository.getTransitionsWithParticipant(geneProvenanceEntityList.get(0).getEntityUUID());
		for (SBMLSimpleTransition tr : transitions) {
			logger.info(tr.getTransitionId());
			
			if(geneSymbol.equals(tr.getInputSpecies().getsBaseName())) {
				// the other Species will be influenced by this one by this type of transition
				
			} else if (geneSymbol.equals(tr.getOutputSpecies().getsBaseName())) {
				// the geneSymbol Species will be influenced by the outputSpecies by this type of transition
			}
		}
	}
	
	public boolean isGeneInDB(String geneSymbol) {
		
		if(this.findAllProvenanceEntitiesWithSBaseName(geneSymbol, "ExternalResource").size() > 0) {
			return true;
		} else {
			return false;
		}
	}
	
}
