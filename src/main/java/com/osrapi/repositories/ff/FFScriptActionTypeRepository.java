package com.osrapi.repositories.ff;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.ff.FFScriptActionTypeEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface FFScriptActionTypeRepository
extends CrudRepository<FFScriptActionTypeEntity, Long> {
	/**
	 * Retrieves a list of script action types by their code.
	 * @param code the code
	 * @return {@link List}<{@link FFScriptActionTypeEntity}>
	 */
	List<FFScriptActionTypeEntity> findByCode(String code);
}
