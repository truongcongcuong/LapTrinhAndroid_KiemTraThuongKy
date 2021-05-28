package com.example.laptrinhandroid_kiemtrathuongky;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.NameViewHolder> {
    List<User> users;
    LayoutInflater inflater;
    Context context;
    Activity activity;
    String url = "https://60ad9ae180a61f00173313b8.mockapi.io/user";

    public UserAdapter(List<User> users,Context context, Activity activity) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.users = users;
        this.activity = activity;

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
        holder.itemView.setOnClickListener(v->{
            TextView txt_id = activity.findViewById(R.id.txt_id);
            EditText edt_first_name = activity.findViewById(R.id.edt_first_name);
            EditText edt_last_name = activity.findViewById(R.id.edt_last_name);
            EditText edt_age = activity.findViewById(R.id.edt_age);

            txt_id.setText(user.getId());
            edt_first_name.setText(user.getFirstName());
            edt_last_name.setText(user.getLastName());
            edt_age.setText(user.getAge()+"");

        });

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

    public void dataChange(){
        notifyDataSetChanged();
    }


}
