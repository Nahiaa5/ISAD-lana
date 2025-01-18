package JUnits;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Eredua.Film;

class FilmTest {
	private Film film;

	@BeforeEach
	void setUp() throws Exception {
		film = new Film(1, "La la land", "Ryan Gosling", "2021", "Musikala", "Damien Chazelle", "79224675A", true, 4.00, "resources/LaLaLand.mp4");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testIzenburuaTestuarekinKointziditu() {
		//Izenburua kointziditzen du
		boolean erantzuna = film.izenburuaTestuarekinKointziditu("Interstellar");
		assertFalse(erantzuna);
		//Izenburua ez du kointziditzen
		erantzuna = film.izenburuaTestuarekinKointziditu("La la land");
		assertTrue(erantzuna);
	}

}
