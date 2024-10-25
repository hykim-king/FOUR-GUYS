package com.pcwk.ehr.theater;

import java.util.Arrays;
import java.util.Objects;

import com.pcwk.ehr.cmn.DTO;

public class TheaterVO extends DTO {
	private String TheaterName;
	private String Screen;
	private String[][] seats;
	private int totalSeats;
	private int remainSeats;

	public TheaterVO(String theaterName) {
		super();
		TheaterName = theaterName;
		Screen = "1관";
		seats = initializeSeats();
		totalSeats = 100;
		remainSeats = totalSeats;
	}

	private String[][] initializeSeats() {
		String[] rows = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
		String[][] initArr = new String[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				initArr[i][j] = rows[i] + j;
			}
		}
		return initArr;
	}

	public String getTheaterName() {
		return TheaterName;
	}

	public void setTheaterName(String theaterName) {
		TheaterName = theaterName;
	}

	public String getScreen() {
		return Screen;
	}

	public void setScreen(String screen) {
		Screen = screen;
	}

	public String[][] getSeats() {
		return seats;
	}

	public void setSeats(String[][] seats) {
		this.seats = seats;
	}

	public int getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}

	public int getRemainSeats() {
		return remainSeats;
	}

	public void setRemainSeats(int remainSeats) {
		this.remainSeats = remainSeats;
	}

	@Override
	public int hashCode() {
		return Objects.hash(TheaterName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TheaterVO other = (TheaterVO) obj;
		return Objects.equals(TheaterName, other.TheaterName);
	}

	@Override
	public String toString() {
		return "TheaterVO [TheaterName=" + TheaterName + ", Screen=" + Screen + ", seats=" + Arrays.toString(seats)
				+ "]";
	}

}
