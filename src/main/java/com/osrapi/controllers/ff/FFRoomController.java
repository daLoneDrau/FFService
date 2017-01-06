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

import com.osrapi.models.ff.FFRoomEntity;
import com.osrapi.models.ff.FFTextEntity;
import com.osrapi.models.ff.FFDoorEntity;

import com.osrapi.repositories.ff.FFRoomRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/ff/rooms")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FFRoomController {
    /** the static instance of {@link FFRoomController}. */
    private static FFRoomController instance;
    /**
     * Gets the static instance.
     * @return {@link FFRoomController}
     */
    public static FFRoomController getInstance() {
        if (instance == null) {
            new FFRoomController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private FFRoomRepository repository;
    /** Creates a new instance of {@link FFRoomController}. */
    public FFRoomController() {
        instance = this;
    }
    /**
     * Gets a list of {@link FFRoomEntity}s.
     * @return {@link List}<{@link Resource}<{@link FFRoomEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<FFRoomEntity>> getAll() {
        Iterator<FFRoomEntity> iter = repository.findAll()
                .iterator();
        List<Resource<FFRoomEntity>> resources =
                new ArrayList<Resource<FFRoomEntity>>();
        while (iter.hasNext()) {
            resources.add(getRoomResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link FFRoomEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link FFRoomEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<FFRoomEntity>> getById(
            @PathVariable final Long id) {
        FFRoomEntity entity = repository.findOne(id);
        List<Resource<FFRoomEntity>> resources =
                new ArrayList<Resource<FFRoomEntity>>();
        resources.add(getRoomResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link FFRoomEntity}.
     * @param entity the {@link FFRoomEntity}
     * @return {@link Resource}<{@link FFRoomEntity}>
     */
    private Resource<FFRoomEntity> getRoomResource(
            final FFRoomEntity entity) {
        Resource<FFRoomEntity> resource =
                new Resource<FFRoomEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link FFRoomEntity}s.
     * @param entities the list of {@link FFRoomEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFRoomEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<FFRoomEntity>> save(
            @RequestBody final List<FFRoomEntity> entities) {
        List<Resource<FFRoomEntity>> resources =
                new ArrayList<Resource<FFRoomEntity>>();
        Iterator<FFRoomEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link FFRoomEntity}.
     * @param entity the {@link FFRoomEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFRoomEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<FFRoomEntity>> save(
            @RequestBody final FFRoomEntity entity) {
        if (entity.getDoors() != null
                && !entity.getDoors().isEmpty()) {
            for (int i = entity.getDoors().size() - 1; i >= 0; i--) {
                FFDoorEntity doors = null;
                List<Resource<FFDoorEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = FFDoorController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    Field field = null;
          try {
            field = FFDoorEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getDoors().get(i)) != null) {
                            list = (List<Resource<FFDoorEntity>>) method
                                    .invoke(
                                            FFDoorController.getInstance(),
                                            (String) field.get(entity.getDoors().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = FFDoorController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              e.printStackTrace();
            }
            try {
              field = FFDoorEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              e.printStackTrace();
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getDoors().get(i)) != null) {
                                list = (List<Resource<FFDoorEntity>>) method
                                        .invoke(
                                                FFDoorController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getDoors().get(i)));
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
                if (list != null
                        && !list.isEmpty()) {
                    doors = list.get(0).getContent();
                }
                if (doors == null) {
                    doors = (FFDoorEntity) ((Resource) FFDoorController
                            .getInstance()
                            .save(entity.getDoors().get(i)).get(0)).getContent();
                }
                entity.getDoors().set(i, doors);
                list = null;
            }
        }

        if (entity.getText() != null
        && entity.getText().getId() == null) {
      setTextIdFromRepository(entity);
        }


    
        FFRoomEntity savedEntity = repository.save(entity);
        List<Resource<FFRoomEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link FFRoomEntity} instance
     */
    private void setIdFromRepository(final FFRoomEntity entity) {
        List<FFRoomEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = FFRoomEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<FFRoomEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = FFRoomEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<FFRoomEntity>) method.invoke(
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
     * Updates multiple {@link FFRoomEntity}s.
     * @param entities the list of {@link FFRoomEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFRoomEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<FFRoomEntity>> update(
            @RequestBody final List<FFRoomEntity> entities) {
        List<Resource<FFRoomEntity>> resources = new ArrayList<Resource<FFRoomEntity>>();
        Iterator<FFRoomEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link FFRoomEntity}.
     * @param entity the {@link FFRoomEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFRoomEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<FFRoomEntity>> update(
            @RequestBody final FFRoomEntity entity) {        
        if (entity.getId() == null) {
            setIdFromRepository(entity);
        }
        if (entity.getDoors() != null
                && !entity.getDoors().isEmpty()) {
            for (int i = entity.getDoors().size() - 1; i >= 0; i--) {
                FFDoorEntity doors = null;
                List<Resource<FFDoorEntity>> list = null;
                try {
                    Method method = null;
          try {
            method = FFDoorController.class.getDeclaredMethod(
                "getByName", new Class[] { String.class });
          } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    Field field = null;
          try {
            field = FFDoorEntity.class
                .getDeclaredField("name");
          } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                    if (method != null
                            && field != null) {
                        field.setAccessible(true);
                        if (field.get(entity.getDoors().get(i)) != null) {
                            list = (List<Resource<FFDoorEntity>>) method
                                    .invoke(
                                            FFDoorController.getInstance(),
                                            (String) field.get(entity.getDoors().get(i)));
                        }
                    }
                    if (list == null) {
            try {
              method = FFDoorController.class.getDeclaredMethod(
                  "getByCode", new Class[] { String.class });
            } catch (NoSuchMethodException e) {
              e.printStackTrace();
            }
            try {
              field = FFDoorEntity.class.getDeclaredField(
                  "code");
            } catch (NoSuchFieldException e) {
              e.printStackTrace();
            }
                        if (method != null
                                && field != null) {
                            field.setAccessible(true);
                            if (field.get(entity.getDoors().get(i)) != null) {
                                list = (List<Resource<FFDoorEntity>>) method
                                        .invoke(
                                                FFDoorController
                                                        .getInstance(),
                                                (String) field
                                                        .get(entity.getDoors().get(i)));
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
                if (list != null
                        && !list.isEmpty()) {
                    doors = list.get(0).getContent();
                }
                if (doors == null) {
                    doors = (FFDoorEntity) ((Resource) FFDoorController
                            .getInstance()
                            .save(entity.getDoors().get(i)).get(0)).getContent();
                }
                entity.getDoors().set(i, doors);
                list = null;
            }
        }

        if (entity.getText() != null
        && entity.getText().getId() == null) {
      setTextIdFromRepository(entity);
        }


    
        FFRoomEntity savedEntity = repository.save(entity);
        List<Resource<FFRoomEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

  private void setTextIdFromRepository(
      final FFRoomEntity entity) {
    FFTextEntity memberEntity = null;
    List<Resource<FFTextEntity>> list = null;
    try {
      Method method = null;
      Field field = null;
      try {
        method = FFTextController.class.getDeclaredMethod(
            "getByName", new Class[] { String.class });
        field = FFTextEntity.class.getDeclaredField("name");
      } catch (NoSuchMethodException | NoSuchFieldException e) {
      }
      if (method != null
          && field != null) {
        field.setAccessible(true);
        if (field.get(entity.getText()) != null) {
          list = (List<Resource<FFTextEntity>>) method
              .invoke(
                  FFTextController.getInstance(),
                  (String) field
                      .get(entity.getText()));
        }
      }
      if (list == null) {
        try {
          method = FFTextController.class.getDeclaredMethod(
              "getByCode", new Class[] { String.class });
          field = FFTextEntity.class
              .getDeclaredField("code");
        } catch (NoSuchMethodException | NoSuchFieldException e) {
        }
        if (method != null
            && field != null) {
          field.setAccessible(true);
          if (field.get(entity.getText()) != null) {
            list = (List<Resource<FFTextEntity>>)
                method.invoke(FFTextController
                    .getInstance(),(String) field.get(
                        entity.getText()));
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
      memberEntity = (FFTextEntity)
          ((Resource) FFTextController.getInstance().save(
              entity.getText()).get(0)).getContent();
    }
    entity.setText(memberEntity);
    list = null;
    }


    /**
     * Gets a list of {@link FFRoomEntity}s that share a code.
     * @param code the room' code
     * @return {@link List}<{@link Resource}<{@link FFRoomEntity}>>
     */
    @RequestMapping(path = "code/{code}",
            method = RequestMethod.GET)
    public List<Resource<FFRoomEntity>> getByCode(
            @PathVariable final String code) {
        Iterator<FFRoomEntity> iter = repository.findByCode(code)
                .iterator();
        List<Resource<FFRoomEntity>> resources =
                new ArrayList<Resource<FFRoomEntity>>();
        while (iter.hasNext()) {
            resources.add(getRoomResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
