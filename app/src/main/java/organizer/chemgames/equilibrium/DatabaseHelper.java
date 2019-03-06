package organizer.chemgames.equilibrium;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Tasks.db";
    public static final String TABLE_NAME = "tasks_table";
    public static final String COL_1 = "NAME";
    public static final String COL_2 = "CATEGORY";
    public static final String COL_3 = "DATE";
    public static final String COL_4 = "SETDATE";
    public static final String COL_5 = "CALDATE";
    public static final String COL_6 = "PROG_WHEN_BACK";
    public static final String COL_7 = "TIME_WHEN_BACK";

    //id will be index of item

    public DatabaseHelper(MainActivityThreadTest context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,CATEGORY TEXT, DATE TEXT, SETDATE TEXT, CALDATE TEXT, PROG_WHEN_BACK TEXT, TIME_WHEN_BACK TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name, String category, String date, String setdate, String caldate, String prog_when_back, String time_when_back) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,name);
        contentValues.put(COL_2,category);
        contentValues.put(COL_3,date);
        contentValues.put(COL_4,setdate);
        contentValues.put(COL_5,caldate);
        contentValues.put(COL_6,prog_when_back);
        contentValues.put(COL_7,time_when_back);
        long result = db.insertWithOnConflict(TABLE_NAME,null ,contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public boolean updateData(String name, String category, String date, String setdate, String caldate, String prog_when_back, String time_when_back) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,name);
        contentValues.put(COL_2,category);
        contentValues.put(COL_3,date);
        contentValues.put(COL_4,setdate);
        contentValues.put(COL_5,caldate);
        contentValues.put(COL_6,prog_when_back);
        contentValues.put(COL_7,time_when_back);
        db.update(TABLE_NAME, contentValues, null, null);
        return true;
    }

    public Integer deleteData () {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, null, null);
    }

    public Integer deleteItem (String caldate) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COL_5 + "=" + caldate , null);
    }

    public Integer checkidItem (String caldate) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, caldate , null);
    }
}
