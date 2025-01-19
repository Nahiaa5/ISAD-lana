package JUnits;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Eredua.Erabiltzaile;
import Kontroladorea.GestoreErabiltzaile;
import Kontroladorea.GestoreZerrenda;

class GestoreZerrendaTest {

	private GestoreZerrenda gestorea;
	private Erabiltzaile erab;
	
	@BeforeEach
	void setUp() throws Exception {
		gestorea = GestoreZerrenda.getnZZ();
		GestoreErabiltzaile.getGE().loadErabiltzaileak();
		erab = GestoreErabiltzaile.getGE().erabiltzaileaBilatuNAN("12345678Z");
		
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
		String NAN = "12345678Z";
		int ID = gestorea.sortuZerrendaBerria(izena, pribazitatea, NAN);
		/* Zerrenda berria sortzen da eta metodoak zerrendaren ID-a bueltatzen du,
		 * zerrenda jadanik badago -1 bueltatzen du. Kasu honetan ez da -1.*/
		assertNotEquals(ID,-1);
		// Zerrenda sartu denez berriro sartzean -1 bueltatzen du jadanik dagoelako
		assertEquals(gestorea.sortuZerrendaBerria(izena, pribazitatea, NAN),-1);
		
		gestorea.ezabatuZerrenda(ID, NAN); // datu basean ez pilatzeko
	}
	
	@Test
	void EzabatuZerrendaTest() {
		gestorea.getZerrendak().clear(); // Zerrenda garbitu
		assertTrue(gestorea.getZerrendak().isEmpty()); //Hutsik dagoela konprobatu
		
		String izena = "Musikala";
		Boolean pribazitatea = true;
		String NAN = "12345678Z";
		int ID = gestorea.sortuZerrendaBerria(izena, pribazitatea, NAN); //Zerrenda sartu
		assertFalse(gestorea.getZerrendak().isEmpty());
		gestorea.ezabatuZerrenda(ID, "12345678Z");
		assertTrue(gestorea.getZerrendak().isEmpty());
		
	}

}
