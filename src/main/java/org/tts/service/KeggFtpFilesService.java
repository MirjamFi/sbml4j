package org.tts.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import org.tts.model.Gene;
import org.tts.model.GeneResponseEntity;

public interface KeggFtpFilesService {

	List<Gene> parseGenesFile(MultipartFile file) throws IOException;
	
	List<GeneResponseEntity> convertToResponseEntities(List<Gene> genes);
	
}
