package sg.edu.rp.c346.id22012433.dbrevise;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //test whether if the dbhelper works
        DBHelper db = new DBHelper(MainActivity.this);
        db.insertStudent("hazim", 4);
        db.insertStudent("Clarence", 3);

        Log.d("gpa",""+db.retrieveStudentsbyGPA(3.5).size());
        Log.d("gpa",""+db.retrieveStudentsbyGPA(2).size());


    }
}