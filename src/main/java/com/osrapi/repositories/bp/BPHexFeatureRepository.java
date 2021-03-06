package com.osrapi.repositories.bp;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.bp.BPHexFeatureEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface BPHexFeatureRepository
extends CrudRepository<BPHexFeatureEntity, Long> {
	/**
	 * Retrieves a list of hex features by their flag.
	 * @param flag the flag
	 * @return {@link List}<{@link BPHexFeatureEntity}>
	 */
	List<BPHexFeatureEntity> findByFlag(Long flag);
	/**
	 * Retrieves a list of hex features by their name.
	 * @param name the name
	 * @return {@link List}<{@link BPHexFeatureEntity}>
	 */
	List<BPHexFeatureEntity> findByName(String name);
}
