package com.example.kkeys.listview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] values = new String[]{"User Location","Dublin","Kerry","Belfast","Cork","Galway","Wexford"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, values);
        listView = (ListView)findViewById(R.id.listView);
        imageView = (ImageView)findViewById(R.id.imageView);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> listview, View row, int index, long rowID) {
                        Toast.makeText(MainActivity.this,
                                "Selected "+listview.getItemAtPosition(index),
                                Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}
