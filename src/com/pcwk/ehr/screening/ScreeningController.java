package com.pcwk.ehr.screening;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.pcwk.ehr.main.UIMain;
import com.pcwk.ehr.movie.MovieDao;

public class ScreeningController {
	ScreeningDao dao = null;
	ScreeningVO newScreening = null;

	public ScreeningController() {
		super();
		dao = new ScreeningDao();
	}

	public static Iterator<Integer> iter = null;

	public void displaySortedTime() {
		LocalDateTime now = LocalDateTime.now();
		List<Map.Entry<Integer, ScreeningVO>> entryList = new ArrayList<>(ScreeningDao.screenings.entrySet());

		// 날짜와 시간 기준으로 정렬 (Comparator 사용)
		Collections.sort(entryList, new Comparator<Map.Entry<Integer, ScreeningVO>>() {
			@Override
			public int compare(Map.Entry<Integer, ScreeningVO> entry1, Map.Entry<Integer, ScreeningVO> entry2) {
				// 날짜 비교
				int dateComparison = entry1.getValue().getScreeningDate()
						.compareTo(entry2.getValue().getScreeningDate());
				if (dateComparison != 0) {
					return dateComparison; // 날짜가 다르면 날짜에 따라 정렬
				}
				// 날짜가 같으면 시간 비교
				return entry1.getValue().getScreeningStartTime().compareTo(entry2.getValue().getScreeningStartTime());
			}
		});

		if (entryList.isEmpty()) {
			System.out.println("영화 정보가 없습니다.");
			return;
		}

		String message = "=========================Screening List=========================";
		System.out.println(message);
		System.out.println();
		int i = 1;
		for (Map.Entry<Integer, ScreeningVO> entry : entryList) {
			ScreeningVO screening = entry.getValue();
			int key = entry.getKey();
			LocalDateTime screeningStartDateTime = LocalDateTime.of(screening.getScreeningDate(),
					screening.getScreeningStartTime());

			// 현재 시간 이후의 영화만 출력
			if (screeningStartDateTime.isAfter(now)) {

				System.out.printf(
						"%d. 제목 : [%s] | %s/%d분/%s 개봉\n" + "▶상영날짜 : %s | 상영시간 : %s~%s | %s | 총 %d석 | 현재 %d석 \n", i,
						MovieDao.movies.get(screening.getMovieCode()).getMovieTitle(),
						MovieDao.movies.get(screening.getMovieCode()).getGenre(),
						MovieDao.movies.get(screening.getMovieCode()).getRunningTime(),
						MovieDao.movies.get(screening.getMovieCode()).getReleaseDate(), screening.getScreeningDate(),
						screening.getScreeningStartTime(), screening.getScreeningEndtime(),
						screening.getTheater().getTheaterName(), screening.getTheater().getTotalSeats(),
						screening.getTheater().getRemainSeats());

				System.out.println();
				System.out.println("==============================================================");
				System.out.println();
				i++;
			}
		}
	}

	public void displaySortedByTheater() {
		LocalDateTime now = LocalDateTime.now();
		// List로 변환
		List<Map.Entry<Integer, ScreeningVO>> entryList = new ArrayList<>(ScreeningDao.screenings.entrySet());

		// 극장 기준으로 정렬 (Comparator 사용)
		Collections.sort(entryList, new Comparator<Map.Entry<Integer, ScreeningVO>>() {
			@Override
			public int compare(Map.Entry<Integer, ScreeningVO> entry1, Map.Entry<Integer, ScreeningVO> entry2) {
				// 극장 이름 비교
				return entry1.getValue().getTheater().getTheaterName()
						.compareTo(entry2.getValue().getTheater().getTheaterName());
			}
		});

		if (entryList.isEmpty()) {
			System.out.println("영화 정보가 없습니다.");
			return;
		}

		String message = "=========================Screening List=========================";
		System.out.println(message);
		System.out.println();
		int i = 1;
		for (Map.Entry<Integer, ScreeningVO> entry : entryList) {
			ScreeningVO screening = entry.getValue();
			LocalDateTime screeningStartDateTime = LocalDateTime.of(screening.getScreeningDate(),
					screening.getScreeningStartTime());

			// 현재 시간 이후의 영화만 출력
			if (screeningStartDateTime.isAfter(now)) {

				int key = entry.getKey();

				System.out.printf(
						"%d. 제목 : [%s] | %s/%d분/%s 개봉\n" + "▶상영날짜 : %s | 상영시간 : %s~%s | %s | 총 %d석 | 현재 %d석 \n", i,
						MovieDao.movies.get(screening.getMovieCode()).getMovieTitle(),
						MovieDao.movies.get(screening.getMovieCode()).getGenre(),
						MovieDao.movies.get(screening.getMovieCode()).getRunningTime(),
						MovieDao.movies.get(screening.getMovieCode()).getReleaseDate(), screening.getScreeningDate(),
						screening.getScreeningStartTime(), screening.getScreeningEndtime(),
						screening.getTheater().getTheaterName(), screening.getTheater().getTotalSeats(),
						screening.getTheater().getRemainSeats());

				System.out.println();
				System.out.println("==============================================================");
				System.out.println();
				i++;
			}
		}
	}

