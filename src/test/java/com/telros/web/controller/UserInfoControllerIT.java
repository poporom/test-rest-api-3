package com.telros.web.controller;

import com.telros.entity.UserInfo;
import com.telros.repo.UserInfoRepository;
import com.telros.repo.UserRepository;
import com.telros.utils.BaseIntegrationTest;
import com.telros.utils.TestHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class UserInfoControllerIT extends BaseIntegrationTest {
    private UserInfo newUserInfo;
    private UserInfo updateUserInfo;
    private UserInfo existingUserInfo;

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    UserRepository userRepository;

    @Before
    void setup() {
        setupTestData();

        newUserInfo = TestHelper.buildUserInfo();
        newUserInfo.setUser(newUser);
        newUserInfo = userInfoRepository.save(newUserInfo);
        updateUserInfo = TestHelper.buildUserInfo();
        updateUserInfo = userInfoRepository.save(updateUserInfo);
        existingUserInfo = TestHelper.buildUserInfo();
        existingUserInfo = userInfoRepository.save(existingUserInfo);
    }

    @After
    void tearDown() {
        cleanupTestData();
        if(newUserInfo.getId() != null) {
            userInfoRepository.deleteById(newUserInfo.getId());
        }
        userInfoRepository.deleteAll(userInfoRepository.findAllById(asList(existingUserInfo.getId(), updateUserInfo.getId())));

    }

    @Test
    void should_get_all_userInfos() {
        ResponseEntity<UserInfo[]> responseEntity = restTemplate.getForEntity("/api/userInfos", UserInfo[].class);
        List<UserInfo> userInfos = asList(responseEntity.getBody());
        assertThat(userInfos).isNotEmpty();
    }

    @Test
    void should_get_userInfo_by_id() {
        ResponseEntity<UserInfo> responseEntity = restTemplate.getForEntity("/api/userInfos/" + newUserInfo.getId(), UserInfo.class);
        UserInfo userInfo = responseEntity.getBody();
        assertThat(userInfo).isNotNull();
    }

    @Test
    void should_create_userInfo() {
        HttpEntity<UserInfo> request= new HttpEntity<>(newUserInfo);
        ResponseEntity<UserInfo> responseEntity = restTemplate.postForEntity("/api/userInfos", request, UserInfo.class);
        UserInfo savedUserInfo = responseEntity.getBody();
        assertThat(savedUserInfo.getId()).isNotNull();
    }

    @Test
    void should_update_userInfo() {
        HttpEntity<UserInfo> request = new HttpEntity<>(updateUserInfo);
        String url = "/api/userInfos/"+updateUserInfo.getId();
        restTemplate.put(url, request, UserInfo.class);
        ResponseEntity<UserInfo> responseEntity = restTemplate.getForEntity(url, UserInfo.class);
        UserInfo updatedUserInfo = responseEntity.getBody();
        assertThat(updatedUserInfo.getId()).isEqualTo(updateUserInfo.getId());
    }

    @Test
    void should_delete_userInfo() {
        String url = "/api/userInfos/"+ existingUserInfo.getId();
        ResponseEntity<UserInfo> responseEntity = restTemplate.getForEntity(url, UserInfo.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        restTemplate.delete(url);
        responseEntity = restTemplate.getForEntity(url, UserInfo.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
    
}
