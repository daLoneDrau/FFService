package com.osrapi.repositories.bp;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.bp.BPHexTypeEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface BPHexTypeRepository
extends CrudRepository<BPHexTypeEntity, Long> {
	/**
	 * Retrieves a list of hex types by their type.
	 * @param type the type
	 * @return {@link List}<{@link BPHexTypeEntity}>
	 */
	List<BPHexTypeEntity> findByType(Long type);
	/**
	 * Retrieves a list of hex types by their name.
	 * @param name the name
	 * @return {@link List}<{@link BPHexTypeEntity}>
	 */
	List<BPHexTypeEntity> findByName(String name);
}
