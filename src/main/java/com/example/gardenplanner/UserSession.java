package com.example.gardenplanner;

import People.IPerson;

public class UserSession {
    private static UserSession instance;
    private int personId;
    private String email;
    private String firstName;
    private String lastName;

    private UserSession() {}

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void clearSession() {
        personId = -1;
        email = null;
        firstName = null;
        lastName = null;
        instance = null;
    }
}
