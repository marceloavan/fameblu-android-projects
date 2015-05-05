package com.semavancados.communicationactivities.store;

import java.util.ArrayList;
import java.util.List;

import com.semavancados.communicationactivities.model.CarMake;
import com.semavancados.communicationactivities.model.Fuel;

public class DataBase {
	
	private static List<CarMake> carMakeList;
	private static List<Fuel> fuelList;
	
	public static List<Fuel> getFuelList() {
		if (fuelList == null) {
			load();				
		}
		return fuelList;
	}
	
	public static List<CarMake> getCarMakeList() {
		if (carMakeList == null) {
			load();
		}
		return carMakeList;
	}
	
	private static void load() {
		carMakeList = new ArrayList<CarMake>();
		carMakeList.add(new CarMake(1, "Renault", 2015));
		carMakeList.add(new CarMake(2, "VW", 2015));
		carMakeList.add(new CarMake(3, "Cherry", 2015));
		carMakeList.add(new CarMake(4, "Pegeout", 2015));
		carMakeList.add(new CarMake(5, "Fiat", 2015));
		carMakeList.add(new CarMake(6, "BMW", 2015));
		carMakeList.add(new CarMake(7, "Jeep", 2015));
		carMakeList.add(new CarMake(8, "Chevrolet", 2015));
		
		fuelList = new ArrayList<Fuel>();
		fuelList.add(new Fuel(1, "Gasolina"));
		fuelList.add(new Fuel(2, "Alcool"));
		fuelList.add(new Fuel(3, "Gás"));
	}
}
