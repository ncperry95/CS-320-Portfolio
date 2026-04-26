package ProjectOne.ContactService;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the ContactService class.
 * 
 * This test class verifies that the ContactService correctly implements
 * all three requirements from the specification:
 * 
 * Requirement 1: Add Contacts
 *   - The contact service shall be able to add contacts with a unique ID
 *   - Duplicate IDs should be rejected
 * 
 * Requirement 2: Delete Contacts
 *   - The contact service shall be able to delete contacts per contact ID
 *   - Deleting a non-existent contact should fail
 * 
 * Requirement 3: Update Contacts
 *   - The contact service shall be able to update contact fields per contact ID
 *   - Updatable fields: firstName, lastName, Number (phone), Address
 *   - Updating a non-existent contact should fail
 * 
 * Each test uses a fresh ContactService instance to ensure test isolation.
 * The @BeforeEach annotation creates a new service before each test runs.
 */
public class ContactServiceTest {
    
    // The ContactService instance used by each test
    // This is reset before each test by the setUp() method
    private ContactService service;
    
    /**
     * Set up method that runs before each test.
     * Creates a fresh ContactService instance so each test starts
     * with an empty contact list - tests don't affect each other.
     */
    @BeforeEach
    public void setUp() {
        service = new ContactService();
    }
    
    // ==================== ADD CONTACT TESTS (Requirement 1) ====================
    // Requirement: "The contact service shall be able to add contacts with a unique ID"
    
    /**
     * Test that a valid contact can be added and then retrieved.
     * This is the basic "happy path" test for adding contacts.
     */
    @Test
    public void testAddContactSuccess() {
        // Create a valid contact
        Contact contact = new Contact("1234567890", "John", "Doe", "5551234567", "123 Main St");
        
        // Add the contact to the service
        service.addContact(contact);
        
        // Retrieve the contact and verify it was stored correctly
        Contact retrieved = service.getContact("1234567890");
        assertNotNull(retrieved);
        assertEquals("John", retrieved.getFirstName());
        assertEquals("Doe", retrieved.getLastName());
    }
    
    /**
     * Test that multiple contacts with different IDs can be added.
     * Verifies the service can store more than one contact.
     */
    @Test
    public void testAddMultipleContacts() {
        // Create two contacts with different IDs
        Contact contact1 = new Contact("ID001", "John", "Doe", "5551234567", "123 Main St");
        Contact contact2 = new Contact("ID002", "Jane", "Smith", "5559876543", "456 Oak Ave");
        
        // Add both contacts
        service.addContact(contact1);
        service.addContact(contact2);
        
        // Verify both contacts can be retrieved
        assertNotNull(service.getContact("ID001"));
        assertNotNull(service.getContact("ID002"));
        
        // Verify each contact has the correct data
        assertEquals("John", service.getContact("ID001").getFirstName());
        assertEquals("Jane", service.getContact("ID002").getFirstName());
    }
    
    /**
     * Test that adding a contact with a duplicate ID throws an exception.
     * Requirement: IDs must be unique - two contacts cannot have the same ID.
     */
    @Test
    public void testAddContactWithDuplicateId() {
        // Create two contacts with the SAME ID
        Contact contact1 = new Contact("SAMEID", "John", "Doe", "5551234567", "123 Main St");
        Contact contact2 = new Contact("SAMEID", "Jane", "Smith", "5559876543", "456 Oak Ave");
        
        // Add the first contact - should succeed
        service.addContact(contact1);
        
        // Attempt to add the second contact with duplicate ID - should throw
        assertThrows(IllegalArgumentException.class, () -> {
            service.addContact(contact2);
        });
    }
    
