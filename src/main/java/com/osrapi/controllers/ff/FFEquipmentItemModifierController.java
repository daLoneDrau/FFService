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

import com.osrapi.models.ff.FFEquipmentItemModifierEntity;

import com.osrapi.repositories.ff.FFEquipmentItemModifierRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/ff/equipment_item_modifiers")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FFEquipmentItemModifierController {
    /** the static instance of {@link FFEquipmentItemModifierController}. */
    private static FFEquipmentItemModifierController instance;
    /**
     * Gets the static instance.
     * @return {@link FFEquipmentItemModifierController}
     */
    public static FFEquipmentItemModifierController getInstance() {
        if (instance == null) {
            new FFEquipmentItemModifierController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private FFEquipmentItemModifierRepository repository;
    /** Creates a new instance of {@link FFEquipmentItemModifierController}. */
    public FFEquipmentItemModifierController() {
        instance = this;
    }
    /**
     * Gets a list of {@link FFEquipmentItemModifierEntity}s.
     * @return {@link List}<{@link Resource}<{@link FFEquipmentItemModifierEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<FFEquipmentItemModifierEntity>> getAll() {
        Iterator<FFEquipmentItemModifierEntity> iter = repository.findAll()
                .iterator();
        List<Resource<FFEquipmentItemModifierEntity>> resources =
                new ArrayList<Resource<FFEquipmentItemModifierEntity>>();
        while (iter.hasNext()) {
            resources.add(getEquipmentItemModifierResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link FFEquipmentItemModifierEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link FFEquipmentItemModifierEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<FFEquipmentItemModifierEntity>> getById(
            @PathVariable final Long id) {
        FFEquipmentItemModifierEntity entity = repository.findOne(id);
        List<Resource<FFEquipmentItemModifierEntity>> resources =
                new ArrayList<Resource<FFEquipmentItemModifierEntity>>();
        resources.add(getEquipmentItemModifierResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link FFEquipmentItemModifierEntity}.
     * @param entity the {@link FFEquipmentItemModifierEntity}
     * @return {@link Resource}<{@link FFEquipmentItemModifierEntity}>
     */
    private Resource<FFEquipmentItemModifierEntity> getEquipmentItemModifierResource(
            final FFEquipmentItemModifierEntity entity) {
        Resource<FFEquipmentItemModifierEntity> resource =
                new Resource<FFEquipmentItemModifierEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link FFEquipmentItemModifierEntity}s.
     * @param entities the list of {@link FFEquipmentItemModifierEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFEquipmentItemModifierEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<FFEquipmentItemModifierEntity>> save(
            @RequestBody final List<FFEquipmentItemModifierEntity> entities) {
        List<Resource<FFEquipmentItemModifierEntity>> resources =
                new ArrayList<Resource<FFEquipmentItemModifierEntity>>();
        Iterator<FFEquipmentItemModifierEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link FFEquipmentItemModifierEntity}.
     * @param entity the {@link FFEquipmentItemModifierEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFEquipmentItemModifierEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<FFEquipmentItemModifierEntity>> save(
            @RequestBody final FFEquipmentItemModifierEntity entity) {
    
    
        FFEquipmentItemModifierEntity savedEntity = repository.save(entity);
        List<Resource<FFEquipmentItemModifierEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link FFEquipmentItemModifierEntity} instance
     */
    private void setIdFromRepository(final FFEquipmentItemModifierEntity entity) {
        List<FFEquipmentItemModifierEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = FFEquipmentItemModifierEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity FFEquipmentItemModifierEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<FFEquipmentItemModifierEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = FFEquipmentItemModifierEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity FFEquipmentItemModifierEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<FFEquipmentItemModifierEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity FFEquipmentItemModifierEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link FFEquipmentItemModifierEntity}s.
     * @param entities the list of {@link FFEquipmentItemModifierEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFEquipmentItemModifierEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<FFEquipmentItemModifierEntity>> update(
            @RequestBody final List<FFEquipmentItemModifierEntity> entities) {
        List<Resource<FFEquipmentItemModifierEntity>> resources = new ArrayList<Resource<FFEquipmentItemModifierEntity>>();
        Iterator<FFEquipmentItemModifierEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link FFEquipmentItemModifierEntity}.
     * @param entity the {@link FFEquipmentItemModifierEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFEquipmentItemModifierEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<FFEquipmentItemModifierEntity>> update(
            @RequestBody final FFEquipmentItemModifierEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        FFEquipmentItemModifierEntity savedEntity = repository.save(entity);
        List<Resource<FFEquipmentItemModifierEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link FFEquipmentItemModifierEntity}s that share a code.
     * @param code the equipment_item_modifier' code
     * @return {@link List}<{@link Resource}<{@link FFEquipmentItemModifierEntity}>>
     */
    @RequestMapping(path = "code/{code}",
            method = RequestMethod.GET)
    public List<Resource<FFEquipmentItemModifierEntity>> getByCode(
            @PathVariable final String code) {
        Iterator<FFEquipmentItemModifierEntity> iter = repository.findByCode(code)
                .iterator();
        List<Resource<FFEquipmentItemModifierEntity>> resources =
                new ArrayList<Resource<FFEquipmentItemModifierEntity>>();
        while (iter.hasNext()) {
            resources.add(getEquipmentItemModifierResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFEquipmentItemModifierEntity}s that share a percent.
     * @param percent the equipment_item_modifier' percent
     * @return {@link List}<{@link Resource}<{@link FFEquipmentItemModifierEntity}>>
     */
    @RequestMapping(path = "percent/{percent}",
            method = RequestMethod.GET)
    public List<Resource<FFEquipmentItemModifierEntity>> getByPercent(
            @PathVariable final Boolean percent) {
        Iterator<FFEquipmentItemModifierEntity> iter = repository.findByPercent(percent)
                .iterator();
        List<Resource<FFEquipmentItemModifierEntity>> resources =
                new ArrayList<Resource<FFEquipmentItemModifierEntity>>();
        while (iter.hasNext()) {
            resources.add(getEquipmentItemModifierResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFEquipmentItemModifierEntity}s that share a special.
     * @param special the equipment_item_modifier' special
     * @return {@link List}<{@link Resource}<{@link FFEquipmentItemModifierEntity}>>
     */
    @RequestMapping(path = "special/{special}",
            method = RequestMethod.GET)
    public List<Resource<FFEquipmentItemModifierEntity>> getBySpecial(
            @PathVariable final Long special) {
        Iterator<FFEquipmentItemModifierEntity> iter = repository.findBySpecial(special)
                .iterator();
        List<Resource<FFEquipmentItemModifierEntity>> resources =
                new ArrayList<Resource<FFEquipmentItemModifierEntity>>();
        while (iter.hasNext()) {
            resources.add(getEquipmentItemModifierResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFEquipmentItemModifierEntity}s that share a value.
     * @param value the equipment_item_modifier' value
     * @return {@link List}<{@link Resource}<{@link FFEquipmentItemModifierEntity}>>
     */
    @RequestMapping(path = "value/{value}",
            method = RequestMethod.GET)
    public List<Resource<FFEquipmentItemModifierEntity>> getByValue(
            @PathVariable final float value) {
        Iterator<FFEquipmentItemModifierEntity> iter = repository.findByValue(value)
                .iterator();
        List<Resource<FFEquipmentItemModifierEntity>> resources =
                new ArrayList<Resource<FFEquipmentItemModifierEntity>>();
        while (iter.hasNext()) {
            resources.add(getEquipmentItemModifierResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
