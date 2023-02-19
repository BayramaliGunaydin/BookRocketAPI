package com.example.library.Service;

import com.example.library.Exception.DefaultException;
import com.example.library.Model.CustomUser;
import com.example.library.Model.Friend;
import com.example.library.Model.FriendRequest;
import com.example.library.Repository.FriendRepository;
import com.example.library.Repository.FriendRequestRepository;
import com.example.library.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FriendService implements IFriendService {

    @Autowired
    FriendRepository friendRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    FriendRequestRepository friendRequestRepository;

    public List<CustomUser> getfriends(Long userid){
        List<CustomUser> friendList1=friendRepository.findFriendsByUserid1(userid);
        List<CustomUser> friendList2=friendRepository.findFriendsByUserid2(userid);
        List<CustomUser> friendList = Stream.concat(friendList1.stream(),friendList2.stream()).collect(Collectors.toList());
        return friendList;
    }

    public void sendfriendrequest (Long senduserid,Long getuserid){
        CustomUser senduser = userRepository.findById(senduserid).get();
        CustomUser getuser = userRepository.findById(getuserid).get();
        List <CustomUser> friendRequestList = friendRequestRepository.findFriendRequests(senduserid);
        for(int i=0; i<friendRequestList.size();i++){
                if(friendRequestList.get(i).getId()==getuserid){
                    throw new DefaultException();
                }
        }
        FriendRequest request = new FriendRequest(senduser,getuser);
            friendRequestRepository.save(request);
    }

    public List<CustomUser> getUserFriendRequest (Long userid){
        return friendRequestRepository.findFriendRequests(userid);
    }

    public void addFriend(Long senduserid,Long getuserid){
        CustomUser senduser = userRepository.findById(senduserid).get();
        CustomUser getuser = userRepository.findById(getuserid).get();
        Friend friend = new Friend(senduser,getuser);
        friendRepository.save(friend);
    }

    public void deleteFriendRequest(Long senduserid,Long getuserid){
        FriendRequest request = friendRequestRepository.findOneFriendRequest(senduserid,getuserid);
        friendRequestRepository.delete(request);
    }

    public void deleteFriendShip(Long userid1,Long userid2){
        Friend friend = friendRepository.getFriendship(userid1,userid2);
        friendRepository.delete(friend);
    }

    public List<CustomUser> getsendedfriendrequests(Long userid){
        List<CustomUser> requests = friendRequestRepository.sendedFriendRequests(userid);
        return requests;
    }
}
