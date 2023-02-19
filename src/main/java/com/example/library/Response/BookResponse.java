package com.example.library.Response;

import com.example.library.Model.Book;
import lombok.Data;

import java.util.List;

@Data
public class BookResponse {
    Long id;
    String author;
    String bookname;
    String topicbook;
    byte[] pic;
    List<LikeResponse> likes;
    public BookResponse(Book entity,List<LikeResponse> likes){
        id=entity.getid();
        author=entity.getauthor();
        bookname=entity.getbookname();
        topicbook=entity.gettopic();
        pic=entity.getPic();
        this.likes=likes;
    }
}
