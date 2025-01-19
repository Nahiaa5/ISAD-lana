package JUnits;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Eredua.Film;
import Eredua.Puntuazioa;

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
	
	@Test
	void testKalkulatuPuntuBb() {
		//Filma horretan ez dago puntuaziorik
		film.kalkulatuPuntuBb();
		assertEquals(0.0, film.getPuntuazioaBb(), 0.001, "Batez besteko puntuazioa 0.0 izango da puntuaziorik ez badago.");
		
		//Puntuazioak daude
		ArrayList<Puntuazioa> puntuazioak=new ArrayList<>();
		puntuazioak.add(new Puntuazioa("12345678Z", 1, 5, "Bikaina", LocalDate.now()));
		puntuazioak.add(new Puntuazioa("98765432M", 1, 3, "Film arrunta", LocalDate.now()));
		puntuazioak.add(new Puntuazioa("67891011P", 1, 1, "Film txarra", LocalDate.now()));
		film.setBalorazioak(puntuazioak);
		film.kalkulatuPuntuBb();
		double esperotakoBb=(5 + 3 + 1) / 3.0;
		assertEquals(esperotakoBb, film.getPuntuazioaBb(), 0.001, "Batez besteko puntuazioa ez da behar bezala kalkulatu.");
		
		//Batez besteko puntuazioa eguneratu (puntuazio berria)
		Puntuazioa puntuazioBerria=new Puntuazioa("10000000G", 1, 2, "Ez da nire gustokoa", LocalDate.now());
		film.gehituPuntuazioa(puntuazioBerria);
		film.kalkulatuPuntuBb();
		esperotakoBb=(5 + 3 + 1 + 2) / 4.0;
		assertEquals(esperotakoBb, film.getPuntuazioaBb(), 0.001, "Batez besteko puntuazioa ez da behar bezala eguneratu.");
		
		//Existitzen den puntuazio bat aldatzea
		for(Puntuazioa p: film.getBalorazioak()) {
			if(p.getNAN().equals("12345678Z")) {
				p.setPuntuazioa(4);
			}
		}
		film.kalkulatuPuntuBb();
		esperotakoBb=(4 + 3 + 1 + 2) / 4.0;
		assertEquals(esperotakoBb, film.getPuntuazioaBb(), 0.001, "Batez besteko puntuazioa ez da behar bezala eguneratu.");
	}

}
