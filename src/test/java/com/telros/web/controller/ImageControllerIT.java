package com.telros.web.controller;

import com.telros.entity.Image;
import com.telros.repo.ImageRepository;
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

public class ImageControllerIT extends BaseIntegrationTest {
    private Image newImage;
    private Image updateImage;
    private Image existingImage;

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    UserRepository userRepository;

    @Before
    void setup() {
        setupTestData();

        newImage = TestHelper.buildImage();
        newImage.setUsers(asList(newUser));
        newImage = imageRepository.save(newImage);
        updateImage = TestHelper.buildImage();
        updateImage = imageRepository.save(updateImage);
        existingImage = TestHelper.buildImage();
        existingImage = imageRepository.save(existingImage);
    }

    @After
    void tearDown() {
        cleanupTestData();
        if(newImage.getId() != null) {
            imageRepository.deleteById(newImage.getId());
        }
        imageRepository.deleteAll(imageRepository.findAllById(asList(existingImage.getId(), updateImage.getId())));

    }

    @Test
    void should_get_all_images() {
        ResponseEntity<Image[]> responseEntity = restTemplate.getForEntity("/api/images", Image[].class);
        List<Image> images = asList(responseEntity.getBody());
        assertThat(images).isNotEmpty();
    }

    @Test
    void should_get_image_by_id() {
        ResponseEntity<Image> responseEntity = restTemplate.getForEntity("/api/images/" + newImage.getId(), Image.class);
        Image image = responseEntity.getBody();
        assertThat(image).isNotNull();
    }

    @Test
    void should_create_image() {
        HttpEntity<Image> request= new HttpEntity<>(newImage);
        ResponseEntity<Image> responseEntity = restTemplate.postForEntity("/api/images", request, Image.class);
        Image savedImage = responseEntity.getBody();
        assertThat(savedImage.getId()).isNotNull();
    }

    @Test
    void should_update_image() {
        HttpEntity<Image> request = new HttpEntity<>(updateImage);
        String url = "/api/images/"+updateImage.getId();
        restTemplate.put(url, request, Image.class);
        ResponseEntity<Image> responseEntity = restTemplate.getForEntity(url, Image.class);
        Image updatedImage = responseEntity.getBody();
        assertThat(updatedImage.getId()).isEqualTo(updateImage.getId());
    }

    @Test
    void should_delete_image() {
        String url = "/api/images/"+ existingImage.getId();
        ResponseEntity<Image> responseEntity = restTemplate.getForEntity(url, Image.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        restTemplate.delete(url);
        responseEntity = restTemplate.getForEntity(url, Image.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
