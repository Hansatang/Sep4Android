package com.example.sep4android.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.sep4android.MainActivity;
import com.example.sep4android.R;
import com.example.sep4android.ViewModels.UserViewModel;
import com.firebase.ui.auth.AuthUI;

public class SettingsFragment extends Fragment {
    EditText oldPassword;
    EditText newPassword;
    EditText repeatNewPass;
    UserViewModel viewModel;
    Button savePasswordButton;
    Button deleteDataButton;
    Button deleteAccountButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("Settings page");
        View view = inflater.inflate(R.layout.settings_layout, container, false);
        findViews(view);
        setListenersToButtons();
        return view;
    }

    private void findViews(View view) {
        oldPassword = view.findViewById(R.id.oldPass);
        newPassword = view.findViewById(R.id.newPass);
        repeatNewPass = view.findViewById(R.id.repeatNewPass);
        savePasswordButton = view.findViewById(R.id.savePassButton);
        deleteAccountButton = view.findViewById(R.id.deleteAccountButton);
        deleteDataButton = view.findViewById(R.id.deleteDataButton);
    }

    private void setListenersToButtons() {
        savePasswordButton.setOnClickListener(
                view -> {
                    if (!oldPassword.getText().toString().equals("") &&
                            newPassword.getText().toString().equals(repeatNewPass.getText().toString())) {
                        viewModel.changePassword(repeatNewPass.getText().toString());
                        AuthUI.getInstance().signOut(getContext()).addOnCompleteListener(task -> {
                            goToMainActivity();
                        });
                    } else
                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
        );
        deleteDataButton.setOnClickListener(
                view -> {
                    //viewModel.deleteRoomData();
                }
        );
        deleteAccountButton.setOnClickListener(
                view -> {
                    viewModel.deleteAccount();
                }
        );

    }

    private void goToMainActivity() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }
}