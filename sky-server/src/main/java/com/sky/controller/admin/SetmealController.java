package com.sky.controller.admin;

import java.util.List;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.impl.SetmealServiceImpl;
import com.sky.vo.SetmealVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/admin/setmeal")
@Slf4j
@Api(tags="套餐相关接口")
public class SetmealController {
    SetmealServiceImpl setmealServiceImpl;
    public SetmealController(SetmealServiceImpl serviceImpl){
        this.setmealServiceImpl = serviceImpl;
    }
    /**
     * 分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    @GetMapping("page")
    @ApiOperation("分页查询")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO){
        PageResult pageResult = setmealServiceImpl.page(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 新增
     * @param setmealDTO
     * @return
     */
    @PostMapping
    @ApiOperation("新增")
    public Result add(@RequestBody SetmealDTO setmealDTO) {
        //TODO: process POST request
        log.info("新增的套餐,{}",setmealDTO);
        setmealServiceImpl.add(setmealDTO);
        return Result.success();
    }

    /**
     * 编辑套餐
     * @param setmealDTO
     * @return
     */
    @PutMapping
    @ApiOperation("编辑套餐")
    public Result update(@RequestBody SetmealDTO setmealDTO) {
        //TODO: process PUT request
        log.info("编辑的套餐，{}",setmealDTO);
        setmealServiceImpl.update(setmealDTO);
        return Result.success();
    }
    
    /**
     * 修改状态
     * @param status
     * @param id
     * @return
     */
    @PostMapping("status/{status}")
    @ApiOperation("修改状态")
    public Result setStatus(@PathVariable Integer status,Long id) {
        //TODO: process POST request
        setmealServiceImpl.setStatus(status,id);
        return Result.success();
    }
    
    /**
     * 通过ids删除套餐
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("通过ids删除套餐")
    public Result deleteByIds(@RequestParam("ids") List<Long> ids){
        setmealServiceImpl.deleteByIds(ids);
        return Result.success();
    }

    /**
     * 通过id查询
     * @param id
     * @return
     */
    @GetMapping
    @ApiOperation("通过id查询")
    public Result<SetmealVO> getById(Long id) {
        SetmealVO setmealVO = setmealServiceImpl.getById(id);
        return Result.success(setmealVO);
    }
    
}
