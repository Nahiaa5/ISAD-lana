package Kontroladorea;

import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;
import java.util.stream.Collectors;
import Eredua.*;

public class KatalogoNagusia extends Observable {
	private List<Film> filmak;
	private List<Film> jatorrizkoFilmak;
	private static KatalogoNagusia nKN=null;
	
	private KatalogoNagusia() {
		filmak=new ArrayList<>();
	}
	
	public static KatalogoNagusia getKN() {
		if(nKN==null) {
			nKN=new KatalogoNagusia();
			
		}
		return nKN;
	}
	
	public List<Film> getFilmak(){
		return this.filmak;
	}
	
	public JSONObject getInfo(Film film) {
		// JSONObject bat sortu
	    JSONObject json = new JSONObject();

	    // Izena eta puntuazioa jarri JSON-ean
	    json.put("izenburua", film.getIzenburua());
	    json.put("puntuazioa", film.getPuntuazioaBb());

	    return json;
	}
	
	public void filmaBilatu(String izena){
		if(izena==null || izena.trim().isEmpty()) {
			this.filmak=new ArrayList<>(jatorrizkoFilmak);
		}else {
			List<Film> emaitza=jatorrizkoFilmak.stream()
					.filter(film->film.getIzenburua().toLowerCase().contains(izena))
					.collect(Collectors.toList());
			if(emaitza.isEmpty()) {
				this.filmak=new ArrayList<>();
			}else {
				this.filmak=new ArrayList<>(emaitza);
			}
		}
		setChanged();
		notifyObservers();
	}
	
	public void loadFilmak() {
	    this.filmak=DB_kudeatzailea.getDB().kargatuFilmak();
	    this.jatorrizkoFilmak=new ArrayList<>(filmak);
	}
	
	public void ordenatuPuntuazioz() {
		this.filmak.sort(Comparator.comparingDouble(Film::getPuntuazioaBb).reversed());
		setChanged();
		notifyObservers();
	}
}
