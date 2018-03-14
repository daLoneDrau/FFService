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

import com.osrapi.models.bp.BPIoPcDataEntity;
import com.osrapi.models.bp.BPGenderEntity;

import com.osrapi.repositories.bp.BPIoPcDataRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/bp/io_pc_data")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class BPIoPcDataController {
    /** the static instance of {@link BPIoPcDataController}. */
    private static BPIoPcDataController instance;
    /**
     * Gets the static instance.
     * @return {@link BPIoPcDataController}
     */
    public static BPIoPcDataController getInstance() {
        if (instance == null) {
            new BPIoPcDataController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private BPIoPcDataRepository repository;
    /** Creates a new instance of {@link BPIoPcDataController}. */
    public BPIoPcDataController() {
        instance = this;
    }
    /**
     * Gets a list of {@link BPIoPcDataEntity}s.
     * @return {@link List}<{@link Resource}<{@link BPIoPcDataEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<BPIoPcDataEntity>> getAll() {
        Iterator<BPIoPcDataEntity> iter = repository.findAll()
                .iterator();
        List<Resource<BPIoPcDataEntity>> resources =
                new ArrayList<Resource<BPIoPcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoPcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link BPIoPcDataEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link BPIoPcDataEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<BPIoPcDataEntity>> getById(
            @PathVariable final Long id) {
        BPIoPcDataEntity entity = repository.findOne(id);
        List<Resource<BPIoPcDataEntity>> resources =
                new ArrayList<Resource<BPIoPcDataEntity>>();
        resources.add(getIoPcDataResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link BPIoPcDataEntity}.
     * @param entity the {@link BPIoPcDataEntity}
     * @return {@link Resource}<{@link BPIoPcDataEntity}>
     */
    private Resource<BPIoPcDataEntity> getIoPcDataResource(
            final BPIoPcDataEntity entity) {
        Resource<BPIoPcDataEntity> resource =
                new Resource<BPIoPcDataEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link BPIoPcDataEntity}s.
     * @param entities the list of {@link BPIoPcDataEntity} instances
     * @return {@link List}<{@link Resource}<{@link BPIoPcDataEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<BPIoPcDataEntity>> save(
            @RequestBody final List<BPIoPcDataEntity> entities) {
        List<Resource<BPIoPcDataEntity>> resources =
                new ArrayList<Resource<BPIoPcDataEntity>>();
        Iterator<BPIoPcDataEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link BPIoPcDataEntity}.
     * @param entity the {@link BPIoPcDataEntity} instance
     * @return {@link List}<{@link Resource}<{@link BPIoPcDataEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<BPIoPcDataEntity>> save(
            @RequestBody final BPIoPcDataEntity entity) {
            if (entity.getGender() != null
        && entity.getGender().getId() == null) {
      setGenderIdFromRepository(entity);
        }


    
        BPIoPcDataEntity savedEntity = repository.save(entity);
        List<Resource<BPIoPcDataEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link BPIoPcDataEntity} instance
     */
    private void setIdFromRepository(final BPIoPcDataEntity entity) {
        List<BPIoPcDataEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = BPIoPcDataEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity BPIoPcDataEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<BPIoPcDataEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = BPIoPcDataEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity BPIoPcDataEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<BPIoPcDataEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity BPIoPcDataEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link BPIoPcDataEntity}s.
     * @param entities the list of {@link BPIoPcDataEntity} instances
     * @return {@link List}<{@link Resource}<{@link BPIoPcDataEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<BPIoPcDataEntity>> update(
            @RequestBody final List<BPIoPcDataEntity> entities) {
        List<Resource<BPIoPcDataEntity>> resources = new ArrayList<Resource<BPIoPcDataEntity>>();
        Iterator<BPIoPcDataEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link BPIoPcDataEntity}.
     * @param entity the {@link BPIoPcDataEntity} instance
     * @return {@link List}<{@link Resource}<{@link BPIoPcDataEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<BPIoPcDataEntity>> update(
            @RequestBody final BPIoPcDataEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
            if (entity.getGender() != null
        && entity.getGender().getId() == null) {
      setGenderIdFromRepository(entity);
        }


    
        BPIoPcDataEntity savedEntity = repository.save(entity);
        List<Resource<BPIoPcDataEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

  private void setGenderIdFromRepository(
      final BPIoPcDataEntity entity) {
    BPGenderEntity memberEntity = null;
    List<Resource<BPGenderEntity>> list = null;
    try {
      Method method = null;
      Field field = null;
      try {
        method = BPGenderController.class.getDeclaredMethod(
            "getByName", new Class[] { String.class });
        field = BPGenderEntity.class.getDeclaredField("name");
      } catch (NoSuchMethodException | NoSuchFieldException e) {
      }
      if (method != null
          && field != null) {
        field.setAccessible(true);
        if (field.get(entity.getGender()) != null) {
          list = (List<Resource<BPGenderEntity>>) method
              .invoke(
                  BPGenderController.getInstance(),
                  (String) field
                      .get(entity.getGender()));
        }
      }
      if (list == null) {
        try {
          method = BPGenderController.class.getDeclaredMethod(
              "getByCode", new Class[] { String.class });
          field = BPGenderEntity.class
              .getDeclaredField("code");
        } catch (NoSuchMethodException | NoSuchFieldException e) {
        }
        if (method != null
            && field != null) {
          field.setAccessible(true);
          if (field.get(entity.getGender()) != null) {
            list = (List<Resource<BPGenderEntity>>)
                method.invoke(BPGenderController
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
      memberEntity = (BPGenderEntity)
          ((Resource) BPGenderController.getInstance().save(
              entity.getGender()).get(0)).getContent();
    }
    entity.setGender(memberEntity);
    list = null;
    }


    /**
     * Gets a list of {@link BPIoPcDataEntity}s that share a bags.
     * @param bags the io_pc_data' bags
     * @return {@link List}<{@link Resource}<{@link BPIoPcDataEntity}>>
     */
    @RequestMapping(path = "bags/{bags}",
            method = RequestMethod.GET)
    public List<Resource<BPIoPcDataEntity>> getByBags(
            @PathVariable final Long bags) {
        Iterator<BPIoPcDataEntity> iter = repository.findByBags(bags)
                .iterator();
        List<Resource<BPIoPcDataEntity>> resources =
                new ArrayList<Resource<BPIoPcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoPcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link BPIoPcDataEntity}s that share a gold.
     * @param gold the io_pc_data' gold
     * @return {@link List}<{@link Resource}<{@link BPIoPcDataEntity}>>
     */
    @RequestMapping(path = "gold/{gold}",
            method = RequestMethod.GET)
    public List<Resource<BPIoPcDataEntity>> getByGold(
            @PathVariable final float gold) {
        Iterator<BPIoPcDataEntity> iter = repository.findByGold(gold)
                .iterator();
        List<Resource<BPIoPcDataEntity>> resources =
                new ArrayList<Resource<BPIoPcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoPcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link BPIoPcDataEntity}s that share a interfaceFlags.
     * @param interfaceFlags the io_pc_data' interfaceFlags
     * @return {@link List}<{@link Resource}<{@link BPIoPcDataEntity}>>
     */
    @RequestMapping(path = "interface_flags/{interfaceFlags}",
            method = RequestMethod.GET)
    public List<Resource<BPIoPcDataEntity>> getByInterfaceFlags(
            @PathVariable final Long interfaceFlags) {
        Iterator<BPIoPcDataEntity> iter = repository.findByInterfaceFlags(interfaceFlags)
                .iterator();
        List<Resource<BPIoPcDataEntity>> resources =
                new ArrayList<Resource<BPIoPcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoPcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link BPIoPcDataEntity}s that share a level.
     * @param level the io_pc_data' level
     * @return {@link List}<{@link Resource}<{@link BPIoPcDataEntity}>>
     */
    @RequestMapping(path = "level/{level}",
            method = RequestMethod.GET)
    public List<Resource<BPIoPcDataEntity>> getByLevel(
            @PathVariable final Long level) {
        Iterator<BPIoPcDataEntity> iter = repository.findByLevel(level)
                .iterator();
        List<Resource<BPIoPcDataEntity>> resources =
                new ArrayList<Resource<BPIoPcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoPcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link BPIoPcDataEntity}s that share a name.
     * @param name the io_pc_data' name
     * @return {@link List}<{@link Resource}<{@link BPIoPcDataEntity}>>
     */
    @RequestMapping(path = "name/{name}",
            method = RequestMethod.GET)
    public List<Resource<BPIoPcDataEntity>> getByName(
            @PathVariable final String name) {
        Iterator<BPIoPcDataEntity> iter = repository.findByName(name)
                .iterator();
        List<Resource<BPIoPcDataEntity>> resources =
                new ArrayList<Resource<BPIoPcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoPcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link BPIoPcDataEntity}s that share a xp.
     * @param xp the io_pc_data' xp
     * @return {@link List}<{@link Resource}<{@link BPIoPcDataEntity}>>
     */
    @RequestMapping(path = "xp/{xp}",
            method = RequestMethod.GET)
    public List<Resource<BPIoPcDataEntity>> getByXp(
            @PathVariable final Long xp) {
        Iterator<BPIoPcDataEntity> iter = repository.findByXp(xp)
                .iterator();
        List<Resource<BPIoPcDataEntity>> resources =
                new ArrayList<Resource<BPIoPcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoPcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
