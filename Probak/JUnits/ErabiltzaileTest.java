package JUnits;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Eredua.Erabiltzaile;
import Eredua.Film;
import Eredua.HasData;
import Eredua.Alokairua;

class ErabiltzaileTest {
	private Erabiltzaile erab;
	private Alokairua alok;
	private Film film;

	@BeforeEach
	void setUp() throws Exception {
		erab = new Erabiltzaile("12345678Z", "Manolito", "Fernandez", "manolito@gmail.com", "1234", 0, 1);
		
		film = new Film(1, "La la land", "Ryan Gosling", "2021", "Musikala", "Damien Chazelle", "79224675A", true, 4.00, "resources/LaLaLand.mp4");
		HasData hasData = new HasData(LocalDate.now());
		LocalDate bukData =  LocalDate.of(2025, 1, 19);
		alok = new Alokairua(film, hasData, bukData);
	}

	@AfterEach
	void tearDown() throws Exception {
		//Instantzia guztiak ezabatu
	    erab = null;
	    film = null;
	    alok = null;
	}
	
	@Test
	void testGehituAlokairua() {
		//Erabiltzaileak ez dauka alokairurik film horretarako edo beste edozeinerako (setUp metodoan definitu dugunaren arabera), beraz bere alokairu zerrenda hutsik egongo da
		assertTrue(erab.getEgindakoAlokairuak().isEmpty());
		//Alokairua gehitu
		erab.gehituAlokairua(alok);
		//Orain zerrenda ez da hutsik egongo, 'alok' gehitu diogulako
		assertFalse(erab.getEgindakoAlokairuak().isEmpty());
	}

	@Test
	void testFilmaAlokatutaDauka() {
		//Erabiltzaileak ez dauka alokairurik film horrentzako
		assertFalse(erab.filmaAlokatutaDauka(film));
		//Aurreko metodoan gehituAlokairua metodoa egiaztatu dugunez, hemen erabiltzen dugu alokairua gehitzeko
		erab.gehituAlokairua(alok);
		//Erabiltzaileak alokairua dauka film horrentzako
		assertTrue(erab.filmaAlokatutaDauka(film));
	}

}
