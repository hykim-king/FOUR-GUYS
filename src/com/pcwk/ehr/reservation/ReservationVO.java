package com.pcwk.ehr.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.movie.MovieDao;
import com.pcwk.ehr.theater.TheaterVO;

public class ReservationVO extends DTO {
	private UUID reserveCode; // 예매 id
	private int ScreeningCode; // 상영코드
	private String id;
	private int pax; // 인원
	private int adult;
	private int teenager;
	private String[] seats;
	private int totalPrice; // 가격
	private LocalDate date; //

	public ReservationVO(int screeningCode, String id, int adult, int teenager, String[] seats, int totalPrice) {
		super();
		this.reserveCode = UUID.randomUUID();
		this.ScreeningCode = screeningCode;
		this.id = id;
		this.pax = adult + teenager;
		this.adult = adult;
		this.teenager = teenager;
		this.seats = seats;
		this.totalPrice = totalPrice;
		this.date = LocalDate.now();
	}

	public UUID getReserveCode() {
		return reserveCode;
	}

	public void setReserveCode(UUID reserveCode) {
		this.reserveCode = reserveCode;
	}

	public int getScreeningCode() {
		return ScreeningCode;
	}

	public void setScreeningCode(int screeningCode) {
		ScreeningCode = screeningCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getPax() {
		return pax;
	}

	public void setPax(int pax) {
		this.pax = pax;
	}

	public int getAdult() {
		return adult;
	}

	public void setAdult(int adult) {
		this.adult = adult;
	}

	public int getTeenager() {
		return teenager;
	}

	public void setTeenager(int teenager) {
		this.teenager = teenager;
	}

	public String[] getSeats() {
		return seats;
	}

	public String getSeatsTostring() {
		String result = "";
		for (int i = 0; i < seats.length; i++) {
			if (i == 0) {
				result += seats[i];
			} else {
				result += "," + seats[i];
			}
		}
		return result;
	}

	public void setSeats(String[] seats) {
		this.seats = seats;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "ReservationVO [reserveCode=" + reserveCode + ", ScreeningCode=" + ScreeningCode + ", id=" + id
				+ ", pax=" + pax + ", adult=" + adult + ", teenager=" + teenager + ", seats=" + getSeatsTostring()
				+ ", totalPrice=" + totalPrice + ", date=" + date + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(reserveCode);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReservationVO other = (ReservationVO) obj;
		return Objects.equals(reserveCode, other.reserveCode);
	}

}
