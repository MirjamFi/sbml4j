package org.tts.repository.common;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.tts.model.api.Output.MetabolicNetworkItem;
import org.tts.model.api.Output.NodeNodeEdge;
import org.tts.model.common.GraphBaseEntity;
import org.tts.model.common.GraphEnum.IDSystem;

public interface GraphBaseEntityRepository extends Neo4jRepository<GraphBaseEntity, Long> {

	GraphBaseEntity findByEntityUUID(String entityUUID);

	boolean existsByEntityUUID(String entityUUID);

	@Query(value = "MATCH (e1:ExternalResourceEntity)-[:BQ {qualifier: {0}}]-" +
					"(s1:SBMLSpecies)-[:IS]-(q1:SBMLQualSpecies)" +
					"-[:IS_INPUT]-(t:SBMLSimpleTransition)-[:IS_OUTPUT]-" +
					"(q2:SBMLQualSpecies)-[:IS]-(s2:SBMLSpecies)" +
					"-[:BQ {qualifier: {1}}]-(e2:ExternalResourceEntity) " +
					"WHERE e1.type = \"kegg.genes\" AND e2.type = \"kegg.genes\""+
					"RETURN  e1.name AS node1, "+
							"e1.entityUUID AS node1UUID, "+
							"t.sBaseSboTerm AS edge, "+
							"e2.name AS node2, "+
							"e2.entityUUID AS node2UUID")
	Iterable<NodeNodeEdge> getInteractionCustomNoGroup(String inputExternalRel, String outputExternalRel);

	@Query(value = "MATCH (e1:ExternalResourceEntity)-[:BQ {qualifier: {0}}]-" +
			"(s1:SBMLSpecies)-[:IS]-(q1:SBMLQualSpecies)" +
			"-[:HAS_GROUP_MEMBER]-(g:SBMLQualSpeciesGroup)" +
			"-[:IS_INPUT]-(t:SBMLSimpleTransition)-[:IS_OUTPUT]-" +
			"(q2:SBMLQualSpecies)-[:IS]-(s2:SBMLSpecies)" +
			"-[:BQ {qualifier: {1}}]-(e2:ExternalResourceEntity) " +
			"WHERE e1.type = \"kegg.genes\" AND e2.type = \"kegg.genes\""+
			"RETURN  e1.name AS node1, "+
					"e1.entityUUID AS node1UUID, "+
					"t.sBaseSboTerm AS edge, "+
					"e2.name AS node2, "+
					"e2.entityUUID AS node2UUID")
	Iterable<NodeNodeEdge> getInteractionCustomGroupOnInput(String inputExternalRel, String outputExternalRel);
	
	@Query(value = "MATCH (e1:ExternalResourceEntity)-[:BQ {qualifier: {0}}]-" +
			"(s1:SBMLSpecies)-[:IS]-(q1:SBMLQualSpecies)" +
			"-[:IS_INPUT]-(t:SBMLSimpleTransition)-[:IS_OUTPUT]-" +
			"(g:SBMLQualSpeciesGroup)-[:HAS_GROUP_MEMBER]-" +
			"(q2:SBMLQualSpecies)-[:IS]-(s2:SBMLSpecies)" +
			"-[:BQ {qualifier: {1}}]-(e2:ExternalResourceEntity) " +
			"WHERE e1.type = \"kegg.genes\" AND e2.type = \"kegg.genes\""+
			"RETURN  e1.name AS node1, "+
					"e1.entityUUID AS node1UUID, "+
					"t.sBaseSboTerm AS edge, "+
					"e2.name AS node2, "+
					"e2.entityUUID AS node2UUID")
	Iterable<NodeNodeEdge> getInteractionCustomGroupOnOutput(String inputExternalRel, String outputExternalRel);
	
