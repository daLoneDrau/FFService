package com.osrapi.controllers.ff;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.osrapi.models.ff.FFTerrainEntity;

import com.osrapi.repositories.ff.FFTerrainRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/ff/terrains")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FFTerrainController {
    /** the static instance of {@link FFTerrainController}. */
    private static FFTerrainController instance;
    /**
     * Gets the static instance.
     * @return {@link FFTerrainController}
     */
    public static FFTerrainController getInstance() {
        if (instance == null) {
            new FFTerrainController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private FFTerrainRepository repository;
    /** Creates a new instance of {@link FFTerrainController}. */
    public FFTerrainController() {
        instance = this;
    }
    /**
     * Gets a list of {@link FFTerrainEntity}s.
     * @return {@link List}<{@link Resource}<{@link FFTerrainEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<FFTerrainEntity>> getAll() {
        Iterator<FFTerrainEntity> iter = repository.findAll()
                .iterator();
        List<Resource<FFTerrainEntity>> resources =
                new ArrayList<Resource<FFTerrainEntity>>();
        while (iter.hasNext()) {
            resources.add(getTerrainResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link FFTerrainEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link FFTerrainEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<FFTerrainEntity>> getById(
            @PathVariable final Long id) {
        FFTerrainEntity entity = repository.findOne(id);
        List<Resource<FFTerrainEntity>> resources =
                new ArrayList<Resource<FFTerrainEntity>>();
        resources.add(getTerrainResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link FFTerrainEntity}.
     * @param entity the {@link FFTerrainEntity}
     * @return {@link Resource}<{@link FFTerrainEntity}>
     */
    private Resource<FFTerrainEntity> getTerrainResource(
            final FFTerrainEntity entity) {
        Resource<FFTerrainEntity> resource =
                new Resource<FFTerrainEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link FFTerrainEntity}s.
     * @param entities the list of {@link FFTerrainEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFTerrainEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<FFTerrainEntity>> save(
            @RequestBody final List<FFTerrainEntity> entities) {
        List<Resource<FFTerrainEntity>> resources =
                new ArrayList<Resource<FFTerrainEntity>>();
        Iterator<FFTerrainEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link FFTerrainEntity}.
     * @param entity the {@link FFTerrainEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFTerrainEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<FFTerrainEntity>> save(
            @RequestBody final FFTerrainEntity entity) {
    
    
        FFTerrainEntity savedEntity = repository.save(entity);
        List<Resource<FFTerrainEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link FFTerrainEntity} instance
     */
    private void setIdFromRepository(final FFTerrainEntity entity) {
        List<FFTerrainEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = FFTerrainEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<FFTerrainEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = FFTerrainEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<FFTerrainEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
            e.printStackTrace();
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link FFTerrainEntity}s.
     * @param entities the list of {@link FFTerrainEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFTerrainEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<FFTerrainEntity>> update(
            @RequestBody final List<FFTerrainEntity> entities) {
        List<Resource<FFTerrainEntity>> resources = new ArrayList<Resource<FFTerrainEntity>>();
        Iterator<FFTerrainEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link FFTerrainEntity}.
     * @param entity the {@link FFTerrainEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFTerrainEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<FFTerrainEntity>> update(
            @RequestBody final FFTerrainEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        FFTerrainEntity savedEntity = repository.save(entity);
        List<Resource<FFTerrainEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link FFTerrainEntity}s that share a name.
     * @param name the terrain' name
     * @return {@link List}<{@link Resource}<{@link FFTerrainEntity}>>
     */
    @RequestMapping(path = "name/{name}",
            method = RequestMethod.GET)
    public List<Resource<FFTerrainEntity>> getByName(
            @PathVariable final String name) {
        Iterator<FFTerrainEntity> iter = repository.findByName(name)
                .iterator();
        List<Resource<FFTerrainEntity>> resources =
                new ArrayList<Resource<FFTerrainEntity>>();
        while (iter.hasNext()) {
            resources.add(getTerrainResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
