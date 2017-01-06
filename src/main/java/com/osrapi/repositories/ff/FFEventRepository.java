package com.osrapi.repositories.ff;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.ff.FFEventEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface FFEventRepository
extends CrudRepository<FFEventEntity, Long> {
	/**
	 * Retrieves a list of events by their code.
	 * @param code the code
	 * @return {@link List}<{@link FFEventEntity}>
	 */
	List<FFEventEntity> findByCode(String code);
}
