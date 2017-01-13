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
@Table(name = "door", schema = "ff")
public final class FFDoorEntity {
    /**
     * the primary key - an autogenerated id (unique for each user in the db).
     */
    @Id
    @Column(name = "door_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
  generator = "door_seq")
    @SequenceGenerator(
        name = "door_seq",
        sequenceName = "ff.door_id_seq",
        allocationSize = 1
    )
    private Long                    id;
    /** Creates a new instance of {@link FFDoorEntity}. */
    public FFDoorEntity() {
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

    /** the title. */
    @Column(name = "title")
    @JsonProperty("title")
    @NotNull
    private String                    title;
    /**
     * Gets the title.
     * @return {@link String}
     */
    public String getTitle() {
        return title;
    }
    /**
     * Sets the title.
     * @param val the new value
     */
    public void setTitle(final String val) {
        title = val;
    }

    /** the numDiceRoll. */
    @Column(name = "num_dice_roll")
    @JsonProperty("num_dice_roll")
    
    private Long                    numDiceRoll;
    /**
     * Gets the numDiceRoll.
     * @return {@link Long}
     */
    public Long getNumDiceRoll() {
        return numDiceRoll;
    }
    /**
     * Sets the numDiceRoll.
     * @param val the new value
     */
    public void setNumDiceRoll(final Long val) {
        numDiceRoll = val;
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

    /** the locked. */
    @Column(name = "locked")
    @JsonProperty("locked")
    @NotNull
    private Boolean                    locked;
    /**
     * Gets the locked.
     * @return {@link Boolean}
     */
    public Boolean getLocked() {
        return locked;
    }
    /**
     * Sets the locked.
     * @param val the new value
     */
    public void setLocked(final Boolean val) {
        locked = val;
    }

    /** the leadsTo. */
    @Column(name = "leads_to")
    @JsonProperty("leads_to")
    
    private String                    leadsTo;
    /**
     * Gets the leadsTo.
     * @return {@link String}
     */
    public String getLeadsTo() {
        return leadsTo;
    }
    /**
     * Sets the leadsTo.
     * @param val the new value
     */
    public void setLeadsTo(final String val) {
        leadsTo = val;
    }

    /** the direction. */
    @Column(name = "direction")
    @JsonProperty("direction")
    @NotNull
    private String                    direction;
    /**
     * Gets the direction.
     * @return {@link String}
     */
    public String getDirection() {
        return direction;
    }
    /**
     * Sets the direction.
     * @param val the new value
     */
    public void setDirection(final String val) {
        direction = val;
    }

    /** the attributeTest. */
    @Column(name = "attribute_test")
    @JsonProperty("attribute_test")
    
    private String                    attributeTest;
    /**
     * Gets the attributeTest.
     * @return {@link String}
     */
    public String getAttributeTest() {
        return attributeTest;
    }
    /**
     * Sets the attributeTest.
     * @param val the new value
     */
    public void setAttributeTest(final String val) {
        attributeTest = val;
    }

    @ElementCollection
    @CollectionTable(name = "door_scripted_events_lookup",
  schema = "ff", joinColumns = @JoinColumn(name = "door_id"))
    @MapKeyColumn(name = "key")
    @Column(name = "value")
    @JsonProperty("scripted_events")
    private Map<String, String> scriptedEvents;
    /**
     * Gets the map of scriptedEventss.
     * @return {@link Map}<{@link String}, {@link String}>
     */
    public Map<String, String> getScriptedEvents() {
        return scriptedEvents;
    }
    /**
     * Sets the mapping for scriptedEventss.
     * @param val the new value
     */
    public void setScriptedEvents(Map<String, String> val) {
        scriptedEvents = val;
    }

}
