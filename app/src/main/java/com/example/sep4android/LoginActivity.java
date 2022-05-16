package com.example.sep4android;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;

import java.util.Arrays;
import java.util.List;


public class LoginActivity extends AppCompatActivity {
    EditText UsernameField;
    EditText PasswordField;
    Button toRegisterButton;

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    goToMainActivity();
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
        UsernameField = findViewById(R.id.UsernameField);
        PasswordField = findViewById(R.id.PasswordField);
        toRegisterButton = findViewById(R.id.toRegisterView);
    }

    private void setListenersToButtons() {
        toRegisterButton.setOnClickListener(
                view -> signIn());
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

        activityResultLauncher.launch(signInIntent);
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
