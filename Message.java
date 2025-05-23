package model;

import java.io.Serializable;

public class Message implements Serializable {
    private String sender;
    private String type;       // "text" or "file"
    private String content;    // message or file content (Base64)
    private String filename;   // used if type == "file"

    public Message(String sender, String type, String content, String filename) {
        this.sender = sender;
        this.type = type;
        this.content = content;
        this.filename = filename;
    }

    public String getSender() {
        return sender;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public String getFilename() {
        return filename;
    }
}