	@Query(value = "MATCH (e1:ExternalResourceEntity)-[:BQ {qualifier: {0}}]-" +
			"(s1:SBMLSpecies)-[:IS]-(q1:SBMLQualSpecies)" +
			"-[:HAS_GROUP_MEMBER]-(g:SBMLQualSpeciesGroup)" +
			"-[:IS_INPUT]-(t:SBMLSimpleTransition)-[:IS_OUTPUT]-" +
			"(g:SBMLQualSpeciesGroup)-[:HAS_GROUP_MEMBER]-" +
			"(q2:SBMLQualSpecies)-[:IS]-(s2:SBMLSpecies)" +
			"-[:BQ {qualifier: {1}}]-(e2:ExternalResourceEntity) " +
			"WHERE e1.type = \"kegg.genes\" AND e2.type = \"kegg.genes\""+
			"RETURN  e1.name AS node1, "+
					"e1.entityUUID AS node1UUID, "+
					"t.sBaseSboTerm AS edge, "+
					"e2.name AS node2, "+
					"e2.entityUUID AS node2UUID")
	Iterable<NodeNodeEdge> getInteractionCustomGroupOnBoth(String inputExternalRel, String outputExternalRel);
	
	
	/**
	 * match (p:PathwayNode {pathwayIdString: "path_hsa05100"})-[:Warehouse {warehouseGraphEdgeType: "CONTAINS"}]-(c:SBMLSimpleTransition) with c match x= (c)-[tr:IS_INPUT | IS_OUTPUT ]-(qin:SBMLQualSpecies)-[:IS]-(s:SBMLSpecies)-[bq:BQ {qualifier:"BQB_HAS_VERSION"}]-(e:ExternalResourceEntity {type: "kegg.genes"}) return e.name, c.sBaseSboTerm, c.entityUUID, type(tr)
	 * 
	 * match (p:PathwayNode {pathwayIdString: "path_hsa05100"})
	 * 			-[:Warehouse {warehouseGraphEdgeType: "CONTAINS"}]-
	 *       (c:SBMLSimpleTransition) 
	 *  with c 
	 * match x= (c)
	 * 				-[tr:IS_INPUT | IS_OUTPUT ]-
	 *          (qin:SBMLQualSpecies)
	 *          	-[:IS]-
	 *          (s:SBMLSpecies)
	 *          	-[bq:BQ {qualifier:"BQB_HAS_VERSION"}]-
	 *          (e:ExternalResourceEntity {type: "kegg.genes"}) 
	 * return 	e.name, 
	 * 			c.sBaseSboTerm, 
	 * 			c.entityUUID, 
	 * 			type(tr)
	 */
	
