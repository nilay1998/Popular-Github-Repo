package com.example.almabaseassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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

import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {
    ArrayList<Repo> repo_response = new ArrayList<>();
    LinearLayout home;
    RelativeLayout relativeLayout;
    private ProgressBar progressBar;
    LinearLayout linearLayout;
    EditText editText_org;
    EditText editText_repo;
    Button search;
    RecyclerView recyclerView;

    String org_old="-999";
    String repo_old="-999";
    String org_new;
    String repo_new;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initviews();


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                org_new=editText_org.getText().toString();
                repo_new=editText_repo.getText().toString();

                if(!org_old.equals(org_new))
                {
                    recyclerView.setAdapter(null);

                    if(org_new==null || org_new.isEmpty())
                        Toast.makeText(getApplicationContext(),"Enter Organization's Name",Toast.LENGTH_SHORT).show();
                    else if(repo_new==null || repo_new.isEmpty())
                        Toast.makeText(getApplicationContext(),"Enter Repo Count",Toast.LENGTH_SHORT).show();

                    else
                    {
                        if(org_old.equals("-999"))
                            startAnimation();

                        progressBar.setVisibility(View.VISIBLE);
                        Retrofit retrofit = NetworkClient.getRetrofitClient();
                        final RequestService requestService=retrofit.create(RequestService.class);
                        Call<List<Repo>> call=requestService.requestGet(org_new);

                        call.enqueue(new Callback<List<Repo>>() {
                            @Override
                            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                                progressBar.setVisibility(View.INVISIBLE);
                                if(response.body()==null)
                                {
                                    Toast.makeText(getApplicationContext(),"Enter Valid Organization",Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    linearLayout.setVisibility(View.VISIBLE);
                                    repo_response=new ArrayList<>(response.body());
                                    Collections.sort(repo_response);
                                    if(Integer.parseInt(repo_new)>repo_response.size())
                                        repo_new=String.valueOf(repo_response.size());
                                    recyclerView.setAdapter(new RecyclerViewAdapter(getApplicationContext(),new ArrayList<Repo>(repo_response.subList(0,Integer.parseInt(repo_new)))));
                                }
                            }

                            @Override
                            public void onFailure(Call<List<Repo>> call, Throwable t) {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(getApplicationContext(),"Try Again",Toast.LENGTH_SHORT).show();
                                Log.e("Failure", "onFailure: "+t.getMessage());
                            }
                        });
                    }
                }
                else
                {
                    if(!repo_old.equals(repo_new))
                    {
                        if(Integer.parseInt(repo_new)>repo_response.size())
                            repo_new=String.valueOf(repo_response.size());
                        recyclerView.setAdapter(new RecyclerViewAdapter(getApplicationContext(),new ArrayList<Repo>(repo_response.subList(0,Integer.parseInt(repo_new)))));
                    }
                }

                org_old=org_new;
                repo_old=repo_new;
            }
        });

    }

    void initviews()
    {
        home=findViewById(R.id.home);

        relativeLayout=findViewById(R.id.relativeLayout);

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        progressBar=findViewById(R.id.progressbar);
        progressBar.setVisibility(View.INVISIBLE);

        linearLayout=findViewById(R.id.header);

        editText_org=findViewById(R.id.organization);
        editText_repo=findViewById(R.id.no_of_repo);

        search=findViewById(R.id.search);
    }

    private void startAnimation() {
        ViewPropertyAnimator viewPropertyAnimator = home.animate();
        viewPropertyAnimator.x(0f);
        viewPropertyAnimator.y(0f);
        viewPropertyAnimator.setDuration(1000);
        relativeLayout.removeView(home);
        RelativeLayout.LayoutParams rLParams =
                new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rLParams.addRule(RelativeLayout.ALIGN_PARENT_START, 1);
        relativeLayout.addView(home,rLParams);
    }
}
