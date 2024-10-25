package com.pcwk.ehr.movie;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.WorkDiv;
import com.pcwk.ehr.screening.ScreeningDao;
import com.pcwk.ehr.screening.ScreeningVO;

public class MovieDao implements WorkDiv<MovieVO> {

	private final String fileName = "movie.csv";

	public static HashMap<Integer, MovieVO> movies = new HashMap<Integer, MovieVO>();

	public MovieDao() {
		super();

		readFile(fileName);
	}

	/**
	 * 1(성공) / 0(실패) / 2(memberId 중복)
	 */

	// boolean isExistsMovie
	private boolean isExistsMovie(MovieVO movie) {
		boolean flag = false;

		for (MovieVO vo : movies.values()) {
			if (vo.equals(movie)) {
				flag = true;
				return flag;
			}
		}
		return flag;
	}

	@Override
	public int doSave(MovieVO param) {
		int flag = 1;
		if (isExistsMovie(param) == true) {
			flag = 2;
			return flag;
		}

		MovieVO result = movies.put(movies.size() + 1, param);

		if (result != null) {
			flag = 0;
			return flag;
		}

		writeFile(fileName);

		return flag;
	}

	@Override
	public int doUpdate(MovieVO param) {
		return 0;
	}

	public void reSort() {
		ScreeningDao dao = new ScreeningDao();
		HashMap<Integer, MovieVO> newMap = new HashMap<>();
		int newKey = 1;
		for (int key : movies.keySet()) {
			newMap.put(newKey, movies.get(key));
			newMap.get(newKey).setMovieId(newKey);
			
			for(ScreeningVO scr : ScreeningDao.screenings.values()) {
				if(scr.getMovieCode() == key) {
					scr.setMovieCode(newKey);
				}
			}
			
			newKey++;
		}
		movies = newMap;
		writeFile(fileName);
		dao.writeFile("screening.csv");

	}

	public int doDelete(int key) {
		// 영화 목록에서 영화를 찾고 삭제
		int flag = 0;

		flag = movies.remove(key, movies.get(key)) == true ? 1 : 0;
		if (flag == 1)
			reSort();

		return flag;
	}

	@Override
	public MovieVO doSelectOne(MovieVO param) {
		return null;
	}

	@Override
	public List<MovieVO> doRetrieve(DTO param) {
		return null;
	}

	public MovieVO stringToMovie(String data) {
		MovieVO out = null;

		String movieStr = data;

		String[] movieArr = movieStr.split("@");

		int movieId = Integer.parseInt(movieArr[0]);
		String movieTitle = movieArr[1];
		String genre = movieArr[2];
		String releaseDate = movieArr[3];
		String country = movieArr[4];
		int runningTime = Integer.parseInt(movieArr[5]);
		int filmRatings = Integer.parseInt(movieArr[6]);
		String director = movieArr[7];
		String actor = movieArr[8];

		out = new MovieVO(movieId, movieTitle, genre, releaseDate, country, runningTime, filmRatings, director, actor);

		return out;
	}

	@Override
	public int readFile(String path) {
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String data = "";

			while ((data = br.readLine()) != null) {
				MovieVO outVO = stringToMovie(data);
				movies.put(outVO.getMovieId(), outVO);
			}

		} catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
		}
		return movies.size();
	}

	public int writeFile(String path) {

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
			for (MovieVO movie : movies.values()) {
				bw.write(movie.getMovieId() + "@" + movie.getMovieTitle() + "@" + movie.getGenre() + "@"
						+ movie.getReleaseDate() + "@" + movie.getCountry() + "@" + movie.getRunningTime() + "@"
						+ movie.getFilmRatings() + "@" + movie.getDirector() + "@" + movie.getActor() + "\n");
			}
		} catch (IOException e) {
			System.out.println("IOException during writing: " + e.getMessage());
		}

		return movies.size();
	}

	@Override
	public int doDelete(MovieVO param) {
		// TODO Auto-generated method stub
		return 0;
	}

}
