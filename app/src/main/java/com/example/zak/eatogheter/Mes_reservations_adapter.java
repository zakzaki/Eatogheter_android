package com.example.zak.eatogheter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import Model.Reservation_model;

public class Mes_reservations_adapter extends ArrayAdapter<Reservation_model> {

    Mes_reservations mes_reservations;

   public Mes_reservations_adapter(@NonNull Context context, int ressource, @NonNull List<Reservation_model> object, Mes_reservations fragment) {
        super(context, ressource, object);
        this.mes_reservations=fragment;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.activity_mes_reservations_adapter, parent, false);
        }

        final TextView m_nom, m_adresse, m_date, m_heure;
        final ImageButton m_supprimer;
        final RelativeLayout r;

        m_nom=convertView.findViewById(R.id.activity_mes_reservation_adapter_nom);
        m_adresse=convertView.findViewById(R.id.activity_mes_reservation_adapter_adress);
        m_date=convertView.findViewById(R.id.activity_mes_reservation_adapter_date);
        m_heure=convertView.findViewById(R.id.activity_mes_reservation_adapter_heure);

        m_supprimer=convertView.findViewById(R.id.mes_reservation_adapter_btn_supprimer);
        r=convertView.findViewById(R.id.mes_reservation_relative);

        final Reservation_model res=getItem(position);

        m_nom.setText(res.getR().getNom());
        m_adresse.setText(res.getR().getAdresse());
        m_date.setText(res.getDate());
        m_heure.setText(res.getHeure());

        m_supprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference mDatabase = database.getReference("reservations");

                try{
                    if(res.getUsers().size()==1){
                        mDatabase.child(res.getKey()).removeValue();

                    }else{
                        mDatabase.child(res.getKey()).child("users").child(res.getKey_key()).removeValue();
                    }
                    mes_reservations.reload(res);
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getContext(), "ERREUR LORS DE LA SUPPRESSION",
                            Toast.LENGTH_LONG).show();
                }
            }
        });


        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mes_reservations.show_users(res);
            }
        });


        return convertView;
    }

}
