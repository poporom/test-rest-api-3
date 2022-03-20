package com.telros.service;

import com.telros.entity.Image;
import com.telros.entity.User;
import com.telros.repo.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

@Service
@Transactional
public class ImageService {
    private final ImageRepository imageRepository;
    private final UserService userService;

    @Autowired
    public ImageService(ImageRepository imageRepository, UserService userService) {
        this.imageRepository = imageRepository;
        this.userService = userService;
    }

    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    public Optional<Image> getImageById(Integer id) {
        return imageRepository.findById(id);
    }

    public Image createImage(Image image) {
        List<User> collect = image.getUsers().stream()
                .map(model -> userService.getUserById(model.getId()).get())
                .collect(Collectors.toList());
        image.setUsers(collect);
        return imageRepository.save(image);
    }

    public Image updateImage(Image image) {
        return imageRepository.save(image);
    }

    public void deleteImage(Integer imageId){
        imageRepository.deleteById(imageId);
    }
}
