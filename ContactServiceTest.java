package contact;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ContactServiceTest {

    @Test
    void addContact_uniqueId_addsSuccessfully() {
        ContactService svc = new ContactService();
        svc.addContact("1", "A", "B", "5551234567", "Addr");

        Contact c = svc.getContact("1");
        assertNotNull(c);
        assertEquals("A", c.getFirstName());
    }

    @Test
    void addContact_duplicateId_throws() {
        ContactService svc = new ContactService();
        svc.addContact("1", "A", "B", "5551234567", "Addr");

        assertThrows(IllegalArgumentException.class,
                () -> svc.addContact("1", "C", "D", "1112223333", "Addr2"));
    }

    @Test
    void deleteContact_existing_removes() {
        ContactService svc = new ContactService();
        svc.addContact("1", "A", "B", "5551234567", "Addr");

        svc.deleteContact("1");
        assertNull(svc.getContact("1"));
    }

    @Test
    void deleteContact_missing_throws() {
        ContactService svc = new ContactService();
        assertThrows(IllegalArgumentException.class, () -> svc.deleteContact("nope"));
    }

    @Test
    void updateFields_byId_updatesCorrectly() {
        ContactService svc = new ContactService();
        svc.addContact("1", "A", "B", "5551234567", "Addr");

        svc.updateFirstName("1", "Ann");
        svc.updateLastName("1", "Bee");
        svc.updatePhone("1", "1112223333");
        svc.updateAddress("1", "New Address");

        Contact c = svc.getContact("1");
        assertEquals("Ann", c.getFirstName());
        assertEquals("Bee", c.getLastName());
        assertEquals("1112223333", c.getPhone());
        assertEquals("New Address", c.getAddress());
    }

    @Test
    void updateContact_missingId_throws() {
        ContactService svc = new ContactService();
        assertThrows(IllegalArgumentException.class, () -> svc.updateFirstName("missing", "Ann"));
    }

    @Test
    void updateContact_invalidValue_throwsAndDoesNotChange() {
        ContactService svc = new ContactService();
        svc.addContact("1", "A", "B", "5551234567", "Addr");

        // Invalid phone should throw, and existing value should remain
        assertThrows(IllegalArgumentException.class, () -> svc.updatePhone("1", "123"));

        Contact c = svc.getContact("1");
        assertEquals("5551234567", c.getPhone());
    }

    @Test
    void updateMultipleFields_nullMeansNoChange() {
        ContactService svc = new ContactService();
        svc.addContact("1", "A", "B", "5551234567", "Addr");

        svc.updateContact("1", null, "Last", null, "New Address");

        Contact c = svc.getContact("1");
        assertEquals("A", c.getFirstName());
        assertEquals("Last", c.getLastName());
        assertEquals("5551234567", c.getPhone());
        assertEquals("New Address", c.getAddress());
    }
}
