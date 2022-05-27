package person.rootwhois.blog.service.impl;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.stereotype.Service;
import person.rootwhois.blog.config.CloudStorageConfig;
import person.rootwhois.blog.service.UploadImageService;

import java.io.FileInputStream;

/**
 * @Author: 陈广生
 * @Date: 2022/03/11/5:49 PM
 * @Description:
 */
@Service
public class UploadImageServiceImpl extends UploadImageService {

    /**
     * 七牛文件上传管理器
     */
    private UploadManager uploadManager;

    private String token;

    public UploadImageServiceImpl(CloudStorageConfig config){
        this.config = config;
        init();
    }

    private void init(){
        uploadManager = new UploadManager(new Configuration());
        // 七牛认证管理
        Auth auth = Auth.create(config.getQiniuAccessKey(), config.getQiniuSecretKey());
        // 根据命名空间生成的上传token
        token = auth.uploadToken(config.getQiniuBucketName());
    }

    @Override
    public String uploadImg(FileInputStream file, String key) {
        try{
            // 上传图片文件
            Response res = uploadManager.put(file, key, token, null, null);
            if (!res.isOK()) {
                throw new RuntimeException("上传七牛出错：" + res);
            }
            // 解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(res.bodyString(), DefaultPutRet.class);

            // 这个returnPath是获得到的外链地址,通过这个地址可以直接打开图片
            return config.getQiniuDomain() + "/" + putRet.key;
        }catch (QiniuException e){
            e.printStackTrace();
        }
        return "";
    }
}
