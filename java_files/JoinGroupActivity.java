package com.example.meethobby;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class JoinGroupActivity extends AppCompatActivity {
    List<Group_product> heroList;
    ListView listView;
    String SQLiteDataBaseQueryHolder;
    int groupId;
    String groupName, groupDescription;

    SQLiteDatabase sqLiteDatabaseObj;
    DatabaseHelper databaseHelper;
    int UserIDHolder;
    Cursor cursor;
    Button buttonHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group);

        heroList = new ArrayList<>();

        Intent intent = getIntent();
        UserIDHolder = intent.getIntExtra("UserID_pass", 0);
        listView = (ListView) findViewById(R.id.listView);
        buttonHome = (Button) findViewById(R.id.buttonHome);

        databaseHelper = new DatabaseHelper(this);
        sqLiteDatabaseObj = databaseHelper.getWritableDatabase();
        cursor = sqLiteDatabaseObj.rawQuery("SELECT GroupID,GroupName, GroupDescription from Groups  where GroupID not in (SELECT g.GroupID FROM Groups g, GroupMembers gm where g.GroupID = gm.GroupID and gm.UserID= " + UserIDHolder + ") ", null);

        cursor.moveToFirst();

        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        while (cursor.isAfterLast() == false) {
            groupId = cursor.getInt(cursor.getColumnIndex("GroupID"));
            groupName = cursor.getString(cursor.getColumnIndex("GroupName"));
            groupDescription = cursor.getString(cursor.getColumnIndex("GroupDescription"));

            heroList.add(new Group_product(groupId, groupName, groupDescription));
            cursor.moveToNext();
        }
        cursor.close();

        JoinGroupListAdapter adapter = new JoinGroupListAdapter(this, R.layout.custom_list_join_group, heroList, UserIDHolder);
        listView.setAdapter(adapter);
    }
}
