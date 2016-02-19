package com.example.admin.taskempat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;

/**
 * Created by Admin on 10/02/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "Currencyy.db";

        public static final String TABLE_NAME = "income";
        public static final String COL_1 = "_id";
        public static final String COL_2 = "description";
        public static final String COL_3 = "amount";
        public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_2 + " TEXT, " + COL_3 + " INTEGER);";

        public static final String TABLE_NAME1 = "outcome";
        public static final String COL_1Out = "_id";
        public static final String COL_2Out = "description";
        public static final String COL_3Out = "amount";
        public static final String TABLE_CREATEOut = "CREATE TABLE " + TABLE_NAME1 + " (" + COL_1Out + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_2Out + " TEXT, " + COL_3Out + " INTEGER);";

        private static final String TAG = "CurrencyDBAdapter";
        private DatabaseHelper dbHelper;
        private Context context;

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(TABLE_CREATE);
            db.execSQL(TABLE_CREATEOut);

        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d(TAG, "upgrade DB");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);

            onCreate(db);
        }

        public void addIncome(Incomee contact) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues val = new ContentValues();
            val.put(COL_2, contact.getDescriptionIncome());
            val.put(COL_3, contact.getAmountIncome());
            db.insert(TABLE_NAME, null, val);
        }

        public void addOutcome(Outcomee contact) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues val = new ContentValues();
            val.put(COL_2Out, contact.getDescriptionOutcome());
            val.put(COL_3Out, contact.getAmountOutcome());
            db.insert(TABLE_NAME1, null, val);
        }


        public DatabaseHelper open() throws SQLException {
            SQLiteDatabase db = this.getWritableDatabase();
            dbHelper = new DatabaseHelper(context);
            db = dbHelper.getWritableDatabase();
            return this;
        }

        public void close() {
            SQLiteDatabase db = this.getWritableDatabase();
            db.close();
        }


        public boolean deleteIncome(int id) {
            SQLiteDatabase db = this.getWritableDatabase();
            return db.delete(TABLE_NAME, COL_1 + "=" + id, null) > 0;
        }
         public boolean deleteOutcome(int id) {
            SQLiteDatabase db = this.getWritableDatabase();
            return db.delete(TABLE_NAME1, COL_1Out + "=" + id, null) > 0;
         }

        public Cursor getAllIncome() {
            SQLiteDatabase db = this.getWritableDatabase();
//            return db.query(TABLE_NAME, new String[]{COL_1, COL_2, COL_3}, null, null, null, null, null);
            return db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        }
         public Cursor getAllOutcome() {
            SQLiteDatabase db = this.getWritableDatabase();
             db = this.getReadableDatabase();
            return db.query(TABLE_NAME1, new String[]{COL_1Out, COL_2Out, COL_3Out}, null, null, null, null, null);
         }
    public int getTotIn(){
        int sum=0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor totIn = db.rawQuery("SELECT SUM(amount) FROM " + TABLE_NAME, null);
        totIn.moveToFirst();
        if(totIn.getCount()>0) {
            sum=totIn.getInt(0);
        }
        return sum;
    }
    public int getTotOut(){
        int sum=0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor totOut = db.rawQuery("SELECT SUM(amount) FROM " + TABLE_NAME1, null);
        totOut.moveToFirst();
        if(totOut.getCount()>0) {
            sum=totOut.getInt(0);
        }
        return sum;
    }

        public Cursor getSingleIncome(int id) {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.query(TABLE_NAME, new String[]
                    {
                            COL_1, COL_2, COL_3
                    }, COL_1 + "=" + id, null, null, null, null);
//            cursor.moveToFirst();
            if (cursor != null&&cursor.getCount()>0)
                cursor.moveToFirst();

            return cursor;
        }

        public Cursor getSingleOutcome(int id) {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.query(TABLE_NAME1, new String[]
                {
                        COL_1Out, COL_2Out, COL_3Out
                }, COL_1Out + "=" + id, null, null, null, null);
            if (cursor != null&&cursor.getCount()>0)
                cursor.moveToFirst();

            return cursor;
         }

        public boolean updateIncome(Incomee income) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues val = new ContentValues();
            val.put(COL_2, income.getDescriptionIncome());
            val.put(COL_3, income.getAmountIncome());

            return db.update(TABLE_NAME, val, COL_1 + "=" + income.getId(), null) > 0;
        }
        public boolean updateOutcome(Outcomee outcome) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues val = new ContentValues();
            val.put(COL_2Out, outcome.getDescriptionOutcome());
            val.put(COL_3Out, outcome.getAmountOutcome());

            return db.update(TABLE_NAME1, val, COL_1Out + "=" + outcome.getId(), null) > 0;
        }

//    public boolean add_income(String description, String amount) {
//
//            SQLiteDatabase db = this.getWritableDatabase();
//            ContentValues content_values = new ContentValues();
//            content_values.put(COL_2, description);
//            content_values.put(COL_3, amount);
//            long result = db.insert(TABLE_NAME, null, content_values);
//            return result != -1;
//        }
//
//
//        public boolean add_outcome(String description, String amount) {
//
//            SQLiteDatabase db = this.getWritableDatabase();
//            ContentValues content_values = new ContentValues();
//            content_values.put(COL_2Out, description);
//            content_values.put(COL_3Out, amount);
//            long result = db.insert(TABLE_NAME1, null, content_values);
//            return result != -1;
//        }
//
//
//        public Cursor list_income() {
//            SQLiteDatabase db = this.getWritableDatabase();
//            Cursor list_income = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
//            return list_income;
//        }
//
//        public Cursor list_outcome() {
//            SQLiteDatabase db = this.getWritableDatabase();
//            Cursor list_outcome = db.rawQuery("SELECT * FROM " + TABLE_NAME1, null);
//            return list_outcome;
//        }
//
//
//        public boolean update_income(String id, String description, String amount) {
//            SQLiteDatabase db = this.getWritableDatabase();
//            ContentValues content_values = new ContentValues();
//            content_values.put(COL_1, id);
//            content_values.put(COL_2, description);
//            content_values.put(COL_3, amount);
//            db.update(TABLE_NAME, content_values, "_id = ? ", new String[]{id});
//            return true;
//        }
//
//        public boolean update_outcome(String id, String description, String amount) {
//            SQLiteDatabase db = this.getWritableDatabase();
//            ContentValues content_values = new ContentValues();
//            content_values.put(COL_1Out, id);
//            content_values.put(COL_2Out, description);
//            content_values.put(COL_3Out, amount);
//            db.update(TABLE_NAME1, content_values, "_id = ? ", new String[]{id});
//            return true;
//        }
//
//
//        public Integer delete_income(String id) {
//            SQLiteDatabase db = this.getWritableDatabase();
//            return db.delete(TABLE_NAME, "_id = ?", new String[]{id});
//        }
//
//        public Integer delete_outcome(String id) {
//            SQLiteDatabase db = this.getWritableDatabase();
//            return db.delete(TABLE_NAME1, "_id = ?", new String[]{id});
//        }
    }
