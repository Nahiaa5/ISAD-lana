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
	//private Erabiltzaile jabea;
	private List<Film> FilmZerrenda;
	private Iterator iter;
	
	public FilmZerrenda(int ID, String izena, boolean pribazitatea ) {
		this.ZerrendaID = ID;
		this.izena = izena;
		this.pribazitatea = pribazitatea;
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
	
	public void setPribazitatea(Boolean pribazitatea) {
		this.pribazitatea = pribazitatea;
	}
	
	public void sartuFilma(Film filma) {
		FilmZerrenda.add(filma);
	}
	
	public Film bilatuFilma(int index) {
		return FilmZerrenda.get(index);
	}
	
	public void kenduFilma(Film filma) {
		for (Film film : FilmZerrenda) {
			if (film.equals(filma)){
				FilmZerrenda.remove(film);
			}
		}
	}
	
	public ArrayList<String> filmenIzenak() {
		ArrayList<String> izenak = new ArrayList<>();
		for (Film filma : FilmZerrenda) {
			String izena = filma.getIzenburua();
			izenak.add(izena);
		}
		return izenak;
	}
}
