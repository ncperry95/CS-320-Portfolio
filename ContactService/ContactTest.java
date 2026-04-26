package ProjectOne.ContactService;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Contact class.
 * 
 * This test class verifies that the Contact class correctly implements
 * all five requirements from the specification:
 * 
 * Requirement 1: Contact ID
 *   - Must be unique (uniqueness enforced by ContactService, not here)
 *   - Cannot be longer than 10 characters
 *   - Cannot be null
 *   - Cannot be updated after creation
 * 
 * Requirement 2: First Name
 *   - Cannot be longer than 10 characters
 *   - Cannot be null
 * 
 * Requirement 3: Last Name
 *   - Cannot be longer than 10 characters
 *   - Cannot be null
 * 
 * Requirement 4: Phone
 *   - Must be exactly 10 digits
 *   - Cannot be null
 * 
 * Requirement 5: Address
 *   - Cannot be longer than 30 characters
 *   - Cannot be null
 * 
 * Each test method is named to clearly indicate what it's testing.
 * Tests use assertThrows() to verify that invalid inputs cause exceptions.
 */
public class ContactTest {
    
    // ==================== VALID CONTACT CREATION TESTS ====================
    // These tests verify that valid inputs create a Contact successfully
    
    /**
     * Test that a contact can be created with valid values for all fields.
     * This is a "happy path" test - everything works as expected.
     */
    @Test
    public void testContactCreationValid() {
        // Create a contact with valid values
        Contact contact = new Contact("1234567890", "John", "Doe", "5551234567", "123 Main St");
        
        // Verify all fields were stored correctly
        assertEquals("1234567890", contact.getContactId());
        assertEquals("John", contact.getFirstName());
        assertEquals("Doe", contact.getLastName());
        assertEquals("5551234567", contact.getPhone());
        assertEquals("123 Main St", contact.getAddress());
    }
    
    /**
     * Test that a contact can be created with maximum allowed lengths for all fields.
     * This is a boundary test - we test right at the maximum allowed limit.
     */
    @Test
    public void testContactCreationWithMaxLengthFields() {
        // Create strings at exactly the maximum allowed length
        String maxId = "1234567890";           // Exactly 10 characters
        String maxFirstName = "Johnnathan";    // Exactly 10 characters
        String maxLastName = "Smithfield";     // Exactly 10 characters
        String phone = "5551234567";           // Exactly 10 digits
        String maxAddress = "123456789012345678901234567890"; // Exactly 30 characters
        
        // Create the contact - should succeed without throwing
        Contact contact = new Contact(maxId, maxFirstName, maxLastName, phone, maxAddress);
        
        // Verify the contact was created successfully
        assertNotNull(contact);
        assertEquals(maxId, contact.getContactId());
        assertEquals(maxFirstName, contact.getFirstName());
        assertEquals(maxLastName, contact.getLastName());
        assertEquals(phone, contact.getPhone());
        assertEquals(maxAddress, contact.getAddress());
    }
    
    // ==================== CONTACT ID TESTS (Requirement 1) ====================
    // Tests for: max 10 chars, not null, not updatable
    
