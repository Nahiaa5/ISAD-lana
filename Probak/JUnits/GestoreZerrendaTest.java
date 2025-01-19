package JUnits;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Eredua.Erabiltzaile;
import Eredua.Film;
import Eredua.FilmZerrenda;
import Kontroladorea.GestoreErabiltzaile;
import Kontroladorea.GestoreFilm;
import Kontroladorea.GestoreZerrenda;

class GestoreZerrendaTest {

	private GestoreZerrenda gestorea;
	private Erabiltzaile erab;
	private Film film;
	
	@BeforeEach
	void setUp() throws Exception {
		gestorea = GestoreZerrenda.getnZZ();
		GestoreErabiltzaile.getGE().loadErabiltzaileak();
		erab = GestoreErabiltzaile.getGE().erabiltzaileaBilatuNAN("12345678Z");
		film = new Film(2, "Interstellar", "Matthew McCounaghey, Jessica Chastain", "2014", "Zientzia fikzioa", "Christopher Nolan", "79224675A", true, 2.50, "resources/Interstellar.mp4");
		GestoreFilm.getKN().getFilmak().add(film);
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
		assertFalse(gestorea.getZerrendak().isEmpty()); // Gestorea ez dago hutsik
		gestorea.ezabatuZerrenda(ID, "12345678Z"); //Zerrenda ezabatu gestoretik
		assertTrue(gestorea.getZerrendak().isEmpty()); // Gestorea hutsik dago
		
	}
	
	@Test
	void BilatuZerrendaTest() {
		String izena = "Musikala";
		Boolean pribazitatea = true;
		String NAN = "12345678Z";
		int ID = 7;
		FilmZerrenda f = new FilmZerrenda(ID, izena, pribazitatea, NAN);
		gestorea.kargatuZerrenda(f); //Sortutako zerrenda gestorean sortu
		assertEquals(gestorea.bilatuZerrenda(ID),f); // Metodoarekin bilatzen dena eta sartutakoa berdinak dira
		
		gestorea.ezabatuZerrenda(ID, "12345678Z"); //datu  basean ez pilatzeko
		
	}
	
	@Test
	void sartuFilmaZerrendaBatenTest() {
		String izena = "Musikala";
		Boolean pribazitatea = true;
		String NAN = "12345678Z";
		int ID = gestorea.sortuZerrendaBerria(izena, pribazitatea, NAN); //Zerrenda sortu
		FilmZerrenda z = gestorea.bilatuZerrenda(ID);
		
		assertTrue(z.getZerrenda().isEmpty()); // Zerrendan ez dago filmik
		gestorea.sartuFilmaZerrendaBaten(ID, "Harry Potter (2001)"); //Ez dago filma
		assertTrue(z.getZerrenda().isEmpty()); // Zerrendan ez dago filmik
		
		assertTrue(z.getZerrenda().isEmpty()); // Zerrendan ez dago filmik
		gestorea.sartuFilmaZerrendaBaten(ID, "Interstellar (2014)");
		assertFalse(z.getZerrenda().isEmpty()); // Zerrenda ez dago hutsa, beraz filma dauka
		
		gestorea.sartuFilmaZerrendaBaten(ID, "Interstellar (2014)");
		assertEquals(gestorea.getZerrendak().size(),1); //Elementu bat bakarrik dago, errepikatuta zegoelako
		
		gestorea.ezabatuZerrenda(ID, NAN); //datu  basean ez pilatzeko
	}
	
	@Test
	void kenduFilmaZerrendaBatenTest() {
		String izena = "Musikala";
		Boolean pribazitatea = true;
		String NAN = "12345678Z";
		int ID = gestorea.sortuZerrendaBerria(izena, pribazitatea, NAN); //Zerrenda sortu
		FilmZerrenda z = gestorea.bilatuZerrenda(ID);
		
		gestorea.sartuFilmaZerrendaBaten(ID, "Interstellar (2014)");
		assertFalse(z.getZerrenda().isEmpty()); // Zerrenda ez dago hutsa, beraz filma dauka
		
		gestorea.kenduFilmaZerrendaBaten(ID, "Interstellar (2014)"); //Filma kentzen da zerrendatik
		assertTrue(z.getZerrenda().isEmpty()); // Zerrenda hutsik dago
		
		gestorea.ezabatuZerrenda(ID, NAN); //datu  basean ez pilatzeko
	}
	
	@Test
	void aldatuXehetasunakTest() {
		String izena = "Musikala";
		Boolean pribazitatea = true;
		String NAN = "12345678Z";
		int ID = gestorea.sortuZerrendaBerria(izena, pribazitatea, NAN); //Zerrenda sortu
		FilmZerrenda z = gestorea.bilatuZerrenda(ID);
		
		String izenBerria = "Romance";
		Boolean pribazitateaBerria = false;
		assertNotEquals(z.getIzena(), izenBerria);//Zerrendaren izena ez da Romance
		assertNotEquals(z.getPribazitatea(), pribazitateaBerria);//Zerrenda ez da pribatua
		gestorea.aldatuXehetasunak(ID, izenBerria, pribazitateaBerria); // Zerrendaren izena aldatu
		
		assertEquals(z.getIzena(), izenBerria); //Izen berria Romance da
		assertEquals(z.getPribazitatea(), pribazitateaBerria); //Zerrenda pribatua da
		
		gestorea.ezabatuZerrenda(ID, NAN); //datu  basean ez pilatzeko
	}

}
