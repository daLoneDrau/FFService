package com.osrapi.repositories.bp;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.bp.BPEquipmentSlotEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface BPEquipmentSlotRepository
extends CrudRepository<BPEquipmentSlotEntity, Long> {
	/**
	 * Retrieves a list of equipment slots by their name.
	 * @param name the name
	 * @return {@link List}<{@link BPEquipmentSlotEntity}>
	 */
	List<BPEquipmentSlotEntity> findByName(String name);
	/**
	 * Retrieves a list of equipment slots by their val.
	 * @param val the val
	 * @return {@link List}<{@link BPEquipmentSlotEntity}>
	 */
	List<BPEquipmentSlotEntity> findByVal(Long val);
}
