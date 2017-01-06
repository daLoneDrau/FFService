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

import com.osrapi.models.ff.FFEventEntity;

import com.osrapi.repositories.ff.FFEventRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/ff/events")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FFEventController {
    /** the static instance of {@link FFEventController}. */
    private static FFEventController instance;
    /**
     * Gets the static instance.
     * @return {@link FFEventController}
     */
    public static FFEventController getInstance() {
        if (instance == null) {
            new FFEventController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private FFEventRepository repository;
    /** Creates a new instance of {@link FFEventController}. */
    public FFEventController() {
        instance = this;
    }
    /**
     * Gets a list of {@link FFEventEntity}s.
     * @return {@link List}<{@link Resource}<{@link FFEventEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<FFEventEntity>> getAll() {
        Iterator<FFEventEntity> iter = repository.findAll()
                .iterator();
        List<Resource<FFEventEntity>> resources =
                new ArrayList<Resource<FFEventEntity>>();
        while (iter.hasNext()) {
            resources.add(getEventResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link FFEventEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link FFEventEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<FFEventEntity>> getById(
            @PathVariable final Long id) {
        FFEventEntity entity = repository.findOne(id);
        List<Resource<FFEventEntity>> resources =
                new ArrayList<Resource<FFEventEntity>>();
        resources.add(getEventResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link FFEventEntity}.
     * @param entity the {@link FFEventEntity}
     * @return {@link Resource}<{@link FFEventEntity}>
     */
    private Resource<FFEventEntity> getEventResource(
            final FFEventEntity entity) {
        Resource<FFEventEntity> resource =
                new Resource<FFEventEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link FFEventEntity}s.
     * @param entities the list of {@link FFEventEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFEventEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<FFEventEntity>> save(
            @RequestBody final List<FFEventEntity> entities) {
        List<Resource<FFEventEntity>> resources =
                new ArrayList<Resource<FFEventEntity>>();
        Iterator<FFEventEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link FFEventEntity}.
     * @param entity the {@link FFEventEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFEventEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<FFEventEntity>> save(
            @RequestBody final FFEventEntity entity) {
    
    
        FFEventEntity savedEntity = repository.save(entity);
        List<Resource<FFEventEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link FFEventEntity} instance
     */
    private void setIdFromRepository(final FFEventEntity entity) {
        List<FFEventEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = FFEventEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<FFEventEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = FFEventEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<FFEventEntity>) method.invoke(
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
     * Updates multiple {@link FFEventEntity}s.
     * @param entities the list of {@link FFEventEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFEventEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<FFEventEntity>> update(
            @RequestBody final List<FFEventEntity> entities) {
        List<Resource<FFEventEntity>> resources = new ArrayList<Resource<FFEventEntity>>();
        Iterator<FFEventEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link FFEventEntity}.
     * @param entity the {@link FFEventEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFEventEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<FFEventEntity>> update(
            @RequestBody final FFEventEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        FFEventEntity savedEntity = repository.save(entity);
        List<Resource<FFEventEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link FFEventEntity}s that share a code.
     * @param code the event' code
     * @return {@link List}<{@link Resource}<{@link FFEventEntity}>>
     */
    @RequestMapping(path = "code/{code}",
            method = RequestMethod.GET)
    public List<Resource<FFEventEntity>> getByCode(
            @PathVariable final String code) {
        Iterator<FFEventEntity> iter = repository.findByCode(code)
                .iterator();
        List<Resource<FFEventEntity>> resources =
                new ArrayList<Resource<FFEventEntity>>();
        while (iter.hasNext()) {
            resources.add(getEventResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
