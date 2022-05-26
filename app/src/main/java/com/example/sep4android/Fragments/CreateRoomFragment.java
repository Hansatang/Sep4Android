package com.example.sep4android.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.sep4android.R;
import com.example.sep4android.ViewModels.RoomViewModel;
import com.google.firebase.auth.FirebaseAuth;

//Fragment for Creating new Rooms
public class CreateRoomFragment extends Fragment {
  private final String TAG = "CreateRoomFragment";
  private View view;
  private RoomViewModel viewModel;
  private Button createRoomButton;
  private EditText deviceText;
  private EditText nameText;

  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    Log.i(TAG,"Create CreateRoom View");
    view = inflater.inflate(R.layout.create_room_layout, container, false);
    viewModel = new ViewModelProvider(requireActivity()).get(RoomViewModel.class);
    findViews();
    setListenersToButtons();
    viewModel.getCreationResult().observe(getViewLifecycleOwner(), this::NavigateToMainFragment);
    return view;
  }

  private void setListenersToButtons() {
    createRoomButton.setOnClickListener(view -> {
          viewModel.addRoomToDatabase(deviceText.getText().toString(), nameText.getText().toString(),
              FirebaseAuth.getInstance().getCurrentUser().getUid());
        }
    );
  }


  private void NavigateToMainFragment(Object creationResult) {
    Boolean bool = (Boolean) creationResult;
    Log.i(TAG,"Checking the room creation result: "+bool);
    if (bool) {
      NavController navController = Navigation.findNavController(getActivity(), R.id.fragmentContainerView);
      navController.popBackStack();
      viewModel.setResult();
    }
  }


  private void findViews() {
    createRoomButton = view.findViewById(R.id.CreateRoom);
    deviceText = view.findViewById(R.id.deviceNameText);
    nameText = view.findViewById(R.id.roomNameText);
  }
}
