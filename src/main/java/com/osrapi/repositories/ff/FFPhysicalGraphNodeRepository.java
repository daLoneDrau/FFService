package com.osrapi.repositories.ff;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.ff.FFPhysicalGraphNodeEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface FFPhysicalGraphNodeRepository
extends CrudRepository<FFPhysicalGraphNodeEntity, Long> {
	/**
	 * Retrieves a list of physical graph nodes by their isMainNode.
	 * @param isMainNode the isMainNode
	 * @return {@link List}<{@link FFPhysicalGraphNodeEntity}>
	 */
	List<FFPhysicalGraphNodeEntity> findByIsMainNode(Boolean isMainNode);
	/**
	 * Retrieves a list of physical graph nodes by their roomNumber.
	 * @param roomNumber the roomNumber
	 * @return {@link List}<{@link FFPhysicalGraphNodeEntity}>
	 */
	List<FFPhysicalGraphNodeEntity> findByRoomNumber(Long roomNumber);
	/**
	 * Retrieves a list of physical graph nodes by their x.
	 * @param x the x
	 * @return {@link List}<{@link FFPhysicalGraphNodeEntity}>
	 */
	List<FFPhysicalGraphNodeEntity> findByX(Long x);
	/**
	 * Retrieves a list of physical graph nodes by their y.
	 * @param y the y
	 * @return {@link List}<{@link FFPhysicalGraphNodeEntity}>
	 */
	List<FFPhysicalGraphNodeEntity> findByY(Long y);
}
