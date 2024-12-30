package Eredua;

import java.time.LocalDate;

public class HasData {

	private LocalDate hasData;
	
	public HasData(LocalDate hasData) {
		this.hasData = hasData;
	}
	
	public LocalDate getData() {
		return hasData;
	}
	
	public LocalDate kalkulatuBiEgun() {
		return hasData.plusDays(2);
	}
}
