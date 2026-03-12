package seedu.address.model.contact;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Contact} matches the specified field keywords.
 * Matching is case-insensitive.
 */
public class ContactMatchesKeywordsPredicate implements Predicate<Contact> {
    private final List<String> nameKeywords;
    private final List<String> phoneKeywords;
    private final List<String> emailKeywords;
    private final List<String> addressKeywords;
    private final List<String> tagKeywords;

    private final NameContainsKeywordsPredicate namePredicate;
    private final PhoneContainsKeywordsPredicate phonePredicate;
    private final EmailContainsKeywordsPredicate emailPredicate;
    private final AddressContainsKeywordsPredicate addressPredicate;
    private final TagContainsKeywordsPredicate tagPredicate;

    /**
     * Creates a predicate that filters by name, phone, email, address and tag keywords.
     *
     * @param nameKeywords Keywords for matching contact names.
     * @param phoneKeywords Keywords for matching contact phone numbers.
     * @param emailKeywords Keywords for matching contact emails.
     * @param addressKeywords Keywords for matching contact addresses.
     * @param tagKeywords Keywords for matching contact tags.
     */
    public ContactMatchesKeywordsPredicate(List<String> nameKeywords, List<String> phoneKeywords,
            List<String> emailKeywords, List<String> addressKeywords, List<String> tagKeywords) {
        requireNonNull(nameKeywords);
        requireNonNull(phoneKeywords);
        requireNonNull(emailKeywords);
        requireNonNull(addressKeywords);
        requireNonNull(tagKeywords);
        this.nameKeywords = List.copyOf(nameKeywords);
        this.phoneKeywords = List.copyOf(phoneKeywords);
        this.emailKeywords = List.copyOf(emailKeywords);
        this.addressKeywords = List.copyOf(addressKeywords);
        this.tagKeywords = List.copyOf(tagKeywords);
        this.namePredicate = new NameContainsKeywordsPredicate(this.nameKeywords);
        this.phonePredicate = new PhoneContainsKeywordsPredicate(this.phoneKeywords);
        this.emailPredicate = new EmailContainsKeywordsPredicate(this.emailKeywords);
        this.addressPredicate = new AddressContainsKeywordsPredicate(this.addressKeywords);
        this.tagPredicate = new TagContainsKeywordsPredicate(this.tagKeywords);
    }

    @Override
    public boolean test(Contact contact) {
        boolean hasNameCriteria = !nameKeywords.isEmpty();
        boolean hasPhoneCriteria = !phoneKeywords.isEmpty();
        boolean hasEmailCriteria = !emailKeywords.isEmpty();
        boolean hasAddressCriteria = !addressKeywords.isEmpty();
        boolean hasTagCriteria = !tagKeywords.isEmpty();

        boolean matches = true;
        boolean hasAnyCriteria = false;

        if (hasNameCriteria) {
            hasAnyCriteria = true;
            matches = matches && namePredicate.test(contact);
        }
        if (hasPhoneCriteria) {
            hasAnyCriteria = true;
            matches = matches && phonePredicate.test(contact);
        }
        if (hasEmailCriteria) {
            hasAnyCriteria = true;
            matches = matches && emailPredicate.test(contact);
        }
        if (hasAddressCriteria) {
            hasAnyCriteria = true;
            matches = matches && addressPredicate.test(contact);
        }
        if (hasTagCriteria) {
            hasAnyCriteria = true;
            matches = matches && tagPredicate.test(contact);
        }

        return hasAnyCriteria && matches;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ContactMatchesKeywordsPredicate)) {
            return false;
        }

        ContactMatchesKeywordsPredicate otherPredicate = (ContactMatchesKeywordsPredicate) other;
        return nameKeywords.equals(otherPredicate.nameKeywords)
                && phoneKeywords.equals(otherPredicate.phoneKeywords)
                && emailKeywords.equals(otherPredicate.emailKeywords)
                && addressKeywords.equals(otherPredicate.addressKeywords)
                && tagKeywords.equals(otherPredicate.tagKeywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("nameKeywords", nameKeywords)
                .add("phoneKeywords", phoneKeywords)
                .add("emailKeywords", emailKeywords)
                .add("addressKeywords", addressKeywords)
                .add("tagKeywords", tagKeywords)
                .toString();
    }
}
