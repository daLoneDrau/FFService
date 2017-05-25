package com.osrapi.repositories.ff;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.ff.FFMapLevelEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface FFMapLevelRepository
extends CrudRepository<FFMapLevelEntity, Long> {
	/**
	 * Retrieves a list of map levels by their name.
	 * @param name the name
	 * @return {@link List}<{@link FFMapLevelEntity}>
	 */
	List<FFMapLevelEntity> findByName(String name);
	/**
	 * Retrieves a list of map levels by their elevation.
	 * @param elevation the elevation
	 * @return {@link List}<{@link FFMapLevelEntity}>
	 */
	List<FFMapLevelEntity> findByElevation(Long elevation);
}
