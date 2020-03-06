package com.example.almabaseassignment.Retrofit;

import com.example.almabaseassignment.Models.Commit;
import com.example.almabaseassignment.Models.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface RequestService {

    @GET("/orgs/{org}/repos?per_page=5000")
    Call<List<Repo>> requestGet(@Path("org") String org);

    @GET("/repos/{org}/{repo}/contributors?per_page=5000")
    Call<List<Commit>> requestCommit(@Path("org") String org,@Path("repo") String repo);
}
