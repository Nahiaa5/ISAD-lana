package Kontroladorea;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Observable;
import Eredua.*;

public class GestoreNagusia extends Observable {

	private static GestoreNagusia nGN = null;
	private boolean kargatuDira = false;
	
	private GestoreNagusia() {}
	
	public static GestoreNagusia getGN() {
		if(nGN==null) {
			nGN=new GestoreNagusia();
			
		}
		return nGN;
	}
	
	public JSONObject getFilmXehetasunak(String filmIzena) {
		JSONObject json = GestoreFilm.getKN().getFilmXehetasunak(filmIzena);
			return json;
	}
	
	public void datuakKargatu() {
		if(!kargatuDira) {
			GestoreFilm.getKN().loadFilmak();
			GestoreFilm.getKN().loadPuntuazioak();
			GestoreErabiltzaile.getGE().loadErabiltzaileak();
			GestoreAlokairu.getGA().loadAlokairuak();
			kargatuDira = true;
		}
	}
	
	public JSONArray getInfoKatalogokoFilmGuztiak() {
		JSONArray array = GestoreFilm.getKN().getInfoKatalogokoFilmGuztiak();
		return array;
	}
	
	public JSONArray getInfoErabiltzaileak() {
		JSONArray array = GestoreErabiltzaile.getGE().getInfoErabiltzaileak();
		return array;
	}
	
	public void getErabiltzaile(String pNan, String pPas) {
		Erabiltzaile e = GestoreErabiltzaile.getGE().erabiltzaileaBilatu(pNan, pPas);
		if (e == null) {
			setChanged();
			notifyObservers("Txarto");
		}
		else {
			GestoreErabiltzaile.getGE().setSaioaNan(e.getNan());
			setChanged();
			notifyObservers(e);
		}		
	}
	
	public void ErabiltzaileBerriaSartu(String pNAN, String pIzena, String pAbizena, String pEmail, String pPasahitza) {
		boolean ondo;
		if(pNAN.isEmpty()|| pIzena.isEmpty() || pAbizena.isEmpty() || pEmail.isEmpty() || pPasahitza.isEmpty()) {
			ondo = false;
		}
		else {
			Erabiltzaile e = new Erabiltzaile(pNAN, pIzena, pAbizena, pEmail, pPasahitza, 0, 0);
			GestoreErabiltzaile.getGE().gehituErabiltzailea(e);
			ondo = true;
		}
		setChanged();
		notifyObservers(ondo);
	}
	
	public JSONArray getInfoEskaerak() {
		JSONArray eskaerak = GestoreErabiltzaile.getGE().getInfoEskaerak();
		return eskaerak;
	}
	
	public void erabiltzaileaOnartu(String nan) {
		GestoreErabiltzaile.getGE().erabiltzaileaOnartu(nan);
	}
	
	public void erabiltzaileaEzabatu(String nan) {
		GestoreErabiltzaile.getGE().erabiltzaileaEzabatu(nan);
	}
	
	public void KZFilmakBilatu(String izena) {
		KatalogoZabalduaKargatu.getnZK().FilmakBilatu(izena);
	}
	
	public void KZXehetasunakBilatu(String izena) {
		KatalogoZabalduaKargatu.getnZK().xehetasunakBilatu(izena);
	}
	
	public void KZBidaliEskaera() {
		KatalogoZabalduaKargatu.getnZK().bidaliEskaera();
	}
	
	public JSONArray getFilmEskaerak() {
		JSONArray eskaerak = GestoreFilm.getKN().getFilmEskaerak();
		return eskaerak;
	}
	
	public void filmaOnartu(String izena) {
		GestoreFilm.getKN().filmaOnartu(izena);
	}
	
	public void filmaEzabatu(String izena) {
		GestoreFilm.getKN().filmaEzabatu(izena);
	}
	
	public void ordenatuFilmaPuntuazioz() {
		GestoreFilm.getKN().ordenatuPuntuazioz();
	}
	
	public Film bilatuFilmaIzenaz(String filmIzena) {
		Film film=GestoreFilm.getKN().bilatuIzenarekin(filmIzena);
		return film;
	}
	
	public void gordePuntuazioa(String NAN, int filmID, int puntuazioa, String iruzkina) {
        GestoreFilm.getKN().gordePuntuazioa(NAN, filmID, puntuazioa, iruzkina);
        setChanged();
        notifyObservers();
    }

	public JSONArray bilaketaEgin(String text) {
		JSONArray json = GestoreFilm.getKN().bilatuFilmKatalogoan(text);
		return json;
	}
	
	public JSONArray bilatzaileanErabiltzaileak(String text) {
		JSONArray erabiltzaileak = GestoreErabiltzaile.getGE().bilatzaileanErabiltzaileak(text);
		return erabiltzaileak;
	}
	
	public void filmaAlokatu(String filmIzena) {
		String erabNAN = GestoreErabiltzaile.getGE().getSaioaNan();
		Film film = GestoreFilm.getKN().bilatuIzenarekin(filmIzena);
		Alokairua alokairua = GestoreAlokairu.getGA().alokairuaEgin(erabNAN, film);
		GestoreErabiltzaile.getGE().alokairuaErabiltzailearenZerrendanGehitu(alokairua);
	}
	
	public void erabiltzaileDatuakAldatu(String pNan, String pIzena, String pAbizena, String pEmail, String pPasahitza) {
		Erabiltzaile e = GestoreErabiltzaile.getGE().erabiltzaileaAurkitu(pNan);
		if(pIzena.isEmpty() || pAbizena.isEmpty() || pEmail.isEmpty() || pPasahitza.isEmpty()) {
			setChanged();
			notifyObservers("Hutsik");
		}
		else {
			e.setIzena(pIzena);
			e.setAbizena(pAbizena);
			e.setEmail(pEmail);
			e.setPasahitza(pPasahitza);
			setChanged();
			notifyObservers("Sartuta");
		}
	}
}
