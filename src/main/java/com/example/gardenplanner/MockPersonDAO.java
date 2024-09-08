package com.example.gardenplanner;

import People.IPerson;
import People.Person;
import Tasks.Task;
import javafx.scene.control.ListView;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class MockPersonDAO implements IPersonDAO {
    /**
     * A static list of contacts to be used as a mock database.
     */
    public static final ArrayList<IPerson> people = new ArrayList<>();
    private static int autoIncrementedId = 0;
    private static int getAutoIncrementedIdTask = 0;

    public MockPersonDAO() {
        // Add some initial contacts to the mock database
        addPerson(new Person("John", "Doe", "johndoe@example.com", "0000"));
        addPerson(new Person("Jane", "Doe", "janedoe@example.com", "0001"));
        addPerson(new Person("Jay", "Doe", "jaydoe@example.com", "0002"));
        addPerson(new Person("Jerry", "Doe", "jerrydoe@example.com", "0003"));
    }

    @Override
    public void addPerson(Person person) {
        person.setId(autoIncrementedId);

        ArrayList<Task> userTasks = new ArrayList<Task>();
        LocalDate aDate = LocalDate.of(2024, Month.APRIL, 1);
        LocalDate dDate = LocalDate.of(2024, Month.APRIL, 1);

        Task task1 = new Task("Wash Beans", aDate, dDate);
        Task task2 = new Task("Burn Cabbages", aDate, dDate);
        task1.setId(getAutoIncrementedIdTask);
        getAutoIncrementedIdTask++;
        task2.setId(getAutoIncrementedIdTask);

        userTasks.add(task1);
        userTasks.add(task2);
        person.setTasks(userTasks);


        autoIncrementedId++;
        getAutoIncrementedIdTask++;
        people.add(person);
    }

    @Override
    public void updatePerson(IPerson contact) {
        for (int i = 0; i < people.size(); i++) {
            if (people.get(i).getId() == contact.getId()) {
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
    public IPerson getPerson(int id) {
        for (IPerson contact : people) {
            if (contact.getId() == id) {
                return contact;
            }
        }
        return null;
    }

    @Override
    public ArrayList<IPerson> getAllPeople() {
        return new ArrayList<>(people);
    }
}

