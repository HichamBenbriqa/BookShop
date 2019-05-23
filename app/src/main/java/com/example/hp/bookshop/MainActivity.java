package com.example.hp.bookshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Catalogue()).commit();
            navigationView.setCheckedItem(R.id.catologue);
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.catologue:
                Intent intent_cat = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent_cat);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Catalogue()).commit();
                break;
            case R.id.panier:
               // Intent intent_cat1 = new Intent(MainActivity.this, Panier.class);
               // startActivity(intent_cat1);
                break;
            case R.id.profil:
               // Intent intent_cat2 = new Intent(MainActivity.this, Profile.class);
                //startActivity(intent_cat2);
                break;
            case R.id.settings:
                //FirebaseAuth fAuth = FirebaseAuth.getInstance();
                //fAuth.signOut();
                //startActivity(new Intent(MainActivity.this, Login.class));
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
