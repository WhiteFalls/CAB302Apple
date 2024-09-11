package com.example.gardenplanner.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public interface IPerson {
        /**
         * Returns person's name
         * @return a string of the user's name
         */

        String getFirstName();
        void setFirstName(String firstName);

        String getLastName();
        void setLastName(String lastName);

        String getName();

        int getId();
        void setId(int userId);

        String getPassword();

        String getEmail();
        void setEmail(String email);

        ArrayList<Task> getTasks(); // tell darragh to chnage to array list
        void setTasks(ArrayList<Task> tasks);

        Task getTask(int id);

        void editTask(Task newTask, Task oldTask);

        void addTask(Task task);


        void removeTask(int id);

    }

