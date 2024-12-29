package Eredua;

public class Erabiltzaile {
	private String nan;
	private String izena;
	private String email;
	private String pasahitza;
	private String abizena;
	private int admin;
	private int onartuta;
	
	public Erabiltzaile(String pNan, String pIzena, String pEmail, String pPasahitza, String pAbizena, int pAdmin, int pOnartuta) {
		nan = pNan;
		izena = pIzena;
		email = pEmail;
		pasahitza = pPasahitza;
		abizena = pAbizena;
		admin = pAdmin;
		onartuta = pOnartuta;
	}

}
