package com.dufrane.inu_noitce.bbs;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_num", updatable = false)
    private Long id_num;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "category1", nullable = false)
    private String category1;

    @Column(name = "category2", nullable = false)
    private String category2;

    @Column(name = "writer", nullable = false)
    private String writer;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "org_num", nullable = false)
    private Long org_num;
    @Builder
    public Article(String category2, Long id_num, String title, String category1, String writer, Date date, String url, Long org_num){
        this.title = title;
        this.id_num= id_num;
        this.category1 = category1;
        this.category2 = category2;
        this.writer = writer;
        this.date = date;
        this.url = url;
        this.org_num = org_num;
    }
}

