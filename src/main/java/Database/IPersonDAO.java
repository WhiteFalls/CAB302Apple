package Database;
import People.IPerson;
import People.Person;

/**
 * Interface for the Contact Data Access Object that handles
 * the CRUD operations for the Contact class with the database.
 */
public interface IPersonDAO {
    /**
     * Adds a person to the database
     * @param person The person to be added
     */
    void addPerson(Person person);

    Person getPersonByEmail(String email);

    /**
     * Retrieves a contact from the database.
     * @param id The id of the contact to retrieve.
     * @return The contact with the given id, or null if not found.
     */
    public IPerson getPerson(int id);

    /**
     * Checks whether an email is registered or not
     * @param email The email to be checked
     * @return True if the email is registered, else false
     */
    boolean isEmailRegistered(String email);
}