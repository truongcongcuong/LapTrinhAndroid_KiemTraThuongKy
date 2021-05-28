package com.example.laptrinhandroid_kiemtrathuongky;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    EditText edt_first_name,edt_last_name,edt_age;
    Button btn_add,btn_update,btn_cancel;
    RecyclerView rcv_users;
    List<User> users;
    TextView txt_id;
    UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url = "https://60ad9ae180a61f00173313b8.mockapi.io/user";

        rcv_users = findViewById(R.id.rcv_users);
        edt_first_name = findViewById(R.id.edt_first_name);
        edt_last_name = findViewById(R.id.edt_last_name);
        edt_age = findViewById(R.id.edt_age);
        btn_add = findViewById(R.id.btn_add);
        btn_update = findViewById(R.id.btn_update);
        btn_cancel = findViewById(R.id.btn_cancel);
        txt_id = findViewById(R.id.txt_id);

        edt_first_name.setHint("First Name");
        edt_last_name.setHint("Last Name");
        edt_age.setHint("Age");

        btn_add.setText("Add");
        btn_update.setText("Update");
        btn_cancel.setText("Cancel");


        getDataFromMockAPI(url,this);

//        adapter = new UserAdapter(users,this);
//        rcv_users.setAdapter(adapter);
//        rcv_users.setLayoutManager(new GridLayoutManager(this ,1));
        
        btn_add.setOnClickListener(v->{
            String txt_lName = edt_last_name.getText().toString();
            if(TextUtils.isEmpty(txt_lName)){
                Toast.makeText(MainActivity.this,"Nhap Last Name....",Toast.LENGTH_SHORT).show();
                return;
            }
            String txt_fName = edt_first_name.getText().toString();
            if(TextUtils.isEmpty(txt_fName)){
                Toast.makeText(MainActivity.this,"Nhap Last Name....",Toast.LENGTH_SHORT).show();
                return;
            }
            String txt_age = edt_age.getText().toString();
            if(TextUtils.isEmpty(txt_age)){
                Toast.makeText(MainActivity.this,"Nhap Last Name....",Toast.LENGTH_SHORT).show();
                return;
            }
            
            postAPI(url);
            getDataFromMockAPI(url,this);

        });
        
        btn_update.setOnClickListener(v->{
            if(!TextUtils.isEmpty(txt_id.getText().toString()))
                putAPI(url);

            else
                Toast.makeText(MainActivity.this, "Chon doi tuong update ....", Toast.LENGTH_SHORT).show();
            txt_id.setText("");
            getDataFromMockAPI(url,this);

        });



    }

    private void putAPI(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url + "/" + txt_id.getText().toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error by Put data!", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("firstName",edt_first_name.getText().toString());
                map.put("lastName",edt_last_name.getText().toString());
                map.put("age",edt_age.getText().toString());
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        getDataFromMockAPI(url,this);

    }

    private void postAPI(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error by Post data!", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>map = new HashMap<>();
                map.put("firstName",edt_first_name.getText().toString());
                map.put("lastName",edt_last_name.getText().toString());
                map.put("age",edt_age.getText().toString());
                return  map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void getDataFromMockAPI(String url, Context context) {
        users = new ArrayList<>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>(){

                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i =0 ; i<response.length();i++){
                            try {
                                JSONObject jsonObject = (JSONObject) response.get(i);
                                User user = new User();
                                user.setId(jsonObject.getString("id"));
                                user.setFirstName(jsonObject.getString("firstName"));
                                user.setLastName(jsonObject.getString("lastName"));
                                user.setAge(jsonObject.getInt("age"));

                                users.add(user);
                                adapter = new UserAdapter(users,context, MainActivity.this);
                                rcv_users.setAdapter(adapter);
                                rcv_users.setLayoutManager(new GridLayoutManager(MainActivity.this ,1));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,
                                "Error by get Json Array!", Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
    }
}