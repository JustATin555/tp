package seedu.address.model.contact;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Contact}'s email matches any of the keywords given.
 */
public class EmailContainsKeywordsPredicate implements Predicate<Contact> {
    private final List<String> keywords;

    /**
     * Creates a predicate that filters by email keywords.
     *
     * @param keywords Keywords for matching contact emails.
     */
    public EmailContainsKeywordsPredicate(List<String> keywords) {
        requireNonNull(keywords);
        this.keywords = List.copyOf(keywords);
    }

    @Override
    public boolean test(Contact contact) {
        return contact.getEmail()
                .map(email -> keywords.stream()
                        .anyMatch(keyword -> email.value.toLowerCase(Locale.ROOT)
                                .contains(keyword.toLowerCase(Locale.ROOT))))
                .orElse(false);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EmailContainsKeywordsPredicate)) {
            return false;
        }

        EmailContainsKeywordsPredicate otherPredicate = (EmailContainsKeywordsPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
