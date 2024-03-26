package com.example.votingappproject.Model;
public class Choice
{
    String choiceID;
    String description;
    int count;

    public Choice()
    {
    }
    public Choice(int count, String choiceID, String description)
    {
        this.count = count;
        this.choiceID = choiceID;
        this.description = description;
    }
    public String getChoiceID() {
        return choiceID;
    }
    public void setChoiceID(String choiceID) {
        this.choiceID = choiceID;
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

    private String getBlankSpaces(int pixels)
    {
        StringBuilder spaces = new StringBuilder();
        for (int i = 0; i < pixels; i++)
        {
            spaces.append(" "); // Add blank spaces
        }
        return spaces.toString();
    }

    @Override
    public String toString()
    {
        int fontSize = 10; // Font size of the text
        int descriptionWidth = estimateDescriptionWidth(fontSize);
        int countXPosition = descriptionWidth + 100; // Move count by 100 pixels from the end of description
        String paddedCount = getBlankSpaces(countXPosition); // Get blank spaces for padding

        return description + paddedCount + count;
    }
}
