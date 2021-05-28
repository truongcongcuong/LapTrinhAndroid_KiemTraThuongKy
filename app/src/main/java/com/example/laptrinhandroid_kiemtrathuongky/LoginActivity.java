package com.example.laptrinhandroid_kiemtrathuongky;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth auth;
    EditText edt_login_user,edt_login_password;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        edt_login_user = findViewById(R.id.edt_login_user);
        edt_login_password = findViewById(R.id.edt_login_password);
        btn_login = findViewById(R.id.btn_login);

        edt_login_password.setHint("Password");
        edt_login_user.setHint("User name");
        edt_login_password.setText("123456");
        edt_login_user.setText("sbmegass1@gmail.com");
        btn_login.setText("Login");

        btn_login.setOnClickListener(v->{
            String email = edt_login_user.getText().toString();
            if(TextUtils.isEmpty(email)){
                Toast.makeText(LoginActivity.this,"Nhap tai khoan .....",Toast.LENGTH_SHORT).show();
                return;
            }
            String password = edt_login_password.getText().toString();
            if(TextUtils.isEmpty(password)){
                Toast.makeText(LoginActivity.this,"Nhap mat khau .....",Toast.LENGTH_SHORT).show();
                return;
            }

            auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful())
                                Toast.makeText(LoginActivity.this,"Dang nhap that bai .....",Toast.LENGTH_SHORT).show();
                            else{
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            }
                        }
                    });
        });
    }
}