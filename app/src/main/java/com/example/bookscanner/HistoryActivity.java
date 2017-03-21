package com.example.bookscanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        if (DataSupport.isExist(Book.class)) {
            List<Book> books = DataSupport.findAll(Book.class);
            final String[] booktitle = new String[books.size()];
            for (int i = 0; i < books.size(); i++) {
                booktitle[i] = books.get(i).getTitle();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(HistoryActivity.this, android.R.layout.simple_list_item_1, booktitle);
            ListView historyListView = (ListView) findViewById(R.id.history_listview);
            historyListView.setAdapter(adapter);
            historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String title=booktitle[position];
                   // Toast.makeText(HistoryActivity.this,title,Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(HistoryActivity.this,ShowBookActivity.class);
                    intent.putExtra("Booktitle",title);
                    startActivity(intent);
                }
            });
        } else {
            Toast.makeText(this, "没有记录！", Toast.LENGTH_SHORT).show();
            finish();
        }


    }
}
