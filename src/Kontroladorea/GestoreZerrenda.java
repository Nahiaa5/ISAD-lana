package Kontroladorea;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

import Bista.FilmKatalogoZabaldua;
import Eredua.DB_kudeatzailea;
import Eredua.Erabiltzaile;
import Eredua.Film;
import Eredua.FilmZerrenda;

public class GestoreZerrenda {
	private static GestoreZerrenda nZZ;
	private List<FilmZerrenda> zerrenda;
	
	private GestoreZerrenda(){
		zerrenda = new ArrayList<FilmZerrenda>();
	}
	
	public static GestoreZerrenda getnZZ(){
		if(nZZ==null) {
			nZZ=new GestoreZerrenda();
		}
		return nZZ;
	}
	
	public int sortuZerrendaBerria(String izena, Boolean pribazitatea, String NAN) {
		boolean aurkituta = false;
		int ID = -1;
		Erabiltzaile e = GestoreErabiltzaile.getGE().erabiltzaileaBilatuNAN(NAN);
		for (FilmZerrenda f : e.getZerrendak()) {
			if (f.getIzena().equals(izena)) {
				aurkituta = true;
			}
		}
		if (aurkituta == false) {
			ID = DB_kudeatzailea.getDB().sortuZerrendaBerria(izena, pribazitatea);
			FilmZerrenda berria = new FilmZerrenda(ID,izena, pribazitatea);
			zerrenda.add(berria);
			e.ZerrendanSartu(berria);
		}
		return ID;
	}
	
	public FilmZerrenda bilatuZerrenda(int ZerrendaID) {
		for (FilmZerrenda bilatzailea : zerrenda){
			if (bilatzailea.getID() == ZerrendaID) {
				return bilatzailea;
			}
		}
		return null;
	}
	
	public boolean sartuFilmaZerrendaBaten(int ID, String izena) {
		boolean sartuta = false;
		GestoreFilm GF = GestoreFilm.getKN();
		GestoreKatalogoZabaldua KZK = GestoreKatalogoZabaldua.getnZK();
		JSONObject xehetasunak = KZK.xehetasunakBilatu(izena);
		Film filma = GF.bilatuFilma(xehetasunak);
		if (filma == null) {
			System.out.println("Filma ez dago");
		} else {
			FilmZerrenda zerrenda = bilatuZerrenda(ID);
			zerrenda.sartuFilma(filma);
			DB_kudeatzailea.getDB().ErlazioaFilmZerrendak(ID, filma.getFilmID());
			sartuta = true;
		}
		return sartuta;
	}
	
	public void kenduFilmaZerrendaBaten(int ID, String izena) {
		JSONObject xehetasunak = GestoreKatalogoZabaldua.getnZK().xehetasunakBilatu(izena);
		FilmZerrenda zerrenda = bilatuZerrenda(ID);
		Film filma = GestoreFilm.getKN().bilatuFilma(xehetasunak);
		DB_kudeatzailea.getDB().kenduFilmaZerrendatik(ID, filma.getFilmID());
		zerrenda.kenduFilma(filma);
			
	}
	
	public void aldatuXehetasunak(int ID, String izena, Boolean pribazitatea) {
		FilmZerrenda zerrenda = bilatuZerrenda(ID);
		zerrenda.setIzena(izena);
		zerrenda.setPribazitatea(pribazitatea);
		DB_kudeatzailea.getDB().xehetasunakAldatuZerrenda(ID, izena, pribazitatea);
	}
	
	public ArrayList<FilmZerrenda> bilatuZerrendakKat(String izena) {
	    ArrayList<FilmZerrenda> ema = new ArrayList<>();
	    for (FilmZerrenda zerrenda : zerrenda) {
	        if (zerrenda.getIzena().toLowerCase().contains(izena.toLowerCase()) && zerrenda.getPribazitatea() == true) {
	            ema.add(zerrenda);
	        }
	    }
	    return ema;
	}

	
}
