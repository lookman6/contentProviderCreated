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

public class DetailPersonneActivity extends AppCompatActivity {

    EditText edtNom, edtPrenom, edtAge;
    Button btnModifier, btnSupprimer;
    String id;
    PersonneDAO h = new PersonneDAO(DetailPersonneActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_personne);

        edtNom = findViewById(R.id.nomDetail);
        edtPrenom = findViewById(R.id.prenomDetail);
        edtAge = findViewById(R.id.ageDetail);

        btnModifier = (Button) findViewById(R.id.btnModifier);
        btnSupprimer = (Button) findViewById(R.id.btnSupprimer);

        id = getIntent().getStringExtra("id");
        Personne p = h.getOnePersonne(Integer.parseInt(id));

//        System.out.println(Integer.parseInt(id));

        edtNom.setText(p.getNom());
        edtPrenom.setText(p.getPrenom());
        edtAge.setText(String.valueOf(p.getAge()));

        Intent intent = getIntent();
        String Message = intent.getStringExtra("Message");
        Toast.makeText(this, Message, Toast.LENGTH_SHORT).show();

        btnModifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Personne p = new Personne(Integer.parseInt(id),edtNom.getText().toString(),
//                        edtPrenom.getText().toString(),Integer.parseInt(edtAge.getText().toString()));
//                h.updatePersonne(p);

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

                Uri uri = PersonnneContract.PersonneEntry.CONTENT_URI;
                uri = uri.buildUpon().appendPath(id).build();


                getContentResolver().update(uri, contentValues, "_id = ?",new String[]{id});

                Intent intent = new Intent(DetailPersonneActivity.this,ListPersonneActivity.class);
                intent.putExtra("Message", "Modification effectuée avec " + uri.toString());
                startActivity(intent);
            }
        });

        btnSupprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // h.deletePersonne(Integer.parseInt(id));

                Uri uri = PersonnneContract.PersonneEntry.CONTENT_URI;
                uri = uri.buildUpon().appendPath(id).build();

                getContentResolver().delete(uri, "_id = ?",new String[]{id});

                Intent intent = new Intent(DetailPersonneActivity.this,ListPersonneActivity.class);
                intent.putExtra("Message", "Suppression effectuée avec " + uri.toString());
                startActivity(intent);
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
            Intent intent = new Intent(DetailPersonneActivity.this, MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
