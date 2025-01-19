package JUnits;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Eredua.DB_kudeatzailea;
import Eredua.Film;
import Eredua.Puntuazioa;
import Kontroladorea.GestoreErabiltzaile;
import Kontroladorea.GestoreFilm;
import Kontroladorea.GestoreNagusia;

class GestoreFilmTest {
	private GestoreFilm gestoreFilm;
    private Film film1, film2, film3;
    private GestoreErabiltzaile gestoreErabiltzaile;
   
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
        
        gestoreErabiltzaile = GestoreErabiltzaile.getGE();
        gestoreErabiltzaile.getErabiltzaileak().clear();

        gestoreErabiltzaile.setSaioaNan("12345678Z");
        
        DB_kudeatzailea.setTestMode(true);
    }
    
    @AfterEach
    void tearDown(){
    	DB_kudeatzailea.setTestMode(false);
    }
    
    public boolean berdinakDira(JSONArray array1, JSONArray array2) {
        if (array1.length() != array2.length()) {
            return false;
        }
        Set<String> set1 = jsonArrayToSet(array1);
        Set<String> set2 = jsonArrayToSet(array2);
        return set1.equals(set2);
    }

    private Set<String> jsonArrayToSet(JSONArray jsonArray) {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            set.add(jsonObject.toString());
        }
        return set;
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
	void testOrdenatuPuntuazioz() {
		//Filmak ordenatu
        gestoreFilm.ordenatuPuntuazioz();
        //Zerrenda ordenatua lortu
        List<Film> ordenatuta = gestoreFilm.getFilmak();
        //Ordena zuzena dela konprobatu
        assertEquals(film1, ordenatuta.get(0)); 
        assertEquals(film3, ordenatuta.get(1)); 
        assertEquals(film2, ordenatuta.get(2));
	}

	@Test
	void testBilatuIzenaDatarekin() {
		//Filma izena eta urtearekin bilatu
		Film aurkitua=gestoreFilm.bilatuIzenaDatarekin("Interstellar", "2014");
		//Filma aurkitu dela konprobatu
		assertNotNull(aurkitua);
		assertEquals(film2, aurkitua);
		
		//Gordeta ez dagoen film bat bilatu
		Film ezAurkitua=gestoreFilm.bilatuIzenaDatarekin("Avatar", "2016");
		//Emaitza ez dela aurkitu konprobatu
		assertNull(ezAurkitua);
	}

	@Test
	void testGordePuntuazioaFilman() {
		//Puntuazio bat sortu eta gorde film batentzat
		Puntuazioa p=new Puntuazioa("12345678Z", 1, 5, "Zoragarria", LocalDate.now());
		gestoreFilm.gordePuntuazioaFilman(p, 1);
		//Puntuazioa filman gorde dela konprobatu
		assertEquals(1, film1.getBalorazioak().size());
		assertEquals(p, film1.getBalorazioak().get(0));
		
		//Puntuazio bat gorde existitzen ez den film batean
		try {
            gestoreFilm.gordePuntuazioaFilman(p, 8); 
            fail("Salbuespena filmID okerragatik.");
        } catch (IllegalArgumentException e) {
            assertEquals("Ez da aurkitu filma ID horrekin", e.getMessage());
        }
	}
	
	@Test
	void testGetInfoKatalogokoFilmGuztiak() {
		//JSONArray honek katalogoan ditugun film guztien izenak eta puntuazioak gordetzen ditu
		JSONArray emaitza = gestoreFilm.getInfoKatalogokoFilmGuztiak();
		//JSONArray bat sortu, gaineko metodoak itzuli beharko lukeenaren egitura bera duena
		JSONArray esperotakoa = new JSONArray();
        JSONObject film1 = new JSONObject();
        film1.put("izenburua", "La la land");
        film1.put("puntuazioa", 4.00);
        esperotakoa.put(film1);

        JSONObject film2 = new JSONObject();
        film2.put("izenburua", "Interstellar");
        film2.put("puntuazioa", 2.50);
        esperotakoa.put(film2);
        
        JSONObject film3 = new JSONObject();
        film3.put("izenburua", "A Minecraft Movie");
        film3.put("puntuazioa", 3.33);
        esperotakoa.put(film3);
        //emaitza eta esperotakoa konparatu, emaitzak egitura egokia duen egiaztatzeko
        assertTrue(berdinakDira(esperotakoa, emaitza));
	}
	
	@Test
	void testBilatuFilmKatalogoan() {
		//JSONArray honek katalogoan ditugun eta emandako String-a tituluan nonbait duten filmen izenak eta puntuazioak gordetzen ditu
		//Interstellar ez da honetan agertuko, bere titulua ez datorrelako bat emandako String-arekin
		JSONArray emaitza = gestoreFilm.bilatuFilmKatalogoan("a ");
		//JSONArray bat sortu, gaineko metodoak itzuli beharko lukeenaren egitura bera duena
		JSONArray esperotakoa = new JSONArray();
        JSONObject film1 = new JSONObject();
        film1.put("izenburua", "La la land");
        film1.put("puntuazioa", 4.00);
        esperotakoa.put(film1);
        JSONObject film2 = new JSONObject();
        film2.put("izenburua", "A Minecraft Movie");
        film2.put("puntuazioa", 3.33);
        esperotakoa.put(film2);
        //emaitza eta esperotakoa konparatu, emaitzak egitura egokia duen egiaztatzeko
        assertTrue(berdinakDira(esperotakoa, emaitza));
	}
	
	@Test
	void testBilatuIzenarekin() {
		//Filma existitzen da
		Film film = gestoreFilm.bilatuIzenarekin("La la land");
		assertEquals(film, film1);
		//Filma ez da existitzen, beraz null izan beharko da emaitza
		film = gestoreFilm.bilatuIzenarekin("Titanic");
		assertNull(film);
	}
	
	@Test
	void getFilmarenPath() {
		//Metodoak filmaren path lortu eta itzuli behar du, asertzioan espero den emaitza jasotakoarekin konparatzen da
		String path = gestoreFilm.getFilmarenPath(film1);
		assertEquals(path, "resources/LaLaLand.mp4");
	}
	
	@Test
	void testBadagoFilma() {
		//Izena ondo, urtea txarto
		assertFalse(gestoreFilm.badagoFilma("La la land", "2020"));
		//Izena txarto, izena ondo
		assertFalse(gestoreFilm.badagoFilma("La land", "2021"));
		//Biak ondo
		assertTrue(gestoreFilm.badagoFilma("La la land", "2021"));
		//Biak txarto
		assertFalse(gestoreFilm.badagoFilma("La land", "2020"));
		//Filma zerrendatik kendu eta gero
		gestoreFilm.getFilmak().clear();
		assertFalse(gestoreFilm.badagoFilma("La la land", "2021"));

	}
	
	@Test
	void testAddFilma() {
		try {
			gestoreFilm.getFilmak().clear();  
	        JSONObject filma = filmJSONBihurtu(film1);
	        gestoreFilm.addFilma(filma);
	        fail("Ez da saiatu filma gehitzen");
		}catch(UnsupportedOperationException e) {
			assertEquals("Proba modua: DB ez da erabiliko", e.getMessage());
			assertEquals(gestoreFilm.getFilmak().size(), 1);
			//Katalogoko lehen filma izango denez id 1 izango da
			assertEquals(gestoreFilm.getFilmak().get(0).getFilmID(),1);
		}
	}
	
	@Test
	void testGetFilmEskaerak() {
		//Filmak daude gordeta, baina guztiak onartuta
		JSONArray eskaerak = gestoreFilm.getFilmEskaerak();
		assertEquals(0, eskaerak.length());
		
		//Onartu gabeko film eskaerak daude
		film1.setKatalogoan(false);
		eskaerak = gestoreFilm.getFilmEskaerak();
		assertEquals(1, eskaerak.length());
	}
	
	@Test 
	void testGetFilmXehetasunak(){
		JSONObject film = gestoreFilm.getFilmXehetasunak(film1.getIzenburua());
		assertEquals(film1.getIzenburua(),film.getString("izenburua"));
		assertEquals(film1.getAktoreak(),film.getString("aktoreak"));
		assertEquals(film1.getUrtea(),film.getString("urtea"));
		assertEquals(film1.getGeneroa(),film.getString("generoa"));
		assertEquals(film1.getZuzendaria(),film.getString("zuzendaria"));
		assertEquals(film1.getPuntuazioaBb(),film.getDouble("puntuazioaBb"));
	}
	
	@Test
	void testFilmaOnartu() {
		try {
			film1.setKatalogoan(false);
			JSONArray eskaerak = gestoreFilm.getFilmEskaerak();
			assertEquals(1, eskaerak.length());
			gestoreFilm.filmaOnartu("La la land");
			fail("Ez da saiatu filma onartzen");
		}catch(UnsupportedOperationException e) {
			assertEquals("Proba modua: DB ez da erabiliko", e.getMessage());
			JSONArray eskaerak = gestoreFilm.getFilmEskaerak();
			assertEquals(0, eskaerak.length());
			assertTrue(film1.getKatalogoan());
			assertEquals("12345678Z", film1.getErabiltzaileNAN());
		}
	}
	
	@Test
	void testFilmaEzabatu() {
		try {
			assertEquals(gestoreFilm.getFilmak().size(),3);
			assertTrue(gestoreFilm.badagoFilma("La la land", "2021"));
			gestoreFilm.filmaEzabatu("La la land");
			fail("Ez da saiatu filma ezabatzen");
		}catch(UnsupportedOperationException e) {
			assertEquals("Proba modua: DB ez da erabiliko", e.getMessage());
			assertFalse(gestoreFilm.badagoFilma("La la land", "2021"));
			assertEquals(gestoreFilm.getFilmak().size(),2);
		}
	}
}
