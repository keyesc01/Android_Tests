package com.example.kkeys.userinfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btn1;
    TextView name;
    TextView password;
    TextView phone;
    TextView email;
    TextView valMsg;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = (Button) findViewById(R.id.btn1);
        name = (TextView) findViewById(R.id.name);
        password = (TextView) findViewById(R.id.password);
        phone = (TextView) findViewById(R.id.phone);
        email = (TextView) findViewById(R.id.email);
        valMsg = (TextView) findViewById(R.id.valMsg);

        btn1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Thank you " + name.getText().toString() + ", your request is being processed", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
