package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sqlite.RecycleAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    OpenHelper dbHelper;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter userAdapter;
    private RecyclerView.LayoutManager layoutManager;
    Button btnRegister;

    List<POJO> studentDetails;
    SQLiteDatabase sqLiteDatabase;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new OpenHelper(this);
        sqLiteDatabase= dbHelper.getReadableDatabase();
        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        studentDetails = new ArrayList<POJO>();
        studentDetails.clear();
        Cursor c1 = sqLiteDatabase.query(DBInfo.TABLE_NAME,null,null,null,null,null,null);
        if(c1 != null && c1.getCount() != 0) {
            studentDetails.clear();
            while (c1.moveToNext()) {
                POJO studDetails = new POJO();

                studDetails.setP_id(c1.getInt(c1.getColumnIndex(DBInfo._ID)));
                studDetails.setP_idno(c1.getString(c1.getColumnIndex(DBInfo.IDNO)));
                studDetails.setP_name(c1.getString(c1.getColumnIndex(DBInfo.StudentName)));
                studDetails.setP_address(c1.getString(c1.getColumnIndex(DBInfo.StudentAddress)));
                studentDetails.add(studDetails);
            }
        }

        c1.close();
        layoutManager = new LinearLayoutManager(this);
        userAdapter = new RecycleAdapter(studentDetails);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(userAdapter);
    }
    //    @Override
//    protected void onDestory() {
//        sqLiteDatabase.close();
//        super.onDestroy();
//    }
    public void clickAddStudent (View view) {
        startActivity(new Intent(this, Add.class));
    }

}