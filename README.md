# 🗨️ ChatApp - Java Socket Based Chat Application

A simple client-server chat application built using Java sockets. This app supports multiple client connections, user authentication (login/register), and message/file broadcasting among connected clients.

---

## 🚀 Features

- User Registration and Login
- Real-time messaging between clients
- File sharing capability
- Multi-threaded server to support concurrent clients
- GUI-based client interface (if implemented)
- Database integration for user credentials (via JDBC)

---

## 🛠️ Technologies Used

- Java SE (Socket Programming, Threads, I/O)
- JDBC (for database access)
- SQLite / MySQL (backend database)
- Java Swing (for client GUI, if present)
- VS Code / IntelliJ / Terminal

---

## 📁 Project Structure

ChatApp/

├── src/

│ ├── client/ # Client-side code (ChatClient.java)

│ ├── server/ # Server-side code (ServerMain.java, ClientHandler.java)

│ ├── model/ # Message model class

│ └── database/ # DBManager class (DB connection)

├── out/ # Compiled classes

├── .vscode/ # VS Code launch config (optional)

└── README.md # You're reading it!



---

## 🧰 Prerequisites

- Java JDK 11 or higher
- A code editor (VS Code recommended)
- SQLite or MySQL driver in your classpath (if using DB)
- Git (optional)

---

## ⚙️ Setup Instructions

### 1. **Clone the repository**

git clone https://github.com/yourusername/ChatApp.git

cd ChatApp

### 2. Compile the project

javac -d out src/client/*.java src/server/*.java src/model/*.java src/database/*.java

### 3. Run the Server

java -cp out server.ServerMain

Or run the ServerMain.java file from your IDE.

### 4. Run the Client

java -cp out client.ChatClient

Or you can simply run the ChatClient.java file from your IDE.

You can run multiple clients in separate terminals or machines pointing to the server IP.

---

## 🌐 Multi-Machine Setup
Ensure all machines are on the same network.

On the client side, update the server IP in ChatClient.java:

Socket socket = new Socket("YOUR_SERVER_IP", 3698);

Build and run on both machines as per the steps above.

### 🧪 Example Credentials
Register a user via the client:

Username: testuser

Password: 123456

---

## 🧾 License
This project is open-source and available under the MIT License.

---

## 🙋‍♂️ Author
### Aditya Kumar Roy
#### Gmail : roy97278@gmail.com
#### LinkedIN : https://www.linkedin.com/in/aditya-kumar-roy-257a1428a/

