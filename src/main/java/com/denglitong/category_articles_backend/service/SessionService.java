package com.denglitong.category_articles_backend.service;

import com.denglitong.category_articles_backend.config.AppConfig;
import com.denglitong.category_articles_backend.context.UserContextHolder;
import com.denglitong.category_articles_backend.entity.SessionEntity;
import com.denglitong.category_articles_backend.entity.UserEntity;
import com.denglitong.category_articles_backend.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.time.LocalDateTime.now;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/29
 */
@Service
public class SessionService {

    private SessionRepository sessionRepository;

    private AppConfig appConfig;

    @Autowired
    public void setSessionRepository(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Autowired
    public void setAppConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public SessionEntity create(UserEntity user, boolean rememberMe) {
        SessionEntity session = new SessionEntity();
        session.setUserId(user.getUserId());
        session.setActive(TRUE);
        if (rememberMe) {
            LocalDateTime now = now();
            session.setCreatedTime(now);
            session.setUpdatedTime(now);
            session.setKeepActiveUntil(now.plusDays(appConfig.getDaysKeepUserLogin()));
        }
        sessionRepository.save(session);
        UserContextHolder.getContext().update(session);
        return session;
    }

    public SessionEntity getActiveSessionById(String sessionId) {
        return sessionRepository.findBySessionIdAndIsActive(sessionId, TRUE);
    }

    public void expireSessionById(String sessionId) {
        sessionRepository.updateSession(sessionId, FALSE, now());
        UserContextHolder.expire();
    }
}
