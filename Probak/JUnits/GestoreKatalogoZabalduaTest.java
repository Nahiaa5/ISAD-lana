package JUnits;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Eredua.DB_kudeatzailea;
import Eredua.Film;
import Kontroladorea.GestoreFilm;
import Kontroladorea.GestoreKatalogoZabaldua;

class GestoreKatalogoZabalduaTest {
	private GestoreKatalogoZabaldua gKZ;
	private GestoreFilm gF;
	private Film film1, film2;

	@BeforeEach
	void setUp() throws Exception {
		DB_kudeatzailea.setTestMode(true);
		gKZ = GestoreKatalogoZabaldua.getnZK();
		gF = GestoreFilm.getKN();
		
        film1 = new Film(1, "La la land", "Ryan Gosling", "2021", "Musikala", "Damien Chazelle", "79224675A", true, 4.00, "resources/LaLaLand.mp4"); 
        film2 = new Film(2, "Interstellar", "Matthew McConaughey, Jessica Chastain", "2014", "Zientzia fikzioa", "Christopher Nolan", "79224675A", true, 2.50, "resources/Interstellar.mp4");  

        gF.getFilmak().add(film1);
	}

	@AfterEach
	void tearDown() throws Exception {
		DB_kudeatzailea.setTestMode(false);
		gKZ.setDatuak(null);
		gF.getFilmak().clear();
	}
	
	public boolean izenburuaDauka(String izenb, JSONArray filmak) {
		if (filmak !=null) {
			for (int i = 0; i < filmak.length(); i++) {
				JSONObject film = filmak.getJSONObject(i);
				if (film.getString("Title").equalsIgnoreCase(izenb)) {
					return true;
				}
			}
		}
		return false;
	}
	
	// getFilmXehetasunak existitzen da, baina honek eta addFilma izen desberdinak erabiltzen dituzte
	public JSONObject filmJSONBihurtu(Film pFilm) {
		JSONObject filma = new JSONObject();
		filma.put("Title", pFilm.getIzenburua());
		filma.put("Actors", pFilm.getAktoreak());
		filma.put("Year", pFilm.getUrtea());
		filma.put("Genre", pFilm.getGeneroa());
		filma.put("Director", pFilm.getZuzendaria());
		return filma;
	}

	@Test
	void testFilmakBilatu() {
		try {
			//Bakarrik konexioaren elementuak, hauek probatzeko
			String apiKey = "209e2977";
			String urlString = "http://www.omdbapi.com/?apikey=" + apiKey + "&s=" + "La La Land";
			
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			//Filmak bilatu
			JSONArray filmak = gKZ.FilmakBilatu("la la land");
			assertTrue(filmak.length()>=1);
			assertTrue(izenburuaDauka("la la land", filmak));
			
			// Filmik ez bilatu
			filmak = gKZ.FilmakBilatu("");
			assertFalse(izenburuaDauka("la la land", filmak));
			assertNull(filmak);
		}catch(Exception e) {
			fail("Errorea API-rekin konektatzeko");
		}
	}

	@Test
	void testXehetasunakBilatu() {
		JSONObject pfilm1 = gKZ.xehetasunakBilatu("Dune (1984)");
		JSONObject pfilm2= gKZ.xehetasunakBilatu("Dune (2000)");
		
		//Filma behar bezala bilatu da
		assertNotNull(pfilm1);
		assertEquals(pfilm1.getString("Title"), "Dune");
		assertEquals(pfilm1.getString("Year"), "1984");

		//Izen berdineko bi film desberdinak egon daitezke
		assertNotEquals(pfilm1,pfilm2);
		assertEquals(pfilm1.getString("Director"), "David Lynch");
		assertNotEquals(pfilm2.getString("Director"), "David Lynch");
	}

	@Test
	void testBidaliEskaera() {
		JSONObject filma1 = filmJSONBihurtu(film1);
		JSONObject filma2 = filmJSONBihurtu(film2);
		gKZ.setDatuak(filma1);
		gKZ.setDatuak(filma2);
		
		//Zerrendan dagoen film bat eskatu
		gKZ.setDatuak(filma1);
		assertFalse(gKZ.bidaliEskaera());
		
		//Zerrendan ez dagoen film bat eskatu, gehitzen saiatzekotan salbuespena jaurtitzen da
		try {
			gKZ.setDatuak(filma2);
			gKZ.bidaliEskaera();
			fail("Ez da saiatu filma gehitzen");
		}catch(UnsupportedOperationException e) {
			assertEquals("Proba modua: DB ez da erabiliko", e.getMessage());;
		}
	}

}
