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

import com.osrapi.models.ff.FFDamageTypeEntity;

import com.osrapi.repositories.ff.FFDamageTypeRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/ff/damage_types")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FFDamageTypeController {
    /** the static instance of {@link FFDamageTypeController}. */
    private static FFDamageTypeController instance;
    /**
     * Gets the static instance.
     * @return {@link FFDamageTypeController}
     */
    public static FFDamageTypeController getInstance() {
        if (instance == null) {
            new FFDamageTypeController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private FFDamageTypeRepository repository;
    /** Creates a new instance of {@link FFDamageTypeController}. */
    public FFDamageTypeController() {
        instance = this;
    }
    /**
     * Gets a list of {@link FFDamageTypeEntity}s.
     * @return {@link List}<{@link Resource}<{@link FFDamageTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<FFDamageTypeEntity>> getAll() {
        Iterator<FFDamageTypeEntity> iter = repository.findAll()
                .iterator();
        List<Resource<FFDamageTypeEntity>> resources =
                new ArrayList<Resource<FFDamageTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getDamageTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link FFDamageTypeEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link FFDamageTypeEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<FFDamageTypeEntity>> getById(
            @PathVariable final Long id) {
        FFDamageTypeEntity entity = repository.findOne(id);
        List<Resource<FFDamageTypeEntity>> resources =
                new ArrayList<Resource<FFDamageTypeEntity>>();
        resources.add(getDamageTypeResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link FFDamageTypeEntity}.
     * @param entity the {@link FFDamageTypeEntity}
     * @return {@link Resource}<{@link FFDamageTypeEntity}>
     */
    private Resource<FFDamageTypeEntity> getDamageTypeResource(
            final FFDamageTypeEntity entity) {
        Resource<FFDamageTypeEntity> resource =
                new Resource<FFDamageTypeEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link FFDamageTypeEntity}s.
     * @param entities the list of {@link FFDamageTypeEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFDamageTypeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<FFDamageTypeEntity>> save(
            @RequestBody final List<FFDamageTypeEntity> entities) {
        List<Resource<FFDamageTypeEntity>> resources =
                new ArrayList<Resource<FFDamageTypeEntity>>();
        Iterator<FFDamageTypeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link FFDamageTypeEntity}.
     * @param entity the {@link FFDamageTypeEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFDamageTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<FFDamageTypeEntity>> save(
            @RequestBody final FFDamageTypeEntity entity) {
    
    
        FFDamageTypeEntity savedEntity = repository.save(entity);
        List<Resource<FFDamageTypeEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link FFDamageTypeEntity} instance
     */
    private void setIdFromRepository(final FFDamageTypeEntity entity) {
        List<FFDamageTypeEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = FFDamageTypeEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<FFDamageTypeEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = FFDamageTypeEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<FFDamageTypeEntity>) method.invoke(
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
     * Updates multiple {@link FFDamageTypeEntity}s.
     * @param entities the list of {@link FFDamageTypeEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFDamageTypeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<FFDamageTypeEntity>> update(
            @RequestBody final List<FFDamageTypeEntity> entities) {
        List<Resource<FFDamageTypeEntity>> resources = new ArrayList<Resource<FFDamageTypeEntity>>();
        Iterator<FFDamageTypeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link FFDamageTypeEntity}.
     * @param entity the {@link FFDamageTypeEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFDamageTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<FFDamageTypeEntity>> update(
            @RequestBody final FFDamageTypeEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        FFDamageTypeEntity savedEntity = repository.save(entity);
        List<Resource<FFDamageTypeEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link FFDamageTypeEntity}s that share a flag.
     * @param flag the damage_type' flag
     * @return {@link List}<{@link Resource}<{@link FFDamageTypeEntity}>>
     */
    @RequestMapping(path = "flag/{flag}",
            method = RequestMethod.GET)
    public List<Resource<FFDamageTypeEntity>> getByFlag(
            @PathVariable final Long flag) {
        Iterator<FFDamageTypeEntity> iter = repository.findByFlag(flag)
                .iterator();
        List<Resource<FFDamageTypeEntity>> resources =
                new ArrayList<Resource<FFDamageTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getDamageTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFDamageTypeEntity}s that share a name.
     * @param name the damage_type' name
     * @return {@link List}<{@link Resource}<{@link FFDamageTypeEntity}>>
     */
    @RequestMapping(path = "name/{name}",
            method = RequestMethod.GET)
    public List<Resource<FFDamageTypeEntity>> getByName(
            @PathVariable final String name) {
        Iterator<FFDamageTypeEntity> iter = repository.findByName(name)
                .iterator();
        List<Resource<FFDamageTypeEntity>> resources =
                new ArrayList<Resource<FFDamageTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getDamageTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
