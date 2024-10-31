package com.pet_project.pastebin.tools;


import lombok.Getter;


@Getter
public enum PasteStatus {
    NO_SUCH_PASTE("No such paste"),
    PASSWORD_INCORRECT("Incorrect password"),
    PASTE_FOUND("Paste found");
    private final String message;
    PasteStatus(String msg) {
        this.message = msg;
    }
}
