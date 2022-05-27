package person.rootwhois.blog.controller;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import person.rootwhois.blog.common.result.ApiErrorCode;
import person.rootwhois.blog.common.result.Result;
import person.rootwhois.blog.service.UploadImageService;
import person.rootwhois.blog.util.StringUtils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Objects;

/**
 * @Author: 陈广生
 * @Date: 2022/03/10/9:31 PM
 * @Description:
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/upload")
public class UploadController {

    @Autowired
    UploadImageService uploadImageService;

    /**
     *上传图片
     */
    @SneakyThrows
    @RequiresAuthentication
    @PostMapping("/uploadPic")
    public Result<String> uploadPic(MultipartHttpServletRequest multiRequest){
        MultipartFile file = Objects.requireNonNull(multiRequest.getFile("image"));
        InputStream stream = file.getInputStream();
        String type = FileTypeUtil.getType(stream);

        if (!StrUtil.isEmpty(type)) {

            // 获取文件的名称
            String fileName = file.getOriginalFilename();

            // 使用工具类根据上传文件生成唯一图片名称
            assert fileName != null;
            String imgName = StringUtils.getRandomImgName(fileName);

            if (!file.isEmpty()) {
                FileInputStream inputStream = (FileInputStream) file.getInputStream();

                String path = uploadImageService.uploadImg(inputStream, imgName);
                return Result.ok(path);
            }
        }

        return Result.failed(null, ApiErrorCode.FAILED.getCode(), "上传失败");
    }

}
