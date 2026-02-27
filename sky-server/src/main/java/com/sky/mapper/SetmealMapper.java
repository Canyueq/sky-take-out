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
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;

@Mapper
public interface SetmealMapper {
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

    /**
     * 根据分类id查询套餐
     * @param categoryId
     * @return
     */
    @Select("select * from setmeal where category_id=#{categoryId}")
    List<Setmeal> getByCategoryId(Long categoryId);

    /**
     * 根据id查询菜品
     * @param id
     * @return
     */
    @Select("select sd.name,sd.copies,d.image,d.description from setmeal_dish sd left join dish d on sd.dish_id = d.id where sd.setmeal_id=#{id}")
    List<DishItemVO> getDishItemById(Long id);
    
}
