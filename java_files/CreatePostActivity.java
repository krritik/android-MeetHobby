package com.example.meethobby;


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

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreatePostActivity extends AppCompatActivity {
    EditText PostDescription;
    Button CreatePost, buttonBack;
    String PostDescriptionHolder, PostTimeHolder;
    int UserIDHolder, PostIDHolder, GroupIDHolder;
    Boolean EditTextEmptyHolder;
    String SQLiteDataBaseQueryHolder;

    SQLiteDatabase sqLiteDatabaseObj;
    DatabaseHelper databaseHelper;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        CreatePost = (Button)findViewById(R.id.buttonCreatePost);
        PostDescription = (EditText)findViewById(R.id.editPostDescription);
        buttonBack = (Button)findViewById(R.id.buttonBack);

        Calendar C = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        PostTimeHolder = df.format(C.getTime());

        Intent intent = getIntent();
        GroupIDHolder = intent.getIntExtra("GroupID_pass", -1);
        UserIDHolder = intent.getIntExtra("UserID_pass", -1);

        databaseHelper = new DatabaseHelper(this);

        CreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDataBaseBuild();
                SQLiteTableBuild();

                CheckEditTextStatus();
                InsertDataIntoSQLiteDatabase();

                EmptyEditTextAfterDataInsert();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
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
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + databaseHelper.TABLE3_NAME + " (PostID INTEGER PRIMARY KEY AUTOINCREMENT, UserID INTEGER, PostDescription TEXT, PostTime TEXT, FOREIGN KEY(UserID) REFERENCES " + databaseHelper.TABLE1_NAME + "(ID))");
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + databaseHelper.TABLE5_NAME + " (UserID INTEGER , PostID INTEGER, PRIMARY KEY(UserID, PostID), FOREIGN KEY(UserID) REFERENCES " + databaseHelper.TABLE1_NAME + "(ID), FOREIGN KEY(PostID) REFERENCES " + databaseHelper.TABLE3_NAME + "(PostID))");
    }

    public void CheckEditTextStatus() {

        PostDescriptionHolder = PostDescription.getText().toString();

        if(TextUtils.isEmpty(PostDescriptionHolder)) {
            EditTextEmptyHolder = false;
        }
        else {
            EditTextEmptyHolder = true;
        }
    }

    public void InsertDataIntoSQLiteDatabase() {
        if(EditTextEmptyHolder == true) {
            SQLiteDataBaseQueryHolder = "INSERT INTO " +databaseHelper.TABLE3_NAME+ " (UserID, PostDescription, PostTime) VALUES('"+UserIDHolder+"', '"+PostDescriptionHolder+"', '"+PostTimeHolder+"');";
            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);

            cursor = sqLiteDatabaseObj.rawQuery("SELECT MAX(PostID) FROM "+databaseHelper.TABLE3_NAME, null);
            cursor.moveToFirst();
            PostIDHolder = cursor.getInt(0);

            SQLiteDataBaseQueryHolder = "INSERT INTO " +databaseHelper.TABLE5_NAME+ " (GroupID, PostID) VALUES('"+GroupIDHolder+"', '"+PostIDHolder+"');";
            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);
            sqLiteDatabaseObj.close();

            Toast.makeText(CreatePostActivity.this,"Post Created Successfully", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(CreatePostActivity.this, ShowPostActivity.class);
            intent.putExtra("UserID_pass", UserIDHolder);
            intent.putExtra("GroupID_pass", GroupIDHolder);
            startActivity(intent);
        }
        else {
            Toast.makeText(CreatePostActivity.this, "Please Fill All The Details ", Toast.LENGTH_LONG).show();
        }
    }

    public void EmptyEditTextAfterDataInsert() {
        PostDescription.getText().clear();
    }
}

