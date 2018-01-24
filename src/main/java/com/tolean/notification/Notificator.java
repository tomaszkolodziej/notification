package com.tolean.notification;

import java.util.Collection;

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

    public Notificator append(Collection<Object> objectCollection) {
        notification.append(objectCollection);
        return this;
    }

    public Notificator append(Object... dataList) {
        notification.append(dataList);
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

    public Notificator fieldError(String name, String message) {
        notification.field(name, message, Notification.Type.ERROR);
        return this;
    }

    public static String fieldRequired() {
        return "Pole wymagane";
    }

    public static String fieldMaxLength(int maxLength) {
        return "Maks. długość wynosi " + maxLength;
    }

    public Notification build() {
        return notification;
    }

}
