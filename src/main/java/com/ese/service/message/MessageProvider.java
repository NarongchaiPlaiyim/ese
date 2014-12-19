package com.ese.service.message;

import com.ese.beans.Bean;

import java.util.Locale;
import java.util.ResourceBundle;

public abstract class MessageProvider extends Bean implements Message {
    protected ResourceBundle getBundle() {
        return ResourceBundle.getBundle("message", new Locale("en", "US"));
    }
}
