package com.example.library.Response;

import com.example.library.Model.Like;
import lombok.Data;

@Data
public class LikeResponse {
    Long id;
    Long userid;
    Long bookid;
    public LikeResponse(Like entity){
        id=entity.getId();
        userid=entity.getCustomuserlike().getId();
        bookid=entity.getBooklike().getid();
    }
}
