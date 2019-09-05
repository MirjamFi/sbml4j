package org.tts.controller;

import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tts.model.common.ExternalResourceEntity;
import org.tts.model.common.GraphBaseEntity;
import org.tts.model.common.SBMLSBaseEntity;
import org.tts.service.GraphBaseEntityService;
import org.tts.service.HttpService;
import org.tts.service.SBMLService;
import org.tts.service.SBMLSimpleModelUtilityServiceImpl;
import org.tts.service.UtilityService;

@RestController
public class GraphBaseEntityTestController {

	GraphBaseEntityService graphBaseEntityService;
	
	UtilityService utilityService;
	
	HttpService httpService;
	
	SBMLService sbmlService;
	
	SBMLSimpleModelUtilityServiceImpl sbmlSimpleModelUtilityServiceImpl;

	@Autowired
	public GraphBaseEntityTestController(	GraphBaseEntityService graphBaseEntityService,
											UtilityService utilityService,
											HttpService httpService,
											@Qualifier("SBMLSimpleModelServiceImpl") SBMLService sbmlService,
											SBMLSimpleModelUtilityServiceImpl sbmlSimpleModelUtilityServiceImpl) {
		super();
		this.graphBaseEntityService = graphBaseEntityService;
		this.utilityService = utilityService;
		this.httpService = httpService;
		this.sbmlService = sbmlService;
		this.sbmlSimpleModelUtilityServiceImpl = sbmlSimpleModelUtilityServiceImpl;
	}
	/**
	 * Test GraphBaseEntity
	 */

	@RequestMapping(value = "/testListBaseEntities", method=RequestMethod.GET)	
	public Iterable<GraphBaseEntity> getBaseEntities() {
		
		return graphBaseEntityService.findAll();
	}
	
	
	@RequestMapping(value = "/testBaseEntity/{uuid}", method=RequestMethod.GET)	
	public GraphBaseEntity getNewBaseEntity(@PathVariable String uuid) {
		
		return graphBaseEntityService.findByEntityUUID(uuid);
	}
	
	@RequestMapping(value = "/testBaseEntity", method=RequestMethod.POST)	
	public GraphBaseEntity createNewBaseEntity() {
		GraphBaseEntity newEntity = new GraphBaseEntity();
		newEntity.setEntityUUID(UUID.randomUUID().toString());
		return graphBaseEntityService.persistEntity(newEntity);
	}
	
	/**
	 * Test SBMLSBaseEntity
	 */
	
	
	@RequestMapping(value = "/testSBase", method=RequestMethod.POST)	
	public GraphBaseEntity createNewSBaseEntity(@RequestParam(value = "sbaseId") String sBaseId,
												@RequestParam(value = "sbaseName") String sBaseName) {
		SBMLSBaseEntity newEntity = new SBMLSBaseEntity();
		newEntity.setEntityUUID(UUID.randomUUID().toString());
		newEntity.setsBaseId(sBaseId);
		newEntity.setsBaseName(sBaseName);
		return graphBaseEntityService.persistEntity(newEntity);
	}
	
	@RequestMapping(value = "/testSBase/{uuid}", method=RequestMethod.GET)
	public GraphBaseEntity getGraphBaseSBase(@PathVariable String uuid) {
		return graphBaseEntityService.findByEntityUUID(uuid);
	}
	
	
	@RequestMapping(value = "/testProc/sboChildren", method=RequestMethod.GET)
	public ResponseEntity<Set<String>> getSBOChildren (@RequestParam(value = "parent") String parent,
										@RequestParam(value = "mode") String mode) {
		if(mode.equals("direct")) {
			return new ResponseEntity<>(this.utilityService.getSBOChildren(parent), HttpStatus.OK);
		} else if (mode.equals("full")) {
			return new ResponseEntity<>(this.utilityService.getAllSBOChildren(parent), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@RequestMapping(value = "/testProc/compound", method=RequestMethod.POST)
	public ResponseEntity<ExternalResourceEntity> parseCompound(@RequestParam(value = "resource") String resource) {
		if(!resource.contains("kegg.compound")) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			ExternalResourceEntity testEntity = new ExternalResourceEntity();
			this.sbmlSimpleModelUtilityServiceImpl.setGraphBaseEntityProperties(testEntity);
			this.httpService.setCompoundAnnotationFromResource(resource, testEntity);
			
			
			return new ResponseEntity<>(testEntity, HttpStatus.OK);
		}
	}
	
	
	
}
