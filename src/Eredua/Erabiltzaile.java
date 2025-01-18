package Eredua;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class Erabiltzaile {
	private String nan;
	private String izena;
	private String abizena;
	private String email;
	private String pasahitza;
	private int admin;
	private int onartuta;
	private List<Alokairua> egindakoAlokairuak;
	private List<FilmZerrenda> filmZerrendak;
	
	public Erabiltzaile(String pNan, String pIzena,String pAbizena, String pEmail, String pPasahitza, int pAdmin, int pOnartuta) {
		nan = pNan;
		izena = pIzena;
		email = pEmail;
		pasahitza = pPasahitza;
		abizena = pAbizena;
		admin = pAdmin;
		onartuta = pOnartuta;
		egindakoAlokairuak = new ArrayList<Alokairua>();
		filmZerrendak = new ArrayList<FilmZerrenda>();
	}
	
	public String getNan() {
		return this.nan;
	}
	
	public String getPasahitza() {
		return this.pasahitza;
	}
	
	public int getAdmin() {
		return this.admin;
	}
	
	public String getIzena() {
		return this.izena;
	}
	
	public int getOnartuta() {
		return this.onartuta;
	}
	
	public String getAbizena() {
		return this.abizena;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setIzena(String pIzena) {
		this.izena = pIzena;
	}
	
	public void setAbizena(String pAbizena) {
		this.abizena = pAbizena;
	}
	
	public void setEmail(String pEmail) {
		this.email = pEmail;
	}
	
	public void setPasahitza(String pPasahitza) {
		this.pasahitza = pPasahitza;
	}
	
	public void Onartu() {
		onartuta = 1;
	}
	
	public List<Alokairua> getEgindakoAlokairuak(){
		return this.egindakoAlokairuak;
	}
	
	public void gehituAlokairua(Alokairua alokairua) {
		egindakoAlokairuak.add(alokairua);
	}
	
	public List<FilmZerrenda> getZerrendak(){
		return this.filmZerrendak;
	}

	public void ZerrendanSartu(FilmZerrenda z) {
		filmZerrendak.add(z);
	}
	
	public int bilatuZerrendaID (String izena) {
		int ID = -1;
		for (FilmZerrenda z : filmZerrendak) {
			if (z.getIzena().equals(izena)) {
				ID = z.getID();
			}
		}
		return ID;
	}
	
	public boolean izenaTestuarekinKointziditu(String text) {
		 if (izena == null || text == null) {
		        return false; 
		    }
		 return izena.trim().equalsIgnoreCase(text.trim());
    }
	
	public boolean filmaAlokatutaDauka(Film film) {
	    for (Alokairua alok : egindakoAlokairuak) {
	        if (alok.filmaKointziditu(film) && alok.alokairuaAktiboDago()) {
	           return true;
	        }
	    }
	    return false;
	}
	
}
