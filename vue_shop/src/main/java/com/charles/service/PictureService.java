package com.charles.service;

import com.charles.entity.Picture;

public interface PictureService {
    Picture save(Picture picture);

    Picture update(Picture pic);
}
