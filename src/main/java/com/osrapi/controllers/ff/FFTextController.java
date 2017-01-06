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

import com.osrapi.models.ff.FFTextEntity;

import com.osrapi.repositories.ff.FFTextRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/ff/texts")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FFTextController {
    /** the static instance of {@link FFTextController}. */
    private static FFTextController instance;
    /**
     * Gets the static instance.
     * @return {@link FFTextController}
     */
    public static FFTextController getInstance() {
        if (instance == null) {
            new FFTextController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private FFTextRepository repository;
    /** Creates a new instance of {@link FFTextController}. */
    public FFTextController() {
        instance = this;
    }
    /**
     * Gets a list of {@link FFTextEntity}s.
     * @return {@link List}<{@link Resource}<{@link FFTextEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<FFTextEntity>> getAll() {
        Iterator<FFTextEntity> iter = repository.findAll()
                .iterator();
        List<Resource<FFTextEntity>> resources =
                new ArrayList<Resource<FFTextEntity>>();
        while (iter.hasNext()) {
            resources.add(getTextResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link FFTextEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link FFTextEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<FFTextEntity>> getById(
            @PathVariable final Long id) {
        FFTextEntity entity = repository.findOne(id);
        List<Resource<FFTextEntity>> resources =
                new ArrayList<Resource<FFTextEntity>>();
        resources.add(getTextResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link FFTextEntity}.
     * @param entity the {@link FFTextEntity}
     * @return {@link Resource}<{@link FFTextEntity}>
     */
    private Resource<FFTextEntity> getTextResource(
            final FFTextEntity entity) {
        Resource<FFTextEntity> resource =
                new Resource<FFTextEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link FFTextEntity}s.
     * @param entities the list of {@link FFTextEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFTextEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<FFTextEntity>> save(
            @RequestBody final List<FFTextEntity> entities) {
        List<Resource<FFTextEntity>> resources =
                new ArrayList<Resource<FFTextEntity>>();
        Iterator<FFTextEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link FFTextEntity}.
     * @param entity the {@link FFTextEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFTextEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<FFTextEntity>> save(
            @RequestBody final FFTextEntity entity) {
    
    
        FFTextEntity savedEntity = repository.save(entity);
        List<Resource<FFTextEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link FFTextEntity} instance
     */
    private void setIdFromRepository(final FFTextEntity entity) {
        List<FFTextEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = FFTextEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<FFTextEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = FFTextEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<FFTextEntity>) method.invoke(
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
     * Updates multiple {@link FFTextEntity}s.
     * @param entities the list of {@link FFTextEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFTextEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<FFTextEntity>> update(
            @RequestBody final List<FFTextEntity> entities) {
        List<Resource<FFTextEntity>> resources = new ArrayList<Resource<FFTextEntity>>();
        Iterator<FFTextEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link FFTextEntity}.
     * @param entity the {@link FFTextEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFTextEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<FFTextEntity>> update(
            @RequestBody final FFTextEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        FFTextEntity savedEntity = repository.save(entity);
        List<Resource<FFTextEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link FFTextEntity}s that share a name.
     * @param name the text' name
     * @return {@link List}<{@link Resource}<{@link FFTextEntity}>>
     */
    @RequestMapping(path = "name/{name}",
            method = RequestMethod.GET)
    public List<Resource<FFTextEntity>> getByName(
            @PathVariable final String name) {
        Iterator<FFTextEntity> iter = repository.findByName(name)
                .iterator();
        List<Resource<FFTextEntity>> resources =
                new ArrayList<Resource<FFTextEntity>>();
        while (iter.hasNext()) {
            resources.add(getTextResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFTextEntity}s that share a text.
     * @param text the text' text
     * @return {@link List}<{@link Resource}<{@link FFTextEntity}>>
     */
    @RequestMapping(path = "text/{text}",
            method = RequestMethod.GET)
    public List<Resource<FFTextEntity>> getByText(
            @PathVariable final String text) {
        Iterator<FFTextEntity> iter = repository.findByText(text)
                .iterator();
        List<Resource<FFTextEntity>> resources =
                new ArrayList<Resource<FFTextEntity>>();
        while (iter.hasNext()) {
            resources.add(getTextResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
