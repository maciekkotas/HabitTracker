package data;

import android.provider.BaseColumns;

public class HabitContract {

    private HabitContract() {
    }

    public static abstract class HabitEntry implements BaseColumns {
        public static final String TABLE_NAME = "habits";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_HABIT_NAME = "name";
        public static final String COLUMN_HABIT_CATEGORY = "category";
        public static final String COLUMN_HABIT_DURATION = "duration";
    }
}
