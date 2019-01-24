package org.tts.model;

import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Compound extends KeggItem {

	// the formula string of the compound
	private String formula;
	
	// the exact mass of the compound
	private String exactMass;
	
	// the molecular weight of the compound
	private String molWeight;

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public String getExactMass() {
		return exactMass;
	}

	public void setExactMass(String exactMass) {
		this.exactMass = exactMass;
	}

	public String getMolWeight() {
		return molWeight;
	}

	public void setMolWeight(String molWeight) {
		this.molWeight = molWeight;
	}
	
	
}
