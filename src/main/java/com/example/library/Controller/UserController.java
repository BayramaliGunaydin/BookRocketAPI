package com.example.library.Controller;
import com.example.library.Exception.DefaultException;
import com.example.library.Exception.NullJWTException;
import com.example.library.Model.*;

import com.example.library.Repository.UserRepository;
import com.example.library.Response.Response;
import com.example.library.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping
public class UserController {

    @Autowired private UserRepository userRepo;
    @Autowired
    private IMyUserDetailsService userDetailsService;
    @Autowired
    private ILibraryService libraryService;

    @Autowired
    private IFriendService friendService;

    @Autowired
    private IChatService chatService;

    @GetMapping("/info")
    @CrossOrigin(origins = "http://localhost:3000")
    public CustomUser getUserDetails(){
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepo.findByUsername(username).get();
    }
    @PutMapping("/updaterole/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity updaterole(@PathVariable(value = "id") Long id, @RequestBody Role role) {
        userDetailsService.addroletouser(id,role.getId());
        return new ResponseEntity<>(userRepo.findById(id), HttpStatus.OK);
    }
    @GetMapping("/users")
    @CrossOrigin(origins = "http://localhost:3000")
    public List<CustomUser> getusers(){
        try{
        return userRepo.findAll();}
        catch (Exception e){
            throw new NullJWTException();
        }
    }

