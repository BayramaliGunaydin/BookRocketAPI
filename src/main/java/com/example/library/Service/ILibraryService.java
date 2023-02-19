package com.example.library.Service;

import com.example.library.Model.*;
import com.example.library.Response.BookResponse;
import com.example.library.Response.MostPostedResponse;

import java.util.List;
import java.util.Optional;

public interface ILibraryService {
    public List<BookResponse> getall();

    public void addbook(Book newbook);
    public Optional<Book> getsingle(Long id);
    public void delete(Long id);
    public Book update(Long id, Book book);
   /* public void messagesend(String message);
    public void messagesend2(String message) throws InterruptedException;*/
    public void addbooktouser(Long id,Long bookid);
    public List<Book> userbooks(Long id);
    public void deletebookfromuser(Long id, Long bookid);
    public List<Post> userposts(Long id);
    public List<Post> bookposts(Long id);
    public CustomUser getuser(Long id);
    public void addposttobook(Post post);

    public void saveuserimage(Long id,base64 base64);
    public List<Like> userlikes(Long id);
    public List<BookResponse> getMostLikedBooks();
    public List<MostPostedResponse> getMostPostedBooks();
}
