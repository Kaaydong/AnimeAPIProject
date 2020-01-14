package com.example.animeapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wireWidgets();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AnimeService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AnimeService animeService = retrofit.create(AnimeService.class);
        Call<Anime> animeCall = animeService.searchPerson("Male");

        animeCall.enqueue(new Callback<Anime>() {
            @Override
            public void onResponse(Call<Anime> call, Response<Anime> response) {
                Anime foundAnime = response.body();

                if(foundAnime != null)
                {
                    test.setText(foundAnime.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Anime> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void test()
    {

    }

    public void wireWidgets()
    {
        test = findViewById(R.id.textView_main_test);
    }
}
