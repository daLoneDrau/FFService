package com.osrapi.repositories.bp;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.bp.BPEquipmentItemModifierEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface BPEquipmentItemModifierRepository
extends CrudRepository<BPEquipmentItemModifierEntity, Long> {
	/**
	 * Retrieves a list of equipment item modifiers by their code.
	 * @param code the code
	 * @return {@link List}<{@link BPEquipmentItemModifierEntity}>
	 */
	List<BPEquipmentItemModifierEntity> findByCode(String code);
	/**
	 * Retrieves a list of equipment item modifiers by their percent.
	 * @param percent the percent
	 * @return {@link List}<{@link BPEquipmentItemModifierEntity}>
	 */
	List<BPEquipmentItemModifierEntity> findByPercent(Boolean percent);
	/**
	 * Retrieves a list of equipment item modifiers by their special.
	 * @param special the special
	 * @return {@link List}<{@link BPEquipmentItemModifierEntity}>
	 */
	List<BPEquipmentItemModifierEntity> findBySpecial(Long special);
	/**
	 * Retrieves a list of equipment item modifiers by their value.
	 * @param value the value
	 * @return {@link List}<{@link BPEquipmentItemModifierEntity}>
	 */
	List<BPEquipmentItemModifierEntity> findByValue(Float value);
}
