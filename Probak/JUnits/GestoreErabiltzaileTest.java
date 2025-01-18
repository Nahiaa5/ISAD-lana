package JUnits;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Eredua.Erabiltzaile;
import Eredua.Film;
import Eredua.HasData;
import Kontroladorea.GestoreErabiltzaile;
import Eredua.Alokairua;

class GestoreErabiltzaileTest {
	private GestoreErabiltzaile gestoreErabiltzaile;
	private Erabiltzaile erab;
	private Alokairua alok;
	private Film film;

	@BeforeEach
	void setUp() throws Exception {
		gestoreErabiltzaile = GestoreErabiltzaile.getGE();
		
		erab = new Erabiltzaile("12345678Z", "Manolito", "Fernandez", "manolito@gmail.com", "1234", 0, 1);
		film = new Film(1, "La la land", "Ryan Gosling", "2021", "Musikala", "Damien Chazelle", "79224675A", true, 4.00, "resources/LaLaLand.mp4");
		HasData hasData = new HasData(LocalDate.now());
		LocalDate bukData =  LocalDate.of(2025, 1, 19);
		alok = new Alokairua(film, hasData, bukData);
		
		gestoreErabiltzaile.getErabiltzaileak().add(erab);
        gestoreErabiltzaile.setSaioaNan("12345678Z");
	}

	@AfterEach
	void tearDown() throws Exception {
		//Erabiltzaile-zerrenda hustu
	    gestoreErabiltzaile.getErabiltzaileak().clear();
	    
	    //Saioa itxi
	    gestoreErabiltzaile.setSaioaNan(null);

	    //Instantzia guztiak ezabatu
	    erab = null;
	    film = null;
	    alok = null;
	}

	@Test
	void testAlokatutaDaukaJada() {
		//Erabiltzaileak ez dauka alokairua eginda film horretarako. Metodoari erabiltzailearen NAN eta filma ematen dizkiogu
		boolean erantzuna = gestoreErabiltzaile.alokatutaDaukaJada("12345678Z", film);
		assertFalse(erantzuna);
		//Erabiltzaileari filmaren alokairua gehitzen diogu
		erab.gehituAlokairua(alok);
		//Erabiltzaileak alokairua eginda dauka
		erantzuna = gestoreErabiltzaile.alokatutaDaukaJada("12345678Z", film);
		assertTrue(erantzuna);
	}
	
	@Test
	void testAlokairuaErabiltzailearenZerrendanGehitu() {
		//Erabiltzaileak ez dauka alokairua eginda film horretarako eta badakigu (setUp metodoan konfiguratu dugunaren arabera) erabiltzaileak 'alok' ez badauka bere zerrendan hutsik egongo dela
		assertTrue(erab.getEgindakoAlokairuak().isEmpty());
		//Alokairua gehitu
		gestoreErabiltzaile.alokairuaErabiltzailearenZerrendanGehitu(alok);
		//Orain zerrenda ez da hutsik egongo, 'alok' gehitu diogulako
		assertFalse(erab.getEgindakoAlokairuak().isEmpty());
	}
	
	@Test
	void testGetErabiltzaileByNAN() {
		//Erabiltzailea ez da zerrendan existitzen, metodoak null itzuli beharko luke
		assertNull(gestoreErabiltzaile.getErabiltzaileByNAN("79224266F"));
		//Erabiltzailea zerrendan existitzen da, metodoak Erabiltzaile objektua itzuli beharko luke
		assertEquals(erab, gestoreErabiltzaile.getErabiltzaileByNAN("12345678Z"));
	}

}
