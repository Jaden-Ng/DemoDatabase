package sg.edu.rp.c346.id20016584.demodatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Currency;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VER=1;
    private static final String DATABASE_NAME="task.db";

    private static final String TABLE_TASK="task";
    private static final String COLUMN_ID="_id";
    private static final String COLUMN_DESCRIPTION="description";
    private static final String COLUMN_DATE="date";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSql= "CREATE TABLE "+TABLE_TASK+"("
                +COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +COLUMN_DATE+" TEXT, "
                +COLUMN_DESCRIPTION+ " TEXT)";
        db.execSQL(createTableSql);
        Log.i("info", "create tables");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_TASK);
        //create table(s) again
        onCreate(db);

    }
    public void insertTask(String description, String date){
        //get instance of the database for writing
        SQLiteDatabase db= this.getWritableDatabase();
        //we use contentvalues object to store the values for the db operation
        ContentValues values= new ContentValues();
        //store the column name as key and the description as value
        values.put(COLUMN_DESCRIPTION, description);
        //store the column name as key and the date as value
        values.put(COLUMN_DATE, date);
        //insert row into TABLE_TASK
        db.insert(TABLE_TASK, null, values);
        //close database connection
        db.close();
    }
    public ArrayList<String> getTaskContent(){
        //create an arraylist that holds string objects
        ArrayList<String> tasks= new ArrayList<String>();
        //select all the task description
        String selectQuery="SELECT "+COLUMN_DESCRIPTION
                +" FROM "+TABLE_TASK;

        //get instance of database to read
        SQLiteDatabase db=this.getReadableDatabase();
        //run the sql query and get back the cursor object
        Cursor cursor= db.rawQuery(selectQuery, null);

        //moveToFirst() moves to first row, null if no records
        if(cursor.moveToFirst()){
            //loop while moveToNext() points to next row and returns true; moveToNext() return false when no more next row to move on
            do{
                tasks.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        //close connection
        cursor.close();
        db.close();
        return tasks;


    }
    public ArrayList<Task> getTasks(boolean asc){
        ArrayList<Task> tasks= new ArrayList<Task>();
        String selectQuery= "SELECT "+COLUMN_ID+", "
                +COLUMN_DESCRIPTION+", "
                +COLUMN_DATE
                +" FROM "+ TABLE_TASK
                +" ORDER BY "+ COLUMN_DESCRIPTION;
        if(asc){
            selectQuery+=" ASC ";
        }else{
            selectQuery+=" DESC ";
        }

        SQLiteDatabase db=this.getReadableDatabase();
        //run the sql query and get back the cursor object
        Cursor cursor= db.rawQuery(selectQuery, null);

        //moveToFirst() moves to first row, null if no records
        if(cursor.moveToFirst()){
            //loop while moveToNext() points to next row and returns true; moveToNext() return false when no more next row to move on
            do{
                int id= cursor.getInt(0);
                String description= cursor.getString(1);
                String date= cursor.getString(2);
                Task obj= new Task(id, description, date);
                tasks.add(obj);
            }while (cursor.moveToNext());
        }
        //close connection
        cursor.close();
        db.close();
        return tasks;
    }

}
