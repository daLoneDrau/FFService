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

import com.osrapi.models.ff.FFIoItemDataEntity;
import com.osrapi.models.ff.FFGroupEntity;
import com.osrapi.models.ff.FFObjectTypeEntity;

import com.osrapi.repositories.ff.FFIoItemDataRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/ff/io_item_data")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FFIoItemDataController {
    /** the static instance of {@link FFIoItemDataController}. */
    private static FFIoItemDataController instance;
    /**
     * Gets the static instance.
     * @return {@link FFIoItemDataController}
     */
    public static FFIoItemDataController getInstance() {
        if (instance == null) {
            new FFIoItemDataController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private FFIoItemDataRepository repository;
    /** Creates a new instance of {@link FFIoItemDataController}. */
    public FFIoItemDataController() {
        instance = this;
    }
    /**
     * Gets a list of {@link FFIoItemDataEntity}s.
     * @return {@link List}<{@link Resource}<{@link FFIoItemDataEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<FFIoItemDataEntity>> getAll() {
        Iterator<FFIoItemDataEntity> iter = repository.findAll()
                .iterator();
        List<Resource<FFIoItemDataEntity>> resources =
                new ArrayList<Resource<FFIoItemDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoItemDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link FFIoItemDataEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link FFIoItemDataEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<FFIoItemDataEntity>> getById(
            @PathVariable final Long id) {
        FFIoItemDataEntity entity = repository.findOne(id);
        List<Resource<FFIoItemDataEntity>> resources =
                new ArrayList<Resource<FFIoItemDataEntity>>();
        resources.add(getIoItemDataResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link FFIoItemDataEntity}.
     * @param entity the {@link FFIoItemDataEntity}
     * @return {@link Resource}<{@link FFIoItemDataEntity}>
     */
    private Resource<FFIoItemDataEntity> getIoItemDataResource(
            final FFIoItemDataEntity entity) {
        Resource<FFIoItemDataEntity> resource =
                new Resource<FFIoItemDataEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link FFIoItemDataEntity}s.
     * @param entities the list of {@link FFIoItemDataEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFIoItemDataEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<FFIoItemDataEntity>> save(
            @RequestBody final List<FFIoItemDataEntity> entities) {
        List<Resource<FFIoItemDataEntity>> resources =
                new ArrayList<Resource<FFIoItemDataEntity>>();
        Iterator<FFIoItemDataEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link FFIoItemDataEntity}.
     * @param entity the {@link FFIoItemDataEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFIoItemDataEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<FFIoItemDataEntity>> save(
            @RequestBody final FFIoItemDataEntity entity) {
        if (entity.getGroups() != null
                && !entity.getGroups().isEmpty()) {
            for (int i = entity.getGroups().size() - 1; i >= 0; i--) {
                FFGroupEntity groups = null;
                List<Resource<FFGroupEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = FFGroupController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity FFGroupEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = FFGroupEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity FFGroupEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getGroups().get(i)) != null) {
                            list = (List<Resource<FFGroupEntity>>) method
                                    .invoke(
                                            FFGroupController.getInstance(),
                                            (String) field.get(entity.getGroups().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = FFGroupController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity FFGroupEntity from Controller by code");
            }
            try {
              field = FFGroupEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity FFGroupEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getGroups().get(i)) != null) {
                                list = (List<Resource<FFGroupEntity>>) method
                                        .invoke(
                                                FFGroupController
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
              System.out.println("CANNOT get embedded lookup Entity FFGroupEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    groups = list.get(0).getContent();
                }
                if (groups == null) {
                    groups = (FFGroupEntity) ((Resource) FFGroupController
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
                FFObjectTypeEntity types = null;
                List<Resource<FFObjectTypeEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = FFObjectTypeController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity FFObjectTypeEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = FFObjectTypeEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity FFObjectTypeEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getTypes().get(i)) != null) {
                            list = (List<Resource<FFObjectTypeEntity>>) method
                                    .invoke(
                                            FFObjectTypeController.getInstance(),
                                            (String) field.get(entity.getTypes().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = FFObjectTypeController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity FFObjectTypeEntity from Controller by code");
            }
            try {
              field = FFObjectTypeEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity FFObjectTypeEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getTypes().get(i)) != null) {
                                list = (List<Resource<FFObjectTypeEntity>>) method
                                        .invoke(
                                                FFObjectTypeController
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
              System.out.println("CANNOT get embedded lookup Entity FFObjectTypeEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    types = list.get(0).getContent();
                }
                if (types == null) {
                    types = (FFObjectTypeEntity) ((Resource) FFObjectTypeController
                            .getInstance()
                            .save(entity.getTypes().get(i)).get(0)).getContent();
                }
                entity.getTypes().set(i, types);
                list = null;
            }
        }


    
        FFIoItemDataEntity savedEntity = repository.save(entity);
        List<Resource<FFIoItemDataEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link FFIoItemDataEntity} instance
     */
    private void setIdFromRepository(final FFIoItemDataEntity entity) {
        List<FFIoItemDataEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = FFIoItemDataEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity FFIoItemDataEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<FFIoItemDataEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = FFIoItemDataEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity FFIoItemDataEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<FFIoItemDataEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity FFIoItemDataEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link FFIoItemDataEntity}s.
     * @param entities the list of {@link FFIoItemDataEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFIoItemDataEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<FFIoItemDataEntity>> update(
            @RequestBody final List<FFIoItemDataEntity> entities) {
        List<Resource<FFIoItemDataEntity>> resources = new ArrayList<Resource<FFIoItemDataEntity>>();
        Iterator<FFIoItemDataEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link FFIoItemDataEntity}.
     * @param entity the {@link FFIoItemDataEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFIoItemDataEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<FFIoItemDataEntity>> update(
            @RequestBody final FFIoItemDataEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
        if (entity.getGroups() != null
                && !entity.getGroups().isEmpty()) {
            for (int i = entity.getGroups().size() - 1; i >= 0; i--) {
                FFGroupEntity groups = null;
                List<Resource<FFGroupEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = FFGroupController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity FFGroupEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = FFGroupEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity FFGroupEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getGroups().get(i)) != null) {
                            list = (List<Resource<FFGroupEntity>>) method
                                    .invoke(
                                            FFGroupController.getInstance(),
                                            (String) field.get(entity.getGroups().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = FFGroupController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity FFGroupEntity from Controller by code");
            }
            try {
              field = FFGroupEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity FFGroupEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getGroups().get(i)) != null) {
                                list = (List<Resource<FFGroupEntity>>) method
                                        .invoke(
                                                FFGroupController
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
              System.out.println("CANNOT get embedded lookup Entity FFGroupEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    groups = list.get(0).getContent();
                }
                if (groups == null) {
                    groups = (FFGroupEntity) ((Resource) FFGroupController
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
                FFObjectTypeEntity types = null;
                List<Resource<FFObjectTypeEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = FFObjectTypeController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity FFObjectTypeEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = FFObjectTypeEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity FFObjectTypeEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getTypes().get(i)) != null) {
                            list = (List<Resource<FFObjectTypeEntity>>) method
                                    .invoke(
                                            FFObjectTypeController.getInstance(),
                                            (String) field.get(entity.getTypes().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = FFObjectTypeController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity FFObjectTypeEntity from Controller by code");
            }
            try {
              field = FFObjectTypeEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity FFObjectTypeEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getTypes().get(i)) != null) {
                                list = (List<Resource<FFObjectTypeEntity>>) method
                                        .invoke(
                                                FFObjectTypeController
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
              System.out.println("CANNOT get embedded lookup Entity FFObjectTypeEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    types = list.get(0).getContent();
                }
                if (types == null) {
                    types = (FFObjectTypeEntity) ((Resource) FFObjectTypeController
                            .getInstance()
                            .save(entity.getTypes().get(i)).get(0)).getContent();
                }
                entity.getTypes().set(i, types);
                list = null;
            }
        }


    
        FFIoItemDataEntity savedEntity = repository.save(entity);
        List<Resource<FFIoItemDataEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link FFIoItemDataEntity}s that share a count.
     * @param count the io_item_data' count
     * @return {@link List}<{@link Resource}<{@link FFIoItemDataEntity}>>
     */
    @RequestMapping(path = "count/{count}",
            method = RequestMethod.GET)
    public List<Resource<FFIoItemDataEntity>> getByCount(
            @PathVariable final Long count) {
        Iterator<FFIoItemDataEntity> iter = repository.findByCount(count)
                .iterator();
        List<Resource<FFIoItemDataEntity>> resources =
                new ArrayList<Resource<FFIoItemDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoItemDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFIoItemDataEntity}s that share a description.
     * @param description the io_item_data' description
     * @return {@link List}<{@link Resource}<{@link FFIoItemDataEntity}>>
     */
    @RequestMapping(path = "description/{description}",
            method = RequestMethod.GET)
    public List<Resource<FFIoItemDataEntity>> getByDescription(
            @PathVariable final String description) {
        Iterator<FFIoItemDataEntity> iter = repository.findByDescription(description)
                .iterator();
        List<Resource<FFIoItemDataEntity>> resources =
                new ArrayList<Resource<FFIoItemDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoItemDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFIoItemDataEntity}s that share a foodValue.
     * @param foodValue the io_item_data' foodValue
     * @return {@link List}<{@link Resource}<{@link FFIoItemDataEntity}>>
     */
    @RequestMapping(path = "food_value/{foodValue}",
            method = RequestMethod.GET)
    public List<Resource<FFIoItemDataEntity>> getByFoodValue(
            @PathVariable final Long foodValue) {
        Iterator<FFIoItemDataEntity> iter = repository.findByFoodValue(foodValue)
                .iterator();
        List<Resource<FFIoItemDataEntity>> resources =
                new ArrayList<Resource<FFIoItemDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoItemDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFIoItemDataEntity}s that share a internalScript.
     * @param internalScript the io_item_data' internalScript
     * @return {@link List}<{@link Resource}<{@link FFIoItemDataEntity}>>
     */
    @RequestMapping(path = "internal_script/{internalScript}",
            method = RequestMethod.GET)
    public List<Resource<FFIoItemDataEntity>> getByInternalScript(
            @PathVariable final String internalScript) {
        Iterator<FFIoItemDataEntity> iter = repository.findByInternalScript(internalScript)
                .iterator();
        List<Resource<FFIoItemDataEntity>> resources =
                new ArrayList<Resource<FFIoItemDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoItemDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFIoItemDataEntity}s that share a leftRing.
     * @param leftRing the io_item_data' leftRing
     * @return {@link List}<{@link Resource}<{@link FFIoItemDataEntity}>>
     */
    @RequestMapping(path = "left_ring/{leftRing}",
            method = RequestMethod.GET)
    public List<Resource<FFIoItemDataEntity>> getByLeftRing(
            @PathVariable final Boolean leftRing) {
        Iterator<FFIoItemDataEntity> iter = repository.findByLeftRing(leftRing)
                .iterator();
        List<Resource<FFIoItemDataEntity>> resources =
                new ArrayList<Resource<FFIoItemDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoItemDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFIoItemDataEntity}s that share a lightValue.
     * @param lightValue the io_item_data' lightValue
     * @return {@link List}<{@link Resource}<{@link FFIoItemDataEntity}>>
     */
    @RequestMapping(path = "light_value/{lightValue}",
            method = RequestMethod.GET)
    public List<Resource<FFIoItemDataEntity>> getByLightValue(
            @PathVariable final Long lightValue) {
        Iterator<FFIoItemDataEntity> iter = repository.findByLightValue(lightValue)
                .iterator();
        List<Resource<FFIoItemDataEntity>> resources =
                new ArrayList<Resource<FFIoItemDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoItemDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFIoItemDataEntity}s that share a maxOwned.
     * @param maxOwned the io_item_data' maxOwned
     * @return {@link List}<{@link Resource}<{@link FFIoItemDataEntity}>>
     */
    @RequestMapping(path = "max_owned/{maxOwned}",
            method = RequestMethod.GET)
    public List<Resource<FFIoItemDataEntity>> getByMaxOwned(
            @PathVariable final Long maxOwned) {
        Iterator<FFIoItemDataEntity> iter = repository.findByMaxOwned(maxOwned)
                .iterator();
        List<Resource<FFIoItemDataEntity>> resources =
                new ArrayList<Resource<FFIoItemDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoItemDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFIoItemDataEntity}s that share a name.
     * @param name the io_item_data' name
     * @return {@link List}<{@link Resource}<{@link FFIoItemDataEntity}>>
     */
    @RequestMapping(path = "name/{name}",
            method = RequestMethod.GET)
    public List<Resource<FFIoItemDataEntity>> getByName(
            @PathVariable final String name) {
        Iterator<FFIoItemDataEntity> iter = repository.findByName(name)
                .iterator();
        List<Resource<FFIoItemDataEntity>> resources =
                new ArrayList<Resource<FFIoItemDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoItemDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFIoItemDataEntity}s that share a price.
     * @param price the io_item_data' price
     * @return {@link List}<{@link Resource}<{@link FFIoItemDataEntity}>>
     */
    @RequestMapping(path = "price/{price}",
            method = RequestMethod.GET)
    public List<Resource<FFIoItemDataEntity>> getByPrice(
            @PathVariable final float price) {
        Iterator<FFIoItemDataEntity> iter = repository.findByPrice(price)
                .iterator();
        List<Resource<FFIoItemDataEntity>> resources =
                new ArrayList<Resource<FFIoItemDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoItemDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFIoItemDataEntity}s that share a ringType.
     * @param ringType the io_item_data' ringType
     * @return {@link List}<{@link Resource}<{@link FFIoItemDataEntity}>>
     */
    @RequestMapping(path = "ring_type/{ringType}",
            method = RequestMethod.GET)
    public List<Resource<FFIoItemDataEntity>> getByRingType(
            @PathVariable final Long ringType) {
        Iterator<FFIoItemDataEntity> iter = repository.findByRingType(ringType)
                .iterator();
        List<Resource<FFIoItemDataEntity>> resources =
                new ArrayList<Resource<FFIoItemDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoItemDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFIoItemDataEntity}s that share a stackSize.
     * @param stackSize the io_item_data' stackSize
     * @return {@link List}<{@link Resource}<{@link FFIoItemDataEntity}>>
     */
    @RequestMapping(path = "stack_size/{stackSize}",
            method = RequestMethod.GET)
    public List<Resource<FFIoItemDataEntity>> getByStackSize(
            @PathVariable final Long stackSize) {
        Iterator<FFIoItemDataEntity> iter = repository.findByStackSize(stackSize)
                .iterator();
        List<Resource<FFIoItemDataEntity>> resources =
                new ArrayList<Resource<FFIoItemDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoItemDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFIoItemDataEntity}s that share a stealValue.
     * @param stealValue the io_item_data' stealValue
     * @return {@link List}<{@link Resource}<{@link FFIoItemDataEntity}>>
     */
    @RequestMapping(path = "steal_value/{stealValue}",
            method = RequestMethod.GET)
    public List<Resource<FFIoItemDataEntity>> getByStealValue(
            @PathVariable final Long stealValue) {
        Iterator<FFIoItemDataEntity> iter = repository.findByStealValue(stealValue)
                .iterator();
        List<Resource<FFIoItemDataEntity>> resources =
                new ArrayList<Resource<FFIoItemDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoItemDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFIoItemDataEntity}s that share a title.
     * @param title the io_item_data' title
     * @return {@link List}<{@link Resource}<{@link FFIoItemDataEntity}>>
     */
    @RequestMapping(path = "title/{title}",
            method = RequestMethod.GET)
    public List<Resource<FFIoItemDataEntity>> getByTitle(
            @PathVariable final String title) {
        Iterator<FFIoItemDataEntity> iter = repository.findByTitle(title)
                .iterator();
        List<Resource<FFIoItemDataEntity>> resources =
                new ArrayList<Resource<FFIoItemDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoItemDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFIoItemDataEntity}s that share a weight.
     * @param weight the io_item_data' weight
     * @return {@link List}<{@link Resource}<{@link FFIoItemDataEntity}>>
     */
    @RequestMapping(path = "weight/{weight}",
            method = RequestMethod.GET)
    public List<Resource<FFIoItemDataEntity>> getByWeight(
            @PathVariable final float weight) {
        Iterator<FFIoItemDataEntity> iter = repository.findByWeight(weight)
                .iterator();
        List<Resource<FFIoItemDataEntity>> resources =
                new ArrayList<Resource<FFIoItemDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoItemDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
