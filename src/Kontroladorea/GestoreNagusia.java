package Kontroladorea;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Observable;
import Eredua.*;

public class GestoreNagusia extends Observable {

	private static GestoreNagusia nGN = null;
	
	private GestoreNagusia() {}
	
	public static GestoreNagusia getGN() {
		if(nGN==null) {
			nGN=new GestoreNagusia();
			
		}
		return nGN;
	}
	
	public JSONObject getFilmXehetasunak(String filmIzena) {
		JSONObject json = GestoreFilm.getKN().getFilmXehetasunak(filmIzena);
		return json;
	}
	
	public void datuakKargatu() {
		GestoreFilm.getKN().loadFilmak();
		GestoreErabiltzaile.getGE().loadErabiltzaileak();
	}
	
	public JSONArray getInfoFilmak() {
		JSONArray array = GestoreFilm.getKN().getInfoFilmak();
		return array;
	}
	
	public void getErabiltzaile(String pNan, String pPas) {
		Erabiltzaile e = GestoreErabiltzaile.getGE().erabiltzaileaBilatu(pNan, pPas);
		if (e == null) {
			setChanged();
			notifyObservers("Txarto");
		}
		else {
			GestoreErabiltzaile.getGE().setSaioaNan(e.getNan());
			setChanged();
			notifyObservers(e);
		}
		
		
	}
	
	public void ErabiltzaileBerriaSartu(String pNAN, String pIzena, String pAbizena, String pEmail, String pPasahitza) {
		boolean ondo;
		if(pNAN.isEmpty()|| pIzena.isEmpty() || pAbizena.isEmpty() || pEmail.isEmpty() || pPasahitza.isEmpty()) {
			ondo = false;
		}
		else {
			ondo = DB_kudeatzailea.getDB().erabiltzaileBerriaSartu(pNAN, pIzena, pAbizena, pEmail, pPasahitza);
		}
		setChanged();
		notifyObservers(ondo);
	}
	
}
