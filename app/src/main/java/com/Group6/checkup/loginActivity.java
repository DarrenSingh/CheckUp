package com.Group6.checkup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class loginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText editText_id = findViewById(R.id.editText_id);
        final EditText editText_password = findViewById(R.id.editText_password);
        Button button_login = findViewById(R.id.button_login);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = editText_id.getText().toString();
                String password = editText_password.getText().toString();
                String empty = "";

                if(id.equals(empty)){
                    Toast.makeText(loginActivity.this, "Please Enter User Id",Toast.LENGTH_LONG).show();

                }else if(password.equals(empty)){
                    Toast.makeText(loginActivity.this, "Please Enter User Password",Toast.LENGTH_LONG).show();

                }else if(id.charAt(0)=='D'||id.charAt(0)=='P'||id.charAt(0)=='A'||id.charAt(0)=='C'){
                    if(id.charAt(0)=='D'){
                        startActivity(new Intent(loginActivity.this, doctorActivity.class));
                    }
                    if(id.charAt(0)=='P'){
                        //startActivity(new Intent(MainActivity.this, patientActivity.class));
                    }
                    if(id.charAt(0)=='A'){
                        //startActivity(new Intent(MainActivity.this, adminActivity.class));
                    }
                    if(id.charAt(0)=='C'){
                        //startActivity(new Intent(MainActivity.this, cashierActivity.class));
                    }
                }else{
                    Toast.makeText(loginActivity.this, "Please Enter Valid User ID", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
