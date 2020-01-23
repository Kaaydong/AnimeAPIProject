package com.example.animeapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView textViewBody, textViewTitle, textViewTester;
    private EditText editTextCharacterName;
    private Button buttonSearchCharacter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wireWidgets();

         setListeners();

    }

    public void setListeners()
    {
        buttonSearchCharacter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = editTextCharacterName.getText().toString();
                name = fixNames(name);


                if (name != null) {
                        callLink(name);
//                    textViewTester.setText(name);
                }
            }
        });
    }

    public String fixNames(String insertedName)
    {
        String name = insertedName;
        name = name.toLowerCase();

        String namep1 = name.substring(0,1).toUpperCase();
        String namep2 = name.substring(1);
        name = namep1 + namep2;

        if (name.indexOf(' ') != -1)
        {
            int space = name.indexOf(' ');
            namep1 = name.substring(0,space + 1);

            namep2 = name.substring(space + 1, space + 2).toUpperCase();
            String namep3 = name.substring(space + 2);


            name = namep1 + namep2 + namep3;
        }


        return name;
    }

    public void callLink(String name)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AnimeService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AnimeService animeService = retrofit.create(AnimeService.class);
        Call<List<Anime>> animeCall = animeService.searchPerson(name);

        animeCall.enqueue(new Callback<List<Anime>>() {
            @Override
            public void onResponse(Call<List<Anime>> call, Response<List<Anime>> response) {


                if (!response.isSuccessful()) {
                    textViewBody.setText(response.code());
                    Toast.makeText(MainActivity.this, "hi", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Anime> anime = response.body();
                for (Anime thing : anime) {
                    String content = "";
                    content += "Name: " + thing.getName();
                    content += "\nGender: " + thing.getGender();
                    content += "\nAge: " + thing.getAge();
                    content += "\nEye Colour: " + thing.getEye_color();
                    content += "\nHair Colour: " + thing.getHair_color();
                    textViewBody.setText(content);
//                                textViewBody.append(content);
                }

            }

            @Override
            public void onFailure(Call<List<Anime>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("Error", "BIG ERROR");
                Log.d("Error", t.getMessage());
            }
        });
    }

    public void wireWidgets()
    {
        textViewBody = findViewById(R.id.textView_main_body);
        textViewTitle = findViewById(R.id.textView_main_title);
        editTextCharacterName = findViewById(R.id.editText_main_character);
        buttonSearchCharacter = findViewById(R.id.button_main_search);
        textViewTester = findViewById(R.id.textView_main_tester);
    }
}
