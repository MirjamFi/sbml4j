package org.tts.repository.flat;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.tts.model.flat.FlatNode;

public interface FlatNodeRepository extends Neo4jRepository<FlatNode, Long> {

	FlatNode findByEntityUUID(String entityUUID);

	
	@Query(value= "MATCH "
			+ "(m:MappingNode {entityUUID: {0}})"
			+ "-[:Warehouse {warehouseGraphEdgeType: \"CONTAINS\"}]-"
			+ "(fs:FlatNode)-[r]-(fs2:FlatNode)"
			+ "-[:Warehouse {warehouseGraphEdgeType: \"CONTAINS\"}]-"
			+ "(m:MappingNode {entityUUID: {0}})"
			+ "RETURN fs, r, fs2")
	List<FlatNode> findAllNetworkNodes(String entityUUID);


	@Query(value="MATCH (fs:FlatNode {entityUUID : {0}}) call apoc.path.expand(fs, {1}, \"+FlatNode\", {2}, {3}) yield path as pp return pp;")
	List<FlatNode> findNetworkContext(String startNodeUUID, String relationTypesApocString,
			int minPathLength, int maxPathLength);

	

}
