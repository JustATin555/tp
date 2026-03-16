package seedu.address.model.contact;

import java.util.Comparator;
import java.util.Map;

public final class ContactComparator {
    /**
     * Prevents instantiation.
     */
    private ContactComparator() {}
    
    public static final Map<Field, Comparator<Contact>> COMPARATORS = Map.of(
            Field.NAME, Comparator.comparing(Contact::getName),
            Field.PHONE,
            Comparator.comparing(contact -> contact.getPhone().orElse(null),
                    Comparator.nullsLast(Comparator.naturalOrder())),
            Field.EMAIL,
            Comparator.comparing(contact -> contact.getEmail().orElse(null),
                    Comparator.nullsLast(Comparator.naturalOrder())),
            Field.ADDRESS, Comparator.comparing(contact -> contact.getAddress().orElse(null),
                    Comparator.nullsLast(Comparator.naturalOrder())));
    
    

    public static enum Field {
        NAME, PHONE, EMAIL, ADDRESS
    }
    
    public static enum Order {
        ASCENDING, DESCENDING
    }
}
