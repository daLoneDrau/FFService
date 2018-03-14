package com.osrapi.repositories.bp;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.bp.BPGenderEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface BPGenderRepository
extends CrudRepository<BPGenderEntity, Long> {
	/**
	 * Retrieves a list of genders by their description.
	 * @param description the description
	 * @return {@link List}<{@link BPGenderEntity}>
	 */
	List<BPGenderEntity> findByDescription(String description);
	/**
	 * Retrieves a list of genders by their name.
	 * @param name the name
	 * @return {@link List}<{@link BPGenderEntity}>
	 */
	List<BPGenderEntity> findByName(String name);
}
