package org.tts.repository.warehouse;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.tts.model.common.SBMLQualSpecies;
import org.tts.model.provenance.ProvenanceEntity;
import org.tts.model.simple.SBMLSimpleTransition;


public interface SBMLGraphRepository extends Neo4jRepository<ProvenanceEntity, Long> {

	@Query(value="MATCH "
			+ "(s:SBMLSpecies) "
			+ "WHERE s.sBaseName = $sBaseName "
			+ "RETURN s")
	public List<ProvenanceEntity> findAllSBMLSpeciesWithSBaseName(String sBaseName);
	

	@Query(value="MATCH "
			+ "(s:SBMLQualSpecies) "
			+ "WHERE s.sBaseName = $sBaseName "
			+ "RETURN s")
	public List<ProvenanceEntity> findAllSBMLQualSpeciesWithSBaseName(String sBaseName);
	
	@Query(value="MATCH "
			+ "(e:ExternalResourceEntity) "
			+ "WHERE e.name = $name "
			+ "OR any(sec in e.secondaryNames WHERE sec CONTAINS $name) "
			+ "RETURN e ")
	public List<ProvenanceEntity> findAllExternalResourcesByName(String name);
	
	@Query(value="MATCH "
			+ "(q:SBMLQualSpecies)"
			+ "<-[r:IS_INPUT|IS_OUTPUT]-"
			+ "(t:SBMLSimpleTransition)"
			+ "-[r2:IS_INPUT|IS_OUTPUT]->"
			+ "(q2:SBMLQualSpecies) "
			+ "WHERE q.entityUUID = $participantEntityUUID "
			+ "AND type(r) <> type(r2)"
			+ "RETURN q, r, t, r2, q2")
	public List<SBMLSimpleTransition> getTransitionsWithParticipant(String participantEntityUUID);
	
	// Query to find the pathway edges for the flat Mapping
	// which is total overkill, as I only need to search for (g1)<-[CONTAINS]-(p:P1)-[CONTAINS]->(g2)
	// or even easier, when I load a pathway, all genes in there need to be connected. How to facilitate that
	// if provenance information of gene would always go back to simpleModel, it is one hop away to the SBMLSpecies/QualSpecies which is connected to the pathway
	
	/*
	 *  match (p:PathwayNode) where p.entityUUID <> "0583ec8b-7642-4fdb-a082-8fe18ebf580e" with p match (p)-[:Warehouse {warehouseGraphEdgeType:"CONTAINS"}]->(s1:SBMLQualSpecies)-[r1:IS_INPUT|IS_OUTPUT]-(t:SBMLSimpleTransition)-[r2:IS_INPUT|IS_OUTPUT]-(s2:SBMLQualSpecies)<-[:Warehouse {warehouseGraphEdgeType:"CONTAINS"}]-(p) where type(r1) <> type(r2) return p, s1, r1, t, r2, s2 limit 20
	 *  
	 */
	
	
}
