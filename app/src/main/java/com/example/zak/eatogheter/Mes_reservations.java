package com.example.zak.eatogheter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import Model.Reponse_requete;
import Model.Reservation_model;

public class Mes_reservations extends Base_fragment {

    private FirebaseAuth mAuth;
    private ListView m_lv;
    private Mes_reservations_adapter adapter;
    ArrayList<Reservation_model> list_reservation = new ArrayList<>();

  /*  @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null  ) {


            list_reservation= (ArrayList<Reservation_model>) savedInstanceState.getSerializable("saved");
            m_lv=getView().findViewById(R.id.activity_mes_reservations_list_view);
            Log.d("h","LAAAAAAAAAAAAAAAAAAAAAAAA");
            adapter= new Mes_reservations_adapter(getActivity(), R.layout.activity_mes_reservations_adapter,list_reservation);
            m_lv.setAdapter(adapter);
            if(list_reservation.size()==0)
                Toast.makeText(getContext(), "Vous n'avez pas de réservations ",
                        Toast.LENGTH_LONG).show();

        }
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup view_group, Bundle savedInstanceState) {

        super.onCreateView(inflater, view_group, savedInstanceState);

        View view=inflater.inflate(R.layout.activity_mes_reservations,view_group,false);
        m_lv=view.findViewById(R.id.activity_mes_reservations_list_view);

    /*    if (savedInstanceState != null  ) {

            Mes_reservations mes_reservations = new Mes_reservations();
            Bundle args = new Bundle();
            mes_reservations.setArguments(args);

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.dynamic_fragment_frame_layout, mes_reservations).commit();

            list_reservation= (ArrayList<Reservation_model>) savedInstanceState.getSerializable("saved");

            Log.d("h","LAAAAAAAAAAAAAAAAAAAAAAAA");
            adapter= new Mes_reservations_adapter(getActivity(), R.layout.activity_mes_reservations_adapter,list_reservation);
            m_lv.setAdapter(adapter);
            if(list_reservation.size()==0)
                Toast.makeText(getContext(), "Vous n'avez pas de réservations ",
                        Toast.LENGTH_LONG).show();

        }

       else */
           read_reservation();

        m_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Reservation_model reservation_model=(Reservation_model)parent.getItemAtPosition(position);

                Users_list users_list=new Users_list();

                Bundle args = new Bundle();
                args.putSerializable("users", reservation_model.getUsers());
                args.putString("provenance","mes_reservations");
                users_list.setArguments(args);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.dynamic_fragment_frame_layout, users_list);
                transaction.commit();
            }
        });

        return view;
    }

    private void read_reservation(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("reservations");



        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try{
                    if (dataSnapshot != null) {
                        HashMap value = (HashMap) dataSnapshot.getValue();
                        if (value != null) {

                        Set cles = value.keySet();
                        Iterator it = cles.iterator();

                        mAuth = FirebaseAuth.getInstance();
                        FirebaseUser userFirebase = mAuth.getCurrentUser();
                        String userId = userFirebase.getUid();

                        while (it.hasNext()) {

                            String key = (String) it.next();
                            Map<String, Object> postValues = (Map) value.get(key);
                            ArrayList<String>users;
                            String key_key="0";
                            try{
                                HashMap<String, String> users_map = (HashMap<String, String>) postValues.get("users");
                                users = new ArrayList<>(users_map.values());
                                /*************************/
                                for (Object o : users_map.keySet()) {
                                    if (users_map.get(o).equals(userId)) {
                                       key_key=(String)o;
                                       break;
                                    }
                                }
                                /*************************/
                            }catch (Exception e){
                               users=(ArrayList<String>) postValues.get("users");
                            }
                            if (users.contains(userId)) {
                                 HashMap<String, String> hash_resto = (HashMap<String, String>) postValues.get("r");
                                 Reponse_requete resto = new Reponse_requete(hash_resto.get("id"), hash_resto.get("nom"), hash_resto.get("adresse"), hash_resto.get("categorie"));
                                 Reservation_model r = new Reservation_model(key,resto, (String) postValues.get("date"), (String) postValues.get("heure"), users,key_key);

                                 list_reservation.add(r);
                            }
                        }
                    }
                }
            }catch(Exception e){
                    Toast.makeText(getContext(), "ERREUR LORS DE LA LECTURES DE VOS RESERVATIONS",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
            }
                m_lv=getView().findViewById(R.id.activity_mes_reservations_list_view);
                adapter= new Mes_reservations_adapter(getActivity(), R.layout.activity_mes_reservations_adapter,list_reservation);
                m_lv.setAdapter(adapter);
                if(list_reservation.size()==0)
                Toast.makeText(getContext(), "Vous n'avez pas de réservations ",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




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

  /*  @Override
    public void onSaveInstanceState(Bundle outState) {
       //outState.putSerializable("saved",list_reservation);
        super.onSaveInstanceState(outState);
    }*/

}
