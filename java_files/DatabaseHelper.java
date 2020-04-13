package com.example.meethobby;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MeetHobby";

    public static final String TABLE1_NAME = "Profile";
    public static final String Profile_COL_1 = "ID";
    public static final String Profile_COL_2 = "Username";
    public static final String Profile_COL_3 = "EmailID";
    public static final String Profile_COL_4 = "HallName";
    public static final String Profile_COL_5 = "HallRoomNo";
    public static final String Profile_COL_6 = "Password";

    public static final String TABLE2_NAME = "PhoneNo";
    public static final String UserID_COL = "UserID";
    public static final String PhoneNo_COL_2 = "Phone";

    public static final String TABLE3_NAME = "Post";
    public static final String Post_COL_1 = "PostID";
    public static final String Post_COL_2 = "PostDescription";
    public static final String Post_COL_3 = "PostTime";

    public static final String TABLE4_NAME = "Groups";
    public static final String Group_COL_1 = "GroupID";
    public static final String Group_COL_2 = "GroupName";
    public static final String Group_COL_3 = "GroupDescription";
    public static final String Group_COL_4 = "CreationTime";

    public static final String TABLE5_NAME = "HasPost";

    public static final String TABLE6_NAME = "GroupMembers";
    public static final String GroupMembers_COL_3 = "NoOfPost";
    public static final String GroupMembers_COL_4 = "Admin";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE1_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, Username TEXT UNIQUE NOT NULL, EmailID TEXT NOT NULL, HallName TEXT, HallRoomNo TEXT, Password TEXT NOT NULL)");
        db.execSQL("create table " + TABLE2_NAME +" (UserID INTEGER, Phone TEXT NOT NULL, PRIMARY KEY(UserID, Phone), FOREIGN KEY(UserID) REFERENCES " + TABLE1_NAME + "(ID))");
        db.execSQL("create table " + TABLE3_NAME +" (PostID INTEGER PRIMARY KEY AUTOINCREMENT, UserID INTEGER, PostDescription TEXT, PostTime TEXT, FOREIGN KEY(UserID) REFERENCES " + TABLE1_NAME + "(ID))");
        db.execSQL("create table " + TABLE4_NAME +" (GroupID INTEGER PRIMARY KEY AUTOINCREMENT,GroupName TEXT,  GroupDescription TEXT, CreationTime TEXT)");
        db.execSQL("create table " + TABLE5_NAME +" (UserID INTEGER , PostID INTEGER, PRIMARY KEY(UserID, PostID), FOREIGN KEY(UserID) REFERENCES " + TABLE1_NAME + "(ID), FOREIGN KEY(PostID) REFERENCES " + TABLE3_NAME + "(PostID))");
        db.execSQL("create table " + TABLE6_NAME +" (UserID INTEGER , GroupID INTEGER, NoOfPost INTEGER, Admin INTEGER, PRIMARY KEY(UserID, GroupID), FOREIGN KEY(UserID) REFERENCES " + TABLE1_NAME + "(ID), FOREIGN KEY(GroupID) REFERENCES " + TABLE4_NAME + "(GroupID))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE1_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE2_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE3_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE4_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE5_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE6_NAME);
        onCreate(db);
    }

//    public boolean insert_TABLE1(String Name , String EmailID, String HallName, String HallRoomNo) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(Profile_COL_2, Name);
//        contentValues.put(Profile_COL_3, EmailID);
//        contentValues.put(Profile_COL_4, HallName);
//        contentValues.put(Profile_COL_5, HallRoomNo);
//        long result = db.insert(TABLE1_NAME, null, contentValues);
//        if(result == -1)
//            return false;
//        else
//            return true;
//    }

//    public boolean insert_TABLE2(int UserID, String Phone) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(UserID_COL, UserID);
//        contentValues.put(PhoneNo_COL_2, Phone);
//        long result = db.insert(TABLE2_NAME,null ,contentValues);
//        if(result == -1)
//            return false;
//        else
//            return true;
//    }

//    public boolean insert_TABLE3(String PostDescription,String PostTime, int UserID) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(Post_COL_2, PostDescription);
//        contentValues.put(Post_COL_3, PostTime);
//        contentValues.put(UserID_COL,UserID);
//        long result = db.insert(TABLE3_NAME,null ,contentValues);
//        if(result == -1)
//            return false;
//        else
//            return true;
//    }

//    public boolean insert_TABLE4(String GroupName,String GroupDescription,String CreationTime) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(Group_COL_2, GroupName);
//        contentValues.put(Group_COL_3, GroupDescription);
//        contentValues.put(Group_COL_4, CreationTime);
//        long result = db.insert(TABLE4_NAME,null ,contentValues);
//        if(result == -1)
//            return false;
//        else
//            return true;
//    }

//    public boolean insert_TABLE5(int GroupID, int PostID) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(Group_COL_1, GroupID);
//        contentValues.put(Post_COL_1, PostID);
//        long result = db.insert(TABLE5_NAME,null ,contentValues);
//        if(result == -1)
//            return false;
//        else
//            return true;
//    }

//    public boolean insert_TABLE6(int UserID, int GroupID, int NoOfPost, int Admin) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(UserID_COL, UserID);
//        contentValues.put(Group_COL_1, GroupID);
//        contentValues.put(GroupMembers_COL_3, NoOfPost);
//        contentValues.put(GroupMembers_COL_4, Admin);
//        long result = db.insert(TABLE6_NAME,null ,contentValues);
//        if(result == -1)
//            return false;
//        else
//            return true;
//    }
}