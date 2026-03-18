package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.RankedTag;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Tag}.
 */
class JsonAdaptedTag {

    private final String tagName;
    private final String tagValue;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code tagName} and {@code tagValue}.
     */
    @JsonCreator
    public JsonAdaptedTag(String[] tagParams) {
        if (tagParams.length == 0) {
            throw new IllegalArgumentException("Tag parameters cannot be empty");
        }

        if (tagParams.length > 2) {
            throw new IllegalArgumentException("Cannot have more than 2 tag parameters");
        }

        this.tagName = tagParams[0];

        if (tagParams.length == 2) {
            this.tagValue = tagParams[1];
        } else {
            this.tagValue = null;
        }
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedTag(Tag source) {
        tagName = source.tagName;

        if (source instanceof RankedTag) {
            tagValue = ((RankedTag) source).tagValue;
        } else {
            tagValue = null;
        }
    }

    @JsonValue
    public String[] getTagParams() {
        return tagValue != null ? new String[] { tagName, tagValue } : new String[] { tagName };
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Tag toModelType() throws IllegalValueException {
        if (!Tag.isValidTagName(tagName)) {
            throw new IllegalValueException(Tag.MESSAGE_CONSTRAINTS);
        }

        boolean isRankedTag = tagValue != null;

        if (!isRankedTag) {
            return new Tag(tagName);
        }

        if (!RankedTag.isValidTagValue(tagValue)) {
            throw new IllegalValueException(RankedTag.MESSAGE_CONSTRAINTS);
        }

        return new RankedTag(tagName, tagValue);
    }

}
