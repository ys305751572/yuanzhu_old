package com.bluemobi.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

import com.bluemobi.constant.Constant;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class QrCodeUtil {
	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;

	public static final String JPG = "jpg";
	public static final String GIF = "gif";
	public static final String PNG = "png";
	
	public static final int WIDTH = 300;
	public static final int HEIGHT = 300;
	
	private static String imagePath = "/upload/qrcode/";
	
	private QrCodeUtil() {
	}

	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}

	public static void writeToFile(BitMatrix matrix, String format, File file)
			throws IOException, WriterException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, file)) {
			throw new IOException("Could not write an image of format "
					+ format + " to " + file);
		}
	}

	/**
	 * 生成二维码
	 * @return
	 * @throws WriterException
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String generateQrCode(int type,String id,String table,Boolean gift,int coin) throws WriterException, IOException{
		Map<String,Object> qrcode = new HashMap<String,Object>();
		qrcode.put("type", String.valueOf(type));
		qrcode.put("id", id);
		if(gift){
			qrcode.put("coin", coin);
		}
		String text = JsonUtils.returnObject(qrcode);
        //二维码的图片格式 
        Hashtable hints = new Hashtable(); 
        //内容所使用编码 
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); 
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text,
                BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints); 
        
        String path = imagePath + System.currentTimeMillis() + "_" + new Random().nextInt(1000000) + "." + JPG;
        String webpath = QrCodeUtil.class.getResource("/").getPath()
        				.substring(0,QrCodeUtil.class.getResource("/").getPath().indexOf("WEB-INF") - 1);
        //生成二维码 
        File outputFile = new File(webpath + path); 
        File dir = outputFile.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        writeToFile(bitMatrix, GIF, outputFile); 
        String returnPath = PathUtils.getRemotePath() + File.separator + path;
        saveToDB(table,returnPath,id);
        return null;
	}
	
	// 更新二维码
	private static void saveToDB(String table, String returnPath,String id) {
		String driverName = PropertiesUtils.getPropertiesValues("jdbc.driver",Constant.JDBC_PROP);
		String url = PropertiesUtils.getPropertiesValues("jdbc.url",Constant.JDBC_PROP);
		String username = PropertiesUtils.getPropertiesValues("jdbc.username", Constant.JDBC_PROP);
		String password = PropertiesUtils.getPropertiesValues("jdbc.password", Constant.JDBC_PROP);
		
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = LocalDriverManager.getConnection(driverName, url, username, password);
			String sql = "UPDATE " + table + " SET qrCode = '"+ returnPath + "' WHERE id = " + id;
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			LocalDriverManager.closeConnection(conn, ps);
		}
	}

	public static void writeToStream(BitMatrix matrix, String format,
			OutputStream stream) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, stream)) {
			throw new IOException("Could not write an image of format "
					+ format);
		}
	}
}
