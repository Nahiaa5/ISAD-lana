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
        
        System.out.println("Título: " + izenburua);
        System.out.println("Año: " + year);
        
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
		System.out.println("cuando este lo demas pues aqui se mandan los datos");
		System.out.println(datuak);	 
		try {
			Connection konexioa = DB_konexioa.getConexion();
			String query = "SELECT COUNT(*) AS count FROM film WHERE izenburua=? AND urtea=?;";
			PreparedStatement cst = konexioa.prepareStatement(query);
			cst.setString(1, datuak.getString("Title"));
			cst.setString(2, datuak.getString("Year"));
			ResultSet rs = cst.executeQuery();
			if (rs.next() && rs.getInt("count") > 0) {
                setChanged();
                notifyObservers(false);
            } 
			else {
				System.out.println(rs.getInt("count"));
				query="INSERT into film(izenburua, aktoreak, urtea, generoa, zuzendaria, erabiltzaileNAN, katalogoan) values(?, ?, ?, ?, ?, ?, ?);" ;
				PreparedStatement st = konexioa.prepareStatement(query);
				st.setString(1, datuak.getString("Title"));
				st.setString(2, datuak.getString("Actors"));
				st.setString(3, datuak.getString("Year"));
				st.setString(4, datuak.getString("Genre"));
				st.setString(5, datuak.getString("Director"));
				st.setString(6, "79224675A");
				st.setString(7, "0");
				st.executeUpdate();
				st.close();
			}
			cst.close();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}