package com.example.gardenplanner;

public class AccountDetails
{
    private String username;
    private String email;
    private String password;
    private String profile_picture;

    public void Account(String username, String email, String profile_picture) {
        this.username = username;
        this.email = email;
        this.profile_picture = profile_picture;
    }

    public String getUsername()
    {
        String username = "";
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
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
        System.out.print("Username:" + username);
        System.out.print("Email:" + email);

    }




}