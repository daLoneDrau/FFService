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

import com.osrapi.models.ff.FFObjectTypeEntity;

import com.osrapi.repositories.ff.FFObjectTypeRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/ff/object_types")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FFObjectTypeController {
    /** the static instance of {@link FFObjectTypeController}. */
    private static FFObjectTypeController instance;
    /**
     * Gets the static instance.
     * @return {@link FFObjectTypeController}
     */
    public static FFObjectTypeController getInstance() {
        if (instance == null) {
            new FFObjectTypeController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private FFObjectTypeRepository repository;
    /** Creates a new instance of {@link FFObjectTypeController}. */
    public FFObjectTypeController() {
        instance = this;
    }
    /**
     * Gets a list of {@link FFObjectTypeEntity}s.
     * @return {@link List}<{@link Resource}<{@link FFObjectTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<FFObjectTypeEntity>> getAll() {
        Iterator<FFObjectTypeEntity> iter = repository.findAll()
                .iterator();
        List<Resource<FFObjectTypeEntity>> resources =
                new ArrayList<Resource<FFObjectTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getObjectTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link FFObjectTypeEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link FFObjectTypeEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<FFObjectTypeEntity>> getById(
            @PathVariable final Long id) {
        FFObjectTypeEntity entity = repository.findOne(id);
        List<Resource<FFObjectTypeEntity>> resources =
                new ArrayList<Resource<FFObjectTypeEntity>>();
        resources.add(getObjectTypeResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link FFObjectTypeEntity}.
     * @param entity the {@link FFObjectTypeEntity}
     * @return {@link Resource}<{@link FFObjectTypeEntity}>
     */
    private Resource<FFObjectTypeEntity> getObjectTypeResource(
            final FFObjectTypeEntity entity) {
        Resource<FFObjectTypeEntity> resource =
                new Resource<FFObjectTypeEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link FFObjectTypeEntity}s.
     * @param entities the list of {@link FFObjectTypeEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFObjectTypeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<FFObjectTypeEntity>> save(
            @RequestBody final List<FFObjectTypeEntity> entities) {
        List<Resource<FFObjectTypeEntity>> resources =
                new ArrayList<Resource<FFObjectTypeEntity>>();
        Iterator<FFObjectTypeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link FFObjectTypeEntity}.
     * @param entity the {@link FFObjectTypeEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFObjectTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<FFObjectTypeEntity>> save(
            @RequestBody final FFObjectTypeEntity entity) {
    
    
        FFObjectTypeEntity savedEntity = repository.save(entity);
        List<Resource<FFObjectTypeEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link FFObjectTypeEntity} instance
     */
    private void setIdFromRepository(final FFObjectTypeEntity entity) {
        List<FFObjectTypeEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = FFObjectTypeEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<FFObjectTypeEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = FFObjectTypeEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<FFObjectTypeEntity>) method.invoke(
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
     * Updates multiple {@link FFObjectTypeEntity}s.
     * @param entities the list of {@link FFObjectTypeEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFObjectTypeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<FFObjectTypeEntity>> update(
            @RequestBody final List<FFObjectTypeEntity> entities) {
        List<Resource<FFObjectTypeEntity>> resources = new ArrayList<Resource<FFObjectTypeEntity>>();
        Iterator<FFObjectTypeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link FFObjectTypeEntity}.
     * @param entity the {@link FFObjectTypeEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFObjectTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<FFObjectTypeEntity>> update(
            @RequestBody final FFObjectTypeEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        FFObjectTypeEntity savedEntity = repository.save(entity);
        List<Resource<FFObjectTypeEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link FFObjectTypeEntity}s that share a code.
     * @param code the object_type' code
     * @return {@link List}<{@link Resource}<{@link FFObjectTypeEntity}>>
     */
    @RequestMapping(path = "code/{code}",
            method = RequestMethod.GET)
    public List<Resource<FFObjectTypeEntity>> getByCode(
            @PathVariable final String code) {
        Iterator<FFObjectTypeEntity> iter = repository.findByCode(code)
                .iterator();
        List<Resource<FFObjectTypeEntity>> resources =
                new ArrayList<Resource<FFObjectTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getObjectTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFObjectTypeEntity}s that share a flag.
     * @param flag the object_type' flag
     * @return {@link List}<{@link Resource}<{@link FFObjectTypeEntity}>>
     */
    @RequestMapping(path = "flag/{flag}",
            method = RequestMethod.GET)
    public List<Resource<FFObjectTypeEntity>> getByFlag(
            @PathVariable final Long flag) {
        Iterator<FFObjectTypeEntity> iter = repository.findByFlag(flag)
                .iterator();
        List<Resource<FFObjectTypeEntity>> resources =
                new ArrayList<Resource<FFObjectTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getObjectTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
