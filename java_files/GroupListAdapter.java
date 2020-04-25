package com.example.meethobby;

import android.content.Context;
import android.content.Intent;
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

public class GroupListAdapter  extends ArrayAdapter<Group_product> {
    List<Group_product> groupList;
    Context context;
    int resource;
    int userId;

    public GroupListAdapter(Context context, int resource, List<Group_product> groupList, int userId) {
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
        Button buttonOpenGroup = view.findViewById(R.id.buttonOpenGroup);

        Group_product temp_gp = groupList.get(position);
        final int groupID = temp_gp.getGroupID();
        textViewName.setText(temp_gp.getName());
        textViewDescription.setText(temp_gp.getDescription());

        buttonOpenGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context,"Entering Group "+ groupID, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(context,ShowPostActivity.class);
                intent.putExtra("UserID_pass", userId);
                intent.putExtra("GroupID_pass", groupID);

                context.startActivity(intent);
            }
        });

        return view;
    }

}
