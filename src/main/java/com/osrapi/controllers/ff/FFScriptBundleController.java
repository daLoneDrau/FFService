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

import com.osrapi.models.ff.FFScriptBundleEntity;
import com.osrapi.models.ff.FFScriptActionEntity;

import com.osrapi.repositories.ff.FFScriptBundleRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/ff/script_bundles")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FFScriptBundleController {
    /** the static instance of {@link FFScriptBundleController}. */
    private static FFScriptBundleController instance;
    /**
     * Gets the static instance.
     * @return {@link FFScriptBundleController}
     */
    public static FFScriptBundleController getInstance() {
        if (instance == null) {
            new FFScriptBundleController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private FFScriptBundleRepository repository;
    /** Creates a new instance of {@link FFScriptBundleController}. */
    public FFScriptBundleController() {
        instance = this;
    }
    /**
     * Gets a list of {@link FFScriptBundleEntity}s.
     * @return {@link List}<{@link Resource}<{@link FFScriptBundleEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<FFScriptBundleEntity>> getAll() {
        Iterator<FFScriptBundleEntity> iter = repository.findAll()
                .iterator();
        List<Resource<FFScriptBundleEntity>> resources =
                new ArrayList<Resource<FFScriptBundleEntity>>();
        while (iter.hasNext()) {
            resources.add(getScriptBundleResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link FFScriptBundleEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link FFScriptBundleEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<FFScriptBundleEntity>> getById(
            @PathVariable final Long id) {
        FFScriptBundleEntity entity = repository.findOne(id);
        List<Resource<FFScriptBundleEntity>> resources =
                new ArrayList<Resource<FFScriptBundleEntity>>();
        resources.add(getScriptBundleResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link FFScriptBundleEntity}.
     * @param entity the {@link FFScriptBundleEntity}
     * @return {@link Resource}<{@link FFScriptBundleEntity}>
     */
    private Resource<FFScriptBundleEntity> getScriptBundleResource(
            final FFScriptBundleEntity entity) {
        Resource<FFScriptBundleEntity> resource =
                new Resource<FFScriptBundleEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link FFScriptBundleEntity}s.
     * @param entities the list of {@link FFScriptBundleEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFScriptBundleEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<FFScriptBundleEntity>> save(
            @RequestBody final List<FFScriptBundleEntity> entities) {
        List<Resource<FFScriptBundleEntity>> resources =
                new ArrayList<Resource<FFScriptBundleEntity>>();
        Iterator<FFScriptBundleEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link FFScriptBundleEntity}.
     * @param entity the {@link FFScriptBundleEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFScriptBundleEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<FFScriptBundleEntity>> save(
            @RequestBody final FFScriptBundleEntity entity) {
        if (entity.getScripts() != null
                && !entity.getScripts().isEmpty()) {
            for (int i = entity.getScripts().size() - 1; i >= 0; i--) {
                FFScriptActionEntity scripts = null;
                List<Resource<FFScriptActionEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = FFScriptActionController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity FFScriptActionEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = FFScriptActionEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity FFScriptActionEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getScripts().get(i)) != null) {
                            list = (List<Resource<FFScriptActionEntity>>) method
                                    .invoke(
                                            FFScriptActionController.getInstance(),
                                            (String) field.get(entity.getScripts().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = FFScriptActionController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity FFScriptActionEntity from Controller by code");
            }
            try {
              field = FFScriptActionEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity FFScriptActionEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getScripts().get(i)) != null) {
                                list = (List<Resource<FFScriptActionEntity>>) method
                                        .invoke(
                                                FFScriptActionController
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
              System.out.println("CANNOT get embedded lookup Entity FFScriptActionEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    scripts = list.get(0).getContent();
                }
                if (scripts == null) {
                    scripts = (FFScriptActionEntity) ((Resource) FFScriptActionController
                            .getInstance()
                            .save(entity.getScripts().get(i)).get(0)).getContent();
                }
                entity.getScripts().set(i, scripts);
                list = null;
            }
        }


    
        FFScriptBundleEntity savedEntity = repository.save(entity);
        List<Resource<FFScriptBundleEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link FFScriptBundleEntity} instance
     */
    private void setIdFromRepository(final FFScriptBundleEntity entity) {
        List<FFScriptBundleEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = FFScriptBundleEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity FFScriptBundleEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<FFScriptBundleEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = FFScriptBundleEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity FFScriptBundleEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<FFScriptBundleEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity FFScriptBundleEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link FFScriptBundleEntity}s.
     * @param entities the list of {@link FFScriptBundleEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFScriptBundleEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<FFScriptBundleEntity>> update(
            @RequestBody final List<FFScriptBundleEntity> entities) {
        List<Resource<FFScriptBundleEntity>> resources = new ArrayList<Resource<FFScriptBundleEntity>>();
        Iterator<FFScriptBundleEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link FFScriptBundleEntity}.
     * @param entity the {@link FFScriptBundleEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFScriptBundleEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<FFScriptBundleEntity>> update(
            @RequestBody final FFScriptBundleEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
        if (entity.getScripts() != null
                && !entity.getScripts().isEmpty()) {
            for (int i = entity.getScripts().size() - 1; i >= 0; i--) {
                FFScriptActionEntity scripts = null;
                List<Resource<FFScriptActionEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = FFScriptActionController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
            System.out.println("Cannot get embedded lookup Entity FFScriptActionEntity from Controller by name");
                    }
                    Field field = null;
          try {
            field = FFScriptActionEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
            System.out.println("Cannot get embedded lookup Entity FFScriptActionEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getScripts().get(i)) != null) {
                            list = (List<Resource<FFScriptActionEntity>>) method
                                    .invoke(
                                            FFScriptActionController.getInstance(),
                                            (String) field.get(entity.getScripts().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = FFScriptActionController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              System.out.println("Cannot get embedded lookup Entity FFScriptActionEntity from Controller by code");
            }
            try {
              field = FFScriptActionEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              System.out.println("Cannot get embedded lookup Entity FFScriptActionEntity from class by code");
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getScripts().get(i)) != null) {
                                list = (List<Resource<FFScriptActionEntity>>) method
                                        .invoke(
                                                FFScriptActionController
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
              System.out.println("CANNOT get embedded lookup Entity FFScriptActionEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    scripts = list.get(0).getContent();
                }
                if (scripts == null) {
                    scripts = (FFScriptActionEntity) ((Resource) FFScriptActionController
                            .getInstance()
                            .save(entity.getScripts().get(i)).get(0)).getContent();
                }
                entity.getScripts().set(i, scripts);
                list = null;
            }
        }


    
        FFScriptBundleEntity savedEntity = repository.save(entity);
        List<Resource<FFScriptBundleEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link FFScriptBundleEntity}s that share a name.
     * @param name the script_bundle' name
     * @return {@link List}<{@link Resource}<{@link FFScriptBundleEntity}>>
     */
    @RequestMapping(path = "name/{name}",
            method = RequestMethod.GET)
    public List<Resource<FFScriptBundleEntity>> getByName(
            @PathVariable final String name) {
        Iterator<FFScriptBundleEntity> iter = repository.findByName(name)
                .iterator();
        List<Resource<FFScriptBundleEntity>> resources =
                new ArrayList<Resource<FFScriptBundleEntity>>();
        while (iter.hasNext()) {
            resources.add(getScriptBundleResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
