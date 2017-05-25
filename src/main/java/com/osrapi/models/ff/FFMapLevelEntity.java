package com.osrapi.models.ff;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author drau
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Entity
@Table(name = "map_level", schema = "ff")
public final class FFMapLevelEntity {
    /**
     * the primary key - an autogenerated id (unique for each user in the db).
     */
    @Id
    @Column(name = "map_level_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
  generator = "map_level_seq")
    @SequenceGenerator(
        name = "map_level_seq",
        sequenceName = "ff.map_level_id_seq",
        allocationSize = 1
    )
    private Long                    id;
    /** Creates a new instance of {@link FFMapLevelEntity}. */
    public FFMapLevelEntity() {
        super();
    }
    /**
     * Gets the id.
     * @return {@link Long}
     */
    public Long getId() {
        return id;
    }
    /**
     * Sets the id.
     * @param val the new value
     */
    public void setId(final Long val) {
        id = val;
    }

    /** the elevation. */
    @Column(name = "elevation")
    @JsonProperty("elevation")
    @NotNull
    private long                    elevation;
    /**
     * Gets the elevation.
     * @return {@link long}
     */
    public long getElevation() {
        return elevation;
    }
    /**
     * Sets the elevation.
     * @param val the new value
     */
    public void setElevation(final long val) {
        elevation = val;
    }

    /** the name. */
    @Column(name = "name")
    @JsonProperty("name")
    @NotNull
    private String                    name;
    /**
     * Gets the name.
     * @return {@link String}
     */
    public String getName() {
        return name;
    }
    /**
     * Sets the name.
     * @param val the new value
     */
    public void setName(final String val) {
        name = val;
    }

    /**
     * the list of {@link FFMapCellEntity}s associated with this
     * {@link FFMapLevelEntity}.
     */
    @OneToMany(targetEntity = FFMapCellEntity.class,
      fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinTable(name = "map_level_cells_lookup", schema = "ff",
  joinColumns = @JoinColumn(name = "map_level_id",
  referencedColumnName = "map_level_id"),
  inverseJoinColumns = @JoinColumn(name = "map_cell_id",
  referencedColumnName = "map_cell_id"))
    @JsonProperty("cells")
    private List<FFMapCellEntity>    cells;
    /**
     * Gets the list of cellss.
     * @return {@link List}<{@link FFMapCellEntity}>
     */
    public List<FFMapCellEntity> getCells() {
        return cells;
    }
    /**
     * Sets the list of cellss.
     * @param val the new value
     */
    public void setCells(final List<FFMapCellEntity> val) {
        cells = val;
    }

}

