package Database;
import People.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface for the Contact Data Access Object that handles
 * the CRUD operations for the Contact class with the database.
 */
public interface IPersonDAO {
    void addPerson(Person newPerson);

    Person getPersonByEmail(String email);

    Person getPerson(int id);

    List<Person> getAllPeople();

    void deletePerson(Person person);
}
