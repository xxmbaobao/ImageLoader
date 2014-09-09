package com.fcar.fcardemo;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import cn.fcar.app.config.KXActivity;

import com.fcar.android.menu.CropImage;
import com.fcar.android.menu.CropImageView;
import com.fcar.android.utils.PhotoUtil;

/**
 * ͼƬ������
 *
 * @author rendongwei
 *
 */
public class ImageFilterCropActivity extends KXActivity {
	private Button mCancel;
	private Button mDetermine;
	private CropImageView mDisplay;
	private ProgressBar mProgressBar;
	private Button mLeft;
	private Button mRight;

	public static final int SHOW_PROGRESS = 0;
	public static final int REMOVE_PROGRESS = 1;

	private String mPath;// �޸ĵ�ͼƬ��ַ
	private Bitmap mBitmap;// �޸ĵ�ͼƬ
	private CropImage mCropImage; // �ü�������

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imagefilter_crop_activity);
		findViewById();
		setListener();
		init();
	}

	private void findViewById() {
		mCancel = (Button) findViewById(R.id.imagefilter_crop_cancel);
		mDetermine = (Button) findViewById(R.id.imagefilter_crop_determine);
		mDisplay = (CropImageView) findViewById(R.id.imagefilter_crop_display);
		mProgressBar = (ProgressBar) findViewById(R.id.imagefilter_crop_progressbar);
		mLeft = (Button) findViewById(R.id.imagefilter_crop_left);
		mRight = (Button) findViewById(R.id.imagefilter_crop_right);
	}

	private void setListener() {
		mCancel.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// ��ʾ���ضԻ���
				backDialog();
			}
		});
		mDetermine.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// �����޸ĵ�ͼƬ������,������ͼƬ��ַ
				mPath = PhotoUtil.saveToLocal(mCropImage.cropAndSave());
				Intent intent = new Intent();
				intent.putExtra("path", mPath);
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		mLeft.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// ����ת
				mCropImage.startRotate(270.f);
			}
		});
		mRight.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// ����ת
				mCropImage.startRotate(90.f);
			}
		});
	}

	private void init() {
		// ���մ��ݵ�ͼƬ��ַ
		mPath = getIntent().getStringExtra("path");
		try {
			// ��ȡ�޸ĵ�ͼƬ
			mBitmap = PhotoUtil
					.createBitmap(mPath, mScreenWidth, mScreenHeight);
			// ���ͼƬ������,�򷵻�,�������ʼ��
			if (mBitmap == null) {
				Toast.makeText(ImageFilterCropActivity.this, "û���ҵ�ͼƬ", 0)
						.show();
				setResult(RESULT_CANCELED);
				finish();
			} else {
				resetImageView(mBitmap);
			}
		} catch (Exception e) {
			Toast.makeText(ImageFilterCropActivity.this, "û���ҵ�ͼƬ", 0).show();
			setResult(RESULT_CANCELED);
			finish();
		}
	}

	/**
	 * ��ʼ��ͼƬ��ʾ
	 *
	 * @param b
	 */
	private void resetImageView(Bitmap b) {
		mDisplay.clear();
		mDisplay.setImageBitmap(b);
		mDisplay.setImageBitmapResetBase(b, true);
		mCropImage = new CropImage(this, mDisplay, handler);
		mCropImage.crop(b);
	}

	/**
	 * ���ƽ�����
	 */
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SHOW_PROGRESS:
				mProgressBar.setVisibility(View.VISIBLE);
				break;
			case REMOVE_PROGRESS:
				handler.removeMessages(SHOW_PROGRESS);
				mProgressBar.setVisibility(View.INVISIBLE);
				break;
			}
		}
	};

	/**
	 * ���ضԻ���
	 */
	private void backDialog() {
		AlertDialog.Builder builder = new Builder(ImageFilterCropActivity.this);
		builder.setTitle("����");
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.setMessage("��ȷ��Ҫȡ���༭��?");
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				setResult(RESULT_CANCELED);
				finish();
			}
		});
		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		builder.create().show();
	}

	public void onBackPressed() {
		backDialog();
	}
}
