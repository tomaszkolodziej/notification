package com.tolean.notification;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static com.tolean.notification.Notificator.fieldMaxLength;
import static com.tolean.notification.Notificator.fieldRequired;
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
    public void shouldCreateGlobalErrorMessageWithRequiredMessage() {
        // when
        Notification notification = new Notificator().errorMessage("20180114005937", fieldRequired()).build();
        // then
        assertEquals("Pole wymagane", notification.getMessage());
    }

    @Test
    public void shouldCreateGlobalErrorMessageWithMaxLengthMessage() {
        // when
        Notification notification = new Notificator().errorMessage("20180114005937", fieldMaxLength(9)).build();
        // then
        assertEquals("Maks. długość wynosi 9", notification.getMessage());
    }

    @Test
    public void shouldCreateGlobalErrorMessageWithData() {
        // when
        Notification notification = new Notificator()
                .errorMessage("20180114000901", "errorMessage")
                .append("test")
                .append("1", "2", "3")
                .append(new ArrayList())
                .build();
        // then
        assertEquals(notification.getDataList().size(), 4);
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
                .append("data1").append("data2", "data3")
                .fieldInfo("field1", "ok")
                .fieldWarn("field2", "warn")
                .fieldError("field3", fieldRequired())
                .build();
        // then
        assertEquals("code", notification.getCode());
        assertEquals("20180114010134", notification.getId());
        assertEquals("warnMessage", notification.getMessage());

        assertEquals(notification.getDataList().size(), 3);
        assertTrue(notification.getDataList().containsAll(asList("data1", "data2", "data3")));

        assertEquals(notification.getFieldMap().size(), 3);
        assertTrue(notification.getFieldMap().containsKey("field1"));
        assertEquals("ok", notification.getFieldMap().get("field1").getMessage());
        assertTrue(notification.getFieldMap().containsKey("field2"));
        assertEquals("warn", notification.getFieldMap().get("field2").getMessage());
        assertTrue(notification.getFieldMap().containsKey("field3"));
        assertEquals("Pole wymagane", notification.getFieldMap().get("field3").getMessage());
    }

}
