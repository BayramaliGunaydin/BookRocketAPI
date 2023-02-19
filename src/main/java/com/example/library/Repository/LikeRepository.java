package com.example.library.Repository;

import com.example.library.Model.Book;
import com.example.library.Model.Like;
import com.example.library.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface LikeRepository extends CrudRepository<Like,Long> {

    @Query(value = "select bookid  from \"like\" l  group by l.bookid  order by count(l.bookid) desc limit 4",nativeQuery = true)
    List<Long> getMostLikedBooks();


}
