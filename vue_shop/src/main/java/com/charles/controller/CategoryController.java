package com.charles.controller;

import com.charles.dto.CategoryDto;
import com.charles.dto.CategoryListDto;
import com.charles.dto.CategoryParamDto;
import com.charles.entity.Category;
import com.charles.entity.CategoryParam;
import com.charles.service.CategoryParamService;
import com.charles.service.CategoryService;
import com.charles.util.JsonResult;
import com.charles.util.State;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("categories")
public class CategoryController extends BaseController {

    @Resource
    private CategoryService categoryService;

    @Resource
    private CategoryParamService categoryParamService;

    @GetMapping("/list")
    public JsonResult<CategoryListDto> list(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                            @RequestParam(value = "pageSize",defaultValue = "5") int pageSize) {
        CategoryListDto categoryListDto = categoryService.findAllByPage(pageNum, pageSize);
        return new JsonResult<>(State.OK,categoryListDto);
    }

    @PostMapping("/add")
    public JsonResult<CategoryDto> add(Category category){
        CategoryDto categoryDto = categoryService.save(category);
        return new JsonResult<>(State.CREATED,categoryDto);
    }

    @GetMapping("/{id}")
    public JsonResult<CategoryDto> findOne(@PathVariable(value = "id") int id){
        CategoryDto categoryDto = categoryService.findOne(id);
        return new JsonResult<>(State.OK,categoryDto);
    }

    @PutMapping("/{id}")
    public JsonResult<CategoryDto> update(@PathVariable(value = "id") int id,Category category){
        CategoryDto categoryDto = categoryService.update(category);
        return new JsonResult<>(State.OK,categoryDto);
    }

    @DeleteMapping("/{id}")
    public JsonResult<Void> delete(@PathVariable(value = "id") int id){
        categoryService.delete(id);
        return new JsonResult<>(State.OK);
    }

    @GetMapping("/{id}/attributes")
    public JsonResult<List<CategoryParamDto>> list(@PathVariable(value = "id")  Integer cid,
                                                   CategoryParam categoryParam){

        List<CategoryParamDto> params = categoryParamService.findByCondition(categoryParam,cid);
        return new JsonResult<>(State.OK,params);
    }

    @PostMapping("/{id}/attributes")
    public JsonResult<CategoryParamDto> add(@PathVariable(value = "id") Integer cid, CategoryParam categoryParam){
        CategoryParamDto param = categoryParamService.save(categoryParam,cid);
        return new JsonResult<>(State.CREATED,param);
    }

    @DeleteMapping("/{id}/attributes/{paramId}")
    public JsonResult<Void> delete(@PathVariable(value = "id") Integer cid,
                                   @PathVariable(value = "paramId") Integer paramId){
        categoryParamService.delete(paramId);
        return new JsonResult<>(State.OK);
    }

    @GetMapping("/{id}/attributes/{paramId}")
    public JsonResult<CategoryParamDto> findOne(@PathVariable(value = "id") Integer cid,
                                   @PathVariable(value = "paramId") Integer paramId){
        CategoryParamDto param = categoryParamService.findOne(paramId);
        return new JsonResult<>(State.OK,param);
    }

    @PutMapping("/{cid}/attributes/{id}")
    public JsonResult<CategoryParamDto> update(@PathVariable(value = "cid") Integer cid,
                                                @PathVariable(value = "id") Integer id,
                                               CategoryParam categoryParam){
        categoryParamService.findOne(id);
        CategoryParamDto param = categoryParamService.update(categoryParam);
        return new JsonResult<>(State.OK,param);
    }
}
