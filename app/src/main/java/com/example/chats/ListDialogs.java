package com.example.chats;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ListDialogs {

    List<Dialogs> dialogs = new ArrayList<Dialogs>();

    public int getSize(){return dialogs.size();}
    public Dialogs getDialogs(int number){ return dialogs.get(number);}

}
