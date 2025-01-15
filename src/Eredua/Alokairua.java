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
	
	public boolean filmaKointziditu(Film pFilm) {
		return this.film.getFilmID() == pFilm.getFilmID();
	}
	
	public boolean alokairuaAktiboDago() {
		LocalDate today = LocalDate.now();
	    return !bukData.isBefore(today);
	}
	
}
