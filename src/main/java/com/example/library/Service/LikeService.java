package com.example.library.Service;

import com.example.library.Model.Book;
import com.example.library.Model.CustomUser;
import com.example.library.Model.Like;
import com.example.library.Repository.BookRepository;
import com.example.library.Repository.LikeRepository;
import com.example.library.Repository.UserRepository;
import com.example.library.Response.LikeResponse;
import com.example.library.Response.SingleBookLikeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PreRemove;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikeService implements ILikeService {
    @Autowired
    private BookRepository bookrepository;
    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private UserRepository userRepository;
    public List<LikeResponse> getbookslikes(Long id){
        List<Like> likelist = bookrepository.findById(id).get().getLikes();
        return likelist.stream().map(like->new LikeResponse(like)).collect(Collectors.toList());
    }

    public List<SingleBookLikeResponse> getbooklikes(Long id){
        List<Like> likelist = bookrepository.findById(id).get().getLikes();
        return likelist.stream().map(like->new SingleBookLikeResponse(like)).collect(Collectors.toList());
    }

    public void addlike(Long userid,Long bookid){
        Like like =new Like(bookrepository.findById(bookid).get(),userRepository.findById(userid).get());
        likeRepository.save(like);
    }
    @PreRemove
    public void deletelike(Long userid,Long bookid){
        System.out.println("user:"+userid+"book:"+bookid);
        List<Like> likelist = bookrepository.findById(bookid).get().getLikes();
        Like deletelike=new Like();
        for(Long i =0L ;i< likelist.size();i++) {
            if (likelist.get(i.intValue()).getCustomuserlike().getId() == userid) {
                deletelike = likelist.get(i.intValue());
                likeRepository.deleteById(likelist.get(i.intValue()).getId());
            }
        }
    }
}
