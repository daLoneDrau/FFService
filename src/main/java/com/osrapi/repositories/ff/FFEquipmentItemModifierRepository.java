package com.osrapi.repositories.ff;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.ff.FFEquipmentItemModifierEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface FFEquipmentItemModifierRepository
extends CrudRepository<FFEquipmentItemModifierEntity, Long> {
	/**
	 * Retrieves a list of equipment item modifiers by their code.
	 * @param code the code
	 * @return {@link List}<{@link FFEquipmentItemModifierEntity}>
	 */
	List<FFEquipmentItemModifierEntity> findByCode(String code);
	/**
	 * Retrieves a list of equipment item modifiers by their percent.
	 * @param percent the percent
	 * @return {@link List}<{@link FFEquipmentItemModifierEntity}>
	 */
	List<FFEquipmentItemModifierEntity> findByPercent(Boolean percent);
	/**
	 * Retrieves a list of equipment item modifiers by their special.
	 * @param special the special
	 * @return {@link List}<{@link FFEquipmentItemModifierEntity}>
	 */
	List<FFEquipmentItemModifierEntity> findBySpecial(Long special);
	/**
	 * Retrieves a list of equipment item modifiers by their value.
	 * @param value the value
	 * @return {@link List}<{@link FFEquipmentItemModifierEntity}>
	 */
	List<FFEquipmentItemModifierEntity> findByValue(Float value);
}
