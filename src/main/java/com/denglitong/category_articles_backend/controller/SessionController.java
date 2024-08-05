package com.denglitong.category_articles_backend.controller;

import com.denglitong.category_articles_backend.auth.AuthRequired;
import com.denglitong.category_articles_backend.entity.SessionEntity;
import com.denglitong.category_articles_backend.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/30
 */
@RestController
@RequestMapping("/rest/session")
public class SessionController {

    @Autowired
    private SessionRepository sessionRepository;

    @RequestMapping("/list")
    @AuthRequired
    public List<SessionEntity> listAll() {
        return sessionRepository.findAll();
    }
}