    @GetMapping ("/searchuser/{name}")
    @ResponseBody
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity searchuser(@PathVariable(value = "name") String username){
        try{
            List<CustomUser> user = userDetailsService.getUsersByUserName(username);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new Response(406,"Kullan??c?? ararken hata"), HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @PutMapping("/saveimage")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity saveuserimage(@RequestBody base64 base64){

        try{
            String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            CustomUser user = userRepo.findByUsername(username).get();
            libraryService.saveuserimage(user.getId(),base64);
            return new ResponseEntity<>(new Response(200,"Resim Degistirildi"),HttpStatus.OK);
        }
        catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(new Response(406,"Arkada?? Eklerken Hata"),HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/userlikes/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public List<Like> getuserlikes(@PathVariable(value = "id") Long id){
        return libraryService.userlikes(id);
    }
    @GetMapping("/userposts/{id}")
    @ResponseBody
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity userposts(@PathVariable(value = "id") Long id){
        List<Post> posts = libraryService.userposts(id);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
   /* @GetMapping("/userbooks/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity userbooks(@PathVariable(value = "id") Long id) {
        List<Book> books = libraryService.userbooks(id);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }*/

    @GetMapping("/getuserfriends/{userid}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity getuserfriends(@PathVariable(value = "userid") Long userid) {
        try {

            List<CustomUser> friends = friendService.getfriends(userid);
            return new ResponseEntity<>(friends, HttpStatus.OK);
        }
        catch (Exception e){
            return  new ResponseEntity<>(new Response(406,"Komut Basarisiz"),HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @PostMapping("/sendfriendrequest/{getuserid}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity sendfriendrequest(@PathVariable(value = "getuserid") Long getuserid) {
        try {
            String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            CustomUser user = userRepo.findByUsername(username).get();
            if(user.getId()!=getuserid){
                friendService.sendfriendrequest(user.getId(),getuserid);
                return new ResponseEntity<>(new Response(200,"Arkada??l??k iste??i g??nderildi"), HttpStatus.OK);
            }else{
                return  new ResponseEntity<>(new Response(406,"Kendinize arkadasl??k istegi g??nderemezsiniz"),HttpStatus.NOT_ACCEPTABLE);
            }

        }catch (Exception e){
            if(e instanceof DefaultException){
                return  new ResponseEntity<>(new Response(406,"Bu kullan??c?? zaten size arkada??l??k iste??i g??ndermi??"),HttpStatus.NOT_ACCEPTABLE);
            }
            else{
                return  new ResponseEntity<>(new Response(406,"Ayn?? Kullan??c??ya ??ki Defa Arkada??l??k ??ste??i G??nderemezsiniz"),HttpStatus.NOT_ACCEPTABLE);
            }

        }


    }

    @GetMapping("/getuserfriendrequests")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity getuserfriendrequests() {
        try{
            String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            CustomUser user = userRepo.findByUsername(username).get();
            List<CustomUser> friendrequests = friendService.getUserFriendRequest(user.getId());
            return new ResponseEntity<>(friendrequests, HttpStatus.OK);
        }
        catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(new Response(406,"Arkada?? Eklerken Hata"),HttpStatus.NOT_ACCEPTABLE);
        }

    }
    @PostMapping("/acceptfriendrequest/{senduserid}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity acceptfriendrequest(@PathVariable(value = "senduserid") Long senduserid) {
        try{
            String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            CustomUser user = userRepo.findByUsername(username).get();
            friendService.deleteFriendRequest(senduserid,user.getId());
            friendService.addFriend(senduserid,user.getId());
        }
        catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(new Response(406,"Arkada?? Eklerken Hata"),HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>(new Response(200,"Arkada?? Eklendi"), HttpStatus.OK);
    }

    @PostMapping("/rejectfriendrequest/{senduserid}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity rejectfriendrequest(@PathVariable(value = "senduserid") Long senduserid) {
        try{
            String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            CustomUser user = userRepo.findByUsername(username).get();
            friendService.deleteFriendRequest(senduserid,user.getId());
        }
        catch (Exception e){
            return new ResponseEntity<>(new Response(406,"Arkada??l??k ??ste??i Reddederken Hata"),HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>(new Response(200,"Arkada?? ??ste??i Reddedildi"), HttpStatus.OK);
    }

    @PostMapping("/cancelfriendrequest/{getuserid}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity cancelfriendrequest(@PathVariable(value = "getuserid") Long getuserid) {
        try{
            String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            CustomUser user = userRepo.findByUsername(username).get();
            friendService.deleteFriendRequest(user.getId(),getuserid);
            return new ResponseEntity<>(new Response(200,"Arkada?? ??ste??i Reddedildi"), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new Response(406,"Arkada??l??k ??ste??i Reddederken Hata"),HttpStatus.NOT_ACCEPTABLE);
        }


    }

    @PostMapping("/deletefriendship/{userid}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity deletefriendship(@PathVariable(value = "userid") Long userid) {
        try{
            String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            CustomUser user = userRepo.findByUsername(username).get();
            friendService.deleteFriendShip(user.getId(),userid);
            return new ResponseEntity<>(new Response(200,"Arkada??l??ktan ????kart??ld??"), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new Response(406,"Arkada??l??ktan ????kart??rken Hata"),HttpStatus.NOT_ACCEPTABLE);
        }


    }

    @GetMapping("/getsendedfriendrequests")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity getsendedfriendrequests() {
        try{
            String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            CustomUser user = userRepo.findByUsername(username).get();
            List<CustomUser> requests = friendService.getsendedfriendrequests(user.getId());
            return new ResponseEntity<>(requests, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new Response(406,"G??nderilen istekler getirilemedi"),HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/seteditor")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity setEditor() {
        try{
            String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            CustomUser user = userRepo.findByUsername(username).get();
            userRepo.setEditor(user.getId());
            return new ResponseEntity<>(new Response(200,"Kullan??c?? Edit??r Oldu"), HttpStatus.OK);
        }
        catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(new Response(406,"Komut Basarisiz"),HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/getuserchats")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity getUserChats() {
        try{
            String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            CustomUser user = userRepo.findByUsername(username).get();
            System.out.println("username:"+username);
            List<ChatList> chatlist = chatService.getUserChats(user.getId());
            System.out.println(chatlist.get(0).getUser1().getUsername());
            return new ResponseEntity<>(chatlist, HttpStatus.OK);
        }
        catch (Exception e){
            if(e instanceof IndexOutOfBoundsException){
                List<ChatList> chatlist = new ArrayList<>();
                return new ResponseEntity<>(chatlist,HttpStatus.OK);
            }else {
                return new ResponseEntity<>(new Response(406,"Komut Basarisiz"),HttpStatus.NOT_ACCEPTABLE);
            }

        }
    }

    @GetMapping("/getchatdetails/{chatid}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity getChatDetails(@PathVariable(value = "chatid") Long chatid) {
        try{
            List<Chat> chat = chatService.getUserChatDetail(chatid);
            return new ResponseEntity<>(chat, HttpStatus.OK);
        }
        catch (Exception e){
            if(e instanceof IndexOutOfBoundsException){
                List<Chat> chat = new ArrayList<>();
                return new ResponseEntity<>(chat, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new Response(406,"Komut Basarisiz"),HttpStatus.NOT_ACCEPTABLE);
            }

        }
    }

    @PostMapping("/createchat/{userid}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity createChat(@PathVariable(value = "userid") Long userid) {
        try{
            String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            CustomUser user = userRepo.findByUsername(username).get();
            ChatList chatList = chatService.createChat(user.getId(),userid);
            return new ResponseEntity<>(chatList, HttpStatus.OK);
        }
        catch (Exception e){
            if(e instanceof DefaultException){
                return new ResponseEntity<>(new Response(406,"Arkada????n??z olmayan birine mesaj g??nderemezsiniz"),HttpStatus.NOT_ACCEPTABLE);
            }
            else{
                return new ResponseEntity<>(new Response(406,"Sohbet ba??lat??l??rken hata"),HttpStatus.NOT_ACCEPTABLE);
            }

        }
    }



    @PostMapping("/sendmessage/{chatid}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity sendMessage(@PathVariable(value = "chatid") Long chatid,@RequestBody Message message) {
        try{
            String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            CustomUser user = userRepo.findByUsername(username).get();
            chatService.sendMessage(chatid,user.getId(),message);
            return new ResponseEntity<>(new Response(200,"Mesaj G??nderildi"), HttpStatus.OK);
        }
        catch (Exception e){
            System.out.println(e);
            if(e instanceof NullPointerException){
                return new ResponseEntity<>(new Response(406,"Arkada????n??z olmayan kullan??c??ya mesaj g??nderemezsiniz"),HttpStatus.NOT_ACCEPTABLE);
            }else{
                return new ResponseEntity<>(new Response(406,"Mesaj g??nderirken hata"),HttpStatus.NOT_ACCEPTABLE);
            }

        }
    }





}
