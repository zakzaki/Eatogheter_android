package com.example.zak.eatogheter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import Model.Reservation_model;

public class Reservation_adapter extends ArrayAdapter<Reservation_model> {
    private Context context;

    public Reservation_adapter(@NonNull Context context, int ressource, @NonNull List<Reservation_model> object){
        super(context,ressource,object);
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.activity_reservation_adapter, parent, false);
        }

        final TextView m_nom, m_adresse, m_date, m_heure;
        final Button m_rejoindre_btn, m_voir_btn;

        m_nom=convertView.findViewById(R.id.activity_reservation_adapter_nom);
        m_adresse=convertView.findViewById(R.id.activity_reservation_adapter_adress);
        m_date=convertView.findViewById(R.id.activity_reservation_adapter_date);
        m_heure=convertView.findViewById(R.id.activity_reservation_adapter_heure);
        m_rejoindre_btn=convertView.findViewById(R.id.reservation_adapter_btn);
        m_voir_btn=convertView.findViewById(R.id.reservation_adapter__voir_btn);

        final Reservation_model res=getItem(position);

        m_nom.setText(res.getR().getNom());
        m_adresse.setText(res.getR().getAdresse());
        m_date.setText(res.getDate());
        m_heure.setText(res.getHeure());

        m_rejoindre_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        m_voir_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        return convertView;
    }
}
