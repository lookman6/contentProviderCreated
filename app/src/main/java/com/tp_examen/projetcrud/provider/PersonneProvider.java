package com.tp_examen.projetcrud.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tp_examen.projetcrud.dao.PersonneDAO;

public class PersonneProvider extends ContentProvider {

    private PersonneDAO personneDAO;


    //Constantes entieres pour mon URI MATCHER
    //les personnes
    public static final int PERSONNES = 100;
    //personne
    public static final int PERSONNE_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        //personnes
        uriMatcher.addURI(PersonnneContract.AUTHORITY, PersonnneContract.PATH_PERSONNE, PERSONNES);
        //personne
        uriMatcher.addURI(PersonnneContract.AUTHORITY,PersonnneContract.PATH_PERSONNE + "#",PERSONNE_WITH_ID);

        return uriMatcher;
    }


    @Override
    public boolean onCreate() {
        Context context = getContext();
        personneDAO = new PersonneDAO(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {

        //pour l'acces a la base de donnees
        final SQLiteDatabase db = personneDAO.getReadableDatabase();

        //notre urimatcher
        int match = sUriMatcher.match(uri);

        //le curseur a retourner
        Cursor returnCursor;

        switch (match){
            case PERSONNES:
                returnCursor = db.query(PersonnneContract.PersonneEntry.TABLE_NAME,
                        strings,    //projection
                        s,          //selection
                        strings1,   //selectionArgs
                         null,
                        null,
                        s1 ); //sortOrder
                break;
            case PERSONNE_WITH_ID:
                //pour recuperer l'id d'une personne
                // URI: content://<authority>/Personne/#
                String id = uri.getPathSegments().get(1);

                //selection, notre colonne _id
                String mselection = "_id=?";
                //selection args = row ID depuis l'URI
                String[] mselectionArgs = new String[]{id};

                returnCursor = db.query(PersonnneContract.PersonneEntry.TABLE_NAME,
                        strings,    //projection
                        mselection,          //selection
                        mselectionArgs,   //selectionArgs
                        null,
                        null,
                        s1 ); //sortOrder
                break;
            default:
                throw  new UnsupportedOperationException("Unknown uri: " + uri);
        }

        //notification au cursor concernant l'uri pour laquel il a ete cree
        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        //pour accceder a notre base de donnees
        final SQLiteDatabase db = personneDAO.getWritableDatabase();

        //notre urimatcher
        int match = sUriMatcher.match(uri);

        //l'URI que l'on va retourner a la fin
        Uri returnUri;

        switch (match){
            case PERSONNES:
                long id = db.insert(PersonnneContract.PersonneEntry.TABLE_NAME, null, contentValues);
                //id positif si insertion reussite, sinon negatif
                if(id>0){
                    returnUri = ContentUris.withAppendedId(PersonnneContract.PersonneEntry.CONTENT_URI,id);
                }
                else{
                    throw  new UnsupportedOperationException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw  new UnsupportedOperationException("Unknown uri: " + uri);
        }

        //on notifie le changement au content resolver
        getContext().getContentResolver().notifyChange(uri,null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase db = personneDAO.getWritableDatabase();

        //notre urimatcher
        int match = sUriMatcher.match(uri);

        //nombre de lignes supprimes
        int count = 0;

        count = db.delete(PersonnneContract.PersonneEntry.TABLE_NAME,
                s,          //selection
                strings );  //selectionArgs

        getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {

        //pour accceder a notre base de donnees
        final SQLiteDatabase db = personneDAO.getWritableDatabase();

        //notre urimatcher
        int match = sUriMatcher.match(uri);

        int count = 0;


        count = db.update(PersonnneContract.PersonneEntry.TABLE_NAME,
                contentValues,
                s,
                strings);

//        switch (match){
//            case PERSONNES:
//                break;
//            case PERSONNE_WITH_ID:
//                count = db.update(PersonnneContract.PersonneEntry.TABLE_NAME,
//                        contentValues,
//                        "_id" + " = " + uri.getPathSegments().get(1) +
//                                (!TextUtils.isEmpty(s) ? " AND (" + s + ')' : ""),
//                        strings);
//                break;
//            default:
//                throw  new UnsupportedOperationException("Unknown uri: " + uri);
//        }

        //on notifie le changement au content resolver
        getContext().getContentResolver().notifyChange(uri,null);

        return 0;
    }
}
