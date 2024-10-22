package com.pcwk.ehr.member;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class MemberDaoMain {
	Date date = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	String today = sdf.format(date);
	MemberVO registerMember = null;
	MemberDao dao = null;

	public MemberDaoMain() {
		dao = new MemberDao();
	}

	public void doSave(String mId, String mName, String mPw, String email, String bDate) {
		System.out.println("회원 등록");
		registerMember = new MemberVO(mId, mName, mPw, email, bDate, today, "user");
		
		int flag = dao.doSave(registerMember);

		if (2 == flag) {
			System.out.println(registerMember.getMemberId() + "중복 되었습니다.");
		} else if (0 == flag) {
			System.out.println(registerMember.getMemberId() + "등록 실패");
		} else {
			System.out.println("************************************");
			System.out.println(registerMember.getMemberId() + "등록 성공");
			System.out.println("************************************");
		}

		dao.displayList(MemberDao.members);
	}

	public void doDelete() {
		System.out.println("회원 삭제");
		int flag = dao.doDelete(registerMember);

		if (1 == flag) {
			System.out.println(registerMember.getMemberId() + "삭제 성공");
		} else {
			System.out.println(registerMember.getMemberId() + "삭제 실패");
		}
		dao.displayList(MemberDao.members);
	}

	public MemberVO doSelectOne() {
		System.out.println("\n회원 조회");

		MemberVO outVO = dao.doSelectOne(registerMember);

		if (null == outVO) {
			System.out.println(registerMember.getMemberId() + "회원이 존재하지 않습니다.");
		} else {
			System.out.println("************************************");
			System.out.println("조회 성공\n" + outVO);
			System.out.println("************************************");
		}
		return outVO;
	}

	public void login(String mId, String mPw) {
		MemberVO outVO = dao.login(registerMember.getMemberId(), registerMember.getPassword());
		if (outVO != null) {
			System.out.println("로그인 성공: " + outVO.getMemberName() + "님");
		} else {
			System.out.println("로그인 실패");
		}
	}

	public void findId() {
		MemberVO outVO = dao.findMemberIdByEmail(registerMember.getEmail(), registerMember.getMemberName());

		if (outVO != null) {
			System.out.println("아이디 찾기 성공: " + outVO.getMemberId());
		} else {
			System.out.println("이메일로 사용자를 찾을 수 없습니다.");
		}
	}

	public void findPassword() {
		MemberVO outVO = dao.findMemberPwByEmail(registerMember.getMemberId(), registerMember.getEmail(), registerMember.getMemberName(),
				registerMember.getBirthDate());
		if (outVO != null) {
			System.out.println("비밀번호 찾기 성공: " + outVO.getPassword());
		} else {
			System.out.println("이메일로 사용자를 찾을 수 없습니다.");
		}
	}

	public static void main(String[] args) {
		MemberDaoMain main = new MemberDaoMain();
		Scanner sc = new Scanner(System.in);

		String mId = null;
		String mName = null;
		String mPw = null;
		String email = null;
		String bDate = null;
		String rDate = null;
		String rName = null;

		boolean run = true;

		while (run) {
			System.out.println("환영합니다. 멤버 관리 메뉴입니다.");
			System.out.println("--------------------------");
			System.out.println("1. 로그인");
			System.out.println("2. 멤버 등록");
			System.out.println("3. 멤버 조회");
			System.out.println("4. 멤버 탈퇴");
			System.out.println("5. 멤버 수정");
			System.out.println("6. 아이디 찾기");
			System.out.println("7. 비밀번호 찾기");
			System.out.println("0. 프로그램 종료");
			int menu = sc.nextInt();

			switch (menu) {
			case 1:
				System.out.println("----------로그인----------");
				System.out.print("아이디를 입력하세요: ");
				mId = sc.next();
				System.out.print("비밀번호를 입력하세요: ");
				mPw = sc.next();

				main.login(mId, mPw);

				break;
			case 2:
				System.out.println("----------멤버 등록----------");
				System.out.print("아이디를 입력하세요: ");
				mId = sc.next();
				System.out.print("이름을 입력하세요: ");
				mName = sc.next();
				System.out.print("비밀번호를 입력하세요: ");
				mPw = sc.next();
				System.out.print("이메일을 입력하세요: ");
				email = sc.next();
				System.out.print("생년월일을 입력하세요: ");
				bDate = sc.next();

				main.doSave(mId, mName, mPw, email, bDate);
				
				break;
			case 3:
				System.out.println("----------멤버 조회----------");
				
			case 4:
				System.out.println("----------멤버 탈퇴----------");
			case 5:
				System.out.println("----------멤버 수정----------");
			case 6:
				System.out.println("----------아이디 찾기----------");
			case 7:
				System.out.println("----------비밀번호 찾기----------");
			case 0:
				run = false;
				break;
			}
		}

		// 등록
		// main.doSave();

		// 삭제
		// main.doDelete();

		// 단건 조회
		main.doSelectOne();

		// 로그인 테스트
		// main.login();

		// 비밀번호 찾기
		// main.findPassword();
	}
}