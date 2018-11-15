package com.example.zak.eatogheter;


import android.annotation.SuppressLint;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import java.util.List;

public class Navigation extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener{


    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    android.support.v4.app.FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);



        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();

        Recherche res = new Recherche();


       fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.dynamic_fragment_frame_layout, res).commit();
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        // 4 - Handle Navigation Item Click
        int id = item.getItemId();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        switch (id){
            case R.id.activity_main_drawer_news :
               Recherche recherche = new Recherche();
               fragmentManager.beginTransaction().replace(R.id.dynamic_fragment_frame_layout, recherche).commit();

                break;

            case R.id.activity_main_drawer_profile:
                Profil profil =new Profil();
                fragmentManager.beginTransaction().replace(R.id.dynamic_fragment_frame_layout, profil).commit();

                break;

            case R.id.activity_main_drawer_reservations:
                Reservation reservation =new Reservation();
                fragmentManager.beginTransaction().replace(R.id.dynamic_fragment_frame_layout, reservation).commit();

                break;

            case R.id.activity_main_drawer_mes_reservations:

                Mes_reservations mes_reservations= new Mes_reservations();
                fragmentManager.beginTransaction().replace(R.id.dynamic_fragment_frame_layout, mes_reservations).commit();

                break;

            default:
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }


    // ---------------------
    // CONFIGURATION
    // ---------------------

    // 1 - Configure Toolbar
    private void configureToolBar(){
        this.toolbar = findViewById(R.id.activity_navigation_toolbar);
        setSupportActionBar(toolbar);
    }

    // 2 - Configure Drawer Layout
    private void configureDrawerLayout(){
        this.drawerLayout =  findViewById(R.id.activity_navigation_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // 3 - Configure NavigationView
    private void configureNavigationView(){
        this.navigationView = findViewById(R.id.activity_navigation_nav_view);
        navigationView.setNavigationItemSelectedListener(Navigation.this);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed()
    {

        Log.d("HH","RETOUUUUUUUUUUR 1");
        drawerLayout.closeDrawers();
        List fragmentList = getSupportFragmentManager().getFragments();

        boolean handled = false;
        for(Object f : fragmentList) {
            if(f instanceof Base_fragment) {
                Log.d("HH","RETOUUUUUUUUUUR OUI");
                handled = ((Base_fragment)f).onBackPressed();

                if(handled) {
                    break;
                }
            }
        }
      /*  if(!handled) {
            Log.d("HH","RETOUUUUUUUUUUR NON");
            super.onBackPressed();
        }*/
    }
}
