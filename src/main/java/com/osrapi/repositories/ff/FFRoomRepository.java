package com.osrapi.repositories.ff;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.ff.FFRoomEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface FFRoomRepository
extends CrudRepository<FFRoomEntity, Long> {
	/**
	 * Retrieves a list of rooms by their code.
	 * @param code the code
	 * @return {@link List}<{@link FFRoomEntity}>
	 */
	List<FFRoomEntity> findByCode(String code);
}
