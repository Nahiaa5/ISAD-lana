package Eredua;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import Kontroladorea.GestoreErabiltzaile;

public class Film extends Observable{
    private int filmID;
    private String izenburua; 
    private String aktoreak; 
    private String urtea; 
    private String generoa; 
    private String zuzendaria; 
    private String adminNAN; 
    private boolean katalogoan; 
    private double puntuazioaBb;
    private List<Puntuazioa> balorazioak;
    private String path;
    
    public Film(int filmID, String izenburua, String aktoreak, String urtea, String generoa, String zuzendaria, String adminNAN, boolean katalogoan, double puntuazioaBb, String path) {
        this.filmID = filmID;
        this.izenburua = izenburua;
        this.aktoreak = aktoreak;
        this.urtea = urtea;
        this.generoa = generoa;
        this.zuzendaria = zuzendaria;
        this.adminNAN = adminNAN;
        this.katalogoan = katalogoan;
        this.puntuazioaBb = puntuazioaBb;
        this.balorazioak = new ArrayList<>();
        this.path = path;
    }

    public int getFilmID() {
        return filmID;
    }

    public void setFilmID(int filmID) {
        this.filmID = filmID;
    }

    public String getIzenburua() {
        return izenburua;
    }

    public void setIzenburua(String izenburua) {
        this.izenburua = izenburua;
        setChanged();
        notifyObservers();
    }
    
    public boolean izenburuaTestuarekinKointziditu(String text) {
    	return izenburua.toLowerCase().contains(text.toLowerCase());
    }

    public String getAktoreak() {
        return aktoreak;
    }

    public void setAktoreak(String aktoreak) {
        this.aktoreak = aktoreak;
    }

    public String getUrtea() {
        return urtea;
    }

    public void setUrtea(String urtea) {
        this.urtea = urtea;
    }

    public String getGeneroa() {
        return generoa;
    }

    public void setGeneroa(String generoa) {
        this.generoa = generoa;
    }

    public String getZuzendaria() {
        return zuzendaria;
    }

    public void setZuzendaria(String zuzendaria) {
        this.zuzendaria = zuzendaria;
    }

    public String getErabiltzaileNAN() {
        return adminNAN;
    }

    public void setErabiltzaileNAN(String erabiltzaileNAN) {
        this.adminNAN = erabiltzaileNAN;
    }

    public boolean getKatalogoan() {
        return katalogoan;
    }

    public void setKatalogoan(boolean katalogoan) {
        this.katalogoan = katalogoan;
    }
    
    public double getPuntuazioaBb() {
    	return this.puntuazioaBb;
    }
    public void setPuntuazioaBb(double puntuBb) {
    	this.puntuazioaBb = puntuBb;
    	setChanged();
    	notifyObservers();
    }
    
    public List<Puntuazioa> getBalorazioak(){
    	return this.balorazioak;
    }
    
    public void setBalorazioak(ArrayList pBalorazioak){
    	this.balorazioak=pBalorazioak;
    }
    
    public List<String> getIruzkinak(){
    	List<String> iruzkinak = new ArrayList<>();
    	for(Puntuazioa p: this.balorazioak) {
    		iruzkinak.add(p.getIruzkina());
    	}
    	return iruzkinak;
    }
    
    public void gordeIruzkinak(DB_kudeatzailea pDBK){
    	for(Puntuazioa p: this.balorazioak) {
    		pDBK.gordePuntuazioa(p);
    	}
		setChanged();
		notifyObservers();
    }
   
    public void kalkulatuPuntuBb() {
        if(balorazioak ==null || balorazioak.isEmpty()){
        	this.puntuazioaBb=0.0;
        }else {
        	double batura = 0.0;
        	for(Puntuazioa balorazio: balorazioak) {
        		batura += balorazio.getPuntuazioa();
        	}
        	this.puntuazioaBb=batura/balorazioak.size();
        }
    }


    @Override
    public String toString() {
        return "Film{" +
                "filmID=" + filmID +
                ", izenburua='" + izenburua + '\'' +
                ", aktoreak='" + aktoreak + '\'' +
                ", urtea=" + urtea +
                ", generoa='" + generoa + '\'' +
                ", zuzendaria='" + zuzendaria + '\'' +
                ", adminNAN='" + adminNAN + '\'' +
                ", katalogoan=" + katalogoan +
                ", Batez besteko puntuazioa=" + puntuazioaBb +
                '}';
    }
    
    public void onartu() {
    	katalogoan = true;
    	adminNAN=GestoreErabiltzaile.getGE().getSaioaNan();
    }
}
