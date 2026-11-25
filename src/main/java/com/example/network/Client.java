package com.example.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    private final String IP;
    private final int PORT;
    private final Scanner scanner;

    public Client(String IP, int PORT, Scanner scanner) {
        this.IP = IP;
        this.PORT = PORT;
        this.scanner = scanner;
    }

    public void connect() {
        try (
            Socket socket = new Socket(IP, PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
        ) {
            String serverMessage;
            while ((serverMessage = in.readLine()) != null) {
                if (serverMessage.startsWith("MSG:")) {
                    System.out.println(serverMessage.substring(4));
                }
                else if (serverMessage.startsWith("ASK:")){
                    System.out.print(serverMessage.substring(4));
                    String input = scanner.nextLine();
                    out.println(input);
                }
                else {
                    System.out.println(serverMessage);
                }
            }        
        } 
        catch (IOException exception) {
            System.err.println("Connection error: " + exception.getMessage());
        }
    }
}
