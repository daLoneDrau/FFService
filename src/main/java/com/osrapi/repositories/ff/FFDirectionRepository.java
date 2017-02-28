package com.osrapi.repositories.ff;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.ff.FFDirectionEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface FFDirectionRepository
extends CrudRepository<FFDirectionEntity, Long> {
	/**
	 * Retrieves a list of directions by their code.
	 * @param code the code
	 * @return {@link List}<{@link FFDirectionEntity}>
	 */
	List<FFDirectionEntity> findByCode(String code);
	/**
	 * Retrieves a list of directions by their value.
	 * @param value the value
	 * @return {@link List}<{@link FFDirectionEntity}>
	 */
	List<FFDirectionEntity> findByValue(Long value);
}
