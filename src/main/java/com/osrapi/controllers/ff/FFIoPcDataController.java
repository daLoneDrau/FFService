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

import com.osrapi.models.ff.FFIoPcDataEntity;
import com.osrapi.models.ff.FFGenderEntity;

import com.osrapi.repositories.ff.FFIoPcDataRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/ff/io_pc_data")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FFIoPcDataController {
    /** the static instance of {@link FFIoPcDataController}. */
    private static FFIoPcDataController instance;
    /**
     * Gets the static instance.
     * @return {@link FFIoPcDataController}
     */
    public static FFIoPcDataController getInstance() {
        if (instance == null) {
            new FFIoPcDataController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private FFIoPcDataRepository repository;
    /** Creates a new instance of {@link FFIoPcDataController}. */
    public FFIoPcDataController() {
        instance = this;
    }
    /**
     * Gets a list of {@link FFIoPcDataEntity}s.
     * @return {@link List}<{@link Resource}<{@link FFIoPcDataEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<FFIoPcDataEntity>> getAll() {
        Iterator<FFIoPcDataEntity> iter = repository.findAll()
                .iterator();
        List<Resource<FFIoPcDataEntity>> resources =
                new ArrayList<Resource<FFIoPcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoPcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link FFIoPcDataEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link FFIoPcDataEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<FFIoPcDataEntity>> getById(
            @PathVariable final Long id) {
        FFIoPcDataEntity entity = repository.findOne(id);
        List<Resource<FFIoPcDataEntity>> resources =
                new ArrayList<Resource<FFIoPcDataEntity>>();
        resources.add(getIoPcDataResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link FFIoPcDataEntity}.
     * @param entity the {@link FFIoPcDataEntity}
     * @return {@link Resource}<{@link FFIoPcDataEntity}>
     */
    private Resource<FFIoPcDataEntity> getIoPcDataResource(
            final FFIoPcDataEntity entity) {
        Resource<FFIoPcDataEntity> resource =
                new Resource<FFIoPcDataEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link FFIoPcDataEntity}s.
     * @param entities the list of {@link FFIoPcDataEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFIoPcDataEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<FFIoPcDataEntity>> save(
            @RequestBody final List<FFIoPcDataEntity> entities) {
        List<Resource<FFIoPcDataEntity>> resources =
                new ArrayList<Resource<FFIoPcDataEntity>>();
        Iterator<FFIoPcDataEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link FFIoPcDataEntity}.
     * @param entity the {@link FFIoPcDataEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFIoPcDataEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<FFIoPcDataEntity>> save(
            @RequestBody final FFIoPcDataEntity entity) {
            if (entity.getGender() != null
        && entity.getGender().getId() == null) {
      setGenderIdFromRepository(entity);
        }


    
        FFIoPcDataEntity savedEntity = repository.save(entity);
        List<Resource<FFIoPcDataEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link FFIoPcDataEntity} instance
     */
    private void setIdFromRepository(final FFIoPcDataEntity entity) {
        List<FFIoPcDataEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = FFIoPcDataEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<FFIoPcDataEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = FFIoPcDataEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<FFIoPcDataEntity>) method.invoke(
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
     * Updates multiple {@link FFIoPcDataEntity}s.
     * @param entities the list of {@link FFIoPcDataEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFIoPcDataEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<FFIoPcDataEntity>> update(
            @RequestBody final List<FFIoPcDataEntity> entities) {
        List<Resource<FFIoPcDataEntity>> resources = new ArrayList<Resource<FFIoPcDataEntity>>();
        Iterator<FFIoPcDataEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link FFIoPcDataEntity}.
     * @param entity the {@link FFIoPcDataEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFIoPcDataEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<FFIoPcDataEntity>> update(
            @RequestBody final FFIoPcDataEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
            if (entity.getGender() != null
        && entity.getGender().getId() == null) {
      setGenderIdFromRepository(entity);
        }


    
        FFIoPcDataEntity savedEntity = repository.save(entity);
        List<Resource<FFIoPcDataEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

  private void setGenderIdFromRepository(
      final FFIoPcDataEntity entity) {
    FFGenderEntity memberEntity = null;
    List<Resource<FFGenderEntity>> list = null;
    try {
      Method method = null;
      Field field = null;
      try {
        method = FFGenderController.class.getDeclaredMethod(
            "getByName", new Class[] { String.class });
        field = FFGenderEntity.class.getDeclaredField("name");
      } catch (NoSuchMethodException | NoSuchFieldException e) {
      }
      if (method != null
          && field != null) {
        field.setAccessible(true);
        if (field.get(entity.getGender()) != null) {
          list = (List<Resource<FFGenderEntity>>) method
              .invoke(
                  FFGenderController.getInstance(),
                  (String) field
                      .get(entity.getGender()));
        }
      }
      if (list == null) {
        try {
          method = FFGenderController.class.getDeclaredMethod(
              "getByCode", new Class[] { String.class });
          field = FFGenderEntity.class
              .getDeclaredField("code");
        } catch (NoSuchMethodException | NoSuchFieldException e) {
        }
        if (method != null
            && field != null) {
          field.setAccessible(true);
          if (field.get(entity.getGender()) != null) {
            list = (List<Resource<FFGenderEntity>>)
                method.invoke(FFGenderController
                    .getInstance(),(String) field.get(
                        entity.getGender()));
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
      memberEntity = (FFGenderEntity)
          ((Resource) FFGenderController.getInstance().save(
              entity.getGender()).get(0)).getContent();
    }
    entity.setGender(memberEntity);
    list = null;
    }


    /**
     * Gets a list of {@link FFIoPcDataEntity}s that share a bags.
     * @param bags the io_pc_data' bags
     * @return {@link List}<{@link Resource}<{@link FFIoPcDataEntity}>>
     */
    @RequestMapping(path = "bags/{bags}",
            method = RequestMethod.GET)
    public List<Resource<FFIoPcDataEntity>> getByBags(
            @PathVariable final Long bags) {
        Iterator<FFIoPcDataEntity> iter = repository.findByBags(bags)
                .iterator();
        List<Resource<FFIoPcDataEntity>> resources =
                new ArrayList<Resource<FFIoPcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoPcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFIoPcDataEntity}s that share a gold.
     * @param gold the io_pc_data' gold
     * @return {@link List}<{@link Resource}<{@link FFIoPcDataEntity}>>
     */
    @RequestMapping(path = "gold/{gold}",
            method = RequestMethod.GET)
    public List<Resource<FFIoPcDataEntity>> getByGold(
            @PathVariable final float gold) {
        Iterator<FFIoPcDataEntity> iter = repository.findByGold(gold)
                .iterator();
        List<Resource<FFIoPcDataEntity>> resources =
                new ArrayList<Resource<FFIoPcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoPcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFIoPcDataEntity}s that share a interfaceFlags.
     * @param interfaceFlags the io_pc_data' interfaceFlags
     * @return {@link List}<{@link Resource}<{@link FFIoPcDataEntity}>>
     */
    @RequestMapping(path = "interface_flags/{interfaceFlags}",
            method = RequestMethod.GET)
    public List<Resource<FFIoPcDataEntity>> getByInterfaceFlags(
            @PathVariable final Long interfaceFlags) {
        Iterator<FFIoPcDataEntity> iter = repository.findByInterfaceFlags(interfaceFlags)
                .iterator();
        List<Resource<FFIoPcDataEntity>> resources =
                new ArrayList<Resource<FFIoPcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoPcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFIoPcDataEntity}s that share a level.
     * @param level the io_pc_data' level
     * @return {@link List}<{@link Resource}<{@link FFIoPcDataEntity}>>
     */
    @RequestMapping(path = "level/{level}",
            method = RequestMethod.GET)
    public List<Resource<FFIoPcDataEntity>> getByLevel(
            @PathVariable final Long level) {
        Iterator<FFIoPcDataEntity> iter = repository.findByLevel(level)
                .iterator();
        List<Resource<FFIoPcDataEntity>> resources =
                new ArrayList<Resource<FFIoPcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoPcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFIoPcDataEntity}s that share a name.
     * @param name the io_pc_data' name
     * @return {@link List}<{@link Resource}<{@link FFIoPcDataEntity}>>
     */
    @RequestMapping(path = "name/{name}",
            method = RequestMethod.GET)
    public List<Resource<FFIoPcDataEntity>> getByName(
            @PathVariable final String name) {
        Iterator<FFIoPcDataEntity> iter = repository.findByName(name)
                .iterator();
        List<Resource<FFIoPcDataEntity>> resources =
                new ArrayList<Resource<FFIoPcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoPcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFIoPcDataEntity}s that share a xp.
     * @param xp the io_pc_data' xp
     * @return {@link List}<{@link Resource}<{@link FFIoPcDataEntity}>>
     */
    @RequestMapping(path = "xp/{xp}",
            method = RequestMethod.GET)
    public List<Resource<FFIoPcDataEntity>> getByXp(
            @PathVariable final Long xp) {
        Iterator<FFIoPcDataEntity> iter = repository.findByXp(xp)
                .iterator();
        List<Resource<FFIoPcDataEntity>> resources =
                new ArrayList<Resource<FFIoPcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoPcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
