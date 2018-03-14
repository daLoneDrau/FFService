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

import com.osrapi.models.bp.BPScriptActionTypeEntity;

import com.osrapi.repositories.bp.BPScriptActionTypeRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/bp/script_action_types")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class BPScriptActionTypeController {
    /** the static instance of {@link BPScriptActionTypeController}. */
    private static BPScriptActionTypeController instance;
    /**
     * Gets the static instance.
     * @return {@link BPScriptActionTypeController}
     */
    public static BPScriptActionTypeController getInstance() {
        if (instance == null) {
            new BPScriptActionTypeController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private BPScriptActionTypeRepository repository;
    /** Creates a new instance of {@link BPScriptActionTypeController}. */
    public BPScriptActionTypeController() {
        instance = this;
    }
    /**
     * Gets a list of {@link BPScriptActionTypeEntity}s.
     * @return {@link List}<{@link Resource}<{@link BPScriptActionTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<BPScriptActionTypeEntity>> getAll() {
        Iterator<BPScriptActionTypeEntity> iter = repository.findAll()
                .iterator();
        List<Resource<BPScriptActionTypeEntity>> resources =
                new ArrayList<Resource<BPScriptActionTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getScriptActionTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link BPScriptActionTypeEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link BPScriptActionTypeEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<BPScriptActionTypeEntity>> getById(
            @PathVariable final Long id) {
        BPScriptActionTypeEntity entity = repository.findOne(id);
        List<Resource<BPScriptActionTypeEntity>> resources =
                new ArrayList<Resource<BPScriptActionTypeEntity>>();
        resources.add(getScriptActionTypeResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link BPScriptActionTypeEntity}.
     * @param entity the {@link BPScriptActionTypeEntity}
     * @return {@link Resource}<{@link BPScriptActionTypeEntity}>
     */
    private Resource<BPScriptActionTypeEntity> getScriptActionTypeResource(
            final BPScriptActionTypeEntity entity) {
        Resource<BPScriptActionTypeEntity> resource =
                new Resource<BPScriptActionTypeEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link BPScriptActionTypeEntity}s.
     * @param entities the list of {@link BPScriptActionTypeEntity} instances
     * @return {@link List}<{@link Resource}<{@link BPScriptActionTypeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<BPScriptActionTypeEntity>> save(
            @RequestBody final List<BPScriptActionTypeEntity> entities) {
        List<Resource<BPScriptActionTypeEntity>> resources =
                new ArrayList<Resource<BPScriptActionTypeEntity>>();
        Iterator<BPScriptActionTypeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link BPScriptActionTypeEntity}.
     * @param entity the {@link BPScriptActionTypeEntity} instance
     * @return {@link List}<{@link Resource}<{@link BPScriptActionTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<BPScriptActionTypeEntity>> save(
            @RequestBody final BPScriptActionTypeEntity entity) {
    
    
        BPScriptActionTypeEntity savedEntity = repository.save(entity);
        List<Resource<BPScriptActionTypeEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link BPScriptActionTypeEntity} instance
     */
    private void setIdFromRepository(final BPScriptActionTypeEntity entity) {
        List<BPScriptActionTypeEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = BPScriptActionTypeEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity BPScriptActionTypeEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<BPScriptActionTypeEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = BPScriptActionTypeEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity BPScriptActionTypeEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<BPScriptActionTypeEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity BPScriptActionTypeEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link BPScriptActionTypeEntity}s.
     * @param entities the list of {@link BPScriptActionTypeEntity} instances
     * @return {@link List}<{@link Resource}<{@link BPScriptActionTypeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<BPScriptActionTypeEntity>> update(
            @RequestBody final List<BPScriptActionTypeEntity> entities) {
        List<Resource<BPScriptActionTypeEntity>> resources = new ArrayList<Resource<BPScriptActionTypeEntity>>();
        Iterator<BPScriptActionTypeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link BPScriptActionTypeEntity}.
     * @param entity the {@link BPScriptActionTypeEntity} instance
     * @return {@link List}<{@link Resource}<{@link BPScriptActionTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<BPScriptActionTypeEntity>> update(
            @RequestBody final BPScriptActionTypeEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        BPScriptActionTypeEntity savedEntity = repository.save(entity);
        List<Resource<BPScriptActionTypeEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link BPScriptActionTypeEntity}s that share a code.
     * @param code the script_action_type' code
     * @return {@link List}<{@link Resource}<{@link BPScriptActionTypeEntity}>>
     */
    @RequestMapping(path = "code/{code}",
            method = RequestMethod.GET)
    public List<Resource<BPScriptActionTypeEntity>> getByCode(
            @PathVariable final String code) {
        Iterator<BPScriptActionTypeEntity> iter = repository.findByCode(code)
                .iterator();
        List<Resource<BPScriptActionTypeEntity>> resources =
                new ArrayList<Resource<BPScriptActionTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getScriptActionTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
