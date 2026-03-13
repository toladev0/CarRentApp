package kh.edu.rupp.carrentapp.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import SessionManager.SessionManager;
import adapter.CarAdapter;
import kh.edu.rupp.carrentapp.R;
import models.CarRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import services.ApiKey;
import services.SupabaseApi;

public class HomeFragment extends Fragment {

    TextView helloTextView;
    RecyclerView recyclerView;

    private CarAdapter carAdapter;
    private List<CarRequest> cars = new ArrayList<>();

    SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        helloTextView = view.findViewById(R.id.helloTextView);
        recyclerView = view.findViewById(R.id.recyclerView);

        showUserName();

        carAdapter = new CarAdapter(cars);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(carAdapter);

        initSessionManager();
    }

    @SuppressLint("SetTextI18n")
    private void showUserName(){
        SharedPreferences prefs = requireContext()
                .getSharedPreferences("MyAppPrefs", MODE_PRIVATE);

        String name = prefs.getString("FULL_NAME", "Anonymous");
        helloTextView.setText("Welcome " + name + " !");
    }

    private void initSessionManager() {

        sessionManager = new SessionManager(requireContext());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiKey.PROJECT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SupabaseApi api = retrofit.create(SupabaseApi.class);

        Call<List<CarRequest>> call = api.getCars();

        call.enqueue(new Callback<List<CarRequest>>() {
            @Override
            public void onResponse(Call<List<CarRequest>> call,
                                   Response<List<CarRequest>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    cars.clear();
                    cars.addAll(response.body());
                    carAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<CarRequest>> call, Throwable t) {
                Toast.makeText(getContext(),
                        "Failed: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}