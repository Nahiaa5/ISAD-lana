package Kontroladorea;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Observable;
import org.json.JSONArray;
import org.json.JSONObject;
import Bista.KZ_XehetasunakIkusi;
import Kontroladorea.GestoreFilm;

@SuppressWarnings("deprecation")
public class GestoreKatalogoZabaldua extends Observable{
	private static GestoreKatalogoZabaldua nKZK;
	private JSONObject datuak;
	
	private GestoreKatalogoZabaldua(){
	}
	
	public static GestoreKatalogoZabaldua getnZK(){
		if(nKZK==null) {
			nKZK=new GestoreKatalogoZabaldua();
		}
		return nKZK;
	}

	public void FilmakBilatu(String izena) {
		try {
			String apiKey = "209e2977";
			String urlString = "http://www.omdbapi.com/?apikey=" + apiKey + "&s=" + izena.replace(" ", "%20");
			
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer respuesta = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				respuesta.append(inputLine);
			}
			in.close();

	        // Parse the JSON response
	        JSONObject jsonResponse = new JSONObject(respuesta.toString());
	        
	        // Check if the response contains movies
	        if (jsonResponse.has("Search")) {
	            // Get the array of movies
	            JSONArray movies = jsonResponse.getJSONArray("Search");
	            setChanged();
	    		notifyObservers(movies);
	    		
	        } else {
	            System.out.println("No movies found with the title: " + izena);
	        }
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void xehetasunakErakutsi(String informazioa) {
			datuak = xehetasunakBilatu(informazioa);
	        KZ_XehetasunakIkusi xehetasunak = new KZ_XehetasunakIkusi();
	        this.addObserver(xehetasunak);
	        setChanged();
	        notifyObservers(datuak);
	        xehetasunak.setVisible(true);       
	}
	
	public JSONObject xehetasunakBilatu(String informazioa) {
		String[] zatiak = informazioa.split(" \\(");
        String izenburua = zatiak[0].trim();
        String year = zatiak[1].replace(")", "").trim();
        
		try {
			String apiKey = "209e2977";
			String urlString = "http://www.omdbapi.com/?apikey=" + apiKey + "&t=" + izenburua.replace(" ", "%20") + "&y=" + year;
			
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer respuesta = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				respuesta.append(inputLine);
			}
			in.close();

	        datuak = new JSONObject(respuesta.toString());
	        
		}catch (Exception e) {
			e.printStackTrace();
		}
		return datuak;
	}
	
	public void bidaliEskaera() {
		String izenburua = datuak.getString("Title");
		String urtea = datuak.getString("Year");
		if (GestoreFilm.getKN().badagoFilma(izenburua, urtea)) {
            setChanged();
            notifyObservers(false);
        } 
		else {
			GestoreFilm.getKN().addFilma(datuak);
		}
	}
}