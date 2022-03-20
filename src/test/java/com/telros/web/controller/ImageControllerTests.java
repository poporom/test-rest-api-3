package com.telros.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telros.entity.Image;
import com.telros.service.ImageService;
import com.telros.utils.TestHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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
@SpringBootTest
@ContextConfiguration
public class ImageControllerTests {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    ImageService imageService;

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    Image existingImage, newImage, updateImage;

    @Before
    public void setUp() {
        newImage = TestHelper.buildImage();
        existingImage = TestHelper.buildImage();
        updateImage = TestHelper.buildImage();

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    public void should_get_all_images() throws Exception {
        given(imageService.getAllImages()).willReturn(Arrays.asList(existingImage, updateImage));

        this.mockMvc
                .perform(get("/api/images"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void should_get_image_by_id() throws Exception {
        given(imageService.getImageById(existingImage.getId())).willReturn(Optional.of(existingImage));

        this.mockMvc
                .perform(get("/api/images/"+existingImage.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(existingImage.getId())));
    }

    @Test
    public void should_create_image() throws Exception {
        given(imageService.createImage(newImage)).willReturn(newImage);

        this.mockMvc
                .perform(post("/api/images/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newImage))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()));
    }

    @Test
    public void should_update_image() throws Exception {
        given(imageService.updateImage(existingImage)).willReturn(existingImage);

        this.mockMvc
                .perform(put("/api/images/"+existingImage.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(existingImage))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(existingImage.getId())));
    }

    @Test
    public void should_delete_image() throws Exception {
        doNothing().when(imageService).deleteImage(existingImage.getId());

        this.mockMvc
                .perform(delete("/api/images/"+existingImage.getId()))
                .andExpect(status().isOk());
    }
    
}
