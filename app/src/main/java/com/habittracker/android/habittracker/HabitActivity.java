package com.habittracker.android.habittracker;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import data.HabitContract;
import data.HabitDbHelper;

public class HabitActivity extends AppCompatActivity {

    private EditText mActivityEditText;
    private Spinner mCategorySpinner;
    private EditText mDurationEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);

        mActivityEditText = (EditText) findViewById(R.id.edit_text_activity);
        mCategorySpinner = (Spinner) findViewById(R.id.category_spinner);
        mDurationEditText = (EditText) findViewById(R.id.edit_duration_activity);
        setupSpinners();
    }

    private void setupSpinners() {
        ArrayAdapter categoryAdapter = ArrayAdapter.createFromResource(
                this, R.array.array_category, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mCategorySpinner.setAdapter(categoryAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_habit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add:
                submitHabits();
                finish();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void submitHabits() {
        String nameValue = mActivityEditText.getText().toString().trim();
        String categoryValue = mCategorySpinner.getSelectedItem().toString();
        String durationValue = mDurationEditText.getText().toString();

        HabitDbHelper mDbHelper = new HabitDbHelper(this);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues habits = new ContentValues();

        habits.put(HabitContract.HabitEntry.COLUMN_HABIT_NAME, nameValue);
        habits.put(HabitContract.HabitEntry.COLUMN_HABIT_CATEGORY, categoryValue);
        habits.put(HabitContract.HabitEntry.COLUMN_HABIT_DURATION, durationValue);

        long newRowId = db.insert(HabitContract.HabitEntry.TABLE_NAME, null, habits);

    }
}
