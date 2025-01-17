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
		Puntuazioa p=new Puntuazioa("12345678F", 1, 5, "Film ezinhobea", LocalDate.now());
		GestorePuntuazio gp=GestorePuntuazio.getGP();
		gp.getBalorazioak().clear();
		gp.getBalorazioak().add(p);
		
		assertTrue(gp.badagoPuntuazioa("12345678F", 1));
		assertFalse(gp.badagoPuntuazioa("12345679D", 2));
	}

	@Test
	void testGordePuntuazioa() {
		DB_kudeatzailea.setTestMode(true);
		
		GestorePuntuazio gp=GestorePuntuazio.getGP();
		gp.getBalorazioak().clear();
		
		Puntuazioa pBerria=new Puntuazioa("12345678F", 1, 4, "Film ona", LocalDate.now());
		
		try{
			gp.gordePuntuazioa(pBerria);
			fail("Salbuespena jaurti da proba moduagatik.");
		}catch(UnsupportedOperationException e) {
			assertEquals("Proba modua: DB ez da erabiliko", e.getMessage());;
		}
		DB_kudeatzailea.setTestMode(false);
		
		assertEquals(1,gp.getBalorazioak().size());
		assertEquals(pBerria, gp.getBalorazioak().get(0));
		
		Puntuazioa pEguneratua=new Puntuazioa("12345678F", 1, 2, "Film txarra", LocalDate.now());
		gp.gordePuntuazioa(pEguneratua);
		assertEquals(1, gp.getBalorazioak().size());
		
		Puntuazioa eguneratuta=gp.getBalorazioak().get(0);
		assertEquals(2, eguneratuta.getPuntuazioa());
		assertEquals("Film txarra", eguneratuta.getIruzkina());
	}
}
