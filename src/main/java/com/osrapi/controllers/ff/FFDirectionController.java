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

import com.osrapi.models.ff.FFDirectionEntity;

import com.osrapi.repositories.ff.FFDirectionRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/ff/directions")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FFDirectionController {
    /** the static instance of {@link FFDirectionController}. */
    private static FFDirectionController instance;
    /**
     * Gets the static instance.
     * @return {@link FFDirectionController}
     */
    public static FFDirectionController getInstance() {
        if (instance == null) {
            new FFDirectionController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private FFDirectionRepository repository;
    /** Creates a new instance of {@link FFDirectionController}. */
    public FFDirectionController() {
        instance = this;
    }
    /**
     * Gets a list of {@link FFDirectionEntity}s.
     * @return {@link List}<{@link Resource}<{@link FFDirectionEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<FFDirectionEntity>> getAll() {
        Iterator<FFDirectionEntity> iter = repository.findAll()
                .iterator();
        List<Resource<FFDirectionEntity>> resources =
                new ArrayList<Resource<FFDirectionEntity>>();
        while (iter.hasNext()) {
            resources.add(getDirectionResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link FFDirectionEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link FFDirectionEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<FFDirectionEntity>> getById(
            @PathVariable final Long id) {
        FFDirectionEntity entity = repository.findOne(id);
        List<Resource<FFDirectionEntity>> resources =
                new ArrayList<Resource<FFDirectionEntity>>();
        resources.add(getDirectionResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link FFDirectionEntity}.
     * @param entity the {@link FFDirectionEntity}
     * @return {@link Resource}<{@link FFDirectionEntity}>
     */
    private Resource<FFDirectionEntity> getDirectionResource(
            final FFDirectionEntity entity) {
        Resource<FFDirectionEntity> resource =
                new Resource<FFDirectionEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link FFDirectionEntity}s.
     * @param entities the list of {@link FFDirectionEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFDirectionEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<FFDirectionEntity>> save(
            @RequestBody final List<FFDirectionEntity> entities) {
        List<Resource<FFDirectionEntity>> resources =
                new ArrayList<Resource<FFDirectionEntity>>();
        Iterator<FFDirectionEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link FFDirectionEntity}.
     * @param entity the {@link FFDirectionEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFDirectionEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<FFDirectionEntity>> save(
            @RequestBody final FFDirectionEntity entity) {
    
    
        FFDirectionEntity savedEntity = repository.save(entity);
        List<Resource<FFDirectionEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link FFDirectionEntity} instance
     */
    private void setIdFromRepository(final FFDirectionEntity entity) {
        List<FFDirectionEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = FFDirectionEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity FFDirectionEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<FFDirectionEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = FFDirectionEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity FFDirectionEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<FFDirectionEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity FFDirectionEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link FFDirectionEntity}s.
     * @param entities the list of {@link FFDirectionEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFDirectionEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<FFDirectionEntity>> update(
            @RequestBody final List<FFDirectionEntity> entities) {
        List<Resource<FFDirectionEntity>> resources = new ArrayList<Resource<FFDirectionEntity>>();
        Iterator<FFDirectionEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link FFDirectionEntity}.
     * @param entity the {@link FFDirectionEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFDirectionEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<FFDirectionEntity>> update(
            @RequestBody final FFDirectionEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        FFDirectionEntity savedEntity = repository.save(entity);
        List<Resource<FFDirectionEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link FFDirectionEntity}s that share a code.
     * @param code the direction' code
     * @return {@link List}<{@link Resource}<{@link FFDirectionEntity}>>
     */
    @RequestMapping(path = "code/{code}",
            method = RequestMethod.GET)
    public List<Resource<FFDirectionEntity>> getByCode(
            @PathVariable final String code) {
        Iterator<FFDirectionEntity> iter = repository.findByCode(code)
                .iterator();
        List<Resource<FFDirectionEntity>> resources =
                new ArrayList<Resource<FFDirectionEntity>>();
        while (iter.hasNext()) {
            resources.add(getDirectionResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFDirectionEntity}s that share a value.
     * @param value the direction' value
     * @return {@link List}<{@link Resource}<{@link FFDirectionEntity}>>
     */
    @RequestMapping(path = "value/{value}",
            method = RequestMethod.GET)
    public List<Resource<FFDirectionEntity>> getByValue(
            @PathVariable final Long value) {
        Iterator<FFDirectionEntity> iter = repository.findByValue(value)
                .iterator();
        List<Resource<FFDirectionEntity>> resources =
                new ArrayList<Resource<FFDirectionEntity>>();
        while (iter.hasNext()) {
            resources.add(getDirectionResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
