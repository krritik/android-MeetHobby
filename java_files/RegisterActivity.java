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

public class RegisterActivity extends AppCompatActivity {
    EditText Username, Email, Password, Hall, RoomNo;
    Button Register;
    String UsernameHolder, EmailHolder, PasswordHolder, HallHolder, RoomNoHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    String SQLiteDataBaseQueryHolder;
    DatabaseHelper databaseHelper;
    Cursor cursor;
    String F_Result = "Not_Found";
    public static final String UserName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Register = (Button)findViewById(R.id.buttonRegister);

        Username = (EditText)findViewById(R.id.editName);
        Email = (EditText)findViewById(R.id.editEmail);
        Password = (EditText)findViewById(R.id.editPassword);
        Hall = (EditText)findViewById(R.id.editHall);
        RoomNo = (EditText)findViewById(R.id.editRoomNo);

        databaseHelper = new DatabaseHelper(this);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDataBaseBuild();
                SQLiteTableBuild();

                CheckEditTextStatus();
                CheckUserAlreadyExistsOrNot();

                EmptyEditTextAfterDataInsert();
            }
        });
    }

    public void SQLiteDataBaseBuild() {
        sqLiteDatabaseObj = openOrCreateDatabase(databaseHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);
    }

    public void SQLiteTableBuild() {
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + databaseHelper.TABLE1_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, Username TEXT UNIQUE NOT NULL, EmailID TEXT NOT NULL, HallName TEXT, HallRoomNo TEXT, Password TEXT NOT NULL)");
    }

    public void CheckEditTextStatus() {
        UsernameHolder = Username.getText().toString();
        EmailHolder = Email.getText().toString();
        PasswordHolder = Password.getText().toString();
        HallHolder = Hall.getText().toString();
        RoomNoHolder = RoomNo.getText().toString();

        if(TextUtils.isEmpty(UsernameHolder) || TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)) {
            EditTextEmptyHolder = false;
        }
        else {
            EditTextEmptyHolder = true;
        }
    }

    public void EmptyEditTextAfterDataInsert() {
        Username.getText().clear();
        Email.getText().clear();
        Password.getText().clear();
        Hall.getText().clear();
        RoomNo.getText().clear();
    }

    public void CheckUserAlreadyExistsOrNot() {
        sqLiteDatabaseObj = databaseHelper.getWritableDatabase();
        cursor = sqLiteDatabaseObj.query(databaseHelper.TABLE1_NAME, null, " "+databaseHelper.Profile_COL_2 + "=?", new String[]{UsernameHolder}, null, null, null);

        while(cursor.moveToNext()) {
            cursor.moveToFirst();
            F_Result = "User Found";
            cursor.close();
        }

        CheckFinalResult();
    }

    public void CheckFinalResult() {
        if(F_Result.equalsIgnoreCase("User Found")) {
            Toast.makeText(RegisterActivity.this, "Username Already Exists", Toast.LENGTH_LONG).show();
        }
        else {
            InsertDataIntoSQLiteDatabase();
        }
        F_Result = "Not_Found";
    }

    public void InsertDataIntoSQLiteDatabase() {
        if(EditTextEmptyHolder == true) {
            SQLiteDataBaseQueryHolder = "INSERT INTO " +databaseHelper.TABLE1_NAME+ " (Username, EmailID, Password, HallName, HallRoomNo) VALUES('"+UsernameHolder+"', '"+EmailHolder+"', '"+PasswordHolder+"', '"+HallHolder+"', '"+RoomNoHolder+"');";
            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);
            sqLiteDatabaseObj.close();
            Toast.makeText(RegisterActivity.this,"User Registered Successfully", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(RegisterActivity.this, DashboardActivity.class);
            intent.putExtra(UserName, UsernameHolder);
            startActivity(intent);
        }
        else {
            Toast.makeText(RegisterActivity.this, "Please Fill All The Details ", Toast.LENGTH_LONG).show();
        }
    }
}

