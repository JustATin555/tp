package seedu.address.model.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ContactBuilder;

public class ContactMatchesKeywordsPredicateTest {

    @Test
    public void equals() {
        ContactMatchesKeywordsPredicate firstPredicate =
                new ContactMatchesKeywordsPredicate(
                        Collections.singletonList("Alice"), List.of(), List.of(), List.of(), List.of("friends"));
        ContactMatchesKeywordsPredicate secondPredicate =
                new ContactMatchesKeywordsPredicate(
                        Collections.singletonList("Bob"), List.of(), List.of(), List.of(), List.of("family"));

        assertTrue(firstPredicate.equals(firstPredicate));

        ContactMatchesKeywordsPredicate firstPredicateCopy =
                new ContactMatchesKeywordsPredicate(
                        Collections.singletonList("Alice"), List.of(), List.of(), List.of(), List.of("friends"));
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        assertFalse(firstPredicate.equals(1));
        assertFalse(firstPredicate.equals(null));
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameAndTagCriteria_returnsTrueOnlyWhenBothMatch() {
        ContactMatchesKeywordsPredicate predicate =
                new ContactMatchesKeywordsPredicate(
                        List.of("Alice"), List.of(), List.of(), List.of(), List.of("friends"));
        assertTrue(predicate.test(new ContactBuilder().withName("Alice Bob").withTags("friends").build()));
        assertFalse(predicate.test(new ContactBuilder().withName("Alice Bob").withTags("family").build()));
        assertFalse(predicate.test(new ContactBuilder().withName("Charlie").withTags("friends").build()));
    }

    @Test
    public void test_nameOnlyCriteria_returnsNameMatch() {
        ContactMatchesKeywordsPredicate predicate =
                new ContactMatchesKeywordsPredicate(
                        Arrays.asList("Alice", "Bob"), List.of(), List.of(), List.of(), Collections.emptyList());
        assertTrue(predicate.test(new ContactBuilder().withName("Bob Carol").build()));
        assertFalse(predicate.test(new ContactBuilder().withName("Daniel").build()));
    }

    @Test
    public void test_tagOnlyCriteria_returnsTagMatch() {
        ContactMatchesKeywordsPredicate predicate =
                new ContactMatchesKeywordsPredicate(
                        Collections.emptyList(), List.of(), List.of(), List.of(),
                        Arrays.asList("friends", "teammates"));
        assertTrue(predicate.test(new ContactBuilder().withTags("teammates").build()));
        assertFalse(predicate.test(new ContactBuilder().withTags("family").build()));
    }

    @Test
    public void test_phoneAndEmailCriteria_returnsTrueOnlyWhenBothMatch() {
        ContactMatchesKeywordsPredicate predicate = new ContactMatchesKeywordsPredicate(
                List.of(), List.of("9435"), List.of("example.com"), List.of(), List.of());
        assertTrue(predicate.test(
                new ContactBuilder().withPhone("94351253").withEmail("alice@example.com").build()));
        assertFalse(predicate.test(new ContactBuilder().withPhone("94351253").withEmail("alice@test.com").build()));
    }

    @Test
    public void toStringMethod() {
        ContactMatchesKeywordsPredicate predicate =
                new ContactMatchesKeywordsPredicate(
                        List.of("Alice"), List.of(), List.of(), List.of(), List.of("friends"));
        String expected = ContactMatchesKeywordsPredicate.class.getCanonicalName()
                + "{nameKeywords=[Alice], phoneKeywords=[], emailKeywords=[], "
                + "addressKeywords=[], tagKeywords=[friends]}";
        assertEquals(expected, predicate.toString());
    }
}
