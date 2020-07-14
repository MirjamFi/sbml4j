package org.tts.repository.common;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import org.tts.model.common.SBMLSpecies;

@Repository
public interface SBMLSpeciesRepository extends Neo4jRepository<SBMLSpecies, Long> {
	
	public SBMLSpecies findBysBaseName(String sBaseName);

	public SBMLSpecies findBysBaseId(String sBaseId);
	
	@Query(value = "MATCH (t:SBMLSpecies) RETURN DISTINCT t.sBaseSboTerm;")
	public Iterable<String> getNodeTypes();

	@Query("MATCH "
			+ "(s:SBMLSpecies)"
			+ "-[b:BQ]->"
			+ "(e:ExternalResourceEntity) "
			+ "WHERE b.type = \"BIOLOGICAL_QUALIFIER\" "
			+ "AND b.qualifier IN [\"BQB_HAS_VERSION\", \"BQB_IS\", \"BQB_IS_ENCODED_BY\"] "
			+ "AND e.name = $name "
			+ "AND e.databaseFromUri = $databaseFromUri "
			+ "RETURN s")
	public Iterable<SBMLSpecies> findByBQConnectionTo(String name, String databaseFromUri);

	@Query("MATCH "
			+ "(g:SBMLSpeciesGroup)"
			+ "-[h:HAS_GROUP_MEMBER]->"
			+ "(s:SBMLSpecies) "
			+ "WHERE g.entityUUID = $groupEntityUUID "
			+ "RETURN s")
	public Iterable<SBMLSpecies> getSBMLSpeciesOfGroup(String groupEntityUUID);
	
	/*@Query("MATCH "
			+ "(s:SBMLSpecies)"
			+ "-[w:Warehouse]-"
			+ "(p:PathwayNode) "
			+ "WHERE p.entityUUID = $pathwayEntityUUID "
			+ "AND NOT EXISTS {"
			+ " MATCH (s)-[c]-(e:GraphBaseEntity) "
			+ "WHERE type(c) <> warehouseGraphEdge"
			+ "")
	public Iterable<SBMLSpecies> getAllUnconnectedSpeciesOfPathway(String pathwayEntityUUID);
	*/
}
