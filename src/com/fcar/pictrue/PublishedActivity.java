package com.fcar.pictrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import cn.fcar.app.config.Base64;
import cn.fcar.app.config.Communication;
import cn.fcar.app.config.Image;

import com.fcar.android.utils.ImageUtils;
import com.fcar.fcardemo.DieselListActivity;
import com.fcar.fcardemo.GasolineListActivity;
import com.fcar.fcardemo.R;
import com.google.gson.Gson;

public class PublishedActivity extends Activity {

	private GridView noScrollgridview;
	private GridAdapter adapter;
	private TextView activity_selectimg_send,cars;
	private ProgressDialog Progress;
	private FrameLayout upload;


	private ArrayList<String> datas = new ArrayList<String>();
	private ArrayList<Image> upLoads = new ArrayList<Image>();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_post_select_normal_page);
		Init();
	}

	public void Init() {
		noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		upload=(FrameLayout) findViewById(R.id.photo_post_select_album_layout);
		upload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				PhotoDialog(PublishedActivity.this);

			}
		});
		adapter = new GridAdapter(this);
		adapter.update();
		noScrollgridview.setAdapter(adapter);
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == Bimp.bmp.size()) {
					new PopupWindows(PublishedActivity.this, noScrollgridview);
				} else {
					Intent intent = new Intent(PublishedActivity.this,
							PhotoActivity.class);
					intent.putExtra("ID", arg2);
					startActivity(intent);
				}
			}
		});
		activity_selectimg_send = (TextView) findViewById(R.id.activity_selectimg_send);
		activity_selectimg_send.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				sendRequset();
				List<String> list = new ArrayList<String>();
				for (int i = 0; i < Bimp.drr.size(); i++) {
					String Str = Bimp.drr.get(i).substring(
							Bimp.drr.get(i).lastIndexOf("/") + 1,
							Bimp.drr.get(i).lastIndexOf("."));
					list.add(FileUtils.SDPATH+Str+".JPEG");
				}
				// 楂樻竻鐨勫帇缂╁浘鐗囧叏閮ㄥ氨鍦�?list 璺緞閲岄潰浜�
				// 楂樻竻鐨勫帇缂╄繃鐨�bmp 瀵硅�? 閮藉�?Bimp.bmp閲岄�?
				// 瀹屾垚涓婁紶鏈嶅姟鍣ㄥ悗 .........
				FileUtils.deleteDir();
			}
		});
		cars=(TextView) findViewById(R.id.cars);
	}
	/**
	 * 照片对话框
	 */
	@SuppressWarnings("unused")
	private void PhotoDialog(Context context) {
		AlertDialog.Builder builder = new Builder(this);
		final Intent intents=new Intent();
		builder.setTitle("上传照片至爱夫卡");
		builder.setItems(new String[] { "柴油车系", "汽油车系" },
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						Intent intent = null;
						switch (which) {
						case 0:
							intents.setClass(PublishedActivity.this, DieselListActivity.class);
							startActivity(intents);

							break;

						case 1:
							intents.setClass(PublishedActivity.this,GasolineListActivity.class);
							startActivity(intents);
							break;
						}
					}
				});
		builder.create().show();
	}

	/**
	 *
	 *  函数名称 : sendRequset
	 *  功能描述 :
	 *  参数及返回值说明：
	 *
	 *  修改记录：
	 *  	日期：2013-7-23 上午10:59:54	修改人：adminstrator
	 *  	描述	：发送上传请求
	 *
	 */
	private void sendRequset() {
		downLoadTip();
		new Thread(){
			/**
			 *  函数名称 : run
			 *  功能描述 :
			 *  参数说明：
			 *  返回值：
			 *
			 *  修改记录：
			 *  日期：2013-7-23 上午11:00:23	修改人：adminstrator
			 *  描述	：
			 *
			 */
			@Override
			public void run() {
				super.run();

				if (datas.size() > 0) {
					String nameSpace = "http://baidu.com/";
					String methodName = "isSaveSuccess";

					ArrayList<Object> parameters = new ArrayList<Object>();

					upLoads.clear();

					for (int i = 0; i < datas.size(); i++) {
						try {
							Image image = new Image();
							image.setPath(Base64.encodeFromFile(datas.get(i).toString()));
							upLoads.add(image);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

					if (upLoads.size() > 0) {
						Gson gson = new Gson();
						String send = gson.toJson(upLoads);
						parameters.add(send);
					}

					String WSDLurl = "http://192.168.0.83:8080/ImageUpLoadService/UploadPort?wsdl";

					Object object = Communication.getInstance().connecting(nameSpace, methodName, parameters, WSDLurl);

					System.out.println(object);

					Message msg = new Message();
					msg.what = 2;
					msg.obj = object;

					handler.sendMessage(msg);
				}else {
					Message msg = new Message();
					msg.what = 1;
					msg.obj = "上传的图片为空";

					handler.sendMessage(msg);
				}
			}
		}.start();
	}
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			Progress.dismiss();

			if (msg.what == 1) {
				String str = (String) msg.obj;

				Toast.makeText(PublishedActivity.this, str, Toast.LENGTH_SHORT).show();
			}else if(msg.what == 2){

				if (msg.obj != null) {
					boolean isInsertSuccess = Boolean.parseBoolean(msg.obj.toString());

					if (isInsertSuccess) {
						Toast.makeText(PublishedActivity.this, "上传成功！", Toast.LENGTH_SHORT).show();
					}else {
						Toast.makeText(PublishedActivity.this, "上传失败！", Toast.LENGTH_SHORT).show();
					}
				}else {
					Toast.makeText(PublishedActivity.this, "返回值为空", Toast.LENGTH_SHORT).show();
				}
			}
		};
	};
	/**
    *
    *  函数名称 : downLoadTip
    *  功能描述 :
    *  参数及返回值说明：
    *
    *  修改记录：
    *  	日期：2013-7-24 下午03:02:12	修改人：adminstrator
    *  	描述	：下载提示
    *
    */
   private void downLoadTip() {
		//加载提示框
		Progress = new ProgressDialog(PublishedActivity.this);
		Progress.setMessage("正在努力的为你加载......");
		Progress.show();
	}

	@SuppressLint("HandlerLeak")
	public class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater; // 瑙嗗浘�?瑰櫒
		private int selectedPosition = -1;// 閫変腑鐨勪綅缃�
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void update() {
			loading();
		}

		public int getCount() {
			return (Bimp.bmp.size() + 1);
		}

		public Object getItem(int arg0) {

			return null;
		}

		public long getItemId(int arg0) {

			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		/**
		 * ListView Item璁剧�?
		 */
		public View getView(int position, View convertView, ViewGroup parent) {
			final int coord = position;
			ViewHolder holder = null;
			if (convertView == null) {

				convertView = inflater.inflate(R.layout.item_published_grida,
						parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.item_grida_image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position == Bimp.bmp.size()) {
				holder.image.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.icon_addpic_unfocused));
				if (position == 30) {
//					holder.image.setVisibility(View.GONE);
				}
			} else {
				holder.image.setImageBitmap(Bimp.bmp.get(position));
			}

			return convertView;
		}

		public class ViewHolder {
			public ImageView image;
		}

		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					adapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};

		public void loading() {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						if (Bimp.max == Bimp.drr.size()) {
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;
						} else {
							try {
								String path = Bimp.drr.get(Bimp.max);
								System.out.println(path);
								Bitmap bm = Bimp.revitionImageSize(path);
								Bimp.bmp.add(bm);
								String newStr = path.substring(
										path.lastIndexOf("/") + 1,
										path.lastIndexOf("."));
								FileUtils.saveBitmap(bm, "" + newStr);
								Bimp.max += 1;
								Message message = new Message();
								message.what = 1;
								handler.sendMessage(message);
							} catch (IOException e) {

								e.printStackTrace();
							}
						}
					}
				}
			}).start();
		}
	}

	public String getString(String s) {
		String path = null;
		if (s == null)
			return "";
		for (int i = s.length() - 1; i > 0; i++) {
			s.charAt(i);
		}
		return path;
	}

	protected void onRestart() {
		adapter.update();
		super.onRestart();
	}

	public class PopupWindows extends PopupWindow {

		public PopupWindows(Context mContext, View parent) {

			View view = View
					.inflate(mContext, R.layout.item_popupwindows, null);
			view.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.fade_ins));
			LinearLayout ll_popup = (LinearLayout) view
					.findViewById(R.id.ll_popup);
			ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.push_bottom_in_2));

			setWidth(LayoutParams.FILL_PARENT);
			setHeight(LayoutParams.FILL_PARENT);
			setBackgroundDrawable(new BitmapDrawable());
			setFocusable(true);
			setOutsideTouchable(true);
			setContentView(view);
			showAtLocation(parent, Gravity.BOTTOM, 0, 0);
			update();