	/**
	 * match (p:PathwayNode {pathwayIdString: "path_hsa04210"})-[:Warehouse {warehouseGraphEdgeType: "CONTAINS"}]-(c:SBMLSimpleTransition) with c match x= (e1:ExternalResourceEntity {type: "kegg.genes"})-[bq1:BQ]-(s1:SBMLSpecies)-[:IS]-(q1:SBMLQualSpecies)-[tr1:IS_INPUT]-(c)-[tr2:IS_OUTPUT ]-(q2:SBMLQualSpecies)-[:IS]-(s2:SBMLSpecies)-[bq2:BQ]-(e2:ExternalResourceEntity {type: "kegg.genes"}) where bq1.qualifier IN ["BQB_HAS_VERSION", "BQB_IS", "BQB_IS_ENCODED_BY"] and bq2.qualifier IN ["BQB_HAS_VERSION", "BQB_IS", "BQB_IS_ENCODED_BY"] return e1.name,e1.entityUUID, bq1.qualifier, s1.sBaseName, s1.entityUUID, type(tr1), c.sBaseSboTerm, c.entityUUID, type(tr2), s2.sBaseName, s2.entityUUID, bq2.qualifier, e2.name, e2.entityUUID
	 * match (p:PathwayNode {pathwayIdString: "path_hsa04210"})-[:Warehouse {warehouseGraphEdgeType: "CONTAINS"}]-(c:SBMLSimpleTransition) with c match x= (e1:ExternalResourceEntity {type: "kegg.genes"})-[bq1:BQ]-(s1:SBMLSpecies)-[:IS]-(q1:SBMLQualSpecies)-[tr1:IS_INPUT]-(c)-[tr2:IS_OUTPUT ]-(q2:SBMLQualSpecies)-[:IS]-(s2:SBMLSpecies)-[bq2:BQ]-(e2:ExternalResourceEntity {type: "kegg.genes"}) where bq1.qualifier IN ["BQB_HAS_VERSION", "BQB_IS", "BQB_IS_ENCODED_BY"] and bq2.qualifier IN ["BQB_HAS_VERSION", "BQB_IS", "BQB_IS_ENCODED_BY"] return e1, s1, q1, c, q2, s2, e2
	 * 
	 * 	match 	(p:PathwayNode {pathwayIdString: "path_hsa05100"})
	 * 				-[:Warehouse {warehouseGraphEdgeType: "CONTAINS"}]-
	 * 			(c:SBMLSimpleTransition) 
	 *   with 	c 
	 *  match   x = 
	 *  		(e1:ExternalResourceEntity {type: "kegg.genes"})
	 *  			-[bq1:BQ]-
	 *  		(s1:SBMLSpecies)
	 *  			-[:IS]-
	 *  		(q1:SBMLQualSpecies)
	 *  			-[tr1:IS_INPUT]-
	 *  		(c)
	 *  			-[tr2:IS_OUTPUT ]-
	 *  		(q2:SBMLQualSpecies)
	 *  			-[:IS]-
	 *  		(s2:SBMLSpecies)
	 *  			-[bq2:BQ]-
	 *  		(e2:ExternalResourceEntity {type: "kegg.genes"}) 
	 * 
	 *  where 	bq1.qualifier IN ["BQB_HAS_VERSION", "BQB_IS", "BQB_IS_ENCODED_BY"] 
	 *    and 	bq2.qualifier IN ["BQB_HAS_VERSION", "BQB_IS", "BQB_IS_ENCODED_BY"] 
	 * return 	e1.name,
	 * 			e1.entityUUID, 
	 * 			bq1.qualifier, 
	 * 			s1.sBaseName, 
	 * 			s1.entityUUID, 
	 * 			type(tr1), 
	 * 			c.sBaseSboTerm, 
	 * 			c.entityUUID, 
	 * 			type(tr2), 
	 * 			s2.sBaseName, 
	 * 			s2.entityUUID, 
	 * 			bq2.qualifier, 
	 * 			e2.name, 
	 * 			e2.entityUUID
	 * 
	 * 
	 * alternative return e1, s1, q1, c, q2, s2, e2
	 */
	@Query(value="MATCH"
			+ "(p:PathwayNode {entityUUID: {0}})"
			+ "-[:Warehouse {warehouseGraphEdgeType: \"CONTAINS\"}]-"
			+ "(t:SBMLSimpleTransition) "
			+ "WITH t "
			+ "MATCH "
			+ "(e1:ExternalResourceEntity {databaseFromUri: {1}})"
			+ "-[bq1:BQ]-(s1:SBMLSpecies)-[:IS]-(q1:SBMLQualSpecies)-[tr1:IS_INPUT]-"
			+ "(t)"
			+ "-[tr2:IS_OUTPUT ]-(q2:SBMLQualSpecies)-[:IS]-(s2:SBMLSpecies)-[bq2:BQ]-"
			+ "(e2:ExternalResourceEntity {databaseFromUri: {1}}) "
			+ "WHERE bq1.qualifier IN [\"BQB_HAS_VERSION\", \"BQB_IS\", \"BQB_IS_ENCODED_BY\"] "
			+ "AND bq2.qualifier IN [\"BQB_HAS_VERSION\", \"BQB_IS\", \"BQB_IS_ENCODED_BY\"] "
			+ "RETURN "
			+ "e1.name AS node1, "
			+ "e1.entityUUID as node1UUID, "
			+ "s1.sBaseSboTerm as node1Type, "
			+ "t.sBaseSboTerm as edge, "
			+ "e2.name as node2, "
			+ "e2.entityUUID as node2UUID, "
			+ "s2.sBaseSboTerm as node2Type"
			)
	Iterable<NodeNodeEdge> getAllFlatTransitionsForPathway(String pathwayEntityUUID, IDSystem idSystem);

	
	
	@Query(value="MATCH"
			+ "(p:PathwayNode {entityUUID: {0}})"
			+ "-[:Warehouse {warehouseGraphEdgeType: \"CONTAINS\"}]-"
			+ "(t:SBMLSimpleTransition) where t.sBaseSboTerm IN ({2})"
			+ "WITH t "
			+ "MATCH "
			+ "(e1:ExternalResourceEntity {databaseFromUri: {1}})"
			+ "-[bq1:BQ]-(s1:SBMLSpecies)-[:IS]-(q1:SBMLQualSpecies)-[tr1:IS_INPUT]-"
			+ "(t)"
			+ "-[tr2:IS_OUTPUT ]-(q2:SBMLQualSpecies)-[:IS]-(s2:SBMLSpecies)-[bq2:BQ]-"
			+ "(e2:ExternalResourceEntity {databaseFromUri: {1}}) "
			+ "WHERE bq1.qualifier IN [\"BQB_HAS_VERSION\", \"BQB_IS\", \"BQB_IS_ENCODED_BY\"] "
			+ "AND bq2.qualifier IN [\"BQB_HAS_VERSION\", \"BQB_IS\", \"BQB_IS_ENCODED_BY\"] "
			+ "RETURN "
			+ "e1.name AS node1, "
			+ "e1.entityUUID as node1UUID, "
			+ "s1.sBaseSboTerm as node1Type, "
			+ "t.sBaseSboTerm as edge, "
			+ "e2.name as node2, "
			+ "e2.entityUUID as node2UUID, "
			+ "s2.sBaseSboTerm as node2Type"
			)
	Iterable<NodeNodeEdge> getFlatTransitionsForPathway(String entityUUID, IDSystem idSystem,
			List<String> transitionSBOTerms);

