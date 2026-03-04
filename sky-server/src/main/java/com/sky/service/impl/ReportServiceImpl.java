package com.sky.service.impl;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;

import io.swagger.models.auth.In;

@Service
public class ReportServiceImpl implements ReportService{
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WorkspaceService workspaceService;
    /**
     * 获取指定时间的营业额
     * @param begin
     * @param end
     * @return
     */
    public TurnoverReportVO getTurnoverStatistics(LocalDate begin,LocalDate end){
        TurnoverReportVO turnoverReportVO = new TurnoverReportVO();
        List<LocalDate> dateList = new ArrayList<>();
        //计算所有时间
        dateList.add(begin);
        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            dateList.add(begin);
        }
        String timeStr = StringUtils.join(dateList,",");
        List<Double> turnoverList = new ArrayList<>();
        for(LocalDate data:dateList){
            LocalDateTime benginTime = LocalDateTime.of(data, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(end,LocalTime.MAX);
            Map map = new HashMap<>();
            map.put("begin",benginTime);
            map.put("end",endTime);
            map.put("status", Orders.COMPLETED);
            Double turnover =  orderMapper.sumByMap(map);
            turnover = turnover == null ?  0.0 : turnover;
            turnoverList.add(turnover);
        }
        turnoverReportVO.setDateList(timeStr);
        turnoverReportVO.setTurnoverList(StringUtils.join(turnoverList,","));
        return turnoverReportVO;
    }

