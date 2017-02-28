package com.osrapi.repositories.ff;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.ff.FFScriptBundleEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface FFScriptBundleRepository
extends CrudRepository<FFScriptBundleEntity, Long> {
	/**
	 * Retrieves a list of script bundles by their name.
	 * @param name the name
	 * @return {@link List}<{@link FFScriptBundleEntity}>
	 */
	List<FFScriptBundleEntity> findByName(String name);
}
