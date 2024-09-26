package Database;

import People.IPerson;
import People.Person;
import Tasks.ITaskDAO;
import Tasks.Task;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

public class MockPersonDAO implements IPersonDAO {
    /**
     * A static list of contacts to be used as a mock database.
     */
    public static final ArrayList<IPerson> people = new ArrayList<>();
    private static int autoIncrementedId = 0;
    private static ITaskDAO taskDAO;

    public MockPersonDAO() {
        //this.taskDAO = taskDAO;
        // Add some initial contacts to the mock database
        addPerson(new Person("John", "Doe", "johndoe@example.com", "0000",""));
        addPerson(new Person("Jane", "Doe", "janedoe@example.com", "0001",""));
        addPerson(new Person("Jay", "Doe", "jaydoe@example.com", "0002",""));
        addPerson(new Person("Jerry", "Doe", "jerrydoe@example.com", "0003",""));
    }

    @Override
    public void addPerson(Person person) {

    }

    @Override
    public Person getPersonByEmail(String email) {
        return null;
    }

    @Override
    public void addPerson(IPerson person) {
        //person.setUserId(autoIncrementedId);
        person.setUserId(autoIncrementedId);

        ArrayList<Task> userTasks = new ArrayList<Task>();
        LocalDate aDate = LocalDate.of(2024, Month.APRIL, 1);
        LocalDate dDate = LocalDate.of(2024, Month.APRIL, 1);

        autoIncrementedId++;
        people.add(person);
    }

    @Override
    public void updatePerson(IPerson contact) {
        for (int i = 0; i < people.size(); i++) {
            if (people.get(i).getUserId() == contact.getUserId()) {
                people.set(i, contact);
                break;
            }
        }
    }

    @Override
    public void deletePerson(IPerson contact) {
        people.remove(contact);
    }

    @Override
    public void deletePersonFromGarden(IPerson person, int garden_ID) throws IllegalArgumentException {

    }

    @Override
    public IPerson getPerson(int id) {
        for (IPerson contact : people) {
            if (contact.getUserId() == id) {
                return contact;
            }
        }
        return null;
    }

    @Override
    public ArrayList<IPerson> getAllPeople() {
        return new ArrayList<>(people);
    }

    @Override
    public void deletePerson(Person person) {

    }
}

