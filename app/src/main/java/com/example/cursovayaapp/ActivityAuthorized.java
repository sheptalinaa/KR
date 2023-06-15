package com.example.cursovayaapp;

import static com.example.cursovayaapp.StdApp.getServerApi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cursovayaapp.databinding.ActivityAuthorizedBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityAuthorized extends AppCompatActivity implements View.OnClickListener{

    int result;
    JSONArray data;
    private JSONObject jsonObject;
    private JSONArray jSONObject;
    private ActivityAuthorizedBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthorizedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnEnter.setOnClickListener(this);

        EditText inputPswd = findViewById(R.id.etPassword);
        inputPswd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

        Button btnEnter = findViewById(R.id.btnEnter);
        btnEnter.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Log.i("simple_app_tag", "Начало проекта");
        EditText inputLog = findViewById(R.id.etLogin);
        EditText inputPswd = findViewById(R.id.etPassword);


        if(!inputLog.getText().toString().isEmpty() && !inputPswd.getText().toString().isEmpty()) {

            StdApp.getServerApi().getStudentInfoAll(inputLog.getText().toString(), inputPswd.getText().toString(), "RIBO-02-21")
                    .enqueue(new Callback<StudentInfoResponse>() {
                @Override
                public void onResponse(Call<StudentInfoResponse> call, Response<StudentInfoResponse> response) {
                    StudentInfoResponse sir = response.body();

                    result = sir.getResult();

                    if(result == 1){
                        Intent actIntent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(actIntent);

                    }else {

                        Toast.makeText(getApplicationContext(), "Неверный пароль и/или логин", Toast.LENGTH_LONG).show();
                        Log.i("simple_app_tag", "Неверный пароль и/или логин");


                    }
    }
                @Override
                public void onFailure(Call<StudentInfoResponse> call, Throwable t) {
                    Log.i("simple_app_tag", "error");
                }
            });

        }else {
            Toast.makeText(getApplicationContext(),"Необходимо заполнить оба поля",Toast.LENGTH_LONG).show();
        }

    }
}
