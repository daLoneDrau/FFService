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

import com.osrapi.models.ff.FFCommandEntity;

import com.osrapi.repositories.ff.FFCommandRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/ff/commands")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FFCommandController {
    /** the static instance of {@link FFCommandController}. */
    private static FFCommandController instance;
    /**
     * Gets the static instance.
     * @return {@link FFCommandController}
     */
    public static FFCommandController getInstance() {
        if (instance == null) {
            new FFCommandController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private FFCommandRepository repository;
    /** Creates a new instance of {@link FFCommandController}. */
    public FFCommandController() {
        instance = this;
    }
    /**
     * Gets a list of {@link FFCommandEntity}s.
     * @return {@link List}<{@link Resource}<{@link FFCommandEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<FFCommandEntity>> getAll() {
        Iterator<FFCommandEntity> iter = repository.findAll()
                .iterator();
        List<Resource<FFCommandEntity>> resources =
                new ArrayList<Resource<FFCommandEntity>>();
        while (iter.hasNext()) {
            resources.add(getCommandResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link FFCommandEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link FFCommandEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<FFCommandEntity>> getById(
            @PathVariable final Long id) {
        FFCommandEntity entity = repository.findOne(id);
        List<Resource<FFCommandEntity>> resources =
                new ArrayList<Resource<FFCommandEntity>>();
        resources.add(getCommandResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link FFCommandEntity}.
     * @param entity the {@link FFCommandEntity}
     * @return {@link Resource}<{@link FFCommandEntity}>
     */
    private Resource<FFCommandEntity> getCommandResource(
            final FFCommandEntity entity) {
        Resource<FFCommandEntity> resource =
                new Resource<FFCommandEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link FFCommandEntity}s.
     * @param entities the list of {@link FFCommandEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFCommandEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<FFCommandEntity>> save(
            @RequestBody final List<FFCommandEntity> entities) {
        List<Resource<FFCommandEntity>> resources =
                new ArrayList<Resource<FFCommandEntity>>();
        Iterator<FFCommandEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link FFCommandEntity}.
     * @param entity the {@link FFCommandEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFCommandEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<FFCommandEntity>> save(
            @RequestBody final FFCommandEntity entity) {
    
    
        FFCommandEntity savedEntity = repository.save(entity);
        List<Resource<FFCommandEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link FFCommandEntity} instance
     */
    private void setIdFromRepository(final FFCommandEntity entity) {
        List<FFCommandEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = FFCommandEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity FFCommandEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<FFCommandEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = FFCommandEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity FFCommandEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<FFCommandEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity FFCommandEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link FFCommandEntity}s.
     * @param entities the list of {@link FFCommandEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFCommandEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<FFCommandEntity>> update(
            @RequestBody final List<FFCommandEntity> entities) {
        List<Resource<FFCommandEntity>> resources = new ArrayList<Resource<FFCommandEntity>>();
        Iterator<FFCommandEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link FFCommandEntity}.
     * @param entity the {@link FFCommandEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFCommandEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<FFCommandEntity>> update(
            @RequestBody final FFCommandEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        FFCommandEntity savedEntity = repository.save(entity);
        List<Resource<FFCommandEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link FFCommandEntity}s that share a name.
     * @param name the command' name
     * @return {@link List}<{@link Resource}<{@link FFCommandEntity}>>
     */
    @RequestMapping(path = "name/{name}",
            method = RequestMethod.GET)
    public List<Resource<FFCommandEntity>> getByName(
            @PathVariable final String name) {
        Iterator<FFCommandEntity> iter = repository.findByName(name)
                .iterator();
        List<Resource<FFCommandEntity>> resources =
                new ArrayList<Resource<FFCommandEntity>>();
        while (iter.hasNext()) {
            resources.add(getCommandResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFCommandEntity}s that share a sortOrder.
     * @param sortOrder the command' sortOrder
     * @return {@link List}<{@link Resource}<{@link FFCommandEntity}>>
     */
    @RequestMapping(path = "sort_order/{sortOrder}",
            method = RequestMethod.GET)
    public List<Resource<FFCommandEntity>> getBySortOrder(
            @PathVariable final Long sortOrder) {
        Iterator<FFCommandEntity> iter = repository.findBySortOrder(sortOrder)
                .iterator();
        List<Resource<FFCommandEntity>> resources =
                new ArrayList<Resource<FFCommandEntity>>();
        while (iter.hasNext()) {
            resources.add(getCommandResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
