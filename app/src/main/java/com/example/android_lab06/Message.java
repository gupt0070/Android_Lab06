package com.example.android_lab06;

public class Message {
    public String message;
    public boolean isSend;
    public long messageID;

    public Message(String message, boolean isSend ) {
        this.message = message;
        this.isSend = isSend;
    }

    public Message() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }

    public long getMessageID() {
        return messageID;
    }

    public void setMessageID(long messageID) {
        this.messageID = messageID;
    }
}