package com.semavancados.communicationactivities.component;

import java.util.List;

import org.androidutilities.adapter.GenericListAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.semavancados.communicationactivities.R;
import com.semavancados.communicationactivities.model.Car;

@SuppressLint("ViewHolder")
public class CarAdapter extends GenericListAdapter<Car> {

	public CarAdapter(Context context, List<Car> list) {
		super(context, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE );
		View lineAdapted = inflater.inflate( R.layout.line_style, parent,false );

		TextView principal = (TextView) lineAdapted.findViewById(R.id.principal);
		TextView secondary = (TextView) lineAdapted.findViewById(R.id.secondary);
		ImageView star = (ImageView) lineAdapted.findViewById(R.id.star);

		Car car = getItem(position);
		principal.setText( car.toString() );
		secondary.setText( car.getFuel().getDesc() + " / " + car.getYear() );
		star.setImageResource(android.R.drawable.btn_star_big_off);
		
		return lineAdapted; 
	}
}