package com.example.meethobby;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ShowPostActivity extends AppCompatActivity {
    List<Post_product> heroList;
    ListView listView;
    String SQLiteDataBaseQueryHolder;
    int postId, postUserID;
    String postDescription, postUser, postPostTime;

    SQLiteDatabase sqLiteDatabaseObj;
    DatabaseHelper databaseHelper;
    int UserIDHolder, GroupIDHolder;
    Cursor cursor, cursor_2;
    Button CreatePost, buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_post);


        heroList = new ArrayList<>();

        Intent intent = getIntent();
        UserIDHolder = intent.getIntExtra("UserID_pass", -1);
        GroupIDHolder = intent.getIntExtra("GroupID_pass", -1);

        listView = (ListView) findViewById(R.id.listViewPost);
        CreatePost = (Button) findViewById(R.id.buttonCreatePost);
        buttonBack = (Button) findViewById(R.id.buttonBack);

        databaseHelper = new DatabaseHelper(this);
        sqLiteDatabaseObj = databaseHelper.getWritableDatabase();
        cursor = sqLiteDatabaseObj.rawQuery("SELECT p.PostID, p.PostDescription, p.PostTime, p.UserID FROM Post p, HasPost hp where p.PostID= hp.PostID and hp.GroupID="+GroupIDHolder, null);

        cursor.moveToFirst();

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        while(cursor.isAfterLast() == false) {
            postId = cursor.getInt(cursor.getColumnIndex("PostID"));
            postDescription= cursor.getString(cursor.getColumnIndex("PostDescription"));
            postPostTime = cursor.getString(cursor.getColumnIndex("PostTime"));
            postUserID = cursor.getInt(cursor.getColumnIndex("UserID"));

            cursor_2 = sqLiteDatabaseObj.rawQuery("SELECT p.Username from Profile p where p.ID = "+ postUserID,  null);
            cursor_2.moveToFirst();
            postUser = cursor_2.getString(cursor_2.getColumnIndex("Username"));
            cursor_2.close();

            heroList.add(new Post_product(postId, postDescription, postUser, postPostTime));
            cursor.moveToNext();
        }
        cursor.close();

        PostListAdapter adapter = new PostListAdapter(this, R.layout.my_custom_list_post, heroList);
        listView.setAdapter(adapter);

        CreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(ShowPostActivity.this, CreatePostActivity.class);
                intent.putExtra("UserID_pass", UserIDHolder);
                intent.putExtra("GroupID_pass", GroupIDHolder);
                startActivity(intent);
                Toast.makeText(ShowPostActivity.this,"new Post area entered", Toast.LENGTH_LONG).show();
            }
        });
    }
}
