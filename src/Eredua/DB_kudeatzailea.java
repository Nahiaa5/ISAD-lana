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
	
	public void alokairuaGorde(String erabNAN, int filmID, LocalDate hasData, LocalDate bukData) {
	    String checkQuery = "SELECT COUNT(*) FROM HasData WHERE data = ?";
	    String insertHasDataQuery = "INSERT INTO HasData (data) VALUES (?)";
	    String insertAlokairuaQuery = "INSERT INTO Alokairua (erabiltzaileNAN, filmID, hasData, bukData) VALUES (?, ?, ?, ?)";

	    try (Connection conn = DB_konexioa.getConexion()) {
	        // HasData-n erregistroa jada existitzen den egiaztatu
	        boolean exists;
	        try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
	            checkStmt.setDate(1, java.sql.Date.valueOf(hasData));
	            try (ResultSet rs = checkStmt.executeQuery()) {
	                rs.next();
	                exists = rs.getInt(1) > 0;
	            }
	        }

	        // HasData-n balioa gordetzen du soilik ez bada jada existitzen
	        if (!exists) {
	            try (PreparedStatement insertHasDataStmt = conn.prepareStatement(insertHasDataQuery)) {
	                insertHasDataStmt.setDate(1, java.sql.Date.valueOf(hasData));
	                insertHasDataStmt.executeUpdate();
	            }
	        }

	        // Alokairua gorde datu-basean
	        try (PreparedStatement insertAlokairuaStmt = conn.prepareStatement(insertAlokairuaQuery)) {
	            insertAlokairuaStmt.setString(1, erabNAN);
	            insertAlokairuaStmt.setInt(2, filmID);
	            insertAlokairuaStmt.setDate(3, java.sql.Date.valueOf(hasData));
	            insertAlokairuaStmt.setDate(4, java.sql.Date.valueOf(bukData));
	            insertAlokairuaStmt.executeUpdate();
	        }

	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }
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
                String urtea = rs.getString("urtea");
                String generoa = rs.getString("generoa");
                String zuzendaria = rs.getString("zuzendaria");
                String adminNAN = rs.getString("adminNAN");
                boolean katalogoan = rs.getInt("katalogoan") == 1;
                double puntuazioaBb = rs.getDouble("puntuazioaBb");
                String path = rs.getString("path");

                Film film = new Film(filmID, izenburua, aktoreak, urtea, generoa, zuzendaria, adminNAN, katalogoan, puntuazioaBb, path);
                filmak.add(film);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return filmak;
    }
	
	public void gordePuntuazioBb(int filmID, double puntuazioaBb) {
		String query="UPDATE Film SET puntuazioaBb = ? WHERE filmID = ?";
		try(Connection conn = DB_konexioa.getConexion();
				PreparedStatement stmt = conn.prepareStatement(query)){
			stmt.setDouble(1, puntuazioaBb);
			stmt.setInt(2, filmID);
			stmt.executeUpdate();
		}catch(SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
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
