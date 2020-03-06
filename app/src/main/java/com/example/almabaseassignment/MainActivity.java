package com.example.almabaseassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.almabaseassignment.Models.Repo;
import com.example.almabaseassignment.Retrofit.NetworkClient;
import com.example.almabaseassignment.Retrofit.RequestService;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    ArrayList<Repo> repo_response = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        final EditText editText_org=findViewById(R.id.organization);
        final EditText editText_repo=findViewById(R.id.no_of_repo);
        Button search=findViewById(R.id.search);

        Log.e("HELLO", "onCreate: "+editText_org.getText().toString());

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("HELLO", "onCreate: "+editText_org.getText().toString());

                final String org=editText_org.getText().toString();
                final String repo=editText_repo.getText().toString();

                if(org==null || org.isEmpty())
                    Toast.makeText(getApplicationContext(),"Enter Organization's Name",Toast.LENGTH_SHORT).show();
                else if(repo==null || repo.isEmpty())
                    Toast.makeText(getApplicationContext(),"Enter Repo Count",Toast.LENGTH_SHORT).show();

                else
                {
                    Retrofit retrofit = NetworkClient.getRetrofitClient();
                    final RequestService requestService=retrofit.create(RequestService.class);
                    Call<List<Repo>> call=requestService.requestGet(org);

                    call.enqueue(new Callback<List<Repo>>() {
                        @Override
                        public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                            recyclerView.setAdapter(null);
                            repo_response=new ArrayList<>(response.body());
                            Collections.sort(repo_response);
                            repo_response= new ArrayList<Repo>(repo_response.subList(0,Integer.parseInt(repo)));
                            recyclerView.setAdapter(new RecyclerViewAdapter(getApplicationContext(),repo_response));
                        }

                        @Override
                        public void onFailure(Call<List<Repo>> call, Throwable t) {
                            Log.e("Failure", "onFailure: "+t.getMessage());
                        }
                    });
                }

            }
        });

    }
}
