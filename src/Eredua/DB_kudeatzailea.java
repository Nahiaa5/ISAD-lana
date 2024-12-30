package Eredua;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import Kontroladorea.*;

public class DB_kudeatzailea {
	
	private static DB_kudeatzailea nDB=null;
	
	private DB_kudeatzailea() {}
	
	public static DB_kudeatzailea getDB() {
		if(nDB==null) {
			nDB=new DB_kudeatzailea();
			
		}
		return nDB;
	}
	
	public List<Erabiltzaile> kargatuErabiltzaileak(){
		List<Erabiltzaile> erabiltzaileak = new ArrayList<>();
        String query = "SELECT * FROM erabiltzaile";

        try (Connection conn = DB_konexioa.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String nan = rs.getString("NAN");
                String izena = rs.getString("izena");
                String abizena = rs.getString("abizena");
                String email = rs.getString("email");
                String pasahitza = rs.getString("pasahitza");
                int admin = rs.getInt("admin");
                int onartuta = rs.getInt("onartuta");
                
                Erabiltzaile erabiltzaile = new Erabiltzaile(nan, izena, abizena, email, pasahitza, admin, onartuta);
                
                erabiltzaileak.add(erabiltzaile);
            }
            
        } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            
        }
        
        return erabiltzaileak;
	}
	
	public List<Alokairua> kargatuAlokairuak() {
		List<Alokairua> alokairuak = new ArrayList<>();
        String query = "SELECT * FROM alokairua";

        try (Connection conn = DB_konexioa.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query);
        	 ResultSet rs = stmt.executeQuery()) {
        	
        	while (rs.next()) {
                String erabiltzaileNAN = rs.getString("erabiltzaileNAN");
                int filmID = rs.getInt("filmID");
                String hasieData = rs.getString("hasData");
                LocalDate hasiData = LocalDate.parse(hasieData);
                
                HasData hasData = new HasData(hasiData);
                
                String bukaData = rs.getString("bukData");
                LocalDate bukData = LocalDate.parse(bukaData);
                
                Film film = GestoreFilm.getKN().bilatuFilma(filmID);
                
                Alokairua alokairua = new Alokairua(film, hasData, bukData);
                
                Erabiltzaile erabiltzailea = GestoreErabiltzaile.getGE().getErabiltzaileByNAN(erabiltzaileNAN);
                erabiltzailea.gehituAlokairua(alokairua);
                
                alokairuak.add(alokairua);
            }
        	
        } catch (SQLException | ClassNotFoundException e) {
        	e.printStackTrace();
        }
        return alokairuak;
	}
	
	public boolean erabiltzaileBerriaSartu(String pNAN, String pIzena, String pAbizena, String pEmail, String pPasahitza) {
		String query = "INSERT INTO Erabiltzaile (NAN, izena, abizena, email, pasahitza) VALUES (?, ?, ?, ?, ?)";
	    boolean ondo = false;
	    try (Connection conn = DB_konexioa.getConexion();
	        PreparedStatement stmt = conn.prepareStatement(query)) {
	        stmt.setString(1, pNAN);
	        stmt.setString(2, pIzena);
	        stmt.setString(3, pAbizena);
	        stmt.setString(4, pEmail);
	        stmt.setString(5, pPasahitza);

	        int rowsAffected = stmt.executeUpdate();

	        if (rowsAffected > 0) {
	            ondo = true;
	        } 

	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	    
	    return ondo;
	}
		
	
	public List<Film> kargatuFilmak() {
        List<Film> filmak = new ArrayList<>();
        String query = "SELECT * FROM Film";

        try (Connection conn = DB_konexioa.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int filmID = rs.getInt("filmID");
                String izenburua = rs.getString("izenburua");
                String aktoreak = rs.getString("aktoreak");
                int urtea = rs.getInt("urtea");
                String generoa = rs.getString("generoa");
                String zuzendaria = rs.getString("zuzendaria");
                String adminNAN = rs.getString("adminNAN");
                boolean katalogoan = rs.getInt("katalogoan") == 1;
                double puntuazioaBb = rs.getDouble("puntuazioaBb");

                Film film = new Film(filmID, izenburua, aktoreak, urtea, generoa, zuzendaria, adminNAN, katalogoan, puntuazioaBb);
                filmak.add(film);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return filmak;
    }
	
	public List<String> iruzkinakKargatu(int filmID) {
        List<String> comments = new ArrayList<>();
        String query = "SELECT iruzkina FROM Puntuazioa WHERE filmID = ?";

        try (Connection conn = DB_konexioa.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, filmID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                comments.add(rs.getString("iruzkina"));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return comments;
    }
	
	public List<Puntuazioa> kargatuPuntuazioak(int filmID) {
        List<Puntuazioa> puntuazioak = new ArrayList<>();
        String query = "SELECT * FROM Puntuazioa WHERE filmID = ?";

        try (Connection conn = DB_konexioa.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, filmID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String NAN = rs.getString("NAN");
                int puntuazioa = rs.getInt("puntuazioa");
                String iruzkina = rs.getString("iruzkina");
                LocalDate data = rs.getDate("data").toLocalDate();
                puntuazioak.add(new Puntuazioa(NAN, filmID, puntuazioa, iruzkina, data));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return puntuazioak;
    }
	
	public boolean puntuazioaExistitzenDa(String NAN, int filmID) {
	    String query = "SELECT COUNT(*) FROM Puntuazioa WHERE NAN = ? AND filmID = ?";
	    try (Connection conn = DB_konexioa.getConexion();
	         PreparedStatement stmt = conn.prepareStatement(query)) {

	        stmt.setString(1, NAN);
	        stmt.setInt(2, filmID);
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt(1) > 0;
	            }
	        }
	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	public void gordePuntuazioa(Puntuazioa puntuazioa) {
        String query = "INSERT INTO Puntuazioa (NAN, filmID, puntuazioa, iruzkina, data) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DB_konexioa.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, puntuazioa.getNAN());
            stmt.setInt(2, puntuazioa.getFilmID());
            stmt.setInt(3, puntuazioa.getPuntuazioa());
            stmt.setString(4, puntuazioa.getIruzkina());
            stmt.setDate(5, java.sql.Date.valueOf(puntuazioa.getData()));
            stmt.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
	
	public void eguneratuPuntuazioa(Puntuazioa puntuazioa) {
        String query = "UPDATE Puntuazioa SET puntuazioa = ?, iruzkina = ?, data = ? WHERE NAN = ? AND filmID = ?";

        try (Connection conn = DB_konexioa.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, puntuazioa.getPuntuazioa());
            stmt.setString(2, puntuazioa.getIruzkina());
            stmt.setDate(3, java.sql.Date.valueOf(puntuazioa.getData()));
            stmt.setString(4, puntuazioa.getNAN());
            stmt.setInt(5, puntuazioa.getFilmID());
            stmt.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
	
	public double kalkulatuPuntuBb(int filmID) {
	    double puntuacionMedia = 0.0;
	    String query = "SELECT AVG(puntuazioa) AS puntuacion_media FROM Puntuazioa WHERE filmID = ?";

	    try (Connection conn = DB_konexioa.getConexion();
	         PreparedStatement stmt = conn.prepareStatement(query)) {

	        stmt.setInt(1, filmID);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            puntuacionMedia = rs.getDouble("puntuacion_media");
	        }

	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }

	    return puntuacionMedia;
	}
	
	public void eguneratuPuntuBb(int filmID, double puntuBb) {
		String query="UPDATE Film SET puntuazioaBb = ? WHERE filmID = ?";
		try(Connection conn = DB_konexioa.getConexion();
				PreparedStatement stmt = conn.prepareStatement(query)){
			stmt.setDouble(1, puntuBb);
			stmt.setInt(2, filmID);
			stmt.executeUpdate();
		}catch(SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public List<Puntuazioa> kargatuPuntuazioak() {
	    List<Puntuazioa> puntuazioak = new ArrayList<>();
	    String query = "SELECT * FROM Puntuazioa";  

	    try (Connection conn = DB_konexioa.getConexion();
	         PreparedStatement stmt = conn.prepareStatement(query);
	         ResultSet rs = stmt.executeQuery()) {

	        while (rs.next()) {
	            String NAN = rs.getString("NAN");
	            int filmID = rs.getInt("filmID");
	            int puntuazioa = rs.getInt("puntuazioa");
	            String iruzkina = rs.getString("iruzkina");
	            LocalDate data = rs.getDate("data").toLocalDate();
	            
	            puntuazioak.add(new Puntuazioa(NAN, filmID, puntuazioa, iruzkina, data));
	        }

	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }

	    return puntuazioak;
	}

}
