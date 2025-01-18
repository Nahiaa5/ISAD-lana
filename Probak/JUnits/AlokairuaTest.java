package JUnits;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Eredua.Alokairua;
import Eredua.Film;
import Eredua.HasData;

class AlokairuaTest {
	private Alokairua alok, alok1;
	private Film film1, film2;

	@BeforeEach
	void setUp() throws Exception {
		film1 = new Film(1, "La la land", "Ryan Gosling", "2021", "Musikala", "Damien Chazelle", "79224675A", true, 4.00, "resources/LaLaLand.mp4");
		HasData hasData = new HasData(LocalDate.of(2025, 1, 17));
		LocalDate bukData =  LocalDate.now(); //bukData gaur bada, alokairua aktibo egongo da
		alok = new Alokairua(film1, hasData, bukData);
		
		film2 = new Film(2, "Interstellar", "Matthew McConaughey, Jessica Chastain", "2014", "Zientzia fikzioa", "Christopher Nolan", "79224675A", true, 2.50, "resources/Interstellar.mp4");
		HasData hasData1 = new HasData(LocalDate.of(2025, 1, 15));
		LocalDate bukData1 =  LocalDate.of(2025, 1, 17);
		alok1 = new Alokairua(film2, hasData1, bukData1);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testFilmaKointziditu() {
		//Filma ez da egokia alokairu honentzako
		assertFalse(alok.filmaKointziditu(film2));
		//Filma egokia da
		assertTrue(alok.filmaKointziditu(film1));
	}
	
	@Test
	void testAlokairuaAktiboDago() {
		//Alokairua aktibo dago
		assertTrue(alok.alokairuaAktiboDago());
		//Alokairua ez dago aktibo
		assertFalse(alok1.alokairuaAktiboDago());
	}

}
