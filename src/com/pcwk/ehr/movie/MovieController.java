package com.pcwk.ehr.movie;

import java.util.Iterator;
import java.util.Scanner;

import com.pcwk.ehr.main.UIMain;

public class MovieController {

	MovieDao dao = null;
	MovieVO newMovie = null;

	public static Iterator<Integer> iter = null;

	public MovieController() {
		dao = new MovieDao();
	}

	public void displaymap() {

		iter = MovieDao.movies.keySet().iterator();

		if (MovieDao.movies.size() > 0) {
			String message = "===============Movie List===============";
			System.out.println(message);
			System.out.println();

			while (iter.hasNext()) {
				int key = iter.next();
				String fr = "";

				if (MovieDao.movies.get(key).getFilmRatings() > 1) {
					fr = MovieDao.movies.get(key).getFilmRatings() + "세이상관람가";
				} else if (MovieDao.movies.get(key).getFilmRatings() == 1) {
					fr = "전체이용관람가";
				}

				System.out.printf("%d. 제목 : %s\n감독 : %s / 배우 : %s\n" + "장르 : %s / 기본 정보 : %s, %d분, %s\n" + "개봉 : %s\n",
						key, MovieDao.movies.get(key).getMovieTitle(), MovieDao.movies.get(key).getDirector(),
						MovieDao.movies.get(key).getActor(), MovieDao.movies.get(key).getGenre(), fr,
						MovieDao.movies.get(key).getRunningTime(), MovieDao.movies.get(key).getCountry(),
						MovieDao.movies.get(key).getReleaseDate());

				System.out.println();
				System.out.println("==========================================");
				System.out.println();
			}
		} else {
			System.out.println("영화 정보가 없습니다.");
		}
	}

	public void doSave(String movieTitle, String genre, String releaseDate, String country, int runningTime,
			int filmRatings, String director, String actor) {

		newMovie = new MovieVO(MovieDao.movies.size() + 1, movieTitle, genre, releaseDate, country, runningTime,
				filmRatings, director, actor);
		int flag = dao.doSave(newMovie);
		if (2 == flag) {
			System.out.println(newMovie.getMovieId() + newMovie.getMovieTitle() + "중복 되었습니다.");
		} else if (0 == flag) {
			System.out.println(newMovie.getMovieId() + newMovie.getMovieTitle() + "이미 존재하는 MovieID입니다.");
		} else {
			System.out.println("************************************");
			System.out.println("영화 코드 : " + newMovie.getMovieId() + ", 영화 제목 : " + newMovie.getMovieTitle() + " 등록 성공");
			System.out.println("************************************");
		}

	}

	public void doInputSave(Scanner scanner) {
		while (true) {
			try {
				System.out.println("제목(문자), 장르(문자), 개봉일(문자), 제작국가(문자), 러닝타임(숫자), 상영등급(숫자), 감독(문자), 배우(문자)");
				System.out.print("제목(취소:Enter)>");
				String movieTitle = scanner.nextLine();
				if (movieTitle == "")
					break;

				System.out.print("장르>");
				String genre = scanner.nextLine();

				System.out.print("개봉일(YYYY.MM.DD)>");
				String releaseDate = scanner.nextLine();

				System.out.print("제작국가>");
				String country = scanner.nextLine();

				System.out.print("러닝타임> ");
				int runningTime = Integer.parseInt(scanner.nextLine()); // nextLine 사용

				int filmRatings;
				while (true) {
					System.out.print("상영등급(1(전체), 7, 12, 15, 19)> ");
					filmRatings = Integer.parseInt(scanner.nextLine()); // nextLine 사용
					if (filmRatings == 1 || filmRatings == 7 || filmRatings == 15 || filmRatings == 19)
						break;
					System.out.println("상영 등급이 올바르지 않습니다. 다시 입력해주세요");
				}

				System.out.print("감독>");
				String director = scanner.nextLine();

				System.out.print("배우>");
				String actor = scanner.nextLine();

				System.out.println("---입력 정보---");
				System.out.printf("제목 : %s\n감독 : %s / 배우 : %s\n" + "장르 : %s / 기본 정보 : %d, %d분, %s\n" + "개봉 : %s\n",
						movieTitle, director, actor, genre, filmRatings, runningTime, country, releaseDate);

				System.out.print("등록 하시겠습니까?(Y/N)>");
				String check = scanner.nextLine();
				if (check.equalsIgnoreCase("Y")) {
					doSave(movieTitle, genre, releaseDate, country, runningTime, filmRatings, director, actor);
					break;
				} else {
					System.out.println("등록이 취소되었습니다.");
					break;
				}

			} catch (Exception e) {
				System.out.println("입력 오류 발생" + e.getMessage());

			}
		}
	}

	public void doDelete(int key) {
		int flag = dao.doDelete(key);

		if (1 == flag) {
			System.out.println("삭제 성공");
		} else {
			System.out.println("삭제 실패");
		}
	}

	public void doInputDelete(Scanner scanner) {
		int key = 0;
		while (true) {
			try {
				System.out.print("삭제할 영화 코드(취소:0)>");
				key = Integer.parseInt(scanner.nextLine());
				if (key == 0)
					break;
				System.out.println("삭제 할 영화 : " + MovieDao.movies.get(key).toString());
				System.out.print("삭제 하시겠습니까?(Y/N)>");
				String check = scanner.nextLine();
				if (check.equalsIgnoreCase("Y")) {
					doDelete(key);
					break;
				} else {
					System.out.println("삭제가 취소되었습니다.");
					break;
				}
			} catch (Exception e) {
				System.out.println("입력 오류 발생" + e.getMessage());
			}
		}

	}

	public void AdminMovieMenu(Scanner scanner) {
		String menu;

		while (true) {
			System.out.println(UIMain.adminMoviemenu());
			System.out.print("Menu를 선택 하세요.>");
			menu = scanner.nextLine().trim();
			switch (menu) {
			case "1":
				displaymap();
				UIMain.backmenu();
				break;
			case "2": {
				doInputSave(scanner);
				UIMain.backmenu();
				break;
			}

			case "3": {
				doInputDelete(scanner);
				UIMain.backmenu();
				break;
			}

			case "4": {
				return;
			}
			default:
				System.out.println("잘못된 메뉴 선택입니다.");
				break;
			}

		}
	}

}
