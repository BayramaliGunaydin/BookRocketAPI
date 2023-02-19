package com.example.library.Response;

import com.example.library.Model.Book;
import lombok.Data;

@Data
public class MostPostedResponse {

    Long id;
    String author;
    String bookname;
    String topicbook;
    byte[] pic;

    int postCount;

    public MostPostedResponse(Book entity,int postCount){
        id=entity.getid();
        author=entity.getauthor();
        bookname=entity.getbookname();
        topicbook=entity.gettopic();
        pic=entity.getPic();
        this.postCount=postCount;
    }
}
