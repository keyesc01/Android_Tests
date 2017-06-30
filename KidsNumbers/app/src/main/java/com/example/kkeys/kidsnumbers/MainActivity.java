package com.example.kkeys.kidsnumbers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button btn1;
    Button btn2;
    TextView counter;
    int clickCount = 0;

    Random randd = new Random();
    int value =randd.nextInt(100)+1;
    int value2 =randd.nextInt(100)+1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = (Button)findViewById(R.id.btn1);
        counter = (TextView)findViewById(R.id.counter);
        btn2 = (Button)findViewById(R.id.btn2);
        counter = (TextView)findViewById(R.id.counter);



        btn1.setText(""+value);
        btn2.setText(""+value2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int value = randd.nextInt(100)+1;

                int num1 = Integer.parseInt(btn1.getText().toString());
                int num2 = Integer.parseInt(btn2.getText().toString());

                if(num1 > num2) {
                    clickCount++;
                    counter.setText("" + clickCount);
                }
                else{
                    clickCount--;
                    counter.setText("" + clickCount);
                }
                btn1.setText("");
                btn1.setText(""+value);
                btn2.setText(""+value2);

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //Random randd = new Random();
            int value2 = randd.nextInt(100)+1;

            int num1 = Integer.parseInt(btn1.getText().toString());
            int num2 = Integer.parseInt(btn2.getText().toString());

            if(num2 > num1) {
                clickCount++;
                counter.setText("" + clickCount);
            }
            else{
                clickCount--;
                counter.setText("" + clickCount);
            }
                btn2.setText("");
                btn1.setText(""+value);
                btn2.setText(""+value2);
            }
        });
    }
}
