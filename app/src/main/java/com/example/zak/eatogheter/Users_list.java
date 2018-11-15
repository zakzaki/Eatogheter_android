package com.example.zak.eatogheter;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

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

import Model.User;

public class Users_list extends Base_fragment {

    private ListView m_lv;
    private Users_adapter adapter;
    private String provenance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup view_group, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.activity_users_list,view_group,false);
        m_lv=view.findViewById(R.id.users_list_lv);

        read_users();
        return view;
    }

    public void read_users(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");



        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<User> list_users = new ArrayList<>();
                ArrayList<String>users=(ArrayList<String>)getArguments().getSerializable("users");
                provenance=getArguments().getString("provenance");
                if(provenance==null) provenance="";

                try {
                    if (dataSnapshot != null) {
                        HashMap value = (HashMap) dataSnapshot.getValue();
                        if (value != null) {

                            Set cles = value.keySet();
                            Iterator it = cles.iterator();

                            while (it.hasNext()) {
                                String key = (String) it.next();
                                Map<String, Object> postValues = (Map) value.get(key);

                                if(users.contains(key)){
                                    User u=new User(key, (String)postValues.get("nom"),(String)postValues.get("prenom"),(String)postValues.get("age") ,(String)postValues.get("pseudo"));
                                    list_users.add(u);
                                }

                            }
                        }
                    }
                }catch(Exception e){
                    Toast.makeText(getContext(), "ERREUR LORS DE LA LECTURES DES RESERVATIONS",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                m_lv=getView().findViewById(R.id.users_list_lv);
                adapter=new Users_adapter(getActivity(),R.layout.activity_users_adapter,list_users);
                m_lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onBackPressed() {
      //  super.onBackPressed();
       Log.d("HH","RETOUUUUUUUUUUR");

        Bundle args = new Bundle();

       if(provenance.equals("mes_reservations")) {
           Log.d("HH","RETOUUUUUUUUUUR MES RESER");
           Mes_reservations r = new Mes_reservations();
           r.setArguments(args);
           FragmentTransaction transaction = getFragmentManager().beginTransaction();
           transaction.replace(R.id.dynamic_fragment_frame_layout, r).commit();
       }

       else {
           Log.d("HH","RETOUUUUUUUUUUR RESER");
           Reservation r = new Reservation();
           r.setArguments(args);
           FragmentTransaction transaction = getFragmentManager().beginTransaction();
           transaction.replace(R.id.dynamic_fragment_frame_layout, r).commit();
       }


        return false;
    }

}
