package com.osrapi.repositories.bp;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.bp.BPIoItemDataEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface BPIoItemDataRepository
extends CrudRepository<BPIoItemDataEntity, Long> {
	/**
	 * Retrieves a list of io item datas by their count.
	 * @param count the count
	 * @return {@link List}<{@link BPIoItemDataEntity}>
	 */
	List<BPIoItemDataEntity> findByCount(Long count);
	/**
	 * Retrieves a list of io item datas by their description.
	 * @param description the description
	 * @return {@link List}<{@link BPIoItemDataEntity}>
	 */
	List<BPIoItemDataEntity> findByDescription(String description);
	/**
	 * Retrieves a list of io item datas by their foodValue.
	 * @param foodValue the foodValue
	 * @return {@link List}<{@link BPIoItemDataEntity}>
	 */
	List<BPIoItemDataEntity> findByFoodValue(Long foodValue);
	/**
	 * Retrieves a list of io item datas by their internalScript.
	 * @param internalScript the internalScript
	 * @return {@link List}<{@link BPIoItemDataEntity}>
	 */
	List<BPIoItemDataEntity> findByInternalScript(String internalScript);
	/**
	 * Retrieves a list of io item datas by their leftRing.
	 * @param leftRing the leftRing
	 * @return {@link List}<{@link BPIoItemDataEntity}>
	 */
	List<BPIoItemDataEntity> findByLeftRing(Boolean leftRing);
	/**
	 * Retrieves a list of io item datas by their lightValue.
	 * @param lightValue the lightValue
	 * @return {@link List}<{@link BPIoItemDataEntity}>
	 */
	List<BPIoItemDataEntity> findByLightValue(Long lightValue);
	/**
	 * Retrieves a list of io item datas by their maxOwned.
	 * @param maxOwned the maxOwned
	 * @return {@link List}<{@link BPIoItemDataEntity}>
	 */
	List<BPIoItemDataEntity> findByMaxOwned(Long maxOwned);
	/**
	 * Retrieves a list of io item datas by their name.
	 * @param name the name
	 * @return {@link List}<{@link BPIoItemDataEntity}>
	 */
	List<BPIoItemDataEntity> findByName(String name);
	/**
	 * Retrieves a list of io item datas by their price.
	 * @param price the price
	 * @return {@link List}<{@link BPIoItemDataEntity}>
	 */
	List<BPIoItemDataEntity> findByPrice(Float price);
	/**
	 * Retrieves a list of io item datas by their ringType.
	 * @param ringType the ringType
	 * @return {@link List}<{@link BPIoItemDataEntity}>
	 */
	List<BPIoItemDataEntity> findByRingType(Long ringType);
	/**
	 * Retrieves a list of io item datas by their stackSize.
	 * @param stackSize the stackSize
	 * @return {@link List}<{@link BPIoItemDataEntity}>
	 */
	List<BPIoItemDataEntity> findByStackSize(Long stackSize);
	/**
	 * Retrieves a list of io item datas by their stealValue.
	 * @param stealValue the stealValue
	 * @return {@link List}<{@link BPIoItemDataEntity}>
	 */
	List<BPIoItemDataEntity> findByStealValue(Long stealValue);
	/**
	 * Retrieves a list of io item datas by their weight.
	 * @param weight the weight
	 * @return {@link List}<{@link BPIoItemDataEntity}>
	 */
	List<BPIoItemDataEntity> findByWeight(Float weight);
}
