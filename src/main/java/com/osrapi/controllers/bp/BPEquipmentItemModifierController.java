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

import com.osrapi.models.bp.BPEquipmentItemModifierEntity;

import com.osrapi.repositories.bp.BPEquipmentItemModifierRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/bp/equipment_item_modifiers")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class BPEquipmentItemModifierController {
    /** the static instance of {@link BPEquipmentItemModifierController}. */
    private static BPEquipmentItemModifierController instance;
    /**
     * Gets the static instance.
     * @return {@link BPEquipmentItemModifierController}
     */
    public static BPEquipmentItemModifierController getInstance() {
        if (instance == null) {
            new BPEquipmentItemModifierController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private BPEquipmentItemModifierRepository repository;
    /** Creates a new instance of {@link BPEquipmentItemModifierController}. */
    public BPEquipmentItemModifierController() {
        instance = this;
    }
    /**
     * Gets a list of {@link BPEquipmentItemModifierEntity}s.
     * @return {@link List}<{@link Resource}<{@link BPEquipmentItemModifierEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<BPEquipmentItemModifierEntity>> getAll() {
        Iterator<BPEquipmentItemModifierEntity> iter = repository.findAll()
                .iterator();
        List<Resource<BPEquipmentItemModifierEntity>> resources =
                new ArrayList<Resource<BPEquipmentItemModifierEntity>>();
        while (iter.hasNext()) {
            resources.add(getEquipmentItemModifierResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link BPEquipmentItemModifierEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link BPEquipmentItemModifierEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<BPEquipmentItemModifierEntity>> getById(
            @PathVariable final Long id) {
        BPEquipmentItemModifierEntity entity = repository.findOne(id);
        List<Resource<BPEquipmentItemModifierEntity>> resources =
                new ArrayList<Resource<BPEquipmentItemModifierEntity>>();
        resources.add(getEquipmentItemModifierResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link BPEquipmentItemModifierEntity}.
     * @param entity the {@link BPEquipmentItemModifierEntity}
     * @return {@link Resource}<{@link BPEquipmentItemModifierEntity}>
     */
    private Resource<BPEquipmentItemModifierEntity> getEquipmentItemModifierResource(
            final BPEquipmentItemModifierEntity entity) {
        Resource<BPEquipmentItemModifierEntity> resource =
                new Resource<BPEquipmentItemModifierEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link BPEquipmentItemModifierEntity}s.
     * @param entities the list of {@link BPEquipmentItemModifierEntity} instances
     * @return {@link List}<{@link Resource}<{@link BPEquipmentItemModifierEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<BPEquipmentItemModifierEntity>> save(
            @RequestBody final List<BPEquipmentItemModifierEntity> entities) {
        List<Resource<BPEquipmentItemModifierEntity>> resources =
                new ArrayList<Resource<BPEquipmentItemModifierEntity>>();
        Iterator<BPEquipmentItemModifierEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link BPEquipmentItemModifierEntity}.
     * @param entity the {@link BPEquipmentItemModifierEntity} instance
     * @return {@link List}<{@link Resource}<{@link BPEquipmentItemModifierEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<BPEquipmentItemModifierEntity>> save(
            @RequestBody final BPEquipmentItemModifierEntity entity) {
    
    
        BPEquipmentItemModifierEntity savedEntity = repository.save(entity);
        List<Resource<BPEquipmentItemModifierEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link BPEquipmentItemModifierEntity} instance
     */
    private void setIdFromRepository(final BPEquipmentItemModifierEntity entity) {
        List<BPEquipmentItemModifierEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = BPEquipmentItemModifierEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity BPEquipmentItemModifierEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<BPEquipmentItemModifierEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = BPEquipmentItemModifierEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity BPEquipmentItemModifierEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<BPEquipmentItemModifierEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity BPEquipmentItemModifierEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link BPEquipmentItemModifierEntity}s.
     * @param entities the list of {@link BPEquipmentItemModifierEntity} instances
     * @return {@link List}<{@link Resource}<{@link BPEquipmentItemModifierEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<BPEquipmentItemModifierEntity>> update(
            @RequestBody final List<BPEquipmentItemModifierEntity> entities) {
        List<Resource<BPEquipmentItemModifierEntity>> resources = new ArrayList<Resource<BPEquipmentItemModifierEntity>>();
        Iterator<BPEquipmentItemModifierEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link BPEquipmentItemModifierEntity}.
     * @param entity the {@link BPEquipmentItemModifierEntity} instance
     * @return {@link List}<{@link Resource}<{@link BPEquipmentItemModifierEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<BPEquipmentItemModifierEntity>> update(
            @RequestBody final BPEquipmentItemModifierEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        BPEquipmentItemModifierEntity savedEntity = repository.save(entity);
        List<Resource<BPEquipmentItemModifierEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link BPEquipmentItemModifierEntity}s that share a code.
     * @param code the equipment_item_modifier' code
     * @return {@link List}<{@link Resource}<{@link BPEquipmentItemModifierEntity}>>
     */
    @RequestMapping(path = "code/{code}",
            method = RequestMethod.GET)
    public List<Resource<BPEquipmentItemModifierEntity>> getByCode(
            @PathVariable final String code) {
        Iterator<BPEquipmentItemModifierEntity> iter = repository.findByCode(code)
                .iterator();
        List<Resource<BPEquipmentItemModifierEntity>> resources =
                new ArrayList<Resource<BPEquipmentItemModifierEntity>>();
        while (iter.hasNext()) {
            resources.add(getEquipmentItemModifierResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link BPEquipmentItemModifierEntity}s that share a percent.
     * @param percent the equipment_item_modifier' percent
     * @return {@link List}<{@link Resource}<{@link BPEquipmentItemModifierEntity}>>
     */
    @RequestMapping(path = "percent/{percent}",
            method = RequestMethod.GET)
    public List<Resource<BPEquipmentItemModifierEntity>> getByPercent(
            @PathVariable final Boolean percent) {
        Iterator<BPEquipmentItemModifierEntity> iter = repository.findByPercent(percent)
                .iterator();
        List<Resource<BPEquipmentItemModifierEntity>> resources =
                new ArrayList<Resource<BPEquipmentItemModifierEntity>>();
        while (iter.hasNext()) {
            resources.add(getEquipmentItemModifierResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link BPEquipmentItemModifierEntity}s that share a special.
     * @param special the equipment_item_modifier' special
     * @return {@link List}<{@link Resource}<{@link BPEquipmentItemModifierEntity}>>
     */
    @RequestMapping(path = "special/{special}",
            method = RequestMethod.GET)
    public List<Resource<BPEquipmentItemModifierEntity>> getBySpecial(
            @PathVariable final Long special) {
        Iterator<BPEquipmentItemModifierEntity> iter = repository.findBySpecial(special)
                .iterator();
        List<Resource<BPEquipmentItemModifierEntity>> resources =
                new ArrayList<Resource<BPEquipmentItemModifierEntity>>();
        while (iter.hasNext()) {
            resources.add(getEquipmentItemModifierResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link BPEquipmentItemModifierEntity}s that share a value.
     * @param value the equipment_item_modifier' value
     * @return {@link List}<{@link Resource}<{@link BPEquipmentItemModifierEntity}>>
     */
    @RequestMapping(path = "value/{value}",
            method = RequestMethod.GET)
    public List<Resource<BPEquipmentItemModifierEntity>> getByValue(
            @PathVariable final float value) {
        Iterator<BPEquipmentItemModifierEntity> iter = repository.findByValue(value)
                .iterator();
        List<Resource<BPEquipmentItemModifierEntity>> resources =
                new ArrayList<Resource<BPEquipmentItemModifierEntity>>();
        while (iter.hasNext()) {
            resources.add(getEquipmentItemModifierResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
