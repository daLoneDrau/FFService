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

import com.osrapi.models.ff.FFHexagonEntity;

import com.osrapi.repositories.ff.FFHexagonRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/ff/hexagons")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FFHexagonController {
    /** the static instance of {@link FFHexagonController}. */
    private static FFHexagonController instance;
    /**
     * Gets the static instance.
     * @return {@link FFHexagonController}
     */
    public static FFHexagonController getInstance() {
        if (instance == null) {
            new FFHexagonController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private FFHexagonRepository repository;
    /** Creates a new instance of {@link FFHexagonController}. */
    public FFHexagonController() {
        instance = this;
    }
    /**
     * Gets a list of {@link FFHexagonEntity}s.
     * @return {@link List}<{@link Resource}<{@link FFHexagonEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<FFHexagonEntity>> getAll() {
        Iterator<FFHexagonEntity> iter = repository.findAll()
                .iterator();
        List<Resource<FFHexagonEntity>> resources =
                new ArrayList<Resource<FFHexagonEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexagonResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link FFHexagonEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link FFHexagonEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<FFHexagonEntity>> getById(
            @PathVariable final Long id) {
        FFHexagonEntity entity = repository.findOne(id);
        List<Resource<FFHexagonEntity>> resources =
                new ArrayList<Resource<FFHexagonEntity>>();
        resources.add(getHexagonResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link FFHexagonEntity}.
     * @param entity the {@link FFHexagonEntity}
     * @return {@link Resource}<{@link FFHexagonEntity}>
     */
    private Resource<FFHexagonEntity> getHexagonResource(
            final FFHexagonEntity entity) {
        Resource<FFHexagonEntity> resource =
                new Resource<FFHexagonEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link FFHexagonEntity}s.
     * @param entities the list of {@link FFHexagonEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFHexagonEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<FFHexagonEntity>> save(
            @RequestBody final List<FFHexagonEntity> entities) {
        List<Resource<FFHexagonEntity>> resources =
                new ArrayList<Resource<FFHexagonEntity>>();
        Iterator<FFHexagonEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link FFHexagonEntity}.
     * @param entity the {@link FFHexagonEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFHexagonEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<FFHexagonEntity>> save(
            @RequestBody final FFHexagonEntity entity) {
    
    
        FFHexagonEntity savedEntity = repository.save(entity);
        List<Resource<FFHexagonEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link FFHexagonEntity} instance
     */
    private void setIdFromRepository(final FFHexagonEntity entity) {
        List<FFHexagonEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = FFHexagonEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<FFHexagonEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = FFHexagonEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<FFHexagonEntity>) method.invoke(
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
     * Updates multiple {@link FFHexagonEntity}s.
     * @param entities the list of {@link FFHexagonEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFHexagonEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<FFHexagonEntity>> update(
            @RequestBody final List<FFHexagonEntity> entities) {
        List<Resource<FFHexagonEntity>> resources = new ArrayList<Resource<FFHexagonEntity>>();
        Iterator<FFHexagonEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link FFHexagonEntity}.
     * @param entity the {@link FFHexagonEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFHexagonEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<FFHexagonEntity>> update(
            @RequestBody final FFHexagonEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        FFHexagonEntity savedEntity = repository.save(entity);
        List<Resource<FFHexagonEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link FFHexagonEntity}s that share a flat.
     * @param flat the hexagon' flat
     * @return {@link List}<{@link Resource}<{@link FFHexagonEntity}>>
     */
    @RequestMapping(path = "flat/{flat}",
            method = RequestMethod.GET)
    public List<Resource<FFHexagonEntity>> getByFlat(
            @PathVariable final Boolean flat) {
        Iterator<FFHexagonEntity> iter = repository.findByFlat(flat)
                .iterator();
        List<Resource<FFHexagonEntity>> resources =
                new ArrayList<Resource<FFHexagonEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexagonResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFHexagonEntity}s that share a height.
     * @param height the hexagon' height
     * @return {@link List}<{@link Resource}<{@link FFHexagonEntity}>>
     */
    @RequestMapping(path = "height/{height}",
            method = RequestMethod.GET)
    public List<Resource<FFHexagonEntity>> getByHeight(
            @PathVariable final float height) {
        Iterator<FFHexagonEntity> iter = repository.findByHeight(height)
                .iterator();
        List<Resource<FFHexagonEntity>> resources =
                new ArrayList<Resource<FFHexagonEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexagonResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFHexagonEntity}s that share a horizontalDistance.
     * @param horizontalDistance the hexagon' horizontalDistance
     * @return {@link List}<{@link Resource}<{@link FFHexagonEntity}>>
     */
    @RequestMapping(path = "horizontal_distance/{horizontalDistance}",
            method = RequestMethod.GET)
    public List<Resource<FFHexagonEntity>> getByHorizontalDistance(
            @PathVariable final float horizontalDistance) {
        Iterator<FFHexagonEntity> iter = repository.findByHorizontalDistance(horizontalDistance)
                .iterator();
        List<Resource<FFHexagonEntity>> resources =
                new ArrayList<Resource<FFHexagonEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexagonResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFHexagonEntity}s that share a size.
     * @param size the hexagon' size
     * @return {@link List}<{@link Resource}<{@link FFHexagonEntity}>>
     */
    @RequestMapping(path = "size/{size}",
            method = RequestMethod.GET)
    public List<Resource<FFHexagonEntity>> getBySize(
            @PathVariable final float size) {
        Iterator<FFHexagonEntity> iter = repository.findBySize(size)
                .iterator();
        List<Resource<FFHexagonEntity>> resources =
                new ArrayList<Resource<FFHexagonEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexagonResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFHexagonEntity}s that share a verticalDistance.
     * @param verticalDistance the hexagon' verticalDistance
     * @return {@link List}<{@link Resource}<{@link FFHexagonEntity}>>
     */
    @RequestMapping(path = "vertical_distance/{verticalDistance}",
            method = RequestMethod.GET)
    public List<Resource<FFHexagonEntity>> getByVerticalDistance(
            @PathVariable final float verticalDistance) {
        Iterator<FFHexagonEntity> iter = repository.findByVerticalDistance(verticalDistance)
                .iterator();
        List<Resource<FFHexagonEntity>> resources =
                new ArrayList<Resource<FFHexagonEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexagonResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFHexagonEntity}s that share a width.
     * @param width the hexagon' width
     * @return {@link List}<{@link Resource}<{@link FFHexagonEntity}>>
     */
    @RequestMapping(path = "width/{width}",
            method = RequestMethod.GET)
    public List<Resource<FFHexagonEntity>> getByWidth(
            @PathVariable final float width) {
        Iterator<FFHexagonEntity> iter = repository.findByWidth(width)
                .iterator();
        List<Resource<FFHexagonEntity>> resources =
                new ArrayList<Resource<FFHexagonEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexagonResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFHexagonEntity}s that share a x.
     * @param x the hexagon' x
     * @return {@link List}<{@link Resource}<{@link FFHexagonEntity}>>
     */
    @RequestMapping(path = "x/{x}",
            method = RequestMethod.GET)
    public List<Resource<FFHexagonEntity>> getByX(
            @PathVariable final Long x) {
        Iterator<FFHexagonEntity> iter = repository.findByX(x)
                .iterator();
        List<Resource<FFHexagonEntity>> resources =
                new ArrayList<Resource<FFHexagonEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexagonResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFHexagonEntity}s that share a y.
     * @param y the hexagon' y
     * @return {@link List}<{@link Resource}<{@link FFHexagonEntity}>>
     */
    @RequestMapping(path = "y/{y}",
            method = RequestMethod.GET)
    public List<Resource<FFHexagonEntity>> getByY(
            @PathVariable final Long y) {
        Iterator<FFHexagonEntity> iter = repository.findByY(y)
                .iterator();
        List<Resource<FFHexagonEntity>> resources =
                new ArrayList<Resource<FFHexagonEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexagonResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFHexagonEntity}s that share a z.
     * @param z the hexagon' z
     * @return {@link List}<{@link Resource}<{@link FFHexagonEntity}>>
     */
    @RequestMapping(path = "z/{z}",
            method = RequestMethod.GET)
    public List<Resource<FFHexagonEntity>> getByZ(
            @PathVariable final Long z) {
        Iterator<FFHexagonEntity> iter = repository.findByZ(z)
                .iterator();
        List<Resource<FFHexagonEntity>> resources =
                new ArrayList<Resource<FFHexagonEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexagonResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
