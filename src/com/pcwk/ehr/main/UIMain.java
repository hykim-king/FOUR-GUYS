package com.pcwk.ehr.main;

import java.util.Scanner;

import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.member.MemberController;
import com.pcwk.ehr.movie.MovieController;
import com.pcwk.ehr.reservation.ReservationController;
import com.pcwk.ehr.screening.ScreeningController;

public class UIMain implements PLog {

	static int loginStatus = 0;
	static String loginId;

	// 컨트롤러 선언
	MovieController movCt = null;
	MemberController memCt = null;
	ScreeningController scrCt = null;
	ReservationController rsvCt = null;

	public UIMain() {
		movCt = new MovieController();
		memCt = new MemberController();
		scrCt = new ScreeningController();
		rsvCt = new ReservationController();
	}

	public static void logout() {
		loginStatus = 0;
		loginId = null;
	}

	public static void backmenu() {

		String enter;
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.print("메뉴로 돌아가시려면 ENTER>");
			enter = scanner.nextLine();
			if (enter.equals("")) {
				break;
			}
		}
	}

	public boolean loginCheck() {
		if (loginStatus != 0) {
			System.out.printf("id : %s 님 로그인 되어있습니다.\n", loginId);
			return true;
		}
		return false;
	}

	public void UserMenu(Scanner scanner) {
		String menu; // 비회원 or 일반회원 메뉴
		boolean check = false;

		while (true) {
			System.out.println(menu());
			System.out.print("Menu를 선택 하세요>");
			menu = scanner.nextLine().trim();
			switch (menu) {
			case "1": {
				movCt.displaymap();
				backmenu();
				break;
			}

			case "2": {
				scrCt.showScreeningMenu(scanner);
				backmenu();
				break;
			}
			case "3": {
				check = loginCheck();
				if (check == false) {
					System.out.println("현재 로그아웃 상태입니다.");
					backmenu();
					break;
				}
				rsvCt.showReservationMenu(scanner, loginId);
				backmenu();
				break;

			}
			case "4": {
				check = loginCheck();
				if (check == true) {
					backmenu();
					break;
				}
				String[] logininfo = memCt.doLogin();
				loginStatus = Integer.parseInt(logininfo[0]);
				loginId = logininfo[1];
				if (loginStatus == 2) {
					backmenu();
					return;
				}
				backmenu();
				break;

			}
			case "5": {
				check = loginCheck();
				if (check == false) {
					System.out.println("현재 로그아웃 상태입니다.");
					backmenu();
					break;
				}
				logout();
				System.out.println("로그아웃 완료!");
				backmenu();
				break;
			}

			case "6": {
				check = loginCheck();
				if (check == true) {
					backmenu();
					break;
				}
				memCt.findIdPw(scanner);
				break;

			}

			case "7": {
				check = loginCheck();
				if (check == true) {
					backmenu();
					break;
				}
				memCt.doSave();
				backmenu();
				break;
			}

			case "8": {
				check = loginCheck();
				if (check == false) {
					System.out.println("현재 로그아웃 상태입니다.");
					backmenu();
					break;
				}
				memCt.userPageMenu(scanner, loginId);
				break;

			}

			case "9": {
				System.out.println("┌─────────────────────────┐");
				System.out.println("│        프로그램 종료!        │");
				System.out.println("└─────────────────────────┘");
				System.exit(0);
			}
			default:
				System.out.println("잘못된 메뉴 선택입니다.");
				break;
			}

		}
	}

	public void AdminMenu(Scanner scanner) {

		String menu;
		// 관리자 전용 메뉴
		while (true) {
			System.out.println(menu());
			System.out.print("Menu를 선택 하세요.>");
			menu = scanner.nextLine().trim();
			switch (menu) {
			case "1": {
				movCt.AdminMovieMenu(scanner);
				break;
			}
			
			case "2": {
				scrCt.showAdminScreeningMenu(scanner);
				break;
			}

			case "3": {
				rsvCt.display();
				backmenu();
				break;
			}

			case "4": {
				memCt.doAdminDeleteMenu(scanner);

				break;
			}

			case "5": {

				logout();
				System.out.println("관리자 로그아웃 완료!");
				backmenu();
				return;

			}

			case "6": {
				System.out.println("┌─────────────────────────┐");
				System.out.println("│        프로그램 종료!        │");
				System.out.println("└─────────────────────────┘");
				System.exit(0);
			}
			default:
				System.out.println("잘못된 메뉴 선택입니다.");
				break;
			}
		}

	}

	public void doActionMenu() {

		Scanner scanner = new Scanner(System.in);
		while (true) {
			if (loginStatus == 0 || loginStatus == 1) {
				UserMenu(scanner);
			} else {
				AdminMenu(scanner);
			}

		}

	}

	public static String adminMoviemenu() {

		StringBuilder sbuUser = new StringBuilder(2000);
		sbuUser.append("+-+-+-+-+ +-+-+-+-+ +-+-+-+-+-+-+-+ \n");
		sbuUser.append("     |A|d|m|i|n| |m|e|n|u| \n");

		sbuUser.append(" ###### 영화 조회/추가/삭제 #####         \n");
		sbuUser.append(" 1. 영화 조회                     \n");
		sbuUser.append(" 2. 영화 등록                     \n");
		sbuUser.append(" 3. 영화 삭제                     \n");
		sbuUser.append(" 4. 이전 메뉴                     \n");

		return sbuUser.toString();
	}

	public static String secreeningmenu() {

		StringBuilder sbuUser = new StringBuilder(2000);
		sbuUser.append("+-+-+-+-+ +-+-+-+-+ +-+-+-+-+-+-+-+ \n");
		sbuUser.append("    |상|영|  |시|간|표| |조|회|  \n");

		sbuUser.append(" ###### 극장별 / 시간별 조회 ######        \n");
		sbuUser.append(" 1. 극장별 조회                     \n");
		sbuUser.append(" 2. 시간별 조회                   \n");
		sbuUser.append(" 3. 이전 메뉴                       \n");

		return sbuUser.toString();
	}

	public static String doAdminDeletemenu() {

		StringBuilder sbuUser = new StringBuilder(2000);
		sbuUser.append("+-+-+-+-+ +-+-+-+-+ +-+-+-+-+-+-+-+ \n");
		sbuUser.append("    |회|원|  |테|이|블| |관|리| \n");

		sbuUser.append(" ###### 회원 조회 / 회원 삭제 ######        \n");
		sbuUser.append(" 1. 회원 조회                     \n");
		sbuUser.append(" 2. 회원 삭제                    \n");
		sbuUser.append(" 3. 이전 메뉴                       \n");

		return sbuUser.toString();
	}

	public static String findIdPwMenu() {

		StringBuilder sbuUser = new StringBuilder(2000);
		sbuUser.append("+-+-+-+-+ +-+-+-+-+ +-+-+-+-+-+-+-+ \n");
		sbuUser.append("    |아|이|디| / |비|밀|번|호| |찾|기| \n");

		sbuUser.append(" ###### ID 찾기 / 비밀번호 찾기 ######        \n");
		sbuUser.append(" 1. 아이디 찾기                      \n");
		sbuUser.append(" 2. 비밀번호 찾기                     \n");
		sbuUser.append(" 3. 이전 메뉴                        \n");

		return sbuUser.toString();
	}


	public static String menu() {

		if (loginStatus == 0 || loginStatus == 1) {

			StringBuilder sbuUser = new StringBuilder(2000);
			sbuUser.append("+-+-+-+-+ +-+-+-+-+ +-+-+-+-+-+-+-+ \n");
			sbuUser.append("|M|i|n|i| |J|a|v|a| |P|r|o|j|e|c|t| \n");
			sbuUser.append("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+ \n");
			sbuUser.append("|2|0|2|4|.|1|0|.|2|5|               \n");
			sbuUser.append("+-+-+-+-+-+-+-+-+-+-+               \n");
			sbuUser.append(" ###### 영화 예매 시스템 #####            \n");
			sbuUser.append(" 1. 영화 목록                    \n");
			sbuUser.append(" 2. 상영시간표                    \n");
			sbuUser.append(" 3. 예매                         \n");
			sbuUser.append(" 4. 로그인                        \n");
			sbuUser.append(" 5. 로그아웃                      \n");
			sbuUser.append(" 6. 아이디/비밀번호 찾기                     \n");
			sbuUser.append(" 7. 회원가입                     \n");
			sbuUser.append(" 8. 마이페이지                         \n");
			sbuUser.append(" 9. 종료                         \n");

			return sbuUser.toString();
		} else {
			StringBuilder sbAdmin = new StringBuilder(2000);
			sbAdmin.append("+-+-+-+-+ +-+-+-+-+ +-+-+-+-+-+-+-+ \n");
			sbAdmin.append("|M|i|n|i| |J|a|v|a| |P|r|o|j|e|c|t| \n");
			sbAdmin.append("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+ \n");
			sbAdmin.append("|2|0|2|4|.|1|0|.|2|5|               \n");
			sbAdmin.append("+-+-+-+-+-+-+-+-+-+-+               \n");
			sbAdmin.append(" ###### 영화 예매 시스템 - 관리자 모드 ######  \n");
			sbAdmin.append(" 1. 영화목록 조회/추가/삭제                  \n");
			sbAdmin.append(" 2. 상영시간표 조회/추가/삭제                  \n");
			sbAdmin.append(" 3. 예매 테이블 관리                \n");
			sbAdmin.append(" 4. 회원 테이블 관리                \n");
			sbAdmin.append(" 5. 로그아웃                         \n");
			sbAdmin.append(" 6. 종료                         \n");

			return sbAdmin.toString();

		}
	}

	public static void main(String[] args) {
		UIMain main = new UIMain();
		main.doActionMenu();

	}

}
