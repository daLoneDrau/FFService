package com.osrapi.repositories.ff;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.ff.FFObjectTypeEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface FFObjectTypeRepository
extends CrudRepository<FFObjectTypeEntity, Long> {
	/**
	 * Retrieves a list of object types by their code.
	 * @param code the code
	 * @return {@link List}<{@link FFObjectTypeEntity}>
	 */
	List<FFObjectTypeEntity> findByCode(String code);
	/**
	 * Retrieves a list of object types by their flag.
	 * @param flag the flag
	 * @return {@link List}<{@link FFObjectTypeEntity}>
	 */
	List<FFObjectTypeEntity> findByFlag(Long flag);
}
