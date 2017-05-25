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

import com.osrapi.models.ff.FFMapTileEntity;

import com.osrapi.repositories.ff.FFMapTileRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/ff/map_tiles")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FFMapTileController {
    /** the static instance of {@link FFMapTileController}. */
    private static FFMapTileController instance;
    /**
     * Gets the static instance.
     * @return {@link FFMapTileController}
     */
    public static FFMapTileController getInstance() {
        if (instance == null) {
            new FFMapTileController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private FFMapTileRepository repository;
    /** Creates a new instance of {@link FFMapTileController}. */
    public FFMapTileController() {
        instance = this;
    }
    /**
     * Gets a list of {@link FFMapTileEntity}s.
     * @return {@link List}<{@link Resource}<{@link FFMapTileEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<FFMapTileEntity>> getAll() {
        Iterator<FFMapTileEntity> iter = repository.findAll()
                .iterator();
        List<Resource<FFMapTileEntity>> resources =
                new ArrayList<Resource<FFMapTileEntity>>();
        while (iter.hasNext()) {
            resources.add(getMapTileResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link FFMapTileEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link FFMapTileEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<FFMapTileEntity>> getById(
            @PathVariable final Long id) {
        FFMapTileEntity entity = repository.findOne(id);
        List<Resource<FFMapTileEntity>> resources =
                new ArrayList<Resource<FFMapTileEntity>>();
        resources.add(getMapTileResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link FFMapTileEntity}.
     * @param entity the {@link FFMapTileEntity}
     * @return {@link Resource}<{@link FFMapTileEntity}>
     */
    private Resource<FFMapTileEntity> getMapTileResource(
            final FFMapTileEntity entity) {
        Resource<FFMapTileEntity> resource =
                new Resource<FFMapTileEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link FFMapTileEntity}s.
     * @param entities the list of {@link FFMapTileEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFMapTileEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<FFMapTileEntity>> save(
            @RequestBody final List<FFMapTileEntity> entities) {
        List<Resource<FFMapTileEntity>> resources =
                new ArrayList<Resource<FFMapTileEntity>>();
        Iterator<FFMapTileEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link FFMapTileEntity}.
     * @param entity the {@link FFMapTileEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFMapTileEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<FFMapTileEntity>> save(
            @RequestBody final FFMapTileEntity entity) {
    
    
        FFMapTileEntity savedEntity = repository.save(entity);
        List<Resource<FFMapTileEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link FFMapTileEntity} instance
     */
    private void setIdFromRepository(final FFMapTileEntity entity) {
        List<FFMapTileEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = FFMapTileEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity FFMapTileEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<FFMapTileEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = FFMapTileEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity FFMapTileEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<FFMapTileEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity FFMapTileEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link FFMapTileEntity}s.
     * @param entities the list of {@link FFMapTileEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFMapTileEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<FFMapTileEntity>> update(
            @RequestBody final List<FFMapTileEntity> entities) {
        List<Resource<FFMapTileEntity>> resources = new ArrayList<Resource<FFMapTileEntity>>();
        Iterator<FFMapTileEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link FFMapTileEntity}.
     * @param entity the {@link FFMapTileEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFMapTileEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<FFMapTileEntity>> update(
            @RequestBody final FFMapTileEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        FFMapTileEntity savedEntity = repository.save(entity);
        List<Resource<FFMapTileEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link FFMapTileEntity}s that share a name.
     * @param name the map_tile' name
     * @return {@link List}<{@link Resource}<{@link FFMapTileEntity}>>
     */
    @RequestMapping(path = "name/{name}",
            method = RequestMethod.GET)
    public List<Resource<FFMapTileEntity>> getByName(
            @PathVariable final String name) {
        Iterator<FFMapTileEntity> iter = repository.findByName(name)
                .iterator();
        List<Resource<FFMapTileEntity>> resources =
                new ArrayList<Resource<FFMapTileEntity>>();
        while (iter.hasNext()) {
            resources.add(getMapTileResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFMapTileEntity}s that share a codeNumber.
     * @param codeNumber the map_tile' codeNumber
     * @return {@link List}<{@link Resource}<{@link FFMapTileEntity}>>
     */
    @RequestMapping(path = "code_number/{codeNumber}",
            method = RequestMethod.GET)
    public List<Resource<FFMapTileEntity>> getByCodeNumber(
            @PathVariable final Long codeNumber) {
        Iterator<FFMapTileEntity> iter = repository.findByCodeNumber(codeNumber)
                .iterator();
        List<Resource<FFMapTileEntity>> resources =
                new ArrayList<Resource<FFMapTileEntity>>();
        while (iter.hasNext()) {
            resources.add(getMapTileResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
