package com.example.meethobby;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.acl.Group;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateGroupActivity extends AppCompatActivity {
    EditText GroupName, GroupDescription;
    Button CreateGroup, buttonHome;
    String GroupNameHolder, GroupDescriptionHolder, GroupCreationTimeHolder;
    int UserIDHolder, GroupIdHolder;
    Boolean EditTextEmptyHolder;
    String SQLiteDataBaseQueryHolder;

    SQLiteDatabase sqLiteDatabaseObj;
    DatabaseHelper databaseHelper;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        CreateGroup = (Button)findViewById(R.id.buttonCreateGroup);
        GroupName = (EditText)findViewById(R.id.editGroupName);
        GroupDescription = (EditText)findViewById(R.id.editGroupDescription);
        buttonHome = (Button)findViewById(R.id.buttonHome);

        Calendar C = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        GroupCreationTimeHolder = df.format(C.getTime());

        Intent intent = getIntent();
        UserIDHolder = intent.getIntExtra("UserID_pass", -1);

        databaseHelper = new DatabaseHelper(this);

        CreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDataBaseBuild();
                SQLiteTableBuild();

                CheckEditTextStatus();
                InsertDataIntoSQLiteDatabase();

                EmptyEditTextAfterDataInsert();
            }
        });

        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void SQLiteDataBaseBuild() {
        sqLiteDatabaseObj = openOrCreateDatabase(databaseHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);
    }

    public void SQLiteTableBuild() {
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + databaseHelper.TABLE4_NAME + " (GroupID INTEGER PRIMARY KEY AUTOINCREMENT,GroupName TEXT,  GroupDescription TEXT, CreationTime TEXT)");
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + databaseHelper.TABLE6_NAME + " (UserID INTEGER , GroupID INTEGER, Admin INTEGER, PRIMARY KEY(UserID, GroupID), FOREIGN KEY(UserID) REFERENCES " + databaseHelper.TABLE1_NAME + "(ID), FOREIGN KEY(GroupID) REFERENCES " + databaseHelper.TABLE4_NAME + "(GroupID))");
    }

    public void CheckEditTextStatus() {
        GroupNameHolder = GroupName.getText().toString();
        GroupDescriptionHolder = GroupDescription.getText().toString();

        if(TextUtils.isEmpty(GroupNameHolder) || TextUtils.isEmpty(GroupDescriptionHolder)) {
            EditTextEmptyHolder = false;
        }
        else {
            EditTextEmptyHolder = true;
        }
    }

    public void InsertDataIntoSQLiteDatabase() {
        if(EditTextEmptyHolder == true) {
            SQLiteDataBaseQueryHolder = "INSERT INTO " +databaseHelper.TABLE4_NAME+ " (GroupName, GroupDescription, CreationTime) VALUES('"+GroupNameHolder+"', '"+GroupDescriptionHolder+"', '"+GroupCreationTimeHolder+"');";
            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);

            cursor = sqLiteDatabaseObj.rawQuery("SELECT MAX(GroupID) FROM "+databaseHelper.TABLE4_NAME, null);
            cursor.moveToFirst();
            GroupIdHolder = cursor.getInt(0);

            SQLiteDataBaseQueryHolder = "INSERT INTO " +databaseHelper.TABLE6_NAME+ " (UserID, GroupID, Admin) VALUES('"+UserIDHolder+"', '"+GroupIdHolder+"', '"+ 1 +"');";
            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);
            sqLiteDatabaseObj.close();

            Toast.makeText(CreateGroupActivity.this,"Group Created Successfully", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(CreateGroupActivity.this, ShowGroupActivity.class);
            intent.putExtra("UserID_pass", UserIDHolder);
            startActivity(intent);
        }
        else {
            Toast.makeText(CreateGroupActivity.this, "Please Fill All The Details ", Toast.LENGTH_LONG).show();
        }
    }

    public void EmptyEditTextAfterDataInsert() {
        GroupName.getText().clear();
        GroupDescription.getText().clear();
    }


}
