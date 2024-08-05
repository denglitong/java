package com.denglitong.category_articles_backend.repository;

import com.denglitong.category_articles_backend.entity.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/28
 */
public interface SessionRepository extends JpaRepository<SessionEntity, String> {

    SessionEntity findBySessionIdAndIsActive(String sessionId, Boolean isActive);

    @Modifying
    @Transactional
    @Query("UPDATE SessionEntity s SET " +
            "s.isActive = :isActive, " +
            "s.keepActiveUntil = :keepActiveUntil " +
            "WHERE s.sessionId = :sessionId")
    void updateSession(@Param("sessionId") String sessionId,
                       @Param("isActive") Boolean isActive,
                       @Param("keepActiveUntil") LocalDateTime keepActiveUntil);
}
