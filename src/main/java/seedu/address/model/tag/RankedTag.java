package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a RankedTag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class RankedTag extends Tag {

    public static final String MESSAGE_CONSTRAINTS = "Ranked tag values should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String tagValue;

    /**
     * Constructs a {@code RankedTag}.
     *
     * @param tagName A valid tag name.
     * @param tagValue A valid tag value.
     */
    public RankedTag(String tagName, String tagValue) {
        super(tagName);
        requireNonNull(tagValue);
        checkArgument(isValidTagValue(tagValue), MESSAGE_CONSTRAINTS);
        this.tagValue = tagValue;
    }

    /**
     * Returns true if a given string is a valid tag value.
     */
    public static boolean isValidTagValue(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RankedTag otherTag)) {
            return false;
        }

        return tagName.equals(otherTag.tagName) && tagValue.equals(otherTag.tagValue);
    }

    @Override
    public int hashCode() {
        return (tagName + ':' + tagValue).hashCode();
    }

    /**
     * Formats state as text for viewing.
     */
    public String toString() {
        return '[' + tagName + ':' + tagValue + ']';
    }

}
