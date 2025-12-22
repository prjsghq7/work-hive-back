package com.dev.workhiveback.services;

import com.dev.workhiveback.entities.ImageEntity;
import com.dev.workhiveback.entities.UserEntity;
import com.dev.workhiveback.exceptions.ImageException;
import com.dev.workhiveback.exceptions.LoginException;
import com.dev.workhiveback.mappers.ImageMapper;
import com.dev.workhiveback.results.CommonResult;
import com.dev.workhiveback.results.reasons.image.ImageFailReason;
import com.dev.workhiveback.results.reasons.image.ImageResult;
import com.dev.workhiveback.results.reasons.login.LoginFailReason;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ImageServices {

    private final ImageMapper imageMapper;

    public ImageEntity getByIndex(int index) {
        if (index < 1) {
            return null;
        }
        return this.imageMapper.selectByIndex(index);
    }

    public ImageResult add(UserEntity user, ImageEntity image) {
        System.out.println("image 추가");
        if (user == null) {
            throw new LoginException(LoginFailReason.USER_NOT_FOUND, "로그인 부탁드립니다.");
        }
        image.setCreatedAt(LocalDateTime.now());
        int result = this.imageMapper.insert(image);
        if (result < 1) {
            throw new ImageException(ImageFailReason.IMAGE_UPLOAD_FAIL, "이미지 업로드 실패");
        }
        return ImageResult.success();
    }

}
