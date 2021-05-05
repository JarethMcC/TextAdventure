package com.textadventure;

public class Room {

    private String name;
    private Integer number;
    private String north;
    private String east;
    private String south;
    private String west;
    private String description;
    private String initialDescription;
    private Integer initialVisit;

    public Room(String name,Integer number, String north,  String east, String south, String west, String description, String initialDescription, Integer initialVisit) {
        this.setName(name);
        this.setNumber(number);
        this.setNorth(north);
        this.setSouth(south);
        this.setWest(west);
        this.setEast(east);
        this.setDescription(description);
        this.setInitialDescription(initialDescription);
        this.setInitialVisit(initialVisit);
    }
    // Start of setters
    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setNorth(String north) {
        this.north = north;
    }

    public void setSouth(String south) {
        this.south = south;
    }

    public void setWest(String west) {
        this.west = west;
    }

    public void setEast(String east) {
        this.east = east;
    }

    public void setDescription(String description) { this.description = description; }

    public void setInitialDescription(String initialDescription) { this.initialDescription = initialDescription; }

    public void setInitialVisit(Integer initialVisit) { this.initialVisit = initialVisit; }


    // Start of getters
    public String getName() {
        return name;
    }

    public Integer getNumber() {
        return number;
    }

    public String getNorth() {
        return north;
    }

    public String getSouth() {
        return south;
    }

    public String getWest() {
        return west;
    }

    public String getEast() {
        return east;
    }

    public String getDescription() { return description; }

    public String getInitialDescription() {
        return initialDescription;
    }

    public Integer getInitialVisit() {
        return initialVisit;
    }
}