package org.tts.model;

import org.neo4j.ogm.annotation.Relationship;
import org.sbml.jsbml.ext.qual.OutputTransitionEffect;

public class SBMLQualOutput extends SBMLSBaseEntity {
		
	private OutputTransitionEffect transitionEffect;
	
	@Relationship(type = "HAS", direction = Relationship.OUTGOING)
	private SBMLQualSpecies qualSpecies;
	
	/**
	 * Non-negative integer to set the output level of the qualSpecies in this Output
	 */
	private int outputLevel;

	public OutputTransitionEffect getTransitionEffect() {
		return transitionEffect;
	}

	public void setTransitionEffect(OutputTransitionEffect transitionEffect) {
		this.transitionEffect = transitionEffect;
	}

	public SBMLQualSpecies getQualSpecies() {
		return qualSpecies;
	}

	public void setQualSpecies(SBMLQualSpecies qualSpecies) {
		this.qualSpecies = qualSpecies;
	}

	public int getOutputLevel() {
		return outputLevel;
	}

	public void setOutputLevel(int outputLevel) {
		this.outputLevel = outputLevel;
	}
}
