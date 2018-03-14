package com.osrapi.controllers.bp;

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

import com.osrapi.models.bp.BPGenderEntity;

import com.osrapi.repositories.bp.BPGenderRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/bp/genders")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class BPGenderController {
    /** the static instance of {@link BPGenderController}. */
    private static BPGenderController instance;
    /**
     * Gets the static instance.
     * @return {@link BPGenderController}
     */
    public static BPGenderController getInstance() {
        if (instance == null) {
            new BPGenderController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private BPGenderRepository repository;
    /** Creates a new instance of {@link BPGenderController}. */
    public BPGenderController() {
        instance = this;
    }
    /**
     * Gets a list of {@link BPGenderEntity}s.
     * @return {@link List}<{@link Resource}<{@link BPGenderEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<BPGenderEntity>> getAll() {
        Iterator<BPGenderEntity> iter = repository.findAll()
                .iterator();
        List<Resource<BPGenderEntity>> resources =
                new ArrayList<Resource<BPGenderEntity>>();
        while (iter.hasNext()) {
            resources.add(getGenderResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link BPGenderEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link BPGenderEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<BPGenderEntity>> getById(
            @PathVariable final Long id) {
        BPGenderEntity entity = repository.findOne(id);
        List<Resource<BPGenderEntity>> resources =
                new ArrayList<Resource<BPGenderEntity>>();
        resources.add(getGenderResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link BPGenderEntity}.
     * @param entity the {@link BPGenderEntity}
     * @return {@link Resource}<{@link BPGenderEntity}>
     */
    private Resource<BPGenderEntity> getGenderResource(
            final BPGenderEntity entity) {
        Resource<BPGenderEntity> resource =
                new Resource<BPGenderEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link BPGenderEntity}s.
     * @param entities the list of {@link BPGenderEntity} instances
     * @return {@link List}<{@link Resource}<{@link BPGenderEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<BPGenderEntity>> save(
            @RequestBody final List<BPGenderEntity> entities) {
        List<Resource<BPGenderEntity>> resources =
                new ArrayList<Resource<BPGenderEntity>>();
        Iterator<BPGenderEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link BPGenderEntity}.
     * @param entity the {@link BPGenderEntity} instance
     * @return {@link List}<{@link Resource}<{@link BPGenderEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<BPGenderEntity>> save(
            @RequestBody final BPGenderEntity entity) {
    
    
        BPGenderEntity savedEntity = repository.save(entity);
        List<Resource<BPGenderEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link BPGenderEntity} instance
     */
    private void setIdFromRepository(final BPGenderEntity entity) {
        List<BPGenderEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = BPGenderEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity BPGenderEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<BPGenderEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = BPGenderEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity BPGenderEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<BPGenderEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity BPGenderEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link BPGenderEntity}s.
     * @param entities the list of {@link BPGenderEntity} instances
     * @return {@link List}<{@link Resource}<{@link BPGenderEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<BPGenderEntity>> update(
            @RequestBody final List<BPGenderEntity> entities) {
        List<Resource<BPGenderEntity>> resources = new ArrayList<Resource<BPGenderEntity>>();
        Iterator<BPGenderEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link BPGenderEntity}.
     * @param entity the {@link BPGenderEntity} instance
     * @return {@link List}<{@link Resource}<{@link BPGenderEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<BPGenderEntity>> update(
            @RequestBody final BPGenderEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        BPGenderEntity savedEntity = repository.save(entity);
        List<Resource<BPGenderEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link BPGenderEntity}s that share a description.
     * @param description the gender' description
     * @return {@link List}<{@link Resource}<{@link BPGenderEntity}>>
     */
    @RequestMapping(path = "description/{description}",
            method = RequestMethod.GET)
    public List<Resource<BPGenderEntity>> getByDescription(
            @PathVariable final String description) {
        Iterator<BPGenderEntity> iter = repository.findByDescription(description)
                .iterator();
        List<Resource<BPGenderEntity>> resources =
                new ArrayList<Resource<BPGenderEntity>>();
        while (iter.hasNext()) {
            resources.add(getGenderResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link BPGenderEntity}s that share a name.
     * @param name the gender' name
     * @return {@link List}<{@link Resource}<{@link BPGenderEntity}>>
     */
    @RequestMapping(path = "name/{name}",
            method = RequestMethod.GET)
    public List<Resource<BPGenderEntity>> getByName(
            @PathVariable final String name) {
        Iterator<BPGenderEntity> iter = repository.findByName(name)
                .iterator();
        List<Resource<BPGenderEntity>> resources =
                new ArrayList<Resource<BPGenderEntity>>();
        while (iter.hasNext()) {
            resources.add(getGenderResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
