package com.textadventure;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    Room [] roomList = new Room[4];
    StaticItems [] staticItemList = new StaticItems[4];
    int flag;
    String roomName;
    int roomNumber;
    boolean loop = false;
    String[] lookAround = {"look around", "look at room"};
    String[] lookAt = {"look at", "examine"};
    String[] look = {"look"};
    String[] whereCanIGo = {"where can"};
    String[] help = {"help"};
    String[] quit = {"quit", "exit"};
    String[] navigate = {"go", "navigate", "travel", "walk"};
    String[] save = {"save"};

    public static void main(String[] args) {
        Main main = new Main();
        main.start();
    }

    public void start() {
        readRooms();
        readSave();
        readStaticItems();
        while (!loop) {
            initialParse();
        }
    }

    public void readRooms() {
        try {
            FileReader roomFile = new FileReader("Rooms.txt");
            Scanner fileReader = new Scanner(roomFile);
            int i = 0;
            while (fileReader.hasNextLine()) {
                String readInput = fileReader.nextLine();
                String[] asList = readInput.split(",");
                int castNumber = Integer.parseInt(asList[1]);
                int castInitialVisit = Integer.parseInt(asList[8]);
                Room newRoom = new Room(asList[0], castNumber, asList[2], asList[3], asList[4], asList[5], asList[6], asList[7], castInitialVisit);
                roomList[i] = newRoom;
                i++;
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Rooms.txt not found");
        }
    }

    public void readStaticItems() {
        try {
            FileReader staticItemsFile = new FileReader("StaticItems.txt");
            Scanner fileReader = new Scanner(staticItemsFile);
            int i = 0;
            while (fileReader.hasNextLine()) {
                String readInput = fileReader.nextLine();
                String[] asList = readInput.split(",");
                StaticItems newStaticItem = new StaticItems(asList[0], asList[1], asList[2]);
                staticItemList[i] = newStaticItem;
                i++;
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("StaticItems.txt not found");
        }
    }

    public void readSave() {
        try {
            FileReader readSave = new FileReader("Save.txt");
            Scanner fileReader = new Scanner(readSave);
            String readInput = fileReader.nextLine();
            String[] asList = readInput.split(",");
            flag = Integer.parseInt(asList[0]);
            roomName = asList[1];
            roomNumber = Integer.parseInt(asList[2]);
        } catch (FileNotFoundException e) {
            System.out.println("Save.txt not found");
        }
    }

    public void initialParse () {
        System.out.println("You are in the " + roomList[roomNumber].getName() + "\n");
        Scanner input = new Scanner(System.in);
        String unParsed = input.nextLine();
        String[] toParse = unParsed.split(" ");

        for (int i = 0; i < lookAround.length; i++) {
            if (unParsed.toLowerCase().contains(lookAround[i])) {
                printRoomDescription();
                printStaticItemCurrentRoom();
                return;
            }
        }
        for (int i = 0; i < lookAt.length; i++) {
            if (unParsed.toLowerCase().contains(lookAt[i])) {
                printStaticItemDescription(toParse[toParse.length - 1]);
                return;
            }
        }
        for (int i = 0; i < look.length; i++) {
            if (unParsed.toLowerCase().contains(look[i])) {
                lookDirection(toParse[toParse.length - 1]);
                return;
            }
        }
        for (int i = 0; i < whereCanIGo.length; i++) {
            if (unParsed.toLowerCase().contains(whereCanIGo[i])) {
                viableDirections();
                return;
            }
        }
        for (int i = 0; i < navigate.length; i++) {
            if (unParsed.toLowerCase().contains(navigate[i])) {
                int directionStringPosition = toParse.length;
                navigation(toParse[directionStringPosition - 1]);
                return;
            }
        }
        for (int i = 0; i < help.length; i++) {
            if (unParsed.toLowerCase().contains(help[i])) {
                help();
                return;
            }
        }
        for (int i = 0; i < quit.length; i++) {
            if (unParsed.toLowerCase().contains(quit[i])) {
                System.out.println("Are you sure you would like to quit? (Y/N): ");
                String yOrN = input.nextLine();
                if (yOrN.equalsIgnoreCase("y")) {
                    System.out.println("Thank you for playing");
                    loop = true;
                    return;
                }
            }
        }
        for (int i = 0; i < save.length; i++) {
            if (unParsed.toLowerCase().contains(save[i])) {
                saveGame();
                return;
            }
            System.out.println("I don't recognise \"" + unParsed + "\"");
            System.out.println("Type \"Help\" if you would like to see a list of useful commands\n");
        }
    }

    public void help() {
        System.out.println("\"Where can I go\" \t\t to see a lift of directions you can go");
        System.out.println("\"Go <direction>\" \t\t to go in a direction");
        System.out.println("\"Look around\" \t\t\t to get a description of the room you are in and a list of items");
        System.out.println("\"Save\" \t\t\t\t\t to save the game");
        System.out.println("\"Quit\" \t\t\t\t\t to quit the game\n");
    }

    public void printRoomDescription() {
            System.out.println(roomList[roomNumber].getInitialDescription());
    }

    public void printStaticItemDescription(String staticItem) {
        for (int i = 0; i < staticItemList.length; i++) {
            if (roomName.equalsIgnoreCase(staticItemList[i].getRoomName()) && staticItemList[i].getName().equalsIgnoreCase(staticItem)) {
                System.out.println(staticItemList[i].getDescription());
            }
        }
    }


    public void printStaticItemCurrentRoom() {
        int foundItem = 0;
        for (int i = 0; i < staticItemList.length; i++){
            if (roomName.equals(staticItemList[i].getRoomName()) && foundItem == 0) {
                System.out.println("You look around and see:\n" + staticItemList[i].getName());
                foundItem++;
            } else if (roomName.equals(staticItemList[i].getRoomName()) && foundItem > 0) {
                System.out.println(staticItemList[i].getName());
            } else if (i == staticItemList.length){
                System.out.println("You see nothing");
            }
        }
        System.out.println();
    }

    public void saveGame() {
        try {
            FileWriter saveFile = new FileWriter("Save.txt");
            saveFile.write(flag + "," + roomName + "," + roomNumber);
            saveFile.close();
            System.out.println("Game has been saved\n");
        } catch (IOException e) {
            System.out.println("Save file was not found");
        }
    }

    public void navigation(String direction) {
        if (direction.equalsIgnoreCase("north") && !roomList[roomNumber].getNorth().equals("null")) {
            roomName = roomList[roomNumber].getNorth();
            System.out.println("You head North");
        } else if (direction.equalsIgnoreCase("east") && !roomList[roomNumber].getEast().equals("null")) {
            roomName = roomList[roomNumber].getEast();
            System.out.println("You head East");
        } else if (direction.equalsIgnoreCase("south") && !roomList[roomNumber].getSouth().equals("null")) {
            roomName = roomList[roomNumber].getSouth();
            System.out.println("You head South");
        } else if (direction.equalsIgnoreCase("west") && !roomList[roomNumber].getWest().equals("null")) {
            roomName = roomList[roomNumber].getWest();
            System.out.println("You head West");
        } else {
            System.out.println("Can't go that way");
        }
        for (int i = 0; i < roomList.length; i++) {
            if (roomName.equals(roomList[i].getName())) {
                roomNumber = roomList[i].getNumber();
            }
        }
        if (roomList[roomNumber].getInitialVisit() == 0) {
            System.out.println(roomList[roomNumber].getInitialDescription());
            roomList[roomNumber].setInitialVisit(1);
        } else {
            System.out.println(roomList[roomNumber].getDescription());
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
            System.out.println("There is nothing to the " + direction);
        }
    }
}