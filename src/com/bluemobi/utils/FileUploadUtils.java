package com.bluemobi.utils;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.bluemobi.pro.controller.UsersController;

/**
 * 文件上传工具类
 * 
 * @author superman
 * 
 */
public class FileUploadUtils {

    /**
     * 
     * @param file
     *            ：需要上传的文件
     * @return String:文件上传返回的文件路径
     * @throws IOException
     */
    public static String uploadMovie(MultipartFile file) throws IOException {
        if (file != null && file.getOriginalFilename().toLowerCase().trim().length() != 0) {
            Random rand = new Random();
            String videoname = file.getOriginalFilename().toLowerCase();
            String filetype = videoname.substring(videoname.indexOf("."));// 获取后缀名
            String path = "upload/vedio/" + System.currentTimeMillis() + "_"
                    + rand.nextInt(1000000) + filetype;
            // String path = "upload/vedio/" + System.currentTimeMillis()+"_" +
            // videoname;
            // 获取项目在磁盘上面的物理路径
            File image = new File(UsersController.class
                    .getResource("/")
                    .getPath()
                    .substring(0,
                            UsersController.class.getResource("/").getPath().indexOf("WEB-INF"))
                    + path);
            File dir = image.getParentFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }
            // 写入文件
            FileCopyUtils.copy(file.getBytes(), image);
            return PathUtils.getRemotePath() + "/" + path;
        }
        else {
            return null;
        }
    }

    /**
     * 删除文件
     * 
     * @author Jason
     * @param String
     *            filePath 文件磁盘路径
     * @return
     * 
     */
    public static void removeFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists())
                file.delete();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
