package com.dufrane.inu_noitce.bbs;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article,Long> {
    Page<Article> findArticlesByTitleContainingIgnoreCase(String title,Pageable pageable);

    @Override
    Page<Article> findAll(Pageable pageable);
}