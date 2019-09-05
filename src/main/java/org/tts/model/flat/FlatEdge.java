package org.tts.model.flat;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.StartNode;
import org.tts.model.common.ContentGraphEdge;

public class FlatEdge extends ContentGraphEdge {

	@StartNode
	FlatNode startNode;

	@EndNode
	FlatNode endNode;
	
	boolean isDirected;
	
	public FlatNode getStartNode() {
		return startNode;
	}

	public void setStartNode(FlatNode startNode) {
		this.startNode = startNode;
	}

	public FlatNode getEndNode() {
		return endNode;
	}

	public void setEndNode(FlatNode endNode) {
		this.endNode = endNode;
	}
	
	public boolean isDirected() {
		return this.isDirected;
	}
	
	public void setDirected(boolean isDirected) {
		this.isDirected = isDirected;
	}

}
