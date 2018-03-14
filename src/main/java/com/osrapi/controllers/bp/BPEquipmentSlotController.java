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

import com.osrapi.models.bp.BPEquipmentSlotEntity;

import com.osrapi.repositories.bp.BPEquipmentSlotRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/bp/equipment_slots")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class BPEquipmentSlotController {
    /** the static instance of {@link BPEquipmentSlotController}. */
    private static BPEquipmentSlotController instance;
    /**
     * Gets the static instance.
     * @return {@link BPEquipmentSlotController}
     */
    public static BPEquipmentSlotController getInstance() {
        if (instance == null) {
            new BPEquipmentSlotController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private BPEquipmentSlotRepository repository;
    /** Creates a new instance of {@link BPEquipmentSlotController}. */
    public BPEquipmentSlotController() {
        instance = this;
    }
    /**
     * Gets a list of {@link BPEquipmentSlotEntity}s.
     * @return {@link List}<{@link Resource}<{@link BPEquipmentSlotEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<BPEquipmentSlotEntity>> getAll() {
        Iterator<BPEquipmentSlotEntity> iter = repository.findAll()
                .iterator();
        List<Resource<BPEquipmentSlotEntity>> resources =
                new ArrayList<Resource<BPEquipmentSlotEntity>>();
        while (iter.hasNext()) {
            resources.add(getEquipmentSlotResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link BPEquipmentSlotEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link BPEquipmentSlotEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<BPEquipmentSlotEntity>> getById(
            @PathVariable final Long id) {
        BPEquipmentSlotEntity entity = repository.findOne(id);
        List<Resource<BPEquipmentSlotEntity>> resources =
                new ArrayList<Resource<BPEquipmentSlotEntity>>();
        resources.add(getEquipmentSlotResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link BPEquipmentSlotEntity}.
     * @param entity the {@link BPEquipmentSlotEntity}
     * @return {@link Resource}<{@link BPEquipmentSlotEntity}>
     */
    private Resource<BPEquipmentSlotEntity> getEquipmentSlotResource(
            final BPEquipmentSlotEntity entity) {
        Resource<BPEquipmentSlotEntity> resource =
                new Resource<BPEquipmentSlotEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link BPEquipmentSlotEntity}s.
     * @param entities the list of {@link BPEquipmentSlotEntity} instances
     * @return {@link List}<{@link Resource}<{@link BPEquipmentSlotEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<BPEquipmentSlotEntity>> save(
            @RequestBody final List<BPEquipmentSlotEntity> entities) {
        List<Resource<BPEquipmentSlotEntity>> resources =
                new ArrayList<Resource<BPEquipmentSlotEntity>>();
        Iterator<BPEquipmentSlotEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link BPEquipmentSlotEntity}.
     * @param entity the {@link BPEquipmentSlotEntity} instance
     * @return {@link List}<{@link Resource}<{@link BPEquipmentSlotEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<BPEquipmentSlotEntity>> save(
            @RequestBody final BPEquipmentSlotEntity entity) {
    
    
        BPEquipmentSlotEntity savedEntity = repository.save(entity);
        List<Resource<BPEquipmentSlotEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link BPEquipmentSlotEntity} instance
     */
    private void setIdFromRepository(final BPEquipmentSlotEntity entity) {
        List<BPEquipmentSlotEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = BPEquipmentSlotEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity BPEquipmentSlotEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<BPEquipmentSlotEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = BPEquipmentSlotEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity BPEquipmentSlotEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<BPEquipmentSlotEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity BPEquipmentSlotEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link BPEquipmentSlotEntity}s.
     * @param entities the list of {@link BPEquipmentSlotEntity} instances
     * @return {@link List}<{@link Resource}<{@link BPEquipmentSlotEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<BPEquipmentSlotEntity>> update(
            @RequestBody final List<BPEquipmentSlotEntity> entities) {
        List<Resource<BPEquipmentSlotEntity>> resources = new ArrayList<Resource<BPEquipmentSlotEntity>>();
        Iterator<BPEquipmentSlotEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link BPEquipmentSlotEntity}.
     * @param entity the {@link BPEquipmentSlotEntity} instance
     * @return {@link List}<{@link Resource}<{@link BPEquipmentSlotEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<BPEquipmentSlotEntity>> update(
            @RequestBody final BPEquipmentSlotEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        BPEquipmentSlotEntity savedEntity = repository.save(entity);
        List<Resource<BPEquipmentSlotEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link BPEquipmentSlotEntity}s that share a name.
     * @param name the equipment_slot' name
     * @return {@link List}<{@link Resource}<{@link BPEquipmentSlotEntity}>>
     */
    @RequestMapping(path = "name/{name}",
            method = RequestMethod.GET)
    public List<Resource<BPEquipmentSlotEntity>> getByName(
            @PathVariable final String name) {
        Iterator<BPEquipmentSlotEntity> iter = repository.findByName(name)
                .iterator();
        List<Resource<BPEquipmentSlotEntity>> resources =
                new ArrayList<Resource<BPEquipmentSlotEntity>>();
        while (iter.hasNext()) {
            resources.add(getEquipmentSlotResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link BPEquipmentSlotEntity}s that share a val.
     * @param val the equipment_slot' val
     * @return {@link List}<{@link Resource}<{@link BPEquipmentSlotEntity}>>
     */
    @RequestMapping(path = "val/{val}",
            method = RequestMethod.GET)
    public List<Resource<BPEquipmentSlotEntity>> getByVal(
            @PathVariable final Long val) {
        Iterator<BPEquipmentSlotEntity> iter = repository.findByVal(val)
                .iterator();
        List<Resource<BPEquipmentSlotEntity>> resources =
                new ArrayList<Resource<BPEquipmentSlotEntity>>();
        while (iter.hasNext()) {
            resources.add(getEquipmentSlotResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
