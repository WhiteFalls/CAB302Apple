package com.example.gardenplanner;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDetailsDAO {
    private Connection connection;

    public AccountDetailsDAO()
    {
        connection = DatabaseConnection.getInstance();
    }

    public void createTable()
    {
        try {
            // create statement to search table for username query
            Statement createTable = connection.createStatement();
            createTable.execute(
                    "CREATE TABLE IF NOT EXISTS userID ("
                            + "userID INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + "firstName VARCHAR NOT NULL, "
                            + "lastName VARCHAR NOT NULL, "
                            + "email VARCHAR NOT NULL"
                            + "password VARCHAR NOT NULL"
                            + ")"
            );
        }
        // call this method in the main class
        /*
        public class Main {
            public static void main(String[] args) {
                AccountDetailsDAO accountDetailsDAO = new accountDetailsDAO();
                accountDetailsDAO.createTable();

                accountDetailsDAO.close();
            }
        }
         */
        catch (SQLException ex)
        {
            System.err.println(ex);
        }

    }


    public void insertDetails(AccountDetails accountDetails)
    {
        try {
            // running insert query
            PreparedStatement insertAccountDetails = connection.prepareStatement(
                    "INSERT INTO AccountDetails (firstName, lastName, email, password) " +
                            "VALUES (?, ?, ?, ?)"
            );
            insertAccountDetails.setString(1, accountDetails.getFirstName());
            insertAccountDetails.setString(2, accountDetails.getLastName());
            insertAccountDetails.setString(3, accountDetails.getEmail());
            insertAccountDetails.setString(4, accountDetails.getPassword());
            insertAccountDetails.execute();
        }
        catch(SQLException ex)
        {
            System.err.println(ex);
        }
    }

    // call method in main class
    /*
    public class Main {
    public static void main(String[] args) {
        AccountDetailsDAO accountDetailsDAO = new AccountDetailsDAO();
        accountDetailsDAO.createTable();

        // Insert some new records
        bankAccountDAO.insert(new BankAccount("testFirstName", "testLastname", "test email", "test password"));

        bankAccountDAO.close();
    }
}
     */

    public void updateDetails(AccountDetails accountDetails)
    {
        try
        {
            PreparedStatement updateAccountDetails = connection.prepareStatement(
                    "UPDATE AccountDetails SET firstName = ?, lastName = ?, email = ?, password = ?, WHERE id = ?"
            );
            updateAccountDetails.setString(1, accountDetails.getFirstName());
            updateAccountDetails.setString(2, accountDetails.getLastName());
            updateAccountDetails.setString(3, accountDetails.getEmail());
            updateAccountDetails.setString(4,accountDetails.getPassword());
            updateAccountDetails.setInt(5, accountDetails.getUserID());
            updateAccountDetails.execute();
        }
        catch (SQLException ex)
        {
            System.err.println(ex);
        }
        // running update query
    }


    /* // UPDATE DETAILS FOR MAIN
    public static void updateDetails
    {
    // records here
    Listt<AccountDetails> accountDetails = AccountDetailsDAO.getAll() // retrieving all records
    for (AccountDetails accdeet : accountDetails
        {
            System.out.println(acc);
        }
    
    //Retrieving a record by ID
    AccountDetails accountDetails = AccountDetailsDAO.getByID(userID);
    System.out.println("Previous details:");
    System.out.println(accountDetails);

    // Update a record
    accountDetails.setAccountDetails(string fname, string lname, string email, string password)
    AccountDetailsDao.update(accountDetails);
    System.out.println("After update: List<accountDetails> );
    System.out.println(AccountDetailsDAO.getByID(userID));

    AccountDetailsDAO.close();
    }

     */


    public void deleteAccountDetails(int userID)
    {
        try
        {
            PreparedStatement deleteAccountDetails = connection.prepareStatement(
                    "DELETE FROM bankAccounts WHERE id = ?"
            );
            deleteAccountDetails.setInt(1, deleteAccountDetails.getuserID()); // deleteAccountDetails.setInt( delete userID)

            deleteAccountDetails.execute();
        }
        catch(SQLException ex)
        {
            System.err.println(ex);
        }

    }

    public List<AccountDetails> getAll() {
        List<AccountDetails> accountDetails = new ArrayList<>();
        try {
            Statement getAll = connection.createStatement();
            ResultSet rs = getAll.executeQuery("SELECT * FROM bankAccounts");
            while (rs.next()) {
                accounts.add(
                        new BankAccount(
                                rs.getInt("id"),
                                rs.getString("firstName"),
                                rs.getString("lastName"),
                                rs.getInt("bankBalance")
                        )
                ); // gets all information inputted by user -> needs to be presented
            }
            catch(SQLException ex)
            {
                System.err.println(ex)
            }
        }
        return accountDetails;
    }

        /*
        to go into main:

        public static void presentDetails
        List<BankAccount> accounts = bankAccountDAO.getAll();
        for (BankAccount acc : accounts) {
            System.out.println(acc);
         }

         AccountDetailsDAO.close();
         */

        // getting a record by ID
    public accountDetails searchUserIDdetails(int userID)
    {
        try {
            PreparedStatement getAccount = connection.prepareStatement("SELECT * FROM bankAccounts WHERE id = ?");
            getAccount.setInt(1, userID); // later userID will need to be autoincremented and linked to email address in a key-value pair
            ResultSet rs = getAccount.executeQuery();
            if (rs.next())
                {
                    return new BankAccount(
                        rs.getInt("id"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getInt("bankBalance")
                     );
                }
            }
                catch (SQLException ex)
            {
                System.err.println(ex);
            }
            return null;
    }

    /*
    // functionality for main
    public static void searchUserID details
    AccountDetails accountDetails = accountDetailsDAO.getById(2) // eventually 2 will be a variable
    System.out.println(accountDetails);
    accountDetailsDAO.close();
     */


    public void closeAccountDetails()
    {
        try
        {
            connection.close();
        }
        catch (SQLException ex)
        {
            System.err.println(ex);
        }
    }

}