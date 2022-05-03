package com.example.sep4android.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.sep4android.MainActivity;
import com.example.sep4android.R;
import com.example.sep4android.ViewModels.RoomViewModel;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class CreateRoomFragment extends Fragment {
    View view;
    RoomViewModel viewModel;
    Button createRoomButton;
    EditText editText;
    NavigationView navigationView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("Create Room View");
        view = inflater.inflate(R.layout.create_room_layout, container, false);
        findViews(view);
        setListenersToButtons();
        viewModel = new ViewModelProvider(requireActivity()).get(RoomViewModel.class);

        return view;
    }

    private void setListenersToButtons() {
        createRoomButton.setOnClickListener(view -> {
                    //   viewModel.addRoomToDatabase(editText.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getUid());
                    NavController navController = Navigation.findNavController(getActivity(), R.id.fragmentContainerView);
                    navController.popBackStack();

                }
        );
    }

    private void findViews(View view) {
        createRoomButton = view.findViewById(R.id.CreateRoom);
        editText = view.findViewById(R.id.tv0005);

    }
}
