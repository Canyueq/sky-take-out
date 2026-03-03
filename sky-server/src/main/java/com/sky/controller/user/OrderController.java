package com.sky.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController("userOrderController")
@RequestMapping("/user/order")
@Api(tags = "订单接口")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;
    
    @PostMapping("submit")
    @ApiOperation("用户下单接口")
    public Result<OrderSubmitVO> sumbit(@RequestBody OrdersSubmitDTO ordersSubmitDTO){
        OrderSubmitVO orderSubmitVO = orderService.submit(ordersSubmitDTO);
        return Result.success(orderSubmitVO);
    }

    /**
     * 订单支付
     *
     * @param ordersPaymentDTO
     * @return
     */
    @PutMapping("/payment")
    @ApiOperation("订单支付")
    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        log.info("订单支付：{}", ordersPaymentDTO);
        OrderPaymentVO orderPaymentVO = orderService.payment(ordersPaymentDTO);
        log.info("生成预支付交易单：{}", orderPaymentVO);
        return Result.success(orderPaymentVO);
    }
    
    /**
     * 用户历史订单分页查询
     * @param page
     * @param size
     * @param status
     * @return
     */
    @GetMapping("historyOrders")
    @ApiOperation("用户历史订单分页查询")
    public Result<PageResult> pageQueryUser(int pageSize ,int page  ,Integer status) {
        PageResult pageResult = orderService.pageQueryUser(page, pageSize, status);
        return Result.success(pageResult);
    }
    

    /**
     * 根据订单id查询历史订单详情
     * @param id
     * @return
     */
    @GetMapping("/orderDetail/{id}")
    @ApiOperation("查询用户订单详情")
    public Result<OrderVO> getOrderDetail(@PathVariable Long id) {
        OrderVO orderVO = orderService.getById(id);
        return Result.success(orderVO);
    }
    
    /**
     * 再来一单
     * @param id
     * @return
     */
    @PostMapping("repetition/{id}")
    @ApiOperation("再来一单")
    public Result repetitionById(@PathVariable Long id) {
        //TODO: process POST request
        orderService.repetition(id);
        return Result.success();
    }
    /**
     * 
     * @param id
     * @return
     */
    @PostMapping("cancel/{id}")
    @ApiOperation("取消订单")
    public Result cancelById(@PathVariable Long id) {
        //TODO: process POST request
        
        return Result.success();
    }
    
}
