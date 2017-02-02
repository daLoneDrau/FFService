package com.osrapi.repositories.ff;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.ff.FFRoomBkupEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface FFRoomBkupRepository
extends CrudRepository<FFRoomBkupEntity, Long> {
	/**
	 * Retrieves a list of room bkups by their code.
	 * @param code the code
	 * @return {@link List}<{@link FFRoomBkupEntity}>
	 */
	List<FFRoomBkupEntity> findByCode(String code);
}
