package com.charles.repository;

import com.charles.entity.Goods;
import com.charles.entity.Picture;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class GoodsRepositoryTest {

    @Resource
    private GoodsRepository goodsRepository;

    @Resource
    private PictureRepository pictureRepository;

    @Test
    public void test() throws JsonProcessingException {
        List<Goods> goodsList = goodsRepository.findAll();
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(goodsList);
        System.out.println(s);
        for (Goods goods : goodsList) {
            List<Picture> pictures = goods.getPictures();
            System.out.println("================================");
            System.out.println(pictures);
            System.out.println("================================");
            for (Picture picture : pictures) {
                System.out.println("================================");
                System.out.println(picture.getPicBig());
                System.out.println("================================");
            }
        }
    }

    @Test
    public void test2() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Picture picture = new Picture();
        picture.setPicBig("hhh");

        Goods goods = new Goods();
        goods.setId(-1);
        goods.setPictures(Arrays.asList(picture));
        System.out.println(mapper.writeValueAsString(goodsRepository.save(goods)));

    }


}
