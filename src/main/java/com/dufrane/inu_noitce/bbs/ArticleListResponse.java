package com.dufrane.inu_noitce.bbs;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ArticleListResponse {
    private final List<ArticleResponse> list;
    private final Long TotalElements;
    public ArticleListResponse(Page<Article> page){
        this.list=page.stream()
                .map(a -> new ArticleResponse(a))
                .collect(Collectors.toList());
        this.TotalElements= page.getTotalElements();
    }

}