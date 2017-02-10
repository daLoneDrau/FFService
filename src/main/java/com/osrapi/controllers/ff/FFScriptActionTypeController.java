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

import com.osrapi.models.ff.FFScriptActionTypeEntity;

import com.osrapi.repositories.ff.FFScriptActionTypeRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/ff/script_action_types")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FFScriptActionTypeController {
    /** the static instance of {@link FFScriptActionTypeController}. */
    private static FFScriptActionTypeController instance;
    /**
     * Gets the static instance.
     * @return {@link FFScriptActionTypeController}
     */
    public static FFScriptActionTypeController getInstance() {
        if (instance == null) {
            new FFScriptActionTypeController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private FFScriptActionTypeRepository repository;
    /** Creates a new instance of {@link FFScriptActionTypeController}. */
    public FFScriptActionTypeController() {
        instance = this;
    }
    /**
     * Gets a list of {@link FFScriptActionTypeEntity}s.
     * @return {@link List}<{@link Resource}<{@link FFScriptActionTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<FFScriptActionTypeEntity>> getAll() {
        Iterator<FFScriptActionTypeEntity> iter = repository.findAll()
                .iterator();
        List<Resource<FFScriptActionTypeEntity>> resources =
                new ArrayList<Resource<FFScriptActionTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getScriptActionTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link FFScriptActionTypeEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link FFScriptActionTypeEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<FFScriptActionTypeEntity>> getById(
            @PathVariable final Long id) {
        FFScriptActionTypeEntity entity = repository.findOne(id);
        List<Resource<FFScriptActionTypeEntity>> resources =
                new ArrayList<Resource<FFScriptActionTypeEntity>>();
        resources.add(getScriptActionTypeResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link FFScriptActionTypeEntity}.
     * @param entity the {@link FFScriptActionTypeEntity}
     * @return {@link Resource}<{@link FFScriptActionTypeEntity}>
     */
    private Resource<FFScriptActionTypeEntity> getScriptActionTypeResource(
            final FFScriptActionTypeEntity entity) {
        Resource<FFScriptActionTypeEntity> resource =
                new Resource<FFScriptActionTypeEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link FFScriptActionTypeEntity}s.
     * @param entities the list of {@link FFScriptActionTypeEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFScriptActionTypeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<FFScriptActionTypeEntity>> save(
            @RequestBody final List<FFScriptActionTypeEntity> entities) {
        List<Resource<FFScriptActionTypeEntity>> resources =
                new ArrayList<Resource<FFScriptActionTypeEntity>>();
        Iterator<FFScriptActionTypeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link FFScriptActionTypeEntity}.
     * @param entity the {@link FFScriptActionTypeEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFScriptActionTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<FFScriptActionTypeEntity>> save(
            @RequestBody final FFScriptActionTypeEntity entity) {
    
    
        FFScriptActionTypeEntity savedEntity = repository.save(entity);
        List<Resource<FFScriptActionTypeEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link FFScriptActionTypeEntity} instance
     */
    private void setIdFromRepository(final FFScriptActionTypeEntity entity) {
        List<FFScriptActionTypeEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = FFScriptActionTypeEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity FFScriptActionTypeEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<FFScriptActionTypeEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = FFScriptActionTypeEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity FFScriptActionTypeEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<FFScriptActionTypeEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity FFScriptActionTypeEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link FFScriptActionTypeEntity}s.
     * @param entities the list of {@link FFScriptActionTypeEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFScriptActionTypeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<FFScriptActionTypeEntity>> update(
            @RequestBody final List<FFScriptActionTypeEntity> entities) {
        List<Resource<FFScriptActionTypeEntity>> resources = new ArrayList<Resource<FFScriptActionTypeEntity>>();
        Iterator<FFScriptActionTypeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link FFScriptActionTypeEntity}.
     * @param entity the {@link FFScriptActionTypeEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFScriptActionTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<FFScriptActionTypeEntity>> update(
            @RequestBody final FFScriptActionTypeEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        FFScriptActionTypeEntity savedEntity = repository.save(entity);
        List<Resource<FFScriptActionTypeEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link FFScriptActionTypeEntity}s that share a code.
     * @param code the script_action_type' code
     * @return {@link List}<{@link Resource}<{@link FFScriptActionTypeEntity}>>
     */
    @RequestMapping(path = "code/{code}",
            method = RequestMethod.GET)
    public List<Resource<FFScriptActionTypeEntity>> getByCode(
            @PathVariable final String code) {
        Iterator<FFScriptActionTypeEntity> iter = repository.findByCode(code)
                .iterator();
        List<Resource<FFScriptActionTypeEntity>> resources =
                new ArrayList<Resource<FFScriptActionTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getScriptActionTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
