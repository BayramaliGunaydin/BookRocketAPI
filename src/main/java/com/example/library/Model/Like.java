package com.example.library.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "bookid",referencedColumnName = "id_book")
    Book booklike;
    @ManyToOne
    @JoinColumn(name = "userid",referencedColumnName = "id")
    CustomUser customuserlike;

    public Like(){

    }

    public Like(Book book,CustomUser user){
        this.booklike=book;
        this.customuserlike=user;
    }
}
