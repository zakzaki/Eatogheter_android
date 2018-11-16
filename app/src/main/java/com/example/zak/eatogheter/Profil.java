package com.example.zak.eatogheter;

import android.net.Uri;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.internal.firebase_auth.zzap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Model.Reponse_requete;
import Model.Reservation_model;
import Model.User;

public class Profil extends Base_fragment {

    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup view_group, Bundle savedInstanceState) {

        super.onCreateView(inflater, view_group, savedInstanceState);
        View view=inflater.inflate(R.layout.activity_profil,view_group,false);


        final EditText m_nom, m_prenom, m_age, m_pseudo;
        final Button m_btn;


        m_nom=view.findViewById(R.id.activity_profil_nom);
        m_prenom=view.findViewById(R.id.activity_profil_prenom);
        m_age=view.findViewById(R.id.activity_profil_age);
        m_pseudo=view.findViewById(R.id.activity_profil_pseudo);

        m_btn=view.findViewById(R.id.activity_profil_cnx_btn);

        if (savedInstanceState != null) {

            Profil profil = new Profil();
            Bundle args = new Bundle();
            profil.setArguments(args);

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.dynamic_fragment_frame_layout, profil).commit();

        }

        /**********************************/

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser userFirebase = mAuth.getCurrentUser();
        String userId = userFirebase.getUid();
        String mail=userFirebase.getEmail();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(userId);

    /*   mAuth.sendPasswordResetEmail(mail)
               .addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {

                       if (task.isSuccessful()) {
                           Toast.makeText(getContext(), "Vérifiez votre messagerie",
                                   Toast.LENGTH_LONG).show();
                       }

                   }
               });*/


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{
                    if (dataSnapshot != null) {
                        HashMap value = (HashMap) dataSnapshot.getValue();

                        Log.d("hh","VALUUUEE "+value.toString());

                        if(value!=null){

                            m_nom.setText((String) value.get("nom"));
                            m_prenom.setText((String) value.get("prenom"));
                            m_age.setText((String) value.get("prenom"));
                            m_pseudo.setText((String) value.get("pseudo"));
                            }
                    }
                }catch(Exception e){
                    Toast.makeText(getContext(), "ERREUR LORS DE LA LECTURES DE VOS INFORMATIONS",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        m_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    int num = Integer.parseInt(m_age.getText().toString().trim());

                    if(m_nom.getText().toString().trim()!="" &&  m_prenom.getText().toString().trim()!="" && m_age.getText().toString().trim()!="" && m_pseudo.getText().toString().trim() != ""){
                        User u=new User(m_nom.getText().toString(), m_prenom.getText().toString(), m_age.getText().toString(), m_pseudo.getText().toString());
                        update_info(u);
                        Toast.makeText(getContext(), "Changements appliqués avec succès",
                                Toast.LENGTH_LONG).show();

                        Recherche recherche = new Recherche();
                        Bundle args = new Bundle();
                        recherche.setArguments(args);

                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.dynamic_fragment_frame_layout, recherche).commit();

                    }else {
                        Toast.makeText(getContext(), "VEULLEZ REMPLIR TOUS LES CHAMPS SVP",
                                Toast.LENGTH_LONG).show();
                    }


                }catch(NumberFormatException e){
                    Toast.makeText(getContext(), "Entrez un age valide",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void update_info(User u){

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser userFirebase = mAuth.getCurrentUser();

        String  userId =  userFirebase.getUid();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

        mDatabase.child("nom").setValue(u.getNom());
        mDatabase.child("prenom").setValue(u.getPrenom());
        mDatabase.child("age").setValue(u.getAge());
        mDatabase.child("pseudo").setValue(u.getPseudo());

    }

    @Override
    public boolean onBackPressed() {

        Recherche recherche=new Recherche();

        Bundle args = new Bundle();
        recherche.setArguments(args);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.dynamic_fragment_frame_layout, recherche);
        transaction.commit();

        return false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //outState.putParcelableArrayList("key", my_books_list);
        super.onSaveInstanceState(outState);
    }

}
