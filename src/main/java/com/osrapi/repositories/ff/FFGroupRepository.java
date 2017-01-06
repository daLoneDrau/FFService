package com.osrapi.repositories.ff;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.ff.FFGroupEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface FFGroupRepository
extends CrudRepository<FFGroupEntity, Long> {
	/**
	 * Retrieves a list of groups by their name.
	 * @param name the name
	 * @return {@link List}<{@link FFGroupEntity}>
	 */
	List<FFGroupEntity> findByName(String name);
}
