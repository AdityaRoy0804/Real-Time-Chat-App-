package model;

import java.io.Serializable;

public class FileMessage implements Serializable {
    private String sender;
    private String filename;
    private byte[] fileData;

    public FileMessage(String sender, String filename, byte[] fileData) {
        this.sender = sender;
        this.filename = filename;
        this.fileData = fileData;
    }

    public String getSender() {
        return sender;
    }

    public String getFilename() {
        return filename;
    }

    public byte[] getFileData() {
        return fileData;
    }
}
