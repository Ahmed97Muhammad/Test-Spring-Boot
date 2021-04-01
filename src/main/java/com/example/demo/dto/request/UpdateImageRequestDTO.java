package com.example.demo.dto.request;

import org.springframework.web.multipart.MultipartFile;

public class UpdateImageRequestDTO {

    private MultipartFile image;
    private String imageName;

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
