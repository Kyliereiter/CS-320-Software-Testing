package contact;

import java.util.HashMap;
import java.util.Map;

public class ContactService {
    private final Map<String, Contact> contacts = new HashMap<>();

    // Convenience method
    public Contact addContact(String contactId, String firstName, String lastName, String phone, String address) {
        Contact contact = new Contact(contactId, firstName, lastName, phone, address);
        addContact(contact);
        return contact;
    }

    public void addContact(Contact contact) {
        if (contact == null) {
            throw new IllegalArgumentException("contact must not be null");
        }
        String id = contact.getContactId();
        if (contacts.containsKey(id)) {
            throw new IllegalArgumentException("contactId must be unique");
        }
        contacts.put(id, contact);
    }

    public void deleteContact(String contactId) {
        if (contactId == null) {
            throw new IllegalArgumentException("contactId must not be null");
        }
        if (!contacts.containsKey(contactId)) {
            throw new IllegalArgumentException("contactId not found");
        }
        contacts.remove(contactId);
    }

    public Contact getContact(String contactId) {
        return contacts.get(contactId);
    }

    // Update methods (fields are validated by Contact setters)
    public void updateFirstName(String contactId, String firstName) {
        Contact c = requireContact(contactId);
        c.setFirstName(firstName);
    }

    public void updateLastName(String contactId, String lastName) {
        Contact c = requireContact(contactId);
        c.setLastName(lastName);
    }

    public void updatePhone(String contactId, String phone) {
        Contact c = requireContact(contactId);
        c.setPhone(phone);
    }

    public void updateAddress(String contactId, String address) {
        Contact c = requireContact(contactId);
        c.setAddress(address);
    }

    // Optional: update multiple fields at once (null means "leave as-is")
    public void updateContact(String contactId, String firstName, String lastName, String phone, String address) {
        Contact c = requireContact(contactId);
        if (firstName != null) c.setFirstName(firstName);
        if (lastName != null) c.setLastName(lastName);
        if (phone != null) c.setPhone(phone);
        if (address != null) c.setAddress(address);
    }

    private Contact requireContact(String contactId) {
        if (contactId == null) {
            throw new IllegalArgumentException("contactId must not be null");
        }
        Contact c = contacts.get(contactId);
        if (c == null) {
            throw new IllegalArgumentException("contactId not found");
        }
        return c;
    }
}
