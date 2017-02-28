package com.osrapi.repositories.ff;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.ff.FFEquipmentSlotEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface FFEquipmentSlotRepository
extends CrudRepository<FFEquipmentSlotEntity, Long> {
	/**
	 * Retrieves a list of equipment slots by their name.
	 * @param name the name
	 * @return {@link List}<{@link FFEquipmentSlotEntity}>
	 */
	List<FFEquipmentSlotEntity> findByName(String name);
	/**
	 * Retrieves a list of equipment slots by their value.
	 * @param value the value
	 * @return {@link List}<{@link FFEquipmentSlotEntity}>
	 */
	List<FFEquipmentSlotEntity> findByValue(Long value);
}
