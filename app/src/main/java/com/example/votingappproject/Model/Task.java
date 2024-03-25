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

    private int estimateDescriptionWidth(int fontSize) {
        // Assuming average width of a character for the given font size
        int averageCharacterWidth = fontSize / 2; // You can adjust this value as needed
        return description.length() * averageCharacterWidth;
    }
    @Override
    public String toString() {
        int fontSize = 10; // Font size of the text
        int descriptionWidth = estimateDescriptionWidth(fontSize);
        int countXPosition = descriptionWidth + 70; // Move count by 50 pixels from the end of description
        String paddedCount = getBlankSpaces(countXPosition); // Get blank spaces for padding

        return description + paddedCount + count;
    }

    private String getBlankSpaces(int pixels) {
        StringBuilder spaces = new StringBuilder();
        for (int i = 0; i < pixels; i++) {
            spaces.append(" "); // Add blank spaces
        }
        return spaces.toString();
    }
}
