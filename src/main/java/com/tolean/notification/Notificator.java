package com.tolean.notification;

import java.util.Collection;

import static java.util.Arrays.asList;

public class Notificator {

    private Notification notification;

    public Notificator() {
        notification = new Notification();
    }

    public Notificator code(String code) {
        assert code != null && !code.isEmpty();

        notification.setCode(code);

        return this;
    }

    public Notificator infoMessage(String id, String message) {
        notification.info(id, message);
        return this;
    }

    public Notificator warnMessage(String id, String message) {
        notification.warn(id, message);
        return this;
    }

    public Notificator errorMessage(String id, String message) {
        notification.error(id, message);
        return this;
    }

    public Notificator append(String key, Object... objects) {
        if (objects != null && objects.length > 1) {
            notification.append(key, asList(objects));
        } else {
            notification.append(key, objects[0]);
        }
        return this;
    }

    public Notificator append(String key, Collection<Object> objectCollection) {
        notification.append(key, objectCollection);
        return this;
    }

    public Notificator fieldInfo(String name, String message) {
        notification.field(name, message, Notification.Type.INFO);
        return this;
    }

    public Notificator fieldWarn(String name, String message) {
        notification.field(name, message, Notification.Type.WARN);
        return this;
    }

    public Notificator fieldRequired(String name) {
        return fieldError(name, "Pole wymagane");
    }

    public Notificator fieldMaxLength(String name, int maxLength) {
        return fieldError(name, "Maks. ilość znaków wynosi " + maxLength);
    }

    public Notificator fieldError(String name, String message) {
        notification.field(name, message, Notification.Type.ERROR);
        return this;
    }

    public boolean hasFields() {
        return !notification.getFieldMap().isEmpty();
    }

    public Notification build() {
        return notification;
    }

}
