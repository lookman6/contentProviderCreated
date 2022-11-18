package com.tp_examen.projetcrud.controller;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.tp_examen.projetcrud.model.Personne;
import com.tp_examen.projetcrud.dao.PersonneDAO;
import com.tp_examen.projetcrud.R;
import com.tp_examen.projetcrud.provider.PersonneProvider;
import com.tp_examen.projetcrud.provider.PersonnneContract;

import java.util.ArrayList;

public class ListPersonneActivity  extends AppCompatActivity {

    ListView ls;
    PersonneDAO h = new PersonneDAO(ListPersonneActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_personne);

        ls = (ListView) findViewById(R.id.listPersonnes);

//        Cursor c = h.getAllPersonne();
        try{
            Cursor c = getContentResolver().query(PersonnneContract.PersonneEntry.CONTENT_URI,
                    null,
                    null,
                    null,
                    PersonnneContract.PersonneEntry.COLUMN_ID);

            Intent intent = getIntent();
            String Message = intent.getStringExtra("Message");
            Toast.makeText(this, Message, Toast.LENGTH_SHORT).show();

            ls = (ListView) findViewById(R.id.listPersonnes);
            freshy("");

            ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    TextView txtview = view.findViewById(R.id.id);
                    Intent intent = new Intent(ListPersonneActivity.this,DetailPersonneActivity.class);
                    intent.putExtra("id",txtview.getText().toString());
                    intent.putExtra("Message","");
                    startActivity(intent);
                }
            });
        }
        catch (Exception e){
            Log.e(TAG, "Failed to asynchronously load data");
            e.printStackTrace();
        }



    }

    public void freshy(String res)
    {
        ArrayList<Personne> lis = h.getSearch2(res);
        DisplayListPerson disp = new DisplayListPerson(lis);
        ls.setAdapter(disp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.choice,menu);
        getMenuInflater().inflate(R.menu.menu_principal,menu);

        MenuItem searchItem = menu.findItem(R.id.app_bar_search);

        SearchView search = (SearchView) searchItem.getActionView();
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                freshy(s);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.app_ajouter){
            Intent intent = new Intent(ListPersonneActivity.this, CreatePersonneActivity.class);
            startActivity(intent);
        }
        if(id == R.id.app_home){
            Intent intent = new Intent(ListPersonneActivity.this, MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    class DisplayListPerson extends BaseAdapter
    {
        private ArrayList<Personne> lst = new ArrayList<>();
        public DisplayListPerson(ArrayList<Personne> p)
        {
            this.lst=p;
        }
        @Override
        public int getCount()
        {
            return lst.size();
        }

        @Override
        public Object getItem(int position)
        {
            return lst.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return lst.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View v = getLayoutInflater().inflate(R.layout.item,null);

            TextView txtId = v.findViewById(R.id.id);
            TextView txtNom = v.findViewById(R.id.nom);
            TextView txtPrenom = v.findViewById(R.id.prenom);
            TextView txtAge = v.findViewById(R.id.age);

            txtId.setText(Integer.toString(lst.get(position).getId()));
            txtNom.setText(lst.get(position).getNom());
            txtPrenom.setText(lst.get(position).getPrenom());
            txtAge.setText(Integer.toString(lst.get(position).getAge()));

            return v;
        }
    }
}
