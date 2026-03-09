package kh.edu.rupp.carrentapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import SessionManager.SessionManager;
import kh.edu.rupp.carrentapp.R;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import services.ApiKey;
import services.SupabaseApi;

public class AccountFragment extends Fragment {

    Button logoutButton;
    SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        logoutButton = view.findViewById(R.id.logoutButton);

        initSessionManager();
        setLogoutButton();
    }

    private void setLogoutButton(){
        logoutButton.setOnClickListener(view -> sessionManager.logout());
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