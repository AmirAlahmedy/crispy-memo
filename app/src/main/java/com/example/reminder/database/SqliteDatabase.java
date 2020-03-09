package com.example.reminder.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.reminder.Reminder;

import java.util.ArrayList;

public class SqliteDatabase extends SQLiteOpenHelper {

    private static  final int DATABASE_VERSION = 5;
    private static  final String DATABASE_NAME = "reminder";
    private static  final String TABLE_REMINDERS = "reminders";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_CONTENT = "remindercontent";
    private static final String COLUMN_IMPORTANCE = "importance";

    public SqliteDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_REMINDERS_TABLE = "CREATE TABLE " + TABLE_REMINDERS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_CONTENT + " TEXT," + COLUMN_IMPORTANCE + " INTEGER DEFAULT 0)";
        sqLiteDatabase.execSQL(CREATE_REMINDERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDERS);
        onCreate(sqLiteDatabase);
    }

    public ArrayList<Reminder> listReminders() {

        String query = "select * from " + TABLE_REMINDERS;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Reminder> storeReminders = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String content = cursor.getString(1);
                boolean important = (cursor.getInt(cursor.getColumnIndex(COLUMN_IMPORTANCE)) == 1);
                storeReminders.add(new Reminder(id, content, important));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return  storeReminders;
    }

    public void addReminder(Reminder reminder) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CONTENT, reminder.getContent());
        values.put(COLUMN_IMPORTANCE, reminder.isImportant());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_REMINDERS, null, values);
    }

    public void updateReminder(Reminder reminder) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CONTENT,  reminder.getContent());
        values.put(COLUMN_IMPORTANCE, reminder.isImportant());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_REMINDERS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(reminder.getId())});
    }

    public void deleteReminder(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REMINDERS, COLUMN_ID + " = ?", new String[] {String.valueOf(id)});
    }
}
