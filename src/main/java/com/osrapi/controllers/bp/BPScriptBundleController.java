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

import com.osrapi.models.bp.BPScriptBundleEntity;
import com.osrapi.models.bp.BPScriptActionEntity;

import com.osrapi.repositories.bp.BPScriptBundleRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/bp/script_bundles")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class BPScriptBundleController {
    /** the static instance of {@link BPScriptBundleController}. */
    private static BPScriptBundleController instance;
    /**
     * Gets the static instance.
     * @return {@link BPScriptBundleController}
     */
    public static BPScriptBundleController getInstance() {
        if (instance == null) {
            new BPScriptBundleController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private BPScriptBundleRepository repository;
    /** Creates a new instance of {@link BPScriptBundleController}. */
    public BPScriptBundleController() {
        instance = this;
    }
    /**
     * Gets a list of {@link BPScriptBundleEntity}s.
     * @return {@link List}<{@link Resource}<{@link BPScriptBundleEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<BPScriptBundleEntity>> getAll() {
        Iterator<BPScriptBundleEntity> iter = repository.findAll()
                .iterator();
        List<Resource<BPScriptBundleEntity>> resources =
                new ArrayList<Resource<BPScriptBundleEntity>>();
        while (iter.hasNext()) {
            resources.add(getScriptBundleResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link BPScriptBundleEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link BPScriptBundleEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<BPScriptBundleEntity>> getById(
            @PathVariable final Long id) {
        BPScriptBundleEntity entity = repository.findOne(id);
        List<Resource<BPScriptBundleEntity>> resources =
                new ArrayList<Resource<BPScriptBundleEntity>>();
        resources.add(getScriptBundleResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link BPScriptBundleEntity}.
     * @param entity the {@link BPScriptBundleEntity}
     * @return {@link Resource}<{@link BPScriptBundleEntity}>
     */
    private Resource<BPScriptBundleEntity> getScriptBundleResource(
            final BPScriptBundleEntity entity) {
        Resource<BPScriptBundleEntity> resource =
                new Resource<BPScriptBundleEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link BPScriptBundleEntity}s.
     * @param entities the list of {@link BPScriptBundleEntity} instances
     * @return {@link List}<{@link Resource}<{@link BPScriptBundleEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<BPScriptBundleEntity>> save(
            @RequestBody final List<BPScriptBundleEntity> entities) {
        List<Resource<BPScriptBundleEntity>> resources =
                new ArrayList<Resource<BPScriptBundleEntity>>();
        Iterator<BPScriptBundleEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link BPScriptBundleEntity}.
     * @param entity the {@link BPScriptBundleEntity} instance
     * @return {@link List}<{@link Resource}<{@link BPScriptBundleEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<BPScriptBundleEntity>> save(
            @RequestBody final BPScriptBundleEntity entity) {
        if (entity.getScripts() != null
                && !entity.getScripts().isEmpty()) {
            for (int i = entity.getScripts().size() - 1; i >= 0; i--) {
                BPScriptActionEntity scripts = null;
                List<Resource<BPScriptActionEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = BPScriptActionController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity BPScriptActionEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = BPScriptActionEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity BPScriptActionEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getScripts().get(i)) != null) {
                            list = (List<Resource<BPScriptActionEntity>>) method
                                    .invoke(
                                            BPScriptActionController.getInstance(),
                                            (String) field.get(entity.getScripts().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = BPScriptActionController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity BPScriptActionEntity from Controller by code");
            }
            try {
              field = BPScriptActionEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity BPScriptActionEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getScripts().get(i)) != null) {
                                list = (List<Resource<BPScriptActionEntity>>) method
                                        .invoke(
                                                BPScriptActionController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getScripts().get(i)));
                            }
                        }
                    }
                    method = null;
                    field = null;
                } catch (SecurityException | IllegalArgumentException
                        | IllegalAccessException
                        | InvocationTargetException e) {
              System.out.println("CANNOT get embedded lookup Entity BPScriptActionEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    scripts = list.get(0).getContent();
                }
                if (scripts == null) {
                    scripts = (BPScriptActionEntity) ((Resource) BPScriptActionController
                            .getInstance()
                            .save(entity.getScripts().get(i)).get(0)).getContent();
                }
                entity.getScripts().set(i, scripts);
                list = null;
            }
        }


    
        BPScriptBundleEntity savedEntity = repository.save(entity);
        List<Resource<BPScriptBundleEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link BPScriptBundleEntity} instance
     */
    private void setIdFromRepository(final BPScriptBundleEntity entity) {
        List<BPScriptBundleEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = BPScriptBundleEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity BPScriptBundleEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<BPScriptBundleEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = BPScriptBundleEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity BPScriptBundleEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<BPScriptBundleEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity BPScriptBundleEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link BPScriptBundleEntity}s.
     * @param entities the list of {@link BPScriptBundleEntity} instances
     * @return {@link List}<{@link Resource}<{@link BPScriptBundleEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<BPScriptBundleEntity>> update(
            @RequestBody final List<BPScriptBundleEntity> entities) {
        List<Resource<BPScriptBundleEntity>> resources = new ArrayList<Resource<BPScriptBundleEntity>>();
        Iterator<BPScriptBundleEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link BPScriptBundleEntity}.
     * @param entity the {@link BPScriptBundleEntity} instance
     * @return {@link List}<{@link Resource}<{@link BPScriptBundleEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<BPScriptBundleEntity>> update(
            @RequestBody final BPScriptBundleEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
        if (entity.getScripts() != null
                && !entity.getScripts().isEmpty()) {
            for (int i = entity.getScripts().size() - 1; i >= 0; i--) {
                BPScriptActionEntity scripts = null;
                List<Resource<BPScriptActionEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = BPScriptActionController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity BPScriptActionEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = BPScriptActionEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity BPScriptActionEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getScripts().get(i)) != null) {
                            list = (List<Resource<BPScriptActionEntity>>) method
                                    .invoke(
                                            BPScriptActionController.getInstance(),
                                            (String) field.get(entity.getScripts().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = BPScriptActionController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity BPScriptActionEntity from Controller by code");
            }
            try {
              field = BPScriptActionEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity BPScriptActionEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getScripts().get(i)) != null) {
                                list = (List<Resource<BPScriptActionEntity>>) method
                                        .invoke(
                                                BPScriptActionController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getScripts().get(i)));
                            }
                        }
                    }
                    method = null;
                    field = null;
                } catch (SecurityException | IllegalArgumentException
                        | IllegalAccessException
                        | InvocationTargetException e) {
              System.out.println("CANNOT get embedded lookup Entity BPScriptActionEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    scripts = list.get(0).getContent();
                }
                if (scripts == null) {
                    scripts = (BPScriptActionEntity) ((Resource) BPScriptActionController
                            .getInstance()
                            .save(entity.getScripts().get(i)).get(0)).getContent();
                }
                entity.getScripts().set(i, scripts);
                list = null;
            }
        }


    
        BPScriptBundleEntity savedEntity = repository.save(entity);
        List<Resource<BPScriptBundleEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link BPScriptBundleEntity}s that share a name.
     * @param name the script_bundle' name
     * @return {@link List}<{@link Resource}<{@link BPScriptBundleEntity}>>
     */
    @RequestMapping(path = "name/{name}",
            method = RequestMethod.GET)
    public List<Resource<BPScriptBundleEntity>> getByName(
            @PathVariable final String name) {
        Iterator<BPScriptBundleEntity> iter = repository.findByName(name)
                .iterator();
        List<Resource<BPScriptBundleEntity>> resources =
                new ArrayList<Resource<BPScriptBundleEntity>>();
        while (iter.hasNext()) {
            resources.add(getScriptBundleResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
