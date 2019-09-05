package org.tts.repository.warehouse;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.tts.model.api.Output.MappingReturnType;
import org.tts.model.api.Output.NodeNodeEdge;
import org.tts.model.flat.FlatSpecies;
import org.tts.model.warehouse.MappingNode;

public interface MappingNodeRepository extends Neo4jRepository<MappingNode, Long> {

	MappingNode findByEntityUUID(String mappingNodeEntityUUID);

	@Query(value = "MATCH "
			+ "(m:MappingNode {entityUUID: {0}})-[:Warehouse {warehouseGraphEdgeType: \"CONTAINS\"}]->(fs:FlatSpecies) "
			+ "RETURN fs")
	List<FlatSpecies> getMappingFlatSpecies(String mappingNodeEntityUUID);

	/*
	 * This undirected version seems to be the fastest. Faster than the directed one by 200 ms and faster than the traditional way by factor 3
	 */
	@Query(value = "MATCH "
			+ "(m:MappingNode {entityUUID: {0}})"
			+ "-[:Warehouse {warehouseGraphEdgeType: \"CONTAINS\"}]-"
			+ "(fs:FlatSpecies)-[r]->(fs2:FlatSpecies) "
			+ "RETURN fs.symbol as node1, fs.entityUUID as node1UUID, fs.sboTerm as node1Type,  "
			+ "fs2.symbol as node2, fs2.entityUUID as node2UUID, fs2.sboTerm as node2Type, type(r) as edge;")
	List<NodeNodeEdge> getMappingContentUnDirected(String mappingNodeEntityUUID);

	@Query(value = "MATCH "
			+ "(m:MappingNode {entityUUID: {0}})"
			+ "-[:Warehouse {warehouseGraphEdgeType: \"CONTAINS\"}]->"
			+ "(fs:FlatSpecies)-[r]->(fs2:FlatSpecies) "
			+ "RETURN fs.symbol as node1, fs.entityUUID as node1UUID, fs.sboTerm as node1Type, "
			+ "fs2.symbol as node2, fs2.entityUUID as node2UUID, fs2.sboTerm as node2Type, type(r) as edge;")
	List<NodeNodeEdge> getMappingContentDirected(String mappingNodeEntityUUID);
	
	
	@Query(value = "MATCH "
			+ "(m:MappingNode {entityUUID: {0}})"
			+ "-[:Warehouse {warehouseGraphEdgeType: \"CONTAINS\"}]->"
			+ "(fs:FlatSpecies)-[r]->(fs2:FlatSpecies) "
			+ "RETURN properties(fs) as node1Properties, "
			+ "properties(fs2) as node2properties, type(r) as edge;")
	List<MappingReturnType> getMappingContentAsProperties(String mappingNodeEntityUUID);
	
}
