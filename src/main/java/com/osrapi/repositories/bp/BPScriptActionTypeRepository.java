package com.osrapi.repositories.bp;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.bp.BPScriptActionTypeEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface BPScriptActionTypeRepository
extends CrudRepository<BPScriptActionTypeEntity, Long> {
	/**
	 * Retrieves a list of script action types by their code.
	 * @param code the code
	 * @return {@link List}<{@link BPScriptActionTypeEntity}>
	 */
	List<BPScriptActionTypeEntity> findByCode(String code);
}
