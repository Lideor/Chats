package com.example.chats;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ListDialogs {

    private List<Dialogs> dialogs = new ArrayList<Dialogs>();

    public int getSize(){return dialogs.size();}
    public Dialogs getDialogs(int number){ return dialogs.get(number);}

    public int serchDialogsOnId(int idDialogsSerch){
        for(int i = 0; i<dialogs.size()-1;i++){
            if (dialogs.get(i).getIdDialogs()==idDialogsSerch) return i;
        }
        return -1;
    }

    public void addDialogs(Dialogs newDialogs){
        dialogs.add(newDialogs);
    }
}
