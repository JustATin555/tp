package seedu.address.ui;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Reminder;
import seedu.address.model.contact.TimePoint;

/**
 * Controller for a help page
 */
public class ReminderWindow extends UiPart<Stage> {

    public static final int DUE_PERIOD_DAYS = 7;

    public static final String REMINDER_MESSAGE =
            String.format("The following reminders are due in %d days: \n", DUE_PERIOD_DAYS);

    private static final Logger logger = LogsCenter.getLogger(ReminderWindow.class);
    private static final String FXML = "ReminderWindow.fxml";

    @FXML
    private Label reminderMessage;

    @FXML
    private VBox reminderMessageContainer;

    /**
     * Creates a new ReminderWindow.
     *
     * @param root Stage to use as the root of the ReminderWindow.
     */
    public ReminderWindow(Stage root, List<Contact> contactList) {
        super(FXML, root);
        reminderMessage.setText(REMINDER_MESSAGE);
        contactList.stream().filter(ReminderWindow::contactHasDueReminders).forEach(contact -> {
            Label nameLabel = new Label(contact.getName().toString() + ":   ");
            nameLabel.getStyleClass().add("name");
            reminderMessageContainer.getChildren().add(nameLabel);
            contact.getReminders().stream()
                    .filter(ReminderWindow :: reminderIsDue)
                    .forEach(reminder -> {
                        ReminderLabel reminderLabel = new ReminderLabel(reminder);
                        reminderLabel.hideHeader();
                        reminderMessageContainer.getChildren().add(reminderLabel); });
        });
    }

    /**
     * Creates a new HelpWindow.
     */
    public ReminderWindow(List<Contact> contactList) {
        this(new Stage(), contactList);
    }

    /**
     * Shows the reminder window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Checks if a list of contacts contain reminders that are due in {@code DUE_PERIOD_DAYS} number of days.
     *
     * @param contactList List of contacts to check for reminders that are due soon.
     */
    public static boolean hasDueReminders(List<Contact> contactList) {
        return contactList.stream().anyMatch(ReminderWindow::contactHasDueReminders);
    }

    private static boolean contactHasDueReminders(Contact contact) {
        return contact.getReminders().stream().anyMatch(ReminderWindow :: reminderIsDue);
    }

    private static boolean reminderIsDue(Reminder reminder) {
        TimePoint cutOffTime = TimePoint.of(LocalDateTime.now().plusDays(DUE_PERIOD_DAYS));
        TimePoint nowTime = TimePoint.of(LocalDateTime.now());
        return reminder.timePoint.isBefore(cutOffTime) && reminder.timePoint.isAfter(nowTime);
    }
}
