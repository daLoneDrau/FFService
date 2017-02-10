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

import com.osrapi.models.ff.FFDoorEntity;

import com.osrapi.repositories.ff.FFDoorRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/ff/doors")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FFDoorController {
    /** the static instance of {@link FFDoorController}. */
    private static FFDoorController instance;
    /**
     * Gets the static instance.
     * @return {@link FFDoorController}
     */
    public static FFDoorController getInstance() {
        if (instance == null) {
            new FFDoorController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private FFDoorRepository repository;
    /** Creates a new instance of {@link FFDoorController}. */
    public FFDoorController() {
        instance = this;
    }
    /**
     * Gets a list of {@link FFDoorEntity}s.
     * @return {@link List}<{@link Resource}<{@link FFDoorEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<FFDoorEntity>> getAll() {
        Iterator<FFDoorEntity> iter = repository.findAll()
                .iterator();
        List<Resource<FFDoorEntity>> resources =
                new ArrayList<Resource<FFDoorEntity>>();
        while (iter.hasNext()) {
            resources.add(getDoorResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link FFDoorEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link FFDoorEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<FFDoorEntity>> getById(
            @PathVariable final Long id) {
        FFDoorEntity entity = repository.findOne(id);
        List<Resource<FFDoorEntity>> resources =
                new ArrayList<Resource<FFDoorEntity>>();
        resources.add(getDoorResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link FFDoorEntity}.
     * @param entity the {@link FFDoorEntity}
     * @return {@link Resource}<{@link FFDoorEntity}>
     */
    private Resource<FFDoorEntity> getDoorResource(
            final FFDoorEntity entity) {
        Resource<FFDoorEntity> resource =
                new Resource<FFDoorEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link FFDoorEntity}s.
     * @param entities the list of {@link FFDoorEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFDoorEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<FFDoorEntity>> save(
            @RequestBody final List<FFDoorEntity> entities) {
        List<Resource<FFDoorEntity>> resources =
                new ArrayList<Resource<FFDoorEntity>>();
        Iterator<FFDoorEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link FFDoorEntity}.
     * @param entity the {@link FFDoorEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFDoorEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<FFDoorEntity>> save(
            @RequestBody final FFDoorEntity entity) {
    
    
        FFDoorEntity savedEntity = repository.save(entity);
        List<Resource<FFDoorEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link FFDoorEntity} instance
     */
    private void setIdFromRepository(final FFDoorEntity entity) {
        List<FFDoorEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = FFDoorEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity FFDoorEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<FFDoorEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = FFDoorEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity FFDoorEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<FFDoorEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity FFDoorEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link FFDoorEntity}s.
     * @param entities the list of {@link FFDoorEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFDoorEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<FFDoorEntity>> update(
            @RequestBody final List<FFDoorEntity> entities) {
        List<Resource<FFDoorEntity>> resources = new ArrayList<Resource<FFDoorEntity>>();
        Iterator<FFDoorEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link FFDoorEntity}.
     * @param entity the {@link FFDoorEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFDoorEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<FFDoorEntity>> update(
            @RequestBody final FFDoorEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        FFDoorEntity savedEntity = repository.save(entity);
        List<Resource<FFDoorEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link FFDoorEntity}s that share a attributeTest.
     * @param attributeTest the door' attributeTest
     * @return {@link List}<{@link Resource}<{@link FFDoorEntity}>>
     */
    @RequestMapping(path = "attribute_test/{attributeTest}",
            method = RequestMethod.GET)
    public List<Resource<FFDoorEntity>> getByAttributeTest(
            @PathVariable final String attributeTest) {
        Iterator<FFDoorEntity> iter = repository.findByAttributeTest(attributeTest)
                .iterator();
        List<Resource<FFDoorEntity>> resources =
                new ArrayList<Resource<FFDoorEntity>>();
        while (iter.hasNext()) {
            resources.add(getDoorResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFDoorEntity}s that share a direction.
     * @param direction the door' direction
     * @return {@link List}<{@link Resource}<{@link FFDoorEntity}>>
     */
    @RequestMapping(path = "direction/{direction}",
            method = RequestMethod.GET)
    public List<Resource<FFDoorEntity>> getByDirection(
            @PathVariable final String direction) {
        Iterator<FFDoorEntity> iter = repository.findByDirection(direction)
                .iterator();
        List<Resource<FFDoorEntity>> resources =
                new ArrayList<Resource<FFDoorEntity>>();
        while (iter.hasNext()) {
            resources.add(getDoorResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFDoorEntity}s that share a leadsTo.
     * @param leadsTo the door' leadsTo
     * @return {@link List}<{@link Resource}<{@link FFDoorEntity}>>
     */
    @RequestMapping(path = "leads_to/{leadsTo}",
            method = RequestMethod.GET)
    public List<Resource<FFDoorEntity>> getByLeadsTo(
            @PathVariable final String leadsTo) {
        Iterator<FFDoorEntity> iter = repository.findByLeadsTo(leadsTo)
                .iterator();
        List<Resource<FFDoorEntity>> resources =
                new ArrayList<Resource<FFDoorEntity>>();
        while (iter.hasNext()) {
            resources.add(getDoorResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFDoorEntity}s that share a locked.
     * @param locked the door' locked
     * @return {@link List}<{@link Resource}<{@link FFDoorEntity}>>
     */
    @RequestMapping(path = "locked/{locked}",
            method = RequestMethod.GET)
    public List<Resource<FFDoorEntity>> getByLocked(
            @PathVariable final Boolean locked) {
        Iterator<FFDoorEntity> iter = repository.findByLocked(locked)
                .iterator();
        List<Resource<FFDoorEntity>> resources =
                new ArrayList<Resource<FFDoorEntity>>();
        while (iter.hasNext()) {
            resources.add(getDoorResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFDoorEntity}s that share a name.
     * @param name the door' name
     * @return {@link List}<{@link Resource}<{@link FFDoorEntity}>>
     */
    @RequestMapping(path = "name/{name}",
            method = RequestMethod.GET)
    public List<Resource<FFDoorEntity>> getByName(
            @PathVariable final String name) {
        Iterator<FFDoorEntity> iter = repository.findByName(name)
                .iterator();
        List<Resource<FFDoorEntity>> resources =
                new ArrayList<Resource<FFDoorEntity>>();
        while (iter.hasNext()) {
            resources.add(getDoorResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFDoorEntity}s that share a numDiceRoll.
     * @param numDiceRoll the door' numDiceRoll
     * @return {@link List}<{@link Resource}<{@link FFDoorEntity}>>
     */
    @RequestMapping(path = "num_dice_roll/{numDiceRoll}",
            method = RequestMethod.GET)
    public List<Resource<FFDoorEntity>> getByNumDiceRoll(
            @PathVariable final Long numDiceRoll) {
        Iterator<FFDoorEntity> iter = repository.findByNumDiceRoll(numDiceRoll)
                .iterator();
        List<Resource<FFDoorEntity>> resources =
                new ArrayList<Resource<FFDoorEntity>>();
        while (iter.hasNext()) {
            resources.add(getDoorResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFDoorEntity}s that share a title.
     * @param title the door' title
     * @return {@link List}<{@link Resource}<{@link FFDoorEntity}>>
     */
    @RequestMapping(path = "title/{title}",
            method = RequestMethod.GET)
    public List<Resource<FFDoorEntity>> getByTitle(
            @PathVariable final String title) {
        Iterator<FFDoorEntity> iter = repository.findByTitle(title)
                .iterator();
        List<Resource<FFDoorEntity>> resources =
                new ArrayList<Resource<FFDoorEntity>>();
        while (iter.hasNext()) {
            resources.add(getDoorResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
