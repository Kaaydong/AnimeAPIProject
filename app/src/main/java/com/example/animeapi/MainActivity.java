package com.example.animeapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView test, textViewTitle, textViewName, textViewGender;

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
        Call<List<Anime>> animeCall = animeService.searchPerson("Ashitaka");

        animeCall.enqueue(new Callback<List<Anime>>() {
            @Override
            public void onResponse(Call<List<Anime>> call, Response<List<Anime>> response) {


                if (!response.isSuccessful())
                {
                    test.setText(response.code());
                    Toast.makeText(MainActivity.this, "hi",Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Anime> anime = response.body();
                for (Anime thing : anime)
                {
                    String content = "";
                    content += "Name: " + thing.getName();

                    test.append(content);
                }

            }

            @Override
            public void onFailure(Call<List<Anime>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.d("Error","BIG ERROR");
                Log.d("Error",t.getMessage());
            }
        });
    }

    public void test()
    {

    }

    public void wireWidgets()
    {
        test = findViewById(R.id.textView_main_test);
        textViewName = findViewById(R.id.textView_main_name);
        textViewGender = findViewById(R.id.textView_main_gender);
        textViewTitle = findViewById(R.id.textView_main_title);
    }
}
