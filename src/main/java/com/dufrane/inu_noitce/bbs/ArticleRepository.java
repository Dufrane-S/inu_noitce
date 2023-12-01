package com.dufrane.inu_noitce.bbs;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article,Long> {
    List<Article> findArticlesByTitleContainingIgnoreCase(String title);
}