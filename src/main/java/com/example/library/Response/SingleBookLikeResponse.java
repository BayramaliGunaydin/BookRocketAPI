package com.example.library.Response;

import com.example.library.Model.CustomUser;
import com.example.library.Model.Like;
import lombok.Data;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

@Data
public class SingleBookLikeResponse {
    Long id;
    CustomUser user;
    Long bookid;

    public SingleBookLikeResponse(Like entity){
        id=entity.getId();
        user=entity.getCustomuserlike();
        bookid=entity.getBooklike().getid();
    }
}

