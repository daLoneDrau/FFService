package com.osrapi.repositories.ff;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.ff.FFIoNpcDataEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface FFIoNpcDataRepository
extends CrudRepository<FFIoNpcDataEntity, Long> {
	/**
	 * Retrieves a list of io npc datas by their behavior.
	 * @param behavior the behavior
	 * @return {@link List}<{@link FFIoNpcDataEntity}>
	 */
	List<FFIoNpcDataEntity> findByBehavior(Long behavior);
	/**
	 * Retrieves a list of io npc datas by their behaviorParam.
	 * @param behaviorParam the behaviorParam
	 * @return {@link List}<{@link FFIoNpcDataEntity}>
	 */
	List<FFIoNpcDataEntity> findByBehaviorParam(Float behaviorParam);
	/**
	 * Retrieves a list of io npc datas by their climbCount.
	 * @param climbCount the climbCount
	 * @return {@link List}<{@link FFIoNpcDataEntity}>
	 */
	List<FFIoNpcDataEntity> findByClimbCount(Float climbCount);
	/**
	 * Retrieves a list of io npc datas by their collidState.
	 * @param collidState the collidState
	 * @return {@link List}<{@link FFIoNpcDataEntity}>
	 */
	List<FFIoNpcDataEntity> findByCollidState(Long collidState);
	/**
	 * Retrieves a list of io npc datas by their collidTime.
	 * @param collidTime the collidTime
	 * @return {@link List}<{@link FFIoNpcDataEntity}>
	 */
	List<FFIoNpcDataEntity> findByCollidTime(Long collidTime);
	/**
	 * Retrieves a list of io npc datas by their critical.
	 * @param critical the critical
	 * @return {@link List}<{@link FFIoNpcDataEntity}>
	 */
	List<FFIoNpcDataEntity> findByCritical(Float critical);
	/**
	 * Retrieves a list of io npc datas by their cut.
	 * @param cut the cut
	 * @return {@link List}<{@link FFIoNpcDataEntity}>
	 */
	List<FFIoNpcDataEntity> findByCut(Boolean cut);
	/**
	 * Retrieves a list of io npc datas by their cuts.
	 * @param cuts the cuts
	 * @return {@link List}<{@link FFIoNpcDataEntity}>
	 */
	List<FFIoNpcDataEntity> findByCuts(Long cuts);
	/**
	 * Retrieves a list of io npc datas by their damages.
	 * @param damages the damages
	 * @return {@link List}<{@link FFIoNpcDataEntity}>
	 */
	List<FFIoNpcDataEntity> findByDamages(Float damages);
	/**
	 * Retrieves a list of io npc datas by their internalScript.
	 * @param internalScript the internalScript
	 * @return {@link List}<{@link FFIoNpcDataEntity}>
	 */
	List<FFIoNpcDataEntity> findByInternalScript(String internalScript);
	/**
	 * Retrieves a list of io npc datas by their life.
	 * @param life the life
	 * @return {@link List}<{@link FFIoNpcDataEntity}>
	 */
	List<FFIoNpcDataEntity> findByLife(Float life);
	/**
	 * Retrieves a list of io npc datas by their mana.
	 * @param mana the mana
	 * @return {@link List}<{@link FFIoNpcDataEntity}>
	 */
	List<FFIoNpcDataEntity> findByMana(Float mana);
	/**
	 * Retrieves a list of io npc datas by their maxlife.
	 * @param maxlife the maxlife
	 * @return {@link List}<{@link FFIoNpcDataEntity}>
	 */
	List<FFIoNpcDataEntity> findByMaxlife(Float maxlife);
	/**
	 * Retrieves a list of io npc datas by their maxmana.
	 * @param maxmana the maxmana
	 * @return {@link List}<{@link FFIoNpcDataEntity}>
	 */
	List<FFIoNpcDataEntity> findByMaxmana(Float maxmana);
	/**
	 * Retrieves a list of io npc datas by their name.
	 * @param name the name
	 * @return {@link List}<{@link FFIoNpcDataEntity}>
	 */
	List<FFIoNpcDataEntity> findByName(String name);
	/**
	 * Retrieves a list of io npc datas by their npcFlags.
	 * @param npcFlags the npcFlags
	 * @return {@link List}<{@link FFIoNpcDataEntity}>
	 */
	List<FFIoNpcDataEntity> findByNpcFlags(Long npcFlags);
	/**
	 * Retrieves a list of io npc datas by their title.
	 * @param title the title
	 * @return {@link List}<{@link FFIoNpcDataEntity}>
	 */
	List<FFIoNpcDataEntity> findByTitle(String title);
	/**
	 * Retrieves a list of io npc datas by their weapon.
	 * @param weapon the weapon
	 * @return {@link List}<{@link FFIoNpcDataEntity}>
	 */
	List<FFIoNpcDataEntity> findByWeapon(String weapon);
	/**
	 * Retrieves a list of io npc datas by their xpvalue.
	 * @param xpvalue the xpvalue
	 * @return {@link List}<{@link FFIoNpcDataEntity}>
	 */
	List<FFIoNpcDataEntity> findByXpvalue(Long xpvalue);
}
