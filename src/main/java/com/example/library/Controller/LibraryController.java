package com.example.library.Controller;

import com.example.library.Model.*;
import com.example.library.Repository.BookRepository;
import com.example.library.Repository.UserRepository;
import com.example.library.Response.*;
import com.example.library.Service.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class LibraryController {
    @Autowired
    private ILibraryService libraryService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IMyUserDetailsService userDetailsService;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ILikeService likeService;

    @JsonFormat(pattern="yyyy-MM-dd-HH-mm-ss")
    private Date date=new Date();

    @GetMapping("/getallbooks")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity getall() {
        try {
            List<BookResponse> bookList = libraryService.getall();
            return new ResponseEntity<>(bookList, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new Response(406,"Kitap listesi getirirken hata"), HttpStatus.OK);
        }


    }

    @PostMapping("/booknamesearch")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity searchbookname(@RequestBody Book book) {
        try{
            List<BookResponse> bookList = libraryService.getall();
            List<BookResponse> searchedList = new ArrayList<>();
            for(int i =0 ;i<bookList.size();i++){
                if(bookList.get(i).getBookname().toLowerCase().contains(book.getbookname().toLowerCase())){
                    searchedList.add(bookList.get(i));
                }
            }
            if(searchedList.size() == 0){
                return new ResponseEntity<>(new Response(404,"Aradığınız kriterde kitap bulunamadı"), HttpStatus.NOT_FOUND);
            }
            else{
                return new ResponseEntity<>(searchedList, HttpStatus.OK);
            }
        }
        catch (Exception e){
            return new ResponseEntity<>(new Response(406,"Kitap araması yaparken hata"), HttpStatus.NOT_ACCEPTABLE);
        }


    }
    @PostMapping("/booktopicsearch")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity searchbooktopic(@RequestBody Book book) {
        try{
            List<BookResponse> bookList = libraryService.getall();
            List<BookResponse> searchedList = new ArrayList<>();
            for(int i =0 ;i<bookList.size();i++){
                if(bookList.get(i).getTopicbook().toLowerCase().contains(book.gettopic().toLowerCase())){
                    searchedList.add(bookList.get(i));
                }
            }
            if(searchedList.size() == 0){
                return new ResponseEntity<>(new Response(404,"Aradığınız kriterde kitap bulunamadı"), HttpStatus.NOT_FOUND);
            }
            else{
                return new ResponseEntity<>(searchedList, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>(new Response(406,"Kitap araması yaparken hata"), HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @PostMapping("/bookauthorsearch")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity searchbookauthor(@RequestBody Book book) {
        try{
            List<BookResponse> bookList = libraryService.getall();
            List<BookResponse> searchedList = new ArrayList<>();
            for(int i =0 ;i<bookList.size();i++){
                if(bookList.get(i).getAuthor().toLowerCase().contains(book.getauthor().toLowerCase())){
                    searchedList.add(bookList.get(i));
                }
            }
            if(searchedList.size() == 0){
                return new ResponseEntity<>(new Response(404,"Aradığınız kriterde kitap bulunamadı"), HttpStatus.NOT_FOUND);
            }
            else{
                return new ResponseEntity<>(searchedList, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>(new Response(406,"Kitap araması yaparken hata"), HttpStatus.NOT_ACCEPTABLE);
        }

    }
    /*@PostMapping("/message")
    public void getmessage(@RequestBody CustomMessage message){
        libraryService.messagesend(message.getMessage());
    }
    @PostMapping("/message2")
    public void getmessage2(@RequestBody CustomMessage message) throws InterruptedException {
            libraryService.messagesend2(message.getMessage());
        }*/


    @PostMapping("/addbook")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity addbook(@RequestBody Book newbook) {
        try{
            String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            CustomUser user = userRepository.findByUsername(username).get();
            newbook.setEditor(user);
            date=new Date();
            newbook.setCreatedate(date);
            libraryService.addbook(newbook);
            return new ResponseEntity<>(new Response(201,"Kitap eklendi"), HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(new Response(406,"Kitap eklerken hata"), HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @GetMapping("/getbook/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity getsingle(@PathVariable(value = "id") Long id) {
        try{
            Book book = libraryService.getsingle(id).get();
            return new ResponseEntity<>(book, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new Response(406,"Kitap getirilirken hata"), HttpStatus.NOT_ACCEPTABLE);
        }
}
    @DeleteMapping("/deletebook/{bookid}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity delete(@PathVariable(value = "id") Long bookid) {
        try{
            Book book = bookRepository.findById(bookid).get();
            String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            CustomUser user = userRepository.findByUsername(username).get();
            if(book.getEditor().getId()==user.getId()){
                libraryService.delete(bookid);
                return new ResponseEntity<>(new Response(200,book.getbookname()+" isimli kitap silindi"), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new Response(406,"Kitabı silme izniniz yok"), HttpStatus.NOT_ACCEPTABLE);
            }
        }
        catch (Exception e){
            return new ResponseEntity<>(new Response(406,"Kitap silerken hata"), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @PutMapping("/bookupdate/{bookid}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity update(@PathVariable(value = "bookid") Long bookid,@RequestBody Book newbook) {
        try{
            Book book = bookRepository.findById(bookid).get();
            String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            CustomUser user =userRepository.findByUsername(username).get();
            date=new Date();


            if(book.getEditor().getId()==user.getId()){
                newbook.setEditor(user);
                newbook.setUpdatedate(date);
                libraryService.update(bookid, newbook);
                return new ResponseEntity<>(new Response(200,book.getbookname()+" isimli kitap güncellendi"), HttpStatus.OK);
            }else {
                return new ResponseEntity<>(new Response(406, "Kitabı güncelleme izniniz yok"), HttpStatus.NOT_ACCEPTABLE);
            }
        }catch (Exception e){
            return new ResponseEntity<>(new Response(406, "Kitabı güncellerken hata"), HttpStatus.NOT_ACCEPTABLE);
        }




    }
    /*@PutMapping("/addbooktouser/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity addbooktouser(@PathVariable(value = "id") Long id,@RequestBody Bookid bookid) {

        Optional<CustomUser> user = userRepository.findById(id);
        libraryService.addbooktouser(id,bookid.getBookid());
        return new ResponseEntity<>("Kitap eklendi.", HttpStatus.CREATED);
    }*/

   /* @GetMapping("/image/{id}")
    @ResponseBody
    @CrossOrigin(origins = "http://localhost:3000")
    public void image(@PathVariable(value = "id") Long id, HttpServletResponse response) throws IOException {
        Optional<Book> book = bookRepository.findById(id);
        response.setContentType("image/jpeg,image/png,image/jpg");
        response.getOutputStream().write(book.get().getPic());
        response.getOutputStream().close();
    }*/
   /* @DeleteMapping("/deletebookfromuser/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity deletebookfromuser(@PathVariable(value = "id") Long id,@RequestBody Bookid bookid) {
        libraryService.deletebookfromuser(id,bookid.getBookid());
        return new ResponseEntity<>("Kitap kullanıcıdan silindi.", HttpStatus.OK);
    }*/

    @GetMapping("/bookposts/{bookid}")
    @ResponseBody
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity bookposts(@PathVariable(value = "bookid") Long bookid){
        try{
            List<Post> posts = libraryService.bookposts(bookid);
            return new ResponseEntity<>(posts, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new Response(406,"Kitap yorumları getirilirken hata"), HttpStatus.NOT_ACCEPTABLE);
        }


    }

    @GetMapping("/user/{userid}")
    @ResponseBody
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity getuser(@PathVariable(value = "userid") Long userid){
        try {
            CustomUser user = libraryService.getuser(userid);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new Response(406,"Kullanıcı getirilirken hata"), HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @PostMapping("/bookpost/{bookid}")
    @ResponseBody
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity addposttobook(@PathVariable(value = "bookid") Long bookid,@RequestBody PostRequest post){
        try{
            String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            CustomUser user = userRepository.findByUsername(username).get();
            Post newpost = new Post();
            newpost.setBook(bookRepository.findById(bookid).get());
            newpost.setPostdate(new Date());
            newpost.setCustomuser(user);
            newpost.setPost(post.getText());
            libraryService.addposttobook(newpost);
            return new ResponseEntity<>(new Response(200,"Yorum gönderildi"), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new Response(406,"Yorum gönderilirken hata"), HttpStatus.NOT_ACCEPTABLE);
        }

    }
   /* @GetMapping("/pic/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity getpic(@PathVariable(value = "id") Long id){
        Book book = bookRepository.findById(id).get();
        return new ResponseEntity<>(book.getPic(), HttpStatus.OK);
    }*/

    @GetMapping("/booklikes/{bookid}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity getbooklikes(@PathVariable(value = "bookid") Long bookid){
        try {
            return new ResponseEntity<>(likeService.getbooklikes(bookid), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new Response(406,"Kitap begenileri getirilirken hata"), HttpStatus.OK);
        }

    }
  @PostMapping ("/addlike/{bookid}")
  @ResponseBody
  @CrossOrigin(origins = "http://localhost:3000")
  public ResponseEntity addlike(@PathVariable(value = "bookid") Long bookid){
        try {
            String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            CustomUser user = userRepository.findByUsername(username).get();
            likeService.addlike(user.getId(),bookid);
            return new ResponseEntity<>(new Response(200,"Kitap begenildi"), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new Response(406,"Kitap begenirken hata"), HttpStatus.NOT_ACCEPTABLE);
        }
  }


    @DeleteMapping ("/deletelike/{bookid}")
    @ResponseBody
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity deletelike(@PathVariable(value = "bookid") Long bookid){
        try {
            String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            CustomUser user = userRepository.findByUsername(username).get();
            likeService.deletelike(user.getId(),bookid);
            return new ResponseEntity<>(new Response(200,"Kitap begenildi"), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new Response(406,"Kitap begenirken hata"), HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @GetMapping ("/mostlikedbooks")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity mostlikedbooks(){
        try {
            List<BookResponse> booklist = libraryService.getMostLikedBooks();
            return new ResponseEntity<>(booklist, HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(new Response(406,"Kitap begenirken hata"), HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @GetMapping ("/mostpostedbooks")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity mostpostesbooks(){
        try {
            List<MostPostedResponse> booklist = libraryService.getMostPostedBooks();
            return new ResponseEntity<>(booklist, HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(new Response(406,"Kitap begenirken hata"), HttpStatus.NOT_ACCEPTABLE);
        }

    }

}
