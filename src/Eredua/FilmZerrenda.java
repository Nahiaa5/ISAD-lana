package Eredua;

import java.util.List;
import java.util.UUID;

public class FilmZerrenda {

	private int ZerrendaID;
	private String izena;
	private boolean pribazitatea;
	//private Erabiltzaile jabea;
	private List<Film> FilmZerrenda;
	
	public FilmZerrenda(int ID, String izena, boolean pribazitatea ) {
		this.ZerrendaID = Integer.parseInt(UUID.randomUUID().toString());
		this.izena = izena;
		this.pribazitatea = pribazitatea;
	}
}
