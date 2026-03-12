package seedu.address.model.contact;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Contact}'s address matches any of the keywords given.
 */
public class AddressContainsKeywordsPredicate implements Predicate<Contact> {
    private final List<String> keywords;

    /**
     * Creates a predicate that filters by address keywords.
     *
     * @param keywords Keywords for matching contact addresses.
     */
    public AddressContainsKeywordsPredicate(List<String> keywords) {
        requireNonNull(keywords);
        this.keywords = List.copyOf(keywords);
    }

    @Override
    public boolean test(Contact contact) {
        return contact.getAddress()
                .map(address -> keywords.stream()
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(address.value, keyword)))
                .orElse(false);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddressContainsKeywordsPredicate)) {
            return false;
        }

        AddressContainsKeywordsPredicate otherPredicate = (AddressContainsKeywordsPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
