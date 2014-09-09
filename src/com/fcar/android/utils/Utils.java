package com.fcar.android.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.format.DateUtils;

import com.fcar.fcardemo.R;

/**
 * 工具�?
 *
 * @author rendongwei
 *
 */
public class Utils {
	/**
	 * 根据性别数字获取到�?别图�?
	 *
	 * @param res
	 *            Resources对象
	 * @param gender
	 *            0代表女�?,1代表男�?
	 * @return 性别图片(Bitmap 类型)
	 */
	public static Bitmap getGender(Resources res, int gender) {
		switch (gender) {
		case 0:
			return BitmapFactory.decodeResource(res,
					R.drawable.profile_icon_girl);
		case 1:
			return BitmapFactory.decodeResource(res,
					R.drawable.profile_icon_boy);
		default:
			return BitmapFactory.decodeResource(res,
					R.drawable.profile_icon_boy);
		}
	}

	/**
	 * 根据性别数字获取到�?别名�?
	 *
	 * @param gender
	 *            0代表女�?,1代表男�?
	 * @return 性别名称(String 类型)
	 */
	public static String getGender(int gender) {
		switch (gender) {
		case 0:
			return "Ů";
		case 1:
			return "��";
		default:
			return "δ֪";
		}
	}

	/**
	 * 转换long型日期格�?
	 *
	 * @param context
	 * @param date
	 * @return
	 */
	public static String formatDate(Context context, long date) {
		int format_flags = DateUtils.FORMAT_NO_NOON_MIDNIGHT
				| DateUtils.FORMAT_ABBREV_ALL | DateUtils.FORMAT_CAP_AMPM
				| DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_DATE
				| DateUtils.FORMAT_SHOW_TIME;
		return DateUtils.formatDateTime(context, date, format_flags);
	}

	/**
	 * 转换long型日期格�?
	 *
	 * @param date
	 * @return
	 */
	public static String formatDate(long date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(new Date(date));
	}

	/**
	 * 获取当前的时�?
	 *
	 * @param context
	 * @return
	 */
	public static String getTime(Context context) {
		return formatDate(context, System.currentTimeMillis());
	}

	/**
	 * 获取当前的时�?
	 *
	 * @return
	 */
	public static String getTime() {
		return formatDate(System.currentTimeMillis());
	}
}
