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

import com.osrapi.models.bp.BPAttributeEntity;
import com.osrapi.models.bp.BPEquipmentElementTypeEntity;

import com.osrapi.repositories.bp.BPAttributeRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/bp/attributes")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class BPAttributeController {
    /** the static instance of {@link BPAttributeController}. */
    private static BPAttributeController instance;
    /**
     * Gets the static instance.
     * @return {@link BPAttributeController}
     */
    public static BPAttributeController getInstance() {
        if (instance == null) {
            new BPAttributeController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private BPAttributeRepository repository;
    /** Creates a new instance of {@link BPAttributeController}. */
    public BPAttributeController() {
        instance = this;
    }
    /**
     * Gets a list of {@link BPAttributeEntity}s.
     * @return {@link List}<{@link Resource}<{@link BPAttributeEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<BPAttributeEntity>> getAll() {
        Iterator<BPAttributeEntity> iter = repository.findAll()
                .iterator();
        List<Resource<BPAttributeEntity>> resources =
                new ArrayList<Resource<BPAttributeEntity>>();
        while (iter.hasNext()) {
            resources.add(getAttributeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link BPAttributeEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link BPAttributeEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<BPAttributeEntity>> getById(
            @PathVariable final Long id) {
        BPAttributeEntity entity = repository.findOne(id);
        List<Resource<BPAttributeEntity>> resources =
                new ArrayList<Resource<BPAttributeEntity>>();
        resources.add(getAttributeResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link BPAttributeEntity}.
     * @param entity the {@link BPAttributeEntity}
     * @return {@link Resource}<{@link BPAttributeEntity}>
     */
    private Resource<BPAttributeEntity> getAttributeResource(
            final BPAttributeEntity entity) {
        Resource<BPAttributeEntity> resource =
                new Resource<BPAttributeEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link BPAttributeEntity}s.
     * @param entities the list of {@link BPAttributeEntity} instances
     * @return {@link List}<{@link Resource}<{@link BPAttributeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<BPAttributeEntity>> save(
            @RequestBody final List<BPAttributeEntity> entities) {
        List<Resource<BPAttributeEntity>> resources =
                new ArrayList<Resource<BPAttributeEntity>>();
        Iterator<BPAttributeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link BPAttributeEntity}.
     * @param entity the {@link BPAttributeEntity} instance
     * @return {@link List}<{@link Resource}<{@link BPAttributeEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<BPAttributeEntity>> save(
            @RequestBody final BPAttributeEntity entity) {
            if (entity.getElement() != null
        && entity.getElement().getId() == null) {
      setElementIdFromRepository(entity);
        }


    
        BPAttributeEntity savedEntity = repository.save(entity);
        List<Resource<BPAttributeEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link BPAttributeEntity} instance
     */
    private void setIdFromRepository(final BPAttributeEntity entity) {
        List<BPAttributeEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = BPAttributeEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity BPAttributeEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<BPAttributeEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = BPAttributeEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity BPAttributeEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<BPAttributeEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity BPAttributeEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link BPAttributeEntity}s.
     * @param entities the list of {@link BPAttributeEntity} instances
     * @return {@link List}<{@link Resource}<{@link BPAttributeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<BPAttributeEntity>> update(
            @RequestBody final List<BPAttributeEntity> entities) {
        List<Resource<BPAttributeEntity>> resources = new ArrayList<Resource<BPAttributeEntity>>();
        Iterator<BPAttributeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link BPAttributeEntity}.
     * @param entity the {@link BPAttributeEntity} instance
     * @return {@link List}<{@link Resource}<{@link BPAttributeEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<BPAttributeEntity>> update(
            @RequestBody final BPAttributeEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
            if (entity.getElement() != null
        && entity.getElement().getId() == null) {
      setElementIdFromRepository(entity);
        }


    
        BPAttributeEntity savedEntity = repository.save(entity);
        List<Resource<BPAttributeEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

  private void setElementIdFromRepository(
      final BPAttributeEntity entity) {
    BPEquipmentElementTypeEntity memberEntity = null;
    List<Resource<BPEquipmentElementTypeEntity>> list = null;
    try {
      Method method = null;
      Field field = null;
      try {
        method = BPEquipmentElementTypeController.class.getDeclaredMethod(
            "getByName", new Class[] { String.class });
        field = BPEquipmentElementTypeEntity.class.getDeclaredField("name");
      } catch (NoSuchMethodException | NoSuchFieldException e) {
      }
      if (method != null
          && field != null) {
        field.setAccessible(true);
        if (field.get(entity.getElement()) != null) {
          list = (List<Resource<BPEquipmentElementTypeEntity>>) method
              .invoke(
                  BPEquipmentElementTypeController.getInstance(),
                  (String) field
                      .get(entity.getElement()));
        }
      }
      if (list == null) {
        try {
          method = BPEquipmentElementTypeController.class.getDeclaredMethod(
              "getByCode", new Class[] { String.class });
          field = BPEquipmentElementTypeEntity.class
              .getDeclaredField("code");
        } catch (NoSuchMethodException | NoSuchFieldException e) {
        }
        if (method != null
            && field != null) {
          field.setAccessible(true);
          if (field.get(entity.getElement()) != null) {
            list = (List<Resource<BPEquipmentElementTypeEntity>>)
                method.invoke(BPEquipmentElementTypeController
                    .getInstance(),(String) field.get(
                        entity.getElement()));
          }
        }
      }
      method = null;
      field = null;
    } catch (SecurityException | IllegalArgumentException
        | IllegalAccessException
        | InvocationTargetException e) {
    }
    if (list != null
        && !list.isEmpty()) {
      memberEntity = list.get(0).getContent();
    }
    if (memberEntity == null) {
      memberEntity = (BPEquipmentElementTypeEntity)
          ((Resource) BPEquipmentElementTypeController.getInstance().save(
              entity.getElement()).get(0)).getContent();
    }
    entity.setElement(memberEntity);
    list = null;
    }


    /**
     * Gets a list of {@link BPAttributeEntity}s that share a code.
     * @param code the attribute' code
     * @return {@link List}<{@link Resource}<{@link BPAttributeEntity}>>
     */
    @RequestMapping(path = "code/{code}",
            method = RequestMethod.GET)
    public List<Resource<BPAttributeEntity>> getByCode(
            @PathVariable final String code) {
        Iterator<BPAttributeEntity> iter = repository.findByCode(code)
                .iterator();
        List<Resource<BPAttributeEntity>> resources =
                new ArrayList<Resource<BPAttributeEntity>>();
        while (iter.hasNext()) {
            resources.add(getAttributeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link BPAttributeEntity}s that share a description.
     * @param description the attribute' description
     * @return {@link List}<{@link Resource}<{@link BPAttributeEntity}>>
     */
    @RequestMapping(path = "description/{description}",
            method = RequestMethod.GET)
    public List<Resource<BPAttributeEntity>> getByDescription(
            @PathVariable final String description) {
        Iterator<BPAttributeEntity> iter = repository.findByDescription(description)
                .iterator();
        List<Resource<BPAttributeEntity>> resources =
                new ArrayList<Resource<BPAttributeEntity>>();
        while (iter.hasNext()) {
            resources.add(getAttributeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link BPAttributeEntity}s that share a name.
     * @param name the attribute' name
     * @return {@link List}<{@link Resource}<{@link BPAttributeEntity}>>
     */
    @RequestMapping(path = "name/{name}",
            method = RequestMethod.GET)
    public List<Resource<BPAttributeEntity>> getByName(
            @PathVariable final String name) {
        Iterator<BPAttributeEntity> iter = repository.findByName(name)
                .iterator();
        List<Resource<BPAttributeEntity>> resources =
                new ArrayList<Resource<BPAttributeEntity>>();
        while (iter.hasNext()) {
            resources.add(getAttributeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
