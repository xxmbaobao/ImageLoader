package com.fcar.android.result;

/**
 * 好友数据的实�?
 * 
 * @author rendongwei
 * 
 */
public class FriendsResult {
	/**
	 * 好友的编�?
	 */
	private String uid;
	/**
	 * 好友的头像编�?
	 */
	private int avatar;
	/**
	 * 好友的姓�?
	 */
	private String name;
	/**
	 * 好友的姓名拼�?
	 */
	private String name_pinyin;
	/**
	 * 好友姓名的首字母
	 */
	private String name_first;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public int getAvatar() {
		return avatar;
	}

	public void setAvatar(int avatar) {
		this.avatar = avatar;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName_pinyin() {
		return name_pinyin;
	}

	public void setName_pinyin(String name_pinyin) {
		this.name_pinyin = name_pinyin;
	}

	public String getName_first() {
		return name_first;
	}

	public void setName_first(String name_first) {
		this.name_first = name_first;
	}
}
