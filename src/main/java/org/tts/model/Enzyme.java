package org.tts.model;

import java.util.List;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Enzyme extends KeggItem {

	// the EC number of the enzyme
	private String ecNumber;
	
	// the class of the enzyme
	private String enzymeClass;
	
	// the description for the class of the enzyme
	private String enzymeClassDescription;
	
	// comment string for the enzyme
	private String comment;
	
	@Relationship(type = "HAS_COMPOUND_AS_SUBSTRATE", direction = Relationship.OUTGOING)
	private List<Compound> compoundSubstrates;
	
	@Relationship(type = "HAS_COMPOUND_AS_PRODUCTS", direction = Relationship.OUTGOING)
	private List<Compound> compoundProducts;

	public String getEcNumber() {
		return ecNumber;
	}

	public void setEcNumber(String ecNumber) {
		this.ecNumber = ecNumber;
	}

	public String getEnzymeClass() {
		return enzymeClass;
	}

	public void setEnzymeClass(String enzymeClass) {
		this.enzymeClass = enzymeClass;
	}

	public String getEnzymeClassDescription() {
		return enzymeClassDescription;
	}

	public void setEnzymeClassDescription(String enzymeClassDescription) {
		this.enzymeClassDescription = enzymeClassDescription;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<Compound> getCompoundSubstrates() {
		return compoundSubstrates;
	}

	public void setCompoundSubstrates(List<Compound> compoundSubstrates) {
		this.compoundSubstrates = compoundSubstrates;
	}

	public List<Compound> getCompoundProducts() {
		return compoundProducts;
	}

	public void setCompoundProducts(List<Compound> compoundProducts) {
		this.compoundProducts = compoundProducts;
	}
	
	
}
