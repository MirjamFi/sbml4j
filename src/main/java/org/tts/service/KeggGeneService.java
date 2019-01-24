package org.tts.service;

import java.util.List;
import java.util.UUID;

import org.tts.model.Gene;

public interface KeggGeneService {
	
	List<Gene> saveOrUpdate(List<Gene> genes);
	
	Gene saveOrUpdate(Gene gene);

	Gene getByKegg4jId(UUID kegg4jId);
	
	
}
