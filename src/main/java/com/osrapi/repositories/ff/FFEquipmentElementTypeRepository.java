package com.osrapi.repositories.ff;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.ff.FFEquipmentElementTypeEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface FFEquipmentElementTypeRepository
extends CrudRepository<FFEquipmentElementTypeEntity, Long> {
	/**
	 * Retrieves a list of equipment element types by their code.
	 * @param code the code
	 * @return {@link List}<{@link FFEquipmentElementTypeEntity}>
	 */
	List<FFEquipmentElementTypeEntity> findByCode(String code);
	/**
	 * Retrieves a list of equipment element types by their value.
	 * @param value the value
	 * @return {@link List}<{@link FFEquipmentElementTypeEntity}>
	 */
	List<FFEquipmentElementTypeEntity> findByValue(Long value);
}
