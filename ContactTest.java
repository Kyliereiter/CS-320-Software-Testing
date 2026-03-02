package contact;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ContactTest {

    // ---------- Helpers ----------
    private Contact makeValidContact() {
        return new Contact("12345", "Kylie", "Reiter", "5551234567", "123 Main St, Town");
    }

    // ---------- Happy path ----------
    @Test
    void createContact_validInputs_setsFields() {
        Contact c = makeValidContact();
        assertEquals("12345", c.getContactId());
        assertEquals("Kylie", c.getFirstName());
        assertEquals("Reiter", c.getLastName());
        assertEquals("5551234567", c.getPhone());
        assertEquals("123 Main St, Town", c.getAddress());
    }

    // ---------- Boundary valid cases ----------
    @Test
    void createContact_contactId_length10_valid() {
        Contact c = new Contact("1234567890", "A", "B", "5551234567", "Addr");
        assertEquals("1234567890", c.getContactId());
    }

    @Test
    void createContact_firstName_length10_valid() {
        Contact c = new Contact("1", "ABCDEFGHIJ", "B", "5551234567", "Addr");
        assertEquals("ABCDEFGHIJ", c.getFirstName());
    }

    @Test
    void createContact_lastName_length10_valid() {
        Contact c = new Contact("1", "A", "ABCDEFGHIJ", "5551234567", "Addr");
        assertEquals("ABCDEFGHIJ", c.getLastName());
    }

    @Test
    void createContact_phone_exactly10Digits_valid() {
        Contact c = new Contact("1", "A", "B", "0123456789", "Addr");
        assertEquals("0123456789", c.getPhone());
    }

    @Test
    void createContact_address_length30_valid() {
        String addr30 = "123456789012345678901234567890"; // 30 chars
        assertEquals(30, addr30.length());
        Contact c = new Contact("1", "A", "B", "5551234567", addr30);
        assertEquals(addr30, c.getAddress());
    }

    // ---------- ContactId invalid ----------
    @Test
    void createContact_nullId_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact(null, "A", "B", "5551234567", "Addr"));
    }

    @Test
    void createContact_blankId_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("   ", "A", "B", "5551234567", "Addr"));
    }

    @Test
    void createContact_idTooLong_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("12345678901", "A", "B", "5551234567", "Addr"));
    }

    // ---------- FirstName invalid ----------
    @Test
    void createContact_nullFirstName_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("1", null, "B", "5551234567", "Addr"));
    }

    @Test
    void createContact_blankFirstName_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("1", "   ", "B", "5551234567", "Addr"));
    }

    @Test
    void createContact_firstNameTooLong_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("1", "ABCDEFGHIJK", "B", "5551234567", "Addr"));
    }

    // ---------- LastName invalid ----------
    @Test
    void createContact_nullLastName_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("1", "A", null, "5551234567", "Addr"));
    }

    @Test
    void createContact_blankLastName_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("1", "A", "   ", "5551234567", "Addr"));
    }

    @Test
    void createContact_lastNameTooLong_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("1", "A", "ABCDEFGHIJK", "5551234567", "Addr"));
    }

    // ---------- Phone invalid ----------
    @Test
    void createContact_nullPhone_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("1", "A", "B", null, "Addr"));
    }

    @Test
    void createContact_blankPhone_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("1", "A", "B", "   ", "Addr"));
    }

    @Test
    void createContact_phoneTooShort_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("1", "A", "B", "123", "Addr"));
    }

    @Test
    void createContact_phoneTooLong_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("1", "A", "B", "55512345678", "Addr"));
    }

    // hit the digit-check loop with non-digit in different positions
    @Test
    void createContact_phoneNonDigit_firstChar_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("1", "A", "B", "A551234567", "Addr"));
    }

    @Test
    void createContact_phoneNonDigit_middleChar_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("1", "A", "B", "55512A4567", "Addr"));
    }

    @Test
    void createContact_phoneNonDigit_lastChar_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("1", "A", "B", "555123456A", "Addr"));
    }

    // ---------- Address invalid ----------
    @Test
    void createContact_nullAddress_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("1", "A", "B", "5551234567", null));
    }

    @Test
    void createContact_blankAddress_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("1", "A", "B", "5551234567", "   "));
    }

    @Test
    void createContact_addressTooLong_throws() {
        String addr31 = "1234567890123456789012345678901"; // 31 chars
        assertEquals(31, addr31.length());
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("1", "A", "B", "5551234567", addr31));
    }

    // ---------- Setter tests ----------
    @Test
    void setters_validUpdates_work() {
        Contact c = makeValidContact();

        c.setFirstName("Ann");
        c.setLastName("Bee");
        c.setPhone("1112223333");
        c.setAddress("New Address");

        assertEquals("Ann", c.getFirstName());
        assertEquals("Bee", c.getLastName());
        assertEquals("1112223333", c.getPhone());
        assertEquals("New Address", c.getAddress());
    }

    @Test
    void setters_invalidUpdates_throw() {
        Contact c = makeValidContact();

        // firstName
        assertThrows(IllegalArgumentException.class, () -> c.setFirstName(null));
        assertThrows(IllegalArgumentException.class, () -> c.setFirstName("   "));
        assertThrows(IllegalArgumentException.class, () -> c.setFirstName("ABCDEFGHIJK"));

        // lastName
        assertThrows(IllegalArgumentException.class, () -> c.setLastName(null));
        assertThrows(IllegalArgumentException.class, () -> c.setLastName("   "));
        assertThrows(IllegalArgumentException.class, () -> c.setLastName("ABCDEFGHIJK"));

        // phone
        assertThrows(IllegalArgumentException.class, () -> c.setPhone(null));
        assertThrows(IllegalArgumentException.class, () -> c.setPhone("   "));
        assertThrows(IllegalArgumentException.class, () -> c.setPhone("123"));          // wrong length
        assertThrows(IllegalArgumentException.class, () -> c.setPhone("55512345678"));  // wrong length
        assertThrows(IllegalArgumentException.class, () -> c.setPhone("55512A4567"));   // non-digit

        // address
        assertThrows(IllegalArgumentException.class, () -> c.setAddress(null));
        assertThrows(IllegalArgumentException.class, () -> c.setAddress("   "));
        assertThrows(IllegalArgumentException.class,
                () -> c.setAddress("1234567890123456789012345678901")); // 31 chars
    }
}