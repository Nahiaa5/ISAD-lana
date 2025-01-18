package JUnits;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Eredua.Alokairua;
import Eredua.Film;
import Eredua.HasData;
import Kontroladorea.GestoreAlokairu;

class GestoreAlokairuTest {
	private GestoreAlokairu gestoreAlokairu;
	private Film film;

	@BeforeEach
	void setUp() throws Exception {
		gestoreAlokairu = GestoreAlokairu.getGA();
		
		film = new Film(1, "La la land", "Ryan Gosling", "2021", "Musikala", "Damien Chazelle", "79224675A", true, 4.00, "resources/LaLaLand.mp4");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testAlokairuaEgin() {
		//PROBA HAU XAMPP-en MYSQL MODULOA PIZTUTA IZAN GABE EGITEN BADA, ERROREA AGERTUKO DA KONEXIO FALTAGATIK, HALA ERE METODOAK OBJEKTUEKIN ONDO LAN EGITEN DUELA IKUSI DAITEKE AZPIKO ASERTZIOEKIN
		//XAMPP MODULOA PIZTEN BADA PROBA EGITEKO ETA '12345678Z' NAN-A DUEN ERABILTZAILEAK ALOKAIRU AKTIBOREN BAT BADAUKA DATU-BASEAREN ARABERA FILM HORRENTZAKO, BESTE ERRORE BAT AGERTUKO DA, FOREIGN KEY BALDINTZAREN ERAGINEZ
				
		//DATU-BASEAREKIN KONEXIOA ONDO DAGOELA ETA GUZTI PROBATU NAHI BADA, '12345678Z' NAN-A DUEN ERABILTZAILEAK FILM HONENTZAKO ALOKAIRU AKTIBORIK EZ DUELA EGIAZTATU BEHAR DA

		//Gestoreak duen alokairu kopurua
		int kop = gestoreAlokairu.getAlokairuak().size();
		//Alokairua egin
		gestoreAlokairu.alokairuaEgin("12345678Z", film);
		int kop1 = gestoreAlokairu.getAlokairuak().size();
		//Gestoreak duen alokairu kopurua +1 izan behar da
		assertEquals(kop+1, kop1);
	}

}
