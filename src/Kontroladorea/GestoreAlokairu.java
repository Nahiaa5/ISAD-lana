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
	
	public void loadAlokairuak() {
	    this.alokairuak=DB_kudeatzailea.getDB().kargatuAlokairuak();
	}
	
}
