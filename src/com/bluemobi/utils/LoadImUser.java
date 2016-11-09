package com.bluemobi.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class LoadImUser {

	private static final String PREFIX = "easemob_";
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List loadFromTxt() {
		List list = new ArrayList();
		File file = new File("C:\\Users\\yes\\Desktop\\2.txt");
		String encoding = "UTF-8";
		String textLine = null;
		InputStreamReader reader = null;
		BufferedReader br = null;
		try {
			reader = new InputStreamReader(new FileInputStream(file),encoding);
			br = new BufferedReader(reader);
			while((textLine = br.readLine()) != null) {
				textLine = textLine.trim().substring(1, textLine.length() -2);
//				String[] user = analysis(textLine);
//				String[] user = analysisFriend(textLine);
				String[] user = analysisGroupUpdate(textLine);
				list.add(user);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	public String[] analysis(String textLine) {
		String[] strs = textLine.split(",");
//		//用户名
//		String username = PREFIX + remove(strs[0]) ;
//		//密码
//		String password = remove(strs[3]);
//		//昵称
//		String nickName = remove(strs[1]);
		String groupId = remove(strs[0]);
		String groupName = remove(strs[1]);
		String desc = remove(strs[10]);
		String nums = getNum(strs[2]);
		String createdId = PREFIX + remove(strs[7]);
		
		System.out.println("groupId:" + groupId + "==groupName:" + groupName + "==desc:" + desc + "==nums:" + nums + "==createdId:" + createdId);
		return new String[]{groupId,groupName,desc,nums,createdId};
	}
	
	public String[] analysisGroupUpdate(String textLine) {
		String[] strs = textLine.split(",");
		String groupId = remove(strs[0]);
		String desc = remove(strs[10]);
//		System.out.println("groupId:" + groupId + "==desc:" + desc);
		return new String[]{groupId,desc};
	}
	/**
	 * 
	 * @param textLine
	 * @return
	 */
	public String[] analysisGroup(String textLine) {
		String[] strs = textLine.split(",");
		String groupId = remove(strs[0]);
		String userId = PREFIX + remove(strs[1]);
		
		System.out.println("groupId:" + groupId + "==userId:" + userId );
		return new String[]{groupId,userId};
	}

	/**
	 * 
	 * @param textLine
	 * @return
	 */
	public String[] analysisFriend(String textLine) {
		String[] strs = textLine.split(",");
		String userId =PREFIX + remove(strs[0]);
		String friend = PREFIX + remove(strs[1]);
		
		System.out.println("userId:" + userId + "==frinedId:" + friend );
		return new String[]{userId,friend};
	}
	
	private String getNum(String string) {
		String num = null;
		switch (Integer.parseInt(remove(string))) {
		case 1:
			num = 100 + "";
			break;
		case 2:
			num = 200 + "";
			break;
		case 3:
			num = 300 + "";
			break;
		case 4:
			num = 400 + "";
			break;
		case 5:
			num = 500 + "";
			break;
		default:
			break;
		}
		return num;
	}

	private String remove(String str) {
		return str.replaceAll("'","");
	}

	public static void main(String[] args) {
		LoadImUser l = new LoadImUser();
		l.loadFromTxt();
	}
}
