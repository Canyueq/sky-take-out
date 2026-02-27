package com.sky.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;

@Mapper
public interface DishMapper {

     @Select("select count(id) from dish where category_id=#{categoryId}")
     Integer getByCategoryId(long categoryId);

     /**
     * 新增菜品
     * @param dishDto
     * @return
     */
     @AutoFill(OperationType.INSERT)
     void add(Dish dish);

     /**
     * 修改菜品
     * @param dish
     * @return
     */
     @AutoFill(value = OperationType.UPDATE)
     void update(Dish dish);

     /**
      * 菜品分页查询
      * @param dishPageQueryDTO
      * @return
      */
     Page<DishVO> page(DishPageQueryDTO dishPageQueryDTO);

     /**
      * 通过id删除菜品
      * @param id
      */
     void deleteById(Long id);

     /**
      * 通过id查询菜品
      * @param id
      * @return
      */
     @Select("select * from dish where id=#{id}")
     Dish selectById(long id);

     /**
      * 通过id集合删除菜品
      * @param id
      */
     void deleteByIds(List<Long> ids);

     /**
     * 根据id查询菜品
     * @param id
     * @return
     */
    @Select("select * from dish where id=#{id}")
     Dish getById(long id);

     /**
      * 通过名称查询菜品
      * @param name
      * @return
      */
     @Select("select * from dish where name like concat('%',#{name},'%')")
     List<Dish> getListByCategoryName(String name);

     /**
      * 通过分类id查询菜品
      * @param categoryId
      * @return
      */
     @Select("select * from dish where category_id=#{categoryId}")
     List<Dish> getListByCategoryId(Long categoryId);
     

}
