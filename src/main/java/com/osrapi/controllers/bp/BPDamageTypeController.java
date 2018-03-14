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

import com.osrapi.models.bp.BPDamageTypeEntity;

import com.osrapi.repositories.bp.BPDamageTypeRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/bp/damage_types")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class BPDamageTypeController {
    /** the static instance of {@link BPDamageTypeController}. */
    private static BPDamageTypeController instance;
    /**
     * Gets the static instance.
     * @return {@link BPDamageTypeController}
     */
    public static BPDamageTypeController getInstance() {
        if (instance == null) {
            new BPDamageTypeController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private BPDamageTypeRepository repository;
    /** Creates a new instance of {@link BPDamageTypeController}. */
    public BPDamageTypeController() {
        instance = this;
    }
    /**
     * Gets a list of {@link BPDamageTypeEntity}s.
     * @return {@link List}<{@link Resource}<{@link BPDamageTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<BPDamageTypeEntity>> getAll() {
        Iterator<BPDamageTypeEntity> iter = repository.findAll()
                .iterator();
        List<Resource<BPDamageTypeEntity>> resources =
                new ArrayList<Resource<BPDamageTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getDamageTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link BPDamageTypeEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link BPDamageTypeEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<BPDamageTypeEntity>> getById(
            @PathVariable final Long id) {
        BPDamageTypeEntity entity = repository.findOne(id);
        List<Resource<BPDamageTypeEntity>> resources =
                new ArrayList<Resource<BPDamageTypeEntity>>();
        resources.add(getDamageTypeResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link BPDamageTypeEntity}.
     * @param entity the {@link BPDamageTypeEntity}
     * @return {@link Resource}<{@link BPDamageTypeEntity}>
     */
    private Resource<BPDamageTypeEntity> getDamageTypeResource(
            final BPDamageTypeEntity entity) {
        Resource<BPDamageTypeEntity> resource =
                new Resource<BPDamageTypeEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link BPDamageTypeEntity}s.
     * @param entities the list of {@link BPDamageTypeEntity} instances
     * @return {@link List}<{@link Resource}<{@link BPDamageTypeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<BPDamageTypeEntity>> save(
            @RequestBody final List<BPDamageTypeEntity> entities) {
        List<Resource<BPDamageTypeEntity>> resources =
                new ArrayList<Resource<BPDamageTypeEntity>>();
        Iterator<BPDamageTypeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link BPDamageTypeEntity}.
     * @param entity the {@link BPDamageTypeEntity} instance
     * @return {@link List}<{@link Resource}<{@link BPDamageTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<BPDamageTypeEntity>> save(
            @RequestBody final BPDamageTypeEntity entity) {
    
    
        BPDamageTypeEntity savedEntity = repository.save(entity);
        List<Resource<BPDamageTypeEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link BPDamageTypeEntity} instance
     */
    private void setIdFromRepository(final BPDamageTypeEntity entity) {
        List<BPDamageTypeEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = BPDamageTypeEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity BPDamageTypeEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<BPDamageTypeEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = BPDamageTypeEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity BPDamageTypeEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<BPDamageTypeEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity BPDamageTypeEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link BPDamageTypeEntity}s.
     * @param entities the list of {@link BPDamageTypeEntity} instances
     * @return {@link List}<{@link Resource}<{@link BPDamageTypeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<BPDamageTypeEntity>> update(
            @RequestBody final List<BPDamageTypeEntity> entities) {
        List<Resource<BPDamageTypeEntity>> resources = new ArrayList<Resource<BPDamageTypeEntity>>();
        Iterator<BPDamageTypeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link BPDamageTypeEntity}.
     * @param entity the {@link BPDamageTypeEntity} instance
     * @return {@link List}<{@link Resource}<{@link BPDamageTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<BPDamageTypeEntity>> update(
            @RequestBody final BPDamageTypeEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        BPDamageTypeEntity savedEntity = repository.save(entity);
        List<Resource<BPDamageTypeEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link BPDamageTypeEntity}s that share a flag.
     * @param flag the damage_type' flag
     * @return {@link List}<{@link Resource}<{@link BPDamageTypeEntity}>>
     */
    @RequestMapping(path = "flag/{flag}",
            method = RequestMethod.GET)
    public List<Resource<BPDamageTypeEntity>> getByFlag(
            @PathVariable final Long flag) {
        Iterator<BPDamageTypeEntity> iter = repository.findByFlag(flag)
                .iterator();
        List<Resource<BPDamageTypeEntity>> resources =
                new ArrayList<Resource<BPDamageTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getDamageTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link BPDamageTypeEntity}s that share a name.
     * @param name the damage_type' name
     * @return {@link List}<{@link Resource}<{@link BPDamageTypeEntity}>>
     */
    @RequestMapping(path = "name/{name}",
            method = RequestMethod.GET)
    public List<Resource<BPDamageTypeEntity>> getByName(
            @PathVariable final String name) {
        Iterator<BPDamageTypeEntity> iter = repository.findByName(name)
                .iterator();
        List<Resource<BPDamageTypeEntity>> resources =
                new ArrayList<Resource<BPDamageTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getDamageTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