	public void displayMap() {

		iter = ScreeningDao.screenings.keySet().iterator();

		if (ScreeningDao.screenings.size() > 0) {
			String message = "=========================Screening List=========================";
			System.out.println(message);
			System.out.println();

			while (iter.hasNext()) {
				int key = iter.next();

				System.out.printf(
						"상영코드 : %d | 제목 : [%s] | %s/%d분/%s 개봉\n▶상영날짜 : %s | 상영시간 : %s~%s | %s | 총 %d석 | 현재 %d석 \n",
						ScreeningDao.screenings.get(key).getScreeningCode(),
						MovieDao.movies.get(ScreeningDao.screenings.get(key).getMovieCode()).getMovieTitle(),
						MovieDao.movies.get(ScreeningDao.screenings.get(key).getMovieCode()).getGenre(),
						MovieDao.movies.get(ScreeningDao.screenings.get(key).getMovieCode()).getRunningTime(),
						MovieDao.movies.get(ScreeningDao.screenings.get(key).getMovieCode()).getReleaseDate(),
						ScreeningDao.screenings.get(key).getScreeningDate(),
						ScreeningDao.screenings.get(key).getScreeningStartTime(),
						ScreeningDao.screenings.get(key).getScreeningEndtime(),
						ScreeningDao.screenings.get(key).getTheater().getTheaterName(),
						ScreeningDao.screenings.get(key).getTheater().getTotalSeats(),
						ScreeningDao.screenings.get(key).getTheater().getRemainSeats());

				System.out.println();
				System.out.println("==============================================================");
				System.out.println();
			}
		} else {
			System.out.println("영화 정보가 없습니다.");
		}
	}

	public void doSave(int movieCode, LocalDate date, LocalTime time, String theaterName) {
		newScreening = new ScreeningVO(ScreeningDao.screenings.size() + 1, movieCode, date, time, theaterName);
		System.out.println(newScreening);
		int flag = dao.doSave(newScreening);

		if (flag == 2) {
			System.out.println(newScreening.getScreeningCode() + "중복 되었습니다.");
		} else if (flag == 0) {
			System.out.println(newScreening.getScreeningCode() + "이미 존재하는 ScreeningCode입니다.");
		} else {
			System.out.println("************************************");
			System.out.println("상영 코드 : " + newScreening.getScreeningCode() + ", 영화 제목 : "
					+ MovieDao.movies.get(movieCode).getMovieTitle() + " 등록 성공");
			System.out.println("************************************");
		}
	}

	public void doInputSave(Scanner scanner) {
		while (true) {
			try {
				System.out.println("영화 코드(숫자), 상영 날짜(YYYY-MM-DD), 상영 시작 시간(시간), 극장명(문자)");
				System.out.print("영화 코드(취소:0)>");
				int movieCode = Integer.parseInt(scanner.nextLine());
				if (movieCode == 0)
					break;

				System.out.print("상영 날짜(YYYY-MM-DD)>");
				String dateInput = scanner.nextLine();
				DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate date = LocalDate.parse(dateInput, dateFormatter);

				System.out.print("상영 시작 시간(HH:MM)>");
				String timeInput = scanner.nextLine();
				DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
				LocalTime time = LocalTime.parse(timeInput, timeFormatter);

				System.out.print("극장명>");
				String theaterName = scanner.nextLine();

				System.out.println("---입력 정보---");
				System.out.printf("영화 코드 : %d\n상영 날짜 : %tF\n상영 시작 시간: %tR\n극장명 : %s\n", movieCode, date, time, theaterName);

				System.out.print("등록 하시겠습니까?(Y/N)>");
				String check = scanner.nextLine();
				if (check.equalsIgnoreCase("Y")) {
					doSave(movieCode, date, time, theaterName);
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
				System.out.print("삭제할 상영 시간표 코드(취소:0)>");
				key = Integer.parseInt(scanner.nextLine());
				if (key == 0)
					break;
				System.out.println("삭제 할 상영 시간표 : " + ScreeningDao.screenings.get(key).toString());
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

	public static String adminScreeningMenu() {

		StringBuilder sbuUser = new StringBuilder(2000);
		sbuUser.append("+-+-+-+-+ +-+-+-+-+ +-+-+-+-+-+-+-+ \n");
		sbuUser.append("     |A|d|m|i|n| |m|e|n|u| \n");

		sbuUser.append(" ###### 상영 시간표 조회/추가/삭제 #####      \n");
		sbuUser.append(" 1. 상영 시간표 조회                   \n");
		sbuUser.append(" 2. 상영 시간표 등록                    \n");
		sbuUser.append(" 3. 상영 시간표 삭제                     \n");
		sbuUser.append(" 4. 이전 메뉴                     \n");

		return sbuUser.toString();
	}

	public void showAdminScreeningMenu(Scanner scanner) {
		String menu;

		while (true) {
			System.out.println(adminScreeningMenu());
			System.out.print("Menu를 선택 하세요.>");
			menu = scanner.nextLine().trim();
			switch (menu) {
			case "1": // 상영 시간표 조회
				displayMap();
				UIMain.backmenu();
				break;
			case "2": // 상영 시간표 등록
				doInputSave(scanner);
				UIMain.backmenu();
				break;
			case "3": // 상영 시간표 삭제
				doInputDelete(scanner);
				UIMain.backmenu();
				break;
			case "4": // 이전 메뉴
				UIMain.backmenu();
				return;
			default:
				System.out.println("잘못된 입력입니다.");
				break;
			}
		}
	}

	public void showScreeningMenu(Scanner scanner) {
		String menu;

		while (true) {
			System.out.println(UIMain.secreeningmenu());
			System.out.print("Menu를 선택 하세요>");
			menu = scanner.nextLine().trim();
			switch (menu) {
			case "1":
				displaySortedByTheater();
				UIMain.backmenu();
				break;
			case "2": {
				displaySortedTime();
				UIMain.backmenu();
				break;
			}

			case "3": {
				return;
			}
			default: {
				System.out.println("잘못된 입력입니다.");
				break;
			}

			}

		}
	}
}
