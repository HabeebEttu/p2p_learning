package com.habeeb.p2plearn.repositories;

import com.habeeb.p2plearn.models.Article;
import com.habeeb.p2plearn.models.ArticleCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Long> {
    Optional<Article> findBySlug(String slug);
    List<Article> findByUserId(Long userId);
    void deleteBySlug(String slug);
    List<Article> findByTitleContainingIgnoreCase(String keyword);
    List<Article> findByCategory(ArticleCategory category);
}
