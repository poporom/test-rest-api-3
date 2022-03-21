package com.telros.service;

import com.telros.entity.UserInfo;
import com.telros.repo.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserInfoService {
    private final UserInfoRepository userInfoRepository;

    @Autowired
    public UserInfoService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    public Optional<UserInfo> getUserInfoById(Integer id) {
        return userInfoRepository.findById(id);
    }

    public List<UserInfo> getAllUserInfos() {
        return userInfoRepository.findAll();
    }

    public UserInfo createUserInfo(UserInfo userInfo) {
        return userInfoRepository.save(userInfo);
    }

    public UserInfo updateUserInfo(UserInfo userInfo) {
        return userInfoRepository.save(userInfo);
    }

    public void deleteUserInfo(Integer userInfoId) {
        userInfoRepository.deleteById(userInfoId);
    }
}
