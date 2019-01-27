package org.tts.model;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;
import org.neo4j.ogm.annotation.Version;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Constraint;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.ext.qual.QualConstants;
import org.sbml.jsbml.ext.qual.QualModelPlugin;
import org.sbml.jsbml.ext.qual.QualitativeSpecies;
import org.sbml.jsbml.ext.qual.Transition;

import com.fasterxml.jackson.annotation.JsonIgnore;

@NodeEntity
public class GraphModel {

	// ID for the Neo4j Database
	
	@Id @GeneratedValue
	private Long id = null;

	@Version
	private Long version;
	
	
	@Transient
	public static final String QUAL_NS = QualConstants.namespaceURI; // TODO: change this to the short handle that does not change on version change
	
	@Transient
	boolean createQual;
	
	private boolean isQualitativeModel;
	
	// Direct Fields of the SBML Model Class
	
	private String 						modelName;
	
	private String 						modelOriginalFileName;
	
	private String						organism;
	
	private int							organismTaxonomyId;
	
	/**
	 * Represents the 'areaUnits' XML attribute of a model element.
	 */
	private String						areaUnitsID;
	/**
	 * Represents the 'conversionFactor' XML attribute of a model element.
	 */
	private String						conversionFactorID;
	/**
	 * Represents the 'extentUnits' XML attribute of a model element.
	 */
	private String						extentUnitsID;
	/**
	 * Represents the 'lengthUnits' XML attribute of a model element.
	 */
	private String 						lengthUnitsID;

	/**
	 * Represents the 'substanceUnits' XML attribute of a model element.
	 */
	private String                        substanceUnitsID;

	/**
	 * Represents the 'timeUnits' XML attribute of a model element.
	 */
	private String                        timeUnitsID;

	/**
	 * Represents the 'volumeUnits' XML attribute of a model element.
	 */
	private String                        volumeUnitsID;
	
	private int numNodes;
	private int numEdges;
	
	// List of all compartments of the model
	
	@Relationship(type = "IN_MODEL", direction = Relationship.INCOMING)
	private List<GraphCompartment>				listCompartment;
	
	// List of all Constraints of the model
	
	@Relationship(type = "IN_MODEL", direction = Relationship.INCOMING)
	private List<GraphConstraint>            listConstraint;
	
	// List of all Species of the model
	
	@Relationship(type = "IN_MODEL", direction = Relationship.INCOMING)
	private List<GraphSpecies>				listSpecies;
	
	// List of all Reactions of the model
	
	@Relationship(type = "IN_MODEL", direction = Relationship.INCOMING)
	private List<GraphReaction>              listReaction;
	
	// List of Relations / Transitions from the qual-Extension
	
	@Relationship(type = "IN_MODEL", direction = Relationship.INCOMING)
	private List<GraphTransition>		 	listTransition;

	// List of Qualitative Species from the qual-Extension
	
	@Relationship(type = "IN_MODEL", direction = Relationship.INCOMING)
	private List<GraphQualitativeSpecies> listQualSpecies;
	
	
	
	public GraphModel() {}


