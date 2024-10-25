package com.pcwk.ehr.member;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import com.pcwk.ehr.main.UIMain;
import com.pcwk.ehr.movie.MovieDao;
import com.pcwk.ehr.reservation.ReservationDao;
import com.pcwk.ehr.reservation.ReservationVO;
import com.pcwk.ehr.screening.ScreeningDao;

public class MemberController {
	private MemberDao dao;
	private ScreeningDao sDao;

	public MemberController() {
		dao = new MemberDao();
		sDao = new ScreeningDao();
	}

	private int doChargeBalance(Scanner scanner, String id) {
		int flag = 0;

		MemberVO param = new MemberVO();

		param.setMemberId(id);

		MemberVO existingMember = dao.doSelectOne(param);

		if (existingMember == null) {
			System.out.println("존재하지 않는 ID입니다.");
			return flag;
		}

		System.out.printf("현재 잔액 : %d\n", existingMember.getBalance());
		int inputBalance = -1;
		while (inputBalance <= 0) {
			System.out.print("충전할 포인트를 입력하세요: ");
			inputBalance = Integer.parseInt(scanner.nextLine());

			if (inputBalance <= 0) {
				System.out.println("올바른 금액을 입력하세요.");
			}
		}

		int newBalance = existingMember.getBalance() + inputBalance;

		System.out.print("충전하시겠습니까?(Y/N))>");
		String check = scanner.nextLine();
		if (check.equalsIgnoreCase("Y")) {
			existingMember.setBalance(newBalance);
			flag = dao.doChargeBalance(existingMember);
			System.out.println("충전이 완료되었습니다.");
			System.out.printf("충전 후 잔액 : %d\n", newBalance);
		} else {
			System.out.println("충전이 취소되었습니다.");
		}

		return flag;
	}

	/**
	 * 로그인
	 * 
	 * @return 2(관리자) / 1(사용자) / 0(실패)
	 */
	public String[] doLogin() {
		int flag = 0;
		String[] strArr = new String[2];

		MemberVO param = new MemberVO();

		Scanner sc = new Scanner(System.in);

		System.out.print("아이디를 입력하세요>");
		String inputId = sc.nextLine().trim();
		System.out.print("비밀번호를 입력하세요>");
		String inputPw = sc.nextLine().trim();

		param.setMemberId(inputId);
		param.setPassword(inputPw);

		flag = dao.doLogin(param);

		if (flag == 0) {
			strArr[0] = String.valueOf(flag);
			strArr[1] = null;

			System.out.printf("로그인 실패! 아이디 및 비밀번호를 확인해주세요.\n");

		} else {
			strArr[0] = String.valueOf(flag);
			strArr[1] = param.getMemberId();
			System.out.printf("ID : %s 로그인 성공.\n", param.getMemberId());
		}

		return strArr;
	}

	/**
	 * 회원 가입
	 * 
	 * @return 1(성공) / 0(실패)
	 */
	public void doSave() {
		int flag = 0;

		MemberVO param = null;

		Scanner sc = new Scanner(System.in);
		System.out.println("가입할 회원 정보를 입력하세요 ");
		System.out.print("아이디를 입력하세요>");
		String inputId = sc.nextLine().trim();
		System.out.print("이름을 입력하세요>");
		String inputName = sc.nextLine().trim();
		System.out.print("비밀번호를 입력하세요>");
		String inputPw = sc.nextLine().trim();
		System.out.print("이메일을 입력하세요>");
		String inputEmail = sc.nextLine().trim();
		System.out.print("생년월일을 입력하세요 ex)2024/10/1>");
		String inputBDate = sc.nextLine().trim();

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String registerDate = sdf.format(date);

		// 회원 정보를 받아서 MemberVO로 변환
		param = new MemberVO(inputId, inputName, inputPw, inputEmail, inputBDate, registerDate, "user");

		flag = dao.doSave(param);

		if (flag == 1) {
			System.out.println("회원 가입 성공!");
		} else {
			System.out.println("회원 가입 실패!");
		}

	}

	// 3. 회원 목록 조회
	public void doSelectAll() {
		dao.displayList(MemberDao.members);
	}

	/**
	 * 회원 단건 조회
	 * 
	 * @return MemberVO
	 */
	public MemberVO doSelectOne() {
		MemberVO outVO = null; // 조회 결과
		MemberVO param = new MemberVO(); // 조회 파라미터: memberId

		// 1. Scanner
		// 2. 조회 사용자Id 입력
		// 3. dao.doSelectOne(param);
		Scanner sc = new Scanner(System.in);
		System.out.print("조회할 회원 ID를 입력하세요: ");
		String inputData = sc.nextLine().trim();
		System.out.printf("1. inputData: %s\n", inputData);

		param.setMemberId(inputData);
		System.out.printf("2. param: %s\n", param);

		outVO = dao.doSelectOne(param);
		System.out.printf("3. outVO: %s\n", outVO);

		return outVO;
	}

