package seedu.address.model.contact;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import seedu.address.model.tag.RankedTag;
import seedu.address.model.tag.Tag;

/**
 * Comparator for sorting contacts based on specified field and order.
 */
public final class ContactTagComparator implements Comparator<Contact> {

    private final String tag;
    private final ContactComparator.Order order;

    /**
     * Constructs a ContactTagComparator for the specified tag and order.
     *
     * @param tag The tag to sort by.
     * @param order The order to sort in.
     * @throws NullPointerException if either field or order is null.
     */
    public ContactTagComparator(String tag, ContactComparator.Order order) {
        requireAllNonNull(tag, order);
        this.tag = tag;
        this.order = order;
    }

    @Override
    public int compare(Contact o1, Contact o2) {
        // Neither has the Tag
        if (!o1.hasTag(tag) && !o2.hasTag(tag)) {
            return 0;
        }

        // Either does not have the Tag
        if (!o1.hasTag(tag) || !o2.hasTag(tag)) {
            return o1.hasTag(tag) ? 1 : -1;
        }

        Optional<RankedTag> rankedTag1 = extractRankedTag(o1, tag);
        Optional<RankedTag> rankedTag2 = extractRankedTag(o2, tag);

        // Both have the Tag (but not the RankedTag)
        if (rankedTag1.isEmpty() && rankedTag2.isEmpty()) {
            return 0;
        }

        // Only one of the two has a RankedTag
        if (rankedTag1.isEmpty() || rankedTag2.isEmpty()) {
            return rankedTag1.isPresent() ? 1 : -1;
        }

        // Both have RankedTags
        return rankedTag1.get().tagValue.compareTo(rankedTag2.get().tagValue)
            * (order == ContactComparator.Order.ASCENDING ? 1 : -1);
    }

    /**
     * Extract a specific {@code RankedTag} from a {@code Contact}.
     * @param contact The contact with the tag.
     * @param tagName The name of the tag.
     * @return The {@code RankedTag} if present, nothing otherwise.
     */
    private static Optional<RankedTag> extractRankedTag(Contact contact, String tagName) {
        return contact.getTags().stream()
            .filter(tag -> !tag.tagName.isEmpty() && tag.tagName.equals(tagName) && tag instanceof RankedTag)
            .map(tag -> (RankedTag) tag)
            .findFirst();
    }
}
