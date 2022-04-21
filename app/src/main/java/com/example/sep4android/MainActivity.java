package com.example.sep4android;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavController navController;
    NavigationView navigationView;
    TextView UsernameInNavBar;

    AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Main test");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();

        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        NavInflater navInflater = navController.getNavInflater();
        NavGraph graph = navInflater.inflate(R.navigation.nav_graph);
        if (SaveSharedPreference.getStatus(MainActivity.this)) {
            graph.setStartDestination(R.id.Home);
            UsernameInNavBar.setText(SaveSharedPreference.getUserName(MainActivity.this));
            System.out.println("YAY");
        } else {
            graph.setStartDestination(R.id.Login);
            System.out.println("not YAY");
        }
        navController.setGraph(graph);
    }

    private void findViews() {
        toolbar = findViewById(R.id.topAppBar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        View headerContainer = navigationView.getHeaderView(0);
        UsernameInNavBar = headerContainer.findViewById(R.id.nav_header_title);
        navController = Navigation.findNavController(this, R.id.fragmentContainerView);
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.Home, R.id.Login)
                .setOpenableLayout(drawerLayout)
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == findViewById(R.id.NavigationBut).getId()) {
            SaveSharedPreference.logOutUser(MainActivity.this);
            System.out.println(SaveSharedPreference.getUser(MainActivity.this));
            UsernameInNavBar.setText("");
            navController.navigate(R.id.action_Home_to_Login);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}