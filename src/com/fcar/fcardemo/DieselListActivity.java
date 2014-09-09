package com.fcar.fcardemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class DieselListActivity extends Activity {
	private GridView gridView;

	@SuppressWarnings("rawtypes")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gridview_item);
		gridView=(GridView) findViewById(R.id.grid);
		ArrayAdapter adapter=ArrayAdapter.createFromResource(this, R.array.ccar,android.R.layout.simple_list_item_1);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				finish();


			}
		});

	}

}
