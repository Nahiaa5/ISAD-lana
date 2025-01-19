package JUnits;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Eredua.Erabiltzaile;
import Eredua.Film;
import Eredua.HasData;
import Kontroladorea.GestoreErabiltzaile;
import Eredua.Alokairua;

class GestoreErabiltzaileTest {
	private GestoreErabiltzaile gestoreErabiltzaile;
	private Erabiltzaile erab;
	private Erabiltzaile erab2;
	private Alokairua alok;
	private Film film;

	@BeforeEach
	void setUp() throws Exception {
		gestoreErabiltzaile = GestoreErabiltzaile.getGE();
		
		erab = new Erabiltzaile("12345678Z", "Manolito", "Fernandez", "manolito@gmail.com", "1234", 0, 1);
		erab2 = new Erabiltzaile("56789101Y", "Antonio", "Pérez", "antonio@gmail.com", "7891", 0, 0); //Ez onartua
		film = new Film(1, "La la land", "Ryan Gosling", "2021", "Musikala", "Damien Chazelle", "79224675A", true, 4.00, "resources/LaLaLand.mp4");
		HasData hasData = new HasData(LocalDate.now());
		LocalDate bukData =  LocalDate.of(2025, 1, 19);
		alok = new Alokairua(film, hasData, bukData);
		
		gestoreErabiltzaile.getErabiltzaileak().add(erab);
		gestoreErabiltzaile.getErabiltzaileak().add(erab2);

        gestoreErabiltzaile.setSaioaNan("12345678Z");
	}

	@AfterEach
	void tearDown() throws Exception {
		//Erabiltzaile-zerrenda hustu
	    gestoreErabiltzaile.getErabiltzaileak().clear();
	    
	    //Saioa itxi
	    gestoreErabiltzaile.setSaioaNan(null);

	    //Instantzia guztiak ezabatu
	    erab = null;
	    film = null;
	    alok = null;
	}
	
	@Test
	void testBilatzaileanErabiltzaileak() {
		//Onartuta dagoen erabiltzailea aurkitu
		JSONArray emaitza = gestoreErabiltzaile.bilatzaileanErabiltzaileak("Manolito");
		assertEquals(1, emaitza.length(), "Emaitza egon behar da Manolitorentzat.");
		JSONObject json = emaitza.getJSONObject(0);
		assertEquals("12345678Z", json.getString("NAN"), "NAN-a '12345678Z' izan behar da.");
		assertEquals("Manolito", json.getString("Izena"), "Izena 'Manolito' izan behar da.");
		
		//Ez da aurkitu
		emaitza = gestoreErabiltzaile.bilatzaileanErabiltzaileak("Lucía");
	    assertEquals(0, emaitza.length(), "Ez da emaitzik egon behar Lucíarentzat.");
	    
	    //Ez onartutako erabiltzailea
		emaitza = gestoreErabiltzaile.bilatzaileanErabiltzaileak("Antonio");
	    assertEquals(0, emaitza.length(), "Ez da emaitzik egon behar Antoniorentzat onartuta ez dagoelako.");
	}
	
	@Test
	void testGetInfoEskaerak() {
		//Onartuta ez dauden erabiltzaileak lortu
        JSONArray emaitza = gestoreErabiltzaile.getInfoEskaerak();
        assertEquals(1, emaitza.length(), "Onartuta ez dauden 2 erabiltzaile.");
        
        //Ez onartutako erabiltzailearen informazioa zuzena dela konprobatu
        JSONObject json2 = emaitza.getJSONObject(0);
        assertEquals("56789101Y", json2.getString("NAN"), "Antonioren NAN-a '56789101Y' izan behar da.");
        assertEquals("Antonio", json2.getString("Izena"), "Izena 'Antonio' izan behar da.");
        
        //Onartutako erabiltzailea zerrendan ez dagoela konprobatu
        boolean aurkitua = false;
        for (int i = 0; i < emaitza.length(); i++) {
            JSONObject json = emaitza.getJSONObject(i);
            if (json.getString("NAN").equals("12345678Z")) {
            	aurkitua = true;
                break;
            }
        }
        
        assertFalse(aurkitua, "Manolito ez da erabiltzaileen eskaeren artean agertzen.");
	}
	
	@Test
	void testErabiltzaileaOnartu() {
		//Onartuta ez dagoen erabiltzailea onartu
        gestoreErabiltzaile.erabiltzaileaOnartu("56789101Y");

        //Antonio onartuta izan dela konprobatu
        Erabiltzaile antonio = gestoreErabiltzaile.getErabiltzaileByNAN("56789101Y");
        assertEquals(1, antonio.getOnartuta(), "Antonio onartuta izan da.");

        //Manolito onartuta dagoela konprobatu
        Erabiltzaile manolito = gestoreErabiltzaile.getErabiltzaileByNAN("12345678Z");
        assertEquals(1, manolito.getOnartuta(), "Manolito onartuta dago.");

        //Onartuta dagoen erabiltzailea berriz onartu, ez da aldatuko
        gestoreErabiltzaile.erabiltzaileaOnartu("12345678Z");

        //Manolito onartuta jarraitzen duela konprobatu
        manolito = gestoreErabiltzaile.getErabiltzaileByNAN("12345678Z");
        assertEquals(1, manolito.getOnartuta(), "Manolitoren egoera ez da aldatu.");
	}
	
