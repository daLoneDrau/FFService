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

import com.osrapi.models.ff.FFScriptActionEntity;

import com.osrapi.repositories.ff.FFScriptActionRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/ff/script_actions")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FFScriptActionController {
    /** the static instance of {@link FFScriptActionController}. */
    private static FFScriptActionController instance;
    /**
     * Gets the static instance.
     * @return {@link FFScriptActionController}
     */
    public static FFScriptActionController getInstance() {
        if (instance == null) {
            new FFScriptActionController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private FFScriptActionRepository repository;
    /** Creates a new instance of {@link FFScriptActionController}. */
    public FFScriptActionController() {
        instance = this;
    }
    /**
     * Gets a list of {@link FFScriptActionEntity}s.
     * @return {@link List}<{@link Resource}<{@link FFScriptActionEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<FFScriptActionEntity>> getAll() {
        Iterator<FFScriptActionEntity> iter = repository.findAll()
                .iterator();
        List<Resource<FFScriptActionEntity>> resources =
                new ArrayList<Resource<FFScriptActionEntity>>();
        while (iter.hasNext()) {
            resources.add(getScriptActionResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link FFScriptActionEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link FFScriptActionEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<FFScriptActionEntity>> getById(
            @PathVariable final Long id) {
        FFScriptActionEntity entity = repository.findOne(id);
        List<Resource<FFScriptActionEntity>> resources =
                new ArrayList<Resource<FFScriptActionEntity>>();
        resources.add(getScriptActionResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link FFScriptActionEntity}.
     * @param entity the {@link FFScriptActionEntity}
     * @return {@link Resource}<{@link FFScriptActionEntity}>
     */
    private Resource<FFScriptActionEntity> getScriptActionResource(
            final FFScriptActionEntity entity) {
        Resource<FFScriptActionEntity> resource =
                new Resource<FFScriptActionEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link FFScriptActionEntity}s.
     * @param entities the list of {@link FFScriptActionEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFScriptActionEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<FFScriptActionEntity>> save(
            @RequestBody final List<FFScriptActionEntity> entities) {
        List<Resource<FFScriptActionEntity>> resources =
                new ArrayList<Resource<FFScriptActionEntity>>();
        Iterator<FFScriptActionEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link FFScriptActionEntity}.
     * @param entity the {@link FFScriptActionEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFScriptActionEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<FFScriptActionEntity>> save(
            @RequestBody final FFScriptActionEntity entity) {
    
    
        FFScriptActionEntity savedEntity = repository.save(entity);
        List<Resource<FFScriptActionEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link FFScriptActionEntity} instance
     */
    private void setIdFromRepository(final FFScriptActionEntity entity) {
        List<FFScriptActionEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = FFScriptActionEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity FFScriptActionEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<FFScriptActionEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = FFScriptActionEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity FFScriptActionEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<FFScriptActionEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity FFScriptActionEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link FFScriptActionEntity}s.
     * @param entities the list of {@link FFScriptActionEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFScriptActionEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<FFScriptActionEntity>> update(
            @RequestBody final List<FFScriptActionEntity> entities) {
        List<Resource<FFScriptActionEntity>> resources = new ArrayList<Resource<FFScriptActionEntity>>();
        Iterator<FFScriptActionEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link FFScriptActionEntity}.
     * @param entity the {@link FFScriptActionEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFScriptActionEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<FFScriptActionEntity>> update(
            @RequestBody final FFScriptActionEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
    
    
        FFScriptActionEntity savedEntity = repository.save(entity);
        List<Resource<FFScriptActionEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

    /**
     * Gets a list of {@link FFScriptActionEntity}s that share a name.
     * @param name the script_action' name
     * @return {@link List}<{@link Resource}<{@link FFScriptActionEntity}>>
     */
    @RequestMapping(path = "name/{name}",
            method = RequestMethod.GET)
    public List<Resource<FFScriptActionEntity>> getByName(
            @PathVariable final String name) {
        Iterator<FFScriptActionEntity> iter = repository.findByName(name)
                .iterator();
        List<Resource<FFScriptActionEntity>> resources =
                new ArrayList<Resource<FFScriptActionEntity>>();
        while (iter.hasNext()) {
            resources.add(getScriptActionResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFScriptActionEntity}s that share a type.
     * @param type the script_action' type
     * @return {@link List}<{@link Resource}<{@link FFScriptActionEntity}>>
     */
    @RequestMapping(path = "type/{type}",
            method = RequestMethod.GET)
    public List<Resource<FFScriptActionEntity>> getByType(
            @PathVariable final String type) {
        Iterator<FFScriptActionEntity> iter = repository.findByType(type)
                .iterator();
        List<Resource<FFScriptActionEntity>> resources =
                new ArrayList<Resource<FFScriptActionEntity>>();
        while (iter.hasNext()) {
            resources.add(getScriptActionResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFScriptActionEntity}s that share a origin.
     * @param origin the script_action' origin
     * @return {@link List}<{@link Resource}<{@link FFScriptActionEntity}>>
     */
    @RequestMapping(path = "origin/{origin}",
            method = RequestMethod.GET)
    public List<Resource<FFScriptActionEntity>> getByOrigin(
            @PathVariable final String origin) {
        Iterator<FFScriptActionEntity> iter = repository.findByOrigin(origin)
                .iterator();
        List<Resource<FFScriptActionEntity>> resources =
                new ArrayList<Resource<FFScriptActionEntity>>();
        while (iter.hasNext()) {
            resources.add(getScriptActionResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFScriptActionEntity}s that share a direction.
     * @param direction the script_action' direction
     * @return {@link List}<{@link Resource}<{@link FFScriptActionEntity}>>
     */
    @RequestMapping(path = "direction/{direction}",
            method = RequestMethod.GET)
    public List<Resource<FFScriptActionEntity>> getByDirection(
            @PathVariable final String direction) {
        Iterator<FFScriptActionEntity> iter = repository.findByDirection(direction)
                .iterator();
        List<Resource<FFScriptActionEntity>> resources =
                new ArrayList<Resource<FFScriptActionEntity>>();
        while (iter.hasNext()) {
            resources.add(getScriptActionResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFScriptActionEntity}s that share a destination.
     * @param destination the script_action' destination
     * @return {@link List}<{@link Resource}<{@link FFScriptActionEntity}>>
     */
    @RequestMapping(path = "destination/{destination}",
            method = RequestMethod.GET)
    public List<Resource<FFScriptActionEntity>> getByDestination(
            @PathVariable final String destination) {
        Iterator<FFScriptActionEntity> iter = repository.findByDestination(destination)
                .iterator();
        List<Resource<FFScriptActionEntity>> resources =
                new ArrayList<Resource<FFScriptActionEntity>>();
        while (iter.hasNext()) {
            resources.add(getScriptActionResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFScriptActionEntity}s that share a numDieRolled.
     * @param numDieRolled the script_action' numDieRolled
     * @return {@link List}<{@link Resource}<{@link FFScriptActionEntity}>>
     */
    @RequestMapping(path = "num_die_rolled/{numDieRolled}",
            method = RequestMethod.GET)
    public List<Resource<FFScriptActionEntity>> getByNumDieRolled(
            @PathVariable final Long numDieRolled) {
        Iterator<FFScriptActionEntity> iter = repository.findByNumDieRolled(numDieRolled)
                .iterator();
        List<Resource<FFScriptActionEntity>> resources =
                new ArrayList<Resource<FFScriptActionEntity>>();
        while (iter.hasNext()) {
            resources.add(getScriptActionResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFScriptActionEntity}s that share a isDieRoll.
     * @param isDieRoll the script_action' isDieRoll
     * @return {@link List}<{@link Resource}<{@link FFScriptActionEntity}>>
     */
    @RequestMapping(path = "is_die_roll/{isDieRoll}",
            method = RequestMethod.GET)
    public List<Resource<FFScriptActionEntity>> getByIsDieRoll(
            @PathVariable final Boolean isDieRoll) {
        Iterator<FFScriptActionEntity> iter = repository.findByIsDieRoll(isDieRoll)
                .iterator();
        List<Resource<FFScriptActionEntity>> resources =
                new ArrayList<Resource<FFScriptActionEntity>>();
        while (iter.hasNext()) {
            resources.add(getScriptActionResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFScriptActionEntity}s that share a attribute.
     * @param attribute the script_action' attribute
     * @return {@link List}<{@link Resource}<{@link FFScriptActionEntity}>>
     */
    @RequestMapping(path = "attribute/{attribute}",
            method = RequestMethod.GET)
    public List<Resource<FFScriptActionEntity>> getByAttribute(
            @PathVariable final String attribute) {
        Iterator<FFScriptActionEntity> iter = repository.findByAttribute(attribute)
                .iterator();
        List<Resource<FFScriptActionEntity>> resources =
                new ArrayList<Resource<FFScriptActionEntity>>();
        while (iter.hasNext()) {
            resources.add(getScriptActionResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFScriptActionEntity}s that share a amount.
     * @param amount the script_action' amount
     * @return {@link List}<{@link Resource}<{@link FFScriptActionEntity}>>
     */
    @RequestMapping(path = "amount/{amount}",
            method = RequestMethod.GET)
    public List<Resource<FFScriptActionEntity>> getByAmount(
            @PathVariable final Long amount) {
        Iterator<FFScriptActionEntity> iter = repository.findByAmount(amount)
                .iterator();
        List<Resource<FFScriptActionEntity>> resources =
                new ArrayList<Resource<FFScriptActionEntity>>();
        while (iter.hasNext()) {
            resources.add(getScriptActionResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFScriptActionEntity}s that share a textName.
     * @param textName the script_action' textName
     * @return {@link List}<{@link Resource}<{@link FFScriptActionEntity}>>
     */
    @RequestMapping(path = "text_name/{textName}",
            method = RequestMethod.GET)
    public List<Resource<FFScriptActionEntity>> getByTextName(
            @PathVariable final String textName) {
        Iterator<FFScriptActionEntity> iter = repository.findByTextName(textName)
                .iterator();
        List<Resource<FFScriptActionEntity>> resources =
                new ArrayList<Resource<FFScriptActionEntity>>();
        while (iter.hasNext()) {
            resources.add(getScriptActionResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFScriptActionEntity}s that share a roomCode.
     * @param roomCode the script_action' roomCode
     * @return {@link List}<{@link Resource}<{@link FFScriptActionEntity}>>
     */
    @RequestMapping(path = "room_code/{roomCode}",
            method = RequestMethod.GET)
    public List<Resource<FFScriptActionEntity>> getByRoomCode(
            @PathVariable final String roomCode) {
        Iterator<FFScriptActionEntity> iter = repository.findByRoomCode(roomCode)
                .iterator();
        List<Resource<FFScriptActionEntity>> resources =
                new ArrayList<Resource<FFScriptActionEntity>>();
        while (iter.hasNext()) {
            resources.add(getScriptActionResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFScriptActionEntity}s that share a doorName.
     * @param doorName the script_action' doorName
     * @return {@link List}<{@link Resource}<{@link FFScriptActionEntity}>>
     */
    @RequestMapping(path = "door_name/{doorName}",
            method = RequestMethod.GET)
    public List<Resource<FFScriptActionEntity>> getByDoorName(
            @PathVariable final String doorName) {
        Iterator<FFScriptActionEntity> iter = repository.findByDoorName(doorName)
                .iterator();
        List<Resource<FFScriptActionEntity>> resources =
                new ArrayList<Resource<FFScriptActionEntity>>();
        while (iter.hasNext()) {
            resources.add(getScriptActionResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFScriptActionEntity}s that share a error.
     * @param error the script_action' error
     * @return {@link List}<{@link Resource}<{@link FFScriptActionEntity}>>
     */
    @RequestMapping(path = "error/{error}",
            method = RequestMethod.GET)
    public List<Resource<FFScriptActionEntity>> getByError(
            @PathVariable final Boolean error) {
        Iterator<FFScriptActionEntity> iter = repository.findByError(error)
                .iterator();
        List<Resource<FFScriptActionEntity>> resources =
                new ArrayList<Resource<FFScriptActionEntity>>();
        while (iter.hasNext()) {
            resources.add(getScriptActionResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFScriptActionEntity}s that share a mobCode.
     * @param mobCode the script_action' mobCode
     * @return {@link List}<{@link Resource}<{@link FFScriptActionEntity}>>
     */
    @RequestMapping(path = "mob_code/{mobCode}",
            method = RequestMethod.GET)
    public List<Resource<FFScriptActionEntity>> getByMobCode(
            @PathVariable final String mobCode) {
        Iterator<FFScriptActionEntity> iter = repository.findByMobCode(mobCode)
                .iterator();
        List<Resource<FFScriptActionEntity>> resources =
                new ArrayList<Resource<FFScriptActionEntity>>();
        while (iter.hasNext()) {
            resources.add(getScriptActionResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFScriptActionEntity}s that share a passScripts.
     * @param passScripts the script_action' passScripts
     * @return {@link List}<{@link Resource}<{@link FFScriptActionEntity}>>
     */
    @RequestMapping(path = "pass_scripts/{passScripts}",
            method = RequestMethod.GET)
    public List<Resource<FFScriptActionEntity>> getByPassScripts(
            @PathVariable final String passScripts) {
        Iterator<FFScriptActionEntity> iter = repository.findByPassScripts(passScripts)
                .iterator();
        List<Resource<FFScriptActionEntity>> resources =
                new ArrayList<Resource<FFScriptActionEntity>>();
        while (iter.hasNext()) {
            resources.add(getScriptActionResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFScriptActionEntity}s that share a failScripts.
     * @param failScripts the script_action' failScripts
     * @return {@link List}<{@link Resource}<{@link FFScriptActionEntity}>>
     */
    @RequestMapping(path = "fail_scripts/{failScripts}",
            method = RequestMethod.GET)
    public List<Resource<FFScriptActionEntity>> getByFailScripts(
            @PathVariable final String failScripts) {
        Iterator<FFScriptActionEntity> iter = repository.findByFailScripts(failScripts)
                .iterator();
        List<Resource<FFScriptActionEntity>> resources =
                new ArrayList<Resource<FFScriptActionEntity>>();
        while (iter.hasNext()) {
            resources.add(getScriptActionResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
