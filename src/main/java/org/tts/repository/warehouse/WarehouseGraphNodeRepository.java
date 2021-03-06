package org.tts.repository.warehouse;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.tts.model.common.GraphEnum.WarehouseGraphEdgeType;
import org.tts.model.provenance.ProvenanceEntity;
import org.tts.model.warehouse.WarehouseGraphNode;

public interface WarehouseGraphNodeRepository extends Neo4jRepository<WarehouseGraphNode, Long> {

	/**
	 * match (w:WarehouseGraphNode)-[e:Warehouse {warehouseGraphEdgeType: "CONTAINS"}]-(:WarehouseGraphNode {entityUUID: "2c841061-fe1c-439b-927a-82ddb06ea604"}) return w;
	 * 
	 * @param contains
	 * @param endNode
	 * @return
	 */
	@Query(value="MATCH"
			+ "(w:WarehouseGraphNode)"
			+ "-[e:Warehouse {warehouseGraphEdgeType: {0}}]->"
			+ "(:WarehouseGraphNode {entityUUID: {1}})"
			+ "RETURN w")
	WarehouseGraphNode findByWarehouseGraphEdgeTypeAndEndNode(WarehouseGraphEdgeType warehouseGraphEdgeType, String EndNodeEntityUUID);

	
	@Query(value="MATCH"
			+ "(s:WarehouseGraphNode {entityUUID: {1}})"
			+ "-[e:Warehouse {warehouseGraphEdgeType: {0}}]->"
			+ "(p:ProvenanceEntity)"
			+ "RETURN p")
	List<ProvenanceEntity> findAllByWarehouseGraphEdgeTypeAndStartNode(WarehouseGraphEdgeType warehouseGraphEdgeType,
			String startNodeEntityUUID);


	@Query(value="MATCH"
			+ "(w:WarehouseGraphNode {entityUUID: {0}})"
			+ "-[:FOR]->"
			+ "(o:Organism)"
			+ "RETURN o")
	WarehouseGraphNode findOrganismForWarehouseGraphNode(String entityUUID);

	
	
	@Query(value = "MATCH p=(e1:ProvenanceEntity {entityUUID: {0}})-" +
			"[:Warehouse {warehouseGraphEdgeType: {2}}]-" +
			"(e2:ProvenanceEntity {entityUUID: {1}}) " +
			"RETURN count(p) > 0")
	boolean areWarehouseEntitiesConnectedWithWarehouseGraphEdgeType(String sourceEntityUUID, 
																String targetEntityUUID,
																WarehouseGraphEdgeType edgetype);
	}
