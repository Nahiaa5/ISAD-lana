package Eredua;

import java.time.LocalDate;

public class Puntuazioa {
	private String NAN;
    private int filmID;
    private int puntuazioa; // 1-5
    private String iruzkina;
    private LocalDate data;

    public Puntuazioa(String pNAN, int pFilmID, int pPuntuazioa, String pIruzkina, LocalDate pData) {
        this.NAN = pNAN;
        this.filmID = pFilmID;
        this.puntuazioa = pPuntuazioa;
        this.iruzkina = pIruzkina;
        this.data = pData;
    }
    
    public String getNAN() {
    	return this.NAN;
    }
    
    public int getFilmID() {
    	return this.filmID;
    }
    
    public int getPuntuazioa() {
    	return this.puntuazioa;
    }
    
    public void setPuntuazioa(int pPuntu) {
        if (pPuntu >= 1 && pPuntu <= 5) {
            this.puntuazioa = pPuntu;
        } else {
            throw new IllegalArgumentException("Puntuazioa 1-5 tartean egon behar da.");
        }
    }
    
    public String getIruzkina() {
    	return this.iruzkina;
    }
    
    public void setIruzkina(String pIruzkin) {
    	this.iruzkina=pIruzkin;
    }
    
    public LocalDate getData(){
    	return this.data;
    }
    
    public boolean puntuazioaDa(String pNAN, int pFilmID) {
    	if(this.NAN.equals(pNAN) && this.filmID==pFilmID) {
    		return true;
    	}else {
    		return false;
    	}
    }
}
