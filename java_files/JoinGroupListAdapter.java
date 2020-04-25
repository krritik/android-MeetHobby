package com.example.meethobby;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class JoinGroupListAdapter  extends ArrayAdapter<Group_product> {
    List<Group_product> groupList;
    Context context;
    int resource;
    int userId;

    public JoinGroupListAdapter(Context context, int resource, List<Group_product> groupList, int userId) {
        super(context, resource, groupList);
        this.context = context;
        this.resource = resource;
        this.groupList = groupList;
        this.userId = userId;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(resource, null, false);

        TextView textViewName = view.findViewById(R.id.textViewName);
        TextView textViewDescription = view.findViewById(R.id.textViewDescription);
        Button buttonOpenGroup = view.findViewById(R.id.buttonJoinGroup);

        Group_product temp_gp = groupList.get(position);
        final int groupID = temp_gp.getGroupID();
        textViewName.setText(temp_gp.getName());
        textViewDescription.setText(temp_gp.getDescription());



        buttonOpenGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper databaseHelper = new DatabaseHelper(context);
                SQLiteDatabase sqLiteDatabaseObj = databaseHelper.getWritableDatabase();
                String SQLiteDataBaseQueryHolder;

                SQLiteDataBaseQueryHolder = "INSERT INTO " +databaseHelper.TABLE6_NAME+ " (UserID, GroupID, Admin) VALUES('"+userId+"', '"+groupID+"', '"+ 0 +"');";
                sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);

                Toast.makeText(context,"Group "+ groupID+ " is joined. ", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(context,ShowGroupActivity.class);
                intent.putExtra("UserID_pass", userId);
                context.startActivity(intent);
            }
        });

        return view;
    }

}
