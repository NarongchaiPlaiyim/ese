package com.ese.service.message;

import java.util.Locale;
import java.util.ResourceBundle;

public abstract class MessageProvider implements Message {
    protected ResourceBundle getBundle() {
        return ResourceBundle.getBundle("message", new Locale("en", "US"));
    }
}
