package seedu.address.model.contact;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Comparator for sorting contacts based on specified field and order.
 */
public final class ContactComparatorSet implements Comparator<Contact> {

    private final ArrayList<Comparator<Contact>> comparators;

    /**
     * Constructs an empty combined {@code Comparator<Contact>}.
     */
    public ContactComparatorSet() {
        this.comparators = new ArrayList<>();
    }

    @Override
    public int compare(Contact o1, Contact o2) {
        for (Comparator<Contact> comparator : this.comparators) {
            int result = comparator.compare(o1, o2);

            if (result != 0) {
                return result;
            }
        }

        return 0;
    }

    /**
     * Checks if there are no stored comparators.
     * @return True if there are no stored comparators, false otherwise.
     */
    public boolean isEmpty() {
        return this.comparators.isEmpty();
    }

    /**
     * Adds a new comparator to the list.
     * @param comparator The new comparator.
     */
    public void addComparator(Comparator<Contact> comparator) {
        this.comparators.add(comparator);
    }
}
