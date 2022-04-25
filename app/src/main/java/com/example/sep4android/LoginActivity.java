package com.example.sep4android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;


public class LoginActivity extends AppCompatActivity {
    EditText UsernameField;
    EditText PasswordField;
    Button loginButton;
    Button toRegisterButton;
    UserViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Login Activity test");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        findViews();
        setListenersToButtons();

        if (SaveSharedPreference.getStatus(this)) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    private void setListenersToButtons() {
        loginButton.setOnClickListener(
                view -> {
                    viewModel.getUser().observe(this, listObjects -> checkCredentials(listObjects));
                    viewModel.getUserFromRepo();
                }
        );
        toRegisterButton.setOnClickListener(
                view -> Navigation.findNavController(view).navigate(R.id.action_Login_to_Register));
    }

    private void findViews() {
        UsernameField = findViewById(R.id.UsernameField);
        PasswordField = findViewById(R.id.PasswordField);
        loginButton = findViewById(R.id.LoginButton);
        toRegisterButton = findViewById(R.id.toRegisterView);
    }


    private void checkCredentials(UserObject userObject) {
        if (userObject == null) {
            Toast.makeText(this, "Wrong username or password", Toast.LENGTH_SHORT).show();
        } else {
            SaveSharedPreference.setUser(this, "Robot", 1, true);

            if (SaveSharedPreference.getStatus(this)) {
                System.out.println("Valid cred");
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
