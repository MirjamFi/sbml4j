package org.tts.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class KeggTransition extends KeggItem {

	@Relationship(type="IS_INPUT", direction = Relationship.INCOMING)
	private Gene inputGene;
	
	@Relationship(type="IS_OUTPUT", direction = Relationship.INCOMING)
	private Gene outputGene;
	
	private int sboTerm;
	
	public KeggTransition() {}

	public KeggTransition(String keggIDString, String keggType) {
		super(keggIDString, keggType);
	}

	public Gene getInputGene() {
		return inputGene;
	}

	public void setInputGene(Gene inputGene) {
		this.inputGene = inputGene;
	}

	public Gene getOutputGene() {
		return outputGene;
	}

	public void setOutputGene(Gene outputGene) {
		this.outputGene = outputGene;
	}

	public int getSboTerm() {
		return sboTerm;
	}

	public void setSboTerm(int sboTerm) {
		this.sboTerm = sboTerm;
	}
	
	public String toString() {
		return "Transition " + 
					this.getKegg4jId() + " (Kegg4jId): " +
					this.getKeggIDString() + " (KeggIDString)";
				
	}
}
