package com.fcar.fcardemo;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.OnMapClickListener;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.mapapi.core.GeoPoint;
import com.amap.mapapi.core.OverlayItem;
import com.amap.mapapi.map.ItemizedOverlay;
import com.amap.mapapi.map.MapActivity;
import com.amap.mapapi.map.MapController;
import com.amap.mapapi.map.MapView;
import com.amap.mapapi.map.MyLocationOverlay;
import com.amap.mapapi.map.Projection;
import com.baidu.location.BDLocation;

public class Map extends MapActivity implements OnMapClickListener{

	static View mPopView = null;	// 点击mark时弹出的气泡View


	private AMap aMap;
	 static MapView myMapView;
	    MapController myMapController;
	    LocationListener mLocationListener = null;//onResume时注册此listener，onPause时需要Remove
		MyLocationOverlay mLocationOverlay = null;	//定位图层
		private double lat, lon;
		private Circle circle;
		/**
	     * 暂时缓存我的当前位置数据
	     */
	    private BDLocation mBDLocation;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.map);
	myMapView = (MapView) findViewById(R.id.myMapView);
	// 设置地图模式为交通地图
	myMapView.setTraffic(true);
	//设置可控
	myMapView.setClickable(true);
	myMapView.setEnabled(true);
	myMapView.setBuiltInZoomControls(true);
	myMapController = myMapView.getController();
	GeoPoint point =new GeoPoint((int)(39.90923*1e6), (int)(116.397428*1e6));
	myMapView.getController().setCenter(point);
	Drawable marker = getResources().getDrawable(R.drawable.icon_gcoding);
	marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker
			.getIntrinsicHeight());// Intrinsic固有
	myMapView.getOverlays().add(new OverItemT(marker, this));
	// 创建点击mark时的弹出泡泡
			mPopView=super.getLayoutInflater().inflate(R.layout.popview, null);
			myMapView.addView( mPopView,
	                new MapView.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
	                		null, MapView.LayoutParams.TOP_LEFT));
			mPopView.setVisibility(View.GONE);
	// 添加定位图层
    mLocationOverlay = new MyLocationOverlay(this, myMapView);
    myMapView.getOverlays().add(mLocationOverlay);
    myMapView.setOnTouchListener(new View.OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// 获得屏幕点击的位置
            int x = (int) event.getX();
            int y = (int) event.getY();

            // 将像素坐标转为地址坐标
            Projection getProjection = myMapView.getProjection();
            GeoPoint pt = getProjection.fromPixels(x, y);



			return false;
		}
	});

    // 注册定位事件
    mLocationListener = new LocationListener(){

		public void onLocationChanged(Location location) {
			if (location != null){
				GeoPoint pt = new GeoPoint((int)(location.getLatitude()*1e6),
						(int)(location.getLongitude()*1e6));
				myMapView.getController().animateTo(pt);
			}
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}


    };

	}
	 /**
     * 显示我当前位置的详细信息
     * @param location
     */
    @SuppressWarnings("unused")
	private void showLocation(BDLocation location) {
        StringBuffer sb = new StringBuffer(256);
        sb.append(" time : ");
        sb.append(location.getTime());
        sb.append("\n error code : ");
        sb.append(location.getLocType());
        sb.append("\n latitude : ");
        sb.append(location.getLatitude());
        sb.append("\n lontitude : ");
        sb.append(location.getLongitude());

        // 判断是否有定位精度半径
        if (location.hasRadius()) {
            // 获取定位精度半径，单位是米
            float accuracy = location.getRadius();
            //Log.i(TAG, "accuracy = " + accuracy);

            sb.append("\n radius : ");
            sb.append(location.getRadius());
        }

        if (location.getLocType() == BDLocation.TypeGpsLocation) {
            if (location.hasSpeed()) {
                sb.append("\n speed : ");
                sb.append(location.getSpeed());
            }

            if (location.hasSateNumber()) {
                sb.append("\n satellite : ");
                sb.append(location.getSatelliteNumber());
            }
        } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
            // 获取反地理编码。 只有使用网络定位的情况下，才能获取当前位置的反地理编码描述。
            if (location.hasAddr()) {
                String address = location.getAddrStr();
                //Log.i(TAG, "address = " + address);

                sb.append("\n addr : ");
                sb.append(address);
            }
        }

