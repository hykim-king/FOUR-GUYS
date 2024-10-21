package com.pcwk.ehr.member;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.cmn.WorkDiv;

public class MemberDao implements WorkDiv<MemberVO>, PLog {

	// TODO: 파일 경로로 변경
	private final String fileName = "member.csv";

	public static List<MemberVO> members = new ArrayList<>();

	public MemberDao() {
		super();

		int count = readFile(fileName);

		// -------------------------------

		// -------------------------------
	}

	public void displayList(List<MemberVO> list) {
		if (list.size() > 0) {
			String message = "    __  ___               __                 __    _      __ \r\n"
					+ "   /  |/  /__  ____ ___  / /_  ___  _____   / /   (_)____/ /_\r\n"
					+ "  / /|_/ / _ \\/ __ `__ \\/ __ \\/ _ \\/ ___/  / /   / / ___/ __/\r\n"
					+ " / /  / /  __/ / / / / / /_/ /  __/ /     / /___/ (__  ) /_  \r\n"
					+ "/_/  /_/\\___/_/ /_/ /_/_.___/\\___/_/     /_____/_/____/\\__/  \r\n" + "";
			System.out.println(message);
			for (MemberVO vo : list) {
				System.out.println(vo);
			}
		} else {
			System.out.println("회원정보가 없습니다.");
		}
	}

	/**
	 * 1(성공) / 0(실패) / 2(memberId 중복)
	 */

	// boolean isExistsMember
	private boolean isExistsMember(MemberVO member) {
		boolean flag = false;

		for (MemberVO vo : members) {
			// param, vo의 memberId 비교
			if (vo.getMemberId().equals(member.getMemberId())) {
				flag = true;
				return flag;
			}
		}
		return flag;
	}

	@Override
	public int doSave(MemberVO param) {
		// 1. 입력 전에 memberId 중복여부 check
		// 2. param입력된 데이터를 members에 추가
		int flag = 0;

		if (isExistsMember(param) == true) {
			flag = 2;
			return flag;
		}

		boolean check = this.members.add(param);
		flag = check == true ? 1 : 0;

		return flag;
	}

	@Override
	public int doUpdate(MemberVO param) {
		int flag = 0;

		return flag;
	}

	@Override
	public int doDelete(MemberVO param) {
		// 회원 목록에서 동일한 회원을 찾고 삭제
		int flag = 0;

		flag = members.remove(param) == true ? 1 : 0;

		return flag;
	}

	@Override
	public MemberVO doSelectOne(MemberVO param) {
		// members에서 회원ID에 해당되는 회원정보 전체를 return
		MemberVO outVO = null;

		for (MemberVO vo : members) {
			if (vo.getMemberId().equals(param.getMemberId())) {
				outVO = vo;
				break;
			}
		}

		return outVO;
	}

	@Override
	public List<MemberVO> doRetrieve(DTO param) {
		return null;
	}
	
	public MemberVO stringToMember(String data) {
		MemberVO out = null;

		String memberStr = data;
		log.debug("memberStr: {}", memberStr);
		String[] memberArr = memberStr.split(",");

		String memberId = memberArr[0];
		String memberName = memberArr[1];
		String password = memberArr[2];
		String email = memberArr[3];
		int teamId = Integer.parseInt(memberArr[4]);
		int loginCount = Integer.parseInt(memberArr[5]);
		String regDt = memberArr[6];
		String roleName = memberArr[7];

		out = new MemberVO(memberId, memberName, password, email, teamId, loginCount, regDt, roleName);
		
		return out;
	}
	
	@Override
	public int readFile(String path) {
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String data = "";

			while ((data = br.readLine()) != null) {
				MemberVO outVO = stringToMember(data);
				members.add(outVO);
			}
		} catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
		}

		// 회원정조 전체 조회
		displayList(members);
		return members.size();
	}
}