package com.example.zak.eatogheter;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
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

public class Reservation extends Base_fragment {

    private FirebaseAuth mAuth;
    String TAG="D";

    private ListView m_lv;
    private Reservation_adapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup view_group, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.activity_reservation,view_group,false);
        m_lv=view.findViewById(R.id.activity_reservation_list_view);

        read_reservation();

        m_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Reservation_model reservation_model=(Reservation_model)parent.getItemAtPosition(position);

                Users_list users_list=new Users_list();

                Bundle args = new Bundle();
                args.putSerializable("users", reservation_model.getUsers());
                users_list.setArguments(args);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.dynamic_fragment_frame_layout, users_list);
                transaction.commit();
            }
        });

return  view;
    }


   private void read_reservation(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("reservations");


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Reservation_model> list_reservation = new ArrayList<>();
                try {
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

                            ArrayList<String> users;

                            try {
                                HashMap<String, String> users_map = (HashMap<String, String>) postValues.get("users");
                                users = new ArrayList<>(users_map.values());
                            } catch (Exception e) {
                                users = (ArrayList<String>) postValues.get("users");
                            }

                            if (!users.contains(userId)) {

                                HashMap<String, String> hash_resto = (HashMap<String, String>) postValues.get("r");
                                Reponse_requete resto = new Reponse_requete(hash_resto.get("id"), hash_resto.get("nom"), hash_resto.get("adresse"), hash_resto.get("categorie"));
                                Reservation_model r = new Reservation_model(key, resto, (String) postValues.get("date"), (String) postValues.get("heure"), users);

                                list_reservation.add(r);
                            }
                        }
                    }
                }
            }catch(Exception e){
                    Toast.makeText(getContext(), "ERREUR LORS DE LA LECTURES DES RESERVATIONS",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
            }
                m_lv=getView().findViewById(R.id.activity_reservation_list_view);
                adapter=new Reservation_adapter(getActivity(),R.layout.activity_reservation_adapter,list_reservation);
                m_lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
