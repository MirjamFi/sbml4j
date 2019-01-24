package org.tts.model;

import java.util.List;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;


@NodeEntity
public class Organism extends KeggItem {
	
	private String shortHandle; // like hsa
	
	private String longName; // like Homo sapiens
	
	private String shortName; // like human
	
	private String identifier; // like T01001 -> use this as the keggIdString from KeggItem
	
	@Relationship(type = "IN_ORGANISM", direction = Relationship.INCOMING)
	private List<Gene> genes;

	public String getShortHandle() {
		return shortHandle;
	}

	public void setShortHandle(String shortHandle) {
		this.shortHandle = shortHandle;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public List<Gene> getGenes() {
		return genes;
	}

	public void setGenes(List<Gene> genes) {
		this.genes = genes;
	}
	
	
	
	
	
}

