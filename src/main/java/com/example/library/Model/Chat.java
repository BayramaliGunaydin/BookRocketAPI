package com.example.library.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class Chat {

    @Id
    @Column(name="cid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long cid;

    @ManyToOne
    @JoinColumn(name = "chatid",referencedColumnName = "chatListid")
    ChatList chatList;

    @ManyToOne
    CustomUser user;

    String message;

    Date date;
}
