package com.example.library.Service;

import com.example.library.Model.Like;
import com.example.library.Repository.BookRepository;
import com.example.library.Response.LikeResponse;
import com.example.library.Response.SingleBookLikeResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public interface ILikeService {
    public List<LikeResponse> getbookslikes(Long id);
    public void addlike(Long userid,Long bookid);
    public void deletelike(Long userid,Long bookid);
    public List<SingleBookLikeResponse> getbooklikes(Long id);
}