	public int doUpdate(Scanner scanner, String id) {
		int flag = 0;

		MemberVO param = new MemberVO();

		param.setMemberId(id);

		MemberVO existingMember = dao.doSelectOne(param);

		if (existingMember == null) {
			System.out.println("존재하지 않는 ID입니다.");
			return flag;
		}
		System.out.printf("현재 회원정보 : %s\n", existingMember);
		System.out.println("수정할 항목을 선택하세요");
		System.out.println("1. 이름 수정");
		System.out.println("2. 비밀번호 수정");
		System.out.println("3. 이메일 수정");
		System.out.println("4. 생일 수정");
		System.out.print("메뉴를 선택하세요: ");
		String input = scanner.nextLine();

		switch (input) {
		case "1": {
			System.out.print("새 이름을 입력하세요: ");
			String mName = scanner.nextLine();
			existingMember.setMemberName(mName);
		}
			break;
		case "2": {
			System.out.print("새 비밀번호를 입력하세요: ");
			String mPw = scanner.nextLine();
			existingMember.setPassword(mPw);
		}
			break;
		case "3": {
			System.out.print("새 이메일을 입력하세요: ");
			String email = scanner.nextLine();
			existingMember.setEmail(email);
		}
			break;
		case "4": {
			System.out.print("새 생년월일을 입력하세요 (yyyy/MM/dd): ");
			String bDate = scanner.nextLine();
			existingMember.setBirthDate(bDate);
		}
			break;
		default:
			System.out.println("잘못된 선택입니다. 다시 선택하세요.");
			break;
		}

		// 변경 후 회원정보
		System.out.printf("변경 후 회원정보 : %s\n", existingMember);
		System.out.print("변경하시겠습니까?(Y/N))>");
		String check = scanner.nextLine();
		if (check.equalsIgnoreCase("Y")) {
			flag = dao.doUpdate(existingMember);
			return flag;
		}

		return flag;
	}

	/**
	 * 회원 삭제
	 * 
	 * @return 1(성공) / 0(실패)
	 */
	public int doDelete(Scanner scanner, String id) {
		int flag = 0;
		MemberVO param = new MemberVO(); // 조회 파라미터: memberId
		param.setMemberId(id);

		System.out.printf("%s 님 탈퇴하시겠습니까?(Y/N))>", id);
		String check = scanner.nextLine();
		if (check.equalsIgnoreCase("Y")) {
			System.out.print("비밀번호를 입력해주세요>");
			String pw = scanner.nextLine().trim();
			param.setPassword(pw);
			flag = dao.doDeleteMy(param);
			return flag;
		}
		return flag;

	}

	/**
	 * 관리자-회원 테이블 삭제
	 * 
	 * @param scanner
	 * @return 2(성공)/1(실패)/0(취소)
	 */
	public int doAdminDelete(Scanner scanner) {
		int flag = 0;
		MemberVO param = new MemberVO(); // 조회 파라미터: memberId

		System.out.print("삭제할 아이디를 입력해주세요>");
		String inputId = scanner.nextLine().trim();
		param.setMemberId(inputId);

		System.out.printf("%s 님을 삭제하시겠습니까?(Y/N))>", inputId);
		String check = scanner.nextLine();
		if (check.equalsIgnoreCase("Y")) {
			flag = dao.doDelete(param);
			return flag;
		}
		return flag;

	}

	public MemberVO doFindId(Scanner scanner) {
		MemberVO outVO = null;
		MemberVO param = new MemberVO();

		System.out.print("이름을 입력 하세요: ");
		String inputName = scanner.nextLine().trim();

		System.out.print("이메일을 입력 하세요>");
		String inputEmail = scanner.nextLine().trim();

		System.out.printf("이름 : %s, 이메일 : %s\n", inputName, inputEmail);
		System.out.print("입력하신 정보가 정확한가요?(Y/N)>");
		String check = scanner.nextLine().trim();

		if (check.equalsIgnoreCase("Y")) {

			param.setMemberName(inputName);
			param.setEmail(inputEmail);
			outVO = dao.doFindId(param);
			if (outVO != null) {
				System.out.printf("%s 님의 아이디는 %s 입니다.\n", inputName, outVO.getMemberId());
			}
			return outVO;

		}

		return outVO;
	}

	public MemberVO doFindPassWord(Scanner scanner) {
		MemberVO outVO = null;
		MemberVO param = new MemberVO();

		System.out.print("아이디를 입력하세요>");
		String inputId = scanner.nextLine().trim();

		System.out.print("이름을 입력하세요>");
		String inputName = scanner.nextLine().trim();

		System.out.print("이메일을 입력하세요>");
		String inputEmail = scanner.nextLine().trim();

		System.out.print("생년월일을 입력하세요(YYYY/MM/DD)>");
		String inputBirthDate = scanner.nextLine().trim();

		System.out.printf("아이디 : %s, 이름 : %s, 이메일 : %s, 생년월일 : %s\n", inputId, inputName, inputEmail, inputBirthDate);
		System.out.print("입력하신 정보가 정확한가요?(Y/N)>");
		String check = scanner.nextLine().trim();

		if (check.equalsIgnoreCase("Y")) {

			param.setMemberId(inputId);
			param.setMemberName(inputName);
			param.setEmail(inputEmail);
			param.setBirthDate(inputBirthDate);

			outVO = dao.doFindPassWord(param);
			if (outVO != null) {
				System.out.printf("%s 님의 비밀번호는 %s 입니다.\n", inputId, outVO.getPassword());
			}
			return outVO;
		}
		return outVO;
	}

