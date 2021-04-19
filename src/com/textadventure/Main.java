package com.textadventure;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
    Room [] roomList = new Room[4];
    int flag;
    String roomName;
    int roomNumber;
    boolean loop = false;

    public static void main(String[] args) {
        Main main = new Main();
        main.start();
    }

    public void start() {
        readRoomsFile();
        readSaveFile();
        while (!loop) {
            initialParse();
        }
    }

    public void readRoomsFile() {
        try {
            FileReader roomFile = new FileReader("Rooms.txt");
            Scanner fileReader = new Scanner(roomFile);
            int i = 0;
            while (fileReader.hasNextLine()) {
                String readInput = fileReader.nextLine();
                String[] asList = readInput.split(",");
                int castNumber = Integer.parseInt(asList[1]);
                Room newRoom = new Room(asList[0], castNumber, asList[2], asList[3], asList[4], asList[5]);
                roomList[i] = newRoom;
                i++;
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Rooms file was not found");
        }
    }

    public void readSaveFile() {
        try {
            FileReader saveFile = new FileReader("Save.txt");
            Scanner fileReader = new Scanner(saveFile);
            String readInput = fileReader.nextLine();
            String[] asList = readInput.split(",");
            flag = Integer.parseInt(asList[0]);
            roomName = asList[1];
            roomNumber = Integer.parseInt(asList[2]);
        } catch (FileNotFoundException e) {
            System.out.println("saveFile was not found");
        }
    }

    public void initialParse () {
        System.out.println("You are in the " + roomList[roomNumber].getName());
        Scanner input = new Scanner(System.in);
        String unParsed = input.nextLine();
        String[] toParse = unParsed.split(" ");

        if (toParse[0].equalsIgnoreCase("go")) {
            navigation(toParse[1]);
        } else if (toParse[0].equalsIgnoreCase("where") && toParse[1].equalsIgnoreCase("can")
                && toParse[2].equalsIgnoreCase("i") && toParse[3].equalsIgnoreCase("go")) {
            viableDirections();
        } else if (toParse[0].equalsIgnoreCase("look")) {
            lookDirection(toParse[1]);
        } else if (toParse[0].equalsIgnoreCase("quit")) {
            System.out.println("Thank you for playing");
            loop = true;
        } else {
            System.out.println("I don't recognise \"" + unParsed + "\"\n");
        }
    }

    public void navigation(String direction) {
        if (direction.equalsIgnoreCase("north") && !roomList[roomNumber].getNorth().equals("null")) {
            roomName = roomList[roomNumber].getNorth();
        } else if (direction.equalsIgnoreCase("east") && !roomList[roomNumber].getEast().equals("null")) {
            roomName = roomList[roomNumber].getEast();
        } else if (direction.equalsIgnoreCase("south") && !roomList[roomNumber].getSouth().equals("null")) {
            roomName = roomList[roomNumber].getSouth();
        } else if (direction.equalsIgnoreCase("west") && !roomList[roomNumber].getWest().equals("null")) {
            roomName = roomList[roomNumber].getWest();
        } else {
            System.out.println("Can't go that way");
        }
        for (int i = 0; i < roomList.length; i++) {
            if (roomName.equals(roomList[i].getName())) {
                roomNumber = roomList[i].getNumber();
            }
        }
    }

    public void viableDirections() {
        if (!roomList[roomNumber].getNorth().equals("null")) {
            System.out.println("The " + roomList[roomNumber].getNorth() + " is to the north");
        }
        if (!roomList[roomNumber].getEast().equals("null")) {
            System.out.println("The " + roomList[roomNumber].getEast() + " is to the east");
        }
        if (!roomList[roomNumber].getSouth().equals("null")) {
            System.out.println("The " + roomList[roomNumber].getSouth() + " is to the south");
        }
        if (!roomList[roomNumber].getWest().equals("null")) {
            System.out.println("The " + roomList[roomNumber].getWest() + " is to the west");
        }
        System.out.println();
    }

    public void lookDirection(String direction) {
        if (direction.equalsIgnoreCase("north") && !roomList[roomNumber].getNorth().equals("null")) {
            System.out.println("The " + roomList[roomNumber].getNorth() + " is north\n");
        } else if (direction.equalsIgnoreCase("east") && !roomList[roomNumber].getEast().equals("null")) {
            System.out.println("The " + roomList[roomNumber].getEast() + " is east\n");
        } else if (direction.equalsIgnoreCase("south") && !roomList[roomNumber].getSouth().equals("null")) {
            System.out.println("The " + roomList[roomNumber].getSouth() + " is south\n");
        } else if (direction.equalsIgnoreCase("west") && !roomList[roomNumber].getWest().equals("null")) {
            System.out.println("The " + roomList[roomNumber].getWest() + " is west\n");
        } else {
            System.out.println("Can't go that way");
        }
    }

}