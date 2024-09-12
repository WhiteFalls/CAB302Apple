package com.example.gardenplanner;

import People.IMockPerson;
import People.MockPerson;
import Tasks.ITaskDAO;
import Tasks.Task;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

public class MockPersonDAO implements IPersonDAO {
    /**
     * A static list of contacts to be used as a mock database.
     */
    public static final ArrayList<IMockPerson> people = new ArrayList<>();
    private static int autoIncrementedId = 0;
    private static ITaskDAO taskDAO;

    public MockPersonDAO() {
        this.taskDAO = taskDAO;
        // Add some initial contacts to the mock database
        addPerson(new MockPerson("John", "Doe", "johndoe@example.com", "0000"));
        addPerson(new MockPerson("Jane", "Doe", "janedoe@example.com", "0001"));
        addPerson(new MockPerson("Jay", "Doe", "jaydoe@example.com", "0002"));
        addPerson(new MockPerson("Jerry", "Doe", "jerrydoe@example.com", "0003"));
    }

    @Override
    public void addPerson(MockPerson mockPerson) {
        mockPerson.setId(autoIncrementedId);

        ArrayList<Task> userTasks = new ArrayList<Task>();
        LocalDate aDate = LocalDate.of(2024, Month.APRIL, 1);
        LocalDate dDate = LocalDate.of(2024, Month.APRIL, 1);

        autoIncrementedId++;
        people.add(mockPerson);
    }

    @Override
    public void updatePerson(IMockPerson contact) {
        for (int i = 0; i < people.size(); i++) {
            if (people.get(i).getId() == contact.getId()) {
                people.set(i, contact);
                break;
            }
        }
    }

    @Override
    public void deletePerson(IMockPerson contact) {
        people.remove(contact);
    }

    @Override
    public IMockPerson getPerson(int id) {
        for (IMockPerson contact : people) {
            if (contact.getId() == id) {
                return contact;
            }
        }
        return null;
    }

    @Override
    public ArrayList<IMockPerson> getAllPeople() {
        return new ArrayList<>(people);
    }
}

