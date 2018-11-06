package com.example.zak.eatogheter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText m_login, m_pass;
    TextView m_signup;
    Button m_btn;

    private FirebaseAuth mAuth;
    String TAG="D";


  /*  @Override
    protected void onStart(){

        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_login=findViewById(R.id.activity_main_login_txt);
        m_pass=findViewById(R.id.activity_main_password_txt);
        m_signup=findViewById(R.id.link_signup);
        m_btn=findViewById(R.id.activity_main_cnx_btn);

        mAuth = FirebaseAuth.getInstance();

        m_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!m_login.getText().toString().equals("")&&!m_pass.getText().toString().equals("")){

                    signIn(m_login.getText().toString(),m_pass.getText().toString());

                }else{
                    Toast.makeText(MainActivity.this, "Veuillez remplir tous les champs SVP",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        m_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Inscription.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
      //  updateUI(currentUser);
    }

    private void signIn(String email, String password) {
        Log.d("TAG", "signIn:" + email);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // La c'est bon la connexion est établie
                            Log.d(TAG, "signInWithEmail:success");
                            //ici tu peut résuperer ton utilisateur dans une instance de FirebaseUser
                            FirebaseUser user = mAuth.getCurrentUser();

                            // la par exemple je vais vers une autre activité qui est celle du profil
                              Intent intent = new Intent(MainActivity.this, Navigation.class);
                              startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Adresse mail ou mot de passe incorrect",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
    private void updateUI(FirebaseUser user) {
        if (user != null) {

            Intent intent = new Intent(MainActivity.this, Navigation.class);
            startActivity(intent);

        }
    }

}
