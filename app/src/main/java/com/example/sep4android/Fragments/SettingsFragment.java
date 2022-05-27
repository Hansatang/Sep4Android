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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.sep4android.MainActivity;
import com.example.sep4android.R;
import com.example.sep4android.SignUpActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SettingsFragment extends Fragment {
  private final String TAG = "SettingsFragment";
  private EditText oldPassword;
  private EditText newPassword;
  private EditText repeatNewPass;
  private Button savePasswordButton;
  private Button deleteDataButton;
  private Button deleteAccountButton;
  private Button changeThemeButton;
  private View view;

  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    Log.i(TAG,"Create setting View");
    view = inflater.inflate(R.layout.settings_layout, container, false);
    findViews();
    setListenersToButtons();
    return view;
  }

  /**
   * assign all needed Views in this fragment
   */
  private void findViews() {
    oldPassword = view.findViewById(R.id.oldPass);
    newPassword = view.findViewById(R.id.newPass);
    repeatNewPass = view.findViewById(R.id.repeatNewPass);
    savePasswordButton = view.findViewById(R.id.savePassButton);
    deleteAccountButton = view.findViewById(R.id.deleteAccountButton);
    deleteDataButton = view.findViewById(R.id.deleteDataButton);
    changeThemeButton = view.findViewById(R.id.changeThemeButton);
  }

  /**
   * add functionality to existing in this view buttons
   */
  private void setListenersToButtons() {
    savePasswordButton.setOnClickListener(
        view -> {
          if (!oldPassword.getText().toString().equals("") &&
              newPassword.getText().toString().equals(repeatNewPass.getText().toString())) {
            String regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{5,}$";
            System.out.println(newPassword.getText().toString());
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(newPassword.getText().toString());
            if (m.matches()) {
              FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
              user.updatePassword(newPassword.getText().toString()).addOnCompleteListener(task -> goToMainView());
            } else {
              System.out.println("1234");
              Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
          }
        }
    );
    changeThemeButton.setOnClickListener(
        view -> changeTheme()
    );
    deleteAccountButton.setOnClickListener(
        view -> FirebaseAuth.getInstance().getCurrentUser().delete().addOnCompleteListener(task -> goToSignInActivity())
    );
  }

  /**
   * navigate to
   */
  private void goToMainActivity() {
    Intent intent = new Intent(getActivity(), MainActivity.class);
    startActivity(intent);
  }

  /**
   * change application theme from light to dark and from dark to light
   */
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

  private void goToSignInActivity() {
    Intent intent = new Intent(getActivity(), SignUpActivity.class);
    startActivity(intent);
  }

  private void goToMainView() {
    NavController navController = Navigation.findNavController(getActivity(), R.id.fragmentContainerView);
    navController.popBackStack();
  }
}
