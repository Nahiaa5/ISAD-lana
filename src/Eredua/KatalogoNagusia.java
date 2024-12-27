package Eredua;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;
import java.util.stream.Collectors;

public class KatalogoNagusia extends Observable {
	private List<Film> filmak;
	private List<Film> jatorrizkoFilmak;
	private static KatalogoNagusia nKN=null;
	
	private KatalogoNagusia() {
		filmak=new ArrayList<>();
	}
	
	public static KatalogoNagusia getKN() {
		if(nKN==null) {
			nKN=new KatalogoNagusia();
			
		}
		return nKN;
	}
	
	public List<Film> getFilmak(){
		return this.filmak;//
	}
	
	public void filmaBilatu(String izena){
		if(izena==null || izena.trim().isEmpty()) {
			this.filmak=new ArrayList<>(jatorrizkoFilmak);
		}else {
			List<Film> emaitza=jatorrizkoFilmak.stream()
					.filter(film->film.getIzenburua().toLowerCase().contains(izena))
					.collect(Collectors.toList());
			if(emaitza.isEmpty()) {
				this.filmak=new ArrayList<>();
			}else {
				this.filmak=new ArrayList<>(emaitza);
			}
		}
		setChanged();
		notifyObservers();
	}
	
	public void loadFilmak(DB_kudeatzailea pDBK) {
	    this.filmak=pDBK.kargatuFilmak();
	    this.jatorrizkoFilmak=new ArrayList<>(filmak);
	    setChanged();
	    notifyObservers();
	}
	
	public void ordenatuPuntuazioz() {
		this.filmak.sort(Comparator.comparingDouble(Film::getPuntuazioaBb).reversed());
		setChanged();
		notifyObservers();
	}
}
