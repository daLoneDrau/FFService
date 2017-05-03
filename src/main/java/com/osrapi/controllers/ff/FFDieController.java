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

import com.osrapi.models.ff.FFDieEntity;

import com.osrapi.repositories.ff.FFDieRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/ff/dies")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FFDieController {
    /** the static instance of {@link FFDieController}. */
    private static FFDieController instance;
    /**
     * Gets the static instance.
     * @return {@link FFDieController}
     */
    public static FFDieController getInstance() {
        if (instance == null) {
            new FFDieController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private FFDieRepository repository;
    /** Creates a new instance of {@link FFDieController}. */
    public FFDieController() {
        instance = this;
    }
    /**
     * Gets a list of {@link FFDieEntity}s.
     * @return {@link List}<{@link Resource}<{@link FFDieEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<FFDieEntity>> getAll() {
        Iterator<FFDieEntity> iter = repository.findAll()
                .iterator();
        List<Resource<FFDieEntity>> resources =
                new ArrayList<Resource<FFDieEntity>>();
        while (iter.hasNext()) {
            resources.add(getDieResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link FFDieEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link FFDieEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<FFDieEntity>> getById(
            @PathVariable final Long id) {
        FFDieEntity entity = repository.findOne(id);
        List<Resource<FFDieEntity>> resources =
                new ArrayList<Resource<FFDieEntity>>();
        resources.add(getDieResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link FFDieEntity}.
     * @param entity the {@link FFDieEntity}
     * @return {@link Resource}<{@link FFDieEntity}>
     */
    private Resource<FFDieEntity> getDieResource(
            final FFDieEntity entity) {
        Resource<FFDieEntity> resource =
                new Resource<FFDieEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link FFDieEntity}s.
     * @param entities the list of {@link FFDieEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFDieEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<FFDieEntity>> save(
            @RequestBody final List<FFDieEntity> entities) {
        List<Resource<FFDieEntity>> resources =
                new ArrayList<Resource<FFDieEntity>>();
        Iterator<FFDieEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link FFDieEntity}.
     * @param entity the {@link FFDieEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFDieEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<FFDieEntity>> save(
            @RequestBody final FFDieEntity entity) {
    
    
        FFDieEntity savedEntity = repository.save(entity);
        List<Resource<FFDieEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link FFDieEntity} instance
     */
    private void setIdFromRepository(final FFDieEntity entity) {
        List<FFDieEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = FFDieEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity FFDieEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<FFDieEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = FFDieEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity FFDieEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<FFDieEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity FFDieEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link FFDieEntity}s.
     * @param entities the list of {@link FFDieEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFDieEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<FFDieEntity>> update(
            @RequestBody final List<FFDieEntity> entities) {
        List<Resource<FFDieEntity>> resources = new ArrayList<Resource<FFDieEntity>>();
        Iterator<FFDieEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link FFDieEntity}.
     * @param entity the {@link FFDieEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFDieEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<FFDieEntity>> update(
            @RequestBody final FFDieEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        FFDieEntity savedEntity = repository.save(entity);
        List<Resource<FFDieEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link FFDieEntity}s that share a code.
     * @param code the die' code
     * @return {@link List}<{@link Resource}<{@link FFDieEntity}>>
     */
    @RequestMapping(path = "code/{code}",
            method = RequestMethod.GET)
    public List<Resource<FFDieEntity>> getByCode(
            @PathVariable final String code) {
        Iterator<FFDieEntity> iter = repository.findByCode(code)
                .iterator();
        List<Resource<FFDieEntity>> resources =
                new ArrayList<Resource<FFDieEntity>>();
        while (iter.hasNext()) {
            resources.add(getDieResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFDieEntity}s that share a value.
     * @param value the die' value
     * @return {@link List}<{@link Resource}<{@link FFDieEntity}>>
     */
    @RequestMapping(path = "value/{value}",
            method = RequestMethod.GET)
    public List<Resource<FFDieEntity>> getByValue(
            @PathVariable final Long value) {
        Iterator<FFDieEntity> iter = repository.findByValue(value)
                .iterator();
        List<Resource<FFDieEntity>> resources =
                new ArrayList<Resource<FFDieEntity>>();
        while (iter.hasNext()) {
            resources.add(getDieResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
