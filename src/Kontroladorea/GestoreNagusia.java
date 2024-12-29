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
		JSONObject json = KatalogoNagusia.getKN().getFilmXehetasunak(filmIzena);
		return json;
	}
	
	public void datuakKargatu() {
		KatalogoNagusia.getKN().loadFilmak();
	}
	
	public JSONArray getInfoFilmak() {
		JSONArray array = KatalogoNagusia.getKN().getInfoFilmak();
		return array;
	}
	
}
