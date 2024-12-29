package Kontroladorea;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class GestoreNagusia {

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
	
}
