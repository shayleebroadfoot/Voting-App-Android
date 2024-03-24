package com.example.votingappproject.Model;
public class Task {
    String taskID;
    String description;

    int count;
    //default constructor
    public Task(){
    }
    public Task(int count, String taskID, String description)
    {
        this.count = count;
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
    public int getCount(){
        return count;
    }
    public void setCount(int count){
        this.count = count;
    }

    private String getBlankSpaces(int count) {
        StringBuilder spaces = new StringBuilder();
        for (int i = 0; i < count; i++) {
            spaces.append(" "); // Add blank spaces
        }
        return spaces.toString();
    }
    @Override
    public String toString() {
        // Assuming you want count to always appear at cell position 20
        String paddedCount = getBlankSpaces(50 - description.length()); // Get blank spaces for padding

        return description + paddedCount + count;
    }
//    public String toString() {
//        return "task" + taskID + " " + description;
//    public String toString() {
//        return description + (20 - description.length()) +count;
//    }
}
