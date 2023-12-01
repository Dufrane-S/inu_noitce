package com.dufrane.inu_noitce.bbs;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
//@AllArgsConstructor
@Getter
//@Setter
public class AddArticleRequest {
    private String title;
    private String category1;
    private String category2;
    private String writer;
    private String date;
    private String url;
    private Long org_num;

    @Builder
    public AddArticleRequest(String category2, String title, String category1, String writer, String date, String url, Long org_num){
        this.title = title;
        this.category1 = category1;
        this.category2 = category2;
        this.writer = writer;
        this.date = date;
        this.url = url;
        this.org_num = org_num;
    }

    public Article toEntitiy(){
        return Article.builder()
                .title(title)
                .category1(category1)
                .category2(category2)
                .writer(writer)
                .date(date)
                .url(url)
                .org_num(org_num)
                .id_num(null)
                .build();
    }
}
