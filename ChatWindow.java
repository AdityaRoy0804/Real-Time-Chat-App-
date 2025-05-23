package client;

import model.Message;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.Base64;

public class ChatWindow extends JFrame {
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton, fileButton;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String username;

    public ChatWindow(Socket socket, ObjectOutputStream out, ObjectInputStream in, String username) {
        this.out = out;
        this.in = in;
        this.username = username;

        setTitle("Chat - " + username);
        setSize(600, 400);
        setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        inputField = new JTextField();
        sendButton = new JButton("Send");
        fileButton = new JButton("Send File");

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(fileButton, BorderLayout.WEST);
        panel.add(inputField, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.EAST);

        add(new JScrollPane(chatArea), BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        sendButton.addActionListener(e -> sendMessage());
        fileButton.addActionListener(e -> sendFile());

        inputField.addActionListener(e -> sendMessage());

        new Thread(this::listenForMessages).start();

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void listenForMessages() {
        try {
            Object obj;
            while ((obj = in.readObject()) != null) {
                if (obj instanceof Message message) {
                    if (message.getType().equals("text")) {
                        chatArea.append(message.getSender() + ": " + message.getContent() + "\n");
                    } else if (message.getType().equals("file")) {
                        // Save the file
                        byte[] fileBytes = Base64.getDecoder().decode(message.getContent());
                        File file = new File("received_" + message.getFilename());
                        try (FileOutputStream fos = new FileOutputStream(file)) {
                            fos.write(fileBytes);
                        }
                        chatArea.append(message.getSender() + " sent a file: " + file.getName() + "\n");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        String text = inputField.getText().trim();
        if (!text.isEmpty()) {
            try {
                Message msg = new Message(username, "text", text, null);
                out.writeObject(msg);
                out.flush();
                chatArea.append("Me: " + text + "\n");
                inputField.setText("");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendFile() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try {
                byte[] fileBytes = new byte[(int) file.length()];
                try (FileInputStream fis = new FileInputStream(file)) {
                    fis.read(fileBytes);
                }
                String encoded = Base64.getEncoder().encodeToString(fileBytes);
                Message msg = new Message(username, "file", encoded, file.getName());
                out.writeObject(msg);
                out.flush();
                chatArea.append("Me: Sent file " + file.getName() + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
