package com.mjcasl.aslintermediaire.model;/* Created by Alexandre Labreveux */

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mjcasl.aslintermediaire.R;
import com.mjcasl.aslintermediaire.controller.EventHandlerClass;

import java.util.ArrayList;

public class SoundboxRecyclerAdapter extends RecyclerView.Adapter<SoundboxRecyclerAdapter.SoundboxViewHolder> {

    private ArrayList<com.mjcasl.aslintermediaire.model.SoundObject> soundObjects;

    public SoundboxRecyclerAdapter(ArrayList<com.mjcasl.aslintermediaire.model.SoundObject> soundObjects){
        this.soundObjects = soundObjects;
    }

    @Override
    public SoundboxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sound_item,null);
        return new SoundboxViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SoundboxViewHolder holder, int position) {

        final SoundObject object = soundObjects.get(position);
        final Integer soundID = object.getItemID();

        holder.itemTextView.setText(object.getItemName());

        holder.itemImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventHandlerClass.startSound(v,soundID);
            }
        });
    }

    @Override
    public int getItemCount() {
        return soundObjects.size();
    }

    public class SoundboxViewHolder extends RecyclerView.ViewHolder{

        ImageButton itemImageView;
        TextView itemTextView;

        public SoundboxViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImageView = (ImageButton) itemView.findViewById(R.id.imageViewItem);
            itemTextView = (TextView) itemView.findViewById(R.id.textViewItem);
        }
    }


}
