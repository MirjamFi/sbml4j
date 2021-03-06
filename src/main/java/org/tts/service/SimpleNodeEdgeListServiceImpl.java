package org.tts.service;

import org.sbml.jsbml.SBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tts.model.api.Output.NodeEdgeList;
import org.tts.repository.simpleModel.SBMLSimpleTransitionRepository;

@Service
public class SimpleNodeEdgeListServiceImpl implements NodeEdgeListService {

	private SBMLSimpleTransitionRepository sbmlSimpleTransitionRepository;
	
	@Autowired
	public SimpleNodeEdgeListServiceImpl(SBMLSimpleTransitionRepository sbmlSimpleTransitionRepository) {
		super();
		this.sbmlSimpleTransitionRepository = sbmlSimpleTransitionRepository;
	}

	@Override
	public NodeEdgeList getFullNet() {
		NodeEdgeList fullNet = new NodeEdgeList();
		sbmlSimpleTransitionRepository.findAll().forEach(transition->{
			fullNet.addListEntry(	transition.getInputSpecies().getsBaseName(), 
									transition.getInputSpecies().getEntityUUID(),
									transition.getOutputSpecies().getsBaseName(), 
									transition.getOutputSpecies().getEntityUUID(),
									transition.getsBaseSboTerm() == "" ? "Unknown Transition Type" : SBO.getTerm(transition.getsBaseSboTerm()).getName()
								);
		});
		return fullNet;
	}

	@Override
	public NodeEdgeList getMetabolicNet() {
		// TODO Auto-generated method stub
		return null;
	}

}
