package com.telros.service;

import com.telros.entity.User;
import com.telros.entity.UserInfo;
import com.telros.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByUserInfoId(Long userInfoId) {return userRepository.findByUserInfoId(userInfoId); }

    //public User findByUserInfo(UserInfo userInfo) {return userRepository.findByUserInfo(userInfo); }

    //public User findByImageId(Long imageId) {return  userRepository.findByImageId(imageId); }
}
