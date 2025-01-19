package JUnits;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Eredua.Film;
import Eredua.Puntuazioa;
import Kontroladorea.GestoreFilm;
import Kontroladorea.GestoreNagusia;

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
}