	public int readFile(String path) {
		return dao.readFile(path);
	}

	public static String memberPage() {

		StringBuilder sbuUser = new StringBuilder(2000);
		sbuUser.append("+-+-+-+-+ +-+-+-+-+ +-+-+-+-+-+-+-+ \n");
		sbuUser.append("        |마|이|페|이|지|  \n");

		sbuUser.append(" ###### 예매 조회 / 회원정보 수정 ######        \n");
		sbuUser.append(" 1. 예매 조회                   \n");
		sbuUser.append(" 2. 캐시 충전                    \n");
		sbuUser.append(" 3. 회원정보 수정                    \n");
		sbuUser.append(" 4. 회원 탈퇴                    \n");
		sbuUser.append(" 5. 이전 메뉴                     \n");

		return sbuUser.toString();
	}

	public void userPageMenu(Scanner scanner, String id) {
		String menu;

		while (true) {
			int curBal = 0;
			for (MemberVO vo : MemberDao.members) {
				if (vo.getMemberId().equals(id)) {
					curBal = vo.getBalance();
				}
			}
			System.out.println(memberPage());
			System.out.printf("현재 잔액 : %d\n", curBal);
			System.out.print("Menu를 선택 하세요.>");
			menu = scanner.nextLine().trim();
			switch (menu) {
			case "1":
				for (ReservationVO vo : ReservationDao.reserves) {
					if (vo.getId().equals(id)) {
						System.out.println("========================================================================");
						System.out.println(MovieDao.movies.get(ScreeningDao.screenings.get(vo.getScreeningCode()).getMovieCode()));
						System.out.println((ScreeningDao.screenings.get(vo.getScreeningCode()).toString()));
						System.out.println(vo.toString());
						System.out.println("========================================================================");
					}
				}

				break;
			case "2": {
				int flag = doChargeBalance(scanner, id);
				if (flag == 1) {
					System.out.println("충전 성공!");
				} else {
					System.out.println("충전 실패!");
				}
				break;
			}

			case "3": {
				int flag = doUpdate(scanner, id);
				if (flag == 1) {
					System.out.println("수정 성공!");
				} else {
					System.out.println("수정 실패!");
				}
				UIMain.backmenu();
				break;
			}

			case "4": {
				int flag = doDelete(scanner, id);
				if (flag == 2) {
					System.out.println("탈퇴 성공");
					UIMain.logout();
					return;
				} else if (flag == 1) {
					System.out.println("비밀번호 오류");
				}
				UIMain.backmenu();
				break;
			}

			case "5": {
				UIMain.backmenu();
				return;
			}
			default:
				System.out.println("잘못된 메뉴 선택입니다.");
				break;

			}
		}

	}

	public void findIdPw(Scanner scanner) {
		String menu;
		MemberVO vo = null;

		while (true) {
			System.out.println(UIMain.findIdPwMenu());
			System.out.print("Menu를 선택 하세요.>");
			menu = scanner.nextLine().trim();
			switch (menu) {
			case "1":
				vo = doFindId(scanner);
				if (vo == null) {
					System.out.println("일치하는 정보가 없습니다.");
				}
				UIMain.backmenu();
				break;
			case "2": {
				vo = doFindPassWord(scanner);
				if (vo == null) {
					System.out.println("본인확인에 실패하였습니다.");
				}
				UIMain.backmenu();
				break;
			}

			case "3":
				UIMain.backmenu();
				return;

			}

		}
	}

	public void doAdminDeleteMenu(Scanner scanner) {
		String menu;

		while (true) {
			System.out.println(UIMain.doAdminDeletemenu());
			System.out.print("Menu를 선택 하세요.>");
			menu = scanner.nextLine().trim();
			switch (menu) {
			case "1":
				doSelectAll();
				UIMain.backmenu();
				break;
			case "2": {
				int flag = doAdminDelete(scanner);
				if (flag == 2) {
					System.out.println("삭제 완료!");
				} else if (flag == 1) {
					System.out.println("삭제 실패! 아이디 오류!");
				} else {
					System.out.println("삭제 취소");
				}
				UIMain.backmenu();
				break;
			}

			case "3": {
				UIMain.backmenu();
				return;
			}
			default:
				System.out.println("잘못된 메뉴 선택입니다.");
				break;
			}

		}

	}
}