    /**
     * Test that adding a null contact throws an exception.
     * The service should reject null inputs.
     */
    @Test
    public void testAddNullContact() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.addContact(null);
        });
    }
    
    // ==================== DELETE CONTACT TESTS (Requirement 2) ====================
    // Requirement: "The contact service shall be able to delete contacts per contact ID"
    
    /**
     * Test that a contact can be successfully deleted by ID.
     * After deletion, the contact should no longer be retrievable.
     */
    @Test
    public void testDeleteContactSuccess() {
        // Add a contact first
        Contact contact = new Contact("1234567890", "John", "Doe", "5551234567", "123 Main St");
        service.addContact(contact);
        
        // Verify the contact exists
        assertNotNull(service.getContact("1234567890"));
        
        // Delete the contact
        service.deleteContact("1234567890");
        
        // Verify the contact no longer exists (getContact returns null)
        assertNull(service.getContact("1234567890"));
    }
    
    /**
     * Test that attempting to delete a non-existent contact throws an exception.
     * The service should alert the caller when a delete fails.
     */
    @Test
    public void testDeleteContactNotFound() {
        // Try to delete a contact that was never added
        assertThrows(IllegalArgumentException.class, () -> {
            service.deleteContact("NONEXISTENT");
        });
    }
    
    /**
     * Test that deleting one contact doesn't affect other contacts.
     * When we have multiple contacts and delete one, the others should remain.
     */
    @Test
    public void testDeleteOneOfMultipleContacts() {
        // Add two contacts
        Contact contact1 = new Contact("ID001", "John", "Doe", "5551234567", "123 Main St");
        Contact contact2 = new Contact("ID002", "Jane", "Smith", "5559876543", "456 Oak Ave");
        service.addContact(contact1);
        service.addContact(contact2);
        
        // Delete only the first contact
        service.deleteContact("ID001");
        
        // Verify ID001 is deleted
        assertNull(service.getContact("ID001"));
        
        // Verify ID002 still exists
        assertNotNull(service.getContact("ID002"));
    }
    
    // ==================== UPDATE CONTACT TESTS (Requirement 3) ====================
    // Requirement: "The contact service shall be able to update contact fields per contact ID"
    // Updatable fields: firstName, lastName, Number (phone), Address
    
    // ---------- Update First Name Tests ----------
    
    /**
     * Test that firstName can be successfully updated.
     */
    @Test
    public void testUpdateFirstNameSuccess() {
        // Add a contact
        Contact contact = new Contact("1234567890", "John", "Doe", "5551234567", "123 Main St");
        service.addContact(contact);
        
        // Update the first name
        service.updateFirstName("1234567890", "Jane");
        
        // Verify the update took effect
        assertEquals("Jane", service.getContact("1234567890").getFirstName());
    }
    
    /**
     * Test that updating firstName for a non-existent contact throws an exception.
     */
    @Test
    public void testUpdateFirstNameContactNotFound() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.updateFirstName("NONEXISTENT", "Jane");
        });
    }
    
    /**
     * Test that updating firstName with an invalid value (null) throws an exception.
     * The Contact class validates the input, so the exception bubbles up.
     */
    @Test
    public void testUpdateFirstNameInvalid() {
        // Add a contact first
        Contact contact = new Contact("1234567890", "John", "Doe", "5551234567", "123 Main St");
        service.addContact(contact);
        
        // Attempt to update with null - should throw
        assertThrows(IllegalArgumentException.class, () -> {
            service.updateFirstName("1234567890", null);
        });
    }
    
    // ---------- Update Last Name Tests ----------
    
    /**
     * Test that lastName can be successfully updated.
     */
    @Test
    public void testUpdateLastNameSuccess() {
        Contact contact = new Contact("1234567890", "John", "Doe", "5551234567", "123 Main St");
        service.addContact(contact);
        
        service.updateLastName("1234567890", "Smith");
        
        assertEquals("Smith", service.getContact("1234567890").getLastName());
    }
    
    /**
     * Test that updating lastName for a non-existent contact throws an exception.
     */
    @Test
    public void testUpdateLastNameContactNotFound() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.updateLastName("NONEXISTENT", "Smith");
        });
    }
    
    /**
     * Test that updating lastName with an invalid value (null) throws an exception.
     */
    @Test
    public void testUpdateLastNameInvalid() {
        Contact contact = new Contact("1234567890", "John", "Doe", "5551234567", "123 Main St");
        service.addContact(contact);
        
        assertThrows(IllegalArgumentException.class, () -> {
            service.updateLastName("1234567890", null);
        });
    }
    
    // ---------- Update Phone (Number) Tests ----------
    // Note: The requirement says "Number" but we use "phone" in our implementation
    
    /**
     * Test that phone number can be successfully updated.
     */
    @Test
    public void testUpdatePhoneSuccess() {
        Contact contact = new Contact("1234567890", "John", "Doe", "5551234567", "123 Main St");
        service.addContact(contact);
        
        service.updatePhone("1234567890", "9998887777");
        
        assertEquals("9998887777", service.getContact("1234567890").getPhone());
    }
    
    /**
     * Test that updating phone for a non-existent contact throws an exception.
     */
    @Test
    public void testUpdatePhoneContactNotFound() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.updatePhone("NONEXISTENT", "9998887777");
        });
    }
    
    /**
     * Test that updating phone with an invalid value (wrong length) throws an exception.
     */
    @Test
    public void testUpdatePhoneInvalid() {
        Contact contact = new Contact("1234567890", "John", "Doe", "5551234567", "123 Main St");
        service.addContact(contact);
        
        // "123" is only 3 digits - should throw
        assertThrows(IllegalArgumentException.class, () -> {
            service.updatePhone("1234567890", "123");
        });
    }
    
    // ---------- Update Address Tests ----------
    
    /**
     * Test that address can be successfully updated.
     */
    @Test
    public void testUpdateAddressSuccess() {
        Contact contact = new Contact("1234567890", "John", "Doe", "5551234567", "123 Main St");
        service.addContact(contact);
        
        service.updateAddress("1234567890", "456 Oak Ave");
        
        assertEquals("456 Oak Ave", service.getContact("1234567890").getAddress());
    }
    
    /**
     * Test that updating address for a non-existent contact throws an exception.
     */
    @Test
    public void testUpdateAddressContactNotFound() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.updateAddress("NONEXISTENT", "456 Oak Ave");
        });
    }
    
    /**
     * Test that updating address with an invalid value (null) throws an exception.
     */
    @Test
    public void testUpdateAddressInvalid() {
        Contact contact = new Contact("1234567890", "John", "Doe", "5551234567", "123 Main St");
        service.addContact(contact);
        
        assertThrows(IllegalArgumentException.class, () -> {
            service.updateAddress("1234567890", null);
        });
    }
    
    // ==================== ADDITIONAL TESTS ====================
    // These tests provide additional coverage for edge cases
    
    /**
     * Test that multiple fields can be updated on the same contact.
     * Verifies that updating one field doesn't affect other fields.
     */
    @Test
    public void testUpdateMultipleFieldsOnSameContact() {
        // Add a contact
        Contact contact = new Contact("1234567890", "John", "Doe", "5551234567", "123 Main St");
        service.addContact(contact);
        
        // Update all four updatable fields
        service.updateFirstName("1234567890", "Jane");
        service.updateLastName("1234567890", "Smith");
        service.updatePhone("1234567890", "9998887777");
        service.updateAddress("1234567890", "456 Oak Ave");
        
        // Verify all updates took effect
        Contact updated = service.getContact("1234567890");
        assertEquals("Jane", updated.getFirstName());
        assertEquals("Smith", updated.getLastName());
        assertEquals("9998887777", updated.getPhone());
        assertEquals("456 Oak Ave", updated.getAddress());
    }
    
    /**
     * Test that getContact returns null for a non-existent contact ID.
     * Unlike delete/update which throw exceptions, get simply returns null.
     */
    @Test
    public void testGetContactNotFound() {
        // Try to get a contact that doesn't exist
        assertNull(service.getContact("NONEXISTENT"));
    }
}
