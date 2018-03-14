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

import com.osrapi.models.bp.BPIoItemDataEntity;
import com.osrapi.models.bp.BPGroupEntity;
import com.osrapi.models.bp.BPObjectTypeEntity;

import com.osrapi.repositories.bp.BPIoItemDataRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/bp/io_item_data")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class BPIoItemDataController {
    /** the static instance of {@link BPIoItemDataController}. */
    private static BPIoItemDataController instance;
    /**
     * Gets the static instance.
     * @return {@link BPIoItemDataController}
     */
    public static BPIoItemDataController getInstance() {
        if (instance == null) {
            new BPIoItemDataController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private BPIoItemDataRepository repository;
    /** Creates a new instance of {@link BPIoItemDataController}. */
    public BPIoItemDataController() {
        instance = this;
    }
    /**
     * Gets a list of {@link BPIoItemDataEntity}s.
     * @return {@link List}<{@link Resource}<{@link BPIoItemDataEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<BPIoItemDataEntity>> getAll() {
        Iterator<BPIoItemDataEntity> iter = repository.findAll()
                .iterator();
        List<Resource<BPIoItemDataEntity>> resources =
                new ArrayList<Resource<BPIoItemDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoItemDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link BPIoItemDataEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link BPIoItemDataEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<BPIoItemDataEntity>> getById(
            @PathVariable final Long id) {
        BPIoItemDataEntity entity = repository.findOne(id);
        List<Resource<BPIoItemDataEntity>> resources =
                new ArrayList<Resource<BPIoItemDataEntity>>();
        resources.add(getIoItemDataResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link BPIoItemDataEntity}.
     * @param entity the {@link BPIoItemDataEntity}
     * @return {@link Resource}<{@link BPIoItemDataEntity}>
     */
    private Resource<BPIoItemDataEntity> getIoItemDataResource(
            final BPIoItemDataEntity entity) {
        Resource<BPIoItemDataEntity> resource =
                new Resource<BPIoItemDataEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link BPIoItemDataEntity}s.
     * @param entities the list of {@link BPIoItemDataEntity} instances
     * @return {@link List}<{@link Resource}<{@link BPIoItemDataEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<BPIoItemDataEntity>> save(
            @RequestBody final List<BPIoItemDataEntity> entities) {
        List<Resource<BPIoItemDataEntity>> resources =
                new ArrayList<Resource<BPIoItemDataEntity>>();
        Iterator<BPIoItemDataEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link BPIoItemDataEntity}.
     * @param entity the {@link BPIoItemDataEntity} instance
     * @return {@link List}<{@link Resource}<{@link BPIoItemDataEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<BPIoItemDataEntity>> save(
            @RequestBody final BPIoItemDataEntity entity) {
        if (entity.getGroups() != null
                && !entity.getGroups().isEmpty()) {
            for (int i = entity.getGroups().size() - 1; i >= 0; i--) {
                BPGroupEntity groups = null;
                List<Resource<BPGroupEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = BPGroupController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity BPGroupEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = BPGroupEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity BPGroupEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getGroups().get(i)) != null) {
                            list = (List<Resource<BPGroupEntity>>) method
                                    .invoke(
                                            BPGroupController.getInstance(),
                                            (String) field.get(entity.getGroups().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = BPGroupController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity BPGroupEntity from Controller by code");
            }
            try {
              field = BPGroupEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity BPGroupEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getGroups().get(i)) != null) {
                                list = (List<Resource<BPGroupEntity>>) method
                                        .invoke(
                                                BPGroupController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getGroups().get(i)));
                            }
                        }
                    }
                    method = null;
                    field = null;
                } catch (SecurityException | IllegalArgumentException
                        | IllegalAccessException
                        | InvocationTargetException e) {
              System.out.println("CANNOT get embedded lookup Entity BPGroupEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    groups = list.get(0).getContent();
                }
                if (groups == null) {
                    groups = (BPGroupEntity) ((Resource) BPGroupController
                            .getInstance()
                            .save(entity.getGroups().get(i)).get(0)).getContent();
                }
                entity.getGroups().set(i, groups);
                list = null;
            }
        }

    if (entity.getTypes() != null
                && !entity.getTypes().isEmpty()) {
            for (int i = entity.getTypes().size() - 1; i >= 0; i--) {
                BPObjectTypeEntity types = null;
                List<Resource<BPObjectTypeEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = BPObjectTypeController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity BPObjectTypeEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = BPObjectTypeEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity BPObjectTypeEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getTypes().get(i)) != null) {
                            list = (List<Resource<BPObjectTypeEntity>>) method
                                    .invoke(
                                            BPObjectTypeController.getInstance(),
                                            (String) field.get(entity.getTypes().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = BPObjectTypeController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity BPObjectTypeEntity from Controller by code");
            }
            try {
              field = BPObjectTypeEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity BPObjectTypeEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getTypes().get(i)) != null) {
                                list = (List<Resource<BPObjectTypeEntity>>) method
                                        .invoke(
                                                BPObjectTypeController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getTypes().get(i)));
                            }
                        }
                    }
                    method = null;
                    field = null;
                } catch (SecurityException | IllegalArgumentException
                        | IllegalAccessException
                        | InvocationTargetException e) {
              System.out.println("CANNOT get embedded lookup Entity BPObjectTypeEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    types = list.get(0).getContent();
                }
                if (types == null) {
                    types = (BPObjectTypeEntity) ((Resource) BPObjectTypeController
                            .getInstance()
                            .save(entity.getTypes().get(i)).get(0)).getContent();
                }
                entity.getTypes().set(i, types);
                list = null;
            }
        }


    
        BPIoItemDataEntity savedEntity = repository.save(entity);
        List<Resource<BPIoItemDataEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link BPIoItemDataEntity} instance
     */
    private void setIdFromRepository(final BPIoItemDataEntity entity) {
        List<BPIoItemDataEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = BPIoItemDataEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity BPIoItemDataEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<BPIoItemDataEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = BPIoItemDataEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity BPIoItemDataEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<BPIoItemDataEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity BPIoItemDataEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link BPIoItemDataEntity}s.
     * @param entities the list of {@link BPIoItemDataEntity} instances
     * @return {@link List}<{@link Resource}<{@link BPIoItemDataEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<BPIoItemDataEntity>> update(
            @RequestBody final List<BPIoItemDataEntity> entities) {
        List<Resource<BPIoItemDataEntity>> resources = new ArrayList<Resource<BPIoItemDataEntity>>();
        Iterator<BPIoItemDataEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link BPIoItemDataEntity}.
     * @param entity the {@link BPIoItemDataEntity} instance
     * @return {@link List}<{@link Resource}<{@link BPIoItemDataEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<BPIoItemDataEntity>> update(
            @RequestBody final BPIoItemDataEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
        if (entity.getGroups() != null
                && !entity.getGroups().isEmpty()) {
            for (int i = entity.getGroups().size() - 1; i >= 0; i--) {
                BPGroupEntity groups = null;
                List<Resource<BPGroupEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = BPGroupController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity BPGroupEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = BPGroupEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity BPGroupEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getGroups().get(i)) != null) {
                            list = (List<Resource<BPGroupEntity>>) method
                                    .invoke(
                                            BPGroupController.getInstance(),
                                            (String) field.get(entity.getGroups().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = BPGroupController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity BPGroupEntity from Controller by code");
            }
            try {
              field = BPGroupEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity BPGroupEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getGroups().get(i)) != null) {
                                list = (List<Resource<BPGroupEntity>>) method
                                        .invoke(
                                                BPGroupController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getGroups().get(i)));
                            }
                        }
                    }
                    method = null;
                    field = null;
                } catch (SecurityException | IllegalArgumentException
                        | IllegalAccessException
                        | InvocationTargetException e) {
              System.out.println("CANNOT get embedded lookup Entity BPGroupEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    groups = list.get(0).getContent();
                }
                if (groups == null) {
                    groups = (BPGroupEntity) ((Resource) BPGroupController
                            .getInstance()
                            .save(entity.getGroups().get(i)).get(0)).getContent();
                }
                entity.getGroups().set(i, groups);
                list = null;
            }
        }

    if (entity.getTypes() != null
                && !entity.getTypes().isEmpty()) {
            for (int i = entity.getTypes().size() - 1; i >= 0; i--) {
                BPObjectTypeEntity types = null;
                List<Resource<BPObjectTypeEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = BPObjectTypeController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity BPObjectTypeEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = BPObjectTypeEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity BPObjectTypeEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getTypes().get(i)) != null) {
                            list = (List<Resource<BPObjectTypeEntity>>) method
                                    .invoke(
                                            BPObjectTypeController.getInstance(),
                                            (String) field.get(entity.getTypes().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = BPObjectTypeController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity BPObjectTypeEntity from Controller by code");
            }
            try {
              field = BPObjectTypeEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity BPObjectTypeEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getTypes().get(i)) != null) {
                                list = (List<Resource<BPObjectTypeEntity>>) method
                                        .invoke(
                                                BPObjectTypeController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getTypes().get(i)));
                            }
                        }
                    }
                    method = null;
                    field = null;
                } catch (SecurityException | IllegalArgumentException
                        | IllegalAccessException
                        | InvocationTargetException e) {
              System.out.println("CANNOT get embedded lookup Entity BPObjectTypeEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    types = list.get(0).getContent();
                }
                if (types == null) {
                    types = (BPObjectTypeEntity) ((Resource) BPObjectTypeController
                            .getInstance()
                            .save(entity.getTypes().get(i)).get(0)).getContent();
                }
                entity.getTypes().set(i, types);
                list = null;
            }
        }


    
        BPIoItemDataEntity savedEntity = repository.save(entity);
        List<Resource<BPIoItemDataEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link BPIoItemDataEntity}s that share a count.
     * @param count the io_item_data' count
     * @return {@link List}<{@link Resource}<{@link BPIoItemDataEntity}>>
     */
    @RequestMapping(path = "count/{count}",
            method = RequestMethod.GET)
    public List<Resource<BPIoItemDataEntity>> getByCount(
            @PathVariable final Long count) {
        Iterator<BPIoItemDataEntity> iter = repository.findByCount(count)
                .iterator();
        List<Resource<BPIoItemDataEntity>> resources =
                new ArrayList<Resource<BPIoItemDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoItemDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link BPIoItemDataEntity}s that share a description.
     * @param description the io_item_data' description
     * @return {@link List}<{@link Resource}<{@link BPIoItemDataEntity}>>
     */
    @RequestMapping(path = "description/{description}",
            method = RequestMethod.GET)
    public List<Resource<BPIoItemDataEntity>> getByDescription(
            @PathVariable final String description) {
        Iterator<BPIoItemDataEntity> iter = repository.findByDescription(description)
                .iterator();
        List<Resource<BPIoItemDataEntity>> resources =
                new ArrayList<Resource<BPIoItemDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoItemDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link BPIoItemDataEntity}s that share a foodValue.
     * @param foodValue the io_item_data' foodValue
     * @return {@link List}<{@link Resource}<{@link BPIoItemDataEntity}>>
     */
    @RequestMapping(path = "food_value/{foodValue}",
            method = RequestMethod.GET)
    public List<Resource<BPIoItemDataEntity>> getByFoodValue(
            @PathVariable final Long foodValue) {
        Iterator<BPIoItemDataEntity> iter = repository.findByFoodValue(foodValue)
                .iterator();
        List<Resource<BPIoItemDataEntity>> resources =
                new ArrayList<Resource<BPIoItemDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoItemDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link BPIoItemDataEntity}s that share a internalScript.
     * @param internalScript the io_item_data' internalScript
     * @return {@link List}<{@link Resource}<{@link BPIoItemDataEntity}>>
     */
    @RequestMapping(path = "internal_script/{internalScript}",
            method = RequestMethod.GET)
    public List<Resource<BPIoItemDataEntity>> getByInternalScript(
            @PathVariable final String internalScript) {
        Iterator<BPIoItemDataEntity> iter = repository.findByInternalScript(internalScript)
                .iterator();
        List<Resource<BPIoItemDataEntity>> resources =
                new ArrayList<Resource<BPIoItemDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoItemDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link BPIoItemDataEntity}s that share a leftRing.
     * @param leftRing the io_item_data' leftRing
     * @return {@link List}<{@link Resource}<{@link BPIoItemDataEntity}>>
     */
    @RequestMapping(path = "left_ring/{leftRing}",
            method = RequestMethod.GET)
    public List<Resource<BPIoItemDataEntity>> getByLeftRing(
            @PathVariable final Boolean leftRing) {
        Iterator<BPIoItemDataEntity> iter = repository.findByLeftRing(leftRing)
                .iterator();
        List<Resource<BPIoItemDataEntity>> resources =
                new ArrayList<Resource<BPIoItemDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoItemDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link BPIoItemDataEntity}s that share a lightValue.
     * @param lightValue the io_item_data' lightValue
     * @return {@link List}<{@link Resource}<{@link BPIoItemDataEntity}>>
     */
    @RequestMapping(path = "light_value/{lightValue}",
            method = RequestMethod.GET)
    public List<Resource<BPIoItemDataEntity>> getByLightValue(
            @PathVariable final Long lightValue) {
        Iterator<BPIoItemDataEntity> iter = repository.findByLightValue(lightValue)
                .iterator();
        List<Resource<BPIoItemDataEntity>> resources =
                new ArrayList<Resource<BPIoItemDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoItemDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link BPIoItemDataEntity}s that share a maxOwned.
     * @param maxOwned the io_item_data' maxOwned
     * @return {@link List}<{@link Resource}<{@link BPIoItemDataEntity}>>
     */
    @RequestMapping(path = "max_owned/{maxOwned}",
            method = RequestMethod.GET)
    public List<Resource<BPIoItemDataEntity>> getByMaxOwned(
            @PathVariable final Long maxOwned) {
        Iterator<BPIoItemDataEntity> iter = repository.findByMaxOwned(maxOwned)
                .iterator();
        List<Resource<BPIoItemDataEntity>> resources =
                new ArrayList<Resource<BPIoItemDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoItemDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link BPIoItemDataEntity}s that share a name.
     * @param name the io_item_data' name
     * @return {@link List}<{@link Resource}<{@link BPIoItemDataEntity}>>
     */
    @RequestMapping(path = "name/{name}",
            method = RequestMethod.GET)
    public List<Resource<BPIoItemDataEntity>> getByName(
            @PathVariable final String name) {
        Iterator<BPIoItemDataEntity> iter = repository.findByName(name)
                .iterator();
        List<Resource<BPIoItemDataEntity>> resources =
                new ArrayList<Resource<BPIoItemDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoItemDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link BPIoItemDataEntity}s that share a price.
     * @param price the io_item_data' price
     * @return {@link List}<{@link Resource}<{@link BPIoItemDataEntity}>>
     */
    @RequestMapping(path = "price/{price}",
            method = RequestMethod.GET)
    public List<Resource<BPIoItemDataEntity>> getByPrice(
            @PathVariable final float price) {
        Iterator<BPIoItemDataEntity> iter = repository.findByPrice(price)
                .iterator();
        List<Resource<BPIoItemDataEntity>> resources =
                new ArrayList<Resource<BPIoItemDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoItemDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link BPIoItemDataEntity}s that share a ringType.
     * @param ringType the io_item_data' ringType
     * @return {@link List}<{@link Resource}<{@link BPIoItemDataEntity}>>
     */
    @RequestMapping(path = "ring_type/{ringType}",
            method = RequestMethod.GET)
    public List<Resource<BPIoItemDataEntity>> getByRingType(
            @PathVariable final Long ringType) {
        Iterator<BPIoItemDataEntity> iter = repository.findByRingType(ringType)
                .iterator();
        List<Resource<BPIoItemDataEntity>> resources =
                new ArrayList<Resource<BPIoItemDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoItemDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link BPIoItemDataEntity}s that share a stackSize.
     * @param stackSize the io_item_data' stackSize
     * @return {@link List}<{@link Resource}<{@link BPIoItemDataEntity}>>
     */
    @RequestMapping(path = "stack_size/{stackSize}",
            method = RequestMethod.GET)
    public List<Resource<BPIoItemDataEntity>> getByStackSize(
            @PathVariable final Long stackSize) {
        Iterator<BPIoItemDataEntity> iter = repository.findByStackSize(stackSize)
                .iterator();
        List<Resource<BPIoItemDataEntity>> resources =
                new ArrayList<Resource<BPIoItemDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoItemDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link BPIoItemDataEntity}s that share a stealValue.
     * @param stealValue the io_item_data' stealValue
     * @return {@link List}<{@link Resource}<{@link BPIoItemDataEntity}>>
     */
    @RequestMapping(path = "steal_value/{stealValue}",
            method = RequestMethod.GET)
    public List<Resource<BPIoItemDataEntity>> getByStealValue(
            @PathVariable final Long stealValue) {
        Iterator<BPIoItemDataEntity> iter = repository.findByStealValue(stealValue)
                .iterator();
        List<Resource<BPIoItemDataEntity>> resources =
                new ArrayList<Resource<BPIoItemDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoItemDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link BPIoItemDataEntity}s that share a weight.
     * @param weight the io_item_data' weight
     * @return {@link List}<{@link Resource}<{@link BPIoItemDataEntity}>>
     */
    @RequestMapping(path = "weight/{weight}",
            method = RequestMethod.GET)
    public List<Resource<BPIoItemDataEntity>> getByWeight(
            @PathVariable final float weight) {
        Iterator<BPIoItemDataEntity> iter = repository.findByWeight(weight)
                .iterator();
        List<Resource<BPIoItemDataEntity>> resources =
                new ArrayList<Resource<BPIoItemDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoItemDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
