package com.example.almabaseassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.almabaseassignment.Models.Commit;
import com.example.almabaseassignment.Models.Repo;
import com.example.almabaseassignment.Retrofit.NetworkClient;
import com.example.almabaseassignment.Retrofit.RequestService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Committee extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee);

        Intent intent=getIntent();
        String url = intent.getStringExtra("link");

        url=url.substring(23);

        Log.e("hello", "onCreate: "+url);
        String[] paths=url.split("/");

        Retrofit retrofit = NetworkClient.getRetrofitClient();
        final RequestService requestService=retrofit.create(RequestService.class);
        Call<List<Commit>> call=requestService.requestCommit(paths[1],paths[2]);

        call.enqueue(new Callback<List<Commit>>() {
            @Override
            public void onResponse(Call<List<Commit>> call, Response<List<Commit>> response) {
                Log.e("SUCCESS", "onResponse: "+response.body().get(0).getLogin());
            }

            @Override
            public void onFailure(Call<List<Commit>> call, Throwable t) {
                Log.e("HEYY", "onFailure: "+t.getMessage());
            }
        });
    }
}
