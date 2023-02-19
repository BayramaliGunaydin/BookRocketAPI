package com.example.library.Service;

import com.example.library.Model.CustomUser;

import java.util.List;

public interface IFriendService {
    public List<CustomUser> getfriends(Long userid);
    public void sendfriendrequest (Long senduserid,Long getuserid);
    public List<CustomUser> getUserFriendRequest (Long userid);
    public void addFriend(Long senduserid,Long getuserid);
    public void deleteFriendRequest(Long senduserid,Long getuserid);
    public void deleteFriendShip(Long userid1,Long userid2);
    public List<CustomUser> getsendedfriendrequests(Long userid);
}
