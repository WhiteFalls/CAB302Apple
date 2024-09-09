package com.example.gardenplanner.model;

import java.util.ArrayList;
import java.util.List;

public class MockUsers implements IMockUsersDAO {

        /**
         * A static list of contacts to be used as a mock database.
         */
        // public static final ArrayList<Person> users = new ArrayList<>();
        private static int autoIncrementedId = 0;
        public static final ArrayList<Person> persons = new ArrayList<>();

        public MockUsers() {
            // Add some initial contacts to the mock database
            addPerson(new Person("Clean", "1", "johndoe@example.com", "1"));
            addPerson(new Person("Water", "2", "janedoe@example.com", "2"));
            addPerson(new Person("Trim", "3", "jaydoe@example.com", "3"));
            addPerson(new Person("Collect", "4", "jerrydoe@example.com","4"));
        }

        public void addPerson(Person person) {
            person.setId(autoIncrementedId);
            autoIncrementedId++;
            persons.add(person);
        }


        public void updatePerson(Person person) {
            for (int i = 0; i < persons.size(); i++) {
                if (persons.get(i).getId() == person.getId()) {
                    persons.set(i, person);
                    break;
                }
            }
        }


        public void deletePerson(Person person) {
            persons.remove(person);
        }


        public Person getPerson(int id) {
            for (Person person : persons) {
                if (person.getId() == id) {
                    return person;
                }
            }
            return null;
        }


        public List<Person> getAllPersons() {
            return new ArrayList<>(persons);
        }

    public int getCurrentUserId(int id) {
        for (Person person : persons) {
            if (person.getId() == id) {
                return person.getId();
            }
        }
        return -1;  // Return -1 if user is not found
    }


}


