package com.example.bookscanner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;
import org.litepal.tablemanager.Connector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.LogRecord;

import static android.R.attr.author;


public class InformationActivity extends AppCompatActivity {

    public static final int SEARCH_SUCCESS = 1;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SEARCH_SUCCESS:
                    Log.d("bookauthorhandle", book.toString());
                    Log.d("bookauthorhandle", book.getAuthors());
                    Log.d("bookauthorhandle", book.getSubtitle());
                    Log.d("bookauthorhandleimage", book.getImage());
                    setView(book);
                    break;
                default:
                    break;
            }
        }
    };

    Book book = new Book();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        TextView isbnTextView = (TextView) findViewById(R.id.isbn_textview);

        //Connector.getDatabase();

        Intent intent = getIntent();
        final String result = intent.getStringExtra("code");
        isbnTextView.setText(result);

        final Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Connector.getDatabase();  //


        Search(result);
        // book.setIsbn(result);
        //  setView(book);
        Log.d("bookauthor主线程", book.toString());

    }




    public void setView(Book book) {
        TextView titleTextView = (TextView) findViewById(R.id.title_textview);
        TextView subtitleTextView = (TextView) findViewById(R.id.subtitle_textview);
        TextView pubdateTextView = (TextView) findViewById(R.id.pubdate_textview);
        TextView pagesTextView = (TextView) findViewById(R.id.pages_textview);
        TextView priceTextView = (TextView) findViewById(R.id.price_textview);
        TextView bindingTextView = (TextView) findViewById(R.id.binding_textview);
        TextView summaryTextView = (TextView) findViewById(R.id.summary_textview);
        TextView publisherTextView = (TextView) findViewById(R.id.publisher_textview);
        TextView authorTextView = (TextView) findViewById(R.id.author_textview);
        Button enterdoubanButton = (Button) findViewById(R.id.enter_douban_button);

        ImageView coverImageView = (ImageView) findViewById(R.id.cover_imageview);
        final String bookurl = book.getAlt();
        Log.d("bookurl", bookurl);
        enterdoubanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(bookurl));
                startActivity(intent);


            }
        });

        titleTextView.setText(book.getTitle());
        subtitleTextView.setText(book.getSubtitle());
        pubdateTextView.setText(book.getPubdate());
        pagesTextView.setText(book.getPages());
        priceTextView.setText(book.getPrice());
        bindingTextView.setText(book.getBinding());
        summaryTextView.setText(book.getSummary());
        summaryTextView.setMovementMethod(ScrollingMovementMethod.getInstance());
        publisherTextView.setText(book.getPublisher());
        authorTextView.setText(book.getStringAuthors());
        coverImageView.setImageBitmap(book.getBitmap());


    }

    private void Search(final String result) {
        // Book book;

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL("https://api.douban.com/v2/book/isbn/" + result);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();
                    int statueCode = connection.getResponseCode();
                    if (statueCode == 200) {
                        InputStream in = connection.getInputStream();
                        reader = new BufferedReader(new InputStreamReader(in));
                        //  JsonReader jsonReader=new JsonReader(reader);
                        //  parseJSONWithGSON(jsonReader);
//                        JSONArray jsonArray=new JSONArray();
//                        jsonReader.beginArray();
//                        while (jsonReader.hasNext()){
//                            JSONObject
                        //  }
//                        StringBuilder response = new StringBuilder();
//                        String line;
//                        while ((line=reader.readLine())!=null){
//                            response.append(line);
//                        }
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        String jsonData = response.toString();


                        //   Log.d("responsejson",jsonData);
                        //  Integer i=jsonData.length();
                        //   Log.d("responsejsondata",new String(i.toString()));
                        parseJSONWithGSON(jsonData);
                        Log.d("bookauthor线程", book.toString());

                        book.setIsbn(result);
                        book.setAuthors(book.getStringAuthors());
                        Bitmap bitmap=getHttpBitmap(book.getImage());
                        book.setBitmap(bitmap);
                        Log.d("bookauthor线程authors", book.toString());
                        Log.d("bookauthorbitmap", book.getBitmap().toString());
                        book.save();
                        Message message = new Message();
                        message.what = SEARCH_SUCCESS;
                        message.obj = book;
                        // Bundle themessage=new Bundle();
                        // themessage.putString("themessage",jsonData);
                        //  message.setData(themessage);
                        handler.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    private void parseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
        book = gson.fromJson(jsonData, Book.class);//失败了，下一句执行。。。
        Log.d("bookauthor", book.getSubtitle().toString());
        Log.d("bookauthor解析method", book.toString());
        Log.d("parse", "parse succeed maybe");
        // book.setIsbn(result);
        //  book.save();
        // setThisbook(thisbook);
        // List<Book> bookList=gson.fromJson(jsonData,new TypeToken<List<Book>>(){}.getType());
//        for (Book book:bookList){
//            book.setAuthor(book.getAuthor());
//            book.setBinding(book.getBinding());
//            book.setImage(book.getImage());
//            book.setPages(book.getPages());
//            book.setTitle(book.getTitle());
//            book.setSubtitle(book.getSubtitle());
//            book.setSummary(book.getSummary());
//            book.setPubdate(book.getPubdate());
//            book.setPrice(book.getPrice());
//            book.setPublisher(book.getPublisher());
        //  }
    }
    public static Bitmap getHttpBitmap(String url){
        URL myFileURL;
        Bitmap bitmap=null;
        try{
            myFileURL=new URL(url);
            HttpURLConnection connection=(HttpURLConnection)myFileURL.openConnection();
            connection.setConnectTimeout(6000);
          //  connection.setDoInput(true);
            //connection.setUseCaches(false);
            connection.connect();
            InputStream ins=connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(ins);
            ins.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }
}


