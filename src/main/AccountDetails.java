package com.example.gardenplanner;

public class AccountDetails
{
    private int userID;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    //private String id(?)

    public AccountDetails(int userID, String firstName, String lastName, String email, String password)
    {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    // username is autoincremented so userID is omitted
    public AccountDetails(String firstName, String lastName, String email, String password)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public int getUserID()
    {
        return getUserID();
    }

    public void setUserID(int userID)
    {
        this.userID = userID;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;m
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        if (email != null && email.contains( "@"))
        {
            this.email = email;
        }
        else
        {
            throw new IllegalArgumentException("Invalid email format");
        }

    }

    // PASSWORDS
    public String getPassword()
    {
        return password;
    }

    public void setPassword()
    {
        if (password != null && password.length() >= 8)
        {
            this.password = password;
        }
        else
        {
            throw new IllegalArgumentException();
        }

    }

    public void DisplayAccountDetails()
    {
        System.out.print("User ID:" + userID);
        System.out.print("Email:" + email);

    }




}