	/**
	 * Doesnt work that way well for metabolic.
	 * Proably need a different return element for bi/tripartite graph and different handlers for creating graphml
	 * @param entityUUID
	 * @param erType
	 * @return
	 */
	@Query(value="MATCH"
			+ "(p:PathwayNode {entityUUID: {0}})"
			+ "-[:Warehouse {warehouseGraphEdgeType: \"CONTAINS\"}]-"
			+ "(t:SBMLSimpleReaction) "
			+ "WITH t "
			+ "MATCH "
			+ "(e1:ExternalResourceEntity {databaseFromUri: {1}})"
			+ "-[bq1:BQ]-(s1:SBMLSpecies)<-[:IS_REACTANT]-"
			+ "(t)"
			+ "-[:IS_PRODUCT]->(s2:SBMLSpecies)-[bq2:BQ]-"
			+ "(e2:ExternalResourceEntity {databaseFromUri: {1}}) "
			+ "WHERE bq1.qualifier IN [\"BQB_HAS_VERSION\", \"BQB_IS\", \"BQB_IS_ENCODED_BY\"] "
			+ "AND bq2.qualifier IN [\"BQB_HAS_VERSION\", \"BQB_IS\", \"BQB_IS_ENCODED_BY\"] "
			+ "RETURN "
			+ "e1.name AS node1, "
			+ "e1.entityUUID as node1UUID, "
			+ "s1.sBaseSboTerm as node1Type, "
			+ "t.sBaseName as edge, "
			+ "e2.name as node2, "
			+ "e2.entityUUID as node2UUID, "
			+ "s2.sBaseSboTerm as node2Type"
			)
	Iterable<NodeNodeEdge> getFlatMetabolicReactionsForPathway(String entityUUID, IDSystem idSystem);

	
	//match (n:PathwayNode)-[w1:Warehouse]-(s:SBMLSpecies) where n.entityUUID = "f218d66a-d0d0-49e0-b834-ebb6bb5021dd" and w1.warehouseGraphEdgeType="CONTAINS" with s match (e:ExternalResourceEntity)-[b:BQ]-(s)-[t]-(r:SBMLSimpleReaction)  where b.qualifier="BQB_IS" and e.type="KEGGCOMPOUND" return e, b, s, t, r
			
