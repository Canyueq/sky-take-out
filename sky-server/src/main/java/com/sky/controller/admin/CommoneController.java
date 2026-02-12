package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;

import java.util.UUID;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.formula.constant.ErrorConstant;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("admin/common")
@Api(tags="通用接口")
public class CommoneController {

    private final AliOssUtil aliOssUtil;
    public CommoneController(AliOssUtil aliOssUtil){
        this.aliOssUtil = aliOssUtil;
    }

    /**
     * 上传网址到阿里云
     * @params file
     * @return  
     */
    @PostMapping("/upload")
    @ApiOperation("将图片地址上传到阿里云OSS")
    public Result<String> upload(MultipartFile file) {
        try {
            //原始文件名称
            String originName = file.getOriginalFilename();
            //获取后缀
            String extension = originName.substring(originName.lastIndexOf("."));
            //构造新的文件名
            String objectName = UUID.randomUUID().toString() + extension;
            //上传文件到阿里云
            String filePath = aliOssUtil.upload(file.getBytes(),objectName);
            return Result.success(filePath);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
    
}
