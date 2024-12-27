package Eredua;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

public class Film extends Observable{
    private int filmID;
    private String izenburua; 
    private String aktoreak; 
    private int urtea; 
    private String generoa; 
    private String zuzendaria; 
    private String erabiltzaileNAN; 
    private boolean katalogoan; 
    private double puntuazioaBb;
    private List<String> iruzkinak;
    
    public Film(int filmID, String izenburua, String aktoreak, int urtea, String generoa, String zuzendaria, String erabiltzaileNAN, boolean katalogoan, double puntuazioaBb) {
        this.filmID = filmID;
        this.izenburua = izenburua;
        this.aktoreak = aktoreak;
        this.urtea = urtea;
        this.generoa = generoa;
        this.zuzendaria = zuzendaria;
        this.erabiltzaileNAN = erabiltzaileNAN;
        this.katalogoan = katalogoan;
        this.puntuazioaBb = puntuazioaBb;
        this.iruzkinak=new ArrayList<>();
    }

    public int getFilmID() {
        return filmID;
    }

    public void setFilmID(int filmID) {
        this.filmID = filmID;
    }

    public String getIzenburua() {
        return izenburua;
    }

    public void setIzenburua(String izenburua) {
        this.izenburua = izenburua;
        setChanged();
        notifyObservers();
    }

    public String getAktoreak() {
        return aktoreak;
    }

    public void setAktoreak(String aktoreak) {
        this.aktoreak = aktoreak;
    }

    public int getUrtea() {
        return urtea;
    }

    public void setUrtea(int urtea) {
        this.urtea = urtea;
    }

    public String getGeneroa() {
        return generoa;
    }

    public void setGeneroa(String generoa) {
        this.generoa = generoa;
    }

    public String getZuzendaria() {
        return zuzendaria;
    }

    public void setZuzendaria(String zuzendaria) {
        this.zuzendaria = zuzendaria;
    }

    public String getErabiltzaileNAN() {
        return erabiltzaileNAN;
    }

    public void setErabiltzaileNAN(String erabiltzaileNAN) {
        this.erabiltzaileNAN = erabiltzaileNAN;
    }

    public boolean isKatalogoan() {
        return katalogoan;
    }

    public void setKatalogoan(boolean katalogoan) {
        this.katalogoan = katalogoan;
    }
    
    public double getPuntuazioaBb() {
    	return this.puntuazioaBb;
    }
    public void setPuntuazioaBb(double puntuBb) {
    	this.puntuazioaBb = puntuBb;
    	setChanged();
    	notifyObservers();
    }
    
    public List<String> getIruzkinak(){
    	List<String> iruzkinakOrdenatuta=new ArrayList<>(this.iruzkinak);
    	Collections.reverse(iruzkinakOrdenatuta);
    	return iruzkinakOrdenatuta;
    }
    
    public void kalkulatuBatezBestekoPuntuazioa() {
        String query = "SELECT AVG(puntuazioa) AS puntuazioaBb FROM Puntuazioa WHERE filmID = ?";
        try (PreparedStatement stmt = DB_konexioa.getConexion().prepareStatement(query)) {
            stmt.setInt(1, this.filmID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                this.puntuazioaBb = rs.getDouble("puntuazioaBb");
            } else {
                this.puntuazioaBb = 0; 
            }
            
            String updateQuery = "UPDATE Film SET puntuazioaBb = ? WHERE filmID = ? ;";
            try(PreparedStatement stmt2 = DB_konexioa.getConexion().prepareStatement(updateQuery)){
            	stmt2.setDouble(1, this.puntuazioaBb);
            	stmt2.setInt(2, this.filmID);
            	stmt2.executeUpdate();
            }
            
            setChanged();
            notifyObservers();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public void gordeIruzkinak(){
    	String query = "SELECT iruzkina FROM Puntuazioa WHERE filmID = ?";
    	try(PreparedStatement stmt = DB_konexioa.getConexion().prepareStatement(query)){
    		stmt.setInt(1, this.filmID);
    		ResultSet rs = stmt.executeQuery();
    		iruzkinak.clear();
    		while(rs.next()) {
    			iruzkinak.add(rs.getString("iruzkina"));
    		}
    		setChanged();
    		notifyObservers();
    	}catch(SQLException | ClassNotFoundException e) {
    		e.printStackTrace();
    	}
    }

    // Método toString (para visualización)
    @Override
    public String toString() {
        return "Film{" +
                "filmID=" + filmID +
                ", izenburua='" + izenburua + '\'' +
                ", aktoreak='" + aktoreak + '\'' +
                ", urtea=" + urtea +
                ", generoa='" + generoa + '\'' +
                ", zuzendaria='" + zuzendaria + '\'' +
                ", erabiltzaileNAN='" + erabiltzaileNAN + '\'' +
                ", katalogoan=" + katalogoan +
                ", Batez besteko puntuazioa=" + puntuazioaBb +
                '}';
    }
}
