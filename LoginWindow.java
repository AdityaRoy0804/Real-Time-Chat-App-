package client;

import javax.swing.*;
import java.awt.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class LoginWindow extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public LoginWindow() {
        setTitle("Login / Register");
        setSize(300, 180);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 5, 5));

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        add(loginButton);
        add(registerButton);

        loginButton.addActionListener(e -> login());
        registerButton.addActionListener(e -> register());

        setVisible(true);

        try {
            socket = new Socket("localhost", 3698);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            new Thread(this::listenForServerMessages).start();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Unable to connect to server");
            System.exit(0);
        }
    }

    private void login() {
        try {
            out.writeObject("LOGIN");
            out.writeObject(usernameField.getText().trim());
            out.writeObject(new String(passwordField.getPassword()));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void register() {
        try {
            out.writeObject("REGISTER");
            out.writeObject(usernameField.getText().trim());
            out.writeObject(new String(passwordField.getPassword()));
            out.flush();  // <-- add parentheses here to call the method
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void listenForServerMessages() {
        try {
            Object response;
            while ((response = in.readObject()) != null) {
                if ("LOGIN_SUCCESS".equals(response)) {
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(this, "Login Successful!");
                        new ChatWindow(socket, out, in, usernameField.getText().trim());
                        this.dispose();
                    });
                    break;
                } else if ("LOGIN_FAIL".equals(response)) {
                    JOptionPane.showMessageDialog(this, "Login Failed. Try again.");
                } else if ("REGISTER_SUCCESS".equals(response)) {
                    JOptionPane.showMessageDialog(this, "Registration Successful! Now login.");
                } else if ("REGISTER_FAIL".equals(response)) {
                    JOptionPane.showMessageDialog(this, "Registration Failed. Username may exist.");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Server disconnected.");
            System.exit(0);
        }
    }
}

