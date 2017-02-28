package com.osrapi.repositories.ff;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.ff.FFScriptActionEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface FFScriptActionRepository
extends CrudRepository<FFScriptActionEntity, Long> {
	/**
	 * Retrieves a list of script actions by their name.
	 * @param name the name
	 * @return {@link List}<{@link FFScriptActionEntity}>
	 */
	List<FFScriptActionEntity> findByName(String name);
	/**
	 * Retrieves a list of script actions by their type.
	 * @param type the type
	 * @return {@link List}<{@link FFScriptActionEntity}>
	 */
	List<FFScriptActionEntity> findByType(String type);
	/**
	 * Retrieves a list of script actions by their origin.
	 * @param origin the origin
	 * @return {@link List}<{@link FFScriptActionEntity}>
	 */
	List<FFScriptActionEntity> findByOrigin(String origin);
	/**
	 * Retrieves a list of script actions by their direction.
	 * @param direction the direction
	 * @return {@link List}<{@link FFScriptActionEntity}>
	 */
	List<FFScriptActionEntity> findByDirection(String direction);
	/**
	 * Retrieves a list of script actions by their destination.
	 * @param destination the destination
	 * @return {@link List}<{@link FFScriptActionEntity}>
	 */
	List<FFScriptActionEntity> findByDestination(String destination);
	/**
	 * Retrieves a list of script actions by their numDieRolled.
	 * @param numDieRolled the numDieRolled
	 * @return {@link List}<{@link FFScriptActionEntity}>
	 */
	List<FFScriptActionEntity> findByNumDieRolled(Long numDieRolled);
	/**
	 * Retrieves a list of script actions by their isDieRoll.
	 * @param isDieRoll the isDieRoll
	 * @return {@link List}<{@link FFScriptActionEntity}>
	 */
	List<FFScriptActionEntity> findByIsDieRoll(Boolean isDieRoll);
	/**
	 * Retrieves a list of script actions by their attribute.
	 * @param attribute the attribute
	 * @return {@link List}<{@link FFScriptActionEntity}>
	 */
	List<FFScriptActionEntity> findByAttribute(String attribute);
	/**
	 * Retrieves a list of script actions by their amount.
	 * @param amount the amount
	 * @return {@link List}<{@link FFScriptActionEntity}>
	 */
	List<FFScriptActionEntity> findByAmount(Long amount);
	/**
	 * Retrieves a list of script actions by their textName.
	 * @param textName the textName
	 * @return {@link List}<{@link FFScriptActionEntity}>
	 */
	List<FFScriptActionEntity> findByTextName(String textName);
	/**
	 * Retrieves a list of script actions by their roomCode.
	 * @param roomCode the roomCode
	 * @return {@link List}<{@link FFScriptActionEntity}>
	 */
	List<FFScriptActionEntity> findByRoomCode(String roomCode);
	/**
	 * Retrieves a list of script actions by their doorName.
	 * @param doorName the doorName
	 * @return {@link List}<{@link FFScriptActionEntity}>
	 */
	List<FFScriptActionEntity> findByDoorName(String doorName);
	/**
	 * Retrieves a list of script actions by their error.
	 * @param error the error
	 * @return {@link List}<{@link FFScriptActionEntity}>
	 */
	List<FFScriptActionEntity> findByError(Boolean error);
	/**
	 * Retrieves a list of script actions by their mobCode.
	 * @param mobCode the mobCode
	 * @return {@link List}<{@link FFScriptActionEntity}>
	 */
	List<FFScriptActionEntity> findByMobCode(String mobCode);
	/**
	 * Retrieves a list of script actions by their passScripts.
	 * @param passScripts the passScripts
	 * @return {@link List}<{@link FFScriptActionEntity}>
	 */
	List<FFScriptActionEntity> findByPassScripts(String passScripts);
	/**
	 * Retrieves a list of script actions by their failScripts.
	 * @param failScripts the failScripts
	 * @return {@link List}<{@link FFScriptActionEntity}>
	 */
	List<FFScriptActionEntity> findByFailScripts(String failScripts);
}
