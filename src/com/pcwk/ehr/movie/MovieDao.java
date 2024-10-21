package com.pcwk.ehr.movie;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.WorkDiv;

public class MovieDao implements WorkDiv<MovieVO> {
	
	private final String fileName = "movie.csv";
	
	public static List<MovieVO> movies = new ArrayList<MovieVO>();
	
	public MovieDao() {
		super();
		
		int count = readFile(fileName);

		// -------------------------------

		// -------------------------------
	}
	
	public void displayList(List<MovieVO> list) {
		if (list.size() > 0) {
			String message = "===============Movie List===============";
			System.out.println(message);
			
			for (MovieVO vo : list) {
				System.out.println(vo);
			}
		} else {
			System.out.println("영화 정보가 없습니다.");
		}
	}
	
	/**
	 * 1(성공) / 0(실패) / 2(memberId 중복)
	 */

	// boolean isExistsMovie
	private boolean isExistsMovie(MovieVO movie) {
		boolean flag = false;

		for (MovieVO vo : movies) {
			// param, vo의 movieId 비교
			if (vo.getMovieId().equals(movie.getMovieId())) {
				flag = true;
				return flag;
			}
		}
		return flag;
	}

	@Override
	public int doSave(MovieVO param) {
		int flag = 0;
		
		if (isExistsMovie(param) == true) {
			flag = 2;
			return flag;
		}
		
		boolean check = this.movies.add(param);
		flag = check == true ? 1 : 0;
		
		return flag;
	}

	@Override
	public int doUpdate(MovieVO param) {
		return 0;
	}

	@Override
	public int doDelete(MovieVO param) {
		// 영화 목록에서 영화를 찾고 삭제
		int flag = 0;
		
		flag = movies.remove(param) == true ? 1 : 0;
		
		return flag;
	}

	@Override
	public MovieVO doSelectOne(MovieVO param) {
		// movie에서 영화ID에 해당되는 영화 정보 전체를 return
		MovieVO outVO = null;
		
		for (MovieVO vo : movies) {
			if (vo.getMovieId().equals(param.getMovieId())) {
				outVO = vo;
				break;
			}
		}
		
		return outVO;
	}

	@Override
	public List<MovieVO> doRetrieve(DTO param) {
		return null;
	}
	
	public MovieVO stringToMovie(String data) {
		MovieVO out = null;
		
		String movieStr = data;
		System.out.println("movieStr: " + movieStr);
		
		String[] movieArr = movieStr.split(",");
		
		String movieId = movieArr[0];
		String movieTitle = movieArr[1];
		String genre = movieArr[2];
		String releaseDate = movieArr[3];
		String country = movieArr[4];
		int runningTime = Integer.parseInt(movieArr[5]);
		int filmRatings = Integer.parseInt(movieArr[6]);
		String director = movieArr[7];
		String actor = movieArr[8];
		double movieRate = Double.parseDouble(movieArr[9]);
		
		out = new MovieVO(movieId, movieTitle, genre, releaseDate, country, runningTime, filmRatings, director, actor, movieRate);
		
		return out;
	}

	@Override
	public int readFile(String path) {
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String data = "";
			
			while ((data = br.readLine()) != null) {
				MovieVO outVO = stringToMovie(data);
				movies.add(outVO);
			}
			
		} catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
		}
		
		displayList(movies);
		return movies.size();
	}
}
