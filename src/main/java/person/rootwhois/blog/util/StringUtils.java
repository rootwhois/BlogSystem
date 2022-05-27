package person.rootwhois.blog.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

import java.util.UUID;

/**
 * @Author: 陈广生
 * @Date: 2022/03/11/5:45 PM
 * @Description:
 */
public class StringUtils {
    /**
     * @Description: 生成唯一图片名称
     * @Param: fileName
     * @return: 云服务器fileName
     */
    public static String getRandomImgName(String fileName) {

        int index = fileName.lastIndexOf(".");

        if (StrUtil.isEmpty(fileName) || index == -1){
            throw new IllegalArgumentException();
        }
        // 获取文件后缀
        String suffix = fileName.substring(index);
        // 生成UUID
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        // 生成上传至云服务器的路径
        return "res/" + DateUtil.today() + "-" + uuid + suffix;
    }
}
