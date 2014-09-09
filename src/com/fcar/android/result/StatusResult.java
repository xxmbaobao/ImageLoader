package com.fcar.android.result;

/**
 * 状�?数据的实�?
 * 
 * @author rendongwei
 * 
 */
public class StatusResult {
	/**
	 * 状�?的所有�?姓名
	 */
	private String name;
	/**
	 * 状�?的时�?
	 */
	private String time;
	/**
	 * 状�?的内�?
	 */
	private String content;
	/**
	 * 状�?的来�?包括网页,Android客户�?
	 */
	private String from;
	/**
	 * 状�?评论的数�?
	 */
	private int comment_count;
	/**
	 * 状�?转发的数�?
	 */
	private int forward_count;
	/**
	 * 状�?赞的数量
	 */
	private int like_count;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public int getComment_count() {
		return comment_count;
	}

	public void setComment_count(int comment_count) {
		this.comment_count = comment_count;
	}

	public int getForward_count() {
		return forward_count;
	}

	public void setForward_count(int forward_count) {
		this.forward_count = forward_count;
	}

	public int getLike_count() {
		return like_count;
	}

	public void setLike_count(int like_count) {
		this.like_count = like_count;
	}

}
