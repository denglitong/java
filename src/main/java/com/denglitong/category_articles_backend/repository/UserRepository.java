package com.denglitong.category_articles_backend.repository;

import com.denglitong.category_articles_backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/27
 */
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByUserName(String userName);

    UserEntity findByEmail(String email);

    UserEntity findByUserNameAndPassword(String userName, String password);

    UserEntity findByEmailAndPassword(String email, String password);
}
