package com.example.meethobby;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button LogInButton, RegisterButton ;
    EditText Username, Password ;
    String UsernameHolder, PasswordHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    DatabaseHelper databaseHelper;
    Cursor cursor;
    String TempPassword = "NOT_FOUND";
    public static final String UserName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LogInButton = (Button)findViewById(R.id.buttonLogin);
        RegisterButton = (Button)findViewById(R.id.buttonRegister);

        Username = (EditText)findViewById(R.id.editName);
        Password = (EditText)findViewById(R.id.editPassword);

        databaseHelper = new DatabaseHelper(this);

        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckEditTextStatus();
                LoginFunction();
            }
        });

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public void CheckEditTextStatus() {
        UsernameHolder = Username.getText().toString();
        PasswordHolder = Password.getText().toString();

        if(TextUtils.isEmpty(UsernameHolder) || TextUtils.isEmpty(PasswordHolder)) {
            EditTextEmptyHolder = false;
        }
        else {
            EditTextEmptyHolder = true;
        }
    }

    public void LoginFunction() {
        if(EditTextEmptyHolder) {
            sqLiteDatabaseObj = databaseHelper.getWritableDatabase();

            cursor = sqLiteDatabaseObj.query(databaseHelper.TABLE1_NAME, null, " "+databaseHelper.Profile_COL_2 + "=?", new String[]{UsernameHolder}, null, null, null);
            while(cursor.moveToNext()) {
                cursor.moveToFirst();
                TempPassword = cursor.getString(cursor.getColumnIndex(databaseHelper.Profile_COL_6));
                cursor.close();
            }
            CheckFinalResult();
        }
        else {
            Toast.makeText(MainActivity.this, "Please Enter Username or Password.", Toast.LENGTH_LONG).show();
        }
    }

    public void CheckFinalResult() {
        if(TempPassword.equals(PasswordHolder)) {
            Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
            intent.putExtra(UserName, UsernameHolder);
            startActivity(intent);
        }
        else {
            Toast.makeText(MainActivity.this, "Username or Password is Wrong, Please Try Again.", Toast.LENGTH_LONG).show();
        }
        TempPassword = "NOT_FOUND";
    }
}


