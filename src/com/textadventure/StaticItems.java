package com.textadventure;

public class StaticItems {

    private String name;
    private String roomName;
    private String description;

    public StaticItems(String name, String roomName, String description) {
        this.setName(name);
        this.setRoomName(roomName);
        this.setDescription(description);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getDescription() {
        return description;
    }
}
