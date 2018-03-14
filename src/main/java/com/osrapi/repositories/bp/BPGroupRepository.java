package com.osrapi.repositories.bp;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.bp.BPGroupEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface BPGroupRepository
extends CrudRepository<BPGroupEntity, Long> {
	/**
	 * Retrieves a list of groups by their name.
	 * @param name the name
	 * @return {@link List}<{@link BPGroupEntity}>
	 */
	List<BPGroupEntity> findByName(String name);
}
