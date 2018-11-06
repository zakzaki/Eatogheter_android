package com.example.zak.eatogheter;

import android.content.Context;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import Model.Reponse_requete;


public class Resultat_adapter extends ArrayAdapter<Reponse_requete> {

 private Context context;

    public Resultat_adapter(@NonNull Context context, int ressource, @NonNull List<Reponse_requete> object){
        super(context,ressource,object);
        this.context=context;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){


        if(convertView==null){
            LayoutInflater inflater= LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.activity_resultat_adapter,parent,false);
        }

        final TextView m_nom,m_adress,m_cat;
        final ImageView m_img;
        final ImageButton m_btn;

        m_nom=convertView.findViewById(R.id.activity_resultat_adapter_nom);
        m_adress=convertView.findViewById(R.id.activity_resultat_adapter_adress);
        m_cat=convertView.findViewById(R.id.activity_resultat_adapter_categorie);
        m_btn=convertView.findViewById(R.id.result_adapter_btn);



        final Reponse_requete rep_req=getItem(position);


        m_nom.setText(rep_req.getNom());
        m_adress.setText(rep_req.getAdresse());
        m_cat.setText(rep_req.getCategorie());

        m_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(context, Reserver.class);
                intent.putExtra("resto",rep_req);
                context.startActivity(intent);

            }
        });

        return convertView;

    }


}
