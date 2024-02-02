package com.example.myfitnesshub;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.myfitnesshub.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class Home_page extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView;
    FragmentManager fragmentManager;
    Toolbar toolbar;
    BottomNavigationView bottom_view;

    private final OnBackPressedDispatcher onBackPressedDispatcher = getOnBackPressedDispatcher();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        replace_fragment(new HomeFragment());

        bottom_view = findViewById(R.id.bottomNavigationView);

        bottom_view.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.home:
                    replace_fragment(new HomeFragment());
                    break;
                case R.id.exercise:
                    replace_fragment(new ExericseFragment());
                    break;
                case R.id.diet:
                    replace_fragment(new DietFragment());
                    break;
                case R.id.blog:
                    replace_fragment(new BlogFragment());
                    break;
                case R.id.shopping:
                    replace_fragment(new ShoppingFragment());
                    break;
            }
            return true;
        });

        onBackPressedDispatcher.addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // do not delete
            }
        });


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.neon));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_drawer);
        navigationView.setNavigationItemSelectedListener(this);


    }

    private void replace_fragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.nav_bmi){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new BMI_Fragment()).commit();
        } else if (itemId == R.id.nav_about_us){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new About_usFragment()).commit();
        } else if (itemId == R.id.nav_contact_us){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new contact_usFragment()).commit();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }




}