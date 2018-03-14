package com.osrapi.repositories.bp;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.bp.BPScriptActionEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface BPScriptActionRepository
extends CrudRepository<BPScriptActionEntity, Long> {
	/**
	 * Retrieves a list of script actions by their name.
	 * @param name the name
	 * @return {@link List}<{@link BPScriptActionEntity}>
	 */
	List<BPScriptActionEntity> findByName(String name);
	/**
	 * Retrieves a list of script actions by their type.
	 * @param type the type
	 * @return {@link List}<{@link BPScriptActionEntity}>
	 */
	List<BPScriptActionEntity> findByType(String type);
}
