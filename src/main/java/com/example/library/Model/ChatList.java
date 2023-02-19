package com.example.library.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class ChatList {


    @Id
    @Column(name="chatListid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long chatListid;

    @ManyToOne
    @JoinColumn(name = "user1",referencedColumnName = "id")
    private CustomUser user1;

    @ManyToOne
    @JoinColumn(name = "user2",referencedColumnName = "id")
    private CustomUser user2;


    @OneToMany(mappedBy = "chatList")
    @JsonIgnore
    private List<Chat> chat;
}
