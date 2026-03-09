package kh.edu.rupp.carrentapp.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import SessionManager.SessionManager;
import kh.edu.rupp.carrentapp.R;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import services.ApiKey;
import services.SupabaseApi;

public class HomeFragment extends Fragment {

    TextView helloTextView;
    SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        helloTextView = view.findViewById(R.id.helloTextView);

        initSessionManager();
        showUserName();
    }

    @SuppressLint("SetTextI18n")
    private void showUserName(){
        SharedPreferences prefs = requireContext().getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String name = prefs.getString("FULL_NAME", "Anonymous");
        helloTextView.setText("Welcome " + name + " !");
    }

    private void initSessionManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiKey.PROJECT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofit.create(SupabaseApi.class);
        sessionManager = new SessionManager(requireContext());
    }
}