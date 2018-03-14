package com.osrapi.repositories.bp;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.bp.BPAttributeEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface BPAttributeRepository
extends CrudRepository<BPAttributeEntity, Long> {
	/**
	 * Retrieves a list of attributes by their code.
	 * @param code the code
	 * @return {@link List}<{@link BPAttributeEntity}>
	 */
	List<BPAttributeEntity> findByCode(String code);
	/**
	 * Retrieves a list of attributes by their description.
	 * @param description the description
	 * @return {@link List}<{@link BPAttributeEntity}>
	 */
	List<BPAttributeEntity> findByDescription(String description);
	/**
	 * Retrieves a list of attributes by their name.
	 * @param name the name
	 * @return {@link List}<{@link BPAttributeEntity}>
	 */
	List<BPAttributeEntity> findByName(String name);
}
