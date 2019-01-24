package org.tts.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tts.model.Gene;
import org.tts.repository.KeggGeneRepository;

@Service
public class KeggGeneServiceImpl implements KeggGeneService {

	private KeggGeneRepository keggGeneRepository;
	
	@Autowired
	public KeggGeneServiceImpl(KeggGeneRepository keggGeneRepository) {
		super();
		this.keggGeneRepository = keggGeneRepository;
	}
	
	
	@Override
	public List<Gene> saveOrUpdate(List<Gene> genes) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Gene saveOrUpdate(Gene gene) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Gene getByKegg4jId(UUID kegg4jId) {
		return keggGeneRepository.getBykegg4jId(kegg4jId);
	}
	
	
	

}
