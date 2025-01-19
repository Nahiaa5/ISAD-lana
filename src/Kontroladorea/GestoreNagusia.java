package Kontroladorea;

import java.time.LocalDate;

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
			GestorePuntuazio.getGP().loadBalorazioak();
			GestoreFilm.getKN().loadPuntuazioak();
			GestoreErabiltzaile.getGE().loadErabiltzaileak();
			GestoreAlokairu.getGA().loadAlokairuak();
			GestoreZerrenda.getnZZ().loadZerrenda();
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
		if (e == null || e.getOnartuta()==0) {
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
		boolean ondo = false;
		if(pNAN.isEmpty()|| pIzena.isEmpty() || pAbizena.isEmpty() || pEmail.isEmpty() || pPasahitza.isEmpty()) {
			ondo = false;
		}
		else {
			Erabiltzaile e = new Erabiltzaile(pNAN, pIzena, pAbizena, pEmail, pPasahitza, 0, 0);
			boolean erabEzExistitu = GestoreErabiltzaile.getGE().gehituErabiltzailea(e);
			if (erabEzExistitu) {
				ondo = true;
			} else {
				ondo = false;
			}
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
		DB_kudeatzailea.getDB().erabiltzaileaOnartu(nan);
	}
	
	public void erabiltzaileaEzabatu(String nan) {
		GestoreErabiltzaile.getGE().erabiltzaileaEzabatu(nan);
		DB_kudeatzailea.getDB().erabiltzaileaEzabatu(nan);
	}
	
	public JSONArray KZFilmakBilatu(String izena) {
		JSONArray filmak = GestoreKatalogoZabaldua.getnZK().FilmakBilatu(izena);
		return filmak;
	}
	
	public JSONObject KZXehetasunakBilatu(String izena) {
		JSONObject datuak = GestoreKatalogoZabaldua.getnZK().xehetasunakErakutsi(izena);
		return datuak;
	}
	
	public void KZXehetasunakErakutsi(String izena) {
		GestoreKatalogoZabaldua.getnZK().xehetasunakErakutsi(izena);
	}
	
	public Boolean KZBidaliEskaera() {
		Boolean bidalita = GestoreKatalogoZabaldua.getnZK().bidaliEskaera();
		return bidalita;
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
	
	public void ordenatuFilmakPuntuazioz() {
		GestoreFilm.getKN().ordenatuPuntuazioz();
	}
	
	public Film bilatuFilmaIzenaz(String filmIzena) {
		Film film=GestoreFilm.getKN().bilatuIzenarekin(filmIzena);
		return film;
	}
	
	public void gordePuntuazioa(String NAN, int filmID, int puntu, String iruzkina, LocalDate data) {	
		Puntuazioa p=new Puntuazioa(NAN, filmID, puntu, iruzkina, data);
		GestorePuntuazio.getGP().gordePuntuazioa(p);
		GestoreFilm.getKN().gordePuntuazioaFilman(p, filmID);
		setChanged();
        notifyObservers();
    }
	
	public void kalkulatuPuntuazioaBb(String izenburua, String urtea) {
		Film f=GestoreFilm.getKN().bilatuIzenaDatarekin(izenburua, urtea);
		f.kalkulatuPuntuBb();
		int filmID=f.getFilmID();
		double puntuBb=f.getPuntuazioaBb();
		DB_kudeatzailea.getDB().gordePuntuazioBb(filmID, puntuBb);
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
	
	public boolean filmaAlokatu(String filmIzena) {
		Film film = GestoreFilm.getKN().bilatuIzenarekin(filmIzena);
		String erabNAN = GestoreErabiltzaile.getGE().getSaioaNan();
		boolean alokatutaJada = GestoreErabiltzaile.getGE().alokatutaDaukaJada(erabNAN, film);
		if (!alokatutaJada) {
			Alokairua alokairua = GestoreAlokairu.getGA().alokairuaEgin(erabNAN, film);
			GestoreErabiltzaile.getGE().alokairuaErabiltzailearenZerrendanGehitu(alokairua);
		}
		return !alokatutaJada;
	}
	
	public boolean erabiltzaileakAlokatuDu(String NAN, int filmID) {
		Erabiltzaile erab = GestoreErabiltzaile.getGE().getErabiltzaileByNAN(NAN);

		if(erab!=null) {
			for(Alokairua alok: erab.getEgindakoAlokairuak()) {
				if(alok.getFilm().getFilmID()==filmID) {
					return true;
				}
			}
		}
		return false;
	}
	
	public String getSaioaNAN() {
		return GestoreErabiltzaile.getGE().getSaioaNan();
	}
	
	public void erabiltzaileDatuakAldatu(String pNan, String pIzena, String pAbizena, String pEmail, String pPasahitza) {
		Erabiltzaile e = GestoreErabiltzaile.getGE().getErabiltzaileByNAN(pNan);
		if(pIzena.isEmpty() || pAbizena.isEmpty() || pEmail.isEmpty() || pPasahitza.isEmpty()) {
			setChanged();
			notifyObservers("Hutsik");
		}
		else {
			e.setIzena(pIzena);
			e.setAbizena(pAbizena);
			e.setEmail(pEmail);
			e.setPasahitza(pPasahitza);
			GestoreErabiltzaile.getGE().erabiltzailearenDatuakDBEguneratu(pNan, pIzena, pAbizena, pEmail, pPasahitza);
			setChanged();
			notifyObservers("Sartuta");
		}
	}
	
	public boolean alokairuaDauka(String filmIzena) {
		Film film = GestoreFilm.getKN().bilatuIzenarekin(filmIzena);
		String erabNAN = GestoreErabiltzaile.getGE().getSaioaNan();
		return GestoreErabiltzaile.getGE().alokatutaDaukaJada(erabNAN, film);
	}
	
	public String filmaPantailaratu(String filmIzena) {
		Film film = GestoreFilm.getKN().bilatuIzenarekin(filmIzena);
		return GestoreFilm.getKN().getFilmarenPath(film);
	}
}
