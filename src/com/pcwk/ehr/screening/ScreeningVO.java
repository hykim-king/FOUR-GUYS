package com.pcwk.ehr.screening;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.movie.MovieDao;
import com.pcwk.ehr.theater.TheaterVO;



public class ScreeningVO extends DTO {
	private int screeningCode; // 상영코드
	private int movieCode; // 영화코드
	private LocalDate screeningDate; // 상영날짜
	private LocalTime screeningStartTime; // 상영시작시간
	private LocalTime screeningEndtime; // 상영끝나는시간
	private TheaterVO theater; // 극장객체
	private int ticketPrice = 10000; // 가격
	
	
	public ScreeningVO(int screeningCode, int movieCode, LocalDate screeningDate, LocalTime screeningStartTime,
			String theaterName) {
		super();
		this.screeningCode = screeningCode;
		this.movieCode = movieCode;
		this.screeningDate = screeningDate;
		this.screeningStartTime = screeningStartTime;
		this.screeningEndtime = screeningStartTime.plusMinutes(MovieDao.movies.get(movieCode).getRunningTime());
		this.theater = new TheaterVO(theaterName);
	}

	public int getScreeningCode() {
		return screeningCode;
	}

	public void setScreeningCode(int screeningCode) {
		this.screeningCode = screeningCode;
	}

	public int getMovieCode() {
		return movieCode;
	}

	public void setMovieCode(int movieCode) {
		this.movieCode = movieCode;
	}

	public LocalDate getScreeningDate() {
		return screeningDate;
	}

	public void setScreeningDate(LocalDate screeningDate) {
		this.screeningDate = screeningDate;
	}

	public LocalTime getScreeningStartTime() {
		return screeningStartTime;
	}

	public void setScreeningStartTime(LocalTime screeningStartTime) {
		this.screeningStartTime = screeningStartTime;
	}

	public LocalTime getScreeningEndtime() {
		return screeningEndtime;
	}

	public void setScreeningEndtime(LocalTime screeningEndtime) {
		this.screeningEndtime = screeningEndtime;
	}

	public TheaterVO getTheater() {
		return theater;
	}

	public void setTheater(TheaterVO theater) {
		this.theater = theater;
	}

	public int getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(int ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	@Override
	public int hashCode() {
		return Objects.hash(screeningCode);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScreeningVO other = (ScreeningVO) obj;
		return screeningCode == other.screeningCode;
	}

	@Override
	public String toString() {
		return "ScreeningVO [screeningCode=" + screeningCode + ", movieCode=" + movieCode + ", screeningDate="
				+ screeningDate + ", screeningStartTime=" + screeningStartTime + ", screeningEndtime="
				+ screeningEndtime + ", theater=" + theater + ", ticketPrice=" + ticketPrice + "]";
	}

}
