package data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import data.HabitContract.HabitEntry;

public class HabitDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "habits.db";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_HABITS_TABLE = "CREATE TABLE " + HabitEntry.TABLE_NAME
            + "(" + HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + ","
            + HabitEntry.COLUMN_HABIT_NAME + " TEXT NOT NULL" + ","
            + HabitEntry.COLUMN_HABIT_CATEGORY + " TEXT" + ","
            + HabitEntry.COLUMN_HABIT_DURATION + " INTEGER NOT NULL DEFAULT 0" + ")";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE " + HabitEntry.TABLE_NAME;

    public HabitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_HABITS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);

    }
}

