package com.charles.service.impl;

import com.charles.entity.Picture;
import com.charles.repository.PictureRepository;
import com.charles.service.PictureService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class PictureServiceImpl implements PictureService {

    @Resource
    private PictureRepository pictureRepository;

    @Override
    public Picture save(Picture picture) {
        return pictureRepository.save(picture);
    }

    @Override
    public Picture update(Picture pic) {
        if (pic.getPicId() == null) {
            return pictureRepository.save(pic);
        }
        return pic;
    }
}

