package com.tolean.notification;

public final class FieldNotification {

    private String name;
    private String message;
    private String type;

    private FieldNotification() {
        // empty constructor
    }

    public FieldNotification(String name, String message) {
        assert name != null && !name.isEmpty();
        assert message != null && !message.isEmpty();

        this.name = name;
        this.message = message;
    }

    public FieldNotification(String name, String message, Notification.Type type) {
        this(name, message);

        assert type != null;

        this.type = type.name().toLowerCase();
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }
}
