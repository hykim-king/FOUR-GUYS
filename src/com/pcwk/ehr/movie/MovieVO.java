package com.pcwk.ehr.movie;

import java.util.Objects;

import com.pcwk.ehr.cmn.DTO;

public class MovieVO extends DTO {
	// 영화 정보 변수
	private String movieId;		// 영화 ID
	private String movieTitle;	// 영화 제목
	private String genre;		// 영화 장르
	private String releaseDate;	// 개봉 일자
	private String country;		// 제작 국가
	private int    runningTime;	// 러닝 타임
	private int    filmRatings;	// 상영 등급
	private String director;	// 감독
	private String actor;		// 배우
	private double movieRate;	// 영화 평점
	
	// 기본 생성자
	public MovieVO() {
		super();
	}
	
	// 인자 1개짜리 생성자 -> 영화명, 장르, 감독의 해시코드를 합쳐 영화코드 생성
	public MovieVO(String movieTitle, String genre, String director) {
		this.movieTitle = movieTitle;
		this.genre = genre;
		this.director = director;
		this.movieId = generateMovieId(movieTitle, genre, director);
	}
	
	// 영화 코드 생성 메소드(영화 제목, 장르, 감독의 hashCode 조합)
	public String generateMovieId(String movieTitle, String genre, String director) {
		int titleHash = Objects.hashCode(movieTitle);
		int genreHash = Objects.hashCode(genre);
		int directorHash = Objects.hashCode(director);
		
		return Integer.toString(titleHash + genreHash + directorHash);
	}
	
	// 모든 필드를 포함한 생성자
	public MovieVO(String movieTitle, String genre, String releaseDate, String country, int runningTime,
			int filmRatings, String director, String actor, double movieRate) {
		super();
		this.movieTitle = movieTitle;
		this.genre = genre;
		this.releaseDate = releaseDate;
		this.country = country;
		this.runningTime = runningTime;
		this.filmRatings = filmRatings;
		this.director = director;
		this.actor = actor;
		this.movieRate = movieRate;
	}

	public String getMovieId() {
		return movieId;
	}

	public String getMovieTitle() {
		return movieTitle;
	}

	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
		this.movieId = generateMovieId(this.movieTitle, this.genre, this.director);
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
		this.movieId = generateMovieId(this.movieTitle, this.genre, this.director);
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
		this.movieId = generateMovieId(this.movieTitle, this.genre, this.director);
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public double getMovieRate() {
		return movieRate;
	}

	public void setMovieRate(double movieRate) {
		this.movieRate = movieRate;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(movieId);
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
		return Objects.equals(movieId, other.movieId);
	}

	@Override
	public String toString() {
		return "MovieVO [movieId=" + movieId + ", movieTitle=" + movieTitle + ", genre=" + genre + ", releaseDate="
				+ releaseDate + ", country=" + country + ", runningTime=" + runningTime + ", filmRatings=" + filmRatings
				+ ", director=" + director + ", actor=" + actor + ", movieRate=" + movieRate + "]";
	}
	
	
}