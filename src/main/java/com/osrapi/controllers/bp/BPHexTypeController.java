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

import com.osrapi.models.bp.BPHexTypeEntity;

import com.osrapi.repositories.bp.BPHexTypeRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/bp/hex_types")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class BPHexTypeController {
    /** the static instance of {@link BPHexTypeController}. */
    private static BPHexTypeController instance;
    /**
     * Gets the static instance.
     * @return {@link BPHexTypeController}
     */
    public static BPHexTypeController getInstance() {
        if (instance == null) {
            new BPHexTypeController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private BPHexTypeRepository repository;
    /** Creates a new instance of {@link BPHexTypeController}. */
    public BPHexTypeController() {
        instance = this;
    }
    /**
     * Gets a list of {@link BPHexTypeEntity}s.
     * @return {@link List}<{@link Resource}<{@link BPHexTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<BPHexTypeEntity>> getAll() {
        Iterator<BPHexTypeEntity> iter = repository.findAll()
                .iterator();
        List<Resource<BPHexTypeEntity>> resources =
                new ArrayList<Resource<BPHexTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link BPHexTypeEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link BPHexTypeEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<BPHexTypeEntity>> getById(
            @PathVariable final Long id) {
        BPHexTypeEntity entity = repository.findOne(id);
        List<Resource<BPHexTypeEntity>> resources =
                new ArrayList<Resource<BPHexTypeEntity>>();
        resources.add(getHexTypeResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link BPHexTypeEntity}.
     * @param entity the {@link BPHexTypeEntity}
     * @return {@link Resource}<{@link BPHexTypeEntity}>
     */
    private Resource<BPHexTypeEntity> getHexTypeResource(
            final BPHexTypeEntity entity) {
        Resource<BPHexTypeEntity> resource =
                new Resource<BPHexTypeEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link BPHexTypeEntity}s.
     * @param entities the list of {@link BPHexTypeEntity} instances
     * @return {@link List}<{@link Resource}<{@link BPHexTypeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<BPHexTypeEntity>> save(
            @RequestBody final List<BPHexTypeEntity> entities) {
        List<Resource<BPHexTypeEntity>> resources =
                new ArrayList<Resource<BPHexTypeEntity>>();
        Iterator<BPHexTypeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link BPHexTypeEntity}.
     * @param entity the {@link BPHexTypeEntity} instance
     * @return {@link List}<{@link Resource}<{@link BPHexTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<BPHexTypeEntity>> save(
            @RequestBody final BPHexTypeEntity entity) {
    
    
        BPHexTypeEntity savedEntity = repository.save(entity);
        List<Resource<BPHexTypeEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link BPHexTypeEntity} instance
     */
    private void setIdFromRepository(final BPHexTypeEntity entity) {
        List<BPHexTypeEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = BPHexTypeEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity BPHexTypeEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<BPHexTypeEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = BPHexTypeEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity BPHexTypeEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<BPHexTypeEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity BPHexTypeEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link BPHexTypeEntity}s.
     * @param entities the list of {@link BPHexTypeEntity} instances
     * @return {@link List}<{@link Resource}<{@link BPHexTypeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<BPHexTypeEntity>> update(
            @RequestBody final List<BPHexTypeEntity> entities) {
        List<Resource<BPHexTypeEntity>> resources = new ArrayList<Resource<BPHexTypeEntity>>();
        Iterator<BPHexTypeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link BPHexTypeEntity}.
     * @param entity the {@link BPHexTypeEntity} instance
     * @return {@link List}<{@link Resource}<{@link BPHexTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<BPHexTypeEntity>> update(
            @RequestBody final BPHexTypeEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        BPHexTypeEntity savedEntity = repository.save(entity);
        List<Resource<BPHexTypeEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link BPHexTypeEntity}s that share a type.
     * @param type the hex_type' type
     * @return {@link List}<{@link Resource}<{@link BPHexTypeEntity}>>
     */
    @RequestMapping(path = "type/{type}",
            method = RequestMethod.GET)
    public List<Resource<BPHexTypeEntity>> getByType(
            @PathVariable final Long type) {
        Iterator<BPHexTypeEntity> iter = repository.findByType(type)
                .iterator();
        List<Resource<BPHexTypeEntity>> resources =
                new ArrayList<Resource<BPHexTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link BPHexTypeEntity}s that share a name.
     * @param name the hex_type' name
     * @return {@link List}<{@link Resource}<{@link BPHexTypeEntity}>>
     */
    @RequestMapping(path = "name/{name}",
            method = RequestMethod.GET)
    public List<Resource<BPHexTypeEntity>> getByName(
            @PathVariable final String name) {
        Iterator<BPHexTypeEntity> iter = repository.findByName(name)
                .iterator();
        List<Resource<BPHexTypeEntity>> resources =
                new ArrayList<Resource<BPHexTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
