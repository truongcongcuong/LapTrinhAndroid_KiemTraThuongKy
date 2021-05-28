package com.example.laptrinhandroid_kiemtrathuongky;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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


public class MainActivity extends AppCompatActivity {

    EditText edt_first_name,edt_last_name,edt_age;
    Button btn_add,btn_update,btn_cancel;
    RecyclerView rcv_users;
    UserAdapter adapter;
    List<User> users;

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


        getDataFromMockAPI(url);
        adapter = new UserAdapter(users,this);
        rcv_users.setAdapter(adapter);
        rcv_users.setLayoutManager(new GridLayoutManager(this ,1));


    }

    private void getDataFromMockAPI(String url) {
        users = new ArrayList<>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>(){

                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i =0 ; i<response.length();i++){
                            try {
                                JSONObject jsonObject = (JSONObject) response.get(i);
                                User user = new User();
                                user.setFirstName(jsonObject.getString("firstName"));
                                user.setLastName(jsonObject.getString("lastName"));
                                user.setAge(jsonObject.getInt("age"));

                                MainActivity.this.users.add(user);
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
        requestQueue.add(jsonArrayRequest);
    }
}