package com.example.sep4android;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;


public class LoginActivity extends AppCompatActivity {
    EditText UsernameField;
    EditText PasswordField;
    Button loginButton;
    Button toRegisterButton;


    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    System.out.println("LOL");
                    goToMainActivity();
                }
                else
                    Toast.makeText(this, "SIGN IN CANCELLED", Toast.LENGTH_SHORT).show();
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder(StrictMode.getVmPolicy())
                .detectLeakedClosableObjects()
                .build());
        System.out.println("Login Activity test");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        findViews();
        setListenersToButtons();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            System.out.println("hej");
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    private void findViews() {
        UsernameField = findViewById(R.id.UsernameField);
        PasswordField = findViewById(R.id.PasswordField);
        loginButton = findViewById(R.id.LoginButton);
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
}
