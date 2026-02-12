package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import com.github.pagehelper.Page;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);
    /**
     * 存储新增员工数据
     * @param employee
     * @return
     */
    @Insert("Insert into employee(name,username,password,phone,sex,id_number,status,create_time,update_time,create_user,update_user)"
    +" values(#{name},#{username},#{password},#{phone},#{sex},#{idNumber},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    @AutoFill(value = OperationType.INSERT)
    void savEmployee(Employee employee);

    /**
    * 查询员工数据
    * @param name
    * @return  
    */
    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
    * 根据id查询员工
    *
    * @param employeePage
    * @return
    */
    Employee getById(long id);

   /**
    * 修改员工账号
    * @param status
    * @param id
    * @return 
    */
   @AutoFill(value = OperationType.UPDATE)
   void update(Employee employee);
}
