package JUnits;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import Eredua.DB_kudeatzailea;
import Eredua.Puntuazioa;
import Kontroladorea.GestorePuntuazio;

class GestorePuntuazioTest {
	

	@Test
	void testBadagoPuntuazioa() {
		//Puntuazioa sortu eta gorde
		Puntuazioa p1=new Puntuazioa("12345678F", 1, 5, "Film ezinhobea", LocalDate.now());
		Puntuazioa p2=new Puntuazioa("22345678P", 1, 3, "Film arrunta", LocalDate.now());

		GestorePuntuazio gp=GestorePuntuazio.getGP();
		gp.getBalorazioak().clear();
		gp.getBalorazioak().add(p1);
		gp.getBalorazioak().add(p2);

		//Puntuazioa gestorean gordeta dagoela konprobatu
		assertTrue(gp.badagoPuntuazioa("12345678F", 1));
		
		//Gordeta ez dagoen puntuazioa ez dagoela konprobatu
		assertFalse(gp.badagoPuntuazioa("12345679D", 2));
	}

	@Test
	void testGordePuntuazioa() {
		//Proba modua aktibatu datu baserako
		DB_kudeatzailea.setTestMode(true);
		
		GestorePuntuazio gp=GestorePuntuazio.getGP();
		gp.getBalorazioak().clear();
		
		//Puntuazio berria sortu
		Puntuazioa pBerria=new Puntuazioa("12345678F", 1, 4, "Film ona", LocalDate.now());
		
		//Puntuazioa gordetzen saiatu proba moduan eta salbuespena jaurtitzen duela konprobatu
		try{
			gp.gordePuntuazioa(pBerria);
			fail("Salbuespena jaurti da proba moduagatik.");
		}catch(UnsupportedOperationException e) {
			assertEquals("Proba modua: DB ez da erabiliko", e.getMessage());;
		}
		//Proba modua desaktibatu
		DB_kudeatzailea.setTestMode(false);
		
		//Puntuazio berria zuzen gordetzen dela konprobatu
		assertEquals(1,gp.getBalorazioak().size());
		assertEquals(pBerria, gp.getBalorazioak().get(0));
		
		//Existitzen den puntuazioa eguneratu
		Puntuazioa pEguneratua=new Puntuazioa("12345678F", 1, 2, "Film txarra", LocalDate.now());
		//Eguneratutako puntuazioa gorde
		gp.gordePuntuazioa(pEguneratua);
		assertEquals(1, gp.getBalorazioak().size());
		
		//Eguneratutako puntuazioak balio berriak dituela konprobatu
		Puntuazioa eguneratuta=gp.getBalorazioak().get(0);
		assertEquals(2, eguneratuta.getPuntuazioa());
		assertEquals("Film txarra", eguneratuta.getIruzkina());
	}
}
