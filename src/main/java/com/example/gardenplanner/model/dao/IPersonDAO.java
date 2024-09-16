<<<<<<<< HEAD:src/main/java/Database/IPersonDAO.java
package Database;
import People.IMockPerson;
========
package com.example.gardenplanner.model.dao;

>>>>>>>> usertodoList:src/main/java/com/example/gardenplanner/model/dao/IPersonDAO.java

import com.example.gardenplanner.model.person.IPerson;
import com.example.gardenplanner.model.person.Person;

import java.util.ArrayList;

/**
 * Interface for the Contact Data Access Object that handles
 * the CRUD operations for the Contact class with the database.
 */
public interface IPersonDAO {
    /**
     * Adds a new contact to the database.
     * @param mockPerson The contact to add.
     */
    public void addPerson(IMockPerson mockPerson);
    /**
     * Updates an existing contact in the database.
     * @param person The contact to update.
     */
    public void updatePerson(IMockPerson person);
    /**
     * Deletes a contact from the database.
     * @param person The contact to delete.
     */
    public void deletePerson(IMockPerson person);
    /**
     * Retrieves a contact from the database.
     * @param id The id of the contact to retrieve.
     * @return The contact with the given id, or null if not found.
     */
    public IMockPerson getPerson(int id);
    /**
     * Retrieves all contacts from the database.
     * @return A list of all contacts in the database.
     */
    public ArrayList<IMockPerson> getAllPeople();
}