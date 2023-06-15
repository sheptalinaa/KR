package com.example.cursovayaapp;

import static com.example.cursovayaapp.MainActivity.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView resultView = findViewById(R.id.resultView);
        Intent doAgain = new Intent(this, MainActivity.class);
        Button again = findViewById(R.id.btnAgain);
        Bundle arguments = getIntent().getExtras();

        String res = arguments.get("result").toString();
        resultView.setText("ВАШ СЧЁТ: " + res);
        Button sentResult = findViewById(R.id.btnSentResult);
        sentResult.setOnClickListener(v ->{
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "В приложении Trivia я получил:" + res + " " + "балла(ов)");
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent,"Поделиться"));
        });
        again.setOnClickListener(v ->{
            startActivity(doAgain);
        });
    }

}