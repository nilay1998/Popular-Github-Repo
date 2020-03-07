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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initviews();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String org=editText_org.getText().toString();
                final String repo=editText_repo.getText().toString();

                if(org==null || org.isEmpty())
                    Toast.makeText(getApplicationContext(),"Enter Organization's Name",Toast.LENGTH_SHORT).show();
                else if(repo==null || repo.isEmpty())
                    Toast.makeText(getApplicationContext(),"Enter Repo Count",Toast.LENGTH_SHORT).show();

                else
                {
                    startAnimation();
                    progressBar.setVisibility(View.VISIBLE);
                    Retrofit retrofit = NetworkClient.getRetrofitClient();
                    final RequestService requestService=retrofit.create(RequestService.class);
                    Call<List<Repo>> call=requestService.requestGet(org);

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
                                if(Integer.parseInt(repo)<repo_response.size())
                                    repo_response= new ArrayList<Repo>(repo_response.subList(0,Integer.parseInt(repo)));
                                recyclerView.setAdapter(new RecyclerViewAdapter(getApplicationContext(),repo_response));
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
        });

    }

    void initviews()
    {
        home=findViewById(R.id.home);

        relativeLayout=findViewById(R.id.relativeLayout);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView=findViewById(R.id.recyclerView);

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
