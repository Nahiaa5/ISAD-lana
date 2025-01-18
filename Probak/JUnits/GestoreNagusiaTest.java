package JUnits;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Eredua.*;
import Kontroladorea.*;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Set;
import java.time.LocalDate;
import java.util.HashSet;

class GestoreNagusiaTest {
	private GestoreFilm gestoreFilm;
    private Film film1, film2;
    private GestoreErabiltzaile gestoreErabiltzaile;
    private Erabiltzaile erab;

	@BeforeEach
	void setUp() throws Exception {
		gestoreFilm = GestoreFilm.getKN();
        gestoreFilm.getFilmak().clear();  
        
        film1 = new Film(1, "La la land", "Ryan Gosling", "2021", "Musikala", "Damien Chazelle", "79224675A", true, 4.00, "resources/LaLaLand.mp4"); 
        film2 = new Film(2, "Interstellar", "Matthew McConaughey, Jessica Chastain", "2014", "Zientzia fikzioa", "Christopher Nolan", "79224675A", true, 2.50, "resources/Interstellar.mp4");  
        
        gestoreFilm.getFilmak().add(film1);
        gestoreFilm.getFilmak().add(film2);
        
        gestoreErabiltzaile = GestoreErabiltzaile.getGE();
        gestoreErabiltzaile.getErabiltzaileak().clear();
        
        erab = new Erabiltzaile("12345678Z", "Manolito", "Fernandez", "manolito@gmail.com", "1234", 0, 1);
        
        gestoreErabiltzaile.getErabiltzaileak().add(erab);
        gestoreErabiltzaile.setSaioaNan("12345678Z");
	}

	@AfterEach
	void tearDown() throws Exception {
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
	void testGetInfoKatalogokoFilmGuztiak() {
		//JSONArray honek katalogoan ditugun film guztien izenak eta puntuazioak gordetzen ditu
		JSONArray emaitza = GestoreNagusia.getGN().getInfoKatalogokoFilmGuztiak();
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
        //emaitza eta esperotakoa konparatu, emaitzak egitura egokia duen egiaztatzeko
        assertTrue(berdinakDira(esperotakoa, emaitza));
	}
	
	@Test
	void testBilaketaEgin() {
		//JSONArray honek katalogoan ditugun eta emandako String-a tituluan nonbait duten filmen izenak eta puntuazioak gordetzen ditu
		//Interstellar honetan ez da agertuko, bere titulua ez datorrelako bat emandako String-arekin
		JSONArray emaitza = GestoreNagusia.getGN().bilaketaEgin("La la land");
		//JSONArray bat sortu, gaineko metodoak itzuli beharko lukeenaren egitura bera duena
		JSONArray esperotakoa = new JSONArray();
        JSONObject film1 = new JSONObject();
        film1.put("izenburua", "La la land");
        film1.put("puntuazioa", 4.00);
        esperotakoa.put(film1);
        //emaitza eta esperotakoa konparatu, emaitzak egitura egokia duen egiaztatzeko
        assertTrue(berdinakDira(esperotakoa, emaitza));
	}
	
	@Test
	void testFilmaAlokatu() {
		//PROBA HAU XAMPP-en MYSQL MODULOA PIZTUTA IZAN GABE EGITEN BADA, ERROREA AGERTUKO DA KONEXIO FALTAGATIK, HALA ERE METODOAK OBJEKTUEKIN ONDO LAN EGITEN DUELA IKUSI DAITEKE AZPIKO ASERTZIOEKIN
		//XAMPP MODULOA PIZTEN BADA PROBA EGITEKO ETA '12345678Z' NAN-A DUEN ERABILTZAILEAK ALOKAIRU AKTIBOREN BAT BADAUKA DATU-BASEAREN ARABERA FILM HORRENTZAKO, BESTE ERRORE BAT AGERTUKO DA, FOREIGN KEY BALDINTZAREN ERAGINEZ
		
		//DATU-BASEAREKIN KONEXIOA ONDO DAGOELA ETA GUZTI PROBATU NAHI BADA, '12345678Z' NAN-A DUEN ERABILTZAILEAK FILM HONENTZAKO ETA HURRENGO METODOAN ERABILTZEN DENARENTZAKO ALOKAIRU AKTIBORIK EZ DUELA EGIAZTATU BEHAR DA
		
		//Erabiltzaileak ez dauka filma ez dauka aurretik alokatuta
		boolean emaitza = GestoreNagusia.getGN().filmaAlokatu("La la land");
		assertTrue(emaitza);
		//Alokairua egitean erabiltzailearen zerrendan gordetzen da beraz, hutsik egoteari uzten dio
		assertTrue(!erab.getEgindakoAlokairuak().isEmpty());
		//Erabiltzaileak filma aurretik alokatuta dauka
		boolean emaitza1 = GestoreNagusia.getGN().filmaAlokatu("La la land");
		assertFalse(emaitza1);
	}
	
	@Test
	void testAlokairuaDauka() {
		//Erabiltzaileak ez dauka alokairua film horretarako
		boolean emaitza = GestoreNagusia.getGN().alokairuaDauka("Interstellar");
		assertFalse(emaitza);
		//Filma alokatu
		GestoreNagusia.getGN().filmaAlokatu("Interstellar");
		//Erabiltzaileak alokairua dauka film horretarako
		emaitza = GestoreNagusia.getGN().alokairuaDauka("Interstellar");
		assertTrue(emaitza);
	}
	
	@Test
	void filmaPantailaratu() {
		//Metodoak filmaren path lortu eta itzuli behar du, asertzioan espero den emaitza jasotakoarekin konparatzen da
		String path = GestoreNagusia.getGN().filmaPantailaratu("La la land");
		assertEquals(path, "resources/LaLaLand.mp4");
	}
	
	@Test 
	void testErabiltzaileakAlokatuDu() {
		LocalDate gaur=LocalDate.now();
		HasData hasData=new HasData(gaur);
		
		//Erabiltzaileak filma alokatu du
		Alokairua alok1= new Alokairua(film1, hasData, gaur.plusDays(5));
		erab.getEgindakoAlokairuak().add(alok1);
		
		boolean emaitza1=GestoreNagusia.getGN().erabiltzaileakAlokatuDu("12345678Z", 1);
		assertTrue(emaitza1, "Erabiltzaileak filma alokatu du.");
		
		//Erabiltzaileak ez du filma alokatu
		boolean emaitza2=GestoreNagusia.getGN().erabiltzaileakAlokatuDu("12345678Z", 2);
		assertFalse(emaitza2, "Erabiltzaileak ez du filma alokatu.");
		
		//Erabiltzailea ez dago sisteman
		boolean emaitza3=GestoreNagusia.getGN().erabiltzaileakAlokatuDu("98765432M", 1);
		assertFalse(emaitza3, "Erabiltzailea ez da existitzen.");	
	}
}
