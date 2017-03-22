package com.example.bookscanner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.util.List;

import static com.example.bookscanner.InformationActivity.getHttpBitmap;

public class ShowBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_book);
        String bookTitle = getIntent().getStringExtra("Booktitle");
        List<Book> books = DataSupport.where("title=?", bookTitle).find(Book.class);
        final Book book=books.get(0);
        Button backButton=(Button)findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Log.d("bookdbauthors",book.getAuthors());
       // Bitmap bitmap=getHttpBitmap(book.getImage());
      //  book.setBitmap(bitmap);
       //  Log.d("bookimage",book.getBitmap().toString());
        setView(book);
    }
    public  void setView(Book book){
        TextView titleTextView=(TextView)findViewById(R.id.title_textview);
        TextView subtitleTextView=(TextView)findViewById(R.id.subtitle_textview);
        TextView pubdateTextView=(TextView)findViewById(R.id.pubdate_textview);
        TextView pagesTextView=(TextView)findViewById(R.id.pages_textview);
        TextView priceTextView=(TextView)findViewById(R.id.price_textview);
        TextView bindingTextView=(TextView)findViewById(R.id.binding_textview);
        TextView summaryTextView=(TextView)findViewById(R.id.summary_textview);
        TextView isbnTextView = (TextView) findViewById(R.id.isbn_textview);
        TextView publisherTextView=(TextView)findViewById(R.id.publisher_textview);
        ImageView coverImageView=(ImageView)findViewById(R.id.cover_imageview);
        TextView authorTextView=(TextView)findViewById(R.id.author_textview);
        Button enterdoubanButton=(Button)findViewById(R.id.enter_douban_button);
        final String bookurl=book.getAlt();//
        Log.d("bookurl",bookurl);//
        enterdoubanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(bookurl));
                startActivity(intent);


            }
        });

        titleTextView.setText(book.getTitle());
        isbnTextView.setText(book.getIsbn());
        subtitleTextView.setText(book.getSubtitle());
        pubdateTextView.setText(book.getPubdate());
        pagesTextView.setText(book.getPages());
        priceTextView.setText(book.getPrice());
        bindingTextView.setText(book.getBinding());
        summaryTextView.setText(book.getSummary());
        summaryTextView.setMovementMethod(ScrollingMovementMethod.getInstance());
        publisherTextView.setText(book.getPublisher());
        authorTextView.setText(book.getAuthors());
        //coverImageView.setImageResource();



    }
}