	@Query(value="MATCH "
			+ "(p:PathwayNode)"
			+ "-[w:Warehouse]-"
			+ "(r:SBMLSimpleReaction) "
			+ "WHERE p.entityUUID = $entityUUID "
			+ "AND w.warehouseGraphEdgeType = \"CONTAINS\" "
			+ "WITH r "
			+ "MATCH (r)"
			+ "-[t]->"
			+ "(s:SBMLSpecies)"
			+ "-[bq:BQ]-"
			+ "(e:ExternalResourceEntity) "
			+ "WHERE e.databaseFromUri = $idSystem "
			+ "AND bq.qualifier IN [\"BQB_HAS_VERSION\", \"BQB_IS\", \"BQB_IS_ENCODED_BY\"] "
			+ "RETURN "
			+ "r as reaction, "
			+ "s as sbmlSpecies, "
			+ "e as externalResourceEntity, "
			+ "type(t) as transitionType")
	Iterable<MetabolicNetworkItem> getMetabolicNetworkItemsFromPathway(String entityUUID, IDSystem idSystem);
	
	
	@Query(value="MATCH "
			+ "(p:PathwayNode)"
			+ "-[w:Warehouse]-"
			+ "(r:SBMLSimpleReaction) "
			+ "WHERE p.entityUUID = $entityUUID "
			+ "AND w.warehouseGraphEdgeType = \"CONTAINS\" "
			+ "WITH r "
			+ "MATCH (r)"
			+ "-[t]->"
			+ "(s:SBMLSpecies)"
			+ "-[bq:BQ]-"
			+ "(e:ExternalResourceEntity) "
			+ "WHERE e.databaseFromUri = $idSystem "
			+ "AND e.type in $externalResourceType "
			+ "AND bq.qualifier IN [\"BQB_HAS_VERSION\", \"BQB_IS\", \"BQB_IS_ENCODED_BY\"] "
			+ "RETURN "
			+ "r as reaction, "
			+ "s as sbmlSpecies, "
			+ "e as externalResourceEntity, "
			+ "type(t) as transitionType")
	Iterable<MetabolicNetworkItem> getMetabolicNetworkItemsFromPathway(String entityUUID, IDSystem idSystem, List<String> externalResourceType);
	
	
	/*@Query(value="MATCH "
			+ "(p:PathwayNode)"
			+ "-[w:Warehouse]-"
			+ "(r:SBMLSimpleTransition) "
			+ "WHERE p.entityUUID = $entityUUID "
			+ "AND w.warehouseGraphEdgeType = \"CONTAINS\" "
			+ "AND r.sBaseSboTerm in $transitionSBOTerms "
			+ "WITH r "
			+ "MATCH "
			+ "(e1:ExternalResourceEntity)"
			+ "-[bq1:BQ]-(s1:SBMLSpecies)<-[:IS]-"
			+ "(q1:SBMLQualSpecies)"
			+ "<-[tr1:IS_INPUT]-"
			+ "(r)"
			+ "-[tr2:IS_OUTPUT]->"
			+ "(q2:SBMLQualSpecies)"
			+ "-[:IS]->"
			+ "(s2:SBMLSpecies)"
			+ "-[bq2:BQ]-"
			+ "(e2:ExternalResourceEntity) "
			+ "WHERE e.databaseFromUri = $idSystem "
			+ "AND e1.type in $externalResourceType "
			+ "AND e2.type in $externalResourceType "
			+ "AND bq1.qualifier IN [\"BQB_HAS_VERSION\", \"BQB_IS\", \"BQB_IS_ENCODED_BY\"] "
			+ "AND bq2.qualifier IN [\"BQB_HAS_VERSION\", \"BQB_IS\", \"BQB_IS_ENCODED_BY\"] "
			+ "RETURN "
			+ "r as transition, "
			+ "s as sbmlSpecies, "
			+ "q as sbmlQualSpecies, "
			+ "e as externalResourceEntity, "
			+ "type(t) as transitionType")
	Iterable<MetabolicNetworkItem> getNonMetabolicNetworkItemsFromPathway(String entityUUID, IDSystem idSystem, List<String> externalResourceType, List<String> transitionSBOTerms, List<String> nodeSBOTerms);
	*/
	
	
	
	
	@Query(value="MATCH"
			+ "(p:PathwayNode {entityUUID: {0}})"
			+ "-[:Warehouse {warehouseGraphEdgeType: \"CONTAINS\"}]-"
			+ "(t:SBMLSimpleTransition) where t.sBaseSboTerm IN ({2})"
			+ "WITH t "
			+ "MATCH "
			+ "(e1:ExternalResourceEntity {databaseFromUri: {1}})"
			+ "-[bq1:BQ]-(s1:SBMLSpecies)-[:IS]-(q1:SBMLQualSpecies)-[tr1:IS_INPUT]-"
			+ "(t)"
			+ "-[tr2:IS_OUTPUT ]-(q2:SBMLQualSpecies)-[:IS]-(s2:SBMLSpecies)-[bq2:BQ]-"
			+ "(e2:ExternalResourceEntity {databaseFromUri: {1}}) "
			+ "WHERE bq1.qualifier IN [\"BQB_HAS_VERSION\", \"BQB_IS\", \"BQB_IS_ENCODED_BY\"] "
			+ "AND bq2.qualifier IN [\"BQB_HAS_VERSION\", \"BQB_IS\", \"BQB_IS_ENCODED_BY\"] "
			+ "AND s1.sBaseSboTerm IN ({3}) "
			+ "AND s2.sBaseSboTerm IN ({3}) "
			+ "RETURN "
			+ "e1.name AS node1, "
			+ "e1.entityUUID as node1UUID, "
			+ "s1.sBaseSboTerm as node1Type, "
			+ "t.sBaseSboTerm as edge, "
			+ "e2.name as node2, "
			+ "e2.entityUUID as node2UUID, "
			+ "s2.sBaseSboTerm as node2Type"
			)
	Iterable<NodeNodeEdge> getFlatTransitionsForPathway(String entityUUID, IDSystem idSystem,
			List<String> transitionSBOTerms, List<String> nodeSBOTerms);
	
	/**
	 * match (n:MappingNode)-[:Warehouse {warehouseGraphEdgeType: "CONTAINS"}]-(fs:FlatSpecies) where n.mappingName = "Map_PATHWAYMAPPING_path_hsa05210_KEGGGENES" detach delete fs
	 * Delete FlatSpecies of mapping with mappingName
	 */
	
	
	/**
	 * match (org:Organism)-[f:FOR]-(n:MappingNode)-[p:PROV]-(o:GraphBaseEntity) where n.mappingName = "Map_PATHWAYMAPPING_path_hsa05210_KEGGGENES" delete f, p, n
	 * delete mapping node after flatspecies have been deleted
	 */
}
