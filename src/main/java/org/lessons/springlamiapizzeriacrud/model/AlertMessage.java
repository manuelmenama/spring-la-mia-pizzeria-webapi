package org.lessons.springlamiapizzeriacrud.model;

public class AlertMessage {

    public enum AlertMessageType {
        SUCCESS, ERROR
    }

    private AlertMessageType type;
    private String message;

    public AlertMessage() {
    }

    public AlertMessage(AlertMessageType type, String message) {
        this.type = type;
        this.message = message;
    }

    public AlertMessageType getType() {
        return type;
    }

    public void setType(AlertMessageType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "AlertMessage{" +
                "type=" + type +
                ", message='" + message + '\'' +
                '}';
    }
}
