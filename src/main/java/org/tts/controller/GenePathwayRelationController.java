package org.tts.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tts.model.warehouse.PathwayNode;
import org.tts.service.GenePathwayRelationAnalysisService;

@RestController
public class GenePathwayRelationController {

	@Autowired
	GenePathwayRelationAnalysisService genePathwayRelationAnalysisService;
	
	
	@RequestMapping(value = "/pathways", method=RequestMethod.GET)
	public List<String> getPathways(@RequestParam(value = "geneSymbol", defaultValue = "") String geneSymbol) {
		
		List<String> pathwayIds = new ArrayList<>();
		
		Iterable<PathwayNode> pathwayNodeIterable = this.genePathwayRelationAnalysisService.getPathwaysToGene(geneSymbol); //.forEach((pathway) -> pathwayIds.add(pathway.getPathwayIdString()));
		if (pathwayNodeIterable != null) {
			pathwayNodeIterable.forEach((pathway) -> pathwayIds.add(pathway.getPathwayIdString()));
		}
		return pathwayIds;
	}
	
	public void other() {
		/*Iterator<PathwayNode> pathwayIterator = pathways.iterator();
		while (pathwayIterator.hasNext()) {
			PathwayNode current = pathwayIterator.next();
			current.getPathwayIdString();
			current.getPathwayNameString();
		}*/
	}
	
	@RequestMapping(value = "/relatedSpecies", method=RequestMethod.GET)
	public List<String> getRelatedSpecies(@RequestParam(value = "geneSymbol", defaultValue = "") String geneSymbol) {
		List<String> ret = new ArrayList<>();
		this.genePathwayRelationAnalysisService.getRelatedSpecies(geneSymbol);
		return ret;
	}
	
	
	@RequestMapping(value="/geneList", method=RequestMethod.POST)
	public List<String> areGenesInService(@RequestBody List<String> geneList) {
		List<String> ret = new ArrayList<>();
		for (String geneSymbol : geneList) {
			if (!this.genePathwayRelationAnalysisService.isGeneInDB(geneSymbol)) {
				ret.add(geneSymbol);
			}
		}
		return ret;
	}
	
}
