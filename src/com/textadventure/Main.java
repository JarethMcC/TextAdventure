package com.textadventure;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    Room[] roomList = new Room[4];
    StaticItems[] staticItemList = new StaticItems[4];
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
        mainMenu();
        while (!loop) {
            initialParse();
        }
    }

    public void mainMenu() {
        while (!loop) {
            Scanner userInput = new Scanner(System.in);
            System.out.println("Text adventure\n");
            System.out.println("1: New game");
            System.out.println("2: Load game");
            System.out.println("3: Exit\n");

            String menuSelect = userInput.nextLine();
            if (menuSelect.equals("1")) {
                readRooms(0);
                readSave(0);
                readStaticItems(0);
                return;
            } else if (menuSelect.equals("2")) {
                System.out.println("\nSelect a save file\n");
                printSaveNames();
                System.out.println("4: Delete a save\n");

                System.out.println("If you do not select a file and press enter then you will return to the menu\n");
                Scanner whichSave = new Scanner(System.in);
                String saveSelect = whichSave.nextLine();
                if (saveSelect.equals("1")) {
                    readSave(1);
                    readStaticItems(1);
                    readRooms(1);
                    return;
                } else if (saveSelect.equals("2")) {
                    readSave(2);
                    readStaticItems(2);
                    readRooms(2);
                    return;
                } else if (saveSelect.equals("3")) {
                    readSave(3);
                    readStaticItems(3);
                    readRooms(3);
                    return;
                } else if (saveSelect.equals("4")) {
                    System.out.println("Which save would you like to delete?\n");
                } else {
                    System.out.println(saveSelect + " is not a valid option\n");
                }
            } else if (menuSelect.equals("3")) {
                System.out.println("Thank you for playing");
                loop = true;
            } else {
                System.out.println(menuSelect + " is not a valid option\n");
            }
        }
    }

    public void readRooms(int menuOption) {
        try {
            FileReader roomFile = new FileReader("Saves/NewRooms.txt");
            if(menuOption == 1) {
                roomFile = new FileReader("Saves/Rooms1.txt");
            } else if (menuOption == 2) {
                roomFile = new FileReader("Saves/Rooms2.txt");
            } else if (menuOption == 3) {
                roomFile = new FileReader("Saves/Rooms3.txt");
            }
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

    public void readStaticItems(int menuOption) {
        try {
            FileReader staticItemsFile = new FileReader("Saves/NewStaticItems.txt");
            if(menuOption == 1) {
                staticItemsFile = new FileReader("Saves/StaticItems1.txt");
            } else if (menuOption == 2) {
                staticItemsFile = new FileReader("Saves/StaticItems2.txt");
            } else if (menuOption == 3) {
                staticItemsFile = new FileReader("Saves/StaticItems3.txt");
            }
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

    public void readSave(int menuOption) {
        try {
            FileReader readSave = new FileReader("Saves/NewSave.txt");
            if(menuOption == 1) {
                readSave = new FileReader("Saves/Save1.txt");
            } else if (menuOption == 2) {
                readSave = new FileReader("Saves/Save2.txt");
            } else if (menuOption == 3) {
                readSave = new FileReader("Saves/Save3.txt");
            }
            Scanner fileReader = new Scanner(readSave);
            String readInput = fileReader.nextLine();
            String[] asList = readInput.split(",");
            flag = Integer.parseInt(asList[1]);
            roomName = asList[2];
            roomNumber = Integer.parseInt(asList[3]);
            fileReader.close();
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

    public void printSaveNames() {
        try {
            FileReader readSave = new FileReader("Saves/Save1.txt");
            Scanner fileReader = new Scanner(readSave);
            String readInput = fileReader.nextLine();
            String[] asList = readInput.split(",");
            System.out.println("1: " + asList[0]);

            readSave = new FileReader("Saves/Save2.txt");
            fileReader = new Scanner(readSave);
            readInput = fileReader.nextLine();
            asList = readInput.split(",");
            System.out.println("2: " + asList[0]);

            readSave = new FileReader("Saves/Save3.txt");
            fileReader = new Scanner(readSave);
            readInput = fileReader.nextLine();
            asList = readInput.split(",");
            System.out.println("3: " + asList[0]);
            fileReader.close();
        } catch (Exception e) {
            System.out.println("A save file was not found");
        }
    }

    public void saveGame() {
        try {
            System.out.println("Which Save file would you like to save over?");
            printSaveNames();
            FileWriter saveFile = new FileWriter("Saves/Save1.txt");
            FileWriter roomsFile = new FileWriter("Saves/Rooms1.txt");
            FileWriter staticItemsFile = new FileWriter("Saves/StaticItems1.txt");
            Scanner userInput = new Scanner(System.in);
            String whichSaveFile = userInput.nextLine();
            if (whichSaveFile.equals("1")) {
                saveFile = new FileWriter("Saves/Save1.txt");
                roomsFile = new FileWriter("Saves/Rooms1.txt");
                staticItemsFile = new FileWriter("Saves/StaticItems1.txt");
            } else if (whichSaveFile.equals("2")) {
                saveFile = new FileWriter("Saves/Save2.txt");
                roomsFile = new FileWriter("Saves/Rooms2.txt");
                staticItemsFile = new FileWriter("Saves/StaticItems2.txt");
            } else if (whichSaveFile.equals("3")) {
                saveFile = new FileWriter("Saves/Save3.txt");
                roomsFile = new FileWriter("Saves/Rooms3.txt");
                staticItemsFile = new FileWriter("Saves/StaticItems.txt");
            } else {
                System.out.println(whichSaveFile + "Is not a valid save file");
            }
            System.out.println("What would you like to name your save?: ");
            String newSaveName = userInput.nextLine();
            saveFile.write(newSaveName + "," + flag + "," + roomName + "," + roomNumber);
            for (int i = 0; i < roomList.length; i++) {
                roomsFile.write(roomList[i].getName() + "," + roomList[i].getNumber() + "," + roomList[i].getNorth()
                        + "," + roomList[i].getEast() + "," + roomList[i].getSouth() + "," + roomList[i].getWest()
                        + "," + roomList[i].getDescription() + "," + roomList[i].getInitialDescription() + ","
                        + roomList[i].getInitialVisit() + "\n");
            }
            for (int e = 0; e < roomList.length; e++) {
                staticItemsFile.write(staticItemList[e].getName() + "," + staticItemList[e].getRoomName() + ","
                                        + staticItemList[e].getDescription() + "\n");
            }
            saveFile.close();
            roomsFile.close();
            staticItemsFile.close();
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