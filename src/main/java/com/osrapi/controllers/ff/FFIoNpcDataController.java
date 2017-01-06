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

import com.osrapi.models.ff.FFIoNpcDataEntity;
import com.osrapi.models.ff.FFGenderEntity;

import com.osrapi.repositories.ff.FFIoNpcDataRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/ff/io_npc_data")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FFIoNpcDataController {
    /** the static instance of {@link FFIoNpcDataController}. */
    private static FFIoNpcDataController instance;
    /**
     * Gets the static instance.
     * @return {@link FFIoNpcDataController}
     */
    public static FFIoNpcDataController getInstance() {
        if (instance == null) {
            new FFIoNpcDataController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private FFIoNpcDataRepository repository;
    /** Creates a new instance of {@link FFIoNpcDataController}. */
    public FFIoNpcDataController() {
        instance = this;
    }
    /**
     * Gets a list of {@link FFIoNpcDataEntity}s.
     * @return {@link List}<{@link Resource}<{@link FFIoNpcDataEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<FFIoNpcDataEntity>> getAll() {
        Iterator<FFIoNpcDataEntity> iter = repository.findAll()
                .iterator();
        List<Resource<FFIoNpcDataEntity>> resources =
                new ArrayList<Resource<FFIoNpcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoNpcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link FFIoNpcDataEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link FFIoNpcDataEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<FFIoNpcDataEntity>> getById(
            @PathVariable final Long id) {
        FFIoNpcDataEntity entity = repository.findOne(id);
        List<Resource<FFIoNpcDataEntity>> resources =
                new ArrayList<Resource<FFIoNpcDataEntity>>();
        resources.add(getIoNpcDataResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link FFIoNpcDataEntity}.
     * @param entity the {@link FFIoNpcDataEntity}
     * @return {@link Resource}<{@link FFIoNpcDataEntity}>
     */
    private Resource<FFIoNpcDataEntity> getIoNpcDataResource(
            final FFIoNpcDataEntity entity) {
        Resource<FFIoNpcDataEntity> resource =
                new Resource<FFIoNpcDataEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link FFIoNpcDataEntity}s.
     * @param entities the list of {@link FFIoNpcDataEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFIoNpcDataEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<FFIoNpcDataEntity>> save(
            @RequestBody final List<FFIoNpcDataEntity> entities) {
        List<Resource<FFIoNpcDataEntity>> resources =
                new ArrayList<Resource<FFIoNpcDataEntity>>();
        Iterator<FFIoNpcDataEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link FFIoNpcDataEntity}.
     * @param entity the {@link FFIoNpcDataEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFIoNpcDataEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<FFIoNpcDataEntity>> save(
            @RequestBody final FFIoNpcDataEntity entity) {
            if (entity.getGender() != null
        && entity.getGender().getId() == null) {
      setGenderIdFromRepository(entity);
        }


    
        FFIoNpcDataEntity savedEntity = repository.save(entity);
        List<Resource<FFIoNpcDataEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link FFIoNpcDataEntity} instance
     */
    private void setIdFromRepository(final FFIoNpcDataEntity entity) {
        List<FFIoNpcDataEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = FFIoNpcDataEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<FFIoNpcDataEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = FFIoNpcDataEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<FFIoNpcDataEntity>) method.invoke(
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
     * Updates multiple {@link FFIoNpcDataEntity}s.
     * @param entities the list of {@link FFIoNpcDataEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFIoNpcDataEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<FFIoNpcDataEntity>> update(
            @RequestBody final List<FFIoNpcDataEntity> entities) {
        List<Resource<FFIoNpcDataEntity>> resources = new ArrayList<Resource<FFIoNpcDataEntity>>();
        Iterator<FFIoNpcDataEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link FFIoNpcDataEntity}.
     * @param entity the {@link FFIoNpcDataEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFIoNpcDataEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<FFIoNpcDataEntity>> update(
            @RequestBody final FFIoNpcDataEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
            if (entity.getGender() != null
        && entity.getGender().getId() == null) {
      setGenderIdFromRepository(entity);
        }


    
        FFIoNpcDataEntity savedEntity = repository.save(entity);
        List<Resource<FFIoNpcDataEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

  private void setGenderIdFromRepository(
      final FFIoNpcDataEntity entity) {
    FFGenderEntity memberEntity = null;
    List<Resource<FFGenderEntity>> list = null;
    try {
      Method method = null;
      Field field = null;
      try {
        method = FFGenderController.class.getDeclaredMethod(
            "getByName", new Class[] { String.class });
        field = FFGenderEntity.class.getDeclaredField("name");
      } catch (NoSuchMethodException | NoSuchFieldException e) {
      }
      if (method != null
          && field != null) {
        field.setAccessible(true);
        if (field.get(entity.getGender()) != null) {
          list = (List<Resource<FFGenderEntity>>) method
              .invoke(
                  FFGenderController.getInstance(),
                  (String) field
                      .get(entity.getGender()));
        }
      }
      if (list == null) {
        try {
          method = FFGenderController.class.getDeclaredMethod(
              "getByCode", new Class[] { String.class });
          field = FFGenderEntity.class
              .getDeclaredField("code");
        } catch (NoSuchMethodException | NoSuchFieldException e) {
        }
        if (method != null
            && field != null) {
          field.setAccessible(true);
          if (field.get(entity.getGender()) != null) {
            list = (List<Resource<FFGenderEntity>>)
                method.invoke(FFGenderController
                    .getInstance(),(String) field.get(
                        entity.getGender()));
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
      memberEntity = (FFGenderEntity)
          ((Resource) FFGenderController.getInstance().save(
              entity.getGender()).get(0)).getContent();
    }
    entity.setGender(memberEntity);
    list = null;
    }


    /**
     * Gets a list of {@link FFIoNpcDataEntity}s that share a behavior.
     * @param behavior the io_npc_data' behavior
     * @return {@link List}<{@link Resource}<{@link FFIoNpcDataEntity}>>
     */
    @RequestMapping(path = "behavior/{behavior}",
            method = RequestMethod.GET)
    public List<Resource<FFIoNpcDataEntity>> getByBehavior(
            @PathVariable final Long behavior) {
        Iterator<FFIoNpcDataEntity> iter = repository.findByBehavior(behavior)
                .iterator();
        List<Resource<FFIoNpcDataEntity>> resources =
                new ArrayList<Resource<FFIoNpcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoNpcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFIoNpcDataEntity}s that share a behaviorParam.
     * @param behaviorParam the io_npc_data' behaviorParam
     * @return {@link List}<{@link Resource}<{@link FFIoNpcDataEntity}>>
     */
    @RequestMapping(path = "behavior_param/{behaviorParam}",
            method = RequestMethod.GET)
    public List<Resource<FFIoNpcDataEntity>> getByBehaviorParam(
            @PathVariable final Float behaviorParam) {
        Iterator<FFIoNpcDataEntity> iter = repository.findByBehaviorParam(behaviorParam)
                .iterator();
        List<Resource<FFIoNpcDataEntity>> resources =
                new ArrayList<Resource<FFIoNpcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoNpcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFIoNpcDataEntity}s that share a climbCount.
     * @param climbCount the io_npc_data' climbCount
     * @return {@link List}<{@link Resource}<{@link FFIoNpcDataEntity}>>
     */
    @RequestMapping(path = "climb_count/{climbCount}",
            method = RequestMethod.GET)
    public List<Resource<FFIoNpcDataEntity>> getByClimbCount(
            @PathVariable final Float climbCount) {
        Iterator<FFIoNpcDataEntity> iter = repository.findByClimbCount(climbCount)
                .iterator();
        List<Resource<FFIoNpcDataEntity>> resources =
                new ArrayList<Resource<FFIoNpcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoNpcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFIoNpcDataEntity}s that share a collidState.
     * @param collidState the io_npc_data' collidState
     * @return {@link List}<{@link Resource}<{@link FFIoNpcDataEntity}>>
     */
    @RequestMapping(path = "collid_state/{collidState}",
            method = RequestMethod.GET)
    public List<Resource<FFIoNpcDataEntity>> getByCollidState(
            @PathVariable final Long collidState) {
        Iterator<FFIoNpcDataEntity> iter = repository.findByCollidState(collidState)
                .iterator();
        List<Resource<FFIoNpcDataEntity>> resources =
                new ArrayList<Resource<FFIoNpcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoNpcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFIoNpcDataEntity}s that share a collidTime.
     * @param collidTime the io_npc_data' collidTime
     * @return {@link List}<{@link Resource}<{@link FFIoNpcDataEntity}>>
     */
    @RequestMapping(path = "collid_time/{collidTime}",
            method = RequestMethod.GET)
    public List<Resource<FFIoNpcDataEntity>> getByCollidTime(
            @PathVariable final Long collidTime) {
        Iterator<FFIoNpcDataEntity> iter = repository.findByCollidTime(collidTime)
                .iterator();
        List<Resource<FFIoNpcDataEntity>> resources =
                new ArrayList<Resource<FFIoNpcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoNpcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFIoNpcDataEntity}s that share a critical.
     * @param critical the io_npc_data' critical
     * @return {@link List}<{@link Resource}<{@link FFIoNpcDataEntity}>>
     */
    @RequestMapping(path = "critical/{critical}",
            method = RequestMethod.GET)
    public List<Resource<FFIoNpcDataEntity>> getByCritical(
            @PathVariable final Float critical) {
        Iterator<FFIoNpcDataEntity> iter = repository.findByCritical(critical)
                .iterator();
        List<Resource<FFIoNpcDataEntity>> resources =
                new ArrayList<Resource<FFIoNpcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoNpcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFIoNpcDataEntity}s that share a cut.
     * @param cut the io_npc_data' cut
     * @return {@link List}<{@link Resource}<{@link FFIoNpcDataEntity}>>
     */
    @RequestMapping(path = "cut/{cut}",
            method = RequestMethod.GET)
    public List<Resource<FFIoNpcDataEntity>> getByCut(
            @PathVariable final Boolean cut) {
        Iterator<FFIoNpcDataEntity> iter = repository.findByCut(cut)
                .iterator();
        List<Resource<FFIoNpcDataEntity>> resources =
                new ArrayList<Resource<FFIoNpcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoNpcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFIoNpcDataEntity}s that share a cuts.
     * @param cuts the io_npc_data' cuts
     * @return {@link List}<{@link Resource}<{@link FFIoNpcDataEntity}>>
     */
    @RequestMapping(path = "cuts/{cuts}",
            method = RequestMethod.GET)
    public List<Resource<FFIoNpcDataEntity>> getByCuts(
            @PathVariable final Long cuts) {
        Iterator<FFIoNpcDataEntity> iter = repository.findByCuts(cuts)
                .iterator();
        List<Resource<FFIoNpcDataEntity>> resources =
                new ArrayList<Resource<FFIoNpcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoNpcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFIoNpcDataEntity}s that share a damages.
     * @param damages the io_npc_data' damages
     * @return {@link List}<{@link Resource}<{@link FFIoNpcDataEntity}>>
     */
    @RequestMapping(path = "damages/{damages}",
            method = RequestMethod.GET)
    public List<Resource<FFIoNpcDataEntity>> getByDamages(
            @PathVariable final Float damages) {
        Iterator<FFIoNpcDataEntity> iter = repository.findByDamages(damages)
                .iterator();
        List<Resource<FFIoNpcDataEntity>> resources =
                new ArrayList<Resource<FFIoNpcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoNpcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFIoNpcDataEntity}s that share a internalScript.
     * @param internalScript the io_npc_data' internalScript
     * @return {@link List}<{@link Resource}<{@link FFIoNpcDataEntity}>>
     */
    @RequestMapping(path = "internal_script/{internalScript}",
            method = RequestMethod.GET)
    public List<Resource<FFIoNpcDataEntity>> getByInternalScript(
            @PathVariable final String internalScript) {
        Iterator<FFIoNpcDataEntity> iter = repository.findByInternalScript(internalScript)
                .iterator();
        List<Resource<FFIoNpcDataEntity>> resources =
                new ArrayList<Resource<FFIoNpcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoNpcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFIoNpcDataEntity}s that share a life.
     * @param life the io_npc_data' life
     * @return {@link List}<{@link Resource}<{@link FFIoNpcDataEntity}>>
     */
    @RequestMapping(path = "life/{life}",
            method = RequestMethod.GET)
    public List<Resource<FFIoNpcDataEntity>> getByLife(
            @PathVariable final Float life) {
        Iterator<FFIoNpcDataEntity> iter = repository.findByLife(life)
                .iterator();
        List<Resource<FFIoNpcDataEntity>> resources =
                new ArrayList<Resource<FFIoNpcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoNpcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFIoNpcDataEntity}s that share a mana.
     * @param mana the io_npc_data' mana
     * @return {@link List}<{@link Resource}<{@link FFIoNpcDataEntity}>>
     */
    @RequestMapping(path = "mana/{mana}",
            method = RequestMethod.GET)
    public List<Resource<FFIoNpcDataEntity>> getByMana(
            @PathVariable final Float mana) {
        Iterator<FFIoNpcDataEntity> iter = repository.findByMana(mana)
                .iterator();
        List<Resource<FFIoNpcDataEntity>> resources =
                new ArrayList<Resource<FFIoNpcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoNpcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFIoNpcDataEntity}s that share a maxlife.
     * @param maxlife the io_npc_data' maxlife
     * @return {@link List}<{@link Resource}<{@link FFIoNpcDataEntity}>>
     */
    @RequestMapping(path = "maxlife/{maxlife}",
            method = RequestMethod.GET)
    public List<Resource<FFIoNpcDataEntity>> getByMaxlife(
            @PathVariable final Float maxlife) {
        Iterator<FFIoNpcDataEntity> iter = repository.findByMaxlife(maxlife)
                .iterator();
        List<Resource<FFIoNpcDataEntity>> resources =
                new ArrayList<Resource<FFIoNpcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoNpcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFIoNpcDataEntity}s that share a maxmana.
     * @param maxmana the io_npc_data' maxmana
     * @return {@link List}<{@link Resource}<{@link FFIoNpcDataEntity}>>
     */
    @RequestMapping(path = "maxmana/{maxmana}",
            method = RequestMethod.GET)
    public List<Resource<FFIoNpcDataEntity>> getByMaxmana(
            @PathVariable final Float maxmana) {
        Iterator<FFIoNpcDataEntity> iter = repository.findByMaxmana(maxmana)
                .iterator();
        List<Resource<FFIoNpcDataEntity>> resources =
                new ArrayList<Resource<FFIoNpcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoNpcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFIoNpcDataEntity}s that share a name.
     * @param name the io_npc_data' name
     * @return {@link List}<{@link Resource}<{@link FFIoNpcDataEntity}>>
     */
    @RequestMapping(path = "name/{name}",
            method = RequestMethod.GET)
    public List<Resource<FFIoNpcDataEntity>> getByName(
            @PathVariable final String name) {
        Iterator<FFIoNpcDataEntity> iter = repository.findByName(name)
                .iterator();
        List<Resource<FFIoNpcDataEntity>> resources =
                new ArrayList<Resource<FFIoNpcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoNpcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFIoNpcDataEntity}s that share a npcFlags.
     * @param npcFlags the io_npc_data' npcFlags
     * @return {@link List}<{@link Resource}<{@link FFIoNpcDataEntity}>>
     */
    @RequestMapping(path = "npc_flags/{npcFlags}",
            method = RequestMethod.GET)
    public List<Resource<FFIoNpcDataEntity>> getByNpcFlags(
            @PathVariable final Long npcFlags) {
        Iterator<FFIoNpcDataEntity> iter = repository.findByNpcFlags(npcFlags)
                .iterator();
        List<Resource<FFIoNpcDataEntity>> resources =
                new ArrayList<Resource<FFIoNpcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoNpcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFIoNpcDataEntity}s that share a title.
     * @param title the io_npc_data' title
     * @return {@link List}<{@link Resource}<{@link FFIoNpcDataEntity}>>
     */
    @RequestMapping(path = "title/{title}",
            method = RequestMethod.GET)
    public List<Resource<FFIoNpcDataEntity>> getByTitle(
            @PathVariable final String title) {
        Iterator<FFIoNpcDataEntity> iter = repository.findByTitle(title)
                .iterator();
        List<Resource<FFIoNpcDataEntity>> resources =
                new ArrayList<Resource<FFIoNpcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoNpcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFIoNpcDataEntity}s that share a xpvalue.
     * @param xpvalue the io_npc_data' xpvalue
     * @return {@link List}<{@link Resource}<{@link FFIoNpcDataEntity}>>
     */
    @RequestMapping(path = "xpvalue/{xpvalue}",
            method = RequestMethod.GET)
    public List<Resource<FFIoNpcDataEntity>> getByXpvalue(
            @PathVariable final Long xpvalue) {
        Iterator<FFIoNpcDataEntity> iter = repository.findByXpvalue(xpvalue)
                .iterator();
        List<Resource<FFIoNpcDataEntity>> resources =
                new ArrayList<Resource<FFIoNpcDataEntity>>();
        while (iter.hasNext()) {
            resources.add(getIoNpcDataResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
