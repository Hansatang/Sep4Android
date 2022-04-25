package com.example.sep4android;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    EditText registerUsernameField;
    EditText registerPasswordField;
    EditText registerRepeatPasswordField;
    UserViewModel viewModel;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Register Activity test");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        findViews();
        setListenersToButtons();

    }

    private void findViews() {
        registerUsernameField = findViewById(R.id.registerUsernameField);
        registerPasswordField = findViewById(R.id.registerPasswordField);
        registerRepeatPasswordField = findViewById(R.id.registerRepeatPaswordField);
        registerButton = findViewById(R.id.RegisterButton);
    }

    private void setListenersToButtons() {
        registerButton.setOnClickListener(
                view -> {
                    if (registerUsernameField.getText().toString().equals("Name")
                    ) {
                        viewModel.addUserToDatabase(registerUsernameField.getText().toString(), registerPasswordField.getText().toString());
                        startActivity(new Intent(this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}
