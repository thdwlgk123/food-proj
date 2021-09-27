package com.study.android.a4thteamproject01.manager;

public class ChatDTO {
    private String userName;
    private String message;
    private Object timestamp;


    public ChatDTO(){}

    public ChatDTO(String userName, String message, Object timestamp) {
        this.userName = userName;
        this.message = message;
        this.timestamp=timestamp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }
}
