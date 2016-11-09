package com.bluemobi.utils;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.bluemobi.pro.controller.UsersController;

public class ImageUtils {

    private static String imagePath = "upload/temp/";
    private static String imageSuoluePath = "upload/suolue/";

    /**
     * BASE64加密图片字节
     * 
     * @param filename
     * @return 加密后的字符串
     */
    public static String getBase64Pic(String filename) {
        String content = null;
        try {
            InputStream inputStream = new FileInputStream(filename);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            content = new sun.misc.BASE64Encoder().encode(bytes); // 具体的编码方法
            inputStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    private static BufferedImage getResizedImage(BufferedImage source, int targetW, int targetH) {
        int type = source.getType();
        BufferedImage target = null;
        // targetW，targetH分别表示目标长和宽
        double sx = (double) targetW / source.getWidth();
        double sy = (double) targetH / source.getHeight();
        // 这里想实现在targetW，targetH范围内实现等比缩放。如果不需要等比缩放
        // 则将下面的if else语句注释即可
        if (sx > sy) {
            sx = sy;
            targetW = (int) (sx * source.getWidth());
        }
        else {
            sy = sx;
            targetH = (int) (sy * source.getHeight());
        }
        if (type == BufferedImage.TYPE_CUSTOM) { // handmade
            ColorModel cm = source.getColorModel();
            WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();
            target = new BufferedImage(cm, raster, alphaPremultiplied, null);
        }
        else {
            target = new BufferedImage(targetW, targetH, type);
        }
        Graphics2D g = target.createGraphics();
        // smoother than exlax:
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
        g.dispose();
        return target;
    }

    /**
     * 等比缩放图片
     * 
     * @param image
     * @param width
     * @param height
     * @return
     */
    public static BufferedImage resize(File file, int width, int height) {
        try {
            BufferedImage srcImage = ImageIO.read(file);
            if (width > 0 || height > 0) {
                srcImage = getResizedImage(srcImage, width, height);
            }
            return srcImage;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 图片上传 并保存缩略图
     * 
     * @author Jason
     * @param MultipartFile
     *            imageFile, boolean needSmallImage 是否需要生成缩略图
     * @return String[] paths ; paths[0]原图存储路径, paths[1]缩略图存储路径 为空表示不需要缩略图
     * @see 1.保存到服务器的图片名 = 当前毫秒数_原图片名; 2.如果imageFile为空，不会保存任何信息，同时返回空数组{ "", ""
     *      }
     * 
     */
    public static String[] saveImage(MultipartFile imageFile, boolean needSmallImage)
            throws Exception {
        Random rand = new Random();
        String[] paths = new String[2];
        if (null != imageFile && imageFile.getOriginalFilename().toLowerCase().trim().length() != 0) {
            String originalFileName = imageFile.getOriginalFilename().toLowerCase();
            String filetype = originalFileName.substring(originalFileName.indexOf("."));
            String timeMillis = String.valueOf(System.currentTimeMillis());
            String path = imagePath + timeMillis + "_" + rand.nextInt(1000000) + filetype;
            String webpath = (UsersController.class.getResource("/").getPath().substring(0,
                    UsersController.class.getResource("/").getPath().indexOf("WEB-INF")));
            // 获取项目在磁盘上面的物理路径
            File image = new File(webpath + path);
            File dir = image.getParentFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }
            FileCopyUtils.copy(imageFile.getBytes(), image);
            paths[0] = PathUtils.getRemotePath() + "/" + path;
            if (needSmallImage) {
                String suoluepath = imageSuoluePath + timeMillis + "_" + rand.nextInt(1000000)
                        + originalFileName;
                try {
                    FileCopyUtils.copy(imageFile.getBytes(), image);
                    BufferedImage bi = ImageUtils.resize(image, 500, 500);// 根据上传的图片文件生成对应的缩略图
                    File smallfile = new File(new File(webpath) + "/" + suoluepath);
                    if (!smallfile.getParentFile().exists()) {// 如果文件夹不存在
                        smallfile.getParentFile().mkdirs();// 创建上传文件夹
                    }
                    ImageIO.write(bi, "jpg", smallfile);// 将缩略图写入服务器端的制定文件夹中
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                paths[1] = PathUtils.getRemotePath() + "/" + suoluepath;
            }
            else {
                paths[1] = "";
            }
        }
        return paths;
    }

    /**
     * 删除图片
     * 
     * @author Jason
     * @param String
     *            filePath 文件数据库路径
     * @return
     * 
     */
    public static void removeImage(String filePath) throws Exception {
        if (null == filePath || "".equals(filePath.trim()))
            return;
        String webpath = (UsersController.class.getResource("/").getPath().substring(0,
                UsersController.class.getResource("/").getPath().indexOf("WEB-INF")));
        String tempString = "";
        // 大图
        if (0 < filePath.indexOf(imagePath))
            tempString = filePath.substring(filePath.indexOf(imagePath));
        else if (0 < filePath.indexOf(imageSuoluePath))
            tempString = filePath.substring(filePath.indexOf(imagePath));
        String serverFilePath = webpath + tempString;

        FileUploadUtils.removeFile(serverFilePath);
    }

	public static int[] getSize(String path) throws IOException { 
		int [] size = new int[2];
		if(StringUtils.isNotBlank(path)){
			int _index = com.bluemobi.utils.StringUtils.findIndexFromPos(path, "/", 4);
			String _newPath = path.substring(_index+1);
			String webpath = (UsersController.class.getResource("/").getPath().substring(0,
	                 UsersController.class.getResource("/").getPath().indexOf("WEB-INF")));
			File file = new File(webpath + _newPath);
			BufferedImage image = ImageIO.read(file);
			if(image != null){
				size[0] = image.getWidth();
				size[1] = image.getHeight();
			}
		}
		return size;
	}
    
    public static void main(String[] args) throws IOException {

        /**
         * String imagePath = "E:\\test.png"; BufferedImage image =
         * getBufferedImage(imagePath, 100, 100); ImageIO.write(image, "png",
         * new File("e:/"+System.currentTimeMillis()+".png"));
         */
        String filePath = "http://localhost:8080/luolai/upload/temp/1410850956162_28.jpg";
        if (0 < filePath.indexOf(imagePath)) {
            System.out.println(filePath.indexOf(imagePath));
            String tempString = filePath.substring(filePath.indexOf(imagePath));
            System.out.println(tempString);
        }
    }
}
