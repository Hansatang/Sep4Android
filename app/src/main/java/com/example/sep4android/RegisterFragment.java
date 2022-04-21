package com.example.sep4android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class RegisterFragment extends Fragment {
    EditText registerUsernameField;
    EditText registerPasswordField;
    EditText registerRepeatPasswordField;
    UserViewModel viewModel;
    Button registerButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("RegisterView");
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        }

        View view = inflater.inflate(R.layout.register_layout, container, false);

        findViews(view);
        setListenersToButtons();


        return view;
    }

    private void findViews(View view) {
        registerUsernameField = view.findViewById(R.id.registerUsernameField);
        registerPasswordField = view.findViewById(R.id.registerPasswordField);
        registerRepeatPasswordField = view.findViewById(R.id.registerRepeatPaswordField);
        registerButton = view.findViewById(R.id.RegisterButton);
    }

    private void setListenersToButtons() {
        registerButton.setOnClickListener(
                view -> {
                    if (registerUsernameField.getText().toString().equals("Name")
                        //     && PasswordField.getText().toString().equals("1")

                    ) {
                        Navigation.findNavController(view).navigate(R.id.action_Register_to_Login);
                        viewModel.addUserToDatabase();
                    } else {
                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}
