package com.tp_examen.projetcrud.controller;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.tp_examen.projetcrud.model.Personne;
import com.tp_examen.projetcrud.dao.PersonneDAO;
import com.tp_examen.projetcrud.R;
import com.tp_examen.projetcrud.provider.PersonnneContract;

public class CreatePersonneActivity extends AppCompatActivity {

    EditText edtNom, edtPrenom, edtAge;
    Button btnAjouter;
    PersonneDAO h = new PersonneDAO(CreatePersonneActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_personne);

        edtNom = findViewById(R.id.edtNom);
        edtPrenom = findViewById(R.id.edtPrenom);
        edtAge = findViewById(R.id.edtAge);

        btnAjouter = (Button) findViewById(R.id.btnAjouter);

        btnAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Personne p = new Personne(edtNom.getText().toString(),
//                        edtPrenom.getText().toString(),
//                        Integer.parseInt(edtAge.getText().toString()));
//
//                h.insertPersonne(p);
//                Intent intent = new Intent(CreatePersonneActivity.this,ListPersonneActivity.class);
//                intent.putExtra("Message", "Ajout effectuée");
//                startActivity(intent);

                String nom = edtNom.getText().toString();
                String prenom = edtPrenom.getText().toString();
                String age = edtAge.getText().toString();

                if(nom.length() == 0 && prenom.length() == 0){
                    return;
                }

                ContentValues contentValues = new ContentValues();

                contentValues.put(PersonnneContract.PersonneEntry.COLUMN_NOM,nom);
                contentValues.put(PersonnneContract.PersonneEntry.COLUMN_PRENOM,prenom);
                contentValues.put(PersonnneContract.PersonneEntry.COLUMN_AGE,age);

                //On ajoute une personne via le content resolver
                Uri uri = getContentResolver().insert(PersonnneContract.PersonneEntry.CONTENT_URI, contentValues);


                //on affiche l'URI retournée avec un Toast
                if(uri != null){
//                    System.out.println(uri.toString());
//                    Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(CreatePersonneActivity.this,ListPersonneActivity.class);
                    intent.putExtra("Message", "Ajout effectuée avec " + uri.toString());
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_principal,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.app_home){
            Intent intent = new Intent(CreatePersonneActivity.this, MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
