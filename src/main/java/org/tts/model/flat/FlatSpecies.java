package org.tts.model.flat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.neo4j.ogm.annotation.Relationship;
import org.tts.model.common.ContentGraphNode;
import org.tts.model.common.GraphEnum.RelationType;


public class FlatSpecies extends ContentGraphNode {

	private String simpleModelEntityUUID; // maybe use ProvenanceEdge wasDerivedFrom to link back to the Entity in the simpleModel?
	
	private String symbol;
	
	private String sboTerm;
	
	
	 @Relationship(type = "dissociation")
	 List<FlatSpecies> dissociationSpeciesList; // SBO:0000180
	 
	 @Relationship(type = "dephosphorylation")
	 List<FlatSpecies> dephosphorylationSpeciesList; // SBO:0000330
	 
	 @Relationship(type = "uncertain process")
	 List<FlatSpecies> uncertainProcessSpeciesList; // SBO:0000396
	 
	 @Relationship(type = "non-covalent binding")
	 List<FlatSpecies> nonCovalentBindingSpeciesList; // SBO:0000177
	 
	 @Relationship(type = "stimulation")
	 List<FlatSpecies> stimulationSpeciesList; // SBO:0000170
	 
	 @Relationship(type = "glycosylation")
	 List<FlatSpecies> glycosylationSpeciesList; // SBO:0000217
	 
	 @Relationship(type = "phosphorylation")
	 List<FlatSpecies> phosphorylationSpeciesList; // SBO:0000216
	 
	 @Relationship(type = "inhibition")
	 List<FlatSpecies> inhibitionSpeciesList; // SBO:0000169
	 
	 @Relationship(type = "ubiquitination")
	 List<FlatSpecies> ubiquitinationSpeciesList; // SBO:0000224
	 
	 @Relationship(type = "methylation")
	 List<FlatSpecies> methylationSpeciesList; // SBO:0000214
	 
	 @Relationship(type = "molecular interaction")
	 List<FlatSpecies> molecularInteractionSpeciesList; // SBO:0000344
	 
	 @Relationship(type = "control")
	 List<FlatSpecies> controlSpeciesList; // SBO:0000168
	 
	 @Relationship(type = "unknownFromSource")
	 List<FlatSpecies> unknownFromSourceSpeciesList; // no SBO, eg. hsa05133 qual_K
	 
	
	 
	 public String getSimpleModelEntityUUID() {
		return simpleModelEntityUUID;
	}

