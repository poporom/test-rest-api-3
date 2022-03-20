package com.telros.utils;

import com.telros.entity.Image;
import com.telros.entity.User;
import com.telros.entity.UserInfo;

import java.sql.Timestamp;
import java.util.Random;
import java.util.UUID;


public class TestHelper {
    public static User buildUser() {
        String uuid = UUID.randomUUID().toString();
        return User.builder()
                .username("username-"+uuid)
                .password(uuid)
                .build();
    }

    public static User buildUserWithId() {
        Random random = new Random();
        String uuid = UUID.randomUUID().toString();
        return User.builder()
                .id(random.nextInt())
                .username("username-"+uuid)
                .password(uuid)
                .build();
    }

    public static Image buildImage() {
        Random random = new Random();
        String uuid = UUID.randomUUID().toString();
        return Image.builder()
                .id(random.nextInt())
                .url(uuid)
                .build();
    }

    public static UserInfo buildUserInfo() {
        Random random = new Random();
        String uuid = UUID.randomUUID().toString();
        Timestamp birthday = new Timestamp(System.currentTimeMillis());
        return UserInfo.builder()
                .id(random.nextInt())
                .firstName("firstname-" + uuid)
                .lastName("lastname-" + uuid)
                .patronymic("patronymic-" + uuid)
                .email("email-" + uuid)
                .birthday(birthday)
                .build();
    }
}
