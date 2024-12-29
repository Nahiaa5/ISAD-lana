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
	
}