	public void setSimpleModelEntityUUID(String simpleModelEntityUUID) {
		this.simpleModelEntityUUID = simpleModelEntityUUID;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Map<String, List<FlatSpecies>> getAllRelatedSpecies(){
		Map<String, List<FlatSpecies>> allRelatedSpecies = new HashMap<>();
		for(RelationType type : RelationType.values()) {
			switch(type) {
			case INHIBITION:
				if(this.inhibitionSpeciesList != null) {
					allRelatedSpecies.put(type.getRelType(), inhibitionSpeciesList);
				}
				break;
			case CONTROL:
				if(this.controlSpeciesList != null) {
					allRelatedSpecies.put(type.getRelType(), controlSpeciesList);
				}
				break;
			case DEPHOSPHORYLATION:
				if(this.dephosphorylationSpeciesList != null) {
					allRelatedSpecies.put(type.getRelType(), dephosphorylationSpeciesList);
				}
				break;
			case DISSOCIATION:
				if(this.dissociationSpeciesList != null) {
					allRelatedSpecies.put(type.getRelType(), dissociationSpeciesList);
				}
				break;
			case GLYCOSYLATION:
				if(this.glycosylationSpeciesList != null) {
					allRelatedSpecies.put(type.getRelType(), glycosylationSpeciesList);
				}
				break;
			case METHYLATION:
				if(this.methylationSpeciesList != null) {
					allRelatedSpecies.put(type.getRelType(), methylationSpeciesList);
				}
				break;
			case MOLECULARINTERACTION:
				if(this.molecularInteractionSpeciesList != null) {
					allRelatedSpecies.put(type.getRelType(), molecularInteractionSpeciesList);
				}
				break;
			case NONCOVALENTBINDING:
				if(this.nonCovalentBindingSpeciesList != null) {
					allRelatedSpecies.put(type.getRelType(), nonCovalentBindingSpeciesList);
				}
				break;
			case PHOSPHORYLATION:
				if(this.phosphorylationSpeciesList != null) {
					allRelatedSpecies.put(type.getRelType(), phosphorylationSpeciesList);
				}
				break;
			case STIMULATION:
				if(this.stimulationSpeciesList != null) {
					allRelatedSpecies.put(type.getRelType(), stimulationSpeciesList);
				}
				break;
			case UBIQUITINATION:
				if(this.ubiquitinationSpeciesList != null) {
					allRelatedSpecies.put(type.getRelType(), ubiquitinationSpeciesList);
				}
				break;
			case UNCERTAINPROCESS:
				if(this.uncertainProcessSpeciesList != null) {
					allRelatedSpecies.put(type.getRelType(), uncertainProcessSpeciesList);
				}
				break;
			case UNKNOWNFROMSOURCE:
				if(this.unknownFromSourceSpeciesList != null) {
					allRelatedSpecies.put(type.getRelType(), unknownFromSourceSpeciesList);
				}
				break;
			default:
				if(this.unknownFromSourceSpeciesList != null) {
					allRelatedSpecies.put("unknownFromSource", unknownFromSourceSpeciesList);
				}
				break;
			}
		}
		return allRelatedSpecies;
	}
	
	public FlatSpecies addRelatedSpecies(Map<String, List<FlatSpecies>> relatedSpeciesMap) {
		relatedSpeciesMap.forEach((relationType, node2List) -> {
			for(FlatSpecies other : node2List) {
				this.addRelatedSpecies(other, relationType);
			}
		});
		return this;
	}
	
	public FlatSpecies addRelatedSpecies(FlatSpecies other, String sboTermString) {
		 switch (sboTermString) {
		case "SBO:0000180":
			if(dissociationSpeciesList == null) {
				dissociationSpeciesList = new ArrayList<>();
			}
			dissociationSpeciesList.add(other);
			break;
		case "SBO:0000330":
			if(dephosphorylationSpeciesList == null) {
				dephosphorylationSpeciesList = new ArrayList<>();
			}
			dephosphorylationSpeciesList.add(other);
			break;
		case "SBO:0000396":
			if(uncertainProcessSpeciesList == null) {
				uncertainProcessSpeciesList = new ArrayList<>();
			}
			uncertainProcessSpeciesList.add(other);
			break;
		case "SBO:0000177":
			if(nonCovalentBindingSpeciesList == null) {
				nonCovalentBindingSpeciesList = new ArrayList<>();
			}
			nonCovalentBindingSpeciesList.add(other);
			break;
		case "SBO:0000170":
			if(stimulationSpeciesList == null) {
				stimulationSpeciesList = new ArrayList<>();
			}
			stimulationSpeciesList.add(other);
			break;
		case "SBO:0000217":
			if(glycosylationSpeciesList == null) {
				glycosylationSpeciesList = new ArrayList<>();
			}
			glycosylationSpeciesList.add(other);
			break;
		case "SBO:0000216":
			if(phosphorylationSpeciesList == null) {
				phosphorylationSpeciesList = new ArrayList<>();
			}
			phosphorylationSpeciesList.add(other);
			break;
		case "SBO:0000169":
			if(inhibitionSpeciesList == null) {
				inhibitionSpeciesList = new ArrayList<>();
			}
			inhibitionSpeciesList.add(other);
			break;
		case "SBO:0000224":
			if(ubiquitinationSpeciesList == null) {
				ubiquitinationSpeciesList = new ArrayList<>();
			}
			ubiquitinationSpeciesList.add(other);
			break;
		case "SBO:0000214":
			if(methylationSpeciesList == null) {
				methylationSpeciesList = new ArrayList<>();
			}
			methylationSpeciesList.add(other);
			break;
		case "SBO:0000344":
			if(molecularInteractionSpeciesList == null) {
				molecularInteractionSpeciesList = new ArrayList<>();
			}
			molecularInteractionSpeciesList.add(other);
			break;
		case "SBO:0000168":
			if(controlSpeciesList == null) {
				controlSpeciesList = new ArrayList<>();
			}
			controlSpeciesList.add(other);
			break;
		case "unknownFromSource":
			if(unknownFromSourceSpeciesList == null) {
				unknownFromSourceSpeciesList = new ArrayList<>();
			}
			unknownFromSourceSpeciesList.add(other);
			break;
		default:
			if(unknownFromSourceSpeciesList == null) {
				unknownFromSourceSpeciesList = new ArrayList<>();
			}
			unknownFromSourceSpeciesList.add(other);
			break;
		}
		return this; 
	 }

	public String getSboTerm() {
		return sboTerm;
	}

	public void setSboTerm(String sboTerm) {
		this.sboTerm = sboTerm;
	}
	 
	 
}
