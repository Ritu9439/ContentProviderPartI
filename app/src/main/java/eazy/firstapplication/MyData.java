package eazy.firstapplication;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;

/**
 * Created by Administrator on 17-06-2017.
 */

public class MyData extends ContentProvider {

    //unique namesapce with which we will refer out Content Provider
   static final String PROVIDER_NAME = "eazy.firstapplication.MyData";


    //always starts with content:
   static String URL = "content://" + PROVIDER_NAME + "/dummy";
   static Uri CONTENT_URI = Uri.parse(URL);

    //database columns
    static final String _ID = "_id";
    static final String NAME = "name";


    static final int uri_code=1;

    private static HashMap<String,String> values;

  static   UriMatcher uriMatcher;

     static {
         uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
         uriMatcher.addURI(PROVIDER_NAME,"dummy",uri_code);
     }

     SQLiteDatabase sqL;
    String Databasename="mydb";
    String tbname="mytable";
    int version=1;
    String Create_db_table="create table "+tbname+"(id integer primary key autoincrement,name text)";




    @Override
    public boolean onCreate() {
        DatabaseHelper databaseHelper=new DatabaseHelper(getContext());
        sqL=databaseHelper.getWritableDatabase();
        if (sqL!=null){
            return true;
        }

        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String s1) {

//projection is used which column to retrive
        //selection is for where clause

        SQLiteQueryBuilder sqLiteQueryBuilder=new SQLiteQueryBuilder();

        sqLiteQueryBuilder.setTables(tbname);


        switch (uriMatcher.match(uri)){
            case uri_code:
                sqLiteQueryBuilder.setProjectionMap(values);
                break;
        }

        Cursor cursor=sqLiteQueryBuilder.query(sqL,projection,selection,selectionArgs,null,null,s1);
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)){
            case uri_code:
                return "vnd.android.cursor.dir/dummy";

        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {


        long rowid=sqL.insert(tbname,null,contentValues);
        Uri _uri= ContentUris.withAppendedId(CONTENT_URI,rowid);
getContext().getContentResolver().notifyChange(_uri,null);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, Databasename, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqL) {
            sqL.execSQL(Create_db_table);

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqL, int i, int i1) {
sqL.execSQL("Drop table if exists "+tbname);
            onCreate(sqL);
        }
    }
}
