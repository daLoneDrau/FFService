package com.osrapi.repositories.ff;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.ff.FFIoPcDataEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface FFIoPcDataRepository
extends CrudRepository<FFIoPcDataEntity, Long> {
	/**
	 * Retrieves a list of io pc datas by their bags.
	 * @param bags the bags
	 * @return {@link List}<{@link FFIoPcDataEntity}>
	 */
	List<FFIoPcDataEntity> findByBags(Long bags);
	/**
	 * Retrieves a list of io pc datas by their gold.
	 * @param gold the gold
	 * @return {@link List}<{@link FFIoPcDataEntity}>
	 */
	List<FFIoPcDataEntity> findByGold(Float gold);
	/**
	 * Retrieves a list of io pc datas by their interfaceFlags.
	 * @param interfaceFlags the interfaceFlags
	 * @return {@link List}<{@link FFIoPcDataEntity}>
	 */
	List<FFIoPcDataEntity> findByInterfaceFlags(Long interfaceFlags);
	/**
	 * Retrieves a list of io pc datas by their level.
	 * @param level the level
	 * @return {@link List}<{@link FFIoPcDataEntity}>
	 */
	List<FFIoPcDataEntity> findByLevel(Long level);
	/**
	 * Retrieves a list of io pc datas by their name.
	 * @param name the name
	 * @return {@link List}<{@link FFIoPcDataEntity}>
	 */
	List<FFIoPcDataEntity> findByName(String name);
	/**
	 * Retrieves a list of io pc datas by their xp.
	 * @param xp the xp
	 * @return {@link List}<{@link FFIoPcDataEntity}>
	 */
	List<FFIoPcDataEntity> findByXp(Long xp);
}
