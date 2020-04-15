package com.example.meethobby;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class PostListAdapter extends ArrayAdapter<Post_product> {
    List<Post_product> postList;
    Context context;
    int resource;

    public PostListAdapter(Context context, int resource, List<Post_product> postList) {
        super(context, resource, postList);
        this.context = context;
        this.resource = resource;
        this.postList = postList;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(resource, null, false);

        Post_product temp_post = postList.get(position);

        TextView textViewDescription = view.findViewById(R.id.textViewDescription);
        TextView textViewUser = view.findViewById(R.id.textViewUser);
        TextView textViewPostTime = view.findViewById(R.id.textViewPostTime);

        textViewDescription.setText(temp_post.getDescription());
        textViewPostTime.setText(temp_post.getPostTime());
        textViewUser.setText(temp_post.getUser());
        return view;
    }
}
