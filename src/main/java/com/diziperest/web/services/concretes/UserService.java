package com.diziperest.web.services.concretes;

import com.diziperest.web.models.Hashtag;
import com.diziperest.web.models.Role;
import com.diziperest.web.models.User;
import com.diziperest.web.repositories.RoleRepository;
import com.diziperest.web.repositories.UserRepository;
import com.diziperest.web.services.abstracts.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAll() {

        return userRepository.findAll();
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(RuntimeException::new);
    }

    @Override
    public void Add(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByName("USER").orElseGet(() -> {
            Role role1 = new Role();
            role1.setName("USER");
            return roleRepository.save(role1);
        });
        user.setRole(role);
        userRepository.save(user);

    }

    @Override
    public void Delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public User Update(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public Optional<User> findByResetToken(String token){
        return userRepository.findByResetToken(token);
    }
}