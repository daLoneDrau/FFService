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

import com.osrapi.models.bp.BPHexagonEntity;

import com.osrapi.repositories.bp.BPHexagonRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/bp/hexagons")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class BPHexagonController {
    /** the static instance of {@link BPHexagonController}. */
    private static BPHexagonController instance;
    /**
     * Gets the static instance.
     * @return {@link BPHexagonController}
     */
    public static BPHexagonController getInstance() {
        if (instance == null) {
            new BPHexagonController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private BPHexagonRepository repository;
    /** Creates a new instance of {@link BPHexagonController}. */
    public BPHexagonController() {
        instance = this;
    }
    /**
     * Gets a list of {@link BPHexagonEntity}s.
     * @return {@link List}<{@link Resource}<{@link BPHexagonEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<BPHexagonEntity>> getAll() {
        Iterator<BPHexagonEntity> iter = repository.findAll()
                .iterator();
        List<Resource<BPHexagonEntity>> resources =
                new ArrayList<Resource<BPHexagonEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexagonResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link BPHexagonEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link BPHexagonEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<BPHexagonEntity>> getById(
            @PathVariable final Long id) {
        BPHexagonEntity entity = repository.findOne(id);
        List<Resource<BPHexagonEntity>> resources =
                new ArrayList<Resource<BPHexagonEntity>>();
        resources.add(getHexagonResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link BPHexagonEntity}.
     * @param entity the {@link BPHexagonEntity}
     * @return {@link Resource}<{@link BPHexagonEntity}>
     */
    private Resource<BPHexagonEntity> getHexagonResource(
            final BPHexagonEntity entity) {
        Resource<BPHexagonEntity> resource =
                new Resource<BPHexagonEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link BPHexagonEntity}s.
     * @param entities the list of {@link BPHexagonEntity} instances
     * @return {@link List}<{@link Resource}<{@link BPHexagonEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<BPHexagonEntity>> save(
            @RequestBody final List<BPHexagonEntity> entities) {
        List<Resource<BPHexagonEntity>> resources =
                new ArrayList<Resource<BPHexagonEntity>>();
        Iterator<BPHexagonEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link BPHexagonEntity}.
     * @param entity the {@link BPHexagonEntity} instance
     * @return {@link List}<{@link Resource}<{@link BPHexagonEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<BPHexagonEntity>> save(
            @RequestBody final BPHexagonEntity entity) {
    
    
        BPHexagonEntity savedEntity = repository.save(entity);
        List<Resource<BPHexagonEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link BPHexagonEntity} instance
     */
    private void setIdFromRepository(final BPHexagonEntity entity) {
        List<BPHexagonEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = BPHexagonEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity BPHexagonEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<BPHexagonEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = BPHexagonEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity BPHexagonEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<BPHexagonEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity BPHexagonEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link BPHexagonEntity}s.
     * @param entities the list of {@link BPHexagonEntity} instances
     * @return {@link List}<{@link Resource}<{@link BPHexagonEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<BPHexagonEntity>> update(
            @RequestBody final List<BPHexagonEntity> entities) {
        List<Resource<BPHexagonEntity>> resources = new ArrayList<Resource<BPHexagonEntity>>();
        Iterator<BPHexagonEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link BPHexagonEntity}.
     * @param entity the {@link BPHexagonEntity} instance
     * @return {@link List}<{@link Resource}<{@link BPHexagonEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<BPHexagonEntity>> update(
            @RequestBody final BPHexagonEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        BPHexagonEntity savedEntity = repository.save(entity);
        List<Resource<BPHexagonEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link BPHexagonEntity}s that share a flat.
     * @param flat the hexagon' flat
     * @return {@link List}<{@link Resource}<{@link BPHexagonEntity}>>
     */
    @RequestMapping(path = "flat/{flat}",
            method = RequestMethod.GET)
    public List<Resource<BPHexagonEntity>> getByFlat(
            @PathVariable final Boolean flat) {
        Iterator<BPHexagonEntity> iter = repository.findByFlat(flat)
                .iterator();
        List<Resource<BPHexagonEntity>> resources =
                new ArrayList<Resource<BPHexagonEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexagonResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link BPHexagonEntity}s that share a height.
     * @param height the hexagon' height
     * @return {@link List}<{@link Resource}<{@link BPHexagonEntity}>>
     */
    @RequestMapping(path = "height/{height}",
            method = RequestMethod.GET)
    public List<Resource<BPHexagonEntity>> getByHeight(
            @PathVariable final float height) {
        Iterator<BPHexagonEntity> iter = repository.findByHeight(height)
                .iterator();
        List<Resource<BPHexagonEntity>> resources =
                new ArrayList<Resource<BPHexagonEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexagonResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link BPHexagonEntity}s that share a horizontalDistance.
     * @param horizontalDistance the hexagon' horizontalDistance
     * @return {@link List}<{@link Resource}<{@link BPHexagonEntity}>>
     */
    @RequestMapping(path = "horizontal_distance/{horizontalDistance}",
            method = RequestMethod.GET)
    public List<Resource<BPHexagonEntity>> getByHorizontalDistance(
            @PathVariable final float horizontalDistance) {
        Iterator<BPHexagonEntity> iter = repository.findByHorizontalDistance(horizontalDistance)
                .iterator();
        List<Resource<BPHexagonEntity>> resources =
                new ArrayList<Resource<BPHexagonEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexagonResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link BPHexagonEntity}s that share a size.
     * @param size the hexagon' size
     * @return {@link List}<{@link Resource}<{@link BPHexagonEntity}>>
     */
    @RequestMapping(path = "size/{size}",
            method = RequestMethod.GET)
    public List<Resource<BPHexagonEntity>> getBySize(
            @PathVariable final float size) {
        Iterator<BPHexagonEntity> iter = repository.findBySize(size)
                .iterator();
        List<Resource<BPHexagonEntity>> resources =
                new ArrayList<Resource<BPHexagonEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexagonResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link BPHexagonEntity}s that share a verticalDistance.
     * @param verticalDistance the hexagon' verticalDistance
     * @return {@link List}<{@link Resource}<{@link BPHexagonEntity}>>
     */
    @RequestMapping(path = "vertical_distance/{verticalDistance}",
            method = RequestMethod.GET)
    public List<Resource<BPHexagonEntity>> getByVerticalDistance(
            @PathVariable final float verticalDistance) {
        Iterator<BPHexagonEntity> iter = repository.findByVerticalDistance(verticalDistance)
                .iterator();
        List<Resource<BPHexagonEntity>> resources =
                new ArrayList<Resource<BPHexagonEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexagonResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link BPHexagonEntity}s that share a width.
     * @param width the hexagon' width
     * @return {@link List}<{@link Resource}<{@link BPHexagonEntity}>>
     */
    @RequestMapping(path = "width/{width}",
            method = RequestMethod.GET)
    public List<Resource<BPHexagonEntity>> getByWidth(
            @PathVariable final float width) {
        Iterator<BPHexagonEntity> iter = repository.findByWidth(width)
                .iterator();
        List<Resource<BPHexagonEntity>> resources =
                new ArrayList<Resource<BPHexagonEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexagonResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link BPHexagonEntity}s that share a x.
     * @param x the hexagon' x
     * @return {@link List}<{@link Resource}<{@link BPHexagonEntity}>>
     */
    @RequestMapping(path = "x/{x}",
            method = RequestMethod.GET)
    public List<Resource<BPHexagonEntity>> getByX(
            @PathVariable final Long x) {
        Iterator<BPHexagonEntity> iter = repository.findByX(x)
                .iterator();
        List<Resource<BPHexagonEntity>> resources =
                new ArrayList<Resource<BPHexagonEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexagonResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link BPHexagonEntity}s that share a y.
     * @param y the hexagon' y
     * @return {@link List}<{@link Resource}<{@link BPHexagonEntity}>>
     */
    @RequestMapping(path = "y/{y}",
            method = RequestMethod.GET)
    public List<Resource<BPHexagonEntity>> getByY(
            @PathVariable final Long y) {
        Iterator<BPHexagonEntity> iter = repository.findByY(y)
                .iterator();
        List<Resource<BPHexagonEntity>> resources =
                new ArrayList<Resource<BPHexagonEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexagonResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link BPHexagonEntity}s that share a z.
     * @param z the hexagon' z
     * @return {@link List}<{@link Resource}<{@link BPHexagonEntity}>>
     */
    @RequestMapping(path = "z/{z}",
            method = RequestMethod.GET)
    public List<Resource<BPHexagonEntity>> getByZ(
            @PathVariable final Long z) {
        Iterator<BPHexagonEntity> iter = repository.findByZ(z)
                .iterator();
        List<Resource<BPHexagonEntity>> resources =
                new ArrayList<Resource<BPHexagonEntity>>();
        while (iter.hasNext()) {
            resources.add(getHexagonResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
