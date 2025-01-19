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
	
	public ArrayList<Film> bilatuFilmakKat(String izena) {
	    ArrayList<Film> ema = new ArrayList<>();
	    for (Film f : filmak) {
	        if (f.getIzenburua().toLowerCase().contains(izena.toLowerCase())) {
	            ema.add(f);
	        }
	    }
	    return ema;
	}
	
	public List<Film> getFilmak(){
		return this.filmak;
	}
	
	private JSONObject getInfo(Film film) {
		// JSONObject bat sortu
	    JSONObject json = new JSONObject();

	    // Izena eta puntuazioa jarri JSON-ean
	    json.put("izenburua", film.getIzenburua());
	    json.put("puntuazioa", film.getPuntuazioaBb());

	    return json;
	}
	
	public JSONArray getInfoKatalogokoFilmGuztiak() {
		List<Film> filmak = getFilmak();
		
		JSONArray JSONfilm = new JSONArray();
		for (Film film : filmak) {
			if(film.getKatalogoan()) {
				JSONObject json = getInfo(film);
				JSONfilm.put(json);
			}
		}
		
		return JSONfilm;
	}
	
	public Film bilatuFilma(JSONObject datuak) {
	    for (Film film : filmak) {
	        if (datuak.getString("Title").trim().equalsIgnoreCase(film.getIzenburua().trim()) &&
	            datuak.getString("Actors").trim().equalsIgnoreCase(film.getAktoreak().trim()) &&	
	            datuak.getString("Year").trim().equalsIgnoreCase(film.getUrtea().trim()) &&
	            datuak.getString("Genre").trim().equalsIgnoreCase(film.getGeneroa().trim()) &&
	            datuak.getString("Director").trim().equalsIgnoreCase(film.getZuzendaria().trim())
	        ) {
	            return film;
	        }
	    }
	    return null;
	}

	
	public JSONArray bilatuFilmKatalogoan(String text) {
		JSONArray zerrenda = new JSONArray();
		
		for (Film film : filmak) {
			if (film.getKatalogoan() && film.izenburuaTestuarekinKointziditu(text)) {
				JSONObject object = getInfo(film);
				zerrenda.put(object);
			}
		}
		
		return zerrenda;
	}

	public boolean badagoFilma(String izena, String urtea) {
		System.out.println("entra");
		for (Film filma : filmak) {
			if (filma.getIzenburua().equalsIgnoreCase(izena) && filma.getUrtea().contains(urtea)) { 
				return true;
			}
		}
		return false;
	}
	
	public void addFilma(JSONObject filmDatuak) {
		//Zerrendan ez dagoen ID bat ezarri
		int id = 0;
		for(Film filma : filmak) {
			if(filma.getFilmID()>=id) {
				id = filma.getFilmID() + 1;
			}
		}
        String izenburua = filmDatuak.getString("Title");
        String aktoreak = filmDatuak.getString("Actors");
        String urtea = filmDatuak.getString("Year");
        String generoa = filmDatuak.getString("Genre");
        String zuzendaria = filmDatuak.getString("Director");
        String adminNAN = null;
        boolean katalogoan = false;
        double puntuazioaBb = 0.0;
        Film filma = new Film(id, izenburua, aktoreak, urtea, generoa, zuzendaria, adminNAN, katalogoan, puntuazioaBb, null);
        DB_kudeatzailea.getDB().gordeFilma(id, izenburua, aktoreak, urtea, generoa, zuzendaria, adminNAN, null);
        filmak.add(filma);
        System.out.println(filmak);
	}
	
	public void loadFilmak() {
	    this.filmak=DB_kudeatzailea.getDB().kargatuFilmak();
	}
	
	public void ordenatuPuntuazioz() {
		this.filmak.sort(Comparator.comparingDouble(Film::getPuntuazioaBb).reversed());
		setChanged();
		notifyObservers();
	}
	
	public Film bilatuIzenarekin(String filmIzena) {
	    for (Film film : filmak) {
	        if (film.getIzenburua().equalsIgnoreCase(filmIzena)) {
	            return film;
	        }
	    }
	    return null;
	}
	
	public Film bilatuIzenaDatarekin(String filmIzena, String data) {
		for (Film film : filmak) {
	        if (film.getIzenburua().equalsIgnoreCase(filmIzena) && film.getUrtea().equals(data)) {
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
	    json.put("puntuazioaBb", film.getPuntuazioaBb());
	    json.put("iruzkinak", film.getIruzkinak());
	    
	    return json;
	}
	
	public JSONArray getFilmEskaerak() {
		JSONArray JSONesk = new JSONArray();
		for(Film film : filmak) {
			if(film.getKatalogoan() == false) {
				JSONObject json = getFilmXehetasunak(film.getIzenburua());
				JSONesk.put(json);
			}
		}
		return JSONesk;
	}
	
	public void filmaOnartu(String izena) {
		Film filma = bilatuIzenarekin(izena);
		String adminNAN = GestoreErabiltzaile.getGE().getSaioaNan();
		filma.onartu(adminNAN);
		DB_kudeatzailea.getDB().filmaOnartu(filma.getFilmID(), adminNAN);
	}
	
	public void filmaEzabatu(String izena) {
		Film filma = bilatuIzenarekin(izena);
		filmak.remove(filma);
		DB_kudeatzailea.getDB().filmaEzabatu(izena);
	}
	
	public void loadPuntuazioak() {
	    List<Puntuazioa> puntuazioak = DB_kudeatzailea.getDB().kargatuPuntuazioak();

	    for (Film film : filmak) {
	        List<Puntuazioa> puntuazioIragaziak = puntuazioak.stream()
	                .filter(p -> p.getFilmID() == film.getFilmID())
	                .collect(Collectors.toList());

	        film.setBalorazioak(new ArrayList<>(puntuazioIragaziak));
	    }
	    setChanged();
	    notifyObservers();
	}
	
	public boolean puntuazioaBadago(String NAN, int filmID) {
        Film film = bilatuFilma(filmID);
        if (film == null) return false;
        
        List<Puntuazioa> puntuazioak = film.getBalorazioak();
        for (Puntuazioa puntu : puntuazioak) {
            if (puntu.getNAN().equals(NAN)) {
                return true; 
            }
        }
        return false;
    }
	
	public Film bilatuFilma(int filmID) {
        for (Film film : filmak) {
            if (film.getFilmID() == filmID) {
                return film;
            }
        }
        return null;
    }

	public void gordePuntuazioaFilman(Puntuazioa pPuntuazio,int filmID) {
		for(Film film: filmak) {
			if(film.filmaDa(filmID)) {
				film.gehituPuntuazioa(pPuntuazio);
				setChanged();
				notifyObservers();
				return;
			}
		}
		throw new IllegalArgumentException("Ez da aurkitu filma ID horrekin");
	}
	
	public String getFilmarenPath(Film film) {
		return film.getPath();
	}
}
