package Kontroladorea;
import Eredua.*;

import java.util.ArrayList;
import java.util.List;

public class GestoreErabiltzaile {
	private List<Erabiltzaile> erabiltzaileak;
	private String saioaNan = null;
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
	
	public Erabiltzaile erabiltzaileaBilatu(String pNAN, String pPasahitza) {
		Erabiltzaile aurkituta = null;
		for (Erabiltzaile e: erabiltzaileak) {
			if(e.getNan().equals(pNAN) && e.getPasahitza().equals(pPasahitza)){
				aurkituta = e;
			}	
		}
		
		return aurkituta;
	}
	
	public void setSaioaNan(String pNan) {
		this.saioaNan = pNan;
	}
}
