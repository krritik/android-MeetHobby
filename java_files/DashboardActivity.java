package com.example.meethobby;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class DashboardActivity extends AppCompatActivity {
    String UsernameHolder, EmailHolder, HallHolder, RoomNoHolder;
    TextView Username, Email, Hall, RoomNo;
    Button LogOut;
    Cursor cursor;
    SQLiteDatabase sqLiteDatabaseObj;
    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Username = (TextView)findViewById(R.id.textViewName);
        Email = (TextView)findViewById(R.id.textViewEmail);
        Hall = (TextView)findViewById(R.id.textViewHall);
        RoomNo = (TextView)findViewById(R.id.textViewRoomNo);
        LogOut = (Button)findViewById(R.id.button1);

        databaseHelper = new DatabaseHelper(this);
        sqLiteDatabaseObj = databaseHelper.getWritableDatabase();
        Intent intent = getIntent();
        UsernameHolder = intent.getStringExtra(MainActivity.UserName);

        cursor = sqLiteDatabaseObj.query(databaseHelper.TABLE1_NAME, null, " "+databaseHelper.Profile_COL_2 + "=?", new String[]{UsernameHolder}, null, null, null);

        while(cursor.moveToNext()) {
            cursor.moveToFirst();
            EmailHolder = cursor.getString(cursor.getColumnIndex(databaseHelper.Profile_COL_3));
            HallHolder = cursor.getString(cursor.getColumnIndex(databaseHelper.Profile_COL_4));
            RoomNoHolder = cursor.getString(cursor.getColumnIndex(databaseHelper.Profile_COL_5));
            cursor.close();
        }

        Username.setText(Username.getText().toString()+UsernameHolder);
        Email.setText(Email.getText().toString()+EmailHolder);
        Hall.setText(Hall.getText().toString()+HallHolder);
        RoomNo.setText(RoomNo.getText().toString()+RoomNoHolder);


        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(DashboardActivity.this,"Log Out Successfull", Toast.LENGTH_LONG).show();
            }
        });
    }
}
