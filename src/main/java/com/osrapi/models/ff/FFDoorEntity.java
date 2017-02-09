package com.osrapi.models.ff;

import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author drau
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Entity
@Table(name = "door", schema = "ff")
public final class FFDoorEntity {
    /** the attributeTest. */
    @Column(name = "attribute_test")
    @JsonProperty("attribute_test")

    private String attributeTest;
    /** the direction. */
    @Column(name = "direction")
    @JsonProperty("direction")
    @NotNull
    private String direction;
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
            allocationSize = 1)
    private Long id;
    /** the leadsTo. */
    @Column(name = "leads_to")
    @JsonProperty("leads_to")

    private String leadsTo;

    /** the locked. */
    @Column(name = "locked")
    @JsonProperty("locked")
    @NotNull
    private Boolean locked;
    /** the name. */
    @Column(name = "name")
    @JsonProperty("name")
    @NotNull
    private String name;
    /** the numDiceRoll. */
    @Column(name = "num_dice_roll")
    @JsonProperty("num_dice_roll")

    private Long numDiceRoll;

    @ElementCollection
    @CollectionTable(name = "door_scripted_events_lookup",
            schema = "ff", joinColumns = @JoinColumn(name = "door_id"))
    @MapKeyColumn(name = "key")
    @Column(name = "value")
    @JsonProperty("scripted_events")
    private Map<String, String> scriptedEvents;
    /** the title. */
    @Column(name = "title")
    @JsonProperty("title")
    @NotNull
    private String title;
    /** Creates a new instance of {@link FFDoorEntity}. */
    public FFDoorEntity() {
        super();
    }

    /**
     * Gets the attributeTest.
     * @return {@link String}
     */
    public String getAttributeTest() {
        return attributeTest;
    }
    /**
     * Gets the direction.
     * @return {@link String}
     */
    public String getDirection() {
        return direction;
    }
    /**
     * Gets the id.
     * @return {@link Long}
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets the leadsTo.
     * @return {@link String}
     */
    public String getLeadsTo() {
        return leadsTo;
    }
    /**
     * Gets the locked.
     * @return {@link Boolean}
     */
    public Boolean getLocked() {
        return locked;
    }
    /**
     * Gets the name.
     * @return {@link String}
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the numDiceRoll.
     * @return {@link Long}
     */
    public Long getNumDiceRoll() {
        return numDiceRoll;
    }
    /**
     * Gets the map of scriptedEventss.
     * @return {@link Map}<{@link String}, {@link String}>
     */
    public Map<String, String> getScriptedEvents() {
        return scriptedEvents;
    }
    /**
     * Gets the title.
     * @return {@link String}
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the attributeTest.
     * @param val the new value
     */
    public void setAttributeTest(final String val) {
        attributeTest = val;
    }
    /**
     * Sets the direction.
     * @param val the new value
     */
    public void setDirection(final String val) {
        direction = val;
    }
    /**
     * Sets the id.
     * @param val the new value
     */
    public void setId(final Long val) {
        id = val;
    }

    /**
     * Sets the leadsTo.
     * @param val the new value
     */
    public void setLeadsTo(final String val) {
        leadsTo = val;
    }
    /**
     * Sets the locked.
     * @param val the new value
     */
    public void setLocked(final Boolean val) {
        locked = val;
    }
    /**
     * Sets the name.
     * @param val the new value
     */
    public void setName(final String val) {
        name = val;
    }

    /**
     * Sets the numDiceRoll.
     * @param val the new value
     */
    public void setNumDiceRoll(final Long val) {
        numDiceRoll = val;
    }
    /**
     * Sets the mapping for scriptedEventss.
     * @param val the new value
     */
    public void setScriptedEvents(Map<String, String> val) {
        scriptedEvents = val;
    }
    /**
     * Sets the title.
     * @param val the new value
     */
    public void setTitle(final String val) {
        title = val;
    }

}
