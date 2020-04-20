package org.tts.model.api.Output;

import java.util.Iterator;

import org.neo4j.driver.v1.types.Node;
import org.neo4j.driver.v1.types.Path;
import org.neo4j.driver.v1.types.Relationship;
import org.springframework.data.neo4j.annotation.QueryResult;

@QueryResult
public class PathReturnType implements Path {

	public Iterable<Node> pathNodes;
	
	public void setPathNodes(Iterable<Node> pathNodes) {
		this.pathNodes = pathNodes;
	}

	public Iterable<Relationship> pathRelationships;
	
	public void setPathRelationships(Iterable<Relationship> pathRelationships) {
		this.pathRelationships = pathRelationships;
	}

	@Override
	public Iterator<Segment> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node start() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node end() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int length() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean contains(Node node) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(Relationship relationship) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Node> nodes() {
		return this.pathNodes;
	}

	@Override
	public Iterable<Relationship> relationships() {
		return this.pathRelationships;
	}

}
