package com.sky.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

public interface SetmealService {
    /**
     * 分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    public PageResult page(SetmealPageQueryDTO setmealPageQueryDTO);
    
    /**
     * 新增
     * @param setmealDTO
     */
    public void add (SetmealDTO setmealDTO);

    /**
     * 编辑
     * @param setmealDTO
     */
    public void update(SetmealDTO setmealDTO);

    /**
     * 修改状态
     * @param status
     * @param id
     */
    public void setStatus(Integer status,Long id);

    /**
     * 批量删除
     * @param ids
     */
    public void deleteByIds(List<Long> ids);

    /**
     * 通过id查询
     * @param id
     * @return
     */
    public SetmealVO getById(Long id);

    /**
     * 通过分类id查询
     * @param categoryId
     * @return
     */
    public List<Setmeal> list(Setmeal setmeal);
}
