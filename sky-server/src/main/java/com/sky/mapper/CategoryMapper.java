package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import com.github.pagehelper.Page;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface CategoryMapper {
    

    /**
    *分类管理分页查询
    * @param categoryPageQueryDTO
    * @return
    */
    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
    * 修改分类
     * @param categoryDTO
     * @return
    */
    void update(Category category);

    /**
    * 新增分类
     * @param category
     * @return
    */
   @AutoFill(OperationType.INSERT)
    void insert(Category category);

    /**
    * 根据id删除分类
     * @param id
     * @return
    */
    @Delete("delete from category where id = #{id}")
    void deleteById(Long id);

    /**
    * 根据类型查询分类
     * @param type
     * @return
    */
    @Select("select * from category where type = #{type}")
    Category[] selectByType(Integer id);
}
