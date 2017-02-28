package com.osrapi.repositories.ff;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.ff.FFTerrainEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface FFTerrainRepository
extends CrudRepository<FFTerrainEntity, Long> {
	/**
	 * Retrieves a list of terrains by their name.
	 * @param name the name
	 * @return {@link List}<{@link FFTerrainEntity}>
	 */
	List<FFTerrainEntity> findByName(String name);
}
