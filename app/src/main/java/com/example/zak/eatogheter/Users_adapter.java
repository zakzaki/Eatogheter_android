package com.example.zak.eatogheter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import Model.User;

public class Users_adapter extends ArrayAdapter<User> {

    public Users_adapter(@NonNull Context context, int ressource, @NonNull List<User> object){
        super(context, ressource, object);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.activity_users_adapter, parent, false);
        }

        final TextView m_nom,m_prenom,m_age,m_pseudo;
        final User user=getItem(position);

        m_nom=convertView.findViewById(R.id.activity_users_adapter_nom);
        m_prenom=convertView.findViewById(R.id.activity_users_adapter_prenom);
        m_age=convertView.findViewById(R.id.activity_users_adapter_age);
        m_pseudo=convertView.findViewById(R.id.activity_users_adapter_pseudo);



        m_nom.setText(user.getNom());
        m_prenom.setText(user.getPrenom());
        m_age.setText(user.getAge()+" ans");
        m_pseudo.setText(user.getPseudo());

        return convertView;
    }

}
