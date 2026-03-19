package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static seedu.address.testutil.TypicalContacts.ALICE;
import static seedu.address.testutil.TypicalContacts.BENSON;
import static seedu.address.testutil.TypicalContacts.CARL;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.contact.Contact;

/**
 * Contains tests for {@code ContactListPanel}.
 */
public class ContactListPanelTest extends GuiUnitTest {
    private ObservableList<Contact> contacts = FXCollections.observableArrayList(ALICE, BENSON, CARL);
    private ContactListPanel contactListPanel = new ContactListPanel(contacts);
    private ContactListPanel emptyContactListPanel = new ContactListPanel(FXCollections.observableArrayList());

    @Test
    public void scrollToTopTest() throws Exception {
        runAndWait(() -> {
            assertDoesNotThrow(() -> contactListPanel.scrollToTop());
        });
    }

    @Test
    public void scrollToBottomTest() throws Exception {
        runAndWait(() -> {
            assertDoesNotThrow(() -> contactListPanel.scrollToBottom());
        });
    }

    @Test
    public void emptyListScrollToTopTest() throws Exception {
        runAndWait(() -> {
            assertDoesNotThrow(() -> emptyContactListPanel.scrollToTop());
        });
    }

    @Test
    public void emptyListScrollToBottomTest() throws Exception {
        runAndWait(() -> {
            assertDoesNotThrow(() -> emptyContactListPanel.scrollToBottom());
        });
    }
}
