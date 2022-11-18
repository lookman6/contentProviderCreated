package com.tp_examen.projetcrud.provider;

import android.net.Uri;
import android.provider.BaseColumns;

import java.net.URI;

public class PersonnneContract {

    public static final String AUTHORITY = "com.tp_examen.projetcrud";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+AUTHORITY);

    public static final String PATH_PERSONNE = "Personne";


    public static final class PersonneEntry implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_PERSONNE).build();


        public static final String TABLE_NAME = "Personne";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NOM = "nom";
        public static final String COLUMN_PRENOM = "prenom";
        public static final String COLUMN_AGE = "age";

    }
}
