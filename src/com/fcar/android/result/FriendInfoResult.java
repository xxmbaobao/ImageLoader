package com.fcar.android.result;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 好友资料数据的数据实�?
 * 
 * @author rendongwei
 * 
 */
public class FriendInfoResult implements Parcelable {
	/**
	 * 好友的姓�?
	 */
	private String name;
	/**
	 * 好友的头像编�?
	 */
	private int avatar;
	/**
	 * 好友的�?�?
	 */
	private int gender;
	/**
	 * 好友的星�?
	 */
	private String constellation;
	/**
	 * 好友的签�?
	 */
	private String signature;
	/**
	 * 好友的照片个�?
	 */
	private int photo_count;
	/**
	 * 好友的日记个�?
	 */
	private int diary_count;
	/**
	 * 好友的好友个�?
	 */
	private int friend_count;
	/**
	 * 好友的访问�?个数
	 */
	private int visitor_count;
	/**
	 * 好友的背景编�?
	 */
	private int wallpager;
	/**
	 * 好友的生�?
	 */
	private String date;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAvatar() {
		return avatar;
	}

	public void setAvatar(int avatar) {
		this.avatar = avatar;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getConstellation() {
		return constellation;
	}

	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public int getPhoto_count() {
		return photo_count;
	}

	public void setPhoto_count(int photo_count) {
		this.photo_count = photo_count;
	}

	public int getDiary_count() {
		return diary_count;
	}

	public void setDiary_count(int diary_count) {
		this.diary_count = diary_count;
	}

	public int getFriend_count() {
		return friend_count;
	}

	public void setFriend_count(int friend_count) {
		this.friend_count = friend_count;
	}

	public int getVisitor_count() {
		return visitor_count;
	}

	public void setVisitor_count(int visitor_count) {
		this.visitor_count = visitor_count;
	}

	public int getWallpager() {
		return wallpager;
	}

	public void setWallpager(int wallpager) {
		this.wallpager = wallpager;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(constellation);
		dest.writeString(signature);
		dest.writeString(date);
		dest.writeInt(avatar);
		dest.writeInt(gender);
		dest.writeInt(photo_count);
		dest.writeInt(diary_count);
		dest.writeInt(friend_count);
		dest.writeInt(visitor_count);
		dest.writeInt(wallpager);
	}

	public static final Parcelable.Creator<FriendInfoResult> CREATOR = new Parcelable.Creator<FriendInfoResult>() {

		public FriendInfoResult createFromParcel(Parcel source) {
			FriendInfoResult result = new FriendInfoResult();
			result.setName(source.readString());
			result.setConstellation(source.readString());
			result.setSignature(source.readString());
			result.setDate(source.readString());
			result.setAvatar(source.readInt());
			result.setGender(source.readInt());
			result.setPhoto_count(source.readInt());
			result.setDiary_count(source.readInt());
			result.setFriend_count(source.readInt());
			result.setVisitor_count(source.readInt());
			result.setWallpager(source.readInt());
			return result;
		}

		public FriendInfoResult[] newArray(int size) {
			return new FriendInfoResult[size];
		}
	};

}
