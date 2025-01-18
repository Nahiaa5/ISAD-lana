package Kontroladorea;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import Eredua.*;

public class GestoreAlokairu {

	private List<Alokairua> alokairuak;
	private static GestoreAlokairu nGA = null;
	
	private GestoreAlokairu() {
		alokairuak=new ArrayList<>();
	}
	
	public static GestoreAlokairu getGA() {
		if(nGA == null) {
			nGA = new GestoreAlokairu();
			
		}
		return nGA;
	}
	
	public List<Alokairua> getAlokairuak() {
		return this.alokairuak;
	}
	
	public void loadAlokairuak() {
	    this.alokairuak=DB_kudeatzailea.getDB().kargatuAlokairuak();
	}
	
	public Alokairua alokairuaEgin(String erabNAN, Film film) {
		HasData hasData = new HasData(LocalDate.now());
		LocalDate bukData = hasData.kalkulatuBiEgun();
		
		Alokairua alok = new Alokairua(film, hasData, bukData);
		
		alokairuak.add(alok);
		
		DB_kudeatzailea.getDB().alokairuaGorde(erabNAN, film.getFilmID(), hasData.getData(), bukData);
		
		return alok;
	}
	
}