package com.example.chats;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ListMessage {
    List<Message> message = new ArrayList<Message>();

    public int getSize(){return message.size();}
    public Message getMessage(int number){ return message.get(number);}

    public int addNewMessage(Message newMessage){
        message.add(newMessage);
        return 1;
    }
}
