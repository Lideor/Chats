package com.example.chats;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Dialogs {

    @JsonProperty("id_dialog")
    private int idDialogs;

    @JsonProperty("date")
    private String date;

    @JsonProperty("surname")
    private String surName;

    @JsonProperty("firstname")
    private String firsName;

    @JsonProperty("text")
    private String text;

    @JsonProperty("check_message")
    private boolean checkMessage;

    public String getDate(){
        return date;
    }

    public String getSurName(){
        return surName;
    }

    public String getFirsName(){
        return firsName;
    }

    public String getText(){
        return text;
    }

    public int getIdDialogs(){
        return idDialogs;
    }

    public boolean getCheckMessage(){
        return checkMessage;
    }
}
