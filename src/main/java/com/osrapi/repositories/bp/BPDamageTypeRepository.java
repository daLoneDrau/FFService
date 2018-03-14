package com.osrapi.repositories.bp;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.bp.BPDamageTypeEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface BPDamageTypeRepository
extends CrudRepository<BPDamageTypeEntity, Long> {
	/**
	 * Retrieves a list of damage types by their flag.
	 * @param flag the flag
	 * @return {@link List}<{@link BPDamageTypeEntity}>
	 */
	List<BPDamageTypeEntity> findByFlag(Long flag);
	/**
	 * Retrieves a list of damage types by their name.
	 * @param name the name
	 * @return {@link List}<{@link BPDamageTypeEntity}>
	 */
	List<BPDamageTypeEntity> findByName(String name);
}
