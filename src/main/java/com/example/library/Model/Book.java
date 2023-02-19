package com.example.library.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.swing.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Book {
    @Id
    @Column(name="id_book")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name="name_book")
    String bookname;
    @Column(name="author_book")
    String author;
    @Column(name="topic_book")
    String topic;

    Date createdate;
    Date updatedate;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    byte[] pic;

    @ManyToOne
    @JoinColumn(name = "editor_id",referencedColumnName = "id")
    private CustomUser editor;

    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Post> posts;

    @OneToMany(mappedBy = "booklike",cascade = CascadeType.REMOVE, fetch = FetchType.LAZY,orphanRemoval = true)
    @JsonIgnore
    private List<Like> likes;

    public Book() {
    }

    public Book(Long id, String bookname, String author, String topic, byte[] pic) {
        this.id = id;
        this.bookname = bookname;
        this.author = author;
        this.topic = topic;
        this.pic = pic;
    }

    public Long getid() {
        return id;
    }

    public void setid(Long id) {
        this.id = id;
    }

    public String getbookname() {
        return bookname;
    }

    public void setbookname(String bookname) {
        this.bookname = bookname;
    }

    public String getauthor() {
        return author;
    }

    public void setauthor(String author) {
        this.author = author;
    }

    public String gettopic() {
        return topic;
    }

    public void settopic(String topic) {
        this.topic = topic;
    }
}
