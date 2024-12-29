package Eredua;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Observable;
import org.json.JSONArray;
import org.json.JSONObject;
import Bista.KZ_XehetasunakIkusi;
import Kontroladorea.GestoreFilm;

@SuppressWarnings("deprecation")
public class KatalogoZabalduaKargatu extends Observable{
	private static KatalogoZabalduaKargatu nKZK;
	private JSONObject datuak;
	
	private KatalogoZabalduaKargatu(){
	}
	
	public static KatalogoZabalduaKargatu getnZK(){
		if(nKZK==null) {
			nKZK=new KatalogoZabalduaKargatu();
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
	
	public void xehetasunakBilatu(String informazioa) {
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
	        KZ_XehetasunakIkusi xehetasunak = new KZ_XehetasunakIkusi();
	        this.addObserver(xehetasunak);
	        setChanged();
	        notifyObservers(datuak);
	        xehetasunak.setVisible(true);       
	        
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void bidaliEskaera() {
		String izenburua = datuak.getString("Title");
		Integer urtea = datuak.getInt("Year");
		if (GestoreFilm.getKN().badagoFilma(izenburua, urtea)) {
            setChanged();
            notifyObservers(false);
        } 
		else {
			GestoreFilm.getKN().addFilma(datuak);
		}
	}
}