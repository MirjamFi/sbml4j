package org.tts.model.full.ext.qual;

import java.util.List;

import org.neo4j.ogm.annotation.Relationship;
import org.tts.model.common.SBMLQualSpecies;
import org.tts.model.full.ext.SBMLModelExtension;

public class SBMLQualModelExtension extends SBMLModelExtension {
	@Relationship(type = "CONTAINS", direction = Relationship.OUTGOING)
	List<SBMLQualSpecies> sbmlQualSpecies;
	
	@Relationship(type = "CONTAINS", direction = Relationship.OUTGOING)
	List<SBMLQualTransition> sbmlQualTransitions;
	
	public List<SBMLQualSpecies> getSbmlQualSpecies() {
		return sbmlQualSpecies;
	}

	public void setSbmlQualSpecies(List<SBMLQualSpecies> sbmlQualSpecies) {
		this.sbmlQualSpecies = sbmlQualSpecies;
	}

	public List<SBMLQualTransition> getSbmlQualTransitions() {
		return sbmlQualTransitions;
	}

	public void setSbmlQualTransitions(List<SBMLQualTransition> sbmlQualTransitions) {
		this.sbmlQualTransitions = sbmlQualTransitions;
	}
}
