package com.fcar.android.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.Toast;

public class HttpClientTool {

	// 获取图片以及图片的信息
    @SuppressWarnings("unused")
    @SuppressLint("NewApi")
    public static Map<String, Object> send(Context context, String path) {
        Map<String, Object> map = new HashMap<String, Object>();
        ImageView imageView = null;
        imageView = new ImageView(context);
        imageView.setMaxHeight(100);
        imageView.setMaxWidth(100);
        Bitmap bitmap = null;
        try {
            URL url = new URL(path);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url
                    .openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(5000);
            if (httpURLConnection.getResponseCode() == 200) {
                if (bitmap != null) {
                    System.out.println("---"+bitmap);
                    bitmap.recycle();
                    System.gc();
                }
                InputStream is = httpURLConnection.getInputStream();
                bitmap = BitmapFactory.decodeStream(is);
//              BitmapFactory.Options options = new BitmapFactory.Options();
//              options.inSampleSize = computeSampleSize(options, -1, 128*128);
//              options.inJustDecodeBounds = true;
                int length = httpURLConnection.getContentLength();
                // System.out.println(httpURLConnection.get);
                // MediaStore
                map.put("img", bitmap);
                map.put("length", length);
            } else {
                Toast.makeText(context, "服务器端响应错误", Toast.LENGTH_LONG).show();
            }
            httpURLConnection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;

    }

    public static int computeSampleSize(BitmapFactory.Options options,
            int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    public static int computeInitialSampleSize(BitmapFactory.Options options,
            int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
                Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    // 获取服务器端images文件下的所有图片名称
    public static List<String> httpClientJSON(String path) {
        List<String> list = null;
        try {
            list = new ArrayList<String>();
            HttpGet httpGet = new HttpGet(path);
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                StringBuilder builder = new StringBuilder();
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(httpResponse.getEntity()
                                .getContent()));
                String str = null;
                while ((str = bufferedReader.readLine()) != null) {
                    builder.append(str);
                }
                JSONObject jsonObject = new JSONObject(builder.toString());
                JSONArray jsonArray = jsonObject.getJSONArray("list");
                for (int i = 0; i < jsonArray.length(); i++) {
                    Object obj = jsonArray.opt(i);
                    list.add(obj.toString());
                    System.out.println(obj.toString());
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
