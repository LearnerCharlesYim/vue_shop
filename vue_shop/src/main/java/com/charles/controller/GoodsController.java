package com.charles.controller;

import com.charles.controller.ex.FileEmptyException;
import com.charles.dto.GoodsListDto;
import com.charles.dto.GoodsUploadDto;
import com.charles.entity.Goods;
import com.charles.entity.GoodsAttrs;
import com.charles.entity.Picture;
import com.charles.service.GoodsAttrsService;
import com.charles.service.GoodsService;
import com.charles.service.PictureService;
import com.charles.util.IDUtil;
import com.charles.util.JsonResult;
import com.charles.util.State;
import com.charles.vo.GoodsAttrsVO;
import com.charles.vo.PictureVO;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("goods")
public class GoodsController extends BaseController {

    @Resource
    private GoodsService goodsService;

    @Resource
    private PictureService pictureService;

    @Resource
    private GoodsAttrsService goodsAttrsService;

    @GetMapping("/list")
    public JsonResult<GoodsListDto> list(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
                                         Goods goods) {
        GoodsListDto result = goodsService.findByConditionAndPage(goods, pageNum, pageSize);
        return new JsonResult<>(State.OK, result);
    }

    /**
     * @param goods        商品entity
     * @param categoryIds  商品种类 用字符串,号分割
     * @param pictureVO    图片entity集合包装对象
     * @param goodsAttrsVO 商品属性entity集合包装对象
     * @return 商品entity
     */
    @PostMapping("/add")
    public JsonResult<Goods> add(Goods goods,
                                 String categoryIds,
                                 PictureVO pictureVO,
                                 GoodsAttrsVO goodsAttrsVO) {
        List<Picture> pictureList = new ArrayList<>();
        List<GoodsAttrs> goodsAttrsList = new ArrayList<>();
        if (pictureVO == null) {
            pictureList = null;
        } else {
            for (Picture pic : pictureVO.getPics()) {
                Picture picture = pictureService.save(pic);
                pictureList.add(picture);
            }
        }
        if (goodsAttrsVO == null) {
            goodsAttrsList = null;
        } else {
            for (GoodsAttrs attr : goodsAttrsVO.getAttrs()) {
                GoodsAttrs goodsAttrs = goodsAttrsService.save(attr);
                goodsAttrsList.add(goodsAttrs);
            }
        }

        Goods result = goodsService.save(goods, pictureList, goodsAttrsList, categoryIds);
        return new JsonResult<>(State.CREATED, result);
    }

    /**
     * @param id 商品id
     * @return 商品entity
     */
    @GetMapping("/{id}")
    public JsonResult<Goods> findOne(@PathVariable(value = "id") Integer id) {
        Goods goods = goodsService.findOneById(id);
        return new JsonResult<>(State.OK, goods);
    }

    @PutMapping("/{id}")
    public JsonResult<Goods> update(@PathVariable(value = "id") Integer id,
                                    Goods goods,
                                    String categoryIds,
                                    PictureVO pictureVO,
                                    GoodsAttrsVO goodsAttrsVO) {
        List<Picture> pictureList = new ArrayList<>();
        List<GoodsAttrs> goodsAttrsList = new ArrayList<>();
        if (pictureVO == null) {
            pictureList = null;
        } else {
            for (Picture pic : pictureVO.getPics()) {
                Picture picture = pictureService.update(pic);
                pictureList.add(picture);
            }
        }
        if (goodsAttrsVO == null) {
            goodsAttrsList = null;
        } else {
            for (GoodsAttrs attr : goodsAttrsVO.getAttrs()) {
                GoodsAttrs goodsAttrs = goodsAttrsService.update(attr);
                goodsAttrsList.add(goodsAttrs);
            }
        }
        Goods result = goodsService.update(goods, pictureList, goodsAttrsList, categoryIds);
        return new JsonResult<>(State.OK, result);
    }

    @DeleteMapping("/{id}")
    public JsonResult<Void> delete(@PathVariable(value = "id") Integer id) {
        goodsService.delete(id);
        return new JsonResult<>(State.OK);
    }

    @PostMapping("/upload")
    public JsonResult<GoodsUploadDto> upload(MultipartFile file) throws IOException {
        if (file == null) {
            throw new FileEmptyException("文件空指针异常");
        }
        String oldName = file.getOriginalFilename();
        String newName = IDUtil.getImageName();
        String datePath = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        assert oldName != null;
        newName += oldName.substring(oldName.lastIndexOf("."));
        File dest = new File(new File("vue_shop\\src\\main\\resources\\static\\upload\\goods\\").getAbsolutePath(),datePath);
        if (!dest.exists()) {
            dest.mkdirs();
        }
        file.transferTo(new File(dest, newName));
        GoodsUploadDto goodsUploadDto = new GoodsUploadDto();
        goodsUploadDto.setPath("/upload/goods/" + datePath + "/" + newName);
        goodsUploadDto.setUrl("http://localhost:90/upload/goods/" + datePath + "/" + newName);
        return new JsonResult<>(State.OK, goodsUploadDto);
    }
}
