package com.dufrane.inu_noitce.bbs;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    public Article findById(long id){
        return articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    }

    public List<Article> findall(){
        return articleRepository.findAll();
    }
    public Article save(AddArticleRequest request){
        return articleRepository.save(request.toEntitiy());
    }

    public Page<Article> searchTitle(String keyword, int page){
        Pageable pageable = PageRequest.of(page,10);
        return articleRepository.findArticlesByTitleContainingIgnoreCase(keyword , pageable);
    }
    public Page<Article> searchTitle(String keyword1, String keyword2, int page){
        Pageable pageable = PageRequest.of(page,10);
        return articleRepository.searchTitle2(keyword1, keyword2, pageable);
    }
    public Page<Article> searchTitle(String keyword1, String keyword2, String keyword3, int page){
        Pageable pageable = PageRequest.of(page,10);
        return articleRepository.searchTitle3(keyword1, keyword2, keyword3, pageable);
    }

    public Page<Article> loadArticles(String category1, int page){
        Pageable pageable = PageRequest.of(page,10);
        return articleRepository.findArticlesByCategory1OrderByDate(category1, pageable);
    }

    public Page<Article>getAll(int page){
        Pageable pageable = PageRequest.of(page,10);
        return this.articleRepository.findAll(pageable);
    }





}
