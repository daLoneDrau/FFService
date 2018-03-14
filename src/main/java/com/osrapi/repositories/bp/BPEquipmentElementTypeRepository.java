package com.osrapi.repositories.bp;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.bp.BPEquipmentElementTypeEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface BPEquipmentElementTypeRepository
extends CrudRepository<BPEquipmentElementTypeEntity, Long> {
	/**
	 * Retrieves a list of equipment element types by their code.
	 * @param code the code
	 * @return {@link List}<{@link BPEquipmentElementTypeEntity}>
	 */
	List<BPEquipmentElementTypeEntity> findByCode(String code);
	/**
	 * Retrieves a list of equipment element types by their value.
	 * @param value the value
	 * @return {@link List}<{@link BPEquipmentElementTypeEntity}>
	 */
	List<BPEquipmentElementTypeEntity> findByValue(Long value);
}
