package com.osrapi.repositories.ff;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.ff.FFGenderEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface FFGenderRepository
extends CrudRepository<FFGenderEntity, Long> {
	/**
	 * Retrieves a list of genders by their description.
	 * @param description the description
	 * @return {@link List}<{@link FFGenderEntity}>
	 */
	List<FFGenderEntity> findByDescription(String description);
	/**
	 * Retrieves a list of genders by their name.
	 * @param name the name
	 * @return {@link List}<{@link FFGenderEntity}>
	 */
	List<FFGenderEntity> findByName(String name);
}
