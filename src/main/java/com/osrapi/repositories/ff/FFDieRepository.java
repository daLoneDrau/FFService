package com.osrapi.repositories.ff;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.ff.FFDieEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface FFDieRepository
extends CrudRepository<FFDieEntity, Long> {
	/**
	 * Retrieves a list of dies by their code.
	 * @param code the code
	 * @return {@link List}<{@link FFDieEntity}>
	 */
	List<FFDieEntity> findByCode(String code);
	/**
	 * Retrieves a list of dies by their value.
	 * @param value the value
	 * @return {@link List}<{@link FFDieEntity}>
	 */
	List<FFDieEntity> findByValue(Long value);
}
