package org.tts.model.simple;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.ogm.annotation.Relationship;
import org.tts.model.common.SBMLCompartmentalizedSBaseEntity;
import org.tts.model.common.SBMLSpecies;

public class SBMLSimpleReaction extends SBMLCompartmentalizedSBaseEntity {
	
	private boolean reversible;
	
	@Relationship(type = "IS_REACTANT", direction = Relationship.OUTGOING)
	private List<SBMLSpecies> reactants;
	
	@Relationship(type = "IS_PRODUCT", direction = Relationship.OUTGOING)
	private List<SBMLSpecies> products;
	
	@Relationship(type = "IS_CATALYST", direction = Relationship.OUTGOING)
	private List<SBMLSpecies> catalysts;

	public boolean isReversible() {
		return reversible;
	}

	public void setReversible(boolean reversible) {
		this.reversible = reversible;
	}

	public List<SBMLSpecies> getReactants() {
		return reactants;
	}

	public void setReactants(List<SBMLSpecies> reactants) {
		this.reactants = reactants;
	}

	public void addReactant(SBMLSpecies species) {
		if (this.reactants == null) {
			this.reactants = new ArrayList<>();
		}
		this.reactants.add(species);
	}
	
	public List<SBMLSpecies> getProducts() {
		return products;
	}

	public void setProducts(List<SBMLSpecies> products) {
		this.products = products;
	}

	public void addProduct(SBMLSpecies species) {
		if (this.products == null) {
			this.products = new ArrayList<>();
		}
		this.products.add(species);
	}
	
	public List<SBMLSpecies> getCatalysts() {
		return catalysts;
	}

	public void setCatalysts(List<SBMLSpecies> catalysts) {
		this.catalysts = catalysts;
	}
	
	public void addCatalysts(SBMLSpecies species) {
		if (this.catalysts == null) {
			this.catalysts = new ArrayList<>();
		}
		this.catalysts.add(species);
	}
	
}
