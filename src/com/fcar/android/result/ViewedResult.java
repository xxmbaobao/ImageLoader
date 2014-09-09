package com.fcar.android.result;

/**
 * 转帖数据实体�?
 * 
 * @author rendongwei
 * 
 */
public class ViewedResult {
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 时间
	 */
	private String time;
	/**
	 * �?��者姓�?
	 */
	private String owners_name;
	/**
	 * �?��者ID
	 */
	private String owners_uid;
	/**
	 * �?��者头�?
	 */
	private int owners_avatar;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 图片
	 */
	private int image;
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 转帖数量
	 */
	private String forwarding_count;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getOwners_name() {
		return owners_name;
	}

	public void setOwners_name(String owners_name) {
		this.owners_name = owners_name;
	}

	public String getOwners_uid() {
		return owners_uid;
	}

	public void setOwners_uid(String owners_uid) {
		this.owners_uid = owners_uid;
	}

	public int getOwners_avatar() {
		return owners_avatar;
	}

	public void setOwners_avatar(int owners_avatar) {
		this.owners_avatar = owners_avatar;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getForwarding_count() {
		return forwarding_count;
	}

	public void setForwarding_count(String forwarding_count) {
		this.forwarding_count = forwarding_count;
	}

}
