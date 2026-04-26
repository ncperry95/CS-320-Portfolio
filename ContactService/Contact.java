package ProjectOne.ContactService;

/**
 * Contact class for storing contact information in a mobile application.
 * 
 * This class represents a single contact with five fields: contactId, firstName,
 * lastName, phone, and address. Each field has specific validation requirements
 * that are enforced both during object construction and when updating fields.
 * 
 * The contactId is immutable (cannot be changed after creation) to ensure
 * data integrity when contacts are stored and retrieved from the contact service.
 * 
 * All validation failures throw IllegalArgumentException with a descriptive message.
 */
public class Contact {
    
    // ==================== CONSTANTS ====================
    // These constants define the maximum allowed lengths for each field.
    // Using constants makes the code easier to maintain - if requirements
    // change, we only need to update these values in one place.
    
    private static final int MAX_CONTACT_ID_LENGTH = 10;   // Contact ID: max 10 characters
    private static final int MAX_FIRST_NAME_LENGTH = 10;   // First name: max 10 characters
    private static final int MAX_LAST_NAME_LENGTH = 10;    // Last name: max 10 characters
    private static final int PHONE_LENGTH = 10;            // Phone: exactly 10 digits
    private static final int MAX_ADDRESS_LENGTH = 30;      // Address: max 30 characters
    
    // ==================== INSTANCE VARIABLES ====================
    // These are the five required fields for a contact.
    // Note: contactId is declared 'final' which means it can only be assigned
    // once (in the constructor) and cannot be changed afterward. This enforces
    // the requirement that contact ID shall not be updatable.
    
    private final String contactId;  // Unique identifier - cannot be changed after creation
    private String firstName;        // Contact's first name
    private String lastName;         // Contact's last name
    private String phone;            // Contact's phone number (10 digits only)
    private String address;          // Contact's address
    
    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructs a new Contact object with the specified field values.
     * 
     * All five parameters are required and must pass validation:
     * - contactId: Cannot be null, cannot exceed 10 characters
     * - firstName: Cannot be null, cannot exceed 10 characters
     * - lastName: Cannot be null, cannot exceed 10 characters
     * - phone: Cannot be null, must be exactly 10 numeric digits (no dashes, spaces, or letters)
     * - address: Cannot be null, cannot exceed 30 characters
     * 
     * @param contactId Unique identifier for this contact (max 10 chars, immutable)
     * @param firstName Contact's first name (max 10 chars)
     * @param lastName Contact's last name (max 10 chars)
     * @param phone Contact's phone number (exactly 10 digits, numeric only)
     * @param address Contact's address (max 30 chars)
     * @throws IllegalArgumentException if any parameter fails validation
     */
    public Contact(String contactId, String firstName, String lastName, 
                   String phone, String address) {
        
        // Validate contactId: must not be null and must not exceed 10 characters
        // This ID is used to uniquely identify contacts in the contact service
        if (contactId == null || contactId.length() > MAX_CONTACT_ID_LENGTH) {
            throw new IllegalArgumentException("Invalid contact ID");
        }
        
        // Validate firstName: must not be null and must not exceed 10 characters
        if (firstName == null || firstName.length() > MAX_FIRST_NAME_LENGTH) {
            throw new IllegalArgumentException("Invalid first name");
        }
        
        // Validate lastName: must not be null and must not exceed 10 characters
        if (lastName == null || lastName.length() > MAX_LAST_NAME_LENGTH) {
            throw new IllegalArgumentException("Invalid last name");
        }
        
        // Validate phone: must not be null, must be exactly 10 characters,
        // and must contain only numeric digits (0-9).
        // The regex "\\d+" matches one or more digits. We also check length == 10
        // to ensure it's exactly 10 digits, not more or fewer.
        if (phone == null || phone.length() != PHONE_LENGTH || !phone.matches("\\d+")) {
            throw new IllegalArgumentException("Invalid phone number");
        }
        
        // Validate address: must not be null and must not exceed 30 characters
        if (address == null || address.length() > MAX_ADDRESS_LENGTH) {
            throw new IllegalArgumentException("Invalid address");
        }
        
        // All validations passed - assign the values to instance variables
        this.contactId = contactId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
    }
    
    // ==================== GETTER METHODS ====================
    // Getters allow other classes to read the contact's field values.
    // All five fields have getters so they can be retrieved.
    
    /**
     * Returns the contact's unique identifier.
     * This value cannot be changed after the contact is created.
     * 
     * @return The contact ID string
     */
    public String getContactId() {
        return contactId;
    }
    
    /**
     * Returns the contact's first name.
     * 
     * @return The first name string
     */
    public String getFirstName() {
        return firstName;
    }
    
    /**
     * Returns the contact's last name.
     * 
     * @return The last name string
     */
    public String getLastName() {
        return lastName;
    }
    
    /**
     * Returns the contact's phone number.
     * The phone number is always exactly 10 numeric digits.
     * 
     * @return The phone number string
     */
    public String getPhone() {
        return phone;
    }
    
    /**
     * Returns the contact's address.
     * 
     * @return The address string
     */
    public String getAddress() {
        return address;
    }
    
    // ==================== SETTER METHODS ====================
    // Setters allow other classes to update the contact's field values.
    // Note: There is NO setter for contactId because the requirement states
    // that contact ID "shall not be updatable". The 'final' keyword on the
    // contactId field enforces this at compile time.
    
    /**
     * Updates the contact's first name.
     * The new value must pass the same validation as the constructor:
     * cannot be null and cannot exceed 10 characters.
     * 
     * @param firstName The new first name value
     * @throws IllegalArgumentException if firstName is null or exceeds 10 characters
     */
    public void setFirstName(String firstName) {
        // Apply the same validation rules as the constructor
        if (firstName == null || firstName.length() > MAX_FIRST_NAME_LENGTH) {
            throw new IllegalArgumentException("Invalid first name");
        }
        this.firstName = firstName;
    }
    
    /**
     * Updates the contact's last name.
     * The new value must pass the same validation as the constructor:
     * cannot be null and cannot exceed 10 characters.
     * 
     * @param lastName The new last name value
     * @throws IllegalArgumentException if lastName is null or exceeds 10 characters
     */
    public void setLastName(String lastName) {
        // Apply the same validation rules as the constructor
        if (lastName == null || lastName.length() > MAX_LAST_NAME_LENGTH) {
            throw new IllegalArgumentException("Invalid last name");
        }
        this.lastName = lastName;
    }
    
    /**
     * Updates the contact's phone number.
     * The new value must pass the same validation as the constructor:
     * cannot be null, must be exactly 10 characters, and must contain only digits.
     * 
     * @param phone The new phone number value
     * @throws IllegalArgumentException if phone is null, wrong length, or contains non-digits
     */
    public void setPhone(String phone) {
        // Apply the same validation rules as the constructor
        // Phone must be exactly 10 numeric digits
        if (phone == null || phone.length() != PHONE_LENGTH || !phone.matches("\\d+")) {
            throw new IllegalArgumentException("Invalid phone number");
        }
        this.phone = phone;
    }
    
    /**
     * Updates the contact's address.
     * The new value must pass the same validation as the constructor:
     * cannot be null and cannot exceed 30 characters.
     * 
     * @param address The new address value
     * @throws IllegalArgumentException if address is null or exceeds 30 characters
     */
    public void setAddress(String address) {
        // Apply the same validation rules as the constructor
        if (address == null || address.length() > MAX_ADDRESS_LENGTH) {
            throw new IllegalArgumentException("Invalid address");
        }
        this.address = address;
    }
}
