package com.example.library.Service;

import com.example.library.Model.Chat;
import com.example.library.Model.ChatList;
import com.example.library.Model.Message;

import java.util.List;
import java.util.Optional;

public interface IChatService {

    public List<ChatList> getUserChats(Long userid);
    public List<Chat> getUserChatDetail(Long chatid);
    public void sendMessage(Long chatid, Long userid, Message message);
    public ChatList createChat(Long userid1, Long userid2);
}
