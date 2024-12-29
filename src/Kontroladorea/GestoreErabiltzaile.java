package Kontroladorea;
import Eredua.*;

import java.util.ArrayList;
import java.util.List;

public class GestoreErabiltzaile {
	private List<Erabiltzaile> erabiltzaileak;
	private String saioaNan;
	private static GestoreErabiltzaile nGE = null;
	
	private GestoreErabiltzaile() {
		erabiltzaileak = new ArrayList<>();
	}
	
	public static GestoreErabiltzaile getGE() {
		if(nGE == null) {
			nGE = new GestoreErabiltzaile();
			
		}
		return nGE;
	}
	
	public void loadErabiltzaileak() {
		this.erabiltzaileak = DB_kudeatzailea.getDB().kargatuErabiltzaileak();
	}

}
