package com.example.chats;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.text.SimpleDateFormat;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chats.Chat;
import com.example.chats.ListDialogs;
import com.example.chats.R;

import java.util.Calendar;
import java.util.Date;

import static java.lang.Math.abs;

public class RVAdapterListMessage extends RecyclerView.Adapter<RVAdapterListMessage.CardViewHolder> {


    private ListMessage data;//база данных
    private Context ctn;//контекст обьекта который вызвал адаптер

    public static class CardViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;

        CardViewHolder(CardView cv) {
            super(cv);
            cardView = cv;
        }
    }

    RVAdapterListMessage(ListMessage data, Context ctn) {
        this.data = data;
        this.ctn = ctn;
    }

    @Override
    public RVAdapterListMessage.CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())

                .inflate(R.layout.dialogs, parent, false);

        return new RVAdapterListMessage.CardViewHolder(cv);

    }


    @Override
    public void onBindViewHolder(RVAdapterListMessage.CardViewHolder cardViewHolder, final int position) {

        final CardView cardView = cardViewHolder.cardView;
        cardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

            }
        });
        RelativeLayout main = (RelativeLayout) cardView.findViewById(R.id.main);// контейнер всех элементов

        RelativeLayout contentChoiceLeft = (RelativeLayout) main.findViewById(R.id.contentChoiceLeft);// контейнер всех элементов

        TextView fullName = (TextView) contentChoiceLeft.findViewById(R.id.fullName);
        fullName.setText(data.getMessage(position).getFirsName() + " " + data.getMessage(position).getSurName());

        TextView lastMessage = (TextView) contentChoiceLeft.findViewById(R.id.lastMessage);
        lastMessage.setText(data.getMessage(position).getText());

        if(data.getMessage(position).getCheckMessage()==0) lastMessage.setBackgroundColor(ctn.getResources().getColor(R.color.colorPrimaryCard));
        else lastMessage.setBackgroundColor(ctn.getResources().getColor(R.color.colorMainCard));

        SimpleDateFormat dateFormat = new SimpleDateFormat("y-M-d H:m:s");

        Date convertedDate = new Date();
        try
        {
            String asd =data.getMessage(position).getDate();
            convertedDate = dateFormat.parse(data.getMessage(position).getDate());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Date setData = new Date();
        long convertDateMillis = convertedDate.getTime();
        long setDataMillis = setData.getTime();

        String currentDateString = "";
        long f =abs(convertDateMillis-setDataMillis)/(1000*60*60*24);

        if (abs(convertDateMillis-setDataMillis)/(1000*60*60*24)>1){
            dateFormat = new SimpleDateFormat("dd-MM");
            currentDateString = dateFormat.format(convertedDate);
        }
        else {
            dateFormat = new SimpleDateFormat("HH:mm");
            currentDateString = dateFormat.format(convertedDate);
        }

        RelativeLayout contentChoiceRight = (RelativeLayout) main.findViewById(R.id.contentChoiceRight);// контейнер всех элементов

        TextView time = (TextView) cardView.findViewById(R.id.time);
        time.setText(currentDateString);

    }


    @Override

    public int getItemCount() {
        return data.getSize();
    }

    public void addNewMessage(int idLogin,int idDialogs, String text) {
        Message newMessage = new Message();
        newMessage.setText(text);

        SimpleDateFormat dateFormat = new SimpleDateFormat("y-M-d H:m:s");
        Date convertedDate = new Date();
        String currentDateString = dateFormat.format(convertedDate);

        newMessage.setDate(currentDateString);
        newMessage.setIdDialogs(idDialogs);
        newMessage.setIdLogin(idLogin);
        data.addNewMessage(newMessage);
        notifyDataSetChanged();

    }
}
