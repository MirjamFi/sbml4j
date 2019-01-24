package org.tts.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.tts.model.Gene;
import org.tts.model.GeneResponseEntity;

@Service
public class KeggFtpFilesServiceImpl implements KeggFtpFilesService {

	@Override
	public List<Gene> parseGenesFile(MultipartFile file) throws IOException {
		List<Gene> genes = new ArrayList<Gene>();
		BufferedReader br;
		String line;
		InputStream is = file.getInputStream();
		br = new BufferedReader(new InputStreamReader(is));
		while ((line = br.readLine()) != null) {
			System.out.println(line);
		}
		return genes;
	}

	@Override
	public List<GeneResponseEntity> convertToResponseEntities(List<Gene> genes) {
		List<GeneResponseEntity> geneResponseEntities = new ArrayList<GeneResponseEntity>();
		GeneResponseEntity currentGene = new GeneResponseEntity();
		if(genes != null) {
			for (Gene gene : genes) {
				currentGene.clearLinks();
				currentGene.setGene(gene);
				currentGene.addSelfLink();
				geneResponseEntities.add(currentGene);

			}
			return geneResponseEntities;
		} else return null;
	}

}
