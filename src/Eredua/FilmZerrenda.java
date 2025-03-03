package Eredua;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.json.JSONObject;

import Kontroladorea.GestoreFilm;

public class FilmZerrenda {

	private int ZerrendaID;
	private String izena;
	private boolean pribazitatea;
	private String erabiltzaileNAN;
	private List<Film> FilmZerrenda;
	
	public FilmZerrenda(int ID, String izena, boolean pribazitatea, String NAN) {
		this.ZerrendaID = ID;
		this.izena = izena;
		this.pribazitatea = pribazitatea;
		this.erabiltzaileNAN = NAN;
		this.FilmZerrenda = new ArrayList<Film>();
	}
	
	public int getID() {
		return this.ZerrendaID;
	}
	
	public String getIzena() {
		return this.izena;
	}
	
	public void setIzena(String izena) {
		this.izena = izena;
	}
	
	public Boolean getPribazitatea() {
		return this.pribazitatea;
	}
	
	public List<Film> getZerrenda() {
		return this.FilmZerrenda;
	}
	
	public void setPribazitatea(Boolean pribazitatea) {
		this.pribazitatea = pribazitatea;
	}
	
	public boolean sartuFilma(Film filma) {
		boolean badago = false;
		for (Film f : FilmZerrenda) {
			if (f.equals(filma)) {
				badago = true;
				System.out.println("Filma badago zerrendan");
			}
		}
		if (badago == false) {
			FilmZerrenda.add(filma);
		}
		
		return badago;
	}
	
	public Film bilatuFilma(int index) {
		return FilmZerrenda.get(index);
	}
	
	public void kenduFilma(Film filma) {
	    Iterator<Film> iter = FilmZerrenda.iterator();
	    while (iter.hasNext()) {
	        Film film = iter.next();
	        if (film.equals(filma)) {
	            iter.remove();
	        }
	    }
	}
	
	public void print() {
		for (Film f : FilmZerrenda) {
			System.out.println(f.getIzenburua());
		}
	}
	public ArrayList<String> filmenIzenUrte() {
		ArrayList<String> izenak = new ArrayList<>();
		for (Film filma : FilmZerrenda) {
			String titulua = filma.getIzenburua();
            String urtea = filma.getUrtea();
            String izena = (titulua + " (" + urtea + ")");
			izenak.add(izena);
		}
		return izenak;
	}
}
