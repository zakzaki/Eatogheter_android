package com.example.zak.eatogheter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import Model.Reservation_model;

public class Mes_reservations_adapter extends ArrayAdapter<Reservation_model> {


   public Mes_reservations_adapter(@NonNull Context context, int ressource, @NonNull List<Reservation_model> object) {
        super(context, ressource, object);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.activity_mes_reservations_adapter, parent, false);
        }

        final TextView m_nom, m_adresse, m_date, m_heure;
        final Button m_modifier, m_supprimer, m_voir;

        m_nom=convertView.findViewById(R.id.activity_mes_reservation_adapter_nom);
        m_adresse=convertView.findViewById(R.id.activity_mes_reservation_adapter_adress);
        m_date=convertView.findViewById(R.id.activity_mes_reservation_adapter_date);
        m_heure=convertView.findViewById(R.id.activity_mes_reservation_adapter_heure);

        m_modifier=convertView.findViewById(R.id.mes_reservation_adapter_btn_modifier);
        m_supprimer=convertView.findViewById(R.id.mes_reservation_adapter_btn_supprimer);
      //  m_voir=convertView.findViewById(R.id.mes_reservation_adapter_voir_btn);

        final Reservation_model res=getItem(position);

        m_nom.setText(res.getR().getNom());
        m_adresse.setText(res.getR().getAdresse());
        m_date.setText(res.getDate());
        m_heure.setText(res.getHeure());



        return convertView;
    }

}
