package com.example.sep4android;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Arrays;
import java.util.List;


public class SignUpActivity extends AppCompatActivity {
  Button toRegisterButton;

  ActivityResultLauncher<Intent> loginResultLauncher = registerForActivityResult(
      new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
          FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
              Log.w("Token", "Fetching FCM registration token failed", task.getException());
              return;
            }
            // Get new FCM registration token
            String token = task.getResult();
            Log.w("Token", token, task.getException());
            goToMainActivity();
          });
        } else
          Toast.makeText(this, "SIGN IN CANCELLED", Toast.LENGTH_SHORT).show();
      });

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder(StrictMode.getVmPolicy())
        .detectLeakedClosableObjects().build());
    System.out.println("Login Activity test");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.login_layout);
    findViews();
    setListenersToButtons();
  }

  private void findViews() {
    toRegisterButton = findViewById(R.id.toRegisterView);
  }

  private void setListenersToButtons() {
    toRegisterButton.setOnClickListener(view -> signIn());
  }

  private void goToMainActivity() {
    startActivity(new Intent(this, MainActivity.class));
    finish();
  }

  public void signIn() {
    List<AuthUI.IdpConfig> providers = Arrays.asList(
        new AuthUI.IdpConfig.EmailBuilder().build(),
        new AuthUI.IdpConfig.GoogleBuilder().build());

    Intent signInIntent = AuthUI.getInstance()
        .createSignInIntentBuilder()
        .setAvailableProviders(providers)
        .setLogo(R.drawable.ic_launcher_foreground)
        .build();

    loginResultLauncher.launch(signInIntent);
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