package com.dufrane.inu_noitce.bbs;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article,Long> {
    Page<Article> findArticlesByTitleContainingIgnoreCase(String title,Pageable pageable);
    Page<Article> findArticlesByTitleContainingIgnoreCaseAndAndCategory1Containing(String title, String category1 ,Pageable pageable);
    Page<Article> findArticlesByCategory1OrderByDateDesc(String category1, Pageable pageable);
    @Query(value = "select a from Article a where a.title like %:keyword1%")
    public Page<Article> searchTitle1(String keyword1, Pageable pageable);
    @Query(value = "select a from Article a where a.title like CONCAT('%', :keyword1, '%') and a.title like CONCAT('%', :keyword2, '%')")
    //@Query(value = "SELECT * FROM article WHERE title like '%모집%' AND title like '%근로%'",nativeQuery = true)
    public Page<Article> searchTitle2(String keyword1, String keyword2, Pageable pageable);
    @Query(value = "select a from Article a where a.title like %:keyword1% and a.title like %:keyword2% and a.title like %:keyword3%")
    public Page<Article> searchTitle3(String keyword1, String keyword2, String keyword3, Pageable pageable);
    @Override
    Page<Article> findAll(Pageable pageable);

    @Query(value = "select a from Article a where a.title like %:keyword% and a.category1 = :category1 order by a.date desc")
    public Page<Article>loadArticlesBbsByCategory1Keyword(String category1, String keyword, Pageable pageable);
    @Query(value = "select a from Article a where a.title like %:keyword% order by a.date desc")
    public Page<Article>loadArticlesBbsByKeyword(String keyword, Pageable pageable);
    @Query(value = "select a from Article a where a.category1 = :category1 order by a.date desc")
    public Page<Article>loadArticlesBbsByCategory1(String category1, Pageable pageable);

}