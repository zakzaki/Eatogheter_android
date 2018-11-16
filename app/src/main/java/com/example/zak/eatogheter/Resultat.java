package com.example.zak.eatogheter;
import android.os.Bundle;

import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import Model.Reponse_requete;

public class Resultat extends Base_fragment{

    private ListView m_lv;
    private Resultat_adapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup view_group, Bundle savedInstanceState) {




        View view=inflater.inflate(R.layout.activity_resultat,view_group,false);

        final ArrayList<Reponse_requete>resultat;
    //    resultat= (ArrayList<Reponse_requete>) getArguments().getSerializable("resultat");

        if((ArrayList<Reponse_requete>) getArguments().getSerializable("resultat")!=null)
            resultat=(ArrayList<Reponse_requete>) getArguments().getSerializable("resultat");
        else  resultat= (ArrayList<Reponse_requete>) getArguments().getSerializable("retour_resultat");

        Log.d("h","RESULTAAAAAAAAT == "+resultat.toString());

        adapter = new Resultat_adapter(getActivity(),resultat);
        m_lv=view.findViewById(R.id.activity_resultat_list_view);

        m_lv.setAdapter(adapter);

        m_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Reponse_requete reponse_requete=(Reponse_requete)parent.getItemAtPosition(position);

                Reserver reserver=new Reserver();

                Bundle args = new Bundle();
                args.putSerializable("resto2", reponse_requete);
                args.putSerializable("retour_resultat", resultat);
                reserver.setArguments(args);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.dynamic_fragment_frame_layout, reserver);
                transaction.commit();
            }
        });

return view;
    }


    @Override
    public boolean onBackPressed() {

        Log.d("HH","RETOUUUUUUUUUUR RES");
        Recherche recherche=new Recherche();

        Bundle args = new Bundle();
        recherche.setArguments(args);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.dynamic_fragment_frame_layout, recherche);
        transaction.commit();

        return false;
    }



}