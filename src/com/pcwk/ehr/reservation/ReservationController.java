package com.pcwk.ehr.reservation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.pcwk.ehr.main.UIMain;
import com.pcwk.ehr.member.MemberDao;
import com.pcwk.ehr.member.MemberVO;
import com.pcwk.ehr.movie.MovieController;
import com.pcwk.ehr.movie.MovieDao;
import com.pcwk.ehr.screening.ScreeningDao;
import com.pcwk.ehr.screening.ScreeningVO;
import com.pcwk.ehr.theater.TheaterVO;

public class ReservationController {
	ReservationDao dao = null;

	public ReservationController() {
		super();
		dao = new ReservationDao();
	}

	public static Iterator<Integer> iter = null;

	public void display() {
		dao.displayList();
	}

	public void reservation(Scanner scanner, String id) {

		List<ScreeningVO> list = null;
		MovieController movieController = new MovieController();
		movieController.displaymap();

		System.out.print("예매할 영화 번호를 입력하세요>");
		int movieCode = scanner.nextInt();
		scanner.nextLine();

		System.out.print("극장을 선택해주세요(강남/판교/인천)>");
		String theater = scanner.nextLine();

		list = searchMovie(movieCode, theater);
		if (list.isEmpty() == true) {
			System.out.println("일치하는 상영 정보가 없습니다.");
			return;
		}

		for (ScreeningVO vo : list) {
			System.out.printf("상영 번호 : %d | 제목 : [%s] | %d분 | ▶상영날짜 : %s | 상영시간 : %s~%s | %s | 총 %d석 | 잔여 %d석 \n",
					vo.getScreeningCode(), MovieDao.movies.get(vo.getMovieCode()).getMovieTitle(),
					MovieDao.movies.get(vo.getMovieCode()).getRunningTime(), vo.getScreeningDate(),
					vo.getScreeningStartTime(), vo.getScreeningEndtime(), vo.getTheater().getTheaterName(),
					vo.getTheater().getTotalSeats(), vo.getTheater().getRemainSeats());
		}

		System.out.print("예매할 상영 번호를 입력하세요>");
		int screeningCode = scanner.nextInt();
		scanner.nextLine();

		ScreeningVO vo = searchScreening(list, screeningCode);
		if (vo == null) {
			System.out.println("상영 번호를 잘못 입력하셨습니다.");
			return;
		}

		System.out.print("성인 수를 입력하세요>");
		int adult = scanner.nextInt();
		scanner.nextLine();
		System.out.print("청소년 수를 입력하세요>");
		int teenager = scanner.nextInt();
		scanner.nextLine();
		int pax = adult + teenager;
		int totalprice = adult * 10000 + teenager * 8000;

		System.out.printf(
				"상영 번호 : %d | 제목 : [%s] | %d분 | ▶상영날짜 : %s | 상영시간 : %s~%s | %s |\n"
						+ "성인 : %d명 | 청소년 : %d명 | 총 인원수 : %d\n",
				vo.getScreeningCode(), MovieDao.movies.get(vo.getMovieCode()).getMovieTitle(),
				MovieDao.movies.get(vo.getMovieCode()).getRunningTime(), vo.getScreeningDate(),
				vo.getScreeningStartTime(), vo.getScreeningEndtime(), vo.getTheater().getTheaterName(), adult, teenager,
				pax);
		System.out.printf("%d원 결제하시겠습니까?(Y/N)", totalprice);
		String check = scanner.nextLine();
		if (check.equalsIgnoreCase("Y") == false) {
			System.out.println("결제 취소");
			return;
		}

		if (doSelectOne(id).getBalance() < totalprice) {
			System.out.println("잔액이 부족합니다");
			return;
		} else {
			System.out.println("결제 성공");
			System.out.println();
		}
		String[] seats = selectSeats(scanner, vo.getTheater(), pax, id);

		dao.doSave(new ReservationVO(screeningCode, id, adult, teenager, seats, totalprice));
		doSelectOne(id).setBalance(doSelectOne(id).getBalance() - totalprice);
		vo.getTheater().setRemainSeats(vo.getTheater().getRemainSeats() - pax);
		System.out.println("=======");
		System.out.println("예매 완료");
		System.out.println("=======");
		
	}

	public MemberVO doSelectOne(String id) {
		MemberVO outVO = null;

		for (MemberVO vo : MemberDao.members) {
			if (vo.getMemberId().equals(id)) {
				outVO = vo;
				break;
			}
		}

		return outVO;
	}

