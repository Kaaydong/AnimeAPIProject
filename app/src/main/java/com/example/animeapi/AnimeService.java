package com.example.animeapi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AnimeService {

    String BASE_URL = "https://ghibliapi.herokuapp.com/";

//    @GET("person?{trait}={specficTrait}")
////    Call<Anime> searchPerson(@Query("trait") String trait, @Query("specificTrait") String specificTrait);

    @GET("person")
    Call<Anime> searchPerson(@Query("gender") String specificTrait);
}
