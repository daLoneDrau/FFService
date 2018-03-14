package com.osrapi.repositories.bp;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.bp.BPObjectTypeEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface BPObjectTypeRepository
extends CrudRepository<BPObjectTypeEntity, Long> {
	/**
	 * Retrieves a list of object types by their code.
	 * @param code the code
	 * @return {@link List}<{@link BPObjectTypeEntity}>
	 */
	List<BPObjectTypeEntity> findByCode(String code);
	/**
	 * Retrieves a list of object types by their flag.
	 * @param flag the flag
	 * @return {@link List}<{@link BPObjectTypeEntity}>
	 */
	List<BPObjectTypeEntity> findByFlag(Long flag);
}
