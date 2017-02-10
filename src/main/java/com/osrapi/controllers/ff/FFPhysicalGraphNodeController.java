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

import com.osrapi.models.ff.FFPhysicalGraphNodeEntity;
import com.osrapi.models.ff.FFTerrainEntity;

import com.osrapi.repositories.ff.FFPhysicalGraphNodeRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/ff/physical_graph_nodes")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FFPhysicalGraphNodeController {
    /** the static instance of {@link FFPhysicalGraphNodeController}. */
    private static FFPhysicalGraphNodeController instance;
    /**
     * Gets the static instance.
     * @return {@link FFPhysicalGraphNodeController}
     */
    public static FFPhysicalGraphNodeController getInstance() {
        if (instance == null) {
            new FFPhysicalGraphNodeController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private FFPhysicalGraphNodeRepository repository;
    /** Creates a new instance of {@link FFPhysicalGraphNodeController}. */
    public FFPhysicalGraphNodeController() {
        instance = this;
    }
    /**
     * Gets a list of {@link FFPhysicalGraphNodeEntity}s.
     * @return {@link List}<{@link Resource}<{@link FFPhysicalGraphNodeEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<FFPhysicalGraphNodeEntity>> getAll() {
        Iterator<FFPhysicalGraphNodeEntity> iter = repository.findAll()
                .iterator();
        List<Resource<FFPhysicalGraphNodeEntity>> resources =
                new ArrayList<Resource<FFPhysicalGraphNodeEntity>>();
        while (iter.hasNext()) {
            resources.add(getPhysicalGraphNodeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link FFPhysicalGraphNodeEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link FFPhysicalGraphNodeEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<FFPhysicalGraphNodeEntity>> getById(
            @PathVariable final Long id) {
        FFPhysicalGraphNodeEntity entity = repository.findOne(id);
        List<Resource<FFPhysicalGraphNodeEntity>> resources =
                new ArrayList<Resource<FFPhysicalGraphNodeEntity>>();
        resources.add(getPhysicalGraphNodeResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link FFPhysicalGraphNodeEntity}.
     * @param entity the {@link FFPhysicalGraphNodeEntity}
     * @return {@link Resource}<{@link FFPhysicalGraphNodeEntity}>
     */
    private Resource<FFPhysicalGraphNodeEntity> getPhysicalGraphNodeResource(
            final FFPhysicalGraphNodeEntity entity) {
        Resource<FFPhysicalGraphNodeEntity> resource =
                new Resource<FFPhysicalGraphNodeEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link FFPhysicalGraphNodeEntity}s.
     * @param entities the list of {@link FFPhysicalGraphNodeEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFPhysicalGraphNodeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<FFPhysicalGraphNodeEntity>> save(
            @RequestBody final List<FFPhysicalGraphNodeEntity> entities) {
        List<Resource<FFPhysicalGraphNodeEntity>> resources =
                new ArrayList<Resource<FFPhysicalGraphNodeEntity>>();
        Iterator<FFPhysicalGraphNodeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link FFPhysicalGraphNodeEntity}.
     * @param entity the {@link FFPhysicalGraphNodeEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFPhysicalGraphNodeEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<FFPhysicalGraphNodeEntity>> save(
            @RequestBody final FFPhysicalGraphNodeEntity entity) {
            if (entity.getTerrain() != null
        && entity.getTerrain().getId() == null) {
      setTerrainIdFromRepository(entity);
        }


    
        FFPhysicalGraphNodeEntity savedEntity = repository.save(entity);
        List<Resource<FFPhysicalGraphNodeEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link FFPhysicalGraphNodeEntity} instance
     */
    private void setIdFromRepository(final FFPhysicalGraphNodeEntity entity) {
        List<FFPhysicalGraphNodeEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = FFPhysicalGraphNodeEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                System.out.println("Cannot get Entity FFPhysicalGraphNodeEntity from Repository by name");
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<FFPhysicalGraphNodeEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = FFPhysicalGraphNodeEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
          System.out.println("Cannot get Entity FFPhysicalGraphNodeEntity from Repository by code");
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<FFPhysicalGraphNodeEntity>) method.invoke(
                                repository, (String) field.get(entity));
                    }
                }
            }
            method = null;
            field = null;
        } catch (SecurityException | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
                System.out.println("Cannot get Entity FFPhysicalGraphNodeEntity from Repository by name or code");
        }
        if (old != null
                && old.size() == 1) {
            entity.setId(old.get(0).getId());
        }
        old = null;        
    }
    /**
     * Updates multiple {@link FFPhysicalGraphNodeEntity}s.
     * @param entities the list of {@link FFPhysicalGraphNodeEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFPhysicalGraphNodeEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<FFPhysicalGraphNodeEntity>> update(
            @RequestBody final List<FFPhysicalGraphNodeEntity> entities) {
        List<Resource<FFPhysicalGraphNodeEntity>> resources = new ArrayList<Resource<FFPhysicalGraphNodeEntity>>();
        Iterator<FFPhysicalGraphNodeEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link FFPhysicalGraphNodeEntity}.
     * @param entity the {@link FFPhysicalGraphNodeEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFPhysicalGraphNodeEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<FFPhysicalGraphNodeEntity>> update(
            @RequestBody final FFPhysicalGraphNodeEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
            if (entity.getTerrain() != null
        && entity.getTerrain().getId() == null) {
      setTerrainIdFromRepository(entity);
        }


    
        FFPhysicalGraphNodeEntity savedEntity = repository.save(entity);
        List<Resource<FFPhysicalGraphNodeEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

  private void setTerrainIdFromRepository(
      final FFPhysicalGraphNodeEntity entity) {
    FFTerrainEntity memberEntity = null;
    List<Resource<FFTerrainEntity>> list = null;
    try {
      Method method = null;
      Field field = null;
      try {
        method = FFTerrainController.class.getDeclaredMethod(
            "getByName", new Class[] { String.class });
        field = FFTerrainEntity.class.getDeclaredField("name");
      } catch (NoSuchMethodException | NoSuchFieldException e) {
      }
      if (method != null
          && field != null) {
        field.setAccessible(true);
        if (field.get(entity.getTerrain()) != null) {
          list = (List<Resource<FFTerrainEntity>>) method
              .invoke(
                  FFTerrainController.getInstance(),
                  (String) field
                      .get(entity.getTerrain()));
        }
      }
      if (list == null) {
        try {
          method = FFTerrainController.class.getDeclaredMethod(
              "getByCode", new Class[] { String.class });
          field = FFTerrainEntity.class
              .getDeclaredField("code");
        } catch (NoSuchMethodException | NoSuchFieldException e) {
        }
        if (method != null
            && field != null) {
          field.setAccessible(true);
          if (field.get(entity.getTerrain()) != null) {
            list = (List<Resource<FFTerrainEntity>>)
                method.invoke(FFTerrainController
                    .getInstance(),(String) field.get(
                        entity.getTerrain()));
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
      memberEntity = (FFTerrainEntity)
          ((Resource) FFTerrainController.getInstance().save(
              entity.getTerrain()).get(0)).getContent();
    }
    entity.setTerrain(memberEntity);
    list = null;
    }


    /**
     * Gets a list of {@link FFPhysicalGraphNodeEntity}s that share a isMainNode.
     * @param isMainNode the physical_graph_node' isMainNode
     * @return {@link List}<{@link Resource}<{@link FFPhysicalGraphNodeEntity}>>
     */
    @RequestMapping(path = "is_main_node/{isMainNode}",
            method = RequestMethod.GET)
    public List<Resource<FFPhysicalGraphNodeEntity>> getByIsMainNode(
            @PathVariable final Boolean isMainNode) {
        Iterator<FFPhysicalGraphNodeEntity> iter = repository.findByIsMainNode(isMainNode)
                .iterator();
        List<Resource<FFPhysicalGraphNodeEntity>> resources =
                new ArrayList<Resource<FFPhysicalGraphNodeEntity>>();
        while (iter.hasNext()) {
            resources.add(getPhysicalGraphNodeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFPhysicalGraphNodeEntity}s that share a roomNumber.
     * @param roomNumber the physical_graph_node' roomNumber
     * @return {@link List}<{@link Resource}<{@link FFPhysicalGraphNodeEntity}>>
     */
    @RequestMapping(path = "room_number/{roomNumber}",
            method = RequestMethod.GET)
    public List<Resource<FFPhysicalGraphNodeEntity>> getByRoomNumber(
            @PathVariable final Long roomNumber) {
        Iterator<FFPhysicalGraphNodeEntity> iter = repository.findByRoomNumber(roomNumber)
                .iterator();
        List<Resource<FFPhysicalGraphNodeEntity>> resources =
                new ArrayList<Resource<FFPhysicalGraphNodeEntity>>();
        while (iter.hasNext()) {
            resources.add(getPhysicalGraphNodeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFPhysicalGraphNodeEntity}s that share a x.
     * @param x the physical_graph_node' x
     * @return {@link List}<{@link Resource}<{@link FFPhysicalGraphNodeEntity}>>
     */
    @RequestMapping(path = "x/{x}",
            method = RequestMethod.GET)
    public List<Resource<FFPhysicalGraphNodeEntity>> getByX(
            @PathVariable final Long x) {
        Iterator<FFPhysicalGraphNodeEntity> iter = repository.findByX(x)
                .iterator();
        List<Resource<FFPhysicalGraphNodeEntity>> resources =
                new ArrayList<Resource<FFPhysicalGraphNodeEntity>>();
        while (iter.hasNext()) {
            resources.add(getPhysicalGraphNodeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a list of {@link FFPhysicalGraphNodeEntity}s that share a y.
     * @param y the physical_graph_node' y
     * @return {@link List}<{@link Resource}<{@link FFPhysicalGraphNodeEntity}>>
     */
    @RequestMapping(path = "y/{y}",
            method = RequestMethod.GET)
    public List<Resource<FFPhysicalGraphNodeEntity>> getByY(
            @PathVariable final Long y) {
        Iterator<FFPhysicalGraphNodeEntity> iter = repository.findByY(y)
                .iterator();
        List<Resource<FFPhysicalGraphNodeEntity>> resources =
                new ArrayList<Resource<FFPhysicalGraphNodeEntity>>();
        while (iter.hasNext()) {
            resources.add(getPhysicalGraphNodeResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
