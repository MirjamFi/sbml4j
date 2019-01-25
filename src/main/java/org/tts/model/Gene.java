package org.tts.model;

import java.util.List;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;


@NodeEntity
public class Gene extends KeggItem {
	
	/**
	 * Default Constructor for Spring
	 */
	public Gene() {}

	/**
	 * Constructor for creating new Genes upon reading in the genes file from ftp
	 * @param keggIDString
	 * @param keggType
	 */
	public Gene(String keggIDString, String keggType) {
		super(keggIDString, keggType);
	}

	//  a link to an organism
	@Relationship(type = "IN_ORGANISM", direction = Relationship.OUTGOING)
	private List<Organism> organisms;
	
	// definition string from kegg genes entry
	private String keggDefinition;
	
	// id of the kegg orthology entry
	private String keggOrhologyId;
	
	// Name of the kegg orthology entry
	private String keggOrthologyString;
	
	//the position on the reference genome?
	private String GenomePosition;
	
	// the ncbi gene id as string (not sure if geneIDs from ncbi are always numbers, hence we store it as string)
	private String ncbiGeneId;
	
	// the ncbi protein id as string, if present in source
	private String ncbiProteinID;
	
	// the id for the omim database, if present in source
	private String omimId;
	
	// the id for the hgnc database, if present in source
	private String hgncId;
	
	// the id for the Ensembl database, if present in source
	private String ensembleId;
	
	// the id for the vega database, if present in source
	private String vegaId;
	
	// the id for the pharos database, if present in source
	private String pharosId;

	// the id for the uniprot database, if present in source
	private String uniprotId;
	
	// the id for the miRBase database, if present in source
	private String miRBaseId;
		
	// the pdb_ids for the pdb database, if present in source
	private String pdbId;
	
	// TODO: Decide whether this should be used instead of all the db id fields above
	//@Properties(prefix = "dbId")
	//private Map databaseIdentifier;
	
	
	// the length of the amino-acid sequence
	private int aaSeqLength;
	
	// the amino acid sequence
	private String aaSeq;
	
	// the length of the nucleotide sequence
	private int ntSeqLength;
	
	// the nucleotide sequence
	private String ntSeq;

	public List<Organism> getOrganisms() {
		return organisms;
	}

	public void setOrganisms(List<Organism> organisms) {
		this.organisms = organisms;
	}

	public String getKeggDefinition() {
		return keggDefinition;
	}

	public void setKeggDefinition(String keggDefinition) {
		this.keggDefinition = keggDefinition;
	}

	public String getKeggOrhologyId() {
		return keggOrhologyId;
	}

	public void setKeggOrhologyId(String keggOrhologyId) {
		this.keggOrhologyId = keggOrhologyId;
	}

	public String getKeggOrthologyString() {
		return keggOrthologyString;
	}

	public void setKeggOrthologyString(String keggOrthologyString) {
		this.keggOrthologyString = keggOrthologyString;
	}

	public String getGenomePosition() {
		return GenomePosition;
	}

	public void setGenomePosition(String genomePosition) {
		GenomePosition = genomePosition;
	}

	public String getNcbiGeneId() {
		return ncbiGeneId;
	}

	public void setNcbiGeneId(String ncbiGeneId) {
		this.ncbiGeneId = ncbiGeneId;
	}

	public String getNcbiProteinID() {
		return ncbiProteinID;
	}

	public void setNcbiProteinID(String ncbiProteinID) {
		this.ncbiProteinID = ncbiProteinID;
	}

	public String getOmimId() {
		return omimId;
	}

	public void setOmimId(String omimId) {
		this.omimId = omimId;
	}

	public String getHgncId() {
		return hgncId;
	}

	public void setHgncId(String hgncId) {
		this.hgncId = hgncId;
	}

	public String getEnsembleId() {
		return ensembleId;
	}

	public void setEnsembleId(String ensembleId) {
		this.ensembleId = ensembleId;
	}

	public String getVegaId() {
		return vegaId;
	}

	public void setVegaId(String vegaId) {
		this.vegaId = vegaId;
	}

	public String getPharosId() {
		return pharosId;
	}

	public void setPharosId(String pharosId) {
		this.pharosId = pharosId;
	}

	public String getUniprotId() {
		return uniprotId;
	}

	public void setUniprotId(String uniprotId) {
		this.uniprotId = uniprotId;
	}

	public String getMiRBaseId() {
		return miRBaseId;
	}

	public void setMiRBaseId(String miRBaseId) {
		this.miRBaseId = miRBaseId;
	}

	public String getPdbId() {
		return pdbId;
	}

	public void setPdbId(String pdbId) {
		this.pdbId = pdbId;
	}

	/*public Map getDatabaseIdentifier() {
		return databaseIdentifier;
	}

	public void setDatabaseIdentifier(Map databaseIdentifier) {
		this.databaseIdentifier = databaseIdentifier;
	}*/

	public int getAaSeqLength() {
		return aaSeqLength;
	}

	public void setAaSeqLength(int aaSeqLength) {
		this.aaSeqLength = aaSeqLength;
	}

	public String getAaSeq() {
		return aaSeq;
	}

	public void setAaSeq(String aaSeq) {
		this.aaSeq = aaSeq;
	}

	public int getNtSeqLength() {
		return ntSeqLength;
	}

	public void setNtSeqLength(int ntSeqLength) {
		this.ntSeqLength = ntSeqLength;
	}

	public String getNtSeq() {
		return ntSeq;
	}

	public void setNtSeq(String ntSeq) {
		this.ntSeq = ntSeq;
	}
}
