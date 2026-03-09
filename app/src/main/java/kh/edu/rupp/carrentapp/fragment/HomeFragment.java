package kh.edu.rupp.carrentapp.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import SessionManager.SessionManager;
import kh.edu.rupp.carrentapp.R;

public class HomeFragment extends Fragment {

    private TextView helloTextView;
    private SessionManager sessionManager;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        helloTextView = view.findViewById(R.id.helloTextView);
        Button logoutButton = view.findViewById(R.id.logoutButton);

        sessionManager = new SessionManager(requireContext());

        showUserName();

        logoutButton.setOnClickListener(v -> sessionManager.logout());
    }

    @SuppressLint("SetTextI18n")
    private void showUserName() {
        SharedPreferences prefs = requireContext().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String name = prefs.getString("FULL_NAME", "Anonymous");
        helloTextView.setText("Welcome " + name + " !");
    }
}
