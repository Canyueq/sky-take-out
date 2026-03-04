package com.sky.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.sky.entity.User;

@Mapper
public interface UserMapper {
    
    /**
     * 根据openid查询用户
     * @param openid
     * @return
     */
    @Select("select * from \"user\" where openid=#{openid}")
    User getByopenid(String openid);

    /**
     * 创建新用户
     * @param user
     */
    void insert(User user);
    
    /**
     * 根据id查询用户
     * @param id
     * @return 
     */
    @Select("select * from \"user\" where id=#{id}")
    User getById(Long id);

    Integer countByMap(Map map);
}
