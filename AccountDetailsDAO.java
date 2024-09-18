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
                BankAccountDAO bankAccountDAO = new BankAccountDAO();
                bankAccountDAO.createTable();

                bankAccountDAO.close();
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
                            "VALUES (?, ?, ?)"
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

    public List<AccountDetails> getAll()
    {
        List<AccountDetails> accountDetails = new ArrayList<>();
        // Todo Later: Create a Statement to run the SELECT * query
        // and populate the accounts list above
        return accountDetails;
    }
    public AccountDetails getByUsername (String username)
    {
        return null;
    }

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