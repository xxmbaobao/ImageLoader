package com.fcar.android.result;

import java.util.List;
import java.util.Map;

/**
 * 附近的照片数据内容实体类
 * 
 * @author rendongwei
 * 
 */
public class NearbyPhotoDetailResult {
	/**
	 * 照片内容
	 */
	private String image;
	/**
	 * 照片�?��者ID
	 */
	private String owners_uid;
	/**
	 * 照片�?��者姓�?
	 */
	private String owners_name;
	/**
	 * 照片�?��的头�?
	 */
	private int owners_avatar;
	/**
	 * 照片上传时间
	 */
	private String time;
	/**
	 * 照片描述
	 */
	private String description;
	/**
	 * 照片评论数量
	 */
	private int comment_count;
	/**
	 * 照片赞数�?
	 */
	private int like_count;
	/**
	 * 照片评论内容
	 */
	private List<Map<String, Object>> comments;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getOwners_uid() {
		return owners_uid;
	}

	public void setOwners_uid(String owners_uid) {
		this.owners_uid = owners_uid;
	}

	public String getOwners_name() {
		return owners_name;
	}

	public void setOwners_name(String owners_name) {
		this.owners_name = owners_name;
	}

	public int getOwners_avatar() {
		return owners_avatar;
	}

	public void setOwners_avatar(int owners_avatar) {
		this.owners_avatar = owners_avatar;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getComment_count() {
		return comment_count;
	}

	public void setComment_count(int comment_count) {
		this.comment_count = comment_count;
	}

	public int getLike_count() {
		return like_count;
	}

	public void setLike_count(int like_count) {
		this.like_count = like_count;
	}

	public List<Map<String, Object>> getComments() {
		return comments;
	}

	public void setComments(List<Map<String, Object>> comments) {
		this.comments = comments;
	}
}
