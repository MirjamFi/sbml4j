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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
	@RequestMapping(value = "/keggGenes", method=RequestMethod.GET)
	public ResponseEntity<List<GeneResponseEntity>> getKeggGenes(@RequestParam("organism") String organism)
	{
		return new ResponseEntity<List<GeneResponseEntity>>(keggFtpFilesService.convertToResponseEntities(keggGeneService.findAll()), HttpStatus.OK);
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
		String lastLine = "";
		Map<String, String> dbLinks = new HashMap<String, String> ();
		while ((line = br.readLine()) != null) {
			if(line.startsWith("ENTRY")) {
				lastLine = "ENTRY";
				logger.debug("Entry line");
				workingGene = null;
				splitted = line.split("\\s+");
				for(int i =0; i!= splitted.length; i++) {
					logger.debug(i + ": " + splitted[i]);
				}
				workingGene = new Gene(splitted[1], splitted[2]);
			} else if(line.startsWith("NAME")) {
				lastLine = "NAME";
				logger.debug("Name line");
				splitted = line.split("\\s+");
				for(int i =0; i!= splitted.length; i++) {
					logger.debug(i + ": " + splitted[i]);
				}
				if(splitted.length > 1 && splitted[1].endsWith(",")) {
					workingGene.setKeggNamePrimary(splitted[1].substring(0, splitted[1].length() - 1));
				}
				if(splitted.length > 2) {
					String keggNamesSecondary = "";
					for (int i = 2; i!= splitted.length; i++) {
						keggNamesSecondary += splitted[i];
					}
					workingGene.setKeggNamesSecondary(keggNamesSecondary);
				}
			} else if(line.startsWith("DEFINITION")) {
				lastLine = "DEFINITION";
				logger.debug("Definition line");
				workingGene.setKeggDefinition(line.substring(12));
			} else if (line.startsWith("ORGANISM")) {
				lastLine = "ORGANISM";
				logger.debug("Organism Line");
			} else if(line.startsWith("POSITION")) {
				logger.debug("Position Line");
				lastLine = "POSITION";
				workingGene.setGenomePosition(line.split("\\s+")[1]);
			} else if(line.startsWith("DBLINKS")) {
				logger.debug("DBLinks Line");
				lastLine = "DBLINKS";
				splitted = line.split("\\s+");
				dbLinks.put(splitted[1].substring(0, splitted[1].length() - 1), splitted[2]);
			} else if(line.startsWith("STRUCTURE")) {
				lastLine = "STRUCTURE";
				workingGene.setPdbId(line.split("\\s+")[2]);
			} else if(line.startsWith("AASEQ")) {
				lastLine = "AASEQ";
				workingGene.setAaSeqLength(Integer.parseInt(line.split("\\s+")[1]));
				workingGene.setAaSeq("");
			} else if(line.startsWith("NTSEQ")) {
				lastLine = "NTSEQ";
				workingGene.setNtSeqLength(Integer.parseInt(line.split("\\s+")[1]));
				workingGene.setNtSeq("");
			}
			
			else if(line.startsWith(" ")) {
				if (lastLine.equals("NAME")) {
					String keggNamesSecondary = workingGene.getKeggNamesSecondary();
					splitted = line.split("\\s+");
					for (String split : splitted) {
						keggNamesSecondary += split;
					}
					workingGene.setKeggNamesSecondary(keggNamesSecondary);
				} else if (lastLine.equals("DEFINITION")) {
					workingGene.setKeggDefinition(workingGene.getKeggDefinition().concat(line.substring(12)));
				} else if (lastLine.equals("ORGANISM")) {
					logger.debug("WARNING: The last line was ORGANISM, and apparently this is the next line: " + line);
				} else if (lastLine.equals("POSITION")) {
					logger.debug("WARNING: The last line was POSITION, and apparently this is the next line: " + line);
				} else if (lastLine.equals("DBLINKS")) {
					splitted = line.split("\\s+");
					dbLinks.put(splitted[1].substring(0, splitted[1].length() - 1), splitted[2]);
				} else if (lastLine.equals("STRUCTURE")) {
					logger.debug("WARNING: The last line was STRUCTURE, and apparently this is the next line: " + line);
				} else if (lastLine.equals("AASEQ")) {
					workingGene.setAaSeq(workingGene.getAaSeq().concat(line.trim()));
				} else if (lastLine.equals("NTSEQ")) {
					workingGene.setNtSeq(workingGene.getNtSeq().concat(line.trim()));
				}
			}
			else if(line.startsWith("///")) {
				// entry is finished here
				
				if(workingGene != null) {
					logger.debug(workingGene.getKeggIDString() + ", " + workingGene.getKeggType());
					for (String key : dbLinks.keySet()) {
						switch (key) {
						case "NCBI-GeneID":
							workingGene.setNcbiGeneId(dbLinks.get(key));
							break;
						case "NCBI-ProteinID":
							workingGene.setNcbiProteinID(dbLinks.get(key));
							break;
						case "OMIM":
							workingGene.setOmimId(dbLinks.get(key));
							break;
						case "HGNC":
							workingGene.setHgncId(dbLinks.get(key));
							break;
						case "Ensembl":
							workingGene.setEnsembleId(dbLinks.get(key));
							break;
						case "Vega":
							workingGene.setVegaId(dbLinks.get(key));
							break;
						case "Pharos":
							workingGene.setPharosId(dbLinks.get(key));
							break;
						case "UniProt":
							workingGene.setUniprotId(dbLinks.get(key));
							break;
						case "miRBase":
							workingGene.setMiRBaseId(dbLinks.get(key));
							break;
						default:
							logger.debug("Warning: There is a different case for dbLinks: " + key + " with entry: " + dbLinks.get(key));
							break;
						}
					}
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
