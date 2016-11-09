package com.bluemobi.pro.pojo;

/**
 * 秀吧
 * @author yesong
 *
 */
public class PostBar {

	private Integer id;
	private String title;
	private String shortName;
	private String imageId;
	private String content;
	private Integer barManagerId;
	private String createDate;
	private String qrCode;
	private Integer index;
	
	
	private int isList; // 是否上架 -1:未上架 1:已上架
	private int permissions; // 是否有权限  0：无 1：有
	
	private PostImage pic;
	
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public int getPermissions() {
		return permissions;
	}
	public void setPermissions(int permissions) {
		this.permissions = permissions;
	}
	public int getIsList() {
		return isList;
	}
	public void setIsList(int isList) {
		this.isList = isList;
	}
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	public PostImage getPic() {
		return pic;
	}
	public void setPic(PostImage pic) {
		this.pic = pic;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getBarManagerId() {
		return barManagerId;
	}
	public void setBarManagerId(Integer barManagerId) {
		this.barManagerId = barManagerId;
	}
	
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getQrCode() {
		return qrCode;
	}
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
	
	
}
