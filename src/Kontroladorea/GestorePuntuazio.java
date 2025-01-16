package Kontroladorea;

import java.util.ArrayList;
import java.util.List;

import Eredua.DB_kudeatzailea;
import Eredua.Puntuazioa;

public class GestorePuntuazio {
	private List<Puntuazioa> balorazioak;
	private static GestorePuntuazio nGP=null;
	
	private GestorePuntuazio() {
		balorazioak=new ArrayList<>();
	}
	
	public static GestorePuntuazio getGP() {
		if(nGP==null) {
			nGP=new GestorePuntuazio();
			
		}
		return nGP;
	}
	
	public List<Puntuazioa> getBalorazioak(){
		return this.balorazioak;
	}
	
	public void loadBalorazioak() {
		this.balorazioak=DB_kudeatzailea.getDB().kargatuPuntuazioak();
	}
	
	public boolean badagoPuntuazioa(String NAN, int filmID) {
		for(Puntuazioa p: balorazioak) {
			if(p.puntuazioaDa(NAN, filmID)) {
				return true;
			}
		}
		return false;
	}
	
	public void gordePuntuazioa(Puntuazioa pPuntuazioa) {
		String NAN=pPuntuazioa.getNAN();
		int filmID=pPuntuazioa.getFilmID();
		int puntu=pPuntuazioa.getPuntuazioa();
		String iruzkina=pPuntuazioa.getIruzkina();
		if(badagoPuntuazioa(NAN, filmID)) {
			for(Puntuazioa p: balorazioak) {
				if(p.puntuazioaDa(NAN, filmID)) {
					p.setPuntuazioa(puntu);
					p.setIruzkina(iruzkina);
				}
			}
			DB_kudeatzailea.getDB().eguneratuPuntuazioa(pPuntuazioa);
		}else {
			this.balorazioak.add(pPuntuazioa);
			DB_kudeatzailea.getDB().gordePuntuazioa(pPuntuazioa);
		}
	}
}