	// TODO: I want to store the filename of the file, I need some provenance information (Issue # 1)
	public GraphModel(Model model, String fileName, String organism, boolean createQual) {
	
		this.createQual = createQual;
		// Set model fields
		setModelName(model.getName());
		// TODO: Check if model exists - not doing it here, it is being done in the service
		
		setModelOriginalFileName(fileName);
		setOrganism(organism);
		
		// at the moment, we only load homo sapiens
		// we can thus simply set organismTaxonomyId to 9606 (https://www.ncbi.nlm.nih.gov/Taxonomy/Browser/wwwtax.cgi?mode=Info&id=9606)
		// when we want to load more organisms, we need to query the service to get the appropriate id (not sure how and which service to query at this point)
		if (organism.equals("hsa") || organism.equals("Homo sapiens")) {
			setOrganismTaxonomyId(9606);
		}
		
		
		setAreaUnitsID(model.getAreaUnits()); // TODO: If Level == 2 this will hold UnitDefinition.AREA, need to account for that if we want to support SBML Level2
		setConversionFactorID(model.getConversionFactor());
		setExtentUnitsID(model.getExtentUnits());
		setLengthUnitsID(model.getLengthUnits());
		setSubstanceUnitsID(model.getSubstanceUnits());
		setTimeUnitsID(model.getTimeUnits());
		setVolumeUnitsID(model.getVolumeUnits());
		
		// populate the lists of the GraphModel
		// if those items already exist, update them with new properties (part of new model, additional cvterms?)
		listCompartment = createCompartmentList(model.getListOfCompartments());
		listConstraint = createConstraintList(model.getListOfConstraints());
		listSpecies = createSpeciesList(model.getListOfSpecies());
		setNumNodes(listSpecies.size()); // might be able to use model.getNumSpecies here, do not know if it is filled in JSBML
		listReaction = createReactionList(model.getListOfReactions());
		setNumEdges(listReaction.size()); // might be able to use model.getNumReaction here, do not know if it is filled in JSBML
		
		/**
		 * Start of Extension 'Qualitative Model'
		 */
		if(createQual) {
			listQualSpecies = createQualSpeciesList(((QualModelPlugin) model.getExtension(QUAL_NS)).getListOfQualitativeSpecies());
			List<GraphQualitativeSpecies> tmpListGS = this.getListQualSpecies();
			//System.out.println("Number of qualSpec " + tmpListGS.size());
			boolean qual = false;	
			if ( this.getListQualSpecies().size() > 0) qual = true;
			if(qual) {
				connectQualSpecies();
			}
			setNumNodes(listQualSpecies.size());
			listTransition = createTransitionList(((QualModelPlugin) model.getExtension(QUAL_NS)).getListOfTransitions());
			setNumEdges(listTransition.size());
			setQualitativeModel(true);

		} else {
			setQualitativeModel(false);
		}
			
			/*for(GraphCompartment compartment : listCompartment) {
				System.out.println("Compartment " + compartment.getSbmlIdString() + " has " + compartment.getSpeciesInThisCompartment().size() + " species");
			}*/
			
		/**
		 * not sure if I need to updateCompartments and ReactantsAndProducts
		 * as I reversed some of the relationships, and moved some stuff into GraphSBase
		 */

		// 1. compartment
		updateCompartments();
		// 2. GraphSpecies
		//updateReactantsAndProducts();
		

		
	}
	

	private void connectQualSpecies() {
		for (GraphQualitativeSpecies qualSpec : listQualSpecies) {
			for (GraphSpecies spec : listSpecies) {
				if (qualSpec.getSbmlNameString().equals(spec.getSbmlNameString())) {
					//System.out.println("Found matching species");
					qualSpec.setSpecies(spec); 
				}
			}
		}
		
	}

	/*
	private void updateReactantsAndProducts() {
		for (GraphReaction reaction : listReaction) {
			for (GraphSpecies species: reaction.getReactants()) {
				species.setGraphReactionReactant(reaction);
			}
			for (GraphSpecies species : reaction.getProducts()) {
				species.setGraphReactionProduct(reaction);
			}
		}
	}
*/
	private void updateCompartments() {
		for (GraphCompartment compartment : listCompartment) {
			for(GraphSpecies species : listSpecies) {
				if(compartment.getSbmlIdString().equals(species.getSbmlCompartmentString())) {
					compartment.addToContainedSpecies(species);
				}
			}
			
		}
		
	}

	// Create a List of Compartments that can be persisted in the Graph Database
	private List<GraphCompartment> createCompartmentList(ListOf<Compartment> listOfCompartments) {
		List<GraphCompartment> theList = new ArrayList<GraphCompartment>();
		for (Compartment compartment : listOfCompartments) {
			theList.add(new GraphCompartment(compartment, this));
		}
		return theList;
	}
	
	// Create a List of Constraints that can be persisted in the Graph Database
	private List<GraphConstraint> createConstraintList(ListOf<Constraint> listOfConstraints) {
		List<GraphConstraint> theList = new ArrayList<GraphConstraint>();
		for (Constraint constraint : listOfConstraints) {
			theList.add(new GraphConstraint(constraint));
		}
		return theList;
	}
	
	// Create a List of Species that can be persisted in the Graph Database
	private List<GraphSpecies> createSpeciesList(ListOf<Species> listOfSpecies) {
		List<GraphSpecies> theList = new ArrayList<GraphSpecies>();
		for (Species species : listOfSpecies) {
			for (int i = 0; i != listCompartment.size(); i++) {
				if (listCompartment.get(i).getSbmlIdString().equals(species.getCompartment())) {
					// does the species already exist?
					
					theList.add(new GraphSpecies(species, listCompartment.get(i), this));
				}
			}
		}
		return theList;
	}
	
