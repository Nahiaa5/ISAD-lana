package JUnits;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import Eredua.HasData;

class HasDataTest {
	private HasData hasData;

	@BeforeEach
	void setUp() throws Exception {
		hasData = new HasData(LocalDate.of(2025, 1, 18));
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testKalkulatuBiEgun() {
		//2025/01/18 data jarri dugu setUp metodoan; 2025/01/20 data kalkulatu beharko luke
		LocalDate em = hasData.kalkulatuBiEgun();
		assertEquals(em, LocalDate.of(2025, 1, 20));
	}

}
