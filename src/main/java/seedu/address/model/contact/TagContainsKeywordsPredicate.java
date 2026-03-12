package seedu.address.model.contact;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Contact}'s tags match any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<Contact> {
    private final List<String> keywords;

    /**
     * Creates a predicate that filters by tag keywords.
     *
     * @param keywords Keywords for matching contact tags.
     */
    public TagContainsKeywordsPredicate(List<String> keywords) {
        requireNonNull(keywords);
        this.keywords = List.copyOf(keywords);
    }

    @Override
    public boolean test(Contact contact) {
        return contact.getTags().stream()
                .map(tag -> tag.tagName)
                .anyMatch(tagName -> keywords.stream()
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(tagName, keyword)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TagContainsKeywordsPredicate)) {
            return false;
        }

        TagContainsKeywordsPredicate otherPredicate = (TagContainsKeywordsPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
