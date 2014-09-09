package com.fcar.android.result;

import java.util.List;
import java.util.Map;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 照片内容数据的实�?
 * 
 * @author rendongwei
 * 
 */
public class PhotoDetailResult implements Parcelable {
	/**
	 * 照片的编�?
	 */
	private int image;
	/**
	 * 照片的上传时�?
	 */
	private String time;
	/**
	 * 照片的描�?
	 */
	private String description;
	/**
	 * 照片的评论数�?
	 */
	private int comment_count;
	/**
	 * 照片的赞数量
	 */
	private int like_count;
	/**
	 * 照片的评�?
	 */
	private List<Map<String, Object>> comments;

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
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

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(image);
		dest.writeInt(comment_count);
		dest.writeInt(like_count);
		dest.writeString(time);
		dest.writeString(description);
		dest.writeList(comments);
	}

	public static final Parcelable.Creator<PhotoDetailResult> CREATOR = new Parcelable.Creator<PhotoDetailResult>() {

		@SuppressWarnings("unchecked")
		public PhotoDetailResult createFromParcel(Parcel source) {
			PhotoDetailResult result = new PhotoDetailResult();
			result.setImage(source.readInt());
			result.setComment_count(source.readInt());
			result.setLike_count(source.readInt());
			result.setTime(source.readString());
			result.setDescription(source.readString());
			result.setComments(source.readArrayList(PhotoDetailResult.class
					.getClassLoader()));
			return result;
		}

		public PhotoDetailResult[] newArray(int size) {
			return new PhotoDetailResult[size];
		}
	};

}
