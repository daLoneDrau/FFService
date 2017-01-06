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

import com.osrapi.models.ff.FFGroupEntity;

import com.osrapi.repositories.ff.FFGroupRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/ff/groups")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FFGroupController {
    /** the static instance of {@link FFGroupController}. */
    private static FFGroupController instance;
    /**
     * Gets the static instance.
     * @return {@link FFGroupController}
     */
    public static FFGroupController getInstance() {
        if (instance == null) {
            new FFGroupController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private FFGroupRepository repository;
    /** Creates a new instance of {@link FFGroupController}. */
    public FFGroupController() {
        instance = this;
    }
    /**
     * Gets a list of {@link FFGroupEntity}s.
     * @return {@link List}<{@link Resource}<{@link FFGroupEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<FFGroupEntity>> getAll() {
        Iterator<FFGroupEntity> iter = repository.findAll()
                .iterator();
        List<Resource<FFGroupEntity>> resources =
                new ArrayList<Resource<FFGroupEntity>>();
        while (iter.hasNext()) {
            resources.add(getGroupResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link FFGroupEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link FFGroupEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<FFGroupEntity>> getById(
            @PathVariable final Long id) {
        FFGroupEntity entity = repository.findOne(id);
        List<Resource<FFGroupEntity>> resources =
                new ArrayList<Resource<FFGroupEntity>>();
        resources.add(getGroupResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link FFGroupEntity}.
     * @param entity the {@link FFGroupEntity}
     * @return {@link Resource}<{@link FFGroupEntity}>
     */
    private Resource<FFGroupEntity> getGroupResource(
            final FFGroupEntity entity) {
        Resource<FFGroupEntity> resource =
                new Resource<FFGroupEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link FFGroupEntity}s.
     * @param entities the list of {@link FFGroupEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFGroupEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<FFGroupEntity>> save(
            @RequestBody final List<FFGroupEntity> entities) {
        List<Resource<FFGroupEntity>> resources =
                new ArrayList<Resource<FFGroupEntity>>();
        Iterator<FFGroupEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link FFGroupEntity}.
     * @param entity the {@link FFGroupEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFGroupEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<FFGroupEntity>> save(
            @RequestBody final FFGroupEntity entity) {
    
    
        FFGroupEntity savedEntity = repository.save(entity);
        List<Resource<FFGroupEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link FFGroupEntity} instance
     */
    private void setIdFromRepository(final FFGroupEntity entity) {
        List<FFGroupEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = FFGroupEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<FFGroupEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = FFGroupEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<FFGroupEntity>) method.invoke(
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
     * Updates multiple {@link FFGroupEntity}s.
     * @param entities the list of {@link FFGroupEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFGroupEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<FFGroupEntity>> update(
            @RequestBody final List<FFGroupEntity> entities) {
        List<Resource<FFGroupEntity>> resources = new ArrayList<Resource<FFGroupEntity>>();
        Iterator<FFGroupEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link FFGroupEntity}.
     * @param entity the {@link FFGroupEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFGroupEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<FFGroupEntity>> update(
            @RequestBody final FFGroupEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        FFGroupEntity savedEntity = repository.save(entity);
        List<Resource<FFGroupEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link FFGroupEntity}s that share a name.
     * @param name the group' name
     * @return {@link List}<{@link Resource}<{@link FFGroupEntity}>>
     */
    @RequestMapping(path = "name/{name}",
            method = RequestMethod.GET)
    public List<Resource<FFGroupEntity>> getByName(
            @PathVariable final String name) {
        Iterator<FFGroupEntity> iter = repository.findByName(name)
                .iterator();
        List<Resource<FFGroupEntity>> resources =
                new ArrayList<Resource<FFGroupEntity>>();
        while (iter.hasNext()) {
            resources.add(getGroupResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
