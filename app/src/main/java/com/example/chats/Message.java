package com.example.chats;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {
    @JsonProperty("idDialogs")
    private int idDialogs;

    @JsonProperty("idMessage")
    private int idMessage;

    @JsonProperty("idLogin")
    private int idLogin;

    @JsonProperty("data")
    private String data;

    @JsonProperty("surName")
    private String surName;

    @JsonProperty("firsName")
    private String firsName;

    @JsonProperty("text")
    private String text;

    @JsonProperty("checkMessage")
    private int checkMessage=0;

    public String getDate(){
        return data;
    }
    public void setDate(String newDate){ data = newDate;}

    public String getSurName(){
        return surName;
    }
    public void setSurName(String newSurName){ surName = newSurName;}


    public String getFirsName(){
        return firsName;
    }
    public void setFirsName(String newfirsName){ firsName = newfirsName;}


    public String getText(){
        return text;
    }
    public void setText(String newText){ text = newText;}


    public int getIdLogin(){
        return idLogin;
    }
    public void setIdLogin(int newIdLogin){ idLogin = newIdLogin;}


    public int getIdDialogs(){
        return idDialogs;
    }
    public void setIdDialogs(int newIdDialogs){ idDialogs = newIdDialogs;}

    public int getidMessage(){
        return idMessage;
    }
    public void setIdMessage(int newIdDialogs){ idMessage = newIdDialogs;}


    public int getCheckMessage(){
        return checkMessage;
    }
}
