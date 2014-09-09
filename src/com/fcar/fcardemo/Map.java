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

	static View mPopView = null;	// ���markʱ����������View


	private AMap aMap;
	 static MapView myMapView;
	    MapController myMapController;
	    LocationListener mLocationListener = null;//onResumeʱע���listener��onPauseʱ��ҪRemove
		MyLocationOverlay mLocationOverlay = null;	//��λͼ��
		private double lat, lon;
		private Circle circle;
		/**
	     * ��ʱ�����ҵĵ�ǰλ������
	     */
	    private BDLocation mBDLocation;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.map);
	myMapView = (MapView) findViewById(R.id.myMapView);
	// ���õ�ͼģʽΪ��ͨ��ͼ
	myMapView.setTraffic(true);
	//���ÿɿ�
	myMapView.setClickable(true);
	myMapView.setEnabled(true);
	myMapView.setBuiltInZoomControls(true);
	myMapController = myMapView.getController();
	GeoPoint point =new GeoPoint((int)(39.90923*1e6), (int)(116.397428*1e6));
	myMapView.getController().setCenter(point);
	Drawable marker = getResources().getDrawable(R.drawable.icon_gcoding);
	marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker
			.getIntrinsicHeight());// Intrinsic����
	myMapView.getOverlays().add(new OverItemT(marker, this));
	// �������markʱ�ĵ�������
			mPopView=super.getLayoutInflater().inflate(R.layout.popview, null);
			myMapView.addView( mPopView,
	                new MapView.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
	                		null, MapView.LayoutParams.TOP_LEFT));
			mPopView.setVisibility(View.GONE);
	// ��Ӷ�λͼ��
    mLocationOverlay = new MyLocationOverlay(this, myMapView);
    myMapView.getOverlays().add(mLocationOverlay);
    myMapView.setOnTouchListener(new View.OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// �����Ļ�����λ��
            int x = (int) event.getX();
            int y = (int) event.getY();

            // ����������תΪ��ַ����
            Projection getProjection = myMapView.getProjection();
            GeoPoint pt = getProjection.fromPixels(x, y);



			return false;
		}
	});

    // ע�ᶨλ�¼�
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
     * ��ʾ�ҵ�ǰλ�õ���ϸ��Ϣ
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

        // �ж��Ƿ��ж�λ���Ȱ뾶
        if (location.hasRadius()) {
            // ��ȡ��λ���Ȱ뾶����λ����
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
            // ��ȡ��������롣 ֻ��ʹ�����綨λ������£����ܻ�ȡ��ǰλ�õķ��������������
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
		// ���Listener������Marker�����ʱ��Activity���PopupView������
	    private OnTapListener onTapListener = null;

		private List<OverlayItem> mGeoList = new ArrayList<OverlayItem>();
		private Drawable marker;
		private Context mContext;

		private double mLat1 = 22.577667; // point1γ��
		private double mLon1 = 112.967428; // point1����

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

			// �ø����ľ�γ�ȹ���GeoPoint����λ��΢�� (�� * 1E6)
			GeoPoint p1 = new GeoPoint((int) (mLat1 * 1E6), (int) (mLon1 * 1E6));
			GeoPoint p2 = new GeoPoint((int) (mLat2 * 1E6), (int) (mLon2 * 1E6));
			GeoPoint p3 = new GeoPoint((int) (mLat3 * 1E6), (int) (mLon3 * 1E6));
			GeoPoint p4 = new GeoPoint((int) (mLat4 * 1E6), (int) (mLon4 * 1E6));
			GeoPoint p5 = new GeoPoint((int) (mLat5 * 1E6), (int) (mLon5 * 1E6));
			GeoPoint p6 = new GeoPoint((int) (mLat6 * 1E6), (int) (mLon6 * 1E6));
			GeoPoint p7 = new GeoPoint((int) (mLat7 * 1E6), (int) (mLon7 * 1E6));

			// ����OverlayItem��������������Ϊ��item��λ�ã������ı�������Ƭ��
			mGeoList.add(new OverlayItem(p1, "", "����ͨ��"));
			mGeoList.add(new OverlayItem(p2, "", ""));
			mGeoList.add(new OverlayItem(p3, "", ""));
			mGeoList.add(new OverlayItem(p4, "", ""));
			mGeoList.add(new OverlayItem(p5, "", ""));
			mGeoList.add(new OverlayItem(p6, "", ""));
			mGeoList.add(new OverlayItem(p7, "", ""));
			populate();  //createItem(int)��������item��һ���������ݣ��ڵ�����������ǰ�����ȵ����������
		}

		@Override
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {

			// Projection�ӿ�������Ļ��������;�γ������֮��ı任
			Projection projection = mapView.getProjection();
			for (int index = size() - 1; index >= 0; index--) { // ����mGeoList
				OverlayItem overLayItem = getItem(index); // �õ�����������item

				String title = overLayItem.getTitle();
				// �Ѿ�γ�ȱ任�������MapView���Ͻǵ���Ļ��������
				Point point = projection.toPixels(overLayItem.getPoint(), null);

				// ���ڴ˴�������Ļ��ƴ���
				Paint paintText = new Paint();
				paintText.setColor(Color.BLUE);
				paintText.setTextSize(15);
				canvas.drawText(title, point.x-30, point.y, paintText); // �����ı�
			}

			super.draw(canvas, mapView, shadow);
			//����һ��drawable�߽磬ʹ�ã�0��0�������drawable�ײ����һ�����ĵ�һ������
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
		// ��������¼�
		protected boolean onTap(int i) {
			setFocus(mGeoList.get(i));
			// ��������λ��,��ʹ֮��ʾ
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
			// ��ȥ����������
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
