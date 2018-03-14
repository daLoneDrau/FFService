package com.osrapi.repositories.bp;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.bp.BPScriptBundleEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface BPScriptBundleRepository
extends CrudRepository<BPScriptBundleEntity, Long> {
	/**
	 * Retrieves a list of script bundles by their name.
	 * @param name the name
	 * @return {@link List}<{@link BPScriptBundleEntity}>
	 */
	List<BPScriptBundleEntity> findByName(String name);
}
