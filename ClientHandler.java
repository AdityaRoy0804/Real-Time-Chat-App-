package server;

import model.Message;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String username;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            while (true) {
                Object obj = in.readObject();

                if (obj instanceof String command) {
                    if ("LOGIN".equals(command)) {
                        String user = (String) in.readObject();
                        String pass = (String) in.readObject();

                        boolean success = UserManager.login(user, pass);
                        if (success) {
                            username = user;
                            out.writeObject("LOGIN_SUCCESS");
                            out.flush();
                            System.out.println(user + " logged in successfully");
                        } else {
                            out.writeObject("LOGIN_FAIL");
                            out.flush();
                            System.out.println("Login failed for " + user);
                        }
                    } else if ("REGISTER".equals(command)) {
                        String user = (String) in.readObject();
                        String pass = (String) in.readObject();

                        boolean success = UserManager.register(user, pass);
                        if (success) {
                            out.writeObject("REGISTER_SUCCESS");
                            out.flush();
                            System.out.println(user + " registered successfully");
                        } else {
                            out.writeObject("REGISTER_FAIL");
                            out.flush();
                            System.out.println("Registration failed for " + user);
                        }
                    }
                } else if (obj instanceof Message message) {
                    if (username == null) {
                        username = message.getSender();
                    }
                    System.out.println("Received from " + username + ": " + message.getType());
                    ServerMain.broadcast(message, this); // exclude sender from broadcast
                }
            }
        } catch (IOException e) {
            System.out.println("Client disconnected: " + username);
        } catch (ClassNotFoundException e) {
            System.out.println("Received unknown object type from client: " + username);
            e.printStackTrace();
        } finally {
            ServerMain.removeClient(this);
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(Object msg) {
        try {
            out.writeObject(msg);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
