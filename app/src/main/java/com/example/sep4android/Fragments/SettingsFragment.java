package com.example.sep4android.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.sep4android.MainActivity;
import com.example.sep4android.R;
import com.example.sep4android.ViewModels.UserViewModel;
import com.firebase.ui.auth.AuthUI;

public class SettingsFragment extends Fragment {
  private final String TAG = "SettingsFragment";
  private EditText oldPassword;
  private EditText newPassword;
  private EditText repeatNewPass;
  private UserViewModel viewModel;
  private Button savePasswordButton;
  private Button deleteDataButton;
  private Button deleteAccountButton;
  private Button changeThemeButton;
  private View view;

  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    Log.i(TAG,"Create Settings View");
    view = inflater.inflate(R.layout.settings_layout, container, false);
    findViews();
    setListenersToButtons();
    return view;
  }

  private void findViews() {
    oldPassword = view.findViewById(R.id.oldPass);
    newPassword = view.findViewById(R.id.newPass);
    repeatNewPass = view.findViewById(R.id.repeatNewPass);
    savePasswordButton = view.findViewById(R.id.savePassButton);
    deleteAccountButton = view.findViewById(R.id.deleteAccountButton);
    deleteDataButton = view.findViewById(R.id.deleteDataButton);
    changeThemeButton = view.findViewById(R.id.changeThemeButton);
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
    changeThemeButton.setOnClickListener(
        view -> changeTheme()
    );
    deleteAccountButton.setOnClickListener(
        view -> viewModel.deleteAccount()
    );
  }

  private void goToMainActivity() {
    Intent intent = new Intent(getActivity(), MainActivity.class);
    startActivity(intent);
  }

  private void changeTheme() {
    SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
    final SharedPreferences.Editor editor = sharedPreferences.edit();
    final boolean isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false);
    if (isDarkModeOn) {
      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
      editor.putBoolean("isDarkModeOn", false);
    } else {
      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
      editor.putBoolean("isDarkModeOn", true);
    }
    editor.apply();
  }
}
