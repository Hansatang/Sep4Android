package com.example.sep4android;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
  Toolbar toolbar;
  DrawerLayout drawerLayout;
  NavController navController;
  NavigationView navigationView;
  TextView UsernameInNavBar;
  TextView EmailInNavBar;
  private FirebaseAuth mAuth;

  AppBarConfiguration mAppBarConfiguration;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    System.out.println("Main test");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      // Create channel to show notifications.
      String channelId = getString(R.string.default_notification_channel_id);
      String channelName = getString(R.string.default_notification_channel_name);
      NotificationManager notificationManager =
          getSystemService(NotificationManager.class);
      notificationManager.createNotificationChannel(new NotificationChannel(channelId,
          channelName, NotificationManager.IMPORTANCE_HIGH));
    }

    findViews();
    setSupportActionBar(toolbar);
    NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
    NavigationUI.setupWithNavController(navigationView, navController);

    mAuth = FirebaseAuth.getInstance();

    if (FirebaseAuth.getInstance().getCurrentUser() == null) {
      startActivity(new Intent(this, LoginActivity.class));
      finish();
    }
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    if (user != null) {
      user.getIdToken(false).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
        @Override
        public void onSuccess(GetTokenResult result) {
          if (result.getSignInProvider().equals("google.com")) {
            System.out.println("User is signed in with Google");
          } else {
            System.out.println("User is signed in with Email");
          }
        }
      });
      String email = user.getEmail();
      String username = user.getDisplayName();
      UsernameInNavBar.setText(email);
      EmailInNavBar.setText(username);
    }

    FirebaseMessaging.getInstance().getToken()
        .addOnCompleteListener(new OnCompleteListener<String>() {
          @Override
          public void onComplete(@NonNull Task<String> task) {
            if (!task.isSuccessful()) {
              Log.w("Token", "Fetching FCM registration token failed", task.getException());
              return;
            }
            // Get new FCM registration token
            String token = task.getResult();
            Log.w("Token", token, task.getException());
            Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
          }
        });


  }

  private void findViews() {
    toolbar = findViewById(R.id.topAppBar);
    drawerLayout = findViewById(R.id.drawer_layout);
    navigationView = findViewById(R.id.nav_view);
    View headerContainer = navigationView.getHeaderView(0);
    UsernameInNavBar = headerContainer.findViewById(R.id.nav_header_title);
    EmailInNavBar = headerContainer.findViewById(R.id.nav_header_subtitle);
    navController = Navigation.findNavController(this, R.id.fragmentContainerView);
    mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.Home, R.id.Test, R.id.Measurements)
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
    }
    if (item.getItemId() == toolbar.getMenu().findItem(R.id.more).getItemId()) {
      NavigationUI.onNavDestinationSelected(navigationView.getMenu().getItem(1),
          navController);
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public boolean onSupportNavigateUp() {
    return NavigationUI.navigateUp(navController, mAppBarConfiguration)
        || super.onSupportNavigateUp();
  }

  private void onLogOut() {
    AuthUI.getInstance().signOut(this).addOnCompleteListener(task -> {
      startActivity(new Intent(MainActivity.this, LoginActivity.class));
      finish();
    });
  }
}