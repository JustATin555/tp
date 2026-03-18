package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    private static final String INVALID_TAG_NAME = "#";
    private static final Tag SAMPLE_TAG_1 = new Tag("friend");
    private static final Tag SAMPLE_TAG_2 = new Tag("neighbour");

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Tag(INVALID_TAG_NAME));
    }

    @Test
    public void isValidTagName_null_throwsNullPointerException() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }

    @Test
    public void equals_self_returnsTrue() {
        assertEquals(true, SAMPLE_TAG_1.equals(SAMPLE_TAG_1));
    }

    @Test
    public void equals_null_returnsFalse() {
        assertEquals(false, SAMPLE_TAG_1.equals(null));
    }

    @Test
    public void equals_differentTagName_returnsFalse() {
        assertEquals(false, SAMPLE_TAG_1.equals(SAMPLE_TAG_2));
    }
}
