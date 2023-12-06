package com.dufrane.inu_noitce.bbs;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article,Long> {
    Page<Article> findArticlesByTitleContainingIgnoreCase(String title,Pageable pageable);
    Page<Article> findArticlesByCategory1OrderByDate(String category1, Pageable pageable);
    @Query(value = "select a from Article a where a.title like CONCAT('%', :keyword1, '%') and a.title like CONCAT('%', :keyword2, '%')")
    //@Query(value = "SELECT * FROM article WHERE title like '%모집%' AND title like '%근로%'",nativeQuery = true)
    public Page<Article> searchTitle2(String keyword1, String keyword2, Pageable pageable);
    @Query(value = "select a from Article a where a.title like %:keyword1% and a.title like %:keyword2% and a.title like %:keyword3%")
    public Page<Article> searchTitle3(String keyword1, String keyword2, String keyword3, Pageable pageable);
    @Override
    Page<Article> findAll(Pageable pageable);

}