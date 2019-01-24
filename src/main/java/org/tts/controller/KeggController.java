package org.tts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.tts.model.Gene;
import org.tts.model.GeneResponseEntity;
import org.tts.service.KeggFtpFilesService;
import org.tts.service.KeggGeneService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class KeggController {

	private KeggGeneService keggGeneService;
	private KeggFtpFilesService keggFtpFilesService;

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
			genesReadFromFile = keggFtpFilesService.parseGenesFile(file);
		} catch (IOException e) {
			return new ResponseEntity<List<GeneResponseEntity>>(HttpStatus.BAD_REQUEST);
		}
		List<Gene> genesPersistedInDb = keggGeneService.saveOrUpdate(genesReadFromFile);
		return new ResponseEntity<List<GeneResponseEntity>>(keggFtpFilesService.convertToResponseEntities(genesPersistedInDb), HttpStatus.OK);
		
	}


	public Gene getGeneByKegg4jId(UUID kegg4jId) {
		return keggGeneService.getByKegg4jId(kegg4jId);
	}
	
}
