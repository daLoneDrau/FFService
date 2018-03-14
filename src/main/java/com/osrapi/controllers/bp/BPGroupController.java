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

import com.osrapi.models.bp.BPGroupEntity;

import com.osrapi.repositories.bp.BPGroupRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/bp/groups")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class BPGroupController {
    /** the static instance of {@link BPGroupController}. */
    private static BPGroupController instance;
    /**
     * Gets the static instance.
     * @return {@link BPGroupController}
     */
    public static BPGroupController getInstance() {
        if (instance == null) {
            new BPGroupController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private BPGroupRepository repository;
    /** Creates a new instance of {@link BPGroupController}. */
    public BPGroupController() {
        instance = this;
    }
    /**
     * Gets a list of {@link BPGroupEntity}s.
     * @return {@link List}<{@link Resource}<{@link BPGroupEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<BPGroupEntity>> getAll() {
        Iterator<BPGroupEntity> iter = repository.findAll()
                .iterator();
        List<Resource<BPGroupEntity>> resources =
                new ArrayList<Resource<BPGroupEntity>>();
        while (iter.hasNext()) {
            resources.add(getGroupResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link BPGroupEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link BPGroupEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<BPGroupEntity>> getById(
            @PathVariable final Long id) {
        BPGroupEntity entity = repository.findOne(id);
        List<Resource<BPGroupEntity>> resources =
                new ArrayList<Resource<BPGroupEntity>>();
        resources.add(getGroupResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link BPGroupEntity}.
     * @param entity the {@link BPGroupEntity}
     * @return {@link Resource}<{@link BPGroupEntity}>
     */
    private Resource<BPGroupEntity> getGroupResource(
            final BPGroupEntity entity) {
        Resource<BPGroupEntity> resource =
                new Resource<BPGroupEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link BPGroupEntity}s.
     * @param entities the list of {@link BPGroupEntity} instances
     * @return {@link List}<{@link Resource}<{@link BPGroupEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<BPGroupEntity>> save(
            @RequestBody final List<BPGroupEntity> entities) {
        List<Resource<BPGroupEntity>> resources =
                new ArrayList<Resource<BPGroupEntity>>();
        Iterator<BPGroupEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link BPGroupEntity}.
     * @param entity the {@link BPGroupEntity} instance
     * @return {@link List}<{@link Resource}<{@link BPGroupEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<BPGroupEntity>> save(
            @RequestBody final BPGroupEntity entity) {
    
    
        BPGroupEntity savedEntity = repository.save(entity);
        List<Resource<BPGroupEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link BPGroupEntity} instance
     */
    private void setIdFromRepository(final BPGroupEntity entity) {
        List<BPGroupEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = BPGroupEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity BPGroupEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<BPGroupEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = BPGroupEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity BPGroupEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<BPGroupEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity BPGroupEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link BPGroupEntity}s.
     * @param entities the list of {@link BPGroupEntity} instances
     * @return {@link List}<{@link Resource}<{@link BPGroupEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<BPGroupEntity>> update(
            @RequestBody final List<BPGroupEntity> entities) {
        List<Resource<BPGroupEntity>> resources = new ArrayList<Resource<BPGroupEntity>>();
        Iterator<BPGroupEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link BPGroupEntity}.
     * @param entity the {@link BPGroupEntity} instance
     * @return {@link List}<{@link Resource}<{@link BPGroupEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<BPGroupEntity>> update(
            @RequestBody final BPGroupEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        BPGroupEntity savedEntity = repository.save(entity);
        List<Resource<BPGroupEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link BPGroupEntity}s that share a name.
     * @param name the group' name
     * @return {@link List}<{@link Resource}<{@link BPGroupEntity}>>
     */
    @RequestMapping(path = "name/{name}",
            method = RequestMethod.GET)
    public List<Resource<BPGroupEntity>> getByName(
            @PathVariable final String name) {
        Iterator<BPGroupEntity> iter = repository.findByName(name)
                .iterator();
        List<Resource<BPGroupEntity>> resources =
                new ArrayList<Resource<BPGroupEntity>>();
        while (iter.hasNext()) {
            resources.add(getGroupResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
