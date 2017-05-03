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

import com.osrapi.models.ff.FFDiceEntity;
import com.osrapi.models.ff.FFDieEntity;

import com.osrapi.repositories.ff.FFDiceRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/ff/dices")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FFDiceController {
    /** the static instance of {@link FFDiceController}. */
    private static FFDiceController instance;
    /**
     * Gets the static instance.
     * @return {@link FFDiceController}
     */
    public static FFDiceController getInstance() {
        if (instance == null) {
            new FFDiceController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private FFDiceRepository repository;
    /** Creates a new instance of {@link FFDiceController}. */
    public FFDiceController() {
        instance = this;
    }
    /**
     * Gets a list of {@link FFDiceEntity}s.
     * @return {@link List}<{@link Resource}<{@link FFDiceEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<FFDiceEntity>> getAll() {
        Iterator<FFDiceEntity> iter = repository.findAll()
                .iterator();
        List<Resource<FFDiceEntity>> resources =
                new ArrayList<Resource<FFDiceEntity>>();
        while (iter.hasNext()) {
            resources.add(getDiceResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link FFDiceEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link FFDiceEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<FFDiceEntity>> getById(
            @PathVariable final Long id) {
        FFDiceEntity entity = repository.findOne(id);
        List<Resource<FFDiceEntity>> resources =
                new ArrayList<Resource<FFDiceEntity>>();
        resources.add(getDiceResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link FFDiceEntity}.
     * @param entity the {@link FFDiceEntity}
     * @return {@link Resource}<{@link FFDiceEntity}>
     */
    private Resource<FFDiceEntity> getDiceResource(
            final FFDiceEntity entity) {
        Resource<FFDiceEntity> resource =
                new Resource<FFDiceEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link FFDiceEntity}s.
     * @param entities the list of {@link FFDiceEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFDiceEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<FFDiceEntity>> save(
            @RequestBody final List<FFDiceEntity> entities) {
        List<Resource<FFDiceEntity>> resources =
                new ArrayList<Resource<FFDiceEntity>>();
        Iterator<FFDiceEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link FFDiceEntity}.
     * @param entity the {@link FFDiceEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFDiceEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<FFDiceEntity>> save(
            @RequestBody final FFDiceEntity entity) {
            if (entity.getDie() != null
        && entity.getDie().getId() == null) {
      setDieIdFromRepository(entity);
        }


    
        FFDiceEntity savedEntity = repository.save(entity);
        List<Resource<FFDiceEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link FFDiceEntity} instance
     */
    private void setIdFromRepository(final FFDiceEntity entity) {
        List<FFDiceEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = FFDiceEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity FFDiceEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<FFDiceEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = FFDiceEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity FFDiceEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<FFDiceEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity FFDiceEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link FFDiceEntity}s.
     * @param entities the list of {@link FFDiceEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFDiceEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<FFDiceEntity>> update(
            @RequestBody final List<FFDiceEntity> entities) {
        List<Resource<FFDiceEntity>> resources = new ArrayList<Resource<FFDiceEntity>>();
        Iterator<FFDiceEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link FFDiceEntity}.
     * @param entity the {@link FFDiceEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFDiceEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<FFDiceEntity>> update(
            @RequestBody final FFDiceEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
            if (entity.getDie() != null
        && entity.getDie().getId() == null) {
      setDieIdFromRepository(entity);
        }


    
        FFDiceEntity savedEntity = repository.save(entity);
        List<Resource<FFDiceEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

  private void setDieIdFromRepository(
      final FFDiceEntity entity) {
    FFDieEntity memberEntity = null;
    List<Resource<FFDieEntity>> list = null;
    try {
      Method method = null;
      Field field = null;
      try {
        method = FFDieController.class.getDeclaredMethod(
            "getByName", new Class[] { String.class });
        field = FFDieEntity.class.getDeclaredField("name");
      } catch (NoSuchMethodException | NoSuchFieldException e) {
      }
      if (method != null
          && field != null) {
        field.setAccessible(true);
        if (field.get(entity.getDie()) != null) {
          list = (List<Resource<FFDieEntity>>) method
              .invoke(
                  FFDieController.getInstance(),
                  (String) field
                      .get(entity.getDie()));
        }
      }
      if (list == null) {
        try {
          method = FFDieController.class.getDeclaredMethod(
              "getByCode", new Class[] { String.class });
          field = FFDieEntity.class
              .getDeclaredField("code");
        } catch (NoSuchMethodException | NoSuchFieldException e) {
        }
        if (method != null
            && field != null) {
          field.setAccessible(true);
          if (field.get(entity.getDie()) != null) {
            list = (List<Resource<FFDieEntity>>)
                method.invoke(FFDieController
                    .getInstance(),(String) field.get(
                        entity.getDie()));
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
      memberEntity = (FFDieEntity)
          ((Resource) FFDieController.getInstance().save(
              entity.getDie()).get(0)).getContent();
    }
    entity.setDie(memberEntity);
    list = null;
    }


    /**
     * Gets a list of {@link FFDiceEntity}s that share a code.
     * @param code the dice' code
     * @return {@link List}<{@link Resource}<{@link FFDiceEntity}>>
     */
    @RequestMapping(path = "code/{code}",
            method = RequestMethod.GET)
    public List<Resource<FFDiceEntity>> getByCode(
            @PathVariable final String code) {
        Iterator<FFDiceEntity> iter = repository.findByCode(code)
                .iterator();
        List<Resource<FFDiceEntity>> resources =
                new ArrayList<Resource<FFDiceEntity>>();
        while (iter.hasNext()) {
            resources.add(getDiceResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFDiceEntity}s that share a number.
     * @param number the dice' number
     * @return {@link List}<{@link Resource}<{@link FFDiceEntity}>>
     */
    @RequestMapping(path = "number/{number}",
            method = RequestMethod.GET)
    public List<Resource<FFDiceEntity>> getByNumber(
            @PathVariable final Long number) {
        Iterator<FFDiceEntity> iter = repository.findByNumber(number)
                .iterator();
        List<Resource<FFDiceEntity>> resources =
                new ArrayList<Resource<FFDiceEntity>>();
        while (iter.hasNext()) {
            resources.add(getDiceResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFDiceEntity}s that share a plus.
     * @param plus the dice' plus
     * @return {@link List}<{@link Resource}<{@link FFDiceEntity}>>
     */
    @RequestMapping(path = "plus/{plus}",
            method = RequestMethod.GET)
    public List<Resource<FFDiceEntity>> getByPlus(
            @PathVariable final Long plus) {
        Iterator<FFDiceEntity> iter = repository.findByPlus(plus)
                .iterator();
        List<Resource<FFDiceEntity>> resources =
                new ArrayList<Resource<FFDiceEntity>>();
        while (iter.hasNext()) {
            resources.add(getDiceResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
