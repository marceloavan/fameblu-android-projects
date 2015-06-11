package org.asselvi.marceloavancini_01.component;

public class IdGenerator {
	private static int lastId;

	{
		lastId = 100;
	}
	
	public static int nextId() {
		return lastId++;
	}
	
}
