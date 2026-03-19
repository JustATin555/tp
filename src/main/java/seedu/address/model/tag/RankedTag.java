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

    public final String rank;

    /**
     * Constructs a {@code RankedTag}.
     *
     * @param name A valid tag name.
     * @param rank A valid tag rank.
     */
    public RankedTag(String name, String rank) {
        super(name);
        requireNonNull(rank);
        checkArgument(isValidTagValue(rank), MESSAGE_CONSTRAINTS);
        this.rank = rank;
    }

    /**
     * Returns true if a given string is a valid tag rank.
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

        return name.equals(otherTag.name) && rank.equals(otherTag.rank);
    }

    @Override
    public int hashCode() {
        return (name + ':' + rank).hashCode();
    }

    /**
     * Formats state as text for viewing.
     */
    public String toString() {
        return '[' + name + ':' + rank + ']';
    }

}
