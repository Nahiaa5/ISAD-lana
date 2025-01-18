package JUnits;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Eredua.Film;
import Kontroladorea.GestoreFilm;
import Kontroladorea.GestoreNagusia;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Set;
import java.util.HashSet;

class GestoreNagusiaTest {
	private GestoreFilm gestoreFilm;
    private Film film1, film2;

	@BeforeEach
	void setUp() throws Exception {
		gestoreFilm = GestoreFilm.getKN();
        gestoreFilm.getFilmak().clear();  
        
        film1 = new Film(1, "La la land", "Ryan Gosling", "2021", "Musikala", "Damien Chazelle", "79224675A", true, 4.00, "resources/LaLaLand.mp4"); 
        film2 = new Film(2, "Interstellar", "Matthew McConaughey, Jessica Chastain", "2014", "Zientzia fikzioa", "Christopher Nolan", "79224675A", true, 2.50, "resources/Interstellar.mp4");  
        
        gestoreFilm.getFilmak().add(film1);
        gestoreFilm.getFilmak().add(film2);
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
		//JSONArray honek filmen izenak eta puntuazioak gordetzen ditu
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
	
	

}
