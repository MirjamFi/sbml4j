package org.tts.model;

import java.util.UUID;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Version;

@NodeEntity
public class KeggItem {

	public KeggItem() {
		// default constructor for Spring to instantiate as been
	}
	
	

	public KeggItem(String keggIDString, String keggType) {
		this.keggIDString = keggIDString;
		this.keggType = keggType;
		this.setKegg4jId(UUID.randomUUID().toString());
	}



	@Id @GeneratedValue
	private Long id = null; // why initialize with null?

	@Version
	private Long version;

	// A unique Identifier that is persistent even if Neo4j reuses an id
	private String kegg4jId;

	// the KEGG identifierString
	private String keggIDString;

	// the kegg type String (CDS, miRNA, rRNA)
	private String keggType;

	// the primary name from KEGG
	private String keggNamePrimary;

	// all secondary names as one comma separated string
	private String keggNamesSecondary;

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

	public String getKegg4jId() {
		return kegg4jId;
	}

	public void setKegg4jId(String kegg4jId) {
		this.kegg4jId = kegg4jId;
	}

	public String getKeggIDString() {
		return keggIDString;
	}

	public void setKeggIDString(String keggIDString) {
		this.keggIDString = keggIDString;
	}

	public String getKeggType() {
		return keggType;
	}

	public void setKeggType(String keggType) {
		this.keggType = keggType;
	}

	public String getKeggNamePrimary() {
		return keggNamePrimary;
	}

	public void setKeggNamePrimary(String keggNamePrimary) {
		this.keggNamePrimary = keggNamePrimary;
	}

	public String getKeggNamesSecondary() {
		return keggNamesSecondary;
	}

	public void setKeggNamesSecondary(String keggNamesSecondary) {
		this.keggNamesSecondary = keggNamesSecondary;
	}

}
