package com.tolean.notification;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;

public final class Notification {

    private String id;
    private String code;
    private String message;
    private String type;
    private Map<String, Object> dataMap = new HashMap<>();
    private Map<String, FieldNotification> fieldMap = new HashMap<>();

    public Notification info(String id, String message) {
        return notification(id, message, Type.INFO);
    }

    public Notification warn(String id, String message) {
        return notification(id, message, Type.WARN);
    }

    public Notification error(String id, String message) {
        return notification(id, message, Type.ERROR);
    }

    public Notification append(String key, Object data) {
        if (dataMap.containsKey(key)) {
            throw new IllegalArgumentException("Klucz " + key + " został już dodany.");
        }

        if (data == null) {
            throw new IllegalArgumentException("Nie można przekazać argumentu null.");
        }

        dataMap.put(key, data);

        return this;
    }

    public Notification field(String name, String message, Type type) {
        assert name != null && !name.isEmpty();
        assert message != null && !message.isEmpty();

        if (fieldMap.containsKey(name)) {
            throw new IllegalArgumentException("Pole " + name + " było już dodane.");
        } else {
            FieldNotification fieldNotification;

            if (type == null) {
                fieldNotification = new FieldNotification(name, message);
            } else {
                fieldNotification = new FieldNotification(name, message, type);
            }

            fieldMap.put(fieldNotification.getName(), fieldNotification);
        }

        return this;
    }

    private Notification notification(String id, String message, Type type) {
        assert id != null && !id.isEmpty();
        assert message != null && !message.isEmpty();

        this.id = id;
        this.message = message;
        this.type = type.name().toLowerCase();

        return this;
    }

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public Map<String, Object> getDataMap() {
        return unmodifiableMap(dataMap);
    }

    public Map<String, FieldNotification> getFieldMap() {
        return unmodifiableMap(fieldMap);
    }

    enum Type {
        INFO, WARN, ERROR;
    }

}
