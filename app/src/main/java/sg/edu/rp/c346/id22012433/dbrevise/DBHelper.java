package sg.edu.rp.c346.id22012433.dbrevise;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VER = 1;
    private static final String DATABASE_NAME = "students.db";

    private static final String TABLE_STUDENT = "student";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_GPA = "gpa";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table "pokemon"

        String createTableSql = "CREATE TABLE " + TABLE_STUDENT +  "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_GPA + " REAL )";
        db.execSQL(createTableSql);
        Log.i("info" ,"created tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int
            newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        // Create table(s) again
        onCreate(db);
    }

    //Create, Retrieve, Update, Delete
    public void insertStudent(String name, double gpa){

        // Get an instance of the database for writing
        SQLiteDatabase db = this.getWritableDatabase();
        // We use ContentValues object to store the values for
        //  the db operation
        ContentValues values = new ContentValues();

        // Store the column name as key and the description as value
        values.put(COLUMN_NAME, name);
        // Store the column name as key and the date as value
        values.put(COLUMN_GPA, gpa);
        // Insert the row into the TABLE_POKEMON
        db.insert(TABLE_STUDENT, null, values);

        // Close the database connection
        db.close();
    }
    //Retrieve
    public ArrayList<Student> retrieveStudentsbyGPA(double gpa) {
        ArrayList<Student> slist = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_NAME, COLUMN_GPA};//need to be in same order
        String condition = COLUMN_GPA + " > ?";
        String[] args = {String.valueOf(gpa)};
        Cursor cursor = db.query(TABLE_STUDENT, columns, condition, args, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);//need to be in same order
                String name = cursor.getString(1);
                double hgpa = cursor.getDouble(2);
                //creating a new object of pokemon
                Student obj = new Student(id, name, hgpa);
                slist.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return slist;
    }

    //update
    public int updateStudent(Student data){// The method takes in Pokemon object named data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //Value to be update, in this case "type"
        values.put(COLUMN_NAME, data.getName());
        //Condition
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};

        int result = db.update(TABLE_STUDENT, values, condition, args);
        db.close();
        return result;
    }
    public void updateGpaByName(String name, double gpa){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_GPA,gpa);
        //condition
        String condition = COLUMN_NAME + "= ?";
        String[] args = {name};

        int result = db.update(TABLE_STUDENT, values, condition, args);
        db.close();
    }

    //Delete
    public int deleteStudent(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_STUDENT, condition, args);
        db.close();
        return result;
    }

}
