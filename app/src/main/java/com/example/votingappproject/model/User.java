package com.example.votingappproject.model;

import androidx.annotation.NonNull;

public class User
{
    public String username;
    public String password;
    public User()
    {
        this.username = "";
        this.password = "";
    }

    public User(String username, String password)
    {
        this.username= username;
        this.password = password;
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
    @Override
    public String toString()
    {
        return username;
    }
}



