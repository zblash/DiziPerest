package com.diziperest.web.services.abstracts;

import com.diziperest.web.models.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> findAll();

    User findByUserName(String userName);

    User findById(Long userId);

    void Add(User user);

    void Delete(User user);

    User Update(User user);

    User findByEmail(String email);

    Optional<User> findByResetToken(String token);
}
