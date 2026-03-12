package seedu.address.model.contact;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Contact}'s phone matches any of the keywords given.
 */
public class PhoneContainsKeywordsPredicate implements Predicate<Contact> {
    private final List<String> keywords;

    /**
     * Creates a predicate that filters by phone keywords.
     *
     * @param keywords Keywords for matching contact phone numbers.
     */
    public PhoneContainsKeywordsPredicate(List<String> keywords) {
        requireNonNull(keywords);
        this.keywords = List.copyOf(keywords);
    }

    @Override
    public boolean test(Contact contact) {
        return contact.getPhone()
                .map(phone -> keywords.stream()
                        .anyMatch(keyword -> phone.value.toLowerCase(Locale.ROOT)
                                .contains(keyword.toLowerCase(Locale.ROOT))))
                .orElse(false);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PhoneContainsKeywordsPredicate)) {
            return false;
        }

        PhoneContainsKeywordsPredicate otherPredicate = (PhoneContainsKeywordsPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
