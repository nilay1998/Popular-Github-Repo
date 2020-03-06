package com.example.almabaseassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.content.Intent;
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
    LinearLayout top;
    LinearLayout middle;
    ProgressBar progressBar;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee);

        relativeLayout=findViewById(R.id.rl);
        top=findViewById(R.id.top);
        middle=findViewById(R.id.middle);

        Intent intent=getIntent();
        String url = intent.getStringExtra("link");
        setTitle(intent.getStringExtra("name"));

        progressBar=findViewById(R.id.prog11);
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
                    startAnimation();
                    progressBar.setVisibility(View.VISIBLE);
                    Retrofit retrofit = NetworkClient.getRetrofitClient();
                    final RequestService requestService=retrofit.create(RequestService.class);
                    Call<List<Commit>> call=requestService.requestCommit(paths[1],paths[2]);

                    call.enqueue(new Callback<List<Commit>>() {
                        @Override
                        public void onResponse(Call<List<Commit>> call, Response<List<Commit>> response) {
                            if(response.body()==null)
                            {
                                Toast.makeText(getApplicationContext(),"No contributers exists",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                middle.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);

                                Log.e("SUCCESS", "onResponse: "+response.body().get(0).getLogin());
                                commit_response=new ArrayList<>(response.body());
                                if(Integer.parseInt(editText_commit.getText().toString())<commit_response.size())
                                    commit_response=new ArrayList<>(commit_response.subList(0,Integer.parseInt(editText_commit.getText().toString())));
                                recyclerView.setAdapter(new CommitViewAdapter(getApplicationContext(),commit_response));
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Commit>> call, Throwable t) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Log.e("HEYY", "onFailure: "+t.getMessage());
                        }
                    });
                }


            }
        });
    }

    private void startAnimation() {
        ViewPropertyAnimator viewPropertyAnimator = top.animate();
        viewPropertyAnimator.x(0f);
        viewPropertyAnimator.y(0f);
        viewPropertyAnimator.setDuration(1000);
        viewPropertyAnimator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        RelativeLayout.LayoutParams rLParams =
                new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rLParams.addRule(RelativeLayout.ALIGN_PARENT_START, 1);
        relativeLayout.removeView(top);
        relativeLayout.addView(top,rLParams);
    }
}
