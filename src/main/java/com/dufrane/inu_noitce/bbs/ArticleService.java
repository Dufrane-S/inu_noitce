package com.dufrane.inu_noitce.bbs;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public List<ArticleResponse> searchTitle(String keyword){
        return articleRepository.findArticlesByTitleContainingIgnoreCase(keyword)
                .stream()
                .map(ArticleResponse::new)
                .toList();
    }



}
