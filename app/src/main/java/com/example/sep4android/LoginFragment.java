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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

public class LoginFragment extends Fragment {
    EditText UsernameField;
    EditText PasswordField;
    Button loginButton;
    Button toRegisterButton;
    UserViewModel viewModel;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("LoginView");
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        }
        View view = inflater.inflate(R.layout.login_layout, container, false);
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        findViews(view);
        setListenersToButtons();

        return view;
    }

    private void setListenersToButtons() {
        loginButton.setOnClickListener(
                view -> {
                    viewModel.getUser(UsernameField.getText().toString()).observe(getViewLifecycleOwner(), listObjects -> System.out.println(listObjects));
                    if (UsernameField.getText().toString().equals("Name")
                        //     && PasswordField.getText().toString().equals("1")
                    ) {
                        Navigation.findNavController(view).navigate(R.id.action_Login_to_Home);
                    } else {
                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        toRegisterButton.setOnClickListener(
                view -> Navigation.findNavController(view).navigate(R.id.action_Login_to_Register));
    }

    private void findViews(View view) {
        UsernameField = view.findViewById(R.id.UsernameField);
        PasswordField = view.findViewById(R.id.PasswordField);
        loginButton = view.findViewById(R.id.LoginButton);
        toRegisterButton = view.findViewById(R.id.toRegisterView);
    }
}
