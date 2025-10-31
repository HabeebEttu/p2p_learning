package com.habeeb.p2plearn.repositories;

import com.habeeb.p2plearn.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByUserId(Long userId);
    List<Comment> findByArticleSlug(String articleSlug);
}
