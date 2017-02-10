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

import com.osrapi.models.ff.FFAttributeEntity;
import com.osrapi.models.ff.FFEquipmentElementTypeEntity;

import com.osrapi.repositories.ff.FFAttributeRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/ff/attributes")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FFAttributeController {
    /** the static instance of {@link FFAttributeController}. */
    private static FFAttributeController instance;
    /**
     * Gets the static instance.
     * @return {@link FFAttributeController}
     */
    public static FFAttributeController getInstance() {
        if (instance == null) {
            new FFAttributeController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private FFAttributeRepository repository;
    /** Creates a new instance of {@link FFAttributeController}. */
    public FFAttributeController() {
        instance = this;
    }
    /**
     * Gets a list of {@link FFAttributeEntity}s.
     * @return {@link List}<{@link Resource}<{@link FFAttributeEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<FFAttributeEntity>> getAll() {
        Iterator<FFAttributeEntity> iter = repository.findAll()
                .iterator();
        List<Resource<FFAttributeEntity>> resources =
                new ArrayList<Resource<FFAttributeEntity>>();
        while (iter.hasNext()) {
            resources.add(getAttributeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link FFAttributeEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link FFAttributeEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<FFAttributeEntity>> getById(
            @PathVariable final Long id) {
        FFAttributeEntity entity = repository.findOne(id);
        List<Resource<FFAttributeEntity>> resources =
                new ArrayList<Resource<FFAttributeEntity>>();
        resources.add(getAttributeResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link FFAttributeEntity}.
     * @param entity the {@link FFAttributeEntity}
     * @return {@link Resource}<{@link FFAttributeEntity}>
     */
    private Resource<FFAttributeEntity> getAttributeResource(
            final FFAttributeEntity entity) {
        Resource<FFAttributeEntity> resource =
                new Resource<FFAttributeEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link FFAttributeEntity}s.
     * @param entities the list of {@link FFAttributeEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFAttributeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<FFAttributeEntity>> save(
            @RequestBody final List<FFAttributeEntity> entities) {
        List<Resource<FFAttributeEntity>> resources =
                new ArrayList<Resource<FFAttributeEntity>>();
        Iterator<FFAttributeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link FFAttributeEntity}.
     * @param entity the {@link FFAttributeEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFAttributeEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<FFAttributeEntity>> save(
            @RequestBody final FFAttributeEntity entity) {
            if (entity.getElement() != null
        && entity.getElement().getId() == null) {
      setElementIdFromRepository(entity);
        }


    
        FFAttributeEntity savedEntity = repository.save(entity);
        List<Resource<FFAttributeEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link FFAttributeEntity} instance
     */
    private void setIdFromRepository(final FFAttributeEntity entity) {
        List<FFAttributeEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = FFAttributeEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity FFAttributeEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<FFAttributeEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = FFAttributeEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity FFAttributeEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<FFAttributeEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity FFAttributeEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link FFAttributeEntity}s.
     * @param entities the list of {@link FFAttributeEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFAttributeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<FFAttributeEntity>> update(
            @RequestBody final List<FFAttributeEntity> entities) {
        List<Resource<FFAttributeEntity>> resources = new ArrayList<Resource<FFAttributeEntity>>();
        Iterator<FFAttributeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link FFAttributeEntity}.
     * @param entity the {@link FFAttributeEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFAttributeEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<FFAttributeEntity>> update(
            @RequestBody final FFAttributeEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
            if (entity.getElement() != null
        && entity.getElement().getId() == null) {
      setElementIdFromRepository(entity);
        }


    
        FFAttributeEntity savedEntity = repository.save(entity);
        List<Resource<FFAttributeEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

  private void setElementIdFromRepository(
      final FFAttributeEntity entity) {
    FFEquipmentElementTypeEntity memberEntity = null;
    List<Resource<FFEquipmentElementTypeEntity>> list = null;
    try {
      Method method = null;
      Field field = null;
      try {
        method = FFEquipmentElementTypeController.class.getDeclaredMethod(
            "getByName", new Class[] { String.class });
        field = FFEquipmentElementTypeEntity.class.getDeclaredField("name");
      } catch (NoSuchMethodException | NoSuchFieldException e) {
      }
      if (method != null
          && field != null) {
        field.setAccessible(true);
        if (field.get(entity.getElement()) != null) {
          list = (List<Resource<FFEquipmentElementTypeEntity>>) method
              .invoke(
                  FFEquipmentElementTypeController.getInstance(),
                  (String) field
                      .get(entity.getElement()));
        }
      }
      if (list == null) {
        try {
          method = FFEquipmentElementTypeController.class.getDeclaredMethod(
              "getByCode", new Class[] { String.class });
          field = FFEquipmentElementTypeEntity.class
              .getDeclaredField("code");
        } catch (NoSuchMethodException | NoSuchFieldException e) {
        }
        if (method != null
            && field != null) {
          field.setAccessible(true);
          if (field.get(entity.getElement()) != null) {
            list = (List<Resource<FFEquipmentElementTypeEntity>>)
                method.invoke(FFEquipmentElementTypeController
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
      memberEntity = (FFEquipmentElementTypeEntity)
          ((Resource) FFEquipmentElementTypeController.getInstance().save(
              entity.getElement()).get(0)).getContent();
    }
    entity.setElement(memberEntity);
    list = null;
    }


    /**
     * Gets a list of {@link FFAttributeEntity}s that share a code.
     * @param code the attribute' code
     * @return {@link List}<{@link Resource}<{@link FFAttributeEntity}>>
     */
    @RequestMapping(path = "code/{code}",
            method = RequestMethod.GET)
    public List<Resource<FFAttributeEntity>> getByCode(
            @PathVariable final String code) {
        Iterator<FFAttributeEntity> iter = repository.findByCode(code)
                .iterator();
        List<Resource<FFAttributeEntity>> resources =
                new ArrayList<Resource<FFAttributeEntity>>();
        while (iter.hasNext()) {
            resources.add(getAttributeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFAttributeEntity}s that share a description.
     * @param description the attribute' description
     * @return {@link List}<{@link Resource}<{@link FFAttributeEntity}>>
     */
    @RequestMapping(path = "description/{description}",
            method = RequestMethod.GET)
    public List<Resource<FFAttributeEntity>> getByDescription(
            @PathVariable final String description) {
        Iterator<FFAttributeEntity> iter = repository.findByDescription(description)
                .iterator();
        List<Resource<FFAttributeEntity>> resources =
                new ArrayList<Resource<FFAttributeEntity>>();
        while (iter.hasNext()) {
            resources.add(getAttributeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFAttributeEntity}s that share a name.
     * @param name the attribute' name
     * @return {@link List}<{@link Resource}<{@link FFAttributeEntity}>>
     */
    @RequestMapping(path = "name/{name}",
            method = RequestMethod.GET)
    public List<Resource<FFAttributeEntity>> getByName(
            @PathVariable final String name) {
        Iterator<FFAttributeEntity> iter = repository.findByName(name)
                .iterator();
        List<Resource<FFAttributeEntity>> resources =
                new ArrayList<Resource<FFAttributeEntity>>();
        while (iter.hasNext()) {
            resources.add(getAttributeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
