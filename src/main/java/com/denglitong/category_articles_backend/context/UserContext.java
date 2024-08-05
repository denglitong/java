package com.denglitong.category_articles_backend.context;

import com.denglitong.category_articles_backend.entity.SessionEntity;
import org.springframework.stereotype.Component;

import static java.lang.Boolean.FALSE;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/29
 */
@Component
public class UserContext {

    public static final String USER_SESSION_ID = "USER-SESSION-ID";

    private String sessionId;

    /**
     * whether user session is active, or to say whether user is login.
     */
    private boolean isActive = FALSE;

    private Integer userId;

    public void update(SessionEntity session) {
        setSessionId(session.getSessionId());
        setUserId(session.getUserId());
        setActive(session.getActive());
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public boolean getActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
