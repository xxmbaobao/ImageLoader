package com.fcar.android.result;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 相册数据的实�?
 * 
 * @author rendongwei
 * 
 */
public class PhotoResult implements Parcelable {
	/**
	 * 相册的标�?
	 */
	private String pid;
	/**
	 * 相册的封面图片的编号
	 */
	private int image;
	/**
	 * 相册的标�?
	 */
	private String title;
	/**
	 * 相册中照片的数量
	 */
	private int count;
	/**
	 * 相册创建时间
	 */
	private String time;
	/**
	 * 相册的类�?0-头像相册,1-照片相册)
	 */
	private int type;
	/**
	 * 相册中照片的具体内容
	 */
	private List<PhotoDetailResult> images;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<PhotoDetailResult> getImages() {
		return images;
	}

	public void setImages(List<PhotoDetailResult> images) {
		this.images = images;
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(pid);
		dest.writeString(title);
		dest.writeString(time);
		dest.writeInt(image);
		dest.writeInt(count);
		dest.writeInt(type);
		dest.writeList(images);
	}

	public static final Parcelable.Creator<PhotoResult> CREATOR = new Creator<PhotoResult>() {

		public PhotoResult[] newArray(int size) {
			return new PhotoResult[size];
		}

		@SuppressWarnings("unchecked")
		public PhotoResult createFromParcel(Parcel source) {
			PhotoResult result = new PhotoResult();
			result.setPid(source.readString());
			result.setTitle(source.readString());
			result.setTime(source.readString());
			result.setImage(source.readInt());
			result.setCount(source.readInt());
			result.setType(source.readInt());
			result.setImages(source.readArrayList(PhotoResult.class
					.getClassLoader()));
			return result;
		}
	};
}