	@Test
	void testErabiltzaileaEzabatu() {
		// Erabiltzaileak gordeta daudela konprobatu
        List<Erabiltzaile> erabHasieran = gestoreErabiltzaile.getErabiltzaileak();
        assertEquals(2, erabHasieran.size(), "Bi erabiltzaile daude.");

        //Erabiltzaile bat ezabatzen dugu
        gestoreErabiltzaile.erabiltzaileaEzabatu("12345678Z");

        //Manolito erabiltzailea ezabatu dela konprobatu
        List<Erabiltzaile> erabGero = gestoreErabiltzaile.getErabiltzaileak();
        assertEquals(1, erabGero.size(), "Erabiltzaile bakarra dago.");
        assertNull(gestoreErabiltzaile.getErabiltzaileByNAN("12345678Z"), "Manolito ezabatu da.");

        //Beste erabiltzailea dagoela konprobatu
        assertNotNull(gestoreErabiltzaile.getErabiltzaileByNAN("56789101Y"), "Antonio oraindik dago.");

        //Existitzen ez den erabiltzailea ezabatu
        gestoreErabiltzaile.erabiltzaileaEzabatu("99999999A");

        //Zerrenda ez dela aldatu konprobatu
        List<Erabiltzaile> usuariosDespuesDelIntento = gestoreErabiltzaile.getErabiltzaileak();
        assertEquals(1, usuariosDespuesDelIntento.size(), "Zerrenda ez da aldatu");
	}
	
	
	@Test
	void testErabiltzaileaBilatu() {
		//Erabiltzaile eta pasahitza zuzenak dira
        Erabiltzaile emaitza = gestoreErabiltzaile.erabiltzaileaBilatu("12345678Z", "1234");
        assertNotNull(emaitza, "Erabiltzailea aurkitu da.");
        assertEquals("12345678Z", emaitza.getNan(), "NAN zuzena da.");
        
        //Ez da erabiltzailerik aurkitzen
        emaitza = gestoreErabiltzaile.erabiltzaileaBilatu("100000000A", "2345");
        assertNull(emaitza, "Erabiltzailea ez da aurkitu.");
        
        //Pasahitz okerra
        emaitza = gestoreErabiltzaile.erabiltzaileaBilatu("12345678Z", "1321");
        assertNull(emaitza, "Pasahitz okerra, erabiltzailea ez da aurkitu.");
        
        //Ez onartutako erabiltzailea 
        emaitza = gestoreErabiltzaile.erabiltzaileaBilatu("56789101Y", "7891");
        assertNotNull(emaitza, "Onartuta ez dagoen erabiltzailea aurkitu da.");
        assertEquals("56789101Y", emaitza.getNan(), "NAN zuzena da.");
	}
	
	@Test
	void getInfoErabiltzaileak() {
		//Onartutako erabiltzaileen zerrenda
        JSONArray infoErabiltzaileak = gestoreErabiltzaile.getInfoErabiltzaileak();
        
        //Onartuta dagoen erabiltzailea duela konprobatu
        assertEquals(1, infoErabiltzaileak.length(), "La lista de usuarios aprobados debe tener solo 1 usuario.");
        
        //Onartutako erabiltzailea informazio zuzena duela konprobatu
        JSONObject erabO = infoErabiltzaileak.getJSONObject(0);
        assertEquals("12345678Z", erabO.getString("NAN"), "NAN zuzena izan behar da.");
        assertEquals("Manolito", erabO.getString("Izena"), "Izena zuzena izan behar da.");
	}
	
	@Test
	void testAlokatutaDaukaJada() {
		//Erabiltzaileak ez dauka alokairua eginda film horretarako. Metodoari erabiltzailearen NAN eta filma ematen dizkiogu
		boolean erantzuna = gestoreErabiltzaile.alokatutaDaukaJada("12345678Z", film);
		assertFalse(erantzuna);
		//Erabiltzaileari filmaren alokairua gehitzen diogu
		erab.gehituAlokairua(alok);
		//Erabiltzaileak alokairua eginda dauka
		erantzuna = gestoreErabiltzaile.alokatutaDaukaJada("12345678Z", film);
		assertTrue(erantzuna);
	}
	
	@Test
	void testAlokairuaErabiltzailearenZerrendanGehitu() {
		//Erabiltzaileak ez dauka alokairua eginda film horretarako eta badakigu (setUp metodoan konfiguratu dugunaren arabera) erabiltzaileak 'alok' ez badauka bere zerrendan hutsik egongo dela
		assertTrue(erab.getEgindakoAlokairuak().isEmpty());
		//Alokairua gehitu
		gestoreErabiltzaile.alokairuaErabiltzailearenZerrendanGehitu(alok);
		//Orain zerrenda ez da hutsik egongo, 'alok' gehitu diogulako
		assertFalse(erab.getEgindakoAlokairuak().isEmpty());
	}
	
	@Test
	void testGetErabiltzaileByNAN() {
		//Erabiltzailea ez da zerrendan existitzen, metodoak null itzuli beharko luke
		assertNull(gestoreErabiltzaile.getErabiltzaileByNAN("79224266F"));
		//Erabiltzailea zerrendan existitzen da, metodoak Erabiltzaile objektua itzuli beharko luke
		assertEquals(erab, gestoreErabiltzaile.getErabiltzaileByNAN("12345678Z"));
	}

}
