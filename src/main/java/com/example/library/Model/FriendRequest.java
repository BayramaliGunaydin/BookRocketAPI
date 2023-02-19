package com.example.library.Model;

import com.example.library.Repository.FriendRequestRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
//@Table(uniqueConstraints={@UniqueConstraint(name = "UniqueRequest",columnNames={"user1","user2"})})
public class FriendRequest {


    public FriendRequest(CustomUser user1,CustomUser user2){
        this.user1=user1;
        this.user2=user2;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user1",referencedColumnName = "id")
    private CustomUser user1;

    @ManyToOne
    @JoinColumn(name = "user2",referencedColumnName = "id")
    private CustomUser user2;



}

