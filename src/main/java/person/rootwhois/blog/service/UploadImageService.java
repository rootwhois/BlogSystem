package person.rootwhois.blog.service;

import person.rootwhois.blog.config.CloudStorageConfig;

import java.io.FileInputStream;

/**
 * @Author: 陈广生
 * @Date: 2022/03/11/5:49 PM
 * @Description:
 */
public abstract class UploadImageService {

    protected CloudStorageConfig config;
    public abstract String uploadImg(FileInputStream file, String path);
}
