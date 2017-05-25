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
import com.osrapi.models.ff.FFMapTileEntity;
import com.osrapi.repositories.ff.FFMapCellRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/ff/map_cells")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FFMapCellController {
    /** the static instance of {@link FFMapCellController}. */
    private static FFMapCellController instance;
    /**
     * Gets the static instance.
     * @return {@link FFMapCellController}
     */
    public static FFMapCellController getInstance() {
        if (instance == null) {
            new FFMapCellController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private FFMapCellRepository repository;
    /** Creates a new instance of {@link FFMapCellController}. */
    public FFMapCellController() {
        instance = this;
    }
    /**
     * Gets a list of {@link FFMapCellEntity}s.
     * @return {@link List}<{@link Resource}<{@link FFMapCellEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<FFMapCellEntity>> getAll() {
        Iterator<FFMapCellEntity> iter = repository.findAll()
                .iterator();
        List<Resource<FFMapCellEntity>> resources =
                new ArrayList<Resource<FFMapCellEntity>>();
        while (iter.hasNext()) {
            resources.add(getMapCellResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link FFMapCellEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link FFMapCellEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<FFMapCellEntity>> getById(
            @PathVariable
            final Long id) {
        FFMapCellEntity entity = repository.findOne(id);
        List<Resource<FFMapCellEntity>> resources =
                new ArrayList<Resource<FFMapCellEntity>>();
        resources.add(getMapCellResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFMapCellEntity}s that share a levelName.
     * @param levelName the map_cell' levelName
     * @return {@link List}<{@link Resource}<{@link FFMapCellEntity}>>
     */
    @RequestMapping(path = "level_name/{levelName}",
            method = RequestMethod.GET)
    public List<Resource<FFMapCellEntity>> getByLevelName(
            @PathVariable
            final String levelName) {
        Iterator<FFMapCellEntity> iter = repository.findByLevelName(levelName)
                .iterator();
        List<Resource<FFMapCellEntity>> resources =
                new ArrayList<Resource<FFMapCellEntity>>();
        while (iter.hasNext()) {
            resources.add(getMapCellResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFMapCellEntity}s that share a name.
     * @param name the map_cell' name
     * @return {@link List}<{@link Resource}<{@link FFMapCellEntity}>>
     */
    @RequestMapping(path = "name/{name}",
            method = RequestMethod.GET)
    public List<Resource<FFMapCellEntity>> getByName(
            @PathVariable
            final String name) {
        Iterator<FFMapCellEntity> iter = repository.findByName(name)
                .iterator();
        List<Resource<FFMapCellEntity>> resources =
                new ArrayList<Resource<FFMapCellEntity>>();
        while (iter.hasNext()) {
            resources.add(getMapCellResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFMapCellEntity}s that share a x.
     * @param x the map_cell' x
     * @return {@link List}<{@link Resource}<{@link FFMapCellEntity}>>
     */
    @RequestMapping(path = "x/{x}",
            method = RequestMethod.GET)
    public List<Resource<FFMapCellEntity>> getByX(
            @PathVariable
            final Long x) {
        Iterator<FFMapCellEntity> iter = repository.findByX(x)
                .iterator();
        List<Resource<FFMapCellEntity>> resources =
                new ArrayList<Resource<FFMapCellEntity>>();
        while (iter.hasNext()) {
            resources.add(getMapCellResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFMapCellEntity}s that share a y.
     * @param y the map_cell' y
     * @return {@link List}<{@link Resource}<{@link FFMapCellEntity}>>
     */
    @RequestMapping(path = "y/{y}",
            method = RequestMethod.GET)
    public List<Resource<FFMapCellEntity>> getByY(
            @PathVariable
            final Long y) {
        Iterator<FFMapCellEntity> iter = repository.findByY(y)
                .iterator();
        List<Resource<FFMapCellEntity>> resources =
                new ArrayList<Resource<FFMapCellEntity>>();
        while (iter.hasNext()) {
            resources.add(getMapCellResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link FFMapCellEntity}.
     * @param entity the {@link FFMapCellEntity}
     * @return {@link Resource}<{@link FFMapCellEntity}>
     */
    private Resource<FFMapCellEntity> getMapCellResource(
            final FFMapCellEntity entity) {
        Resource<FFMapCellEntity> resource =
                new Resource<FFMapCellEntity>(
                        entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves a single {@link FFMapCellEntity}.
     * @param entity the {@link FFMapCellEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFMapCellEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<FFMapCellEntity>> save(
            @RequestBody
            final FFMapCellEntity entity) {
        System.out.println("************************");
        System.out.println(entity.getMapTile());
        System.out.println("************************");
        if (entity.getMapTile() != null
                && entity.getMapTile().getId() == null) {
            setMapTileIdFromRepository(entity);
        }

        FFMapCellEntity savedEntity = repository.save(entity);
        List<Resource<FFMapCellEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Saves multiple {@link FFMapCellEntity}s.
     * @param entities the list of {@link FFMapCellEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFMapCellEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<FFMapCellEntity>> save(
            @RequestBody
            final List<FFMapCellEntity> entities) {
        List<Resource<FFMapCellEntity>> resources =
                new ArrayList<Resource<FFMapCellEntity>>();
        Iterator<FFMapCellEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }

    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link FFMapCellEntity} instance
     */
    private void setIdFromRepository(final FFMapCellEntity entity) {
        List<FFMapCellEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = FFMapCellEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println(
                        "Cannot get Entity FFMapCellEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<FFMapCellEntity>) method.invoke(
                            repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                            && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = FFMapCellEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
                    System.out.println(
                            "Cannot get Entity FFMapCellEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<FFMapCellEntity>) method.invoke(
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
                    "Cannot get Entity FFMapCellEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;
    }
    private void setMapTileIdFromRepository(
            final FFMapCellEntity entity) {
        FFMapTileEntity memberEntity = null;
        List<Resource<FFMapTileEntity>> list = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = FFMapTileController.class.getDeclaredMethod(
                        "getByName", new Class[] { String.class });
                field = FFMapTileEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {}
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                System.out.println("************************");
                System.out.println(entity.getX()+","+entity.getY()+"::"+(String) field.get(entity.getMapTile()));
                System.out.println("************************");
                if (field.get(entity.getMapTile()) != null) {
                    list = (List<Resource<FFMapTileEntity>>) method
                            .invoke(
                                    FFMapTileController.getInstance(),
                                    (String) field
                                            .get(entity.getMapTile()));
                }
            }
            if (list == null) {
                try {
                    method = FFMapTileController.class.getDeclaredMethod(
                            "getByCode", new Class[] { String.class });
                    field = FFMapTileEntity.class
                            .getDeclaredField("code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {}
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity.getMapTile()) != null) {
                        list = (List<Resource<FFMapTileEntity>>) method
                                .invoke(FFMapTileController
                                        .getInstance(), (String) field.get(
                                                entity.getMapTile()));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {}
        if (list != null
                && !list.isEmpty()) {
            memberEntity = list.get(0).getContent();
        }
        if (memberEntity == null) {
            memberEntity = (FFMapTileEntity) ((Resource) FFMapTileController
                    .getInstance().save(
                            entity.getMapTile())
                    .get(0)).getContent();
        }
        entity.setMapTile(memberEntity);
        list = null;
    }
    /**
     * Updates a single {@link FFMapCellEntity}.
     * @param entity the {@link FFMapCellEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFMapCellEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<FFMapCellEntity>> update(
            @RequestBody
            final FFMapCellEntity entity) {
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
        if (entity.getMapTile() != null
                && entity.getMapTile().getId() == null) {
            setMapTileIdFromRepository(entity);
        }

        FFMapCellEntity savedEntity = repository.save(entity);
        List<Resource<FFMapCellEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Updates multiple {@link FFMapCellEntity}s.
     * @param entities the list of {@link FFMapCellEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFMapCellEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<FFMapCellEntity>> update(
            @RequestBody
            final List<FFMapCellEntity> entities) {
        List<Resource<FFMapCellEntity>> resources =
                new ArrayList<Resource<FFMapCellEntity>>();
        Iterator<FFMapCellEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
}
