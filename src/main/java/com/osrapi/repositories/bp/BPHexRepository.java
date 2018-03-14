package com.osrapi.repositories.bp;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.bp.BPHexEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface BPHexRepository
extends CrudRepository<BPHexEntity, Long> {
	/**
	 * Retrieves a list of hexs by their x.
	 * @param x the x
	 * @return {@link List}<{@link BPHexEntity}>
	 */
	List<BPHexEntity> findByX(Long x);
	/**
	 * Retrieves a list of hexs by their y.
	 * @param y the y
	 * @return {@link List}<{@link BPHexEntity}>
	 */
	List<BPHexEntity> findByY(Long y);
	/**
	 * Retrieves a list of hexs by their name.
	 * @param name the name
	 * @return {@link List}<{@link BPHexEntity}>
	 */
	List<BPHexEntity> findByName(String name);
}
