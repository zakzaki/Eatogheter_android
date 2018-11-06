package com.example.zak.eatogheter;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

import Model.Reponse_requete;
import Model.Reservation_model;
import Model.Restaurant;


public class Reserver extends AppCompatActivity{

    DatePickerDialog.OnDateSetListener m_date;
    TimePickerDialog.OnTimeSetListener m_heure;
    Button m_btn;
    TextView m_tv_date, m_tv_heure;
    String TAG="D";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserver);


        m_btn=findViewById(R.id.reserver_btn);

        m_tv_date=findViewById(R.id.reserver_date_txt_view);
        m_tv_heure=findViewById(R.id.reserver_heure_txt_view);

        m_tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month =cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Reserver.this,android.R.style.Theme_Black,m_date,year,month,day);
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

        m_tv_heure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                int hour=cal.get(Calendar.HOUR_OF_DAY);
                int minute=cal.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(Reserver.this,android.R.style.Theme_Black,m_heure,hour,minute,true);
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

            Reponse_requete rep=(Reponse_requete) getIntent().getSerializableExtra("resto");


            @Override
            public void onClick(View v) {

                try{
                FirebaseAuth mAuth;

                mAuth = FirebaseAuth.getInstance();
                FirebaseUser userFirebase = mAuth.getCurrentUser();

                String userId = userFirebase.getUid();
                Log.d(TAG, "LE USER ID EST " + userId);
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

                ArrayList<String> users = new ArrayList<>();
                    users.add(userId);

                Reservation_model restaurant = new Reservation_model(rep, m_tv_date.getText().toString(), m_tv_heure.getText().toString(), users);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                String key = database.getReference("reservations").push().getKey();

                Log.d(TAG, "LA KEEEEEEY EST " + key);
                mDatabase.child("reservations").child(key).setValue(restaurant);

                    refreshitem();
                    Toast.makeText(Reserver.this, "RESERVATION REUSSIE",
                            Toast.LENGTH_LONG).show();

            }catch(Exception e){
                    Toast.makeText(Reserver.this, "ERREUR LORS DE LA RESERVATION! REESSAYER",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    refreshitem();
                }
            }
        });
    }

    public void refreshitem(){
        m_tv_date.setText("Selectionner la date");
        m_tv_heure.setText("Selectionner l'heure");
    }

}
