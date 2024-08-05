package com.denglitong.category_articles_backend.context;

import com.denglitong.category_articles_backend.entity.SessionEntity;
import com.denglitong.category_articles_backend.service.SessionService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static java.lang.Boolean.TRUE;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/29
 */
@Component
public class UserContextFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(UserContextFilter.class);

    private SessionService sessionService;

    @Autowired
    public void setSessionService(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;

        String headerSessionId = httpServletRequest.getHeader(UserContext.USER_SESSION_ID);
        SessionEntity session = null;
        if (StringUtils.isNotBlank(headerSessionId)) {
            UserContextHolder.getContext().setSessionId(headerSessionId);
            session = sessionService.getActiveSessionById(headerSessionId);
            if (session != null) {
                UserContextHolder.getContext().setActive(TRUE);
                UserContextHolder.getContext().setUserId(session.getUserId());
            }
            logger.info("UserContextFilter headerSessionId: {}, session: {}", headerSessionId, session);
        }

        filterChain.doFilter(httpServletRequest, servletResponse);
    }
}
