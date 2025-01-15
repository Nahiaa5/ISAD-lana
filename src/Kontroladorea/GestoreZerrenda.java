package Kontroladorea;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

import Bista.FilmKatalogoZabaldua;
import Eredua.Film;
import Eredua.FilmZerrenda;

public class GestoreZerrenda {
	private static GestoreZerrenda nZZ;
	private List<FilmZerrenda> zerrenda;
	private Iterator iter;
	private int kont = 0;
	
	private GestoreZerrenda(){
		zerrenda = new ArrayList<FilmZerrenda>();
	}
	
	public static GestoreZerrenda getnZZ(){
		if(nZZ==null) {
			nZZ=new GestoreZerrenda();
		}
		return nZZ;
	}
	
	public int getKont() {
		return kont;
	}
	
	public void sortuZerrendaBerria(String izena, Boolean pribazitatea ) {
		FilmZerrenda berria = new FilmZerrenda(kont,izena, pribazitatea);
		zerrenda.add(berria);
		kont++;
	}
	
	public FilmZerrenda bilatuZerrenda(int ZerrendaID) {
		for (FilmZerrenda bilatzailea : zerrenda){
			if (bilatzailea.getID() == ZerrendaID) {
				return bilatzailea;
			}
		}
		return null;
	}
	
	public void sartuFilmaZerrendaBaten(int ID, String datuak) {
		GestoreFilm GF = GestoreFilm.getKN();
		GestoreKatalogoZabaldua KZK = GestoreKatalogoZabaldua.getnZK();
		JSONObject xehetasunak = KZK.xehetasunakBilatu(datuak);
		Film filma = GF.bilatuFilma(xehetasunak);
		FilmZerrenda zerrenda = bilatuZerrenda(ID);
		zerrenda.sartuFilma(filma);
	}
	
	public void kenduFilmaZerrendaBaten(int ID, String izena) {
		GestoreFilm GF = GestoreFilm.getKN();
		Film filma = GF.bilatuIzenarekin(izena);
		FilmZerrenda zerrenda = bilatuZerrenda(ID);
		zerrenda.kenduFilma(filma);
	}
	
	public void aldatuXehetasunak(int ID, String izena, Boolean pribazitatea) {
		FilmZerrenda zerrenda = bilatuZerrenda(ID);
		zerrenda.setIzena(izena);
		zerrenda.setPribazitatea(pribazitatea);
	}
	
}
