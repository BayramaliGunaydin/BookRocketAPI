package com.example.library.Repository;


import com.example.library.Model.Chat;
import com.example.library.Model.ChatList;
import com.example.library.Model.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("SELECT chat FROM Chat chat WHERE chatid = :chatid")
    List<Chat> getUserChatDetails(@Param("chatid") Long chatid);

    @Transactional
    @Modifying
    @Query(value="INSERT INTO Chat (date,message,chatList,user) VALUES (:date,:message,:chatList,:user)",nativeQuery = true)
    void sendMessage(@Param("date") Date date, @Param("message") String message, @Param("chatList") ChatList chatList, @Param("user") CustomUser user);
}

