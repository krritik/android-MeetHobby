package com.example.meethobby;

public class Group_product {
    int groupID;
    String name, description;

    public Group_product(int groupID, String name , String description) {
        this.name = name;
        this.description = description;
        this.groupID = groupID;
    }

    public String getName() { return name; }
    public String getDescription() { return  description; }
    public int getGroupID() { return  groupID; }
}
