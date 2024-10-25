package com.pcwk.ehr.movie;

import java.util.Objects;

import com.pcwk.ehr.cmn.DTO;

public class MovieVO extends DTO {
	// 영화 정보 변수
	private int movieId; // 영화 ID
	private String movieTitle; // 영화 제목
	private String genre; // 영화 장르
	private String releaseDate; // 개봉 일자
	private String country; // 제작 국가
	private int runningTime; // 러닝 타임
	private int filmRatings; // 상영 등급
	private String director; // 감독
	private String actor; // 배우

	public MovieVO() {
		super();
	}

	public MovieVO(int movieId, String movieTitle, String genre, String releaseDate, String country, int runningTime,
			int filmRatings, String director, String actor) {
		super();
		this.movieId = movieId;
		this.movieTitle = movieTitle;
		this.genre = genre;
		this.releaseDate = releaseDate;
		this.country = country;
		this.runningTime = runningTime;
		this.filmRatings = filmRatings;
		this.director = director;
		this.actor = actor;
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public String getMovieTitle() {
		return movieTitle;
	}

	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getRunningTime() {
		return runningTime;
	}

	public void setRunningTime(int runningTime) {
		this.runningTime = runningTime;
	}

	public int getFilmRatings() {
		return filmRatings;
	}

	public void setFilmRatings(int filmRatings) {
		this.filmRatings = filmRatings;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	@Override
	public int hashCode() {
		return Objects.hash(director, movieTitle, releaseDate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MovieVO other = (MovieVO) obj;
		return Objects.equals(director, other.director) && Objects.equals(movieTitle, other.movieTitle)
				&& Objects.equals(releaseDate, other.releaseDate);
	}

	@Override
	public String toString() {
		return "MovieVO [movieId=" + movieId + ", movieTitle=" + movieTitle + ", genre=" + genre + ", releaseDate="
				+ releaseDate + ", country=" + country + ", runningTime=" + runningTime + ", filmRatings=" + filmRatings
				+ ", director=" + director + ", actor=" + actor + "]";
	}

}