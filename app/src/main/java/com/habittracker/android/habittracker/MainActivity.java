package com.habittracker.android.habittracker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import data.HabitContract.HabitEntry;
import data.HabitDbHelper;

public class MainActivity extends AppCompatActivity {
    private HabitDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, HabitActivity.class);
                startActivity(i);
            }
        });

        mDbHelper = new HabitDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayHabits();
    }
    private Cursor readData(){
        HabitDbHelper mDbHelper = new HabitDbHelper(this);

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_HABIT_NAME,
                HabitEntry.COLUMN_HABIT_CATEGORY,
                HabitEntry.COLUMN_HABIT_DURATION,
        };

        Cursor cursor = db.query(
                HabitEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null,
                null);

        return cursor;
    }

    private void displayHabits (){

        TextView display = (TextView) findViewById(R.id.habit_text_view);
        Cursor cursor = readData();
        try {
            display.setText(HabitEntry.COLUMN_HABIT_NAME + " - " +
                            HabitEntry.COLUMN_HABIT_CATEGORY + " - " +
                            HabitEntry.COLUMN_HABIT_DURATION + "\n");

            int nameColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_NAME);
            int categoryColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_CATEGORY);
            int durationColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_DURATION);

            while (cursor.moveToNext()) {

                String currentName = cursor.getString(nameColumnIndex);
                String currentCategory = cursor.getString(categoryColumnIndex);
                String currentDuration = cursor.getString(durationColumnIndex);

                display.append("\n" + "I was: " + currentName + " during: " +
                        currentCategory + " for: " +
                        currentDuration + "h");
            }
        } finally {
            cursor.close();
        }
    }

    private void deleteAll() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(HabitEntry.TABLE_NAME, null, null);
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        deleteAll();
        displayHabits();
        return super.onOptionsItemSelected(item);
    }
}