    /**
     * 获取指定时间的用户数据
     * @param begin
     * @param end
     * @return
     */
    public UserReportVO getUserStatistics(LocalDate begin,LocalDate end){
        List<LocalDate> dateList = new ArrayList<>();
        //计算所有时间
        dateList.add(begin);
        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            dateList.add(begin);
        }
        //新增用户数量
        List<Integer> newUserList = new ArrayList<>();
        //总用户数量
        List<Integer> totalUserList = new ArrayList<>();
        for(LocalDate data:dateList){
            LocalDateTime benginTime = LocalDateTime.of(data, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);
            Map map = new HashMap<>();
            map.put("end",endTime);
            //总用户数量
            Integer totalUser = userMapper.countByMap(map);
            totalUserList.add(totalUser);
            map.put("begin",benginTime);
            Integer newUser = userMapper.countByMap(map);
            newUserList.add(newUser);
        }
        return UserReportVO.builder()
                            .dateList(StringUtils.join(dateList,","))
                            .newUserList(StringUtils.join(newUserList,","))
                            .totalUserList(StringUtils.join(totalUserList,","))
                            .build();
    }

    /**
     * 获取指定时间的用户数据
     * @param begin
     * @param end
     * @return
     */
    public OrderReportVO getOrderStatistics(LocalDate begin,LocalDate end){
        List<LocalDate> dateList = new ArrayList<>();
        //计算所有时间
        dateList.add(begin);
        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            dateList.add(begin);
        }
        List<Integer> orderCountList = new ArrayList<>();
        List<Integer> validOrderCountList = new ArrayList<>();
        for(LocalDate data:dateList){
            LocalDateTime benginTime = LocalDateTime.of(data, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);
            //总订单数
            Integer orderCount = getOrderCount(benginTime, endTime, null);
            //有效订单数
            Integer validOrderCount = getOrderCount(benginTime,endTime,Orders.COMPLETED);
            orderCountList.add(orderCount);
            validOrderCountList.add(validOrderCount);

        }
        //计算时间区间内的总订单数
        Integer totalOrderCount =  orderCountList.stream().reduce(Integer::sum).get();
        //计算时间区间内的有效订单数
        Integer validOrderCount = validOrderCountList.stream().reduce(Integer::sum).get();
        //计算订单完成率
        Double orderCompletionRate = 0.0;
        if(totalOrderCount != 0){
            orderCompletionRate = validOrderCount.doubleValue() / totalOrderCount;
        }
        return OrderReportVO.builder()
                            .dateList(StringUtils.join(dateList,","))
                            .orderCountList(StringUtils.join(dateList,","))
                            .validOrderCountList(StringUtils.join(validOrderCountList,","))
                            .totalOrderCount(totalOrderCount)
                            .validOrderCount(validOrderCount)
                            .orderCompletionRate(orderCompletionRate)
                            .build();
    }
    private Integer getOrderCount(LocalDateTime begin,LocalDateTime end,Integer stauts){
        Map map = new HashMap<>();
        map.put("begin",begin);
        map.put("end",end);
        map.put("status", stauts);
        return orderMapper.countByMap(map);
    }

    /**
     * 获取指定时间的排名数据
     * @param begin
     * @param end
     * @return
     */
    public SalesTop10ReportVO getSalesTop10(LocalDate begin,LocalDate end){
        LocalDateTime benginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);
        List<GoodsSalesDTO> salesTop10 = orderMapper.getSalesTop10(benginTime, endTime);
        List<String> names = salesTop10.stream().map(GoodsSalesDTO::getName).collect(Collectors.toList());
        List<Integer> numbers = salesTop10.stream().map(GoodsSalesDTO::getNumber).collect(Collectors.toList());
        // List<String> nameList = new ArrayList<>();
        // List<Integer> numberList = new ArrayList<>();
        // for(GoodsSalesDTO goodsSalesDTO : salesTop10){
        //     nameList.add(goodsSalesDTO.getName());
        //     numberList.add(goodsSalesDTO.getNumber());
        // }
        return SalesTop10ReportVO
            .builder()
            .nameList(StringUtils.join(names,","))
            .numberList(StringUtils.join(numbers, ","))
            .build();
    }

    /**
     * 导出运营报表
     * @param httpServletResponse
     */
    public void exportBusinessData(HttpServletResponse httpServletResponse){
        //1.查询数据
        LocalDate dateBegin = LocalDate.now().minusDays(30);
        LocalDate dateEnd = LocalDate.now().minusDays(1);
        BusinessDataVO businessDataVO = workspaceService.getBusinessData(
            LocalDateTime.of(dateBegin,LocalTime.MIN), 
            LocalDateTime.of(dateEnd,LocalTime.MAX)
        );
        //2.通过poi写入excel
        InputStream in =this.getClass().getClassLoader().getResourceAsStream("template/运营数据报表模板.xlsx");
        try {
            //基于模板文件创建新的excel文件

            //获取表格的sheet
            XSSFWorkbook excel = new XSSFWorkbook(in);

            XSSFSheet sheet =  excel.getSheet("sheet1");
                        
            //填充数据时间
            sheet.getRow(1).getCell(1).setCellValue("时间"+dateBegin+"至"+dateEnd);

            //获取第4行
            XSSFRow row =  sheet.getRow(3);
            row.getCell(2).setCellValue(businessDataVO.getTurnover());
            row.getCell(4).setCellValue(businessDataVO.getOrderCompletionRate());
            row.getCell(6).setCellValue(businessDataVO.getNewUsers());
            //;获取第5行
            row =  sheet.getRow(4);
            row.getCell(2).setCellValue(businessDataVO.getValidOrderCount());
            row.getCell(4).setCellValue(businessDataVO.getUnitPrice());
            //填充明细数据
            for(int i=0;i<30;i++){
                LocalDate date = dateBegin.plusDays(i);
                BusinessDataVO businessData = workspaceService.getBusinessData(
                    LocalDateTime.of(date, LocalTime.MIN),
                    LocalDateTime.of(date, LocalTime.MAX));
                //获取循环中的行
                row = sheet.getRow(7+i);
                row.getCell(1).setCellValue(date.toString());
                row.getCell(2).setCellValue(businessData.getTurnover());
                row.getCell(3).setCellValue(businessData.getOrderCompletionRate());
                row.getCell(4).setCellValue(businessData.getValidOrderCount());
                row.getCell(5).setCellValue(businessData.getUnitPrice());
                row.getCell(6).setCellValue(businessData.getNewUsers());
            }
            //3.通过http流下载excel到客户端
            ServletOutputStream out = httpServletResponse.getOutputStream();
            excel.write(out);
            //关闭资源
            out.close();
            excel.close();
        } catch (Exception e) {
            // TODO: handle exception
        }


    }
}
