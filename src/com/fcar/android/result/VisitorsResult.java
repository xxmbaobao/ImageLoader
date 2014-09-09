package com.fcar.android.result;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 访问者数据实�?
 * 
 * @author rendongwei
 * 
 */
public class VisitorsResult implements Parcelable {
	/**
	 * 访问者的编号
	 */
	private String uid;
	/**
	 * 访问者的姓名
	 */
	private String name;
	/**
	 * 访问者来访的时间
	 */
	private String time;
	/**
	 * 访问者头像编�?
	 */
	private int avatar;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

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

	public int getAvatar() {
		return avatar;
	}

	public void setAvatar(int avatar) {
		this.avatar = avatar;
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(uid);
		dest.writeString(name);
		dest.writeString(time);
		dest.writeInt(avatar);
	}

	public static final Parcelable.Creator<VisitorsResult> CREATOR = new Parcelable.Creator<VisitorsResult>() {

		public VisitorsResult createFromParcel(Parcel source) {
			VisitorsResult result = new VisitorsResult();
			result.setUid(source.readString());
			result.setName(source.readString());
			result.setTime(source.readString());
			result.setAvatar(source.readInt());
			return result;
		}

		public VisitorsResult[] newArray(int size) {
			return new VisitorsResult[size];
		}
	};

}
