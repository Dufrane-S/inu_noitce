package com.dufrane.inu_noitce.bbs;

import lombok.Getter;

@Getter
public class ArticleResponse {
    private final String title;
    private final String category1;
    private final String category2;
    private final String writer;
    private final String date;
    private final String url;

    public ArticleResponse(Article article){
        this.category1 = article.getCategory1();
        this.category2 = article.getCategory2();
        this.date = article.getDate();
        this.title = article.getTitle();
        this.writer = article.getWriter();
        this.url = article.getUrl();
    }
}
