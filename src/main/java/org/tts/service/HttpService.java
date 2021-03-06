package org.tts.service;

import java.util.List;

import org.tts.model.common.ExternalResourceEntity;
import org.tts.model.flat.FlatSpecies;
import org.tts.model.warehouse.MappingNode;

public interface HttpService {

	public List<String> getGeneNamesFromKeggURL(String resource);

	public ExternalResourceEntity setCompoundAnnotationFromResource(String resource, ExternalResourceEntity entity);
	
	public List<FlatSpecies> getMyDrugCompoundsForNetwork(String mydrugURL, MappingNode mappingNode);
}
