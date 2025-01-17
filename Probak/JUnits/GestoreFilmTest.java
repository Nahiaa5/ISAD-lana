package JUnits;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Eredua.Film;
import Eredua.Puntuazioa;
import Kontroladorea.GestoreFilm;

class GestoreFilmTest {
	private GestoreFilm gestoreFilm;
    private Film film1, film2, film3;
   
    @BeforeEach
    void setUp() {
        gestoreFilm = GestoreFilm.getKN();
        gestoreFilm.getFilmak().clear();  
        
        film1 = new Film(1, "La la land", "Ryan Gosling", "2021", "Musikala", "Damien Chazelle", "79224675A", true, 4.00, "resources/LaLaLand.mp4"); 
        film2 = new Film(2, "Interstellar", "Matthew McCounaghey, Jessica Chastain", "2014", "Zientzia fikzioa", "Christopher Nolan", "79224675A", true, 2.50, "resources/Interstellar.mp4"); 
        film3 = new Film(3, "A Minecraft Movie", "Jason Momoa, Jack Black", "2025", "Akzioa", "Jared Hess", "79224675A", true, 3.33, "resources/AMinecraftMovie.mp4"); 
        
        gestoreFilm.getFilmak().add(film1);
        gestoreFilm.getFilmak().add(film2);
        gestoreFilm.getFilmak().add(film3);
    }

	@Test
	void testOrdenatuPuntuazioz() {
        gestoreFilm.ordenatuPuntuazioz();
        
        List<Film> ordenatuta = gestoreFilm.getFilmak();
        assertEquals(film1, ordenatuta.get(0)); 
        assertEquals(film3, ordenatuta.get(1)); 
        assertEquals(film2, ordenatuta.get(2));
	}

	@Test
	void testBilatuIzenaDatarekin() {
		Film aurkitua=gestoreFilm.bilatuIzenaDatarekin("Interstellar", "2014");
		assertNotNull(aurkitua);
		assertEquals(film2, aurkitua);
		
		Film ezAurkitua=gestoreFilm.bilatuIzenaDatarekin("Avatar", "2016");
		assertNull(ezAurkitua);
	}

	@Test
	void testGordePuntuazioaFilman() {
		Puntuazioa p=new Puntuazioa("12345678Z", 1, 5, "Zoragarria", LocalDate.now());
		gestoreFilm.gordePuntuazioaFilman(p, 1);
		assertEquals(1, film1.getBalorazioak().size());
		assertEquals(p, film1.getBalorazioak().get(0));
		
		try {
            gestoreFilm.gordePuntuazioaFilman(p, 8); 
            fail("Salbuespena filmID okerragatik.");
        } catch (IllegalArgumentException e) {
            assertEquals("Ez da aurkitu filma ID horrekin", e.getMessage());
        }
	}
}
