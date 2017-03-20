package com.example.bookscanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class InformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        TextView isbnTextView =(TextView)findViewById(R.id.isbn_textview);
        Intent intent=getIntent();
        String result = intent.getStringExtra("code");
        isbnTextView.setText(result);
    }
}
