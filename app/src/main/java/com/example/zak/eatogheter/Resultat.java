package com.example.zak.eatogheter;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import Model.Reponse_requete;

public class Resultat extends Base_fragment{

    private ListView m_lv;
    private Resultat_adapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup view_group, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.activity_resultat,view_group,false);

        final ArrayList<Reponse_requete>resultat = (ArrayList<Reponse_requete>) getArguments().getSerializable("resultat");

        adapter = new Resultat_adapter(getActivity(),R.layout.activity_resultat_adapter,resultat);
        m_lv=view.findViewById(R.id.activity_resultat_list_view);

        m_lv.setAdapter(adapter);

return view;
    }


    @Override
    public boolean onBackPressed() {
      return false;
    }



}