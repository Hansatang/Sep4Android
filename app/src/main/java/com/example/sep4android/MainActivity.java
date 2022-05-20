package com.example.sep4android;

import android.app.NotificationChannel;
import android.app.NotificationManager;
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

import com.example.sep4android.ViewModels.RoomViewModel;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
  Toolbar toolbar;
  DrawerLayout drawerLayout;
  NavController navController;
  NavigationView navigationView;
  TextView UsernameInNavBar;
  TextView EmailInNavBar;
  AppBarConfiguration mAppBarConfiguration;
  RoomViewModel roomViewModel;
  FirebaseUser user;

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
      createNotificationChannel();
      roomViewModel = new ViewModelProvider(this).get(RoomViewModel.class);
      findViews();
      setSupportActionBar(toolbar);
      NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
      NavigationUI.setupWithNavController(navigationView, navController);
      checkUser();
      checkThemePreferences();

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
      user.getIdToken(false).addOnSuccessListener(result -> {
        if (result.getSignInProvider().equals("google.com")) {
          System.out.println("User is signed in with Google");
        } else {
          System.out.println("User is signed in with Email");
        }
      });
      UsernameInNavBar.setText(user.getEmail());
      EmailInNavBar.setText(user.getDisplayName());
    }
  }

  private void createNotificationChannel() {
    String channelId = getString(R.string.default_notification_channel_id);
    String channelName = getString(R.string.default_notification_channel_name);
    NotificationManager notificationManager = getSystemService(NotificationManager.class);
    notificationManager.createNotificationChannel(new NotificationChannel(channelId,
        channelName, NotificationManager.IMPORTANCE_HIGH));

  }

  private void findViews() {
    toolbar = findViewById(R.id.topAppBar);
    drawerLayout = findViewById(R.id.drawer_layout);
    navigationView = findViewById(R.id.nav_view);
    View headerContainer = navigationView.getHeaderView(0);
    UsernameInNavBar = headerContainer.findViewById(R.id.nav_header_title);
    EmailInNavBar = headerContainer.findViewById(R.id.nav_header_subtitle);
    navController = Navigation.findNavController(this, R.id.fragmentContainerView);
    mAppBarConfiguration = new AppBarConfiguration.Builder(
        R.id.Home, R.id.Test, R.id.Measurements)
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
    } else if (item.getItemId() == toolbar.getMenu().findItem(R.id.more).getItemId()) {
      NavigationUI.onNavDestinationSelected(navigationView.getMenu().getItem(1), navController);
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public boolean onSupportNavigateUp() {
    return NavigationUI.navigateUp(navController, mAppBarConfiguration)
        || super.onSupportNavigateUp();
  }

  private void onLogOut() {
    roomViewModel.deleteToken(user.getUid());
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
        startActivity(new Intent(MainActivity.this, SignUpActivity.class));
        finish();
      });
    });
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