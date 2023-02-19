package com.example.library.Service;

import com.example.library.Exception.DefaultException;
import com.example.library.Exception.IdNotFoundException;
import com.example.library.Model.*;
import com.example.library.Repository.ChatListRepository;
import com.example.library.Repository.ChatRepository;
import com.example.library.Repository.FriendRepository;
import com.example.library.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChatService implements IChatService{

    @Autowired
    ChatListRepository chatListRepository;

    @Autowired
    ChatRepository chatRepository;

    @Autowired
    FriendRepository friendRepository;

    @Autowired
    UserRepository userRepository;
    public List<ChatList> getUserChats(Long userid){
        return chatListRepository.getUserChatList(userid);
    }

    public List<Chat> getUserChatDetail(Long chatid){
        System.out.println("dönen"+chatRepository.getUserChatDetails(chatid).get(0).getChatList());
        return chatRepository.getUserChatDetails(chatid);
    }

    public void sendMessage(Long chatid, Long userid, Message message){
        CustomUser user = userRepository.findById(userid).get();
        ChatList chatList = chatListRepository.findById(chatid).get();
        Friend friend = friendRepository.getFriendship(chatList.getUser2().getId(),chatList.getUser1().getId());
        if((user.getId()==chatList.getUser1().getId()||user.getId()==chatList.getUser2().getId()) && friend!=null){
            Chat chat = new Chat();
            chat.setChatList(chatList);
            chat.setUser(user);
            chat.setDate(new Date());
            chat.setMessage(message.getMessage());
            chatRepository.save(chat);
        }
        else if(friend==null){
            throw new NullPointerException();
        }
        else{
            throw new DefaultException();
        }
    }

    public ChatList createChat(Long userid1, Long userid2){
        Friend friend = friendRepository.getFriendship(userid1,userid2);
        ChatList chatList = chatListRepository.getChatList(userid1,userid2);
        System.out.println("chatlist"+chatList);
        if(friend!=null && chatList==null){
            System.out.println("girdi");
            List<Chat> chat = new ArrayList<>();
            ChatList newchatList = new ChatList();
            newchatList.setUser1(userRepository.findById(userid1).get());
            newchatList.setUser2(userRepository.findById(userid2).get());
            newchatList.setChat(chat);
            chatListRepository.save(newchatList);
            return newchatList;
        }
        else if(friend!=null&&chatList!=null){
            return chatList;
        }
        else{
            throw new DefaultException();
        }

    }

    public Long getChatId(Long userid1,Long userid2){
        return chatListRepository.getChatId(userid1,userid2);
    }



}
