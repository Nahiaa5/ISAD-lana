package Eredua;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;
import java.util.stream.Collectors;

public class KatalogoNagusia extends Observable {
	private List<Film> filmak;
	private List<Film> jatorrizkoFilmak;
	private static KatalogoNagusia nKN=null;
	
	private KatalogoNagusia() {
		filmak=new ArrayList<>();
		loadFilmak();
	}
	
	public static KatalogoNagusia getKN() {
		if(nKN==null) {
			nKN=new KatalogoNagusia();
			
		}
		return nKN;
	}
	
	public List<Film> getFilmak(){
		return this.filmak;
	}
	
	public void filmaBilatu(String izena){
		if(izena==null || izena.trim().isEmpty()) {
			this.filmak=new ArrayList<>(jatorrizkoFilmak);
		}else {
			List<Film> emaitza=jatorrizkoFilmak.stream()
					.filter(film->film.getIzenburua().toLowerCase().contains(izena))
					.collect(Collectors.toList());
			if(emaitza.isEmpty()) {
				this.filmak=new ArrayList<>();
			}else {
				this.filmak=new ArrayList<>(emaitza);
			}
		}
		setChanged();
		notifyObservers();
	}
	public void loadFilmak() {
	    List<Film> emaitza = new ArrayList<>();
	    
	    String query = "SELECT * FROM film";
	    
	    try (PreparedStatement stmt = DB_konexioa.getConexion().prepareStatement(query)) {
	        ResultSet rs = stmt.executeQuery();
	        
	        while (rs.next()) {
	            int filmID = rs.getInt("filmID");
	            String izenburua = rs.getString("izenburua");
	            String aktoreak = rs.getString("aktoreak");
	            int urtea = rs.getInt("urtea");
	            String generoa = rs.getString("generoa");
	            String zuzendaria = rs.getString("zuzendaria");
	            String erabiltzaileNAN = rs.getString("erabiltzaileNAN");
	            boolean katalogoan = rs.getInt("katalogoan") == 1;
	            double puntuazioaBb = rs.getDouble("puntuazioaBb");
	            Film film = new Film(filmID, izenburua, aktoreak, urtea, generoa, zuzendaria, erabiltzaileNAN, katalogoan, puntuazioaBb);
	            emaitza.add(film);
	        }
	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	    
	    this.filmak = emaitza; 
	    this.jatorrizkoFilmak=new ArrayList<>(filmak);
	    setChanged();
	    notifyObservers();
	}
	
	public void ordenatuPuntuazioz() {
		this.filmak.sort(Comparator.comparingDouble(Film::getPuntuazioaBb).reversed());
		setChanged();
		notifyObservers();
	}
}