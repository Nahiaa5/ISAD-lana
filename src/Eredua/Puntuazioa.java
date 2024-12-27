package Eredua;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Puntuazioa {
	private String NAN;
    private int filmID;
    private int puntuazioa; // 1-5
    private String iruzkina;
    private LocalDate data;

    public Puntuazioa(String pNAN, int pFilmID, int pPuntuazioa, String pIruzkina, LocalDate pData) {
        this.NAN = pNAN;
        this.filmID = pFilmID;
        this.puntuazioa = pPuntuazioa;
        this.iruzkina = pIruzkina;
        this.data = pData;
    }
    
    public String getNAN() {
    	return this.NAN;
    }
    
    public int getFilmID() {
    	return this.filmID;
    }
    
    public int getPuntuazioa() {
    	return this.puntuazioa;
    }
    
    public void setPuntuazioa(int pPuntu) {
        if (pPuntu >= 1 && pPuntu <= 5) {
            this.puntuazioa = pPuntu;
        } else {
            throw new IllegalArgumentException("Puntuazioa 1-5 tartean egon behar da.");
        }
    }
    
    public String getIruzkina() {
    	return this.iruzkina;
    }
    
    public void setIruzkina(String pIruzkin) {
    	this.iruzkina=pIruzkin;
    }
    public LocalDate getData(){
    	return this.data;
    }
    
    public void gordePuntuazioa() throws SQLException, ClassNotFoundException {
    	String query="INSERT INTO Puntuazioa (NAN, filmID, puntuazioa, iruzkina, data) VALUES (?, ?, ?, ?, ?)";
    	try(PreparedStatement stmt=DB_konexioa.getConexion().prepareStatement(query)){
    		stmt.setString(1, NAN);
    		stmt.setInt(2, filmID);
    		stmt.setInt(3, puntuazioa);
    		stmt.setString(4, iruzkina);
    		stmt.setDate(5, java.sql.Date.valueOf(data));
    		stmt.executeUpdate();
    	}
    }
 
    public void eguneratuPuntuazioa() throws SQLException, ClassNotFoundException {
    	String query="UPDATE Puntuazioa SET puntuazioa = ?, iruzkina = ?, data = ? WHERE NAN = ? AND filmID = ?";
    	try(PreparedStatement stmt=DB_konexioa.getConexion().prepareStatement(query)){
    		stmt.setInt(1, puntuazioa);
    		stmt.setString(2, iruzkina);
    		stmt.setDate(3, java.sql.Date.valueOf(data));
    		stmt.setString(4, NAN);
    		stmt.setInt(5, filmID);
    		stmt.executeUpdate();
    	}
    }
    
    public boolean puntuazioaExistitzenDa() throws SQLException, ClassNotFoundException {
        String query = "SELECT COUNT(*) FROM Puntuazioa WHERE NAN = ? AND filmID = ?";
        try (PreparedStatement stmt = DB_konexioa.getConexion().prepareStatement(query)) {
            stmt.setString(1, NAN);
            stmt.setInt(2, filmID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; //true si existe
                }
            }
        }
        return false;
    }
    
    public void ezabatuPuntuazioa() throws SQLException, ClassNotFoundException {
    	String query="DELETE FROM Puntuazioa WHERE NAN = ? AND filmID = ?";
    	try(PreparedStatement stmt=DB_konexioa.getConexion().prepareStatement(query)){
    		stmt.setString(1, NAN);
    		stmt.setInt(2, filmID);
    		stmt.executeUpdate();
    	}
    }
}