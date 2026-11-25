package com.example;

import java.util.Scanner;

import com.example.network.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    private static boolean wannaExit = false;
    public static void main(String[] args) {

        while (!wannaExit) {
            showMenu();
        } 
    }

    private static void showMenu() {
        System.out.print(
            "====================" +
            "\n     MAIN MENU" +
            "\n====================" +
            "\n1. Singleplayer" +
            "\n2. Multiplayer" +
            "\n3. Exit" +
            "\n====================" +
            "\nYour option: "
        );
        int choice = scanner.nextInt(); scanner.nextLine();
        switch (choice) {
            case 1 -> showSinglePlayer();
            case 2 -> showMultiplayer();
            case 3 -> wannaExit = true;
            default -> {
                System.out.println("Entered option " + choice + " is not exist!");
            }
        }
    }

    private static void showSinglePlayer() {
        System.out.print(
            "====================" +
            "\n    SINGLEPLAYER" +
            "\n====================" +
            "\n1. Play game" +
            "\n" +
            "\n3. Exit" +
            "\n====================" +
            "\nYour option: "
        );
        int choice = scanner.nextInt(); scanner.nextLine();
        switch (choice) {
            case 1 -> {
                System.out.println("Enter number of bots you want against you: ");
                int botCount = scanner.nextInt();
                Server server = new Server();
                new Thread(() -> server.startServer(botCount + 1, botCount)).start();
                try {
                    Thread.sleep(200);
                    Client client = new Client("localhost", server.getPort(), scanner);
                    client.connect();
                }
                catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }

            case 3 -> {}
            default -> {
                System.out.println("Entered option " + choice + " is not exist!");
                showSinglePlayer();
            }
        }
    }
    
    private static void showMultiplayer() {
        System.out.print(
            "====================" +
            "\n    MULTIPLAYER" +
            "\n====================" +
            "\n1. Host a game" +
            "\n2. Join a game" +
            "\n3. Exit" +
            "\n====================" +
            "\nYour option: "
        );
        int choice = scanner.nextInt(); scanner.nextLine();
        switch (choice) {
            case 1 -> {
                System.out.print("Enter how many players you want to play with: ");
                int playersCount = scanner.nextInt();

                System.out.print("And quantity of bots among them: ");
                int botCount = scanner.nextInt(); scanner.nextLine();

                System.out.print("Enter port (left blank for default: 8998): ");
                String port = scanner.nextLine().trim();
                int PORT = 8998; 
                try {
                    PORT = !port.isEmpty() ? Integer.parseInt(port) : 8998;
                }
                catch (NumberFormatException exception) {
                    System.out.println("Invalid Port indicated. Changed to default!");
                }

                Server server = new Server(PORT);
                new Thread(() -> server.startServer(playersCount, botCount)).start();
                try {
                    Thread.sleep(200);
                    Client client = new Client("localhost", PORT, scanner);
                    client.connect();
                }
                catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
            case 2 -> {
                System.out.print("Enter ip-address: ");
                String ip = scanner.nextLine().trim();

                System.out.print("Enter port (left blank for default: 8998): ");
                String port = scanner.nextLine().trim();
                int PORT = 8998;
                try {
                    PORT = !port.isEmpty() ? Integer.parseInt(port) : 8998;
                }
                catch (NumberFormatException exception) {
                    System.out.println("Invalid Port indicated. Changed to default!");
                }
                Client client = new Client(ip, PORT, scanner);
                client.connect();
            }
            case 3 -> {}
            default -> {
                System.out.println("Entered option " + choice + " is not exist!");
                showMultiplayer();
            }
        }
    }
}
