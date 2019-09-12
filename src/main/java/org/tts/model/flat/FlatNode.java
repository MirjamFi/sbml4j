package org.tts.model.flat;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.ogm.annotation.Relationship;
import org.tts.model.common.ContentGraphNode;

public class FlatNode extends ContentGraphNode {

	@Relationship(type = "FLATEDGE")
	List<FlatEdge> outEdges;
	
	
	private String symbol;

	public List<FlatEdge> getOutEdges() {
		return outEdges;
	}

	public void setOutEdges(List<FlatEdge> outEdges) {
		this.outEdges = outEdges;
	}
	
	public boolean addOutEdge(FlatEdge newEdge) {
		if(this.outEdges == null) {
			this.outEdges = new ArrayList<>();
		}
		return this.outEdges.add(newEdge);
	}
	
	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
}
