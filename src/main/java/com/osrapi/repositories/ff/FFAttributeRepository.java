package com.osrapi.repositories.ff;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.ff.FFAttributeEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface FFAttributeRepository
extends CrudRepository<FFAttributeEntity, Long> {
	/**
	 * Retrieves a list of attributes by their code.
	 * @param code the code
	 * @return {@link List}<{@link FFAttributeEntity}>
	 */
	List<FFAttributeEntity> findByCode(String code);
	/**
	 * Retrieves a list of attributes by their description.
	 * @param description the description
	 * @return {@link List}<{@link FFAttributeEntity}>
	 */
	List<FFAttributeEntity> findByDescription(String description);
	/**
	 * Retrieves a list of attributes by their name.
	 * @param name the name
	 * @return {@link List}<{@link FFAttributeEntity}>
	 */
	List<FFAttributeEntity> findByName(String name);
}
