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

import com.osrapi.models.ff.FFEquipmentSlotEntity;

import com.osrapi.repositories.ff.FFEquipmentSlotRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/ff/equipment_slots")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FFEquipmentSlotController {
    /** the static instance of {@link FFEquipmentSlotController}. */
    private static FFEquipmentSlotController instance;
    /**
     * Gets the static instance.
     * @return {@link FFEquipmentSlotController}
     */
    public static FFEquipmentSlotController getInstance() {
        if (instance == null) {
            new FFEquipmentSlotController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private FFEquipmentSlotRepository repository;
    /** Creates a new instance of {@link FFEquipmentSlotController}. */
    public FFEquipmentSlotController() {
        instance = this;
    }
    /**
     * Gets a list of {@link FFEquipmentSlotEntity}s.
     * @return {@link List}<{@link Resource}<{@link FFEquipmentSlotEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<FFEquipmentSlotEntity>> getAll() {
        Iterator<FFEquipmentSlotEntity> iter = repository.findAll()
                .iterator();
        List<Resource<FFEquipmentSlotEntity>> resources =
                new ArrayList<Resource<FFEquipmentSlotEntity>>();
        while (iter.hasNext()) {
            resources.add(getEquipmentSlotResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link FFEquipmentSlotEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link FFEquipmentSlotEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<FFEquipmentSlotEntity>> getById(
            @PathVariable final Long id) {
        FFEquipmentSlotEntity entity = repository.findOne(id);
        List<Resource<FFEquipmentSlotEntity>> resources =
                new ArrayList<Resource<FFEquipmentSlotEntity>>();
        resources.add(getEquipmentSlotResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link FFEquipmentSlotEntity}.
     * @param entity the {@link FFEquipmentSlotEntity}
     * @return {@link Resource}<{@link FFEquipmentSlotEntity}>
     */
    private Resource<FFEquipmentSlotEntity> getEquipmentSlotResource(
            final FFEquipmentSlotEntity entity) {
        Resource<FFEquipmentSlotEntity> resource =
                new Resource<FFEquipmentSlotEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link FFEquipmentSlotEntity}s.
     * @param entities the list of {@link FFEquipmentSlotEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFEquipmentSlotEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<FFEquipmentSlotEntity>> save(
            @RequestBody final List<FFEquipmentSlotEntity> entities) {
        List<Resource<FFEquipmentSlotEntity>> resources =
                new ArrayList<Resource<FFEquipmentSlotEntity>>();
        Iterator<FFEquipmentSlotEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link FFEquipmentSlotEntity}.
     * @param entity the {@link FFEquipmentSlotEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFEquipmentSlotEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<FFEquipmentSlotEntity>> save(
            @RequestBody final FFEquipmentSlotEntity entity) {
    
    
        FFEquipmentSlotEntity savedEntity = repository.save(entity);
        List<Resource<FFEquipmentSlotEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link FFEquipmentSlotEntity} instance
     */
    private void setIdFromRepository(final FFEquipmentSlotEntity entity) {
        List<FFEquipmentSlotEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = FFEquipmentSlotEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity FFEquipmentSlotEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<FFEquipmentSlotEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = FFEquipmentSlotEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity FFEquipmentSlotEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<FFEquipmentSlotEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity FFEquipmentSlotEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link FFEquipmentSlotEntity}s.
     * @param entities the list of {@link FFEquipmentSlotEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFEquipmentSlotEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<FFEquipmentSlotEntity>> update(
            @RequestBody final List<FFEquipmentSlotEntity> entities) {
        List<Resource<FFEquipmentSlotEntity>> resources = new ArrayList<Resource<FFEquipmentSlotEntity>>();
        Iterator<FFEquipmentSlotEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link FFEquipmentSlotEntity}.
     * @param entity the {@link FFEquipmentSlotEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFEquipmentSlotEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<FFEquipmentSlotEntity>> update(
            @RequestBody final FFEquipmentSlotEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        FFEquipmentSlotEntity savedEntity = repository.save(entity);
        List<Resource<FFEquipmentSlotEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link FFEquipmentSlotEntity}s that share a name.
     * @param name the equipment_slot' name
     * @return {@link List}<{@link Resource}<{@link FFEquipmentSlotEntity}>>
     */
    @RequestMapping(path = "name/{name}",
            method = RequestMethod.GET)
    public List<Resource<FFEquipmentSlotEntity>> getByName(
            @PathVariable final String name) {
        Iterator<FFEquipmentSlotEntity> iter = repository.findByName(name)
                .iterator();
        List<Resource<FFEquipmentSlotEntity>> resources =
                new ArrayList<Resource<FFEquipmentSlotEntity>>();
        while (iter.hasNext()) {
            resources.add(getEquipmentSlotResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFEquipmentSlotEntity}s that share a value.
     * @param value the equipment_slot' value
     * @return {@link List}<{@link Resource}<{@link FFEquipmentSlotEntity}>>
     */
    @RequestMapping(path = "value/{value}",
            method = RequestMethod.GET)
    public List<Resource<FFEquipmentSlotEntity>> getByValue(
            @PathVariable final Long value) {
        Iterator<FFEquipmentSlotEntity> iter = repository.findByValue(value)
                .iterator();
        List<Resource<FFEquipmentSlotEntity>> resources =
                new ArrayList<Resource<FFEquipmentSlotEntity>>();
        while (iter.hasNext()) {
            resources.add(getEquipmentSlotResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
