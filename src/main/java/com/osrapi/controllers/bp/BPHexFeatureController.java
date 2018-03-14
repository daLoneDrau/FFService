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

import com.osrapi.models.bp.BPHexFeatureEntity;

import com.osrapi.repositories.bp.BPHexFeatureRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/bp/hex_features")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class BPHexFeatureController {
    /** the static instance of {@link BPHexFeatureController}. */
    private static BPHexFeatureController instance;
    /**
     * Gets the static instance.
     * @return {@link BPHexFeatureController}
     */
    public static BPHexFeatureController getInstance() {
        if (instance == null) {
            new BPHexFeatureController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private BPHexFeatureRepository repository;
    /** Creates a new instance of {@link BPHexFeatureController}. */
    public BPHexFeatureController() {
        instance = this;
    }
    /**
     * Gets a list of {@link BPHexFeatureEntity}s.
     * @return {@link List}<{@link Resource}<{@link BPHexFeatureEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<BPHexFeatureEntity>> getAll() {
        Iterator<BPHexFeatureEntity> iter = repository.findAll()
                .iterator();
        List<Resource<BPHexFeatureEntity>> resources =
                new ArrayList<Resource<BPHexFeatureEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexFeatureResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link BPHexFeatureEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link BPHexFeatureEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<BPHexFeatureEntity>> getById(
            @PathVariable final Long id) {
        BPHexFeatureEntity entity = repository.findOne(id);
        List<Resource<BPHexFeatureEntity>> resources =
                new ArrayList<Resource<BPHexFeatureEntity>>();
        resources.add(getHexFeatureResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link BPHexFeatureEntity}.
     * @param entity the {@link BPHexFeatureEntity}
     * @return {@link Resource}<{@link BPHexFeatureEntity}>
     */
    private Resource<BPHexFeatureEntity> getHexFeatureResource(
            final BPHexFeatureEntity entity) {
        Resource<BPHexFeatureEntity> resource =
                new Resource<BPHexFeatureEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link BPHexFeatureEntity}s.
     * @param entities the list of {@link BPHexFeatureEntity} instances
     * @return {@link List}<{@link Resource}<{@link BPHexFeatureEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<BPHexFeatureEntity>> save(
            @RequestBody final List<BPHexFeatureEntity> entities) {
        List<Resource<BPHexFeatureEntity>> resources =
                new ArrayList<Resource<BPHexFeatureEntity>>();
        Iterator<BPHexFeatureEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link BPHexFeatureEntity}.
     * @param entity the {@link BPHexFeatureEntity} instance
     * @return {@link List}<{@link Resource}<{@link BPHexFeatureEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<BPHexFeatureEntity>> save(
            @RequestBody final BPHexFeatureEntity entity) {
    
    
        BPHexFeatureEntity savedEntity = repository.save(entity);
        List<Resource<BPHexFeatureEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link BPHexFeatureEntity} instance
     */
    private void setIdFromRepository(final BPHexFeatureEntity entity) {
        List<BPHexFeatureEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = BPHexFeatureEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity BPHexFeatureEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<BPHexFeatureEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = BPHexFeatureEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity BPHexFeatureEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<BPHexFeatureEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity BPHexFeatureEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link BPHexFeatureEntity}s.
     * @param entities the list of {@link BPHexFeatureEntity} instances
     * @return {@link List}<{@link Resource}<{@link BPHexFeatureEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<BPHexFeatureEntity>> update(
            @RequestBody final List<BPHexFeatureEntity> entities) {
        List<Resource<BPHexFeatureEntity>> resources = new ArrayList<Resource<BPHexFeatureEntity>>();
        Iterator<BPHexFeatureEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link BPHexFeatureEntity}.
     * @param entity the {@link BPHexFeatureEntity} instance
     * @return {@link List}<{@link Resource}<{@link BPHexFeatureEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<BPHexFeatureEntity>> update(
            @RequestBody final BPHexFeatureEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        BPHexFeatureEntity savedEntity = repository.save(entity);
        List<Resource<BPHexFeatureEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link BPHexFeatureEntity}s that share a flag.
     * @param flag the hex_feature' flag
     * @return {@link List}<{@link Resource}<{@link BPHexFeatureEntity}>>
     */
    @RequestMapping(path = "flag/{flag}",
            method = RequestMethod.GET)
    public List<Resource<BPHexFeatureEntity>> getByFlag(
            @PathVariable final Long flag) {
        Iterator<BPHexFeatureEntity> iter = repository.findByFlag(flag)
                .iterator();
        List<Resource<BPHexFeatureEntity>> resources =
                new ArrayList<Resource<BPHexFeatureEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexFeatureResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link BPHexFeatureEntity}s that share a name.
     * @param name the hex_feature' name
     * @return {@link List}<{@link Resource}<{@link BPHexFeatureEntity}>>
     */
    @RequestMapping(path = "name/{name}",
            method = RequestMethod.GET)
    public List<Resource<BPHexFeatureEntity>> getByName(
            @PathVariable final String name) {
        Iterator<BPHexFeatureEntity> iter = repository.findByName(name)
                .iterator();
        List<Resource<BPHexFeatureEntity>> resources =
                new ArrayList<Resource<BPHexFeatureEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexFeatureResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