//        tvLocationResult.setVisibility(View.VISIBLE);
//        tvLocationResult.setText(sb);
    }

	class OverItemT extends ItemizedOverlay<OverlayItem> {
		// 这个Listener用于在Marker被点击时让Activity填充PopupView的内容
	    private OnTapListener onTapListener = null;

		private List<OverlayItem> mGeoList = new ArrayList<OverlayItem>();
		private Drawable marker;
		private Context mContext;

		private double mLat1 = 22.577667; // point1纬度
		private double mLon1 = 112.967428; // point1经度

		private double mLat2 = 22.577668;
		private double mLon2 = 113.965122;

		private double mLat3 = 22.577669;
		private double mLon3 = 114.966223;

		private double mLat4 = 22.577666;
		private double mLon4 = 115.969023;

		private double mLat5 = 22.575466;
		private double mLon5 = 116.969853;

		private double mLat6 = 22.576566;
		private double mLon6 = 117.969963;

		private double mLat7 = 22.578366;
		private double mLon7 = 118.969773;

		public OverItemT(Drawable marker, Context context) {
			super(boundCenterBottom(marker));

			this.marker = marker;
			this.mContext = context;

			// 用给定的经纬度构造GeoPoint，单位是微度 (度 * 1E6)
			GeoPoint p1 = new GeoPoint((int) (mLat1 * 1E6), (int) (mLon1 * 1E6));
			GeoPoint p2 = new GeoPoint((int) (mLat2 * 1E6), (int) (mLon2 * 1E6));
			GeoPoint p3 = new GeoPoint((int) (mLat3 * 1E6), (int) (mLon3 * 1E6));
			GeoPoint p4 = new GeoPoint((int) (mLat4 * 1E6), (int) (mLon4 * 1E6));
			GeoPoint p5 = new GeoPoint((int) (mLat5 * 1E6), (int) (mLon5 * 1E6));
			GeoPoint p6 = new GeoPoint((int) (mLat6 * 1E6), (int) (mLon6 * 1E6));
			GeoPoint p7 = new GeoPoint((int) (mLat7 * 1E6), (int) (mLon7 * 1E6));

			// 构造OverlayItem的三个参数依次为：item的位置，标题文本，文字片段
			mGeoList.add(new OverlayItem(p1, "", "三读通过"));
			mGeoList.add(new OverlayItem(p2, "", ""));
			mGeoList.add(new OverlayItem(p3, "", ""));
			mGeoList.add(new OverlayItem(p4, "", ""));
			mGeoList.add(new OverlayItem(p5, "", ""));
			mGeoList.add(new OverlayItem(p6, "", ""));
			mGeoList.add(new OverlayItem(p7, "", ""));
			populate();  //createItem(int)方法构造item。一旦有了数据，在调用其它方法前，首先调用这个方法
		}

		@Override
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {

			// Projection接口用于屏幕像素坐标和经纬度坐标之间的变换
			Projection projection = mapView.getProjection();
			for (int index = size() - 1; index >= 0; index--) { // 遍历mGeoList
				OverlayItem overLayItem = getItem(index); // 得到给定索引的item

				String title = overLayItem.getTitle();
				// 把经纬度变换到相对于MapView左上角的屏幕像素坐标
				Point point = projection.toPixels(overLayItem.getPoint(), null);

				// 可在此处添加您的绘制代码
				Paint paintText = new Paint();
				paintText.setColor(Color.BLUE);
				paintText.setTextSize(15);
				canvas.drawText(title, point.x-30, point.y, paintText); // 绘制文本
			}

			super.draw(canvas, mapView, shadow);
			//调整一个drawable边界，使得（0，0）是这个drawable底部最后一行中心的一个像素
			boundCenterBottom(marker);
		}
		public void setOnTapListener(OnTapListener onTapListener) {
	        this.onTapListener = onTapListener;
	    }

		@Override
		protected OverlayItem createItem(int i) {
			// TODO Auto-generated method stub
			return mGeoList.get(i);
		}

		@Override
		public int size() {
			// TODO Auto-generated method stub
			return mGeoList.size();
		}


		@Override
		// 处理当点击事件
		protected boolean onTap(int i) {
			setFocus(mGeoList.get(i));
			// 更新气泡位置,并使之显示
			GeoPoint pt = mGeoList.get(i).getPoint();
			Map.myMapView.updateViewLayout( Map.mPopView,
	                new MapView.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
	                		pt, MapView.LayoutParams.BOTTOM_CENTER));
			Map.mPopView.setVisibility(View.VISIBLE);
			Toast.makeText(this.mContext, mGeoList.get(i).getSnippet(),
					Toast.LENGTH_SHORT).show();
			return true;
		}

		@Override
		public boolean onTap(GeoPoint p, MapView mapview) {
			// TODO Auto-generated method stub
			// 消去弹出的气泡
//			ItemizedOverlayDemo.mPopView.setVisibility(View.GONE);
			p.getLatitudeE6();
			p.getLongitudeE6();
			return super.onTap(p, mapview);
		}



	}

	@Override
	public void onMapClick(LatLng point) {
		// Toast.makeText(MainActivity.this, "Click point = " + point,
		// 0).show();
		lat = point.latitude;
		lon = point.longitude;
		AMapLocation aLocation = new AMapLocation("test");
		aLocation.setLatitude(lat);
		aLocation.setLongitude(lon);

		aMap.clear();

		// changeCamera(
		// CameraUpdateFactory.newCameraPosition(new CameraPosition(
		// new LatLng(lat, lon), 18, 0, 30)), null);

		aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(lat, lon)));

		circle = aMap.addCircle(new CircleOptions()
				.center(new LatLng(lat, lon)).radius(500)
				.strokeColor(Color.argb(50, 1, 1, 1))
				.fillColor(Color.argb(50, 1, 1, 1)).strokeWidth(5));

		// aMap.addMarker(new MarkerOptions().position(new LatLng(lat,
		// lon)).icon(
		// BitmapDescriptorFactory
		// .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
	}

	 public interface OnTapListener {
	        public void onTap(int index, View popupView);
	    }

}
