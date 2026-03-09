package kh.edu.rupp.carrentapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import kh.edu.rupp.carrentapp.fragment.AccountFragment;
import kh.edu.rupp.carrentapp.fragment.CarFragment;
import kh.edu.rupp.carrentapp.fragment.FavFragment;
import kh.edu.rupp.carrentapp.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set default fragment
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                loadFragment(new HomeFragment());
                return true;
            } else if (itemId == R.id.nav_car) {
                loadFragment(new CarFragment());
                return true;
            } else if (itemId == R.id.nav_fav) {
                loadFragment(new FavFragment());
                return true;
            } else if (itemId == R.id.nav_profile) {
                loadFragment(new AccountFragment());
                return true;
            }
            return false;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
