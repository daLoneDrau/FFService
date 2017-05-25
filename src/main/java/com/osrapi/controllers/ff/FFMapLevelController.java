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

import com.osrapi.models.ff.FFMapCellEntity;
import com.osrapi.models.ff.FFMapLevelEntity;
import com.osrapi.repositories.ff.FFMapLevelRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/ff/map_levels")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FFMapLevelController {
    /** the static instance of {@link FFMapLevelController}. */
    private static FFMapLevelController instance;
    /**
     * Gets the static instance.
     * @return {@link FFMapLevelController}
     */
    public static FFMapLevelController getInstance() {
        if (instance == null) {
            new FFMapLevelController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private FFMapLevelRepository repository;
    /** Creates a new instance of {@link FFMapLevelController}. */
    public FFMapLevelController() {
        instance = this;
    }
    /**
     * Gets a list of {@link FFMapLevelEntity}s.
     * @return {@link List}<{@link Resource}<{@link FFMapLevelEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<FFMapLevelEntity>> getAll() {
        Iterator<FFMapLevelEntity> iter = repository.findAll()
                .iterator();
        List<Resource<FFMapLevelEntity>> resources =
                new ArrayList<Resource<FFMapLevelEntity>>();
        while (iter.hasNext()) {
            resources.add(getMapLevelResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFMapLevelEntity}s that share a elevation.
     * @param elevation the map_level' elevation
     * @return {@link List}<{@link Resource}<{@link FFMapLevelEntity}>>
     */
    @RequestMapping(path = "elevation/{elevation}",
            method = RequestMethod.GET)
    public List<Resource<FFMapLevelEntity>> getByElevation(
            @PathVariable
            final Long elevation) {
        Iterator<FFMapLevelEntity> iter = repository.findByElevation(elevation)
                .iterator();
        List<Resource<FFMapLevelEntity>> resources =
                new ArrayList<Resource<FFMapLevelEntity>>();
        while (iter.hasNext()) {
            resources.add(getMapLevelResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link FFMapLevelEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link FFMapLevelEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<FFMapLevelEntity>> getById(
            @PathVariable
            final Long id) {
        FFMapLevelEntity entity = repository.findOne(id);
        List<Resource<FFMapLevelEntity>> resources =
                new ArrayList<Resource<FFMapLevelEntity>>();
        resources.add(getMapLevelResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFMapLevelEntity}s that share a name.
     * @param name the map_level' name
     * @return {@link List}<{@link Resource}<{@link FFMapLevelEntity}>>
     */
    @RequestMapping(path = "name/{name}",
            method = RequestMethod.GET)
    public List<Resource<FFMapLevelEntity>> getByName(
            @PathVariable
            final String name) {
        Iterator<FFMapLevelEntity> iter = repository.findByName(name)
                .iterator();
        List<Resource<FFMapLevelEntity>> resources =
                new ArrayList<Resource<FFMapLevelEntity>>();
        while (iter.hasNext()) {
            resources.add(getMapLevelResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link FFMapLevelEntity}.
     * @param entity the {@link FFMapLevelEntity}
     * @return {@link Resource}<{@link FFMapLevelEntity}>
     */
    private Resource<FFMapLevelEntity> getMapLevelResource(
            final FFMapLevelEntity entity) {
        Resource<FFMapLevelEntity> resource =
                new Resource<FFMapLevelEntity>(
                        entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves a single {@link FFMapLevelEntity}.
     * @param entity the {@link FFMapLevelEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFMapLevelEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<FFMapLevelEntity>> save(
            @RequestBody
            final FFMapLevelEntity entity) {
        if (entity.getCells() != null
                && !entity.getCells().isEmpty()) {
            for (int i = entity.getCells().size() - 1; i >= 0; i--) {
                FFMapCellEntity cells = null;
                List<Resource<FFMapCellEntity>> list = null;
                try {
                    Method method = null;
                    try {
                        method = FFMapCellController.class.getDeclaredMethod(
                                "getByName", new Class[] { String.class });
                    } catch (NoSuchMethodException e) {
                        System.out.println(
                                "Cannot get embedded lookup Entity FFMapCellEntity from Controller by name");
                    }
                    Field field = null;
                    try {
                        field = FFMapCellEntity.class
                                .getDeclaredField("name");
                    } catch (NoSuchFieldException e) {
                        System.out.println(
                                "Cannot get embedded lookup Entity FFMapCellEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getCells().get(i)) != null) {
                            list = (List<Resource<FFMapCellEntity>>) method
                                    .invoke(
                                            FFMapCellController.getInstance(),
                                            (String) field.get(
                                                    entity.getCells().get(i)));
                        }
                    }
                    if (list == null) {
                        try {
                            method = FFMapCellController.class
                                    .getDeclaredMethod(
                                            "getByCode",
                                            new Class[] { String.class });
                        } catch (NoSuchMethodException e) {
                            System.out.println(
                                    "Cannot get embedded lookup Entity FFMapCellEntity from Controller by code");
                        }
                        try {
                            field = FFMapCellEntity.class.getDeclaredField(
                                    "code");
                        } catch (NoSuchFieldException e) {
                            System.out.println(
                                    "Cannot get embedded lookup Entity FFMapCellEntity from class by code");
                        }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getCells().get(i)) != null) {
                                list = (List<Resource<FFMapCellEntity>>) method
                                        .invoke(
                                                FFMapCellController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getCells()
                                                                .get(i)));
                            }
                        }
                    }
                    method = null;
                    field = null;
                } catch (SecurityException | IllegalArgumentException
                        | IllegalAccessException
                        | InvocationTargetException e) {
                    System.out.println(
                            "CANNOT get embedded lookup Entity FFMapCellEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    cells = list.get(0).getContent();
                }
                if (cells == null) {
                    cells = (FFMapCellEntity) ((Resource) FFMapCellController
                            .getInstance()
                            .save(entity.getCells().get(i)).get(0))
                                    .getContent();
                }
                entity.getCells().set(i, cells);
                list = null;
            }
        }

        FFMapLevelEntity savedEntity = repository.save(entity);
        List<Resource<FFMapLevelEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Saves multiple {@link FFMapLevelEntity}s.
     * @param entities the list of {@link FFMapLevelEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFMapLevelEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<FFMapLevelEntity>> save(
            @RequestBody
            final List<FFMapLevelEntity> entities) {
        List<Resource<FFMapLevelEntity>> resources =
                new ArrayList<Resource<FFMapLevelEntity>>();
        Iterator<FFMapLevelEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link FFMapLevelEntity} instance
     */
    private void setIdFromRepository(final FFMapLevelEntity entity) {
        List<FFMapLevelEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = FFMapLevelEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println(
                        "Cannot get Entity FFMapLevelEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<FFMapLevelEntity>) method.invoke(
                            repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                            && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = FFMapLevelEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
                    System.out.println(
                            "Cannot get Entity FFMapLevelEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<FFMapLevelEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
            System.out.println(
                    "Cannot get Entity FFMapLevelEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;
    }

    /**
     * Updates a single {@link FFMapLevelEntity}.
     * @param entity the {@link FFMapLevelEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFMapLevelEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<FFMapLevelEntity>> update(
            @RequestBody
            final FFMapLevelEntity entity) {
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
        if (entity.getCells() != null
                && !entity.getCells().isEmpty()) {
            for (int i = entity.getCells().size() - 1; i >= 0; i--) {
                FFMapCellEntity cells = null;
                List<Resource<FFMapCellEntity>> list = null;
                try {
                    Method method = null;
                    try {
                        method = FFMapCellController.class.getDeclaredMethod(
                                "getByName", new Class[] { String.class });
                    } catch (NoSuchMethodException e) {
                        System.out.println(
                                "Cannot get embedded lookup Entity FFMapCellEntity from Controller by name");
                    }
                    Field field = null;
                    try {
                        field = FFMapCellEntity.class
                                .getDeclaredField("name");
                    } catch (NoSuchFieldException e) {
                        System.out.println(
                                "Cannot get embedded lookup Entity FFMapCellEntity from class by name");
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getCells().get(i)) != null) {
                            list = (List<Resource<FFMapCellEntity>>) method
                                    .invoke(
                                            FFMapCellController.getInstance(),
                                            (String) field.get(
                                                    entity.getCells().get(i)));
                        }
                    }
                    if (list == null) {
                        try {
                            method = FFMapCellController.class
                                    .getDeclaredMethod(
                                            "getByCode",
                                            new Class[] { String.class });
                        } catch (NoSuchMethodException e) {
                            System.out.println(
                                    "Cannot get embedded lookup Entity FFMapCellEntity from Controller by code");
                        }
                        try {
                            field = FFMapCellEntity.class.getDeclaredField(
                                    "code");
                        } catch (NoSuchFieldException e) {
                            System.out.println(
                                    "Cannot get embedded lookup Entity FFMapCellEntity from class by code");
                        }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getCells().get(i)) != null) {
                                list = (List<Resource<FFMapCellEntity>>) method
                                        .invoke(
                                                FFMapCellController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getCells()
                                                                .get(i)));
                            }
                        }
                    }
                    method = null;
                    field = null;
                } catch (SecurityException | IllegalArgumentException
                        | IllegalAccessException
                        | InvocationTargetException e) {
                    System.out.println(
                            "CANNOT get embedded lookup Entity FFMapCellEntity by name or code");
                }
                if (list != null
                        && !list.isEmpty()) {
                    cells = list.get(0).getContent();
                }
                if (cells == null) {
                    cells = (FFMapCellEntity) ((Resource) FFMapCellController
                            .getInstance()
                            .save(entity.getCells().get(i)).get(0))
                                    .getContent();
                }
                entity.getCells().set(i, cells);
                list = null;
            }
        }

        FFMapLevelEntity savedEntity = repository.save(entity);
        List<Resource<FFMapLevelEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Updates multiple {@link FFMapLevelEntity}s.
     * @param entities the list of {@link FFMapLevelEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFMapLevelEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<FFMapLevelEntity>> update(
            @RequestBody
            final List<FFMapLevelEntity> entities) {
        List<Resource<FFMapLevelEntity>> resources =
                new ArrayList<Resource<FFMapLevelEntity>>();
        Iterator<FFMapLevelEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
}
