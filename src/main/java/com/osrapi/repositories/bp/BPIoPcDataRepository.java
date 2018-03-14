package com.osrapi.repositories.bp;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.bp.BPIoPcDataEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface BPIoPcDataRepository
extends CrudRepository<BPIoPcDataEntity, Long> {
	/**
	 * Retrieves a list of io pc datas by their bags.
	 * @param bags the bags
	 * @return {@link List}<{@link BPIoPcDataEntity}>
	 */
	List<BPIoPcDataEntity> findByBags(Long bags);
	/**
	 * Retrieves a list of io pc datas by their gold.
	 * @param gold the gold
	 * @return {@link List}<{@link BPIoPcDataEntity}>
	 */
	List<BPIoPcDataEntity> findByGold(Float gold);
	/**
	 * Retrieves a list of io pc datas by their interfaceFlags.
	 * @param interfaceFlags the interfaceFlags
	 * @return {@link List}<{@link BPIoPcDataEntity}>
	 */
	List<BPIoPcDataEntity> findByInterfaceFlags(Long interfaceFlags);
	/**
	 * Retrieves a list of io pc datas by their level.
	 * @param level the level
	 * @return {@link List}<{@link BPIoPcDataEntity}>
	 */
	List<BPIoPcDataEntity> findByLevel(Long level);
	/**
	 * Retrieves a list of io pc datas by their name.
	 * @param name the name
	 * @return {@link List}<{@link BPIoPcDataEntity}>
	 */
	List<BPIoPcDataEntity> findByName(String name);
	/**
	 * Retrieves a list of io pc datas by their xp.
	 * @param xp the xp
	 * @return {@link List}<{@link BPIoPcDataEntity}>
	 */
	List<BPIoPcDataEntity> findByXp(Long xp);
}
