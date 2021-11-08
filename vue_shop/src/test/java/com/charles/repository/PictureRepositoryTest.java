package com.charles.repository;

import com.charles.entity.Picture;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class PictureRepositoryTest {

    @Resource
    private PictureRepository pictureRepository;

    @Test
    public void test(){
        ObjectMapper mapper = new ObjectMapper();
        List<Picture> pictures = pictureRepository.findAll();
        for (Picture picture : pictures) {
            System.out.println(picture.getGoods());
        }
    }
}
