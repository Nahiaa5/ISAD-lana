package Kontroladorea;
import Eredua.*;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Iterator;  

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
	
	public List<Erabiltzaile> getErabiltzaileak(){
		return this.erabiltzaileak;
	}
	
	public Erabiltzaile getErabiltzaileByNAN(String nan) {
	    for (Erabiltzaile erabiltzaile : erabiltzaileak) {
	        if (erabiltzaile.getNan().equals(nan)) {
	            return erabiltzaile;
	        }
	    }
	    return null;
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
	
	public void gehituErabiltzailea(Erabiltzaile e) {
		this.erabiltzaileak.add(e);
	}
	
	public void setSaioaNan(String pNan) {
		this.saioaNan = pNan;
	}
	
	public String getSaioaNan() {
		return saioaNan;
	}
	
	public JSONArray getInfoErabiltzaileak() {
		List<Erabiltzaile> erabiltzaileak = getErabiltzaileak();
		JSONArray JSONerab = new JSONArray();
		
		for (Erabiltzaile e : erabiltzaileak) {
			if(e.getOnartuta() == 1) {
				JSONObject json = getInfo(e);
				JSONerab.put(json);
			}
		}
		
		return JSONerab;
	}
	
	private JSONObject getInfo(Erabiltzaile e) {
		// JSONObject bat sortu
	    JSONObject json = new JSONObject();

	    // Izena eta NAN-a jarri JSON-ean
	    json.put("NAN", e.getNan());
	    json.put("Izena", e.getIzena());

	    return json;
	}
	
	public JSONArray getInfoEskaerak() {
		List<Erabiltzaile> erabiltzaileak = getErabiltzaileak();
		JSONArray JSONesk = new JSONArray();
		
		for (Erabiltzaile e : erabiltzaileak) {
			if(e.getOnartuta() == 0) {
				JSONObject json = getInfo(e);
				JSONesk.put(json);
			}
		}
		
		return JSONesk;
	}
	
	public void erabiltzaileaOnartu(String nan) {
		for(Erabiltzaile erab : erabiltzaileak) {
			if(erab.getNan().equals(nan)) {
				erab.Onartu();
			}
		}
	}
	
	public void erabiltzaileaEzabatu(String nan) {
		Iterator<Erabiltzaile> it = erabiltzaileak.iterator();
		while(it.hasNext()) {
			Erabiltzaile erab = it.next();
			if(erab.getNan().equals(nan)) {
				it.remove();
				break;
			}
		}
	}
	
	public JSONArray bilatzaileanErabiltzaileak(String text) {
		JSONArray zerrenda = new JSONArray();
		
		for (Erabiltzaile e : erabiltzaileak) {
			if (e.izenaTestuarekinKointziditu(text) && e.getOnartuta() == 1){
				JSONObject object = getInfo(e);
				zerrenda.put(object);
			}
		}
		
		return zerrenda;
	}
	
	public void alokairuaErabiltzailearenZerrendanGehitu(Alokairua alok) {
		Erabiltzaile erab = getErabiltzaileByNAN(saioaNan);
		erab.gehituAlokairua(alok);
	}
	
	public Erabiltzaile erabiltzaileaAurkitu(String pNan) {
		for (Erabiltzaile e : erabiltzaileak) {
			if(e.getNan().equals(pNan)) {
				return e;
			}
		}
		return null;
	}
	
}
