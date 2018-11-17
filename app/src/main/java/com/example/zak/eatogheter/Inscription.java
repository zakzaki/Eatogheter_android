package com.example.zak.eatogheter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import Model.User;

public class Inscription extends AppCompatActivity {

    EditText m_mail, m_nom, m_prenom, m_age, m_pseudo, m_pass;
    Button m_btn;

    private FirebaseAuth mAuth;
    String TAG="D";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        m_mail=findViewById(R.id.activity_inscription_mail);
        m_nom=findViewById(R.id.activity_inscription_nom);
        m_prenom=findViewById(R.id.activity_inscription_prenom);
        m_age=findViewById(R.id.activity_inscription_age);
        m_pseudo=findViewById(R.id.activity_inscription_pseudo);
        m_pass=findViewById(R.id.activity_inscription_password_txt);
        m_btn=findViewById(R.id.activity_inscription_cnx_btn);

        m_btn.setEnabled(false);

        final TextWatcher txt_watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                refrechButton();
            }

            @Override
            public void afterTextChanged(Editable s) {
                refrechButton();
            }
        };

        m_mail.addTextChangedListener(txt_watcher);
        m_nom.addTextChangedListener(txt_watcher);
        m_prenom.addTextChangedListener(txt_watcher);
        m_age.addTextChangedListener(txt_watcher);
        m_pseudo.addTextChangedListener(txt_watcher);
        m_pass.addTextChangedListener(txt_watcher);

        mAuth = FirebaseAuth.getInstance();

        m_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    int num = Integer.parseInt(m_age.getText().toString().trim());

                        mAuth.createUserWithEmailAndPassword(m_mail.getText().toString().trim(), m_pass.getText().toString())
                                .addOnCompleteListener(Inscription.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Log.d(TAG, "signInWithEmail:success");

                                            User u= new User(m_nom.getText().toString().trim(),m_prenom.getText().toString().trim(),m_age.getText().toString().trim(),m_pseudo.getText().toString().trim());

                                            try{
                                                inserer_info(u);

                                                Toast.makeText(Inscription.this, "INSCRIPTION REUSSITE",
                                                        Toast.LENGTH_SHORT).show();

                                                Intent intent = new Intent(Inscription.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }catch (Exception e){
                                                Toast.makeText(Inscription.this, "ERREUR LORS DE L'INSCRIPTION",
                                                        Toast.LENGTH_LONG).show();
                                                e.printStackTrace();
                                            }



                                        } else {
                                            try
                                            {
                                                throw task.getException();
                                            }
                                            catch (FirebaseAuthWeakPasswordException weakPassword)
                                            {
                                                Log.d(TAG, "onComplete: weak_password");
                                                Toast.makeText(Inscription.this, "Mot passe est de moins de 6 caracteres",
                                                        Toast.LENGTH_SHORT).show();

                                            }
                                            catch (FirebaseAuthInvalidCredentialsException malformedEmail)
                                            {
                                                Log.d(TAG, "onComplete: malformed_email");
                                                Toast.makeText(Inscription.this, "Adresse mail non valide",
                                                        Toast.LENGTH_SHORT).show();

                                            }
                                            catch (FirebaseAuthUserCollisionException existEmail)
                                            {
                                                Log.d(TAG, "onComplete: exist_email");
                                                Toast.makeText(Inscription.this, "Adresse mail est deja utilisée",
                                                        Toast.LENGTH_SHORT).show();

                                            }
                                            catch (Exception e)
                                            {
                                                Log.d(TAG, "onComplete: " + e.getMessage());
                                            }

                                        }

                                    }
                                });
                } catch (NumberFormatException e) {
                    Toast.makeText(Inscription.this, "Entrez un age valide",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void refrechButton(){
        m_btn.setEnabled( !m_mail.getText().toString().trim().isEmpty()
                        && !m_nom.getText().toString().trim().isEmpty()
                        && !m_prenom.getText().toString().trim().isEmpty()
                        && !m_age.getText().toString().trim().isEmpty()
                        && !m_pseudo.getText().toString().trim().isEmpty()
                        && !m_pass.getText().toString().isEmpty()
        );
    }

    private void inserer_info(User u){

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser userFirebase = mAuth.getCurrentUser();

        String  userId =  userFirebase.getUid();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("users").child(userId).setValue(u);

        Map<String, Object> hm_traite = u.convert_map();
        Map<String, Object> childUpdates = new HashMap<>();

        childUpdates.put("/users/" + userId, hm_traite);
        mDatabase.updateChildren(childUpdates);

    }
}
