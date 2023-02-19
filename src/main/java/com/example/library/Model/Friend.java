package com.example.library.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
public class Friend {

    public Friend(CustomUser user1,CustomUser user2){
        this.user1=user1;
        this.user2=user2;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user1",referencedColumnName = "id")
    private CustomUser user1;

    @ManyToOne
    @JoinColumn(name = "user2",referencedColumnName = "id")
    private CustomUser user2;

}
