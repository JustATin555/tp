package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.RankedTag;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Tag}.
 */
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = JsonAdaptedRankedTag.class)
})
class JsonAdaptedTag {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Tag's %s field is missing!";
    protected final String name;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code name}.
     * @param name The name of the tag.
     */
    @JsonCreator
    public JsonAdaptedTag(
        @JsonProperty("name") String name
    ) {
        this.name = name;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedTag(Tag source) {
        name = source.name;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Tag toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "name"));
        }

        if (!Tag.isValidTagName(name)) {
            throw new IllegalValueException(Tag.MESSAGE_CONSTRAINTS);
        }

        return new Tag(name);
    }
}

class JsonAdaptedRankedTag extends JsonAdaptedTag {

    protected final String rank;

    /**
     * Constructs a {@code JsonAdaptedRankedTag} with the given {@code name} and {@code rank}.
     * @param name The name of the tag.
     * @param rank The rank of the tag.
     */
    @JsonCreator
    public JsonAdaptedRankedTag(
        @JsonProperty("name") String name,
        @JsonProperty("rank") String rank
    ) {
        super(name);
        this.rank = rank;
    }

    /**
     * Converts a given {@code RankedTag} into this class for Jackson use.
     */
    public JsonAdaptedRankedTag(RankedTag source) {
        super(source.name);
        rank = source.rank;
    }

    /**
     * Converts this Jackson-friendly adapted ranked tag object into the model's {@code RankedTag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public RankedTag toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "name"));
        }

        if (!RankedTag.isValidTagName(name)) {
            throw new IllegalValueException(RankedTag.MESSAGE_CONSTRAINTS);
        }

        if (rank == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "rank"));
        }

        if (!RankedTag.isValidTagValue(rank)) {
            throw new IllegalValueException(RankedTag.MESSAGE_CONSTRAINTS);
        }

        return new RankedTag(name, rank);
    }
}
