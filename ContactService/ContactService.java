package ProjectOne.ContactService;

import java.util.HashMap;
import java.util.Map;

/**
 * ContactService class for managing contacts in memory.
 * 
 * This service provides the core functionality for a contact management system:
 * - Adding new contacts (with unique ID enforcement)
 * - Deleting existing contacts by their ID
 * - Updating individual fields of existing contacts
 * 
 * The service uses a HashMap as an in-memory data structure to store contacts.
 * The HashMap uses the contact ID as the key, which provides O(1) lookup time
 * for retrieving, updating, or deleting contacts by their ID.
 * 
 * Note: This is an in-memory implementation with no database persistence.
 * All data is lost when the application terminates.
 */
public class ContactService {
    
    // ==================== DATA STORAGE ====================
    // HashMap stores contacts with contactId as the key and Contact object as the value.
    // This allows fast O(1) lookups when we need to find, update, or delete a contact.
    // The 'final' keyword means we can't reassign the map, but we can still add/remove entries.
    
    private final Map<String, Contact> contacts = new HashMap<>();
    
    // ==================== ADD OPERATION ====================
    
    /**
     * Adds a new contact to the service.
     * 
     * This method enforces the unique ID requirement - if a contact with the
     * same ID already exists in the system, the operation will fail with an
     * exception. This prevents accidental overwrites of existing contact data.
     * 
     * @param contact The Contact object to add to the service
     * @throws IllegalArgumentException if contact is null
     * @throws IllegalArgumentException if a contact with the same ID already exists
     */
    public void addContact(Contact contact) {
        // Check that the contact object is not null
        if (contact == null) {
            throw new IllegalArgumentException("Contact cannot be null");
        }
        
        // Check if a contact with this ID already exists
        // containsKey() returns true if the HashMap already has an entry with this key
        if (contacts.containsKey(contact.getContactId())) {
            throw new IllegalArgumentException("Contact ID already exists");
        }
        
        // Add the contact to the HashMap
        // The contact ID is used as the key for fast retrieval later
        contacts.put(contact.getContactId(), contact);
    }
    
    // ==================== DELETE OPERATION ====================
    
    /**
     * Deletes a contact from the service by its contact ID.
     * 
     * This method removes the contact completely from the system.
     * If no contact exists with the given ID, an exception is thrown
     * to alert the caller that the delete operation couldn't be performed.
     * 
     * @param contactId The unique ID of the contact to delete
     * @throws IllegalArgumentException if no contact exists with the given ID
     */
    public void deleteContact(String contactId) {
        // Check if a contact with this ID exists before attempting to delete
        if (!contacts.containsKey(contactId)) {
            throw new IllegalArgumentException("Contact ID not found");
        }
        
        // Remove the contact from the HashMap
        contacts.remove(contactId);
    }
    
    // ==================== UPDATE OPERATIONS ====================
    // Each updatable field has its own update method.
    // These methods first locate the contact by ID, then call the
    // appropriate setter method on the Contact object to update the field.
    // The setter methods in Contact class handle validation.
    
    /**
     * Updates the first name of an existing contact.
     * 
     * This method finds the contact by ID and updates only the firstName field.
     * The new firstName value must pass validation (not null, max 10 chars).
     * 
     * @param contactId The unique ID of the contact to update
     * @param firstName The new first name value
     * @throws IllegalArgumentException if no contact exists with the given ID
     * @throws IllegalArgumentException if firstName is null or exceeds 10 characters
     */
    public void updateFirstName(String contactId, String firstName) {
        // Find the contact or throw an exception if not found
        Contact contact = getContactOrThrow(contactId);
        
        // Update the first name using the Contact's setter method
        // The setter will validate the new value
        contact.setFirstName(firstName);
    }
    
    /**
     * Updates the last name of an existing contact.
     * 
     * This method finds the contact by ID and updates only the lastName field.
     * The new lastName value must pass validation (not null, max 10 chars).
     * 
     * @param contactId The unique ID of the contact to update
     * @param lastName The new last name value
     * @throws IllegalArgumentException if no contact exists with the given ID
     * @throws IllegalArgumentException if lastName is null or exceeds 10 characters
     */
    public void updateLastName(String contactId, String lastName) {
        // Find the contact or throw an exception if not found
        Contact contact = getContactOrThrow(contactId);
        
        // Update the last name using the Contact's setter method
        // The setter will validate the new value
        contact.setLastName(lastName);
    }
    
    /**
     * Updates the phone number of an existing contact.
     * 
     * This method finds the contact by ID and updates only the phone field.
     * The new phone value must pass validation (not null, exactly 10 digits).
     * 
     * @param contactId The unique ID of the contact to update
     * @param phone The new phone number value
     * @throws IllegalArgumentException if no contact exists with the given ID
     * @throws IllegalArgumentException if phone is null, wrong length, or contains non-digits
     */
    public void updatePhone(String contactId, String phone) {
        // Find the contact or throw an exception if not found
        Contact contact = getContactOrThrow(contactId);
        
        // Update the phone number using the Contact's setter method
        // The setter will validate the new value
        contact.setPhone(phone);
    }
    
    /**
     * Updates the address of an existing contact.
     * 
     * This method finds the contact by ID and updates only the address field.
     * The new address value must pass validation (not null, max 30 chars).
     * 
     * @param contactId The unique ID of the contact to update
     * @param address The new address value
     * @throws IllegalArgumentException if no contact exists with the given ID
     * @throws IllegalArgumentException if address is null or exceeds 30 characters
     */
    public void updateAddress(String contactId, String address) {
        // Find the contact or throw an exception if not found
        Contact contact = getContactOrThrow(contactId);
        
        // Update the address using the Contact's setter method
        // The setter will validate the new value
        contact.setAddress(address);
    }
    
    // ==================== RETRIEVAL OPERATION ====================
    
    /**
     * Retrieves a contact by its unique ID.
     * 
     * This is a public method that allows other parts of the application
     * to look up contacts. Returns null if no contact exists with the given ID.
     * 
     * @param contactId The unique ID of the contact to retrieve
     * @return The Contact object if found, or null if not found
     */
    public Contact getContact(String contactId) {
        // HashMap.get() returns null if the key doesn't exist
        return contacts.get(contactId);
    }
    
    // ==================== HELPER METHODS ====================
    
    /**
     * Helper method that retrieves a contact or throws an exception if not found.
     * 
     * This is a private method used internally by the update methods.
     * It centralizes the "find contact or throw" logic so we don't have to
     * repeat the same null-check code in every update method.
     * 
     * @param contactId The unique ID of the contact to retrieve
     * @return The Contact object (never null - throws exception instead)
     * @throws IllegalArgumentException if no contact exists with the given ID
     */
    private Contact getContactOrThrow(String contactId) {
        // Attempt to retrieve the contact from the HashMap
        Contact contact = contacts.get(contactId);
        
        // If the contact doesn't exist, throw an exception
        if (contact == null) {
            throw new IllegalArgumentException("Contact ID not found");
        }
        
        // Contact exists, return it
        return contact;
    }
}
