package Eredua;

import java.util.List;
import java.util.ArrayList;

public class Erabiltzaile {
	private String nan;
	private String izena;
	private String abizena;
	private String email;
	private String pasahitza;
	private int admin;
	private int onartuta;
	private List<Alokairua> egindakoAlokairuak;
	
	public Erabiltzaile(String pNan, String pIzena,String pAbizena, String pEmail, String pPasahitza, int pAdmin, int pOnartuta) {
		nan = pNan;
		izena = pIzena;
		email = pEmail;
		pasahitza = pPasahitza;
		abizena = pAbizena;
		admin = pAdmin;
		onartuta = pOnartuta;
		egindakoAlokairuak = new ArrayList<Alokairua>();
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
