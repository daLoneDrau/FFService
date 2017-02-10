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

import com.osrapi.models.ff.FFGenderEntity;

import com.osrapi.repositories.ff.FFGenderRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/ff/genders")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FFGenderController {
    /** the static instance of {@link FFGenderController}. */
    private static FFGenderController instance;
    /**
     * Gets the static instance.
     * @return {@link FFGenderController}
     */
    public static FFGenderController getInstance() {
        if (instance == null) {
            new FFGenderController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private FFGenderRepository repository;
    /** Creates a new instance of {@link FFGenderController}. */
    public FFGenderController() {
        instance = this;
    }
    /**
     * Gets a list of {@link FFGenderEntity}s.
     * @return {@link List}<{@link Resource}<{@link FFGenderEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<FFGenderEntity>> getAll() {
        Iterator<FFGenderEntity> iter = repository.findAll()
                .iterator();
        List<Resource<FFGenderEntity>> resources =
                new ArrayList<Resource<FFGenderEntity>>();
        while (iter.hasNext()) {
            resources.add(getGenderResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link FFGenderEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link FFGenderEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<FFGenderEntity>> getById(
            @PathVariable final Long id) {
        FFGenderEntity entity = repository.findOne(id);
        List<Resource<FFGenderEntity>> resources =
                new ArrayList<Resource<FFGenderEntity>>();
        resources.add(getGenderResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link FFGenderEntity}.
     * @param entity the {@link FFGenderEntity}
     * @return {@link Resource}<{@link FFGenderEntity}>
     */
    private Resource<FFGenderEntity> getGenderResource(
            final FFGenderEntity entity) {
        Resource<FFGenderEntity> resource =
                new Resource<FFGenderEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link FFGenderEntity}s.
     * @param entities the list of {@link FFGenderEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFGenderEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<FFGenderEntity>> save(
            @RequestBody final List<FFGenderEntity> entities) {
        List<Resource<FFGenderEntity>> resources =
                new ArrayList<Resource<FFGenderEntity>>();
        Iterator<FFGenderEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link FFGenderEntity}.
     * @param entity the {@link FFGenderEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFGenderEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<FFGenderEntity>> save(
            @RequestBody final FFGenderEntity entity) {
    
    
        FFGenderEntity savedEntity = repository.save(entity);
        List<Resource<FFGenderEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link FFGenderEntity} instance
     */
    private void setIdFromRepository(final FFGenderEntity entity) {
        List<FFGenderEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = FFGenderEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity FFGenderEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<FFGenderEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = FFGenderEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity FFGenderEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<FFGenderEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity FFGenderEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link FFGenderEntity}s.
     * @param entities the list of {@link FFGenderEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFGenderEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<FFGenderEntity>> update(
            @RequestBody final List<FFGenderEntity> entities) {
        List<Resource<FFGenderEntity>> resources = new ArrayList<Resource<FFGenderEntity>>();
        Iterator<FFGenderEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link FFGenderEntity}.
     * @param entity the {@link FFGenderEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFGenderEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<FFGenderEntity>> update(
            @RequestBody final FFGenderEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        FFGenderEntity savedEntity = repository.save(entity);
        List<Resource<FFGenderEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link FFGenderEntity}s that share a description.
     * @param description the gender' description
     * @return {@link List}<{@link Resource}<{@link FFGenderEntity}>>
     */
    @RequestMapping(path = "description/{description}",
            method = RequestMethod.GET)
    public List<Resource<FFGenderEntity>> getByDescription(
            @PathVariable final String description) {
        Iterator<FFGenderEntity> iter = repository.findByDescription(description)
                .iterator();
        List<Resource<FFGenderEntity>> resources =
                new ArrayList<Resource<FFGenderEntity>>();
        while (iter.hasNext()) {
            resources.add(getGenderResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFGenderEntity}s that share a name.
     * @param name the gender' name
     * @return {@link List}<{@link Resource}<{@link FFGenderEntity}>>
     */
    @RequestMapping(path = "name/{name}",
            method = RequestMethod.GET)
    public List<Resource<FFGenderEntity>> getByName(
            @PathVariable final String name) {
        Iterator<FFGenderEntity> iter = repository.findByName(name)
                .iterator();
        List<Resource<FFGenderEntity>> resources =
                new ArrayList<Resource<FFGenderEntity>>();
        while (iter.hasNext()) {
            resources.add(getGenderResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
