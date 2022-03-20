package com.telros.web.controller;

import com.telros.entity.UserInfo;
import com.telros.service.UserInfoService;
import com.telros.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/userinfos")
@Slf4j
public class UserInfoController {
    private final UserInfoService userInfoService;
    private final UserService userService;

    @Autowired
    public UserInfoController(UserInfoService userInfoService, UserService userService) {
        this.userInfoService = userInfoService;
        this.userService = userService;
    }

    @GetMapping("")
    public List<UserInfo> getUserInfos() {
        log.info("process=get-userinfos");
        return userInfoService.getAllUserInfos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserInfo> getUserInfo(@PathVariable Integer id) {
        log.info("process=get-userinfo, user_info_id={}", id);
        Optional<UserInfo> userInfo = userInfoService.getUserInfoById(id);
        return userInfo.map(u -> ResponseEntity.ok(u))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public UserInfo createUserInfo(@RequestBody UserInfo userInfo) {
        log.info("process=create-userinfo, id={}", userInfo);
        return userInfoService.createUserInfo(userInfo);
    }

    @PutMapping("/{id}")
    public UserInfo updateUserInfo(@PathVariable Integer id, @RequestBody UserInfo userInfo) {
        log.info("process=update-userinfo, user_info_id={}",id);
        userInfo.setId(id);
        return userInfoService.updateUserInfo(userInfo);
    }

    @DeleteMapping("/{id}")
    public void deleteUserInfo(@PathVariable Integer id) {
        log.info("process=delete-userinfo, user-info-id={}", id);
//        User user = userService.findByUserInfoId(id);
//        user.setUserInfo(null);
//        userService.updateUser(user);
        userInfoService.deleteUserInfo(id);
    }
}
