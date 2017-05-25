package com.osrapi.repositories.ff;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osrapi.models.ff.FFMapTileEntity;

/**
 *
 * @author drau
 *
 */
@Repository
public interface FFMapTileRepository
extends CrudRepository<FFMapTileEntity, Long> {
	/**
	 * Retrieves a list of map tiles by their name.
	 * @param name the name
	 * @return {@link List}<{@link FFMapTileEntity}>
	 */
	List<FFMapTileEntity> findByName(String name);
	/**
	 * Retrieves a list of map tiles by their codeNumber.
	 * @param codeNumber the codeNumber
	 * @return {@link List}<{@link FFMapTileEntity}>
	 */
	List<FFMapTileEntity> findByCodeNumber(Long codeNumber);
}
