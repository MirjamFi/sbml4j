package org.tts.repository;

import java.util.UUID;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.tts.model.Gene;

public interface KeggGeneRepository extends Neo4jRepository<Gene, Long> {

	public Gene getBykeggIDString(String keggIDString);
	
	public Gene getBykegg4jId(UUID kegg4jId);
	
}
