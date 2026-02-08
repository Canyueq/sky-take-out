package com.sky.service;

import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.dto.EmployeeDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 员工创建
     * @param employeeDTO
     * @return
     */
    Employee save(EmployeeDTO employeeDTO);
    
    /**
     * 分页查询
     * @param employeeDTO
     * @return
     */
    PageResult page(EmployeePageQueryDTO employeePageQueryDTO);

    /**
    * 设置员工账号状态
    * @param status,id
    * @return 
    */
   public void setStatus(Integer status,long id);


    /**
    * 根据id查询员工
    *
    * @param employeePage
    * @return
    */
   public Employee getById(long id);

    /**
     * 修改员工
     * 
     * @param employee
     * @return
     */
    public void update(Employee employee);
}
