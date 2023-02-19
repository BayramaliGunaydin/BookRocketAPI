package com.example.library.Service;

import com.example.library.Exception.IdNotFoundException;
import com.example.library.Model.*;
import com.example.library.Repository.*;
import com.example.library.Response.*;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;


@Service
public class LibraryService implements ILibraryService{
    Logger logger = Logger.getLogger("MyLog");
   /* @Value("${spring.rabbitmq.routingKey}")
    private String routingName;
    @Value("${spring.rabbitmq.Exchange}")*/
    private String exchange;
    @Autowired
    private BookRepository bookrepository;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PostRepository postRepository;
    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private LikeService likeService;

 /*   @RabbitListener(queues = "${spring.rabbitmq.Queue}")
    public void message(Message message) throws InterruptedException {
        TimeUnit.SECONDS.sleep(15L);
        logger.info(message.toString());
    }
    public void messagesend(String message){
        rabbitTemplate.convertAndSend(exchange,routingName,message);
    }
    public void messagesend2(String message) throws InterruptedException {
        TimeUnit.SECONDS.sleep(15L);
        logger.info(message);
    }*/


    public List<BookResponse> getall() {
        List<Book> bookList = bookrepository.findAll();
        return bookList.stream().map(p -> {
                    List<LikeResponse> responselist= likeService.getbookslikes(p.getid());
                    return new BookResponse(p,responselist);}).collect(Collectors.toList());
    }



  /*  @RabbitListener(queues = "${spring.rabbitmq.SaveQueue}")
    public void addbookListener(Book newbook) throws InterruptedException {
        TimeUnit.SECONDS.sleep(15L);
        bookrepository.save(newbook);
    }*/

    @Override
    public void addbook(Book newbook) {
        bookrepository.save(newbook);
      //  rabbitTemplate.convertAndSend(exchange,routingName,newbook);
    }

    @Override
    public Optional<Book> getsingle(Long id) {
        return bookrepository.findById(id);
    }
    public void delete(Long id){
        bookrepository.deleteById(id);
    }
    public Book update(Long id, Book book){
        Optional<Book> bookupdate = bookrepository.findById(id);
        if(bookupdate.isPresent()){
            bookupdate.get().setbookname(book.getbookname());
            bookupdate.get().setauthor(book.getauthor());
            bookupdate.get().settopic(book.gettopic());
            bookupdate.get().setUpdatedate(book.getUpdatedate());
            bookrepository.save(bookupdate.get());
            return bookupdate.get();
            }
        else{
            throw new IdNotFoundException();
        }

    }
    public void addbooktouser(Long id,Long bookid){
        Optional<CustomUser> user = userRepository.findById(id);
        Optional<Book> book =bookrepository.findById(bookid);
        user.get().getBooks().add(book.get());
        userRepository.save(user.get());
        book.get().setEditor(user.get());
        bookrepository.save(book.get());
    }
    public List<Book> userbooks(Long id){
        return userRepository.findById(id).get().getBooks();
    }
    public void deletebookfromuser(Long id, Long bookid){
        Optional<CustomUser> user = userRepository.findById(id);
        Optional<Book> book =bookrepository.findById(bookid);
        user.get().getBooks().remove(book);
        userRepository.save(user.get());
        book.get().setEditor(null);
        bookrepository.save(book.get());

    }
     public List<Post> userposts(Long id){

        return userRepository.findById(id).get().getPosts();
     }
     public List<Post> bookposts(Long id){
        return bookrepository.findById(id).get().getPosts();
     }

     public CustomUser getuser(Long id){
        return userRepository.findById(id).get();
     }
    public void addposttobook(Post post){
           postRepository.save(post);
    }
    public void saveuserimage(Long id,base64 base64){
       CustomUser user = userRepository.findById(id).get();
        user.setPic(base64.getBase64());
        userRepository.save(user);
    }

    public List<Like> userlikes(Long id){
        return userRepository.findById(id).get().getLikes();
    }

    public List<BookResponse> getMostLikedBooks(){
        List<Long> bookids=likeRepository.getMostLikedBooks();
        List<Book> bookList= new ArrayList<>();
        for(int i=0;i<bookids.size();i++){
            bookList.add(bookrepository.findById(bookids.get(i)).get());
        }
        return bookList.stream().map(p -> {
            List<LikeResponse> responselist= likeService.getbookslikes(p.getid());
            return new BookResponse(p,responselist);}).collect(Collectors.toList());

    }

    public List<MostPostedResponse> getMostPostedBooks(){
        List<Long> bookids=postRepository.getMostPostedBooks();
        List<Book> bookList= new ArrayList<>();
        for(int i=0;i<bookids.size();i++){
            bookList.add(bookrepository.findById(bookids.get(i)).get());
        }
        return bookList.stream().map(p -> {
            List<Post> posts = bookrepository.findById(p.getid()).get().getPosts();
            return new MostPostedResponse(p,posts.size());}).collect(Collectors.toList());

    }
}
