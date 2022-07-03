package com.example.zage.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import androidx.annotation.Nullable;
import com.example.zage.model.Task;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;

public class DatabaseAdapter {
    DatabaseHelper dbHelper;

    //final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("");

    public DatabaseAdapter(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public static class DatabaseEntry implements BaseColumns {
        public static final String TABLE_NAME = "tasks";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_END_DATE = "end_date";
        public static final String COLUMN_NAME_STATE = "state";
        public static final String COLUMN_NAME_IMPORTANCE = "importance";
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DatabaseEntry.TABLE_NAME + " (" +
                    DatabaseEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DatabaseEntry.COLUMN_NAME_TITLE + " TEXT," +
                    DatabaseEntry.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    DatabaseEntry.COLUMN_NAME_END_DATE + " TEXT," +
                    DatabaseEntry.COLUMN_NAME_STATE + " INTEGER," +
                    DatabaseEntry.COLUMN_NAME_IMPORTANCE + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DatabaseEntry.TABLE_NAME;

    public static class DatabaseHelper extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 2;
        public static final String DATABASE_NAME = "Database.db";

        public DatabaseHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
            onCreate(sqLiteDatabase);
        }
    }

    public Cursor createCursor(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] columns = {DatabaseEntry._ID,
                DatabaseEntry.COLUMN_NAME_TITLE,
                DatabaseEntry.COLUMN_NAME_DESCRIPTION,
                DatabaseEntry.COLUMN_NAME_END_DATE,
                DatabaseEntry.COLUMN_NAME_STATE,
                DatabaseEntry.COLUMN_NAME_IMPORTANCE
        };

        return db.query(DatabaseEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null
        );
    }

    public String getData(){
        Cursor cursor = createCursor();

        StringBuilder buffer= new StringBuilder();

        while (cursor.moveToNext()) {
            int cid = cursor.getInt(cursor.getColumnIndex(DatabaseEntry._ID));
            String name = cursor.getString(cursor.getColumnIndex(DatabaseEntry.COLUMN_NAME_TITLE));
            String  password = cursor.getString(cursor.getColumnIndex(DatabaseEntry.COLUMN_NAME_DESCRIPTION));
            String  date = cursor.getString(cursor.getColumnIndex(DatabaseEntry.COLUMN_NAME_END_DATE));
            buffer.append(cid+ "   " + name + "   " + password + " " + date +" \n");
        }

        return buffer.toString();
    }

    public long insertData(String title, String description, String date, int state, int importance) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseEntry.COLUMN_NAME_TITLE, title);
        contentValues.put(DatabaseEntry.COLUMN_NAME_DESCRIPTION, description);
        contentValues.put(DatabaseEntry.COLUMN_NAME_END_DATE, date);
        contentValues.put(DatabaseEntry.COLUMN_NAME_STATE, state);
        contentValues.put(DatabaseEntry.COLUMN_NAME_IMPORTANCE, importance);
        return db.insert(DatabaseEntry.TABLE_NAME, null, contentValues);
    }

    public List<Task> getTaskRows(){
        Cursor cursor = createCursor();

        List<Task> buffer = new ArrayList<>();

        int i = 0;
        while (cursor.moveToNext()) {
            int cid = cursor.getInt(cursor.getColumnIndex(DatabaseEntry._ID));
            String title = cursor.getString(cursor.getColumnIndex(DatabaseEntry.COLUMN_NAME_TITLE));
            String  description = cursor.getString(cursor.getColumnIndex(DatabaseEntry.COLUMN_NAME_DESCRIPTION));
            String  date = cursor.getString(cursor.getColumnIndex(DatabaseEntry.COLUMN_NAME_END_DATE));
            buffer.add(new Task(cid, title,
                    description,
                    date, date,
                    null));
            i++;
        }
        return buffer;
    }

    public long remove(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(DatabaseEntry.TABLE_NAME,DatabaseEntry._ID+"="+id,null);
    }
}
