package com.example.votingappproject.Model;


import java.util.HashMap;
import java.util.Map;

public class User
{
    private String username;
    private String password;
    private Map<String, Boolean> selectedTopicsIds;
    public User()
    {
        this.selectedTopicsIds = new HashMap<>();
    }
    public User(String username, String password)
    {
        this.username= username;
        this.password = password;
    }

    public User(String username, String password, Map<String, Boolean> selectedTopicsIds)
    {
        this.username= username;
        this.password = password;
        this.selectedTopicsIds = selectedTopicsIds;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setSelectedTopicsIds(Map<String, Boolean> selectedTopicsIds)
    {
        this.selectedTopicsIds = selectedTopicsIds;
    }
    public Map<String, Boolean> getSelectedTopicsIds()
    {
        return selectedTopicsIds;
    }

    @Override
    public String toString()
    {
        return username;
    }
}
//package com.example.votingappproject.Model;
//
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class User
//{
//    private String username;
//    private String password;
//    private Map<String, String> selectedTopicAndChoice;
//    public User()
//    {
//        this.selectedTopicAndChoice = new HashMap<>();
//    }
//    public User(String username, String password)
//    {
//        this.username= username;
//        this.password = password;
//        this.selectedTopicAndChoice = new HashMap<>();
//    }
//
//    public User(String username, String password, Map<String, String> selectedTopicAndChoice)
//    {
//        this.username= username;
//        this.password = password;
//        this.selectedTopicAndChoice = selectedTopicAndChoice;
//    }
//
//    public String getUsername()
//    {
//        return username;
//    }
//
//    public void setUsername(String username)
//    {
//        this.username = username;
//    }
//
//    public String getPassword()
//    {
//        return password;
//    }
//
//    public void setPassword(String password)
//    {
//        this.password = password;
//    }
//
//    public void setselectedTopicAndChoice(Map<String, String> selectedTopicAndChoice)
//    {
//        this.selectedTopicAndChoice = selectedTopicAndChoice;
//    }
//    public Map<String, String> getselectedTopicAndChoice()
//    {
//        return selectedTopicAndChoice;
//    }
//
//    @Override
//    public String toString()
//    {
//        return username;
//    }
//}