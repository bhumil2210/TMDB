package com.example.bhumil.testing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by bhumil on 13/2/18.
 */

public class MovieDescription extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView movietitle,description;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description);
        movietitle = (TextView)findViewById(R.id.name);
        description = (TextView)findViewById(R.id.description);
        Intent intent = getIntent();
        String title = intent.getStringExtra("movietitle");
        String desc= intent.getStringExtra("moviedesc");
        movietitle.setText(title);
        description.setText(desc);
    }
}