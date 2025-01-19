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
	
	public void loadZerrenda() {
		this.zerrenda = DB_kudeatzailea.getDB().kargatuFilmZerrendak();
	}
	
	public void kargatuZerrenda(FilmZerrenda f) {
		zerrenda.add(f);
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
			ID = DB_kudeatzailea.getDB().sortuZerrendaBerria(izena, pribazitatea, NAN);
			FilmZerrenda berria = new FilmZerrenda(ID, izena, pribazitatea, NAN);
			zerrenda.add(berria);
			e.ZerrendanSartu(berria);
		}
		return ID;
	}
	
	public void ezabatuZerrenda (int ID , String NAN) {
		Erabiltzaile e = GestoreErabiltzaile.getGE().erabiltzaileaBilatuNAN(NAN);
		Iterator<FilmZerrenda> iter = e.getZerrendak().iterator();
	    while (iter.hasNext()) {
	    	FilmZerrenda b = iter.next();
	    	if (b.getID() == ID) {
	    		iter.remove();
	    		DB_kudeatzailea.getDB().kenduZerrenda(ID);
	        }
	    }
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
		int start = izena.lastIndexOf("(") + 1;
        int end = izena.lastIndexOf(")");
        String urtea = izena.substring(start, end).trim();
        String titulua = izena.substring(0, izena.lastIndexOf("(")).trim();
		Film filma = GF.bilatuIzenaDatarekin(titulua, urtea);
		if (filma == null) {
			System.out.println("Filma ez dago");
		} else {
			FilmZerrenda zerrenda = bilatuZerrenda(ID);
			boolean b = zerrenda.sartuFilma(filma);
			if (!b) {
				DB_kudeatzailea.getDB().ErlazioaFilmZerrendak(ID, filma.getFilmID());
				sartuta = true;
			}
		}
		return sartuta;
	}
	
	public void kenduFilmaZerrendaBaten(int ID, String izena) {
		GestoreFilm GF = GestoreFilm.getKN();
		FilmZerrenda zerrenda = bilatuZerrenda(ID);
		int start = izena.lastIndexOf("(") + 1;
        int end = izena.lastIndexOf(")");
        String urtea = izena.substring(start, end).trim();
        String titulua = izena.substring(0, izena.lastIndexOf("(")).trim();
		Film filma = GF.bilatuIzenaDatarekin(titulua, urtea);
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

	public void print() {
		for (FilmZerrenda f : zerrenda) {
			System.out.println(f.getID());
			System.out.println(f.getIzena());
		}
	}
	
}
