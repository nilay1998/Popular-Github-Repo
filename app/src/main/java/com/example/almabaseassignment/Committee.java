package com.example.almabaseassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

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
    ArrayList<Commit> commit_response = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee);

        Intent intent=getIntent();
        String url = intent.getStringExtra("link");
        setTitle(intent.getStringExtra("name"));

        final ProgressBar progressBar=findViewById(R.id.prog);
        progressBar.setVisibility(View.INVISIBLE);
        url=url.substring(23);
        final String[] paths=url.split("/");
        final RecyclerView recyclerView=findViewById(R.id.recyclerView_commit);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        final EditText editText_commit=findViewById(R.id.edittext_commit);
        Button button=findViewById(R.id.search_commit);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(editText_commit.getText().toString()==null || editText_commit.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(),"Enter Commiter Count",Toast.LENGTH_SHORT).show();
                else
                {
                    progressBar.setVisibility(View.VISIBLE);
                    Retrofit retrofit = NetworkClient.getRetrofitClient();
                    final RequestService requestService=retrofit.create(RequestService.class);
                    Call<List<Commit>> call=requestService.requestCommit(paths[1],paths[2]);

                    call.enqueue(new Callback<List<Commit>>() {
                        @Override
                        public void onResponse(Call<List<Commit>> call, Response<List<Commit>> response) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Log.e("SUCCESS", "onResponse: "+response.body().get(0).getLogin());
                            commit_response=new ArrayList<>(response.body());
                            if(Integer.parseInt(editText_commit.getText().toString())<commit_response.size())
                                commit_response=new ArrayList<>(commit_response.subList(0,Integer.parseInt(editText_commit.getText().toString())));
                            recyclerView.setAdapter(new CommitViewAdapter(getApplicationContext(),commit_response));
                        }

                        @Override
                        public void onFailure(Call<List<Commit>> call, Throwable t) {
                            Log.e("HEYY", "onFailure: "+t.getMessage());
                        }
                    });
                }


            }
        });
    }
}
