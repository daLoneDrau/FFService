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

import com.osrapi.models.ff.FFEquipmentElementTypeEntity;

import com.osrapi.repositories.ff.FFEquipmentElementTypeRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/ff/equipment_element_types")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FFEquipmentElementTypeController {
    /** the static instance of {@link FFEquipmentElementTypeController}. */
    private static FFEquipmentElementTypeController instance;
    /**
     * Gets the static instance.
     * @return {@link FFEquipmentElementTypeController}
     */
    public static FFEquipmentElementTypeController getInstance() {
        if (instance == null) {
            new FFEquipmentElementTypeController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private FFEquipmentElementTypeRepository repository;
    /** Creates a new instance of {@link FFEquipmentElementTypeController}. */
    public FFEquipmentElementTypeController() {
        instance = this;
    }
    /**
     * Gets a list of {@link FFEquipmentElementTypeEntity}s.
     * @return {@link List}<{@link Resource}<{@link FFEquipmentElementTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<FFEquipmentElementTypeEntity>> getAll() {
        Iterator<FFEquipmentElementTypeEntity> iter = repository.findAll()
                .iterator();
        List<Resource<FFEquipmentElementTypeEntity>> resources =
                new ArrayList<Resource<FFEquipmentElementTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getEquipmentElementTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link FFEquipmentElementTypeEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link FFEquipmentElementTypeEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<FFEquipmentElementTypeEntity>> getById(
            @PathVariable final Long id) {
        FFEquipmentElementTypeEntity entity = repository.findOne(id);
        List<Resource<FFEquipmentElementTypeEntity>> resources =
                new ArrayList<Resource<FFEquipmentElementTypeEntity>>();
        resources.add(getEquipmentElementTypeResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link FFEquipmentElementTypeEntity}.
     * @param entity the {@link FFEquipmentElementTypeEntity}
     * @return {@link Resource}<{@link FFEquipmentElementTypeEntity}>
     */
    private Resource<FFEquipmentElementTypeEntity> getEquipmentElementTypeResource(
            final FFEquipmentElementTypeEntity entity) {
        Resource<FFEquipmentElementTypeEntity> resource =
                new Resource<FFEquipmentElementTypeEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link FFEquipmentElementTypeEntity}s.
     * @param entities the list of {@link FFEquipmentElementTypeEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFEquipmentElementTypeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<FFEquipmentElementTypeEntity>> save(
            @RequestBody final List<FFEquipmentElementTypeEntity> entities) {
        List<Resource<FFEquipmentElementTypeEntity>> resources =
                new ArrayList<Resource<FFEquipmentElementTypeEntity>>();
        Iterator<FFEquipmentElementTypeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link FFEquipmentElementTypeEntity}.
     * @param entity the {@link FFEquipmentElementTypeEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFEquipmentElementTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<FFEquipmentElementTypeEntity>> save(
            @RequestBody final FFEquipmentElementTypeEntity entity) {
    
    
        FFEquipmentElementTypeEntity savedEntity = repository.save(entity);
        List<Resource<FFEquipmentElementTypeEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link FFEquipmentElementTypeEntity} instance
     */
    private void setIdFromRepository(final FFEquipmentElementTypeEntity entity) {
        List<FFEquipmentElementTypeEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = FFEquipmentElementTypeEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<FFEquipmentElementTypeEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = FFEquipmentElementTypeEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<FFEquipmentElementTypeEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
            e.printStackTrace();
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link FFEquipmentElementTypeEntity}s.
     * @param entities the list of {@link FFEquipmentElementTypeEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFEquipmentElementTypeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<FFEquipmentElementTypeEntity>> update(
            @RequestBody final List<FFEquipmentElementTypeEntity> entities) {
        List<Resource<FFEquipmentElementTypeEntity>> resources = new ArrayList<Resource<FFEquipmentElementTypeEntity>>();
        Iterator<FFEquipmentElementTypeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link FFEquipmentElementTypeEntity}.
     * @param entity the {@link FFEquipmentElementTypeEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFEquipmentElementTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<FFEquipmentElementTypeEntity>> update(
            @RequestBody final FFEquipmentElementTypeEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        FFEquipmentElementTypeEntity savedEntity = repository.save(entity);
        List<Resource<FFEquipmentElementTypeEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link FFEquipmentElementTypeEntity}s that share a code.
     * @param code the equipment_element_type' code
     * @return {@link List}<{@link Resource}<{@link FFEquipmentElementTypeEntity}>>
     */
    @RequestMapping(path = "code/{code}",
            method = RequestMethod.GET)
    public List<Resource<FFEquipmentElementTypeEntity>> getByCode(
            @PathVariable final String code) {
        Iterator<FFEquipmentElementTypeEntity> iter = repository.findByCode(code)
                .iterator();
        List<Resource<FFEquipmentElementTypeEntity>> resources =
                new ArrayList<Resource<FFEquipmentElementTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getEquipmentElementTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFEquipmentElementTypeEntity}s that share a value.
     * @param value the equipment_element_type' value
     * @return {@link List}<{@link Resource}<{@link FFEquipmentElementTypeEntity}>>
     */
    @RequestMapping(path = "value/{value}",
            method = RequestMethod.GET)
    public List<Resource<FFEquipmentElementTypeEntity>> getByValue(
            @PathVariable final Long value) {
        Iterator<FFEquipmentElementTypeEntity> iter = repository.findByValue(value)
                .iterator();
        List<Resource<FFEquipmentElementTypeEntity>> resources =
                new ArrayList<Resource<FFEquipmentElementTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getEquipmentElementTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
