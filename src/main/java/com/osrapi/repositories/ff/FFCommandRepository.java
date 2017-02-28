package com.osrapi.repositories.ff;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.ff.FFCommandEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface FFCommandRepository
extends CrudRepository<FFCommandEntity, Long> {
	/**
	 * Retrieves a list of commands by their name.
	 * @param name the name
	 * @return {@link List}<{@link FFCommandEntity}>
	 */
	List<FFCommandEntity> findByName(String name);
	/**
	 * Retrieves a list of commands by their sortOrder.
	 * @param sortOrder the sortOrder
	 * @return {@link List}<{@link FFCommandEntity}>
	 */
	List<FFCommandEntity> findBySortOrder(Long sortOrder);
}
