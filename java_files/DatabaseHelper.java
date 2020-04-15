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
    public static final String GroupMembers_COL_3 = "Admin";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE1_NAME +
                " (" +  Profile_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Profile_COL_2 + " TEXT UNIQUE NOT NULL, " +
                        Profile_COL_3 + " TEXT NOT NULL, " +
                        Profile_COL_4 + " TEXT, " +
                        Profile_COL_5 + " TEXT, " +
                        Profile_COL_6 + " TEXT NOT NULL)");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE2_NAME +
                " ("+   UserID_COL + " INTEGER, " +
                        PhoneNo_COL_2 +  " TEXT, " +
                "PRIMARY KEY(" + UserID_COL+ ", " + PhoneNo_COL_2 + " ), " +
                "FOREIGN KEY(" + UserID_COL+ ") REFERENCES " + TABLE1_NAME + "(" + Profile_COL_1 + "))");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE3_NAME +
                " (" +  Post_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                        UserID_COL + " INTEGER NOT NULL, " +
                        Post_COL_2 + " TEXT NOT NULL, " +
                        Post_COL_3 + " TEXT NOT NULL, " +
                "FOREIGN KEY(" + UserID_COL+ ") REFERENCES " + TABLE1_NAME + "(" + Profile_COL_1 + "))");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE4_NAME +
                " (" +  Group_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Group_COL_2 + " TEXT NOT NULL, " +
                        Group_COL_3 + " TEXT NOT NULL, " +
                        Group_COL_4 + " TEXT NOT NULL )");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE5_NAME +
                " (" +  Group_COL_1 + " INTEGER ," +
                        Post_COL_1  + " INTEGER, " +
                "PRIMARY KEY(" + Group_COL_1+ ", "+ Post_COL_1 +"), " +
                "FOREIGN KEY(" + Group_COL_1 + ") REFERENCES " + TABLE4_NAME + "(" + Group_COL_1 + "), " +
                "FOREIGN KEY(" + Post_COL_1 + ") REFERENCES " + TABLE3_NAME + "(" + Post_COL_1 + "))");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE6_NAME +
                " (" +  UserID_COL+ " INTEGER, " +
                        Group_COL_1+ " INTEGER, " +
                        GroupMembers_COL_3+ " INTEGER NOT NULL, " +
                "PRIMARY KEY(" + UserID_COL + ", " + Group_COL_1 +"), " +
                "FOREIGN KEY(" + UserID_COL + ") REFERENCES " + TABLE1_NAME + "(" + Profile_COL_1 + "), " +
                "FOREIGN KEY(" + Group_COL_1 + ") REFERENCES " + TABLE4_NAME + "(" + Group_COL_1 + "))");
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
}
