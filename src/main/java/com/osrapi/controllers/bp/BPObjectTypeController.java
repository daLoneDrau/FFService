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

import com.osrapi.models.bp.BPObjectTypeEntity;

import com.osrapi.repositories.bp.BPObjectTypeRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/bp/object_types")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class BPObjectTypeController {
    /** the static instance of {@link BPObjectTypeController}. */
    private static BPObjectTypeController instance;
    /**
     * Gets the static instance.
     * @return {@link BPObjectTypeController}
     */
    public static BPObjectTypeController getInstance() {
        if (instance == null) {
            new BPObjectTypeController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private BPObjectTypeRepository repository;
    /** Creates a new instance of {@link BPObjectTypeController}. */
    public BPObjectTypeController() {
        instance = this;
    }
    /**
     * Gets a list of {@link BPObjectTypeEntity}s.
     * @return {@link List}<{@link Resource}<{@link BPObjectTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<BPObjectTypeEntity>> getAll() {
        Iterator<BPObjectTypeEntity> iter = repository.findAll()
                .iterator();
        List<Resource<BPObjectTypeEntity>> resources =
                new ArrayList<Resource<BPObjectTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getObjectTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link BPObjectTypeEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link BPObjectTypeEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<BPObjectTypeEntity>> getById(
            @PathVariable final Long id) {
        BPObjectTypeEntity entity = repository.findOne(id);
        List<Resource<BPObjectTypeEntity>> resources =
                new ArrayList<Resource<BPObjectTypeEntity>>();
        resources.add(getObjectTypeResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link BPObjectTypeEntity}.
     * @param entity the {@link BPObjectTypeEntity}
     * @return {@link Resource}<{@link BPObjectTypeEntity}>
     */
    private Resource<BPObjectTypeEntity> getObjectTypeResource(
            final BPObjectTypeEntity entity) {
        Resource<BPObjectTypeEntity> resource =
                new Resource<BPObjectTypeEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link BPObjectTypeEntity}s.
     * @param entities the list of {@link BPObjectTypeEntity} instances
     * @return {@link List}<{@link Resource}<{@link BPObjectTypeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<BPObjectTypeEntity>> save(
            @RequestBody final List<BPObjectTypeEntity> entities) {
        List<Resource<BPObjectTypeEntity>> resources =
                new ArrayList<Resource<BPObjectTypeEntity>>();
        Iterator<BPObjectTypeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link BPObjectTypeEntity}.
     * @param entity the {@link BPObjectTypeEntity} instance
     * @return {@link List}<{@link Resource}<{@link BPObjectTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<BPObjectTypeEntity>> save(
            @RequestBody final BPObjectTypeEntity entity) {
    
    
        BPObjectTypeEntity savedEntity = repository.save(entity);
        List<Resource<BPObjectTypeEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link BPObjectTypeEntity} instance
     */
    private void setIdFromRepository(final BPObjectTypeEntity entity) {
        List<BPObjectTypeEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = BPObjectTypeEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity BPObjectTypeEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<BPObjectTypeEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = BPObjectTypeEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity BPObjectTypeEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<BPObjectTypeEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity BPObjectTypeEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link BPObjectTypeEntity}s.
     * @param entities the list of {@link BPObjectTypeEntity} instances
     * @return {@link List}<{@link Resource}<{@link BPObjectTypeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<BPObjectTypeEntity>> update(
            @RequestBody final List<BPObjectTypeEntity> entities) {
        List<Resource<BPObjectTypeEntity>> resources = new ArrayList<Resource<BPObjectTypeEntity>>();
        Iterator<BPObjectTypeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link BPObjectTypeEntity}.
     * @param entity the {@link BPObjectTypeEntity} instance
     * @return {@link List}<{@link Resource}<{@link BPObjectTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<BPObjectTypeEntity>> update(
            @RequestBody final BPObjectTypeEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        BPObjectTypeEntity savedEntity = repository.save(entity);
        List<Resource<BPObjectTypeEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link BPObjectTypeEntity}s that share a code.
     * @param code the object_type' code
     * @return {@link List}<{@link Resource}<{@link BPObjectTypeEntity}>>
     */
    @RequestMapping(path = "code/{code}",
            method = RequestMethod.GET)
    public List<Resource<BPObjectTypeEntity>> getByCode(
            @PathVariable final String code) {
        Iterator<BPObjectTypeEntity> iter = repository.findByCode(code)
                .iterator();
        List<Resource<BPObjectTypeEntity>> resources =
                new ArrayList<Resource<BPObjectTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getObjectTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link BPObjectTypeEntity}s that share a flag.
     * @param flag the object_type' flag
     * @return {@link List}<{@link Resource}<{@link BPObjectTypeEntity}>>
     */
    @RequestMapping(path = "flag/{flag}",
            method = RequestMethod.GET)
    public List<Resource<BPObjectTypeEntity>> getByFlag(
            @PathVariable final Long flag) {
        Iterator<BPObjectTypeEntity> iter = repository.findByFlag(flag)
                .iterator();
        List<Resource<BPObjectTypeEntity>> resources =
                new ArrayList<Resource<BPObjectTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getObjectTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
