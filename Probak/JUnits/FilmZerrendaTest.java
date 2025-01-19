package JUnits;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Eredua.Film;
import Eredua.FilmZerrenda;

class FilmZerrendaTest {
	private Film film1;
	private Film film2;
	private FilmZerrenda zerrenda;
	
	@BeforeEach
	void setUp() throws Exception {
		film1 = new Film(1, "La la land", "Ryan Gosling", "2021", "Musikala", "Damien Chazelle", "79224675A", true, 4.00, "resources/LaLaLand.mp4");
		film2 = new Film(2, "Interstellar", "Matthew McConaughey, Jessica Chastain", "2014", "Zientzia fikzioa", "Christopher Nolan", "79224675A", true, 2.50, "resources/Interstellar.mp4");
		zerrenda = new FilmZerrenda(0, "Musikalak", true, "12345678Z");
		
	}

	@AfterEach
	void tearDown() throws Exception {
		//Instantzia guztiak ezabatu
	    film1 = null;
	    film2 = null;
	    zerrenda = null;
	}
	
	@Test
	void SartuFilmatest() {
		assertTrue(zerrenda.getZerrenda().isEmpty()); //Zerrenda film barik hutsik dago
		assertFalse(zerrenda.sartuFilma(film1)); //Film bat sartzen da eta ez dagoenez badago false da
		assertFalse(zerrenda.getZerrenda().isEmpty()); //Zerrenda ez dago hutsik
		
		assertTrue(zerrenda.sartuFilma(film1)); //Filma jadanik badago beraz badago true eta ez da sartzen
	}
	
	@Test
	void bilatuFilmaTest() {
		zerrenda.sartuFilma(film1); // Filma sartu zerrendan
		//Zerrendan index 0: lehenengo filma, eta film1: sartutako filma berdinak dira
		assertTrue(zerrenda.bilatuFilma(0).equals(film1));
		
		zerrenda.sartuFilma(film2);
		assertTrue(zerrenda.bilatuFilma(1).equals(film2));
	}
	
	@Test
	void KenduFilmatest() {
		zerrenda.sartuFilma(film1); //Film bat sartzen da
		assertFalse(zerrenda.getZerrenda().isEmpty()); //Zerrenda ez dago hutsik
		zerrenda.kenduFilma(film1); //Filma kentzen da
		assertTrue(zerrenda.getZerrenda().isEmpty()); //Zerrenda hutsik dago
	}
	
	@Test
	void filmenIzenaktest() {
		zerrenda.sartuFilma(film1); //Filmak sartzen dira
		zerrenda.sartuFilma(film2);
		
		String izenburua1 = film1.getIzenburua(); //Filmaren izenburua
		String urtea1 = film1.getUrtea(); // Filmaren urtea
		
		/*Izenen ArrayList-aren 1 elementua (index 0) sartutako filmaren
		 izenburua eta urtearen berdina da*/
		assertTrue(zerrenda.filmenIzenUrte().get(0).equals(izenburua1 + " (" + urtea1 + ")"));
		
		String izenburua2 = film2.getIzenburua();
		String urtea2 = film2.getUrtea();
		assertTrue(zerrenda.filmenIzenUrte().get(1).equals(izenburua2 + " (" + urtea2 + ")"));
	}
}