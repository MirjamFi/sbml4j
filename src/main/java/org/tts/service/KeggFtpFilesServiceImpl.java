package org.tts.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.tts.model.Gene;
import org.tts.model.GeneResponseEntity;

@Service
public class KeggFtpFilesServiceImpl implements KeggFtpFilesService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	

	@Override
	public List<GeneResponseEntity> convertToResponseEntities(List<Gene> genes) {
		List<GeneResponseEntity> geneResponseEntities = new ArrayList<GeneResponseEntity>();
		GeneResponseEntity currentGene;
		if(genes != null) {
			for (Gene gene : genes) {
				currentGene = new GeneResponseEntity();
				currentGene.clearLinks();
				currentGene.setGene(gene);
				currentGene.addSelfLink();
				geneResponseEntities.add(currentGene);

			}
			return geneResponseEntities;
		} else return null;
	}

}
