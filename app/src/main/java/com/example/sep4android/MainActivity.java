package com.example.sep4android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.sep4android.ViewModels.TokenViewModel;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Activity class for the main page
 */
public class MainActivity extends AppCompatActivity {
  private Toolbar toolbar;
  private DrawerLayout drawerLayout;
  private NavController navController;
  private NavigationView navigationView;
  private TextView UsernameInNavBar;
  private TextView EmailInNavBar;
  private AppBarConfiguration mAppBarConfiguration;
  private TokenViewModel tokenViewModel;
  private FirebaseUser user;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    System.out.println("Main test");
    super.onCreate(savedInstanceState);
    user = FirebaseAuth.getInstance().getCurrentUser();
    if (user == null) {
      startActivity(new Intent(this, SignUpActivity.class));
      finish();
    } else {
      setContentView(R.layout.activity_main);
      tokenViewModel = new ViewModelProvider(this).get(TokenViewModel.class);
      tokenViewModel.getDeletionResultLiveData().observe(this, result -> LogOut(result));
      findViews();
      setSupportActionBar(toolbar);
      NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
      NavigationUI.setupWithNavController(navigationView, navController);
      checkUser();
      checkThemePreferences();
    }
  }

  private void LogOut(Integer result) {
    if (result == 200) {
      FirebaseMessaging.getInstance().deleteToken().addOnCompleteListener(deleteTokenTask -> {
        if (!deleteTokenTask.isSuccessful()) {
          Log.w("Token", "Fetching FCM registration token failed", deleteTokenTask.getException());
          return;
        }
        AuthUI.getInstance().signOut(this).addOnCompleteListener(logOutTask -> {
          if (!logOutTask.isSuccessful()) {
            Log.w("Token", "Fetching FCM registration token failed", logOutTask.getException());
            return;
          }
          tokenViewModel.setResult();
          startActivity(new Intent(MainActivity.this, SignUpActivity.class));
          finish();
        });
      });
    }
  }

  private void checkThemePreferences() {
    SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
    final boolean isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false);
    if (isDarkModeOn) {
      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    } else {
      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
  }


  private void checkUser() {
    if (user != null) {
      UsernameInNavBar.setText(user.getEmail());
      EmailInNavBar.setText(user.getDisplayName());
    }
  }

  /**
   * Assigns needed Views in this activity
   */
  private void findViews() {
    toolbar = findViewById(R.id.topAppBar);
    drawerLayout = findViewById(R.id.drawer_layout);
    navigationView = findViewById(R.id.nav_view);
    View headerContainer = navigationView.getHeaderView(0);
    UsernameInNavBar = headerContainer.findViewById(R.id.nav_header_title);
    EmailInNavBar = headerContainer.findViewById(R.id.nav_header_subtitle);
    navController = Navigation.findNavController(this, R.id.fragmentContainerView);
    mAppBarConfiguration = new AppBarConfiguration.Builder(
        R.id.Home, R.id.Archive, R.id.HumidityThreshold, R.id.TemperatureThreshold, R.id.Statistics)
        .setOpenableLayout(drawerLayout).build();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.top_app_bar, menu);
    return true;
  }

  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == findViewById(R.id.LogOutItem).getId()) {
      onLogOut();
    } else if (item.getItemId() == toolbar.getMenu().findItem(R.id.settings).getItemId()) {
      navController.navigate(R.id.Settings);
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public boolean onSupportNavigateUp() {
    return NavigationUI.navigateUp(navController, mAppBarConfiguration)
        || super.onSupportNavigateUp();
  }

  /**
   * Logs out the user
   */
  private void onLogOut() {
    tokenViewModel.deleteToken(user.getUid());
  }


  @Override
  protected void onResume() {
    super.onResume();
    AppStatusChecker.activityResumed();
  }

  @Override
  protected void onPause() {
    super.onPause();
    AppStatusChecker.activityPaused();
  }
}