package com.example.library.Repository;

import com.example.library.Model.Chat;
import com.example.library.Model.ChatList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatListRepository extends JpaRepository<ChatList, Long> {

     @Query("SELECT cl FROM ChatList cl WHERE (cl.user1.id = :userid or cl.user2.id=:userid)")
     List<ChatList> getUserChatList(@Param("userid") Long userid);

     @Query("SELECT cl FROM ChatList cl WHERE (cl.user1.id = :userid1 and cl.user2.id=:userid2) ")
     Long getChatId(@Param("userid1") Long userid1,@Param("userid2") Long userid2);


     @Query("SELECT cl FROM ChatList cl WHERE ((cl.user1.id = :userid1 and cl.user2.id=:userid2) or (cl.user2.id = :userid1 and cl.user1.id=:userid2))")
     ChatList getChatList(@Param("userid1") Long userid1, @Param("userid2") Long userid2);
}