    /**
     * Test that creating a contact with null contactId throws an exception.
     * Requirement: "The contact ID shall not be null"
     */
    @Test
    public void testContactIdNull() {
        // Attempting to create a contact with null ID should throw IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact(null, "John", "Doe", "5551234567", "123 Main St");
        });
    }
    
    /**
     * Test that creating a contact with contactId longer than 10 characters throws an exception.
     * Requirement: "Contact ID string that cannot be longer than 10 characters"
     */
    @Test
    public void testContactIdTooLong() {
        // 11 characters should be rejected
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("12345678901", "John", "Doe", "5551234567", "123 Main St");
        });
    }
    
    /**
     * Test that contactId cannot be changed after creation.
     * Requirement: "The contact ID shall not be updatable"
     * 
     * This is verified by design - the contactId field is declared 'final'
     * and there is no setContactId() method. The test confirms the ID
     * remains unchanged (there's no way to change it).
     */
    @Test
    public void testContactIdNotUpdatable() {
        Contact contact = new Contact("1234567890", "John", "Doe", "5551234567", "123 Main St");
        
        // The contactId should still be the original value
        // There's no setter to even attempt to change it
        assertEquals("1234567890", contact.getContactId());
    }
    
    // ==================== FIRST NAME TESTS (Requirement 2) ====================
    // Tests for: max 10 chars, not null
    
    /**
     * Test that creating a contact with null firstName throws an exception.
     * Requirement: "The firstName field shall not be null"
     */
    @Test
    public void testFirstNameNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("1234567890", null, "Doe", "5551234567", "123 Main St");
        });
    }
    
    /**
     * Test that creating a contact with firstName longer than 10 characters throws an exception.
     * Requirement: "firstName String field that cannot be longer than 10 characters"
     */
    @Test
    public void testFirstNameTooLong() {
        // "Johnnathann" is 11 characters - should be rejected
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("1234567890", "Johnnathann", "Doe", "5551234567", "123 Main St");
        });
    }
    
    /**
     * Test that setFirstName() works with a valid value.
     */
    @Test
    public void testSetFirstNameValid() {
        Contact contact = new Contact("1234567890", "John", "Doe", "5551234567", "123 Main St");
        
        // Update the first name
        contact.setFirstName("Jane");
        
        // Verify it was updated
        assertEquals("Jane", contact.getFirstName());
    }
    
    /**
     * Test that setFirstName() with null throws an exception.
     */
    @Test
    public void testSetFirstNameNull() {
        Contact contact = new Contact("1234567890", "John", "Doe", "5551234567", "123 Main St");
        
        // Attempting to set null should throw
        assertThrows(IllegalArgumentException.class, () -> {
            contact.setFirstName(null);
        });
    }
    
    /**
     * Test that setFirstName() with value longer than 10 characters throws an exception.
     */
    @Test
    public void testSetFirstNameTooLong() {
        Contact contact = new Contact("1234567890", "John", "Doe", "5551234567", "123 Main St");
        
        // Attempting to set a name with 11 characters should throw
        assertThrows(IllegalArgumentException.class, () -> {
            contact.setFirstName("Johnnathann");
        });
    }
    
    // ==================== LAST NAME TESTS (Requirement 3) ====================
    // Tests for: max 10 chars, not null
    
    /**
     * Test that creating a contact with null lastName throws an exception.
     * Requirement: "The lastName field shall not be null"
     */
    @Test
    public void testLastNameNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("1234567890", "John", null, "5551234567", "123 Main St");
        });
    }
    
    /**
     * Test that creating a contact with lastName longer than 10 characters throws an exception.
     * Requirement: "lastName String field that cannot be longer than 10 characters"
     */
    @Test
    public void testLastNameTooLong() {
        // "Smithfieldx" is 11 characters - should be rejected
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("1234567890", "John", "Smithfieldx", "5551234567", "123 Main St");
        });
    }
    
    /**
     * Test that setLastName() works with a valid value.
     */
    @Test
    public void testSetLastNameValid() {
        Contact contact = new Contact("1234567890", "John", "Doe", "5551234567", "123 Main St");
        
        // Update the last name
        contact.setLastName("Smith");
        
        // Verify it was updated
        assertEquals("Smith", contact.getLastName());
    }
    
    /**
     * Test that setLastName() with null throws an exception.
     */
    @Test
    public void testSetLastNameNull() {
        Contact contact = new Contact("1234567890", "John", "Doe", "5551234567", "123 Main St");
        
        assertThrows(IllegalArgumentException.class, () -> {
            contact.setLastName(null);
        });
    }
    
    /**
     * Test that setLastName() with value longer than 10 characters throws an exception.
     */
    @Test
    public void testSetLastNameTooLong() {
        Contact contact = new Contact("1234567890", "John", "Doe", "5551234567", "123 Main St");
        
        assertThrows(IllegalArgumentException.class, () -> {
            contact.setLastName("Smithfieldx");
        });
    }
    
    // ==================== PHONE TESTS (Requirement 4) ====================
    // Tests for: exactly 10 digits, not null
    
    /**
     * Test that creating a contact with null phone throws an exception.
     * Requirement: "The phone field shall not be null"
     */
    @Test
    public void testPhoneNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("1234567890", "John", "Doe", null, "123 Main St");
        });
    }
    
    /**
     * Test that creating a contact with phone shorter than 10 digits throws an exception.
     * Requirement: "phone String field that must be exactly 10 digits"
     */
    @Test
    public void testPhoneTooShort() {
        // 9 digits - should be rejected
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("1234567890", "John", "Doe", "123456789", "123 Main St");
        });
    }
    
    /**
     * Test that creating a contact with phone longer than 10 digits throws an exception.
     * Requirement: "phone String field that must be exactly 10 digits"
     */
    @Test
    public void testPhoneTooLong() {
        // 11 digits - should be rejected
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("1234567890", "John", "Doe", "12345678901", "123 Main St");
        });
    }
    
    /**
     * Test that creating a contact with non-digit characters in phone throws an exception.
     * Requirement: "phone String field that must be exactly 10 digits"
     * The word "digits" implies only numeric characters are allowed.
     */
    @Test
    public void testPhoneWithNonDigits() {
        // Phone number with dashes should be rejected
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("1234567890", "John", "Doe", "555-123-45", "123 Main St");
        });
    }
    
    /**
     * Test that creating a contact with letters in phone throws an exception.
     */
    @Test
    public void testPhoneWithLetters() {
        // Phone number with letters should be rejected
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("1234567890", "John", "Doe", "555CALLNOW", "123 Main St");
        });
    }
    
    /**
     * Test that setPhone() works with a valid value.
     */
    @Test
    public void testSetPhoneValid() {
        Contact contact = new Contact("1234567890", "John", "Doe", "5551234567", "123 Main St");
        
        // Update the phone number
        contact.setPhone("9998887777");
        
        // Verify it was updated
        assertEquals("9998887777", contact.getPhone());
    }
    
    /**
     * Test that setPhone() with null throws an exception.
     */
    @Test
    public void testSetPhoneNull() {
        Contact contact = new Contact("1234567890", "John", "Doe", "5551234567", "123 Main St");
        
        assertThrows(IllegalArgumentException.class, () -> {
            contact.setPhone(null);
        });
    }
    
    /**
     * Test that setPhone() with invalid length throws an exception.
     */
    @Test
    public void testSetPhoneInvalidLength() {
        Contact contact = new Contact("1234567890", "John", "Doe", "5551234567", "123 Main St");
        
        // 9 digits - should be rejected
        assertThrows(IllegalArgumentException.class, () -> {
            contact.setPhone("123456789");
        });
    }
    
    /**
     * Test that setPhone() with non-digit characters throws an exception.
     */
    @Test
    public void testSetPhoneWithNonDigits() {
        Contact contact = new Contact("1234567890", "John", "Doe", "5551234567", "123 Main St");
        
        assertThrows(IllegalArgumentException.class, () -> {
            contact.setPhone("555-123-45");
        });
    }
    
    // ==================== ADDRESS TESTS (Requirement 5) ====================
    // Tests for: max 30 chars, not null
    
    /**
     * Test that creating a contact with null address throws an exception.
     * Requirement: "The address field shall not be null"
     */
    @Test
    public void testAddressNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("1234567890", "John", "Doe", "5551234567", null);
        });
    }
    
    /**
     * Test that creating a contact with address longer than 30 characters throws an exception.
     * Requirement: "address field that must be no longer than 30 characters"
     */
    @Test
    public void testAddressTooLong() {
        // 31 characters - should be rejected
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("1234567890", "John", "Doe", "5551234567", 
                       "1234567890123456789012345678901");
        });
    }
    
    /**
     * Test that setAddress() works with a valid value.
     */
    @Test
    public void testSetAddressValid() {
        Contact contact = new Contact("1234567890", "John", "Doe", "5551234567", "123 Main St");
        
        // Update the address
        contact.setAddress("456 Oak Ave");
        
        // Verify it was updated
        assertEquals("456 Oak Ave", contact.getAddress());
    }
    
    /**
     * Test that setAddress() with null throws an exception.
     */
    @Test
    public void testSetAddressNull() {
        Contact contact = new Contact("1234567890", "John", "Doe", "5551234567", "123 Main St");
        
        assertThrows(IllegalArgumentException.class, () -> {
            contact.setAddress(null);
        });
    }
    
    /**
     * Test that setAddress() with value longer than 30 characters throws an exception.
     */
    @Test
    public void testSetAddressTooLong() {
        Contact contact = new Contact("1234567890", "John", "Doe", "5551234567", "123 Main St");
        
        // 31 characters - should be rejected
        assertThrows(IllegalArgumentException.class, () -> {
            contact.setAddress("1234567890123456789012345678901");
        });
    }
}
