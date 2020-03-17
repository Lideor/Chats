package com.example.chats;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


public class RVAdapterListNewDialog  extends RecyclerView.Adapter<RVAdapterListNewDialog.CardViewHolder> {


    private ListDialogs data;//база данных
    private Context ctn;//контекст обьекта который вызвал адаптер

    public static class CardViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;

        CardViewHolder(CardView cv) {
            super(cv);
            cardView = cv;
        }
    }

    RVAdapterListNewDialog (ListDialogs data, Context ctn) {
        this.data = data;
        this.ctn = ctn;
    }


    @Override
    public RVAdapterListNewDialog.CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())

                .inflate(R.layout.dialogs, parent, false);

        return new RVAdapterListNewDialog.CardViewHolder(cv);

    }


    @Override
    public void onBindViewHolder(RVAdapterListNewDialog.CardViewHolder cardViewHolder, final int position) {

        final CardView cardView = cardViewHolder.cardView;
        cardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctn, Chat.class);
                intent.putExtra("idDialogs", data.getDialogs(position).getIdDialogs());
                ctn.startActivity(intent);
            }
        });
        RelativeLayout main = (RelativeLayout) cardView.findViewById(R.id.main);// контейнер всех элементов

        RelativeLayout contentChoiceLeft = (RelativeLayout) main.findViewById(R.id.contentChoiceLeft);// контейнер всех элементов

        TextView fullName = (TextView) contentChoiceLeft.findViewById(R.id.fullName);
        fullName.setText(data.getDialogs(position).getFirsName() + " " + data.getDialogs(position).getSurName());

        TextView lastMessage = (TextView) contentChoiceLeft.findViewById(R.id.lastMessage);
        lastMessage.setVisibility(View.INVISIBLE);

        RelativeLayout contentChoiceRight = (RelativeLayout) main.findViewById(R.id.contentChoiceRight);// контейнер всех элементов

        TextView time = (TextView) cardView.findViewById(R.id.time);
        time.setVisibility(View.INVISIBLE);

    }


    @Override

    public int getItemCount() {
        return data.getSize();
    }

    public void changes(Dialogs dialogsChanges) {

        int id = data.serchDialogsOnId(dialogsChanges.getIdDialogs());
        if(id!=-1){
            data.getDialogs(id).setText(dialogsChanges.getText());
        }
        else{
            data.addDialogs(dialogsChanges);
        }
        notifyDataSetChanged();

    }
}

