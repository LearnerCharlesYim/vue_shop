package com.charles.vo;

import com.charles.entity.Picture;

import java.util.List;

public class PictureVO {
    List<Picture> pics;

    public List<Picture> getPics() {
        return pics;
    }

    public void setPics(List<Picture> pics) {
        this.pics = pics;
    }

    @Override
    public String toString() {
        return "PictureVO{" +
                "pics=" + pics +
                '}';
    }
}
