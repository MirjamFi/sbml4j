package org.tts.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.tts.model.Gene;
import org.tts.model.GeneResponseEntity;
import org.tts.service.KeggFtpFilesService;
import org.tts.service.KeggGeneService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class KeggController {

	private KeggGeneService keggGeneService;
	private KeggFtpFilesService keggFtpFilesService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public KeggController(KeggGeneService keggGeneService, KeggFtpFilesService keggFtpFilesService) {
		super();
		this.keggGeneService = keggGeneService;
		this.keggFtpFilesService = keggFtpFilesService;
	}
	
	
	@RequestMapping(value = "/keggGenes", method=RequestMethod.POST)
	public ResponseEntity<List<GeneResponseEntity>> loadKeggGenes(@RequestParam("file") MultipartFile file)
	{
		List<Gene> genesReadFromFile = new ArrayList<Gene>();
		try {
			logger.debug("Reading from file" + file.getOriginalFilename());
			genesReadFromFile = parseGenesFile(file);
			logger.debug("Finished " + genesReadFromFile.size());
		} catch (IOException e) {
			return new ResponseEntity<List<GeneResponseEntity>>(HttpStatus.BAD_REQUEST);
		}
		logger.debug("Persisting in db");
		List<Gene> genesPersistedInDb = keggGeneService.saveOrUpdate(genesReadFromFile);
		logger.debug("Finished");
		
		return new ResponseEntity<List<GeneResponseEntity>>(keggFtpFilesService.convertToResponseEntities(genesPersistedInDb), HttpStatus.OK);
		
	}


	private List<Gene> parseGenesFile(MultipartFile file) throws IOException {
		List<Gene> genes = new ArrayList<Gene>();
		BufferedReader br;
		String line;
		InputStream is = file.getInputStream();
		br = new BufferedReader(new InputStreamReader(is));
		String[] splitted;
		Gene workingGene = null;
		while ((line = br.readLine()) != null) {
			if(line.startsWith("ENTRY")) {
				workingGene = null;
				splitted = line.split("\\s+");
				for(int i =0; i!= splitted.length; i++) {
					logger.debug(i + ": " + splitted[i]);
				}
				workingGene = new Gene(splitted[1], splitted[2]);
			} else if(line.startsWith("///")) {
				// entry is finished here	
				if(workingGene != null) {
					logger.debug(workingGene.getKeggIDString() + ", " + workingGene.getKeggType());
					Gene existingGene = keggGeneService.getByKeggIDString(workingGene.getKeggIDString());
					if(existingGene != null) {
						logger.debug("Gene exists, attempting to update");
						workingGene.setKegg4jId(existingGene.getKegg4jId());
						workingGene.setId(existingGene.getId());
						workingGene.setVersion(existingGene.getVersion());
					}
					genes.add(workingGene);
				}
			}
		}
		return genes;
	}


	@RequestMapping(value = "/keggGene/{kegg4jId}", method=RequestMethod.GET)
	public Gene getGeneByKegg4jId(@PathVariable String kegg4jId) {
		return keggGeneService.getByKegg4jId(kegg4jId);
	}
	
}
