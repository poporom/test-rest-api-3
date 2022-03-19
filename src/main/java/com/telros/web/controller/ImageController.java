package com.telros.web.controller;

import com.telros.entity.Image;
import com.telros.entity.User;
import com.telros.service.ImageService;
import com.telros.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/images")
@Slf4j
public class ImageController {
    private final ImageService imageService;
    private final UserService userService;

    public ImageController(ImageService imageService, UserService userService) {
        this.imageService = imageService;
        this.userService = userService;
    }

    @GetMapping("")
    public List<Image> getImages () {
        log.info("process=get-images");
        return imageService.getImages();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Image> getImage(@PathVariable Long id) {
        log.info("process=get-image, image_id={}", id);
        Optional<Image> image = imageService.getImage(id);
        return image.map(u -> ResponseEntity.ok(u))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{userId}")
    public Image createImage(@PathVariable Long userId, Image model){
        log.info("process=create-image, user_id={}", userId);
        Image image = imageService.createImage(model);
        User user = userService.getUserById(userId).get();
        user.setImageId(image.getId());
        userService.updateUser(user);
        return image;
    }

    @PutMapping("/{id}")
    public Image updateImage(@PathVariable Long id, Image image) {
        log.info("process=update-image, image_id={}", id);
        image.setId(id);
        return imageService.updateImage(image);
    }

    @DeleteMapping("/{id}")
    public void deleteImage(@PathVariable Long id) {
        log.info("process=delete-image, image_id={}", id);
        User user = userService.findByImageId(id);
        user.setImageId(0L);
        userService.updateUser(user);
        imageService.deleteImage(id);
    }
}
