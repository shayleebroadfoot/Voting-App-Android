package com.example.votingappproject.Model;

import java.util.ArrayList;

public class Topic
{
    String topicID;
    String description;
    ArrayList<Choice> choicesList;

    int TopChoiceCount;
    Choice TopChoice;

    public Topic()
    {
        this.topicID = "";
        this.description = "";
        this.choicesList = new ArrayList<>();
    }

    public Topic(String topicID, String description, ArrayList<Choice> choicesList)
    {
        this.topicID = topicID;
        this.description = description;
        this.choicesList = choicesList;
    }

    public String getTopicID()
    {
        return topicID;
    }

    public void setTopicID(String topicID)
    {
        this.topicID = topicID;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public ArrayList<Choice> getChoicesList()
    {
        return choicesList;
    }

    public void setChoicesList(ArrayList<Choice> choicesList)
    {
        this.choicesList = choicesList;
    }

    public void setChoicesList1(ArrayList<Choice> choicesList, Choice topChoice, int topCount)
    {
        this.choicesList = choicesList;
        this.TopChoice = topChoice;
        this.TopChoiceCount = topCount;
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
    public String toString() {
        int fontSize = 10; // Font size of the text
        int descriptionWidth = estimateDescriptionWidth(fontSize);
        int countXPosition = descriptionWidth + 50; // Move count by 100 pixels from the end of description
        String paddedCount = getBlankSpaces(countXPosition); // Get blank spaces for padding

        // Check if TopChoice is null before attempting to access its description
        String topChoiceDescription = (TopChoice != null) ? TopChoice.description : "None";
        return description + paddedCount + "Top Choice: " + topChoiceDescription + "    " + TopChoiceCount;
    }

}
