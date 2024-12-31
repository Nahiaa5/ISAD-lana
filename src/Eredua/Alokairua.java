package Eredua;

import java.time.LocalDate;

public class Alokairua {

	private LocalDate bukData;
	private HasData hasData;
	private Film film;
	
	public Alokairua(Film film, HasData hasData, LocalDate bukData) {
		this.film = film;
		this.hasData = hasData;
		this.bukData = bukData;
	}
	public Film getFilm() {
		return this.film;
	}
	
}
