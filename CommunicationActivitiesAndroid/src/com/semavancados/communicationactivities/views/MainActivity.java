package com.semavancados.communicationactivities.views;

import static com.semavancados.communicationactivities.enumm.ResultCode.ERROR;
import static com.semavancados.communicationactivities.enumm.ResultCode.OK;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.semavancados.communicationactivities.R;
import com.semavancados.communicationactivities.component.CarAdapter;
import com.semavancados.communicationactivities.model.Car;

public class MainActivity extends Activity {

	private ListView listView;
	private CarAdapter carAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		listView = (ListView) findViewById(R.id.listViewCar);
		carAdapter = new CarAdapter(getApplicationContext(), new ArrayList<Car>());
		listView.setAdapter(carAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getApplicationContext(), CarCadastreActivity.class);
				intent.putExtra("car", carAdapter.getItem(position) );
				startActivityForResult(intent, 2);
			}
		});
	}
	
	public void cadastreCar(View view) {
		Intent intent = new Intent(getApplicationContext(), CarCadastreActivity.class);
		startActivityForResult(intent, 1);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == OK) {
			Car car = (Car) data.getSerializableExtra("car");
			if (car != null) {
				carAdapter.addOrReplaceItem(car);
			}
			listView.invalidateViews();
		} else if (resultCode == ERROR) {
			
		}
	}
}
