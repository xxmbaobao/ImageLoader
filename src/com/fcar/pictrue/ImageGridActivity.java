package com.fcar.pictrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.fcar.fcardemo.R;
import com.fcar.pictrue.ImageGridAdapter.TextCallback;

public class ImageGridActivity extends Activity {
	public static final String EXTRA_IMAGE_LIST = "imagelist";

	List<ImageItem> dataList;
	GridView gridView;
	ImageGridAdapter adapter;// 鑷畾涔夌殑閫傞厤鍣�
	AlbumHelper helper;
	Button bt;

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(ImageGridActivity.this, "最多选择30张图片", 400).show();
				break;

			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_image_grid);

		helper = AlbumHelper.getHelper();
		helper.init(getApplicationContext());

		dataList = (List<ImageItem>) getIntent().getSerializableExtra(
				EXTRA_IMAGE_LIST);

		initView();
		bt = (Button) findViewById(R.id.bt);
		bt.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				ArrayList<String> list = new ArrayList<String>();
				Collection<String> c = adapter.map.values();
				Iterator<String> it = c.iterator();
				for (; it.hasNext();) {
					list.add(it.next());
				}

				if (Bimp.act_bool) {
					Intent intent = new Intent(ImageGridActivity.this,
							PublishedActivity.class);
					startActivity(intent);
					Bimp.act_bool = false;
				}

				for (int i = 0; i < list.size(); i++) {
					if (Bimp.drr.size() <dataList.size()) {
						Bimp.drr.add(list.get(i));
					}
				}
				finish();
			}

		});
	}

	/**
	 * 閸掓繂顬婇崠鏉ew鐟欏棗娴�?	 */
	private void initView() {
		gridView = (GridView) findViewById(R.id.gridview);
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new ImageGridAdapter(ImageGridActivity.this, dataList,
				mHandler);
		gridView.setAdapter(adapter);
		adapter.setTextCallback(new TextCallback() {
			public void onListen(int count) {
				//閫夋嫨浜嗗嚑寮犲浘鐗�?
				bt.setText("完成" + "(" + count + ")");
			}
		});

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				/**
				 * 閺嶈宓乸osition閸欏倹鏆熼敍灞藉讲娴犮儴骞忓妤勭�?GridView閻ㄥ嫬鐡橵iew閻╁摜绮︾�姘辨畱鐎圭偘缍嬬猾浼欑礉閻掕泛鎮楅弽瑙勫祦鐎瑰啰娈慽sSelected閻樿�?
				 * 锟介敍锟�閺夈儱鍨介弬顓熸Ц閸氾附妯夌粈娲拷娑擃厽鏅ラ弸婧匡�?閼峰厖绨柅澶夎厬閺佸牊鐏夐惃鍕�?閸掓瑱绱濇稉瀣桨闁�?鍘ら崳銊ф畱娴狅絿鐖滄稉顓濈窗閺堝顕╅弰锟�
				 */
				// if(dataList.get(position).isSelected()){
				// dataList.get(position).setSelected(false);
				// }else{
				// dataList.get(position).setSelected(true);
				// }
				/**
				 * 闁氨鐓￠柅鍌炲帳閸ｎ煉绱濈紒鎴濈暰閻ㄥ嫭鏆熼幑顔煎絺閻㈢喍绨￠弨鐟板綁閿涘苯绨茶ぐ鎾冲煕閺傛媽顬呴崶锟�?				 */
				adapter.notifyDataSetChanged();
			}

		});

	}
}
