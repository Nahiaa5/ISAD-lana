package Kontroladorea;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;
import java.util.stream.Collectors;
import Eredua.*;

public class GestoreFilm extends Observable {
	private List<Film> filmak;
	private List<Film> jatorrizkoFilmak;
	private static GestoreFilm nKN=null;
	
	private GestoreFilm() {
		filmak=new ArrayList<>();
	}
	
	public static GestoreFilm getKN() {
		if(nKN==null) {
			nKN=new GestoreFilm();
			
		}
		return nKN;
	}
	
	public List<Film> getFilmak(){
		return this.filmak;
	}
	
	public JSONArray getInfoFilmak() {
		List<Film> filmak = getFilmak();
		
		JSONArray JSONfilm = new JSONArray();
		for (Film film : filmak) {
			JSONObject json = getInfo(film);
			JSONfilm.put(json);
		}
		
		return JSONfilm;
	}
	
	private JSONObject getInfo(Film film) {
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
	
//-------------------------------REVISADO
	private Film bilatuIzenarekin(String filmIzena) {
	    for (Film film : filmak) {
	        if (film.getIzenburua().equalsIgnoreCase(filmIzena)) {
	            return film;
	        }
	    }
	    return null;
	}
	
	public JSONObject getFilmXehetasunak(String filmIzena) {
		Film film = bilatuIzenarekin(filmIzena);
		
		JSONObject json = new JSONObject();

	    json.put("izenburua", film.getIzenburua());
	    json.put("aktoreak", film.getAktoreak());
	    json.put("urtea", film.getUrtea());
	    json.put("generoa", film.getGeneroa());
	    json.put("zuzendaria", film.getZuzendaria());
	    json.put("bbpuntuazioa", film.getPuntuazioaBb());
	    
	    return json;
	}
}
