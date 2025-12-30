package com.habeeb.p2plearn.repositories;

import com.habeeb.p2plearn.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public interface SessionRepository extends JpaRepository<Session,Long> {
    Optional<Session> findByToken(String token);

    List<Session> findByUser(User user);

    List<Session> findByUserAndIsActiveTrue(User user);

    void deleteByToken(String token);

    void deleteByUser(User user);

    void deleteByExpiryDateBefore(LocalDateTime dateTime);

    long countByUserAndIsActiveTrue(User user);

    List<Session> findByIsActiveFalse();

}
