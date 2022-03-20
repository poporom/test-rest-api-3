package com.telros.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telros.entity.UserInfo;
import com.telros.service.UserInfoService;
import com.telros.utils.TestHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserInfoController.class, secure = false)
public class UserInfoControllerTests {
    
    @MockBean
    UserInfoService userInfoService;

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    UserInfo existingUserInfo, newUserInfo, updateUserInfo;

    @Before
    public void setUp() {
        newUserInfo = TestHelper.buildUserInfo();
        existingUserInfo = TestHelper.buildUserInfo();
        updateUserInfo = TestHelper.buildUserInfo();
    }

    @Test
    public void should_get_all_userInfos() throws Exception {
        given(userInfoService.getAllUserInfos()).willReturn(Arrays.asList(existingUserInfo, updateUserInfo));

        this.mockMvc
                .perform(get("/api/userinfos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void should_get_userInfo_by_id() throws Exception {
        given(userInfoService.getUserInfoById(existingUserInfo.getId())).willReturn(Optional.of(existingUserInfo));

        this.mockMvc
                .perform(get("/api/userinfos/"+existingUserInfo.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(existingUserInfo.getId())));
    }

    @Test
    public void should_create_userInfo() throws Exception {
        given(userInfoService.createUserInfo(newUserInfo)).willReturn(newUserInfo);

        this.mockMvc
                .perform(post("/api/userinfos/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUserInfo))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()));
    }

    @Test
    public void should_update_userInfo() throws Exception {
        given(userInfoService.updateUserInfo(existingUserInfo)).willReturn(existingUserInfo);

        this.mockMvc
                .perform(put("/api/userinfos/"+existingUserInfo.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(existingUserInfo))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(existingUserInfo.getId())));
    }

    @Test
    public void should_delete_userInfo() throws Exception {
        doNothing().when(userInfoService).deleteUserInfo(existingUserInfo.getId());

        this.mockMvc
                .perform(delete("/api/userinfos/"+existingUserInfo.getId()))
                .andExpect(status().isOk());
    }
    
}
