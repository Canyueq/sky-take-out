package com.sky.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;

@Mapper
public interface SetmealMapper {

    @Select("select count(id) from setmeal where category_id=#{categoryId}")
    Integer getByCategoryId(long categoryId);

    /**
     * 分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    public Page<Setmeal> page(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 新增
     * @param setmeal
     */
    @AutoFill(value = OperationType.INSERT)
    public void add (Setmeal setmeal);

    /**
     * 编辑
     * @param setmeal
     */
    @AutoFill(value = OperationType.UPDATE)
    public void update(Setmeal setmeal);

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
    @Select("select * from setmeal where id=#{id}")
    public SetmealVO getById(Long id);
}
