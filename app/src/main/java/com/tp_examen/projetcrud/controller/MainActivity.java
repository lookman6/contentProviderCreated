package com.tp_examen.projetcrud.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tp_examen.projetcrud.R;

public class MainActivity extends AppCompatActivity {
    Button btnCreate,btnRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCreate = (Button) findViewById(R.id.btnCreate);
        btnRead = (Button) findViewById(R.id.btnRead);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreatePersonneActivity.class);
                intent.putExtra("Message", "");
                startActivity(intent);

            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListPersonneActivity.class);
                intent.putExtra("Message", "");
                startActivity(intent);
            }
        });



    }
}