# CS-320: Software Testing, Automation & Quality Assurance

This folder contains selected portfolio artifacts from CS-320 at Southern New Hampshire University. The work shown here demonstrates my ability to design unit tests that uncover defects, evaluate testing approaches against documented requirements, and apply appropriate testing strategies to deliver software that is both functional and secure.

## Artifacts

**Project One — Contact Service**
- `Contact.java`
- `ContactService.java`
- `ContactTest.java`
- `ContactServiceTest.java`

The Contact Service is one of three back-end services I built for a mobile application as part of Project One. It enforces strict validation on a `Contact` object (10-character maximum on most fields, exactly 10 digits on phone, an immutable contact ID) and exposes add, update, and delete operations through an in-memory `HashMap`. The accompanying JUnit 5 test suite verifies every requirement, including null inputs, boundary lengths, and rejection of duplicate IDs.

**Project Two — Summary and Reflections Report**
- `Project-Two-Summary-Reflections.docx`

This report summarizes the unit testing approach I took across the contact, task, and appointment services, defends the quality of the JUnit suite using coverage data, and reflects on the testing techniques I employed and the mindset I adopted as a tester.

---

## Reflection

### How can I ensure that my code, program, or software is functional and secure?

Functionality and security both come from treating requirements as testable claims rather than suggestions. In the Contact Service, every requirement (such as "phone shall be exactly 10 digits" or "contactId shall not be updatable") was translated into specific JUnit 5 tests that exercise the rule from both directions: a positive case proving the valid input is accepted, and a negative case proving an invalid input is rejected with an `IllegalArgumentException`. Boundary value tests on the 10-character limits caught off-by-one errors that surface checks alone would have missed.

The security side follows naturally. Validating every field in the constructor, declaring `contactId` as `final`, and centralizing validation logic in private helper methods so the constructor and setters cannot drift apart all close the kinds of gaps that lead to corrupt or malicious data entering the system. Achieving 100 percent line and branch coverage across the service gave me confidence that no validation path was silently failing.

### How do I interpret user needs and incorporate them into a program?

I treat the requirements document as the source of truth and translate each "shall" statement into a contract that the code is required to honor. For the Contact Service, that meant reading the requirement list carefully, listing the constraints in a table (field, type, length, nullability, mutability), and then writing both the production code and the test suite directly from that table. Each test method name was deliberately written to map back to a single requirement, so a future reader can trace any test to the user need it protects.

This same approach scaled to the task and appointment services. Once the requirements were unambiguous, the implementation became a straightforward matter of enforcing them and proving the enforcement worked. When a requirement was less obvious (for example, the rule that an appointment date cannot be in the past), I leaned on `java.util.Date#before(new Date())` and wrote tests using `Calendar` to construct dates one day in the past and one day in the future, so the boundary was exercised explicitly.

### How do I approach designing software?

I design from the requirements outward, with testability as a first-class concern. Before writing any production code I identify the data each class must hold, the invariants those fields must satisfy, and the operations the class must support. Constants are named (`MAX_CONTACT_ID_LENGTH`, `PHONE_LENGTH`) rather than scattered as magic numbers, services use simple in-memory structures like `HashMap` keyed on the unique ID, and validation logic is centralized in private helpers so it cannot be bypassed.

I also design the tests in parallel with the production code. Using JUnit 5 features such as `@BeforeEach` for shared setup and `assertThrows` for negative cases keeps the test suite expressive and concise, which makes it easier to maintain as the code evolves. The result is software that is small, predictable, and easy to verify, which is the most reliable foundation I have found for shipping work that is both correct and trustworthy.
