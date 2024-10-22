package com.pcwk.ehr.member;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class MemberDaoMain {
	Date date = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	String registerDate = sdf.format(date);
	MemberVO registerMember = null;
	MemberDao dao = null;

	public MemberDaoMain() {
		dao = new MemberDao();
		//registerMember = new MemberVO(mId, mName, mPw, email, bDate, registerDate, "user");
	}

	public void doSave(String mId, String mName, String mPw, String email, String bDate) {
		System.out.println("회원 등록");
		registerMember = new MemberVO(mId, mName, mPw, email, bDate, registerDate, "user");
		
		int flag = dao.doSave(registerMember);

		if (flag == 2) {
			System.out.println(mId + "중복 되었습니다.");
		} else if (flag == 0) {
			System.out.println(mId + "등록 실패");
		} else {
			System.out.println("************************************");
			System.out.println(mId + "등록 성공");
			System.out.println("************************************");
		}

		dao.displayList(MemberDao.members);
	}

	public void doDelete(String mId, String mPw) {
		System.out.println("멤버 탈퇴");
		
		MemberVO outVO = new MemberVO();
		
		outVO.setMemberId(mId);
		outVO.setPassword(mPw);
		
		int flag = dao.doDelete(outVO);
		
		if (flag == 1) {
			System.out.println(mId + "삭제 성공");
		} else {
			System.out.println(mId + "삭제 실패");
		}
		dao.displayList(MemberDao.members);
	}

	public MemberVO doSelectOne(String mId) {
		System.out.println("\n회원 조회");
		MemberVO param = new MemberVO();
		param.setMemberId(mId);
		MemberVO outVO = dao.doSelectOne(param);
		
		if (outVO == null) {
			System.out.println(mId + " 회원이 존재하지 않습니다.");
		} else {
			System.out.println("************************************");
			System.out.println("조회 성공\n" + outVO);
			System.out.println("************************************");
		}
		return outVO;
	}
	
	public void doUpdate(String mId, String mName, String mPw, String email, String bDate) {
		System.out.println("멤버 정보 수정");
		
		MemberVO outVO = new MemberVO(mId, mName, mPw, email, bDate, registerDate, "user");
		
		int flag = dao.doUpdate(outVO);
		
		if (flag == 1) {
			System.out.println("************************************");
	        System.out.println(mId + " 정보 수정 성공");
	        System.out.println("************************************");
		} else {
			System.out.println(mId + " 정보 수정 실패");
		}
		
		dao.displayList(MemberDao.members);
	}

	public void login(String mId, String mPw) {
		MemberVO outVO = dao.login(mId, mPw);
		
		if (outVO != null) {
			System.out.println("로그인 성공: " + outVO.getMemberName() + "님");
		} else {
			System.out.println("로그인 실패");
		}
	}

	public void findId(String email, String mName) {
		MemberVO outVO = dao.findMemberIdByEmail(email, mName);

		if (outVO != null) {
			System.out.println("아이디 찾기 성공: " + outVO.getMemberId());
		} else {
			System.out.println("이메일로 사용자를 찾을 수 없습니다.");
		}
	}

	public void findPassword(String mId, String email, String mName, String bDate) {
		MemberVO outVO = dao.findMemberPwByEmail(mId, email, mName, bDate);
		
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
			System.out.print("항목을 선택하세요: ");
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
				System.out.print("생년월일을 입력하세요 ex)2024/10/10: ");
				bDate = sc.next();

				main.doSave(mId, mName, mPw, email, bDate);
				
				break;
			case 3:
				System.out.println("----------멤버 조회----------");
				System.out.print("아이디를 입력하세요: ");
				mId = sc.next();
				
				main.doSelectOne(mId);
				
				break;
			case 4:
				System.out.println("----------멤버 탈퇴----------");
				System.out.print("아이디를 입력하세요: ");
				mId = sc.next();
				System.out.print("비밀번호를 입력하세요: ");
			    mPw = sc.next();
				
				main.doDelete(mId, mPw);
				
				break;
			case 5:
				System.out.println("----------멤버 수정----------");
				System.out.println("수정할 아이디를 입력하세요: ");
				mId = sc.next();
				
				MemberVO existingMember = main.doSelectOne(mId);
				if (existingMember == null) {
					break;
				}
				
				System.out.println("수정할 항목을 선택하세요");
				System.out.println("---------------");
			    System.out.println("1. 이름 수정");
			    System.out.println("2. 비밀번호 수정");
			    System.out.println("3. 이메일 수정");
			    System.out.println("4. 생일 수정");
			    System.out.print("항목을 선택하세요: ");
			    int input = sc.nextInt();
			    
			    switch (input) {
			    case 1:
			    	System.out.print("새 이름을 입력하세요: ");
				    mName = sc.next();
				    
				    break;
			    case 2:
			    	System.out.print("새 비밀번호를 입력하세요: ");
				    mPw = sc.next();
				    
				    break;
			    case 3:
			    	System.out.print("새 이메일을 입력하세요: ");
				    email = sc.next();
				    
				    break;
			    case 4:
			    	System.out.print("새 생년월일을 입력하세요 (yyyy/MM/dd): ");
				    bDate = sc.next();
				    
				    break;
			    default:
			    	System.out.println("잘못된 선택입니다.");
			    	break;
			    }
			    
			   	main.doUpdate(mId, mName, mPw, email, bDate);
				
				break;
			case 6:
				System.out.println("----------아이디 찾기----------");
				System.out.print("이메일을 입력하세요: ");
                email = sc.next();
                System.out.print("이름을 입력하세요: ");
                mName = sc.next();
                
                main.findId(email, mName);
                
                break;
			case 7:
				System.out.println("----------비밀번호 찾기----------");
				System.out.print("아이디를 입력하세요: ");
                mId = sc.next();
                System.out.print("이름을 입력하세요: ");
                mName = sc.next();
                System.out.print("이메일을 입력하세요: ");
                email = sc.next();
                System.out.print("생년월일을 입력하세요 (yyyy/MM/dd): ");
                bDate = sc.next();
                
                main.findPassword(mId, mName, email, bDate);
                
                break;
			case 0:
				run = false;
				System.out.println("프로그램을 종료합니다.");
				break;
			default:
				System.out.println("잘못된 입력입니다. 다시 시도해주세요.");	
			}
		}
		sc.close();

		// 등록
		// main.doSave();

		// 삭제
		// main.doDelete();

		// 단건 조회
		

		// 로그인 테스트
		// main.login();

		// 비밀번호 찾기
		// main.findPassword();
	}
}