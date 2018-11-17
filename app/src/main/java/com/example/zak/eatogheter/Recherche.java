package com.example.zak.eatogheter;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Model.Reponse_requete;


public class Recherche extends Base_fragment{



    private EditText m_edit_txt_localisation, m_edit_txt_mot_cle, m_edit_txt_rayon, m_edit_txt_resultat;
    private Button m_btn;
    private TextView txt = null ;
    final static String idkey = "SPE5SUH4TUBNL3BH3E2URDSALLW0FSIR4EXYYEXUTFF32G22" ;
    final static String appkey = "EVZVG4MB3GXKIJLEYK051SBRTTNG0BYPSCWV5LE3KG5MVRL3" ;
    static final String REQ_TAG = "VACTIVITY";
    final static String version = "20181002";
    final static String url  ="https://api.foursquare.com/v2/venues/search?client_id="+idkey+"&client_secret="+appkey+"&v="+version+"";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup view_group, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.activity_recherche,view_group,false);

        m_edit_txt_localisation= view.findViewById(R.id.activity_recherche_edit_localisation);
        m_edit_txt_mot_cle= view.findViewById(R.id.activity_recherche_edit_mot_cle);
      //  m_edit_txt_rayon = view.findViewById(R.id.activity_recherche_edit_rayon);
        m_edit_txt_resultat= view.findViewById(R.id.activity_recherche_edit_nb_resultat);
        m_btn= view.findViewById(R.id.activity_recherche_btn_rechercher);

        m_btn.setEnabled(false);

        final TextWatcher textWatcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                refreshButton();
            }
            @Override
            public void afterTextChanged(Editable s) {
                refreshButton();
            }
        };

        m_edit_txt_localisation.addTextChangedListener(textWatcher);
        m_edit_txt_mot_cle.addTextChangedListener(textWatcher);
     //   m_edit_txt_rayon.addTextChangedListener(textWatcher);
        m_edit_txt_resultat.addTextChangedListener(textWatcher);

        m_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_resultat();
            }
        });

return view;
    }

    private void refreshButton(){
        m_btn.setEnabled( !m_edit_txt_localisation.getText().toString().isEmpty()
                && !m_edit_txt_mot_cle.getText().toString().isEmpty()
              //  && !m_edit_txt_rayon.getText().toString().isEmpty()
                && !m_edit_txt_resultat.getText().toString().isEmpty() );
    }

    private void get_resultat(){

        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        final String near = m_edit_txt_localisation.getText().toString() ;
        final String query= m_edit_txt_mot_cle.getText().toString();
        //final String radius=m_edit_txt_rayon.getText().toString();
        final String radius="60000";
        final String result=m_edit_txt_resultat.getText().toString();

        try {
            int num = Integer.parseInt(m_edit_txt_resultat.getText().toString().trim());
        }catch (NumberFormatException e){
            Toast.makeText(getContext(), "Entrez un nombre valide",
                    Toast.LENGTH_SHORT).show();
            m_edit_txt_resultat.setText("");
            return;
        }

        final String final_url = url+"&near="+near+"&radius="+radius+"&query="+query+"&limit="+result;


        JsonObjectRequest jsonobjet = new JsonObjectRequest(Request.Method.GET, final_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            ArrayList<Reponse_requete>reponse2=new ArrayList<>();

                            String id, name,address,categorie;
                            JSONArray venue =response.getJSONObject("response").getJSONArray("venues");

                            for(int i=0;i<venue.length();i++){
                                id=""; name=""; address=""; categorie="";
                                JSONObject obj = venue.getJSONObject(i);

                                if(!obj.isNull("id") ) id=obj.getString("id");

                                if(!obj.isNull("name") ) name=obj.getString("name");

                                    if (! obj.getJSONObject("location").isNull("address") ) {
                                        address = obj.getJSONObject("location").getString("address");
                                    }

                                if(! obj.getJSONArray("categories").getJSONObject(0).isNull("pluralName")) categorie=obj.getJSONArray("categories").getJSONObject(0).getString("pluralName");

                                reponse2.add(new Reponse_requete(id,name,address,categorie));

                            }

                            Resultat res = new Resultat();

                            Bundle args = new Bundle();
                            args.putSerializable("resultat", reponse2);
                            res.setArguments(args);

                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.dynamic_fragment_frame_layout, res);
                            transaction.commit();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(), "ERREUR EN PROVENANCE DE L'API",
                        Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(jsonobjet);
    }


    @Override
    public boolean onBackPressed() {
     return false;
    }



}