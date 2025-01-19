package JUnits;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Eredua.DB_kudeatzailea;
import Eredua.Erabiltzaile;
import Eredua.FilmZerrenda;
import Kontroladorea.GestoreErabiltzaile;
import Kontroladorea.GestoreZerrenda;

class GestoreZerrendaTest {

	private GestoreZerrenda gestorea;
	private Erabiltzaile erab;
	private FilmZerrenda zerrenda1;
	private FilmZerrenda zerrenda2;
	
	@BeforeEach
	void setUp() throws Exception {
		gestorea = GestoreZerrenda.getnZZ();
		
		erab = new Erabiltzaile("00000000Z", "Eider", "Santamaria", "e@gmail.com", "1234", 0, 1);
		GestoreErabiltzaile.getGE().gehituErabiltzailea(erab);
		
		zerrenda1 = new FilmZerrenda(0, "Musikalak", true, "12345678Z");
		
	}
	
	@AfterEach
	void tearDown() throws Exception {
		//Instantzia guztiak ezabatu
		gestorea = null;
	}
	
	@Test
	void SortuZerrendaTest() {
		String izena = "Drama";
		Boolean pribazitatea = true;
		String NAN = "00000000Z";
		
		/* Zerrenda berria sortzen da eta metodoak zerrendaren ID-a bueltatzen du,
		 * zerrenda jadanik badago -1 bueltatzen du. Kasu honetan ez da -1.*/
		assertNotEquals(gestorea.sortuZerrendaBerria(izena, pribazitatea, NAN),-1);
		// Zerrenda sartu denez berriro sartzean -1 bueltatzen du jadanik dagoelako
		assertEquals(gestorea.sortuZerrendaBerria(izena, pribazitatea, NAN),-1);
		
	}

}
