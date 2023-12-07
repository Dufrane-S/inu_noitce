package com.dufrane.inu_noitce.bbs;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    public Article findById(long id){
        return articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    }

    public ArticleListResponse findall(int page){
        Pageable pageable =PageRequest.of(page,10);
        Page<Article> articlePage = articleRepository.findAll(pageable);
        return new ArticleListResponse(articlePage);
    }

    public ArticleListResponse loadArticlesBbsByCategory1Keyword(String category1, String keyword, int page){
        Pageable pageable = PageRequest.of(page, 10);
        Page<Article> articlePage = articleRepository.loadArticlesBbsByCategory1Keyword(category1,keyword,pageable);
        return new ArticleListResponse(articlePage);
    }

    public ArticleListResponse loadArtilesByCategory1(String category1, int page){
        Pageable pageable = PageRequest.of(page, 10);
        Page<Article> articlePage = articleRepository.loadArticlesBbsByCategory1(category1,pageable);
        return new ArticleListResponse(articlePage);
    }

    public ArticleListResponse loadArtilesByKeyword(String keyword, int page){
        Pageable pageable = PageRequest.of(page, 10);
        Page<Article> articlePage = articleRepository.loadArticlesBbsByKeyword(keyword,pageable);
        return new ArticleListResponse(articlePage);
    }





    public Article save(AddArticleRequest request){
        return articleRepository.save(request.toEntitiy());
    }

    public Page<Article> searchTitle(String keyword, int page){
        Pageable pageable = PageRequest.of(page,10);
        //return articleRepository.findArticlesByTitleContainingIgnoreCase(keyword , pageable);
        return articleRepository.searchTitle1(keyword , pageable);
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
        return articleRepository.findArticlesByCategory1OrderByDateDesc(category1, pageable);
    }

    public Page<Article>getAll(int page){
        Pageable pageable = PageRequest.of(page,10);
        return this.articleRepository.findAll(pageable);
    }
    public Page<Article>searchTitleAndCategory(String title, String category1, int page){
        Pageable pageable = PageRequest.of(page, 10);
        return this.articleRepository.findArticlesByTitleContainingIgnoreCaseAndAndCategory1Containing(title,category1,pageable);

    }
}
