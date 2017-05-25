package com.osrapi.repositories.ff;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.ff.FFMapCellEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface FFMapCellRepository
extends CrudRepository<FFMapCellEntity, Long> {
	/**
	 * Retrieves a list of map cells by their levelName.
	 * @param levelName the levelName
	 * @return {@link List}<{@link FFMapCellEntity}>
	 */
	List<FFMapCellEntity> findByLevelName(String levelName);
	/**
	 * Retrieves a list of map cells by their name.
	 * @param name the name
	 * @return {@link List}<{@link FFMapCellEntity}>
	 */
	List<FFMapCellEntity> findByName(String name);
	/**
	 * Retrieves a list of map cells by their x.
	 * @param x the x
	 * @return {@link List}<{@link FFMapCellEntity}>
	 */
	List<FFMapCellEntity> findByX(Long x);
	/**
	 * Retrieves a list of map cells by their y.
	 * @param y the y
	 * @return {@link List}<{@link FFMapCellEntity}>
	 */
	List<FFMapCellEntity> findByY(Long y);
}
