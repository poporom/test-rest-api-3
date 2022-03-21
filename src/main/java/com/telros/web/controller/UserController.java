package com.telros.web.controller;

import com.telros.entity.Image;
import com.telros.entity.Role;
import com.telros.entity.User;
import com.telros.entity.UserInfo;
import com.telros.service.ImageService;
import com.telros.service.RoleService;
import com.telros.service.UserInfoService;
import com.telros.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserInfoService userInfoService;
    private final ImageService imageService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, UserInfoService userInfoService, ImageService imageService, RoleService roleService) {
        this.userService = userService;
        this.userInfoService = userInfoService;
        this.imageService = imageService;
        this.roleService = roleService;
    }

    @GetMapping("")
    public List<User> getUsers() {
        log.info("process=get-users");
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id) {
        log.info("process=get-user, user_id={}", id);
        Optional<User> user = userService.getUserById(id);
        return user.map( u -> ResponseEntity.ok(u))
                   .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("")
    @ResponseStatus(CREATED)
    public User createUser(@RequestBody User user) {
        log.info("process=create-user");
        /*
        image and user info must not be null otherwise findById will not work.
        */
        UserInfo userInfo = user.getUserInfo();
        userInfo.setEmail(user.getUserInfo().getEmail());
        userInfo.setUser(user);
        userInfo = userInfoService.updateUserInfo(userInfo);
        user.setUserInfo(userInfo);

        Image image = imageService.getImageById(user.getImage().getId()).get();
        if (null == image) {
            image = imageService.createImage(new Image());
        }

        List<User> list = new ArrayList<>();
        list.add(user);
        image.setUsers(list);
        image = imageService.createImage(image);
        user.setImage(image);

        List<Role> roles = new ArrayList<>();
        roles.add(roleService.getRoleById(1).get());
        user.setRoles(roles);

        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Integer id, @RequestBody User user) {
        log.info("process=update-user, user_id={}", id);
        user.setId(id);
        return userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        log.info("process=delete-user, user_id={}", id);
        //TODO have to clear roles
        //userService.deleteUser(id);
    }

}
