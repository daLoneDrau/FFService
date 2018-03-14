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

import com.osrapi.models.bp.BPScriptActionEntity;

import com.osrapi.repositories.bp.BPScriptActionRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/bp/script_actions")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class BPScriptActionController {
    /** the static instance of {@link BPScriptActionController}. */
    private static BPScriptActionController instance;
    /**
     * Gets the static instance.
     * @return {@link BPScriptActionController}
     */
    public static BPScriptActionController getInstance() {
        if (instance == null) {
            new BPScriptActionController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private BPScriptActionRepository repository;
    /** Creates a new instance of {@link BPScriptActionController}. */
    public BPScriptActionController() {
        instance = this;
    }
    /**
     * Gets a list of {@link BPScriptActionEntity}s.
     * @return {@link List}<{@link Resource}<{@link BPScriptActionEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<BPScriptActionEntity>> getAll() {
        Iterator<BPScriptActionEntity> iter = repository.findAll()
                .iterator();
        List<Resource<BPScriptActionEntity>> resources =
                new ArrayList<Resource<BPScriptActionEntity>>();
        while (iter.hasNext()) {
            resources.add(getScriptActionResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link BPScriptActionEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link BPScriptActionEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<BPScriptActionEntity>> getById(
            @PathVariable final Long id) {
        BPScriptActionEntity entity = repository.findOne(id);
        List<Resource<BPScriptActionEntity>> resources =
                new ArrayList<Resource<BPScriptActionEntity>>();
        resources.add(getScriptActionResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link BPScriptActionEntity}.
     * @param entity the {@link BPScriptActionEntity}
     * @return {@link Resource}<{@link BPScriptActionEntity}>
     */
    private Resource<BPScriptActionEntity> getScriptActionResource(
            final BPScriptActionEntity entity) {
        Resource<BPScriptActionEntity> resource =
                new Resource<BPScriptActionEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link BPScriptActionEntity}s.
     * @param entities the list of {@link BPScriptActionEntity} instances
     * @return {@link List}<{@link Resource}<{@link BPScriptActionEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<BPScriptActionEntity>> save(
            @RequestBody final List<BPScriptActionEntity> entities) {
        List<Resource<BPScriptActionEntity>> resources =
                new ArrayList<Resource<BPScriptActionEntity>>();
        Iterator<BPScriptActionEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link BPScriptActionEntity}.
     * @param entity the {@link BPScriptActionEntity} instance
     * @return {@link List}<{@link Resource}<{@link BPScriptActionEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<BPScriptActionEntity>> save(
            @RequestBody final BPScriptActionEntity entity) {
    
    
        BPScriptActionEntity savedEntity = repository.save(entity);
        List<Resource<BPScriptActionEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link BPScriptActionEntity} instance
     */
    private void setIdFromRepository(final BPScriptActionEntity entity) {
        List<BPScriptActionEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = BPScriptActionEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity BPScriptActionEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<BPScriptActionEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = BPScriptActionEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity BPScriptActionEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<BPScriptActionEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity BPScriptActionEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link BPScriptActionEntity}s.
     * @param entities the list of {@link BPScriptActionEntity} instances
     * @return {@link List}<{@link Resource}<{@link BPScriptActionEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<BPScriptActionEntity>> update(
            @RequestBody final List<BPScriptActionEntity> entities) {
        List<Resource<BPScriptActionEntity>> resources = new ArrayList<Resource<BPScriptActionEntity>>();
        Iterator<BPScriptActionEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link BPScriptActionEntity}.
     * @param entity the {@link BPScriptActionEntity} instance
     * @return {@link List}<{@link Resource}<{@link BPScriptActionEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<BPScriptActionEntity>> update(
            @RequestBody final BPScriptActionEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        BPScriptActionEntity savedEntity = repository.save(entity);
        List<Resource<BPScriptActionEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link BPScriptActionEntity}s that share a name.
     * @param name the script_action' name
     * @return {@link List}<{@link Resource}<{@link BPScriptActionEntity}>>
     */
    @RequestMapping(path = "name/{name}",
            method = RequestMethod.GET)
    public List<Resource<BPScriptActionEntity>> getByName(
            @PathVariable final String name) {
        Iterator<BPScriptActionEntity> iter = repository.findByName(name)
                .iterator();
        List<Resource<BPScriptActionEntity>> resources =
                new ArrayList<Resource<BPScriptActionEntity>>();
        while (iter.hasNext()) {
            resources.add(getScriptActionResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link BPScriptActionEntity}s that share a type.
     * @param type the script_action' type
     * @return {@link List}<{@link Resource}<{@link BPScriptActionEntity}>>
     */
    @RequestMapping(path = "type/{type}",
            method = RequestMethod.GET)
    public List<Resource<BPScriptActionEntity>> getByType(
            @PathVariable final String type) {
        Iterator<BPScriptActionEntity> iter = repository.findByType(type)
                .iterator();
        List<Resource<BPScriptActionEntity>> resources =
                new ArrayList<Resource<BPScriptActionEntity>>();
        while (iter.hasNext()) {
            resources.add(getScriptActionResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
