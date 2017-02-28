package com.osrapi.repositories.ff;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.ff.FFDoorEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface FFDoorRepository
extends CrudRepository<FFDoorEntity, Long> {
	/**
	 * Retrieves a list of doors by their attributeTest.
	 * @param attributeTest the attributeTest
	 * @return {@link List}<{@link FFDoorEntity}>
	 */
	List<FFDoorEntity> findByAttributeTest(String attributeTest);
	/**
	 * Retrieves a list of doors by their direction.
	 * @param direction the direction
	 * @return {@link List}<{@link FFDoorEntity}>
	 */
	List<FFDoorEntity> findByDirection(String direction);
	/**
	 * Retrieves a list of doors by their leadsTo.
	 * @param leadsTo the leadsTo
	 * @return {@link List}<{@link FFDoorEntity}>
	 */
	List<FFDoorEntity> findByLeadsTo(String leadsTo);
	/**
	 * Retrieves a list of doors by their locked.
	 * @param locked the locked
	 * @return {@link List}<{@link FFDoorEntity}>
	 */
	List<FFDoorEntity> findByLocked(Boolean locked);
	/**
	 * Retrieves a list of doors by their name.
	 * @param name the name
	 * @return {@link List}<{@link FFDoorEntity}>
	 */
	List<FFDoorEntity> findByName(String name);
	/**
	 * Retrieves a list of doors by their numDiceRoll.
	 * @param numDiceRoll the numDiceRoll
	 * @return {@link List}<{@link FFDoorEntity}>
	 */
	List<FFDoorEntity> findByNumDiceRoll(Long numDiceRoll);
	/**
	 * Retrieves a list of doors by their title.
	 * @param title the title
	 * @return {@link List}<{@link FFDoorEntity}>
	 */
	List<FFDoorEntity> findByTitle(String title);
}
