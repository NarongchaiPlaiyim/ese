package com.ese.utils;

import lombok.Getter;

@Getter
public enum MessageDialog {
    ERROR("Error", ""),
    EDIT("Edit", "Successfully edited."),
    CREATE("Create", "Successfully created."),
    DELETE("Delete", "Successfully deleted."),
    UPDATE("Update", "Successfully updated."),
    WARNING("Warning", ""),
    SAVE("Save", "Successfully saved.");
    private String messageHeader;
    private String message;

    private MessageDialog(String messageHeader, String message) {
        this.messageHeader = messageHeader;
        this.message = message;
    }
}
