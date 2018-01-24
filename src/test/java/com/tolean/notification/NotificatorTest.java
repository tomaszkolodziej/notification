package com.tolean.notification;

import org.junit.Test;

import java.util.Collection;
import java.util.HashSet;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NotificatorTest {

    @Test
    public void shouldCreateGlobalInfoMessage() {
        // when
        Notification notification = new Notificator().infoMessage("20180114000901", "infoMessage").build();
        // then
        assertEquals("20180114000901", notification.getId());
        assertEquals("infoMessage", notification.getMessage());
        assertEquals("info", notification.getType());
    }

    @Test
    public void shouldCreateGlobalWarnMessage() {
        // when
        Notification notification = new Notificator().warnMessage("20180114000901", "warnMessage").build();
        // then
        assertEquals("20180114000901", notification.getId());
        assertEquals("warnMessage", notification.getMessage());
        assertEquals("warn", notification.getType());
    }

    @Test
    public void shouldCreateGlobalErrorMessage() {
        // when
        Notification notification = new Notificator().errorMessage("20180114000901", "errorMessage").build();
        // then
        assertEquals("20180114000901", notification.getId());
        assertEquals("errorMessage", notification.getMessage());
        assertEquals("error", notification.getType());
    }

    @Test
    public void shouldCreateGlobalErrorMessageWithData() {
        // when
        Notification notification = new Notificator()
                .errorMessage("20180114000901", "errorMessage")
                .append("items", new HashSet<>(asList(1, 2, 3)))
                .build();
        // then
        assertEquals(notification.getDataMap().size(), 1);
        assertEquals(((Collection) notification.getDataMap().get("items")).size(), 3);
    }

    @Test
    public void shouldCreateGlobalInfoMessageWithCode() {
        // when
        Notification notification = new Notificator().code("code").infoMessage("20180114003735", "infoMessage").build();
        // then
        assertEquals("code", notification.getCode());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfFieldWithGivenNameAlreadyExist() {
        // when
        new Notificator().fieldInfo("field", "message").fieldInfo("field", "message").build();
    }

    @Test
    public void shouldCreateInfoWarnErrorMessageOnFields() {
        // when
        Notification notification = new Notificator()
                .fieldInfo("info", "info")
                .fieldWarn("warn", "warn")
                .fieldError("error", "error")
                .build();
        // then
        assertEquals(notification.getFieldMap().size(), 3);
        assertTrue(notification.getFieldMap().containsKey("info"));
        assertTrue(notification.getFieldMap().containsKey("warn"));
        assertTrue(notification.getFieldMap().containsKey("error"));
    }

    @Test
    public void shouldCreateComplexNotification() {
        // when
        Notification notification = new Notificator()
                .code("code")
                .warnMessage("20180114010134", "warnMessage")
                .append("data1", "data1")
                .append("data2", asList("a", "b", "c"))
                .fieldInfo("field1", "ok")
                .fieldWarn("field2", "warn")
                .fieldRequired("field3")
                .fieldMaxLength("field4", 5)
                .build();
        // then
        assertEquals("code", notification.getCode());
        assertEquals("20180114010134", notification.getId());
        assertEquals("warnMessage", notification.getMessage());

        assertEquals(notification.getDataMap().size(), 2);
        assertEquals(notification.getDataMap().get("data1"), "data1");
        assertEquals(notification.getDataMap().get("data2"), asList("a", "b", "c"));

        assertEquals(notification.getFieldMap().size(), 4);
        assertTrue(notification.getFieldMap().containsKey("field1"));
        assertEquals("ok", notification.getFieldMap().get("field1").getMessage());
        assertTrue(notification.getFieldMap().containsKey("field2"));
        assertEquals("warn", notification.getFieldMap().get("field2").getMessage());
        assertTrue(notification.getFieldMap().containsKey("field3"));
        assertEquals("Pole wymagane", notification.getFieldMap().get("field3").getMessage());
        assertEquals("Maks. ilość znaków wynosi 5", notification.getFieldMap().get("field4").getMessage());
    }

}
