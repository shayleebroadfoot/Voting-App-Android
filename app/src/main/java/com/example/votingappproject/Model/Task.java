package com.example.votingappproject.Model;
public class Task {
    String taskID;
    String description;
    //default constructor
    public Task(){
    }
    public Task(String taskID, String description)
    {
        this.taskID = taskID;
        this.description = description;
    }
    public String getTaskID() {
        return taskID;
    }
    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    @Override
    public String toString() {
        return "task" + taskID + " " + description;
    }
}
