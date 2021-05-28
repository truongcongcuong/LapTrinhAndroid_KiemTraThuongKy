package com.example.laptrinhandroid_kiemtrathuongky;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.NameViewHolder> {
    List<User> users;
    LayoutInflater inflater;

    public UserAdapter(List<User> users, Context context) {
        this.users = users;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public UserAdapter.NameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.line_item,parent,false);
        return new NameViewHolder(view,this);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.NameViewHolder holder, int position) {
        User user = users.get(position);
        holder.txt_full_name.setText(user.getFirstName()+" "+ user.getLastName());
        holder.txt_age.setText("Age : " + user.getAge());

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class NameViewHolder extends RecyclerView.ViewHolder {
        UserAdapter adapter;
        TextView txt_full_name,txt_age;
        public NameViewHolder(@NonNull View itemView, UserAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            txt_full_name = itemView.findViewById(R.id.txt_full_name);
            txt_age = itemView.findViewById(R.id.txt_age);

        }
    }
}
