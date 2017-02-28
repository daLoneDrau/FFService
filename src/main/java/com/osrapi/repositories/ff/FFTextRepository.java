package com.osrapi.repositories.ff;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.ff.FFTextEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface FFTextRepository
extends CrudRepository<FFTextEntity, Long> {
	/**
	 * Retrieves a list of texts by their name.
	 * @param name the name
	 * @return {@link List}<{@link FFTextEntity}>
	 */
	List<FFTextEntity> findByName(String name);
	/**
	 * Retrieves a list of texts by their text.
	 * @param text the text
	 * @return {@link List}<{@link FFTextEntity}>
	 */
	List<FFTextEntity> findByText(String text);
}
