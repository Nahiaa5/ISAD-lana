package JUnits;

import static org.junit.jupiter.api.Assertions.*;

import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GestoreKatalogoZabalduaTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testFilmakBilatu() {
		try {
			String apiKey = "209e2977";
			String urlString = "http://www.omdbapi.com/?apikey=" + apiKey + "&s=" + "La La Land";
			
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
		}catch(Exception e) {
			fail("Errorea API-rekin konektatzeko");
		}
		
	}

	@Test
	void testXehetasunakErakutsi() {
		fail("Not yet implemented");
	}

	@Test
	void testXehetasunakBilatu() {
		fail("Not yet implemented");
	}

	@Test
	void testBidaliEskaera() {
		fail("Not yet implemented");
	}

}
