package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Logic;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.contact.Contact;
import seedu.address.testutil.ContactBuilder;

/**
 * Contains tests for {@code MainWindow} panel management logic which require {@code show()} to be run.
 */
public class MainWindowVisibleGuiTest extends GuiUnitTest {

    private MainWindow mainWindow;
    private StubLogic logic;

    @Test
    public void scroll_success() throws Exception {
        logic = new StubLogic();
        runAndWait(() -> {
            Stage stage = new Stage();
            mainWindow = new MainWindow(stage, logic);
            mainWindow.show();
            mainWindow.fillInnerParts();
            logic.setNextResult(new CommandResult(ListCommand.MESSAGE_SUCCESS));
            assertDoesNotThrow(() -> mainWindow.executeCommand("list"));
            logic.setNextResult(
                    new CommandResult(
                            String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(new ContactBuilder().build()))));
            assertDoesNotThrow(() -> mainWindow.executeCommand("add"));
        });
    }

    /**
     * A stub Logic implementation for testing MainWindow.
     */
    private static class StubLogic implements Logic {
        private CommandResult nextResult = new CommandResult("stub");
        private final ObservableList<Contact> contacts = FXCollections.observableArrayList();

        void setNextResult(CommandResult result) {
            this.nextResult = result;
        }

        ObservableList<Contact> getContacts() {
            return contacts;
        }

        @Override
        public CommandResult execute(String commandText) throws CommandException, ParseException {
            return nextResult;
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public ObservableList<Contact> getDisplayedContactList() {
            return contacts;
        }

        @Override
        public Path getAddressBookFilePath() {
            return Paths.get("data", "addressbook.json");
        }

        @Override
        public GuiSettings getGuiSettings() {
            return new GuiSettings();
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            // no-op for testing
        }
    }
}
