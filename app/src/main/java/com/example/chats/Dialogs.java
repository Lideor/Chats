package com.example.chats;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Dialogs {

    @JsonProperty("idDialogs")
    private int idDialogs;

    @JsonProperty("data")
    private String data;

    @JsonProperty("surName")
    private String surName;

    @JsonProperty("firsName")
    private String firsName;

    @JsonProperty("text")
    private String text;

    @JsonProperty("checkMessage")
    private int checkMessage;

    public String getDate(){
        return data;
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

    public void setText(String newText){text=newText;}

    public int getIdDialogs(){
        return idDialogs;
    }

    public int getCheckMessage(){
        return checkMessage;
    }
}
