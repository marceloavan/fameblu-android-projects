package com.semavancados.communicationactivities.views;

import static com.semavancados.communicationactivities.enumm.ResultCode.ERROR;
import static com.semavancados.communicationactivities.enumm.ResultCode.OK;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.semavancados.communicationactivities.R;
import com.semavancados.communicationactivities.model.Car;
import com.semavancados.communicationactivities.model.CarMake;
import com.semavancados.communicationactivities.model.Fuel;
import com.semavancados.communicationactivities.store.DataBase;

public class CarCadastreActivity extends Activity {

	private Car car;
	
	//itens view
	private Spinner spinnerCarMake;
	private RadioGroup radioGroupFuel;
	private EditText editModel;
	private EditText editYear;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_car_cadastre);

		initItensView();
		
		car = (Car) getIntent().getSerializableExtra("car");
		if (car != null) {
			editModel.setText(car.getModel());
			editYear.setText(car.getYear().toString());
			spinnerCarMake.setSelection(DataBase.getCarMakeList().indexOf(car.getMake()) + 1);
			radioGroupFuel.check(DataBase.getFuelList().indexOf(car.getFuel()) + 1);
		}
	}
	
	private void initItensView() {
		// spinner car make
		spinnerCarMake = (Spinner) findViewById(R.id.carMakeSp);
		ArrayAdapter<CarMake> arrayAdapter = new ArrayAdapter<CarMake>(this, android.R.layout.simple_spinner_dropdown_item, DataBase.getCarMakeList());
		arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item );
		spinnerCarMake.setAdapter(arrayAdapter);
		
		// radio fuel
		radioGroupFuel = (RadioGroup) findViewById(R.id.fuelRadio);
		RadioButton radioButton;
		for (Fuel fuel : DataBase.getFuelList()) {
			radioButton = new RadioButton(getApplicationContext());
			radioButton.setText(fuel.getDesc());
			radioButton.setTextColor(Color.BLACK);
			radioGroupFuel.addView(radioButton);
		}
		
		editModel = (EditText) findViewById(R.id.modelEt);
		editYear = (EditText) findViewById(R.id.anoEt);
	}
	
	public void submit(View view) {
		Intent intent = new Intent();
		setResult(OK, intent);

		try {
			String model = editModel.getText().toString();
			String year = editYear.getText().toString();
			CarMake carMake = (CarMake) spinnerCarMake.getSelectedItem();
			RadioButton radioFuel = (RadioButton) findViewById(radioGroupFuel.getCheckedRadioButtonId());
			Fuel fuel = new Fuel(0, radioFuel.getText().toString());
	
			if (car == null) {
				car = new Car(Integer.valueOf((int) Math.random()), model, carMake, Integer.valueOf(year), fuel);
			} else {
				car.setModel(model);
				car.setYear(Integer.valueOf(year));
				car.setMake(carMake);
				car.setFuel(fuel);
			}
			intent.putExtra("car", car);
		} catch (Exception e) {
			setResult(ERROR);
		}
		finish();
	}
	
	public void cancel(View view) {
		finish();
	}
}
