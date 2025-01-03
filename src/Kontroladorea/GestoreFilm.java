package Kontroladorea;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
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
			if (datuak.getString("Title") == film.getIzenburua() &&
				datuak.getString("Actors") == film.getAktoreak() &&	
				datuak.getString("Year") == film.getUrtea() &&
				datuak.getString("Genre") == film.getIzenburua() &&
				datuak.getString("Director") == film.getIzenburua()
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
		for (Film filma : filmak) {
			if (filma.getIzenburua().equalsIgnoreCase(izena) && filma.getUrtea() == urtea) {
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
	
//-------------------------------REVISADO
	public Film bilatuIzenarekin(String filmIzena) {
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
		filma.onartu();
	}
	
	public void filmaEzabatu(String izena) {
		Film filma = bilatuIzenarekin(izena);
		filmak.remove(filma);
	}
	
	public void loadPuntuazioak() {
	    List<Puntuazioa> puntuazioak = DB_kudeatzailea.getDB().kargatuPuntuazioak();

	    for (Film film : filmak) {
	        List<Puntuazioa> puntuazioIragaziak = puntuazioak.stream()
	                .filter(p -> p.getFilmID() == film.getFilmID())
	                .collect(Collectors.toList());

	        film.setBalorazioak(new ArrayList<>(puntuazioIragaziak));
	        //film.eguneratuPuntuBb(DB_kudeatzailea.getDB());
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
	
	public boolean erabiltzaileakAlokatuDu(String nan, int filmID) {
		Erabiltzaile erab = erabiltzaileaBilatu(nan);
		if(erab!=null) {
			for(Alokairua alok: erab.getEgindakoAlokairuak()) {
				if(alok.getFilm().getFilmID()==filmID) {
					return true;
				}
			}
		}
		return false;
	}
	
	public Erabiltzaile erabiltzaileaBilatu(String nan) {
		for(Erabiltzaile erab: GestoreErabiltzaile.getGE().getErabiltzaileak()) {
			if(erab.getNan().equals(nan)) {
				return erab;
			}
		}
		return null;
	}
	
	public void kalkulatuPuntuazioak() {
		for(Film film: filmak) {
			film.kalkulatuPuntuBb();
		}
	}
	
	public void gordePuntuazioa(String NAN, int filmID, int puntuazioa, String iruzkina) {
        Film film = bilatuFilma(filmID);
        if (film == null) {
            throw new IllegalArgumentException("Filma ez da aurkitu");
        }
        String nan = GestoreErabiltzaile.getGE().getSaioaNan();
        
        if(!erabiltzaileakAlokatuDu(nan, filmID)) {
        	throw new IllegalArgumentException("Ez duzu film hau alokatu, ezin duzu baloratu.");
        }

        List<Puntuazioa> puntuazioak = film.getBalorazioak();
        System.out.println(puntuazioak);
        Puntuazioa puntu = new Puntuazioa(nan, filmID, puntuazioa, iruzkina, LocalDate.now());

        if (puntuazioaBadago(nan, filmID)) {
            puntuazioak.removeIf(p -> p.getNAN().equals(nan));
        }
        puntuazioak.add(puntu);
        System.out.println(puntuazioak);
        setChanged();
        notifyObservers();
    }
}
