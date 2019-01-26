package org.tts.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tts.model.Gene;
import org.tts.repository.KeggGeneRepository;

@Service
public class KeggGeneServiceImpl implements KeggGeneService {

	private KeggGeneRepository keggGeneRepository;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public KeggGeneServiceImpl(KeggGeneRepository keggGeneRepository) {
		super();
		this.keggGeneRepository = keggGeneRepository;
	}
	
	
	@Override
	public List<Gene> saveOrUpdate(List<Gene> genes) {
		List<Gene> persistedGenes = new ArrayList<Gene>();
		if (genes != null) {
			for (Gene gene : genes) {
				logger.debug("Persisting Gene: " + gene.getKeggIDString());
				persistedGenes.add(this.saveOrUpdate(gene));
			}
		}
		return persistedGenes;
	}


	@Override
	public Gene saveOrUpdate(Gene gene) {
		Gene existingGene = this.getByKegg4jId(gene.getKegg4jId());
		logger.debug("Does gene " + gene.getKeggIDString() + " exist?");
		if(existingGene != null) {
			logger.debug("It does?");
			gene.setId(existingGene.getId());
			gene.setVersion(existingGene.getVersion());
		}
		return keggGeneRepository.save(gene, 1);
		
	}

	@Override
	public Gene getByKegg4jId(String kegg4jId) {
		return keggGeneRepository.getByKegg4jId(kegg4jId);
	}


	@Override
	public Gene getByKeggIDString(String keggIDString) {
		
		return keggGeneRepository.getByKeggIDString(keggIDString);
	}


	@Override
	public List<Gene> findAll() {
		List<Gene> allGenes = new ArrayList<Gene>();
		keggGeneRepository.findAll().forEach(allGenes::add);
		return allGenes;
	}
	
	
	

}
