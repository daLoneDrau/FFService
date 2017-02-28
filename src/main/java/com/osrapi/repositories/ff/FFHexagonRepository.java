package com.osrapi.repositories.ff;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.ff.FFHexagonEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface FFHexagonRepository
extends CrudRepository<FFHexagonEntity, Long> {
	/**
	 * Retrieves a list of hexagons by their flat.
	 * @param flat the flat
	 * @return {@link List}<{@link FFHexagonEntity}>
	 */
	List<FFHexagonEntity> findByFlat(Boolean flat);
	/**
	 * Retrieves a list of hexagons by their height.
	 * @param height the height
	 * @return {@link List}<{@link FFHexagonEntity}>
	 */
	List<FFHexagonEntity> findByHeight(Float height);
	/**
	 * Retrieves a list of hexagons by their horizontalDistance.
	 * @param horizontalDistance the horizontalDistance
	 * @return {@link List}<{@link FFHexagonEntity}>
	 */
	List<FFHexagonEntity> findByHorizontalDistance(Float horizontalDistance);
	/**
	 * Retrieves a list of hexagons by their size.
	 * @param size the size
	 * @return {@link List}<{@link FFHexagonEntity}>
	 */
	List<FFHexagonEntity> findBySize(Float size);
	/**
	 * Retrieves a list of hexagons by their verticalDistance.
	 * @param verticalDistance the verticalDistance
	 * @return {@link List}<{@link FFHexagonEntity}>
	 */
	List<FFHexagonEntity> findByVerticalDistance(Float verticalDistance);
	/**
	 * Retrieves a list of hexagons by their width.
	 * @param width the width
	 * @return {@link List}<{@link FFHexagonEntity}>
	 */
	List<FFHexagonEntity> findByWidth(Float width);
	/**
	 * Retrieves a list of hexagons by their x.
	 * @param x the x
	 * @return {@link List}<{@link FFHexagonEntity}>
	 */
	List<FFHexagonEntity> findByX(Long x);
	/**
	 * Retrieves a list of hexagons by their y.
	 * @param y the y
	 * @return {@link List}<{@link FFHexagonEntity}>
	 */
	List<FFHexagonEntity> findByY(Long y);
	/**
	 * Retrieves a list of hexagons by their z.
	 * @param z the z
	 * @return {@link List}<{@link FFHexagonEntity}>
	 */
	List<FFHexagonEntity> findByZ(Long z);
}
