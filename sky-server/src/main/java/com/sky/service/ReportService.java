package com.sky.service;

import java.time.LocalDate;

import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;

public interface ReportService {
     
    /**
     * 获取指定时间的营业额
     * @param begin
     * @param end
     * @return
     */
    TurnoverReportVO getTurnoverStatistics(LocalDate begin,LocalDate end);

    /**
     * 获取指定时间的用户数据
     * @param begin
     * @param end
     * @return
     */
    UserReportVO getUserStatistics(LocalDate begin,LocalDate end);

    /**
     * 获取指定时间的订单数据
     * @param begin
     * @param end
     * @return
     */
    OrderReportVO getOrderStatistics(LocalDate begin,LocalDate end);

    /**
     * 获取指定时间的排名数据
     * @param begin
     * @param end
     * @return
     */
    SalesTop10ReportVO getSalesTop10(LocalDate begin,LocalDate end);
}
