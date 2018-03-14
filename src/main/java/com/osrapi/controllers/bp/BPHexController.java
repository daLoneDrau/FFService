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

import com.osrapi.models.bp.BPHexEntity;
import com.osrapi.models.bp.BPHexTypeEntity;
import com.osrapi.models.bp.BPHexFeatureEntity;

import com.osrapi.repositories.bp.BPHexRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/bp/hexs")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class BPHexController {
    /** the static instance of {@link BPHexController}. */
    private static BPHexController instance;
    /**
     * Gets the static instance.
     * @return {@link BPHexController}
     */
    public static BPHexController getInstance() {
        if (instance == null) {
            new BPHexController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private BPHexRepository repository;
    /** Creates a new instance of {@link BPHexController}. */
    public BPHexController() {
        instance = this;
    }
    /**
     * Gets a list of {@link BPHexEntity}s.
     * @return {@link List}<{@link Resource}<{@link BPHexEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<BPHexEntity>> getAll() {
        Iterator<BPHexEntity> iter = repository.findAll()
                .iterator();
        List<Resource<BPHexEntity>> resources =
                new ArrayList<Resource<BPHexEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link BPHexEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link BPHexEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<BPHexEntity>> getById(
            @PathVariable final Long id) {
        BPHexEntity entity = repository.findOne(id);
        List<Resource<BPHexEntity>> resources =
                new ArrayList<Resource<BPHexEntity>>();
        resources.add(getHexResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link BPHexEntity}.
     * @param entity the {@link BPHexEntity}
     * @return {@link Resource}<{@link BPHexEntity}>
     */
    private Resource<BPHexEntity> getHexResource(
            final BPHexEntity entity) {
        Resource<BPHexEntity> resource =
                new Resource<BPHexEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link BPHexEntity}s.
     * @param entities the list of {@link BPHexEntity} instances
     * @return {@link List}<{@link Resource}<{@link BPHexEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<BPHexEntity>> save(
            @RequestBody final List<BPHexEntity> entities) {
        List<Resource<BPHexEntity>> resources =
                new ArrayList<Resource<BPHexEntity>>();
        Iterator<BPHexEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link BPHexEntity}.
     * @param entity the {@link BPHexEntity} instance
     * @return {@link List}<{@link Resource}<{@link BPHexEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<BPHexEntity>> save(
            @RequestBody final BPHexEntity entity) {
        if (entity.getFeatures() != null
                && !entity.getFeatures().isEmpty()) {
            for (int i = entity.getFeatures().size() - 1; i >= 0; i--) {
                BPHexFeatureEntity features = null;
                List<Resource<BPHexFeatureEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = BPHexFeatureController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity BPHexFeatureEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = BPHexFeatureEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity BPHexFeatureEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getFeatures().get(i)) != null) {
                            list = (List<Resource<BPHexFeatureEntity>>) method
                                    .invoke(
                                            BPHexFeatureController.getInstance(),
                                            (String) field.get(entity.getFeatures().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = BPHexFeatureController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity BPHexFeatureEntity from Controller by code");
            }
            try {
              field = BPHexFeatureEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity BPHexFeatureEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getFeatures().get(i)) != null) {
                                list = (List<Resource<BPHexFeatureEntity>>) method
                                        .invoke(
                                                BPHexFeatureController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getFeatures().get(i)));
                            }
                        }
                    }
                    method = null;
                    field = null;
                } catch (SecurityException | IllegalArgumentException
                        | IllegalAccessException
                        | InvocationTargetException e) {
              System.out.println("CANNOT get embedded lookup Entity BPHexFeatureEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    features = list.get(0).getContent();
                }
                if (features == null) {
                    features = (BPHexFeatureEntity) ((Resource) BPHexFeatureController
                            .getInstance()
                            .save(entity.getFeatures().get(i)).get(0)).getContent();
                }
                entity.getFeatures().set(i, features);
                list = null;
            }
        }

        if (entity.getType() != null
        && entity.getType().getId() == null) {
      setTypeIdFromRepository(entity);
        }


    
        BPHexEntity savedEntity = repository.save(entity);
        List<Resource<BPHexEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link BPHexEntity} instance
     */
    private void setIdFromRepository(final BPHexEntity entity) {
        List<BPHexEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = BPHexEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity BPHexEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<BPHexEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = BPHexEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity BPHexEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<BPHexEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity BPHexEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link BPHexEntity}s.
     * @param entities the list of {@link BPHexEntity} instances
     * @return {@link List}<{@link Resource}<{@link BPHexEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<BPHexEntity>> update(
            @RequestBody final List<BPHexEntity> entities) {
        List<Resource<BPHexEntity>> resources = new ArrayList<Resource<BPHexEntity>>();
        Iterator<BPHexEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link BPHexEntity}.
     * @param entity the {@link BPHexEntity} instance
     * @return {@link List}<{@link Resource}<{@link BPHexEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<BPHexEntity>> update(
            @RequestBody final BPHexEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
        if (entity.getFeatures() != null
                && !entity.getFeatures().isEmpty()) {
            for (int i = entity.getFeatures().size() - 1; i >= 0; i--) {
                BPHexFeatureEntity features = null;
                List<Resource<BPHexFeatureEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = BPHexFeatureController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity BPHexFeatureEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = BPHexFeatureEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity BPHexFeatureEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getFeatures().get(i)) != null) {
                            list = (List<Resource<BPHexFeatureEntity>>) method
                                    .invoke(
                                            BPHexFeatureController.getInstance(),
                                            (String) field.get(entity.getFeatures().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = BPHexFeatureController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity BPHexFeatureEntity from Controller by code");
            }
            try {
              field = BPHexFeatureEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity BPHexFeatureEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getFeatures().get(i)) != null) {
                                list = (List<Resource<BPHexFeatureEntity>>) method
                                        .invoke(
                                                BPHexFeatureController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getFeatures().get(i)));
                            }
                        }
                    }
                    method = null;
                    field = null;
                } catch (SecurityException | IllegalArgumentException
                        | IllegalAccessException
                        | InvocationTargetException e) {
              System.out.println("CANNOT get embedded lookup Entity BPHexFeatureEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    features = list.get(0).getContent();
                }
                if (features == null) {
                    features = (BPHexFeatureEntity) ((Resource) BPHexFeatureController
                            .getInstance()
                            .save(entity.getFeatures().get(i)).get(0)).getContent();
                }
                entity.getFeatures().set(i, features);
                list = null;
            }
        }

        if (entity.getType() != null
        && entity.getType().getId() == null) {
      setTypeIdFromRepository(entity);
        }


    
        BPHexEntity savedEntity = repository.save(entity);
        List<Resource<BPHexEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

  private void setTypeIdFromRepository(
      final BPHexEntity entity) {
    BPHexTypeEntity memberEntity = null;
    List<Resource<BPHexTypeEntity>> list = null;
    try {
      Method method = null;
      Field field = null;
      try {
        method = BPHexTypeController.class.getDeclaredMethod(
            "getByName", new Class[] { String.class });
        field = BPHexTypeEntity.class.getDeclaredField("name");
      } catch (NoSuchMethodException | NoSuchFieldException e) {
      }
      if (method != null
          && field != null) {
        field.setAccessible(true);
        if (field.get(entity.getType()) != null) {
          list = (List<Resource<BPHexTypeEntity>>) method
              .invoke(
                  BPHexTypeController.getInstance(),
                  (String) field
                      .get(entity.getType()));
        }
      }
      if (list == null) {
        try {
          method = BPHexTypeController.class.getDeclaredMethod(
              "getByCode", new Class[] { String.class });
          field = BPHexTypeEntity.class
              .getDeclaredField("code");
        } catch (NoSuchMethodException | NoSuchFieldException e) {
        }
        if (method != null
            && field != null) {
          field.setAccessible(true);
          if (field.get(entity.getType()) != null) {
            list = (List<Resource<BPHexTypeEntity>>)
                method.invoke(BPHexTypeController
                    .getInstance(),(String) field.get(
                        entity.getType()));
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
      memberEntity = (BPHexTypeEntity)
          ((Resource) BPHexTypeController.getInstance().save(
              entity.getType()).get(0)).getContent();
    }
    entity.setType(memberEntity);
    list = null;
    }


    /**
     * Gets a list of {@link BPHexEntity}s that share a x.
     * @param x the hex' x
     * @return {@link List}<{@link Resource}<{@link BPHexEntity}>>
     */
    @RequestMapping(path = "x/{x}",
            method = RequestMethod.GET)
    public List<Resource<BPHexEntity>> getByX(
            @PathVariable final Long x) {
        Iterator<BPHexEntity> iter = repository.findByX(x)
                .iterator();
        List<Resource<BPHexEntity>> resources =
                new ArrayList<Resource<BPHexEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link BPHexEntity}s that share a y.
     * @param y the hex' y
     * @return {@link List}<{@link Resource}<{@link BPHexEntity}>>
     */
    @RequestMapping(path = "y/{y}",
            method = RequestMethod.GET)
    public List<Resource<BPHexEntity>> getByY(
            @PathVariable final Long y) {
        Iterator<BPHexEntity> iter = repository.findByY(y)
                .iterator();
        List<Resource<BPHexEntity>> resources =
                new ArrayList<Resource<BPHexEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link BPHexEntity}s that share a name.
     * @param name the hex' name
     * @return {@link List}<{@link Resource}<{@link BPHexEntity}>>
     */
    @RequestMapping(path = "name/{name}",
            method = RequestMethod.GET)
    public List<Resource<BPHexEntity>> getByName(
            @PathVariable final String name) {
        Iterator<BPHexEntity> iter = repository.findByName(name)
                .iterator();
        List<Resource<BPHexEntity>> resources =
                new ArrayList<Resource<BPHexEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