	// Create a List of Qualitative Species that can be persisted in the Graph Database
	// They should be identical to the regular Species but with "qual_" prefixed values (do we need both?)
	private List<GraphQualitativeSpecies> createQualSpeciesList(ListOf<QualitativeSpecies> listOfQualitativeSpecies) {
		List<GraphQualitativeSpecies> theList = new ArrayList<GraphQualitativeSpecies>();
		//System.out.println("Compartment is " + listCompartment.get(0).getSbmlIdString());
		for (QualitativeSpecies qualSpecies : listOfQualitativeSpecies) {
			
			for (int i = 0; i != listCompartment.size(); i++) {
				//System.out.println("QualSpecies has compartment: " +  qualSpecies.getCompartment());
				if (listCompartment.get(i).getSbmlIdString().equals(qualSpecies.getCompartment())) {
					//System.out.println("Yes Compartments are identical");
					theList.add(new GraphQualitativeSpecies(qualSpecies, listCompartment.get(i), this));
				}
			}
			
		}
		//System.out.println("Created Qualtitative Species");
		return theList;
	}
	
	
	// Create a List of Reactions that can be persisted in the Graph Database
	private List<GraphReaction> createReactionList(ListOf<Reaction> listOfReactions) {
		List<GraphReaction> theList = new ArrayList<GraphReaction>();
		for (Reaction reaction : listOfReactions) {
			if(reaction.getLevel() < 3 && listCompartment.size() == 1) {
				theList.add(new GraphReaction(reaction, listCompartment.get(0), listSpecies, this));
			} else {
				for (int i = 0; i != listCompartment.size(); i++) {
					if (listCompartment.get(i).getSbmlIdString().equals(reaction.getCompartment())) {
						theList.add(new GraphReaction(reaction, listCompartment.get(i), listSpecies, this));
					}
				}
			}
		}
		return theList;
	}
	