	public String[] selectSeats(Scanner scanner, TheaterVO theater, int pax, String id) {
		String[][] seats = theater.getSeats();
		String[] seatsArr = new String[pax];
		int init = 0;

		for (int i = 0; i < pax; i++) {
			System.out.println("         ┌──────────────────────┐");
			System.out.println("         │                      │");
			System.out.println("         │                      │");
			System.out.println("         │                      │");
			System.out.println("         │        SCREEN        │");
			System.out.println("         │                      │");
			System.out.println("         │                      │");
			System.out.println("         │                      │");
			System.out.println("         └──────────────────────┘");
			System.out.println("                                 ");
			System.out.println("                                 ");

			for (int row = 0; row < seats.length; row++) {
				for (int col = 0; col < seats[row].length; col++) {
					if (col == 3 || col == 7) {
						System.out.print("|     ");
					}
					if (seats[row][col].length() != 2) {
						System.out.print("|  ");
					} else {
						System.out.print("|" + seats[row][col]);
					}
				}
				System.out.println("|");
			}

			System.out.print("좌석을 선택하세요>");
			String reserveSeat = scanner.nextLine();

			if (reserveSeat.equalsIgnoreCase("x")) {
				System.out.println("좌석 선택 종료");
				break;
			}

			boolean seatFound = false;

			outer: for (int row = 0; row < seats.length; row++) {
				for (int col = 0; col < seats[row].length; col++) {
					if (reserveSeat.equals(seats[row][col])) {
						seatsArr[init] = seats[row][col];
						seats[row][col] = id;
						seatFound = true;
						init++;
						break outer;
					}
				}
			}

			if (seatFound == false) {
				System.out.println("잘못된 좌석번호입니다.");
				i--;
			}
		}

		System.out.println("\n최종 좌석 배치도:");
		System.out.println("         ┌──────────────────────┐");
		System.out.println("         │                      │");
		System.out.println("         │                      │");
		System.out.println("         │                      │");
		System.out.println("         │        SCREEN        │");
		System.out.println("         │                      │");
		System.out.println("         │                      │");
		System.out.println("         │                      │");
		System.out.println("         └──────────────────────┘");
		System.out.println("                                 ");
		System.out.println("                                 ");

		for (int row = 0; row < seats.length; row++) {
			for (int col = 0; col < seats[row].length; col++) {
				if (col == 3 || col == 7) {
					System.out.print("|     ");
				}
				if (seats[row][col].length() != 2) {
					System.out.print("|  ");
				} else {
					System.out.print("|" + seats[row][col]);
				}
			}
			System.out.println("|");
		}

		return seatsArr;
	}

	public ScreeningVO searchScreening(List<ScreeningVO> list, int screeningCode) {

		for (ScreeningVO vo : list) {
			if (vo.getScreeningCode() == screeningCode) {
				return vo;
			}

		}
		return null;

	}

	public List<ScreeningVO> searchMovie(int MovieiD, String theater) {
		List<ScreeningVO> resultlist = new ArrayList<ScreeningVO>();

		for (ScreeningVO vo : ScreeningDao.screenings.values()) {
			if (vo.getMovieCode() == MovieiD && vo.getTheater().getTheaterName().equals(theater)) {
				resultlist.add(vo);
			}

		}
		return resultlist;

	}

	public static String reservemenu() {

		StringBuilder sbuUser = new StringBuilder(2000);
		sbuUser.append("+-+-+-+-+ +-+-+-+-+ +-+-+-+-+-+-+-+ \n");
		sbuUser.append("          |예|매|  |메|뉴| \n");

		sbuUser.append(" ######        예매          ######        \n");
		sbuUser.append(" 1. 예매                   \n");
		sbuUser.append(" 2. 이전 메뉴                  \n");

		return sbuUser.toString();
	}

	public void showReservationMenu(Scanner scanner, String id) {
		String menu;

		while (true) {
			System.out.println(reservemenu());
			System.out.print("Menu를 선택 하세요.>");
			menu = scanner.nextLine().trim();
			switch (menu) {
			case "1":
				reservation(scanner, id);
				UIMain.backmenu();
				break;
			case "2": {
				return;
			}
			default:
				System.out.println("잘못된 메뉴 선택입니다.");
				break;

			}

		}

	}
}
