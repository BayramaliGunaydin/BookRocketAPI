package com.example.library.Repository;

import com.example.library.Model.Book;
import com.example.library.Model.CustomUser;
import com.example.library.Model.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
    @Query("SELECT user2 FROM Friend u WHERE u.user1.id = :userid")
    List<CustomUser> findFriendsByUserid1(@Param("userid") Long userid);

    @Query("SELECT user1 FROM Friend u WHERE u.user2.id = :userid")
    List<CustomUser> findFriendsByUserid2(@Param("userid") Long userid);

    @Query("SELECT u FROM Friend u WHERE ((u.user1.id = :userid1 and u.user2.id=:userid2) or (u.user1.id=:userid2 and u.user2.id = :userid1))")
    Friend getFriendship(@Param("userid1") Long userid1,@Param("userid2") Long userid2);
}