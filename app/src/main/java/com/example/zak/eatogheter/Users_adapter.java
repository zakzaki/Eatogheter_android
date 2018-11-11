package com.example.zak.eatogheter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import Model.Reservation_model;
import Model.User;

public class Users_adapter extends ArrayAdapter<User> {

    public Users_adapter(@NonNull Context context, int ressource, @NonNull List<User> object){
        super(context, ressource, object);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.activity_mes_reservations_adapter, parent, false);
        }

        return convertView;
    }

}
