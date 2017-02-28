package com.osrapi.repositories.ff;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.ff.FFDamageTypeEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface FFDamageTypeRepository
extends CrudRepository<FFDamageTypeEntity, Long> {
	/**
	 * Retrieves a list of damage types by their flag.
	 * @param flag the flag
	 * @return {@link List}<{@link FFDamageTypeEntity}>
	 */
	List<FFDamageTypeEntity> findByFlag(Long flag);
	/**
	 * Retrieves a list of damage types by their name.
	 * @param name the name
	 * @return {@link List}<{@link FFDamageTypeEntity}>
	 */
	List<FFDamageTypeEntity> findByName(String name);
}