//			Button bt1 = (Button) view
//					.findViewById(R.id.item_popupwindows_camera);
			Button bt2 = (Button) view
					.findViewById(R.id.item_popupwindows_Photo);
			Button bt3 = (Button) view
					.findViewById(R.id.item_popupwindows_cancel);
//			bt1.setOnClickListener(new OnClickListener() {
//				public void onClick(View v) {
//					photo();
//					dismiss();
//				}
//			});
			bt2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(PublishedActivity.this,
							TestPicActivity.class);
					startActivity(intent);
					dismiss();
				}
			});
			bt3.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					dismiss();
				}
			});

		}
	}

	private static final int TAKE_PICTURE = 0x000000;
	private String path = "";

	public void photo() {
//		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//		File file = new File(Environment.getExternalStorageDirectory()
//				+ "/myimage/", String.valueOf(System.currentTimeMillis())
//				+ ".jpg");
//		path = file.getPath();
//		Uri imageUri = Uri.fromFile(file);
//		openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//		startActivityForResult(openCameraIntent, TAKE_PICTURE);
		ImageUtils.openCameraImage(this);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case TAKE_PICTURE:
			if (Bimp.drr.size() < 30 && resultCode == -1) {
				Bimp.drr.add(path);
			}
			break;
		}
	}

}
