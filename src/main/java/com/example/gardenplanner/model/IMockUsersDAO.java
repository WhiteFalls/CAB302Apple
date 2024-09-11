package com.example.gardenplanner.model;

import java.util.List;

public interface IMockUsersDAO {
    /**
     * Adds a new task to the database.
     * @param person The task to add.
     */
    public void addPerson(Person person);
    /**
     * Updates an existing task in the database.
     * @param person The task to update.
     */
    public void updatePerson(Person person);
    /**
     * Deletes a task from the database.
     * @param person The task to delete.
     */
    public void deletePerson(Person person);
    /**
     * Retrieves a task from the database.
     * @param id The id of the contact to retrieve.
     * @return The task with the given id, or null if not found.
     */
    public Person getPerson(int id);
    /**
     * Retrieves all task from the database.
     * @return A list of all task in the database.
     */
    public List<Person> getAllPersons();

    public int getCurrentUserId(int id);
}
