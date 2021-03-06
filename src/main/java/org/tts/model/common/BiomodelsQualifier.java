package org.tts.model.common;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import org.sbml.jsbml.CVTerm;

import com.fasterxml.jackson.annotation.JsonIgnore;

@RelationshipEntity(type = "BQ")
public class BiomodelsQualifier extends ContentGraphEdge {

	private CVTerm.Type type;
	
	private CVTerm.Qualifier qualifier;

	@StartNode
	private SBMLSBaseEntity startNode;
	
	@EndNode
	private ExternalResourceEntity endNode;
	
	@JsonIgnore
	public SBMLSBaseEntity getStartNode() {
		return startNode;
	}

	public void setStartNode(SBMLSBaseEntity startNode) {
		this.startNode = startNode;
	}

	public ExternalResourceEntity getEndNode() {
		return endNode;
	}

	public void setEndNode(ExternalResourceEntity endNode) {
		this.endNode = endNode;
	}

	public CVTerm.Type getType() {
		return type;
	}

	public void setType(CVTerm.Type type) {
		this.type = type;
	}

	public CVTerm.Qualifier getQualifier() {
		return qualifier;
	}

	public void setQualifier(CVTerm.Qualifier qualifier) {
		this.qualifier = qualifier;
	}
}
