package com.example.library.Repository;

import com.example.library.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {
    public Optional<Post> findByid(Long id);

    @Query("select book  from Post p where p.book.id =:bookid")
    List<Post> getBookPosts(@Param("bookid") Long bookid);

    @Query(value = "select bookid  from \"post\" p  group by p.bookid  order by count(p.bookid) desc limit 4",nativeQuery = true)
    List<Long> getMostPostedBooks();
}