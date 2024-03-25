package com.example.votingappproject.Model;

import java.util.ArrayList;

public class Topic
{
    String topicID;
    String description;
    ArrayList<Choice> choicesList = new ArrayList<>();

    public Topic()
    {
        this.topicID = "";
        this.description = "";
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

    @Override
    public String toString()
    {
        return description;
    }
}
