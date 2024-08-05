package com.denglitong.category_articles_backend.service;

import com.denglitong.category_articles_backend.DTO.UserDTO;
import com.denglitong.category_articles_backend.DTO.UserLoginDTO;
import com.denglitong.category_articles_backend.context.UserContextHolder;
import com.denglitong.category_articles_backend.entity.SessionEntity;
import com.denglitong.category_articles_backend.entity.UserEntity;
import com.denglitong.category_articles_backend.exception.BusinessException;
import com.denglitong.category_articles_backend.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.denglitong.category_articles_backend.utils.Encrypt.SHA256;
import static com.denglitong.category_articles_backend.utils.EntityDTOBuilder.buildDTO;
import static com.denglitong.category_articles_backend.utils.EntityDTOBuilder.buildEntity;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/28
 */
@Service
public class UserService {

    private UserRepository userRepository;

    private SessionService sessionService;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setSessionService(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    public UserDTO save(UserDTO userDTO) {
        UserEntity user = buildEntity(userDTO, UserEntity.class);
        user.setPassword(SHA256(user.getPassword()));
        userRepository.save(user);
        return buildDTO(user, UserDTO.class);
    }

    public UserDTO findByUserName(String userName) {
        return buildDTO(userRepository.findByUserName(userName), UserDTO.class);
    }

    public List<UserDTO> findAll() {
        return buildDTO(userRepository.findAll(), UserDTO.class);
    }

    public boolean existByUserName(String userName) {
        return userRepository.findByUserName(userName) != null;
    }

    public boolean existByEmail(String email) {
        return userRepository.findByEmail(email) != null;
    }

    public UserLoginDTO login(UserLoginDTO loginDTO) {
        // ① remember me query with frontend header session-id
        if (StringUtils.isNotBlank(UserContextHolder.getContext().getSessionId())) {
            if (!UserContextHolder.getContext().getActive()) {
                throw new BusinessException(HttpStatus.BAD_REQUEST.value(),
                        "Session has expired, please login");
            }
            Optional<UserEntity> user = userRepository.findById(UserContextHolder.getContext().getUserId());
            if (!user.isPresent()) {
                throw new BusinessException(HttpStatus.BAD_REQUEST.value(), String.format(
                        "sessionId %s invalid", UserContextHolder.getContext().getSessionId()));
            }
            UserLoginDTO dto = buildDTO(user.get(), UserLoginDTO.class);
            dto.setSessionId(UserContextHolder.getContext().getSessionId());
            return dto;
        }

        // ② normal login
        if (loginDTO == null) {
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(), "Please enter account and password");
        }
        UserEntity user = null;
        loginDTO.setPassword(SHA256(loginDTO.getPassword()));
        if (StringUtils.isNotBlank(loginDTO.getUserName())) {
            user = userRepository.findByUserNameAndPassword(loginDTO.getUserName(), loginDTO.getPassword());
        } else {
            user = userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        }
        if (user == null) {
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(),
                    (StringUtils.isNotBlank(loginDTO.getUserName()) ? "userName" : "email")
                            + " or password wrong");
        }

        // ③ update user session context
        SessionEntity session = sessionService.create(user, loginDTO.getRememberMe());
        UserLoginDTO dto = buildDTO(user, UserLoginDTO.class);
        dto.setSessionId(session.getSessionId());
        dto.setRememberMe(loginDTO.getRememberMe());

        return dto;
    }

    public void logout() {
        if (!UserContextHolder.getContext().getActive()) {
            return;
        }
        String sessionId = UserContextHolder.getContext().getSessionId();
        sessionService.expireSessionById(sessionId);
    }
}
