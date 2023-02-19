package com.example.library.Repository;

import com.example.library.Model.Book;
import com.example.library.Model.CustomUser;
import com.example.library.Model.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    @Query("SELECT user1 FROM FriendRequest u WHERE u.user2.id = :userid")
    List<CustomUser> findFriendRequests(@Param("userid") Long userid);

    @Query("SELECT user2 FROM FriendRequest u WHERE u.user1.id = :userid")
    List<CustomUser> sendedFriendRequests(@Param("userid") Long userid);
    @Query("SELECT u FROM FriendRequest u WHERE (u.user1.id = :senduserid and u.user2.id= :getuserid) ")
    FriendRequest findOneFriendRequest(@Param("senduserid") Long senduserid,@Param("getuserid") Long getuserid);


}
