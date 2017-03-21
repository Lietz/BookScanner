package com.example.bookscanner;


import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.util.List;

import static android.R.attr.author;

/**
 * Created by CJ on 2017/3/20.
 */

public class Book extends DataSupport{
   // private Book(){};
  //  private static final Book book=new Book();
//    public static Book getInstance(){
//        return book;
//    }




    private String[] author;//list
    private String title;
    private String image;
    private String subtitle;
    private String pubdate;
    private String pages;
    private String price;
    private String binding;
    private String isbn;
    private String summary;
    private String publisher;

    public String getAuthors() {
        return authors;
    }

    private String authors;//注意一下

    @Column(unique=true,nullable = false)
    private String url;
    private String mobile_link ;
    private String alt;

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getMobile_link() {
        return mobile_link;
    }

    public void setMobile_link(String mobile_link) {
        this.mobile_link = mobile_link;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String[] getAuthor() {
        return author;
    }
    public String getStringAuthors() {

        StringBuilder au = new StringBuilder();
        for (int i=0;i<author.length;i++){
            au.append(author[i]);
        }
        String stringauthors=au.toString();
        return stringauthors;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }



    public void setAuthor(String[] author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }


    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
//
//
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
