package com.textadventure;

public class Room {

    private String name;
    private Integer number;
    private String north;
    private String south;
    private String west;
    private String east;

    public Room(String name,Integer number, String north,  String east, String south, String west) {
        this.setName(name);
        this.setNumber(number);
        this.setNorth(north);
        this.setSouth(south);
        this.setWest(west);
        this.setEast(east);
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
}