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

import com.osrapi.models.ff.FFRoomBkupEntity;
import com.osrapi.models.ff.FFTextEntity;
import com.osrapi.models.ff.FFDoorEntity;

import com.osrapi.repositories.ff.FFRoomBkupRepository;

/**
 * @author drau
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/ff/room_bkups")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FFRoomBkupController {
    /** the static instance of {@link FFRoomBkupController}. */
    private static FFRoomBkupController instance;
    /**
     * Gets the static instance.
     * @return {@link FFRoomBkupController}
     */
    public static FFRoomBkupController getInstance() {
        if (instance == null) {
            new FFRoomBkupController();
        }
        return instance;
    }
    /** the data repository. */
    @Autowired
    private FFRoomBkupRepository repository;
    /** Creates a new instance of {@link FFRoomBkupController}. */
    public FFRoomBkupController() {
        instance = this;
    }
    /**
     * Gets a list of {@link FFRoomBkupEntity}s.
     * @return {@link List}<{@link Resource}<{@link FFRoomBkupEntity}>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<FFRoomBkupEntity>> getAll() {
        Iterator<FFRoomBkupEntity> iter = repository.findAll()
                .iterator();
        List<Resource<FFRoomBkupEntity>> resources =
                new ArrayList<Resource<FFRoomBkupEntity>>();
        while (iter.hasNext()) {
            resources.add(getRoomBkupResource(iter.next()));
        }
        iter = null;
        return resources;
    }
    /**
     * Gets a single {@link FFRoomBkupEntity}.
     * @param id the event type's id
     * @return {@link List}<{@link Resource}<{@link FFRoomBkupEntity}>>
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Resource<FFRoomBkupEntity>> getById(
            @PathVariable final Long id) {
        FFRoomBkupEntity entity = repository.findOne(id);
        List<Resource<FFRoomBkupEntity>> resources =
                new ArrayList<Resource<FFRoomBkupEntity>>();
        resources.add(getRoomBkupResource(entity));
        entity = null;
        return resources;
    }
    /**
     * Gets a {@link Resource} instance with links for the
     * {@link FFRoomBkupEntity}.
     * @param entity the {@link FFRoomBkupEntity}
     * @return {@link Resource}<{@link FFRoomBkupEntity}>
     */
    private Resource<FFRoomBkupEntity> getRoomBkupResource(
            final FFRoomBkupEntity entity) {
        Resource<FFRoomBkupEntity> resource =
                new Resource<FFRoomBkupEntity>(
                entity);
        // link to entity
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(getClass()).getById(
                        entity.getId()))
                .withSelfRel());
        return resource;
    }
    /**
     * Saves multiple {@link FFRoomBkupEntity}s.
     * @param entities the list of {@link FFRoomBkupEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFRoomBkupEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.POST)
    public List<Resource<FFRoomBkupEntity>> save(
            @RequestBody final List<FFRoomBkupEntity> entities) {
        List<Resource<FFRoomBkupEntity>> resources =
                new ArrayList<Resource<FFRoomBkupEntity>>();
        Iterator<FFRoomBkupEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(save(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Saves a single {@link FFRoomBkupEntity}.
     * @param entity the {@link FFRoomBkupEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFRoomBkupEntity}>>
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<Resource<FFRoomBkupEntity>> save(
            @RequestBody final FFRoomBkupEntity entity) {
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


    
        FFRoomBkupEntity savedEntity = repository.save(entity);
        List<Resource<FFRoomBkupEntity>> list =
                getById(savedEntity.getId());
        savedEntity = null;
        return list;
    }
    /**
     * Tries to set the Id for an entity to be saved by locating it in the
     * repository.
     * @param entity the {@link FFRoomBkupEntity} instance
     */
    private void setIdFromRepository(final FFRoomBkupEntity entity) {
        List<FFRoomBkupEntity> old = null;
        try {
            Method method = null;
            Field field = null;
            try {
                method = repository.getClass().getDeclaredMethod(
                        "findByName", new Class[] { String.class });
                field = FFRoomBkupEntity.class.getDeclaredField("name");
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (method != null
                    && field != null) {
                field.setAccessible(true);
                if (field.get(entity) != null) {
                    old = (List<FFRoomBkupEntity>) method.invoke(
              repository, (String) field.get(entity));
                }
            }
            if (old == null
                    || (old != null
                    && old.size() > 1)) {
                try {
                    method = repository.getClass().getDeclaredMethod(
                            "findByCode", new Class[] { String.class });
                    field = FFRoomBkupEntity.class.getDeclaredField(
                            "code");
                } catch (NoSuchMethodException | NoSuchFieldException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (method != null
                        && field != null) {
                    field.setAccessible(true);
                    if (field.get(entity) != null) {
                        old = (List<FFRoomBkupEntity>) method.invoke(
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
     * Updates multiple {@link FFRoomBkupEntity}s.
     * @param entities the list of {@link FFRoomBkupEntity} instances
     * @return {@link List}<{@link Resource}<{@link FFRoomBkupEntity}>>
     */
    @RequestMapping(path = "/bulk", method = RequestMethod.PUT)
    public List<Resource<FFRoomBkupEntity>> update(
            @RequestBody final List<FFRoomBkupEntity> entities) {
        List<Resource<FFRoomBkupEntity>> resources = new ArrayList<Resource<FFRoomBkupEntity>>();
        Iterator<FFRoomBkupEntity> iter = entities.iterator();
        while (iter.hasNext()) {
            resources.add(update(iter.next()).get(0));
        }
        iter = null;
        return resources;
    }
    /**
     * Updates a single {@link FFRoomBkupEntity}.
     * @param entity the {@link FFRoomBkupEntity} instance
     * @return {@link List}<{@link Resource}<{@link FFRoomBkupEntity}>>
     */
    @RequestMapping(method = RequestMethod.PUT)
    public List<Resource<FFRoomBkupEntity>> update(
            @RequestBody final FFRoomBkupEntity entity) {        
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


    
        FFRoomBkupEntity savedEntity = repository.save(entity);
        List<Resource<FFRoomBkupEntity>> list = getById(
                savedEntity.getId());
        savedEntity = null;
        return list;
    }

  private void setTextIdFromRepository(
      final FFRoomBkupEntity entity) {
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
     * Gets a list of {@link FFRoomBkupEntity}s that share a code.
     * @param code the room_bkup' code
     * @return {@link List}<{@link Resource}<{@link FFRoomBkupEntity}>>
     */
    @RequestMapping(path = "code/{code}",
            method = RequestMethod.GET)
    public List<Resource<FFRoomBkupEntity>> getByCode(
            @PathVariable final String code) {
        Iterator<FFRoomBkupEntity> iter = repository.findByCode(code)
                .iterator();
        List<Resource<FFRoomBkupEntity>> resources =
                new ArrayList<Resource<FFRoomBkupEntity>>();
        while (iter.hasNext()) {
            resources.add(getRoomBkupResource(iter.next()));
        }
        iter = null;
        return resources;
    }
}
