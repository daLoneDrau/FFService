package com.osrapi.repositories.ff;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.ff.FFDiceEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface FFDiceRepository
extends CrudRepository<FFDiceEntity, Long> {
	/**
	 * Retrieves a list of dices by their code.
	 * @param code the code
	 * @return {@link List}<{@link FFDiceEntity}>
	 */
	List<FFDiceEntity> findByCode(String code);
	/**
	 * Retrieves a list of dices by their number.
	 * @param number the number
	 * @return {@link List}<{@link FFDiceEntity}>
	 */
	List<FFDiceEntity> findByNumber(Long number);
	/**
	 * Retrieves a list of dices by their plus.
	 * @param plus the plus
	 * @return {@link List}<{@link FFDiceEntity}>
	 */
	List<FFDiceEntity> findByPlus(Long plus);
}
