package com.example.zak.eatogheter;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Model.Reponse_requete;
import Model.Reservation_model;
import Model.Restaurant;


public class Reserver extends Base_fragment{

    DatePickerDialog.OnDateSetListener m_date;
    TimePickerDialog.OnTimeSetListener m_heure;
    Button m_btn;
    TextView m_tv_date, m_tv_heure;
    private ImageButton date_dialog_button = null,heure_dialog_button=null;
    String TAG="D";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup view_group, Bundle savedInstanceState) {

        super.onCreateView(inflater, view_group, savedInstanceState);

        View view = inflater.inflate(R.layout.activity_reserver, view_group, false);


        m_btn = view.findViewById(R.id.reserver_btn);

        m_tv_date = view.findViewById(R.id.reserver_date_txt_view);
        m_tv_heure = view.findViewById(R.id.reserver_heure_txt_view);

        date_dialog_button = view.findViewById(R.id.reserver_calandrier);
        heure_dialog_button=view.findViewById(R.id.reserver_heure);


        date_dialog_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month =cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getContext(),android.R.style.Theme_Black,m_date,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        m_date=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String date=dayOfMonth +"/"+month+"/"+year;
                m_tv_date.setText(date);
            }
        };

        heure_dialog_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                int hour=cal.get(Calendar.HOUR_OF_DAY);
                int minute=cal.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(getContext(),android.R.style.Theme_Black,m_heure,hour,minute,true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        m_heure=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String date=hourOfDay +":"+minute;
                m_tv_heure.setText(date);
            }
        };





       m_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String date=m_tv_date.getText().toString();
                int day,month,year,hour,minute;

                try{
                    day=Integer.parseInt(date.substring(0,2));

                    try{
                        month=Integer.parseInt(date.substring(3,5));
                        year=Integer.parseInt(date.substring(6,10));
                    }catch (NumberFormatException e){
                        month=Integer.parseInt(date.substring(3,4));
                        year=Integer.parseInt(date.substring(5,9));
                    }
                }catch (NumberFormatException e){
                    day=Integer.parseInt(date.substring(0,1));
                    try{
                        month=Integer.parseInt(date.substring(2,4));
                        year=Integer.parseInt(date.substring(5,9));
                    }catch (NumberFormatException e2){
                        month=Integer.parseInt(date.substring(2,3));
                        year=Integer.parseInt(date.substring(4,8));
                    }
                }
                month--;
                String heure=m_tv_heure.getText().toString();

                try{
                    hour=Integer.parseInt(heure.substring(0,2));

                    try{
                        minute=Integer.parseInt(heure.substring(3,5));
                    }catch (NumberFormatException e){
                        minute=Integer.parseInt(heure.substring(3,4));
                    }

                }catch (NumberFormatException e){
                    hour=Integer.parseInt(heure.substring(0,1));

                    try{
                        minute=Integer.parseInt(heure.substring(2,heure.length()));
                    }catch (NumberFormatException e2){
                        minute=Integer.parseInt(heure.substring(2,heure.length()));
                    }
                }
                Calendar validDate = Calendar.getInstance();
                validDate.set(year, month, day,hour,minute);
                Calendar currentDate = Calendar.getInstance();
                if (currentDate.after(validDate)) {

                    Toast.makeText(getActivity(), "Veuillez sélectionner une date et un horaire supérieur à celle d'auhourd'hui ",
                            Toast.LENGTH_LONG).show();

                }else{
                    if(m_tv_date.getText().toString()!="Selectionner la date" && m_tv_heure.getText().toString() != "Selectionner l'heure"){

                        Reponse_requete rep = (Reponse_requete) getArguments().getSerializable("resto2");
                        try{

                            FirebaseAuth mAuth;

                            mAuth = FirebaseAuth.getInstance();
                            FirebaseUser userFirebase = mAuth.getCurrentUser();

                            String userId = userFirebase.getUid();
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

                            ArrayList<String> users = new ArrayList<>();
                            users.add(userId);

                            Reservation_model restaurant = new Reservation_model(rep, m_tv_date.getText().toString(), m_tv_heure.getText().toString(), users);

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            String key = database.getReference("reservations").push().getKey();

                            mDatabase.child("reservations").child(key).setValue(restaurant);


                            Toast.makeText(getActivity(), "RESERVATION REUSSIE",
                                    Toast.LENGTH_LONG).show();
                            Recherche recherche = new Recherche();

                            Bundle args = new Bundle();
                            recherche.setArguments(args);

                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.dynamic_fragment_frame_layout, recherche);
                            transaction.commit();


                        }catch(Exception e){
                            Toast.makeText(getActivity(), "ERREUR LORS DE LA RESERVATION! REESSAYER",
                                    Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                            refreshitem();
                        }

                    }else{
                        Toast.makeText(getActivity(), "Veuillez sélectionner une date et un horaire précis",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        return view;
    }

    public void refreshitem(){
        m_tv_date.setText("Selectionner la date");
        m_tv_heure.setText("Selectionner l'heure");
    }

    @Override
    public boolean onBackPressed() {

        Resultat resultat=new Resultat();

        Bundle args = new Bundle();
        args.putSerializable("retour_resultat", (ArrayList<Reponse_requete>) getArguments().getSerializable("retour_resultat"));
        resultat.setArguments(args);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.dynamic_fragment_frame_layout, resultat).commit();

        return false;
    }


}
