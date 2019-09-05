package org.tts.model.api.Output;

import org.springframework.data.neo4j.annotation.QueryResult;
import org.tts.model.common.ExternalResourceEntity;
import org.tts.model.common.SBMLSpecies;
import org.tts.model.simple.SBMLSimpleReaction;

@QueryResult
public class MetabolicNetworkItem {

	private SBMLSimpleReaction reaction;

	private ExternalResourceEntity externalResourceEntity;

	private SBMLSpecies sbmlSpecies;
	
	private String transitionType;

	public SBMLSimpleReaction getReaction() {
		return reaction;
	}

	public void setReaction(SBMLSimpleReaction reaction) {
		this.reaction = reaction;
	}

	public ExternalResourceEntity getExternalResourceEntity() {
		return externalResourceEntity;
	}

	public void setExternalResourceEntity(ExternalResourceEntity externalResourceEntity) {
		this.externalResourceEntity = externalResourceEntity;
	}

	public SBMLSpecies getSbmlSpecies() {
		return sbmlSpecies;
	}

	public void setSbmlSpecies(SBMLSpecies sbmlSpecies) {
		this.sbmlSpecies = sbmlSpecies;
	}

	public String getTransitionType() {
		return transitionType;
	}

	public void setTransitionType(String transitionType) {
		this.transitionType = transitionType;
	}
	
}
