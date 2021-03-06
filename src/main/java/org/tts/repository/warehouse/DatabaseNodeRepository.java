package org.tts.repository.warehouse;

import java.util.List;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.tts.model.warehouse.DatabaseNode;

public interface DatabaseNodeRepository extends Neo4jRepository<DatabaseNode, Long> {

	List<DatabaseNode> findBySourceAndSourceVersion(String source, String sourceVersion);

	DatabaseNode findByEntityUUID(String entityUUID);

}
