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

import com.osrapi.models.bp.BPEquipmentElementTypeEntity;

import com.osrapi.repositories.bp.BPEquipmentElementTypeRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/bp/equipment_element_types")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class BPEquipmentElementTypeController {
    /** the static instance of {@link BPEquipmentElementTypeController}. */
    private static BPEquipmentElementTypeController instance;
    /**
     * Gets the static instance.
     * @return {@link BPEquipmentElementTypeController}
     */
    public static BPEquipmentElementTypeController getInstance() {
        if (instance == null) {
            new BPEquipmentElementTypeController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private BPEquipmentElementTypeRepository repository;
    /** Creates a new instance of {@link BPEquipmentElementTypeController}. */
    public BPEquipmentElementTypeController() {
        instance = this;
    }
    /**
     * Gets a list of {@link BPEquipmentElementTypeEntity}s.
     * @return {@link List}<{@link Resource}<{@link BPEquipmentElementTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<BPEquipmentElementTypeEntity>> getAll() {
        Iterator<BPEquipmentElementTypeEntity> iter = repository.findAll()
                .iterator();
        List<Resource<BPEquipmentElementTypeEntity>> resources =
                new ArrayList<Resource<BPEquipmentElementTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getEquipmentElementTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link BPEquipmentElementTypeEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link BPEquipmentElementTypeEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<BPEquipmentElementTypeEntity>> getById(
            @PathVariable final Long id) {
        BPEquipmentElementTypeEntity entity = repository.findOne(id);
        List<Resource<BPEquipmentElementTypeEntity>> resources =
                new ArrayList<Resource<BPEquipmentElementTypeEntity>>();
        resources.add(getEquipmentElementTypeResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link BPEquipmentElementTypeEntity}.
     * @param entity the {@link BPEquipmentElementTypeEntity}
     * @return {@link Resource}<{@link BPEquipmentElementTypeEntity}>
     */
    private Resource<BPEquipmentElementTypeEntity> getEquipmentElementTypeResource(
            final BPEquipmentElementTypeEntity entity) {
        Resource<BPEquipmentElementTypeEntity> resource =
                new Resource<BPEquipmentElementTypeEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link BPEquipmentElementTypeEntity}s.
     * @param entities the list of {@link BPEquipmentElementTypeEntity} instances
     * @return {@link List}<{@link Resource}<{@link BPEquipmentElementTypeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<BPEquipmentElementTypeEntity>> save(
            @RequestBody final List<BPEquipmentElementTypeEntity> entities) {
        List<Resource<BPEquipmentElementTypeEntity>> resources =
                new ArrayList<Resource<BPEquipmentElementTypeEntity>>();
        Iterator<BPEquipmentElementTypeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link BPEquipmentElementTypeEntity}.
     * @param entity the {@link BPEquipmentElementTypeEntity} instance
     * @return {@link List}<{@link Resource}<{@link BPEquipmentElementTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<BPEquipmentElementTypeEntity>> save(
            @RequestBody final BPEquipmentElementTypeEntity entity) {
    
    
        BPEquipmentElementTypeEntity savedEntity = repository.save(entity);
        List<Resource<BPEquipmentElementTypeEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link BPEquipmentElementTypeEntity} instance
     */
    private void setIdFromRepository(final BPEquipmentElementTypeEntity entity) {
        List<BPEquipmentElementTypeEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = BPEquipmentElementTypeEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity BPEquipmentElementTypeEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<BPEquipmentElementTypeEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = BPEquipmentElementTypeEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity BPEquipmentElementTypeEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<BPEquipmentElementTypeEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity BPEquipmentElementTypeEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link BPEquipmentElementTypeEntity}s.
     * @param entities the list of {@link BPEquipmentElementTypeEntity} instances
     * @return {@link List}<{@link Resource}<{@link BPEquipmentElementTypeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<BPEquipmentElementTypeEntity>> update(
            @RequestBody final List<BPEquipmentElementTypeEntity> entities) {
        List<Resource<BPEquipmentElementTypeEntity>> resources = new ArrayList<Resource<BPEquipmentElementTypeEntity>>();
        Iterator<BPEquipmentElementTypeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link BPEquipmentElementTypeEntity}.
     * @param entity the {@link BPEquipmentElementTypeEntity} instance
     * @return {@link List}<{@link Resource}<{@link BPEquipmentElementTypeEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<BPEquipmentElementTypeEntity>> update(
            @RequestBody final BPEquipmentElementTypeEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        BPEquipmentElementTypeEntity savedEntity = repository.save(entity);
        List<Resource<BPEquipmentElementTypeEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link BPEquipmentElementTypeEntity}s that share a code.
     * @param code the equipment_element_type' code
     * @return {@link List}<{@link Resource}<{@link BPEquipmentElementTypeEntity}>>
     */
    @RequestMapping(path = "code/{code}",
            method = RequestMethod.GET)
    public List<Resource<BPEquipmentElementTypeEntity>> getByCode(
            @PathVariable final String code) {
        Iterator<BPEquipmentElementTypeEntity> iter = repository.findByCode(code)
                .iterator();
        List<Resource<BPEquipmentElementTypeEntity>> resources =
                new ArrayList<Resource<BPEquipmentElementTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getEquipmentElementTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link BPEquipmentElementTypeEntity}s that share a value.
     * @param value the equipment_element_type' value
     * @return {@link List}<{@link Resource}<{@link BPEquipmentElementTypeEntity}>>
     */
    @RequestMapping(path = "value/{value}",
            method = RequestMethod.GET)
    public List<Resource<BPEquipmentElementTypeEntity>> getByValue(
            @PathVariable final Long value) {
        Iterator<BPEquipmentElementTypeEntity> iter = repository.findByValue(value)
                .iterator();
        List<Resource<BPEquipmentElementTypeEntity>> resources =
                new ArrayList<Resource<BPEquipmentElementTypeEntity>>();
        while (iter.hasNext()) {
            resources.add(getEquipmentElementTypeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