	// Create a List of Transitions that can be persisted in the Graph Database
	// TODO: Still need to look at possibly multiple Outputs as well.
	// When KEGGtranslator, specifically the KEGG2SBMLqual Module creates these Transitions
	// only one one Input and one Output get generated.
	// For now (as this is our primary DataSource at the moment) we will expect it to be that way,
	// and look at this case later on again
	private List<GraphTransition> createTransitionList(ListOf<Transition> listOfTransitions) {
		List<GraphTransition> theList = new ArrayList<GraphTransition>();
		for (Transition transition : listOfTransitions) {
			//System.out.println("Transition: " + transition.getName() + " has " + transition.getInputCount() + " inputs (first one: " + transition.getListOfInputs().get(0).getQualitativeSpeciesInstance().getName() + ") and " + transition.getOutputCount() + " outputs (first one: " + transition.getListOfOutputs().get(0).getQualitativeSpeciesInstance().getName() + ")");
			
			if(transition.getInputCount() < 1 || transition.getOutputCount() < 1) {
				System.out.println("WARNING: Transition " + transition.getName()+ " has insufficient Inputs or Outputs!");
			} else {
				// We do have at least one Input and one Output
				// We do use the first one of each at this point
				/**
				 * TODO: Reevaluate the possibility of multiple Inputs/Outputs
				 * TODO: the 4th paramter of the Consstructor is the Index of the function Term; In qualitative SBML Models
				 * 			this term is not set. The function term does not get evaluated at this point.
				 * 
				 */
				//System.out.println("Adding Transition");
				theList.add(new GraphTransition(transition, 0, 0, 0, listQualSpecies, this));
			}
			/** 
			if(transition.getInputCount() > 0 ) {
				// this should always be true ?!
				if(transition.getInputCount() > 1) {
					// multiple Inputs found, need to build multiple Transitions (as one Transition
					// in the Neo4j Database can only have one start and one end node)
					// what about the output count? how many transitions do I build? Is this even possible?
					//for(int i = 0; i != transition.getInputCount(); i++) {
					//	theList.add(new GraphTransition(transition, i));
					//}
					System.out.println("WARNING: Multiple Inputs in Transition: " + transition.getName());
					if(transition.getOutputCount() > 0) {
						// again this should always be true
						if(transition.getOutputCount() > 1) {
							System.out.println("WARNING: Multiple Outputs in Transition: " + transition.getName());
							
						}
					}
					theList.add(new GraphTransition(transition, 0));
				} else {
					// only one Input found
					theList.add(new GraphTransition(transition));
				}
			}
			*/
			
		}
		//System.out.println("Added " + theList.size() + " transitions");
		return theList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getModelName() {
		return modelName;
	}

	public String getModelOriginalFileName() {
		return modelOriginalFileName;
	}


	public void setModelOriginalFileName(String modelOriginalFileName) {
		this.modelOriginalFileName = modelOriginalFileName;
	}


	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getAreaUnitsID() {
		return areaUnitsID;
	}

	public void setAreaUnitsID(String areaUnitsID) {
		this.areaUnitsID = areaUnitsID;
	}

	public String getConversionFactorID() {
		return conversionFactorID;
	}

	public void setConversionFactorID(String conversionFactorID) {
		this.conversionFactorID = conversionFactorID;
	}

	public String getExtentUnitsID() {
		return extentUnitsID;
	}

	public void setExtentUnitsID(String extentUnitsID) {
		this.extentUnitsID = extentUnitsID;
	}

	public String getLengthUnitsID() {
		return lengthUnitsID;
	}

	public void setLengthUnitsID(String lengthUnitsID) {
		this.lengthUnitsID = lengthUnitsID;
	}

	public String getSubstanceUnitsID() {
		return substanceUnitsID;
	}

	public void setSubstanceUnitsID(String substanceUnitsID) {
		this.substanceUnitsID = substanceUnitsID;
	}

	public String getTimeUnitsID() {
		return timeUnitsID;
	}

	public void setTimeUnitsID(String timeUnitsID) {
		this.timeUnitsID = timeUnitsID;
	}

	public String getVolumeUnitsID() {
		return volumeUnitsID;
	}

	public void setVolumeUnitsID(String volumeUnitsID) {
		this.volumeUnitsID = volumeUnitsID;
	}
	
	public List<GraphCompartment> getListCompartment() {
		return listCompartment;
	}

	public void setListCompartment(List<GraphCompartment> listCompartment) {
		this.listCompartment = listCompartment;
	}
	
	public List<GraphConstraint> getListConstraint() {
		return listConstraint;
	}

	public void setListConstraint(List<GraphConstraint> listConstraint) {
		this.listConstraint = listConstraint;
	}
	
	public List<GraphSpecies> getListSpecies() {
		return listSpecies;
	}

	public void setListSpecies(List<GraphSpecies> listSpecies) {
		this.listSpecies = listSpecies;
	}
	
	public List<GraphReaction> getListReaction() {
		return listReaction;
	}

	public void setListReaction(List<GraphReaction> listReaction) {
		this.listReaction = listReaction;
	}
	
	public List<GraphTransition> getListTransition() {
		return listTransition;
	}

	public void setListTransition(List<GraphTransition> listTransition) {
		this.listTransition = listTransition;
	}
	
	public List<GraphQualitativeSpecies> getListQualSpecies() {
		return listQualSpecies;
	}

	public void setListQualSpecies(List<GraphQualitativeSpecies> listQualSpecies) {
		this.listQualSpecies = listQualSpecies;
	}


	public int getNumNodes() {
		return numNodes;
	}


	public void setNumNodes(int numNodes) {
		this.numNodes = numNodes;
	}


	public int getNumEdges() {
		return numEdges;
	}


	public void setNumEdges(int numEdges) {
		this.numEdges = numEdges;
	}


	public boolean isQualitativeModel() {
		return isQualitativeModel;
	}


	public void setQualitativeModel(boolean isQualitativeModel) {
		this.isQualitativeModel = isQualitativeModel;
	}


	public String getOrganism() {
		return organism;
	}


	public void setOrganism(String organism) {
		this.organism = organism;
	}


	public int getOrganismTaxonomyId() {
		return organismTaxonomyId;
	}


	public void setOrganismTaxonomyId(int organismTaxonomyId) {
		this.organismTaxonomyId = organismTaxonomyId;
	}

	

}
