package com.pcwk.ehr.member;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.WorkDiv;

public class MemberDao implements WorkDiv<MemberVO> {

	private final String fileName = "member.csv";

	public static List<MemberVO> members = new ArrayList<MemberVO>();

	public MemberDao() {
		super();
		readFile(fileName);
	}

	public int doChargeBalance(MemberVO param) {
		int flag = 0;

		for (int i = 0; i < members.size(); i++) {
			if (members.get(i).getMemberId().equals(param.getMemberId())) {
				members.set(i, param);
				flag = 1;
				writeFile(fileName);
				break;
			}
		}
		return flag;
	}

	public void displayList(List<MemberVO> list) {
		if (list.size() > 0) {
			int i = 1;
			System.out.println("┌────────────────────────┐");
			System.out.println("│        회원 정보          │");
			System.out.println("└────────────────────────┘");
			for (MemberVO vo : list) {
				System.out.println(i + ". " + vo);
				i++;
			}
		} else {
			System.out.println("회원정보가 없습니다.");
		}
	}

	private boolean isExistsMember(MemberVO member) {
		boolean flag = false;

		for (MemberVO vo : members) {
			if (vo.getMemberId().equals(member.getMemberId())) {
				flag = true;
				return flag;
			}
		}
		return flag;
	}

	@Override
	public int doSave(MemberVO param) {
		int flag = 0;

		if (isExistsMember(param) == true) {
			flag = 2;
			return flag;
		}

		boolean check = members.add(param);
		flag = check == true ? 1 : 0;

		if (flag == 0)
			return flag;

		writeFile(fileName);

		return flag;
	}

	@Override
	public int doUpdate(MemberVO param) {
		int flag = 0;
		for (int i = 0; i < members.size(); i++) {
			if (members.get(i).getMemberId().equals(param.getMemberId())) {
				members.set(i, param);
				flag = 1;
				writeFile(fileName);
				break;
			}
		}
		return flag;
	}

	@Override
	public int doDelete(MemberVO param) {
		int flag = 1;

		for (int i = 0; i < members.size(); i++) {

			// && members.get(i).getPassword().equals(param.getPassword())
			if (members.get(i).getMemberId().equals(param.getMemberId())
					&& members.get(i).getRoleName().equals("user")) {
				members.remove(param);
				flag = 2;
				writeFile(fileName);
				break;
			}
		}
		return flag;
	}

	public int doDeleteMy(MemberVO param) {
		int flag = 1;

		for (int i = 0; i < members.size(); i++) {

			if (members.get(i).getMemberId().equals(param.getMemberId())
					&& members.get(i).getPassword().equals(param.getPassword())) {
				members.remove(param);
				flag = 2;
				writeFile(fileName);
				break;
			}
		}

		// flag = == true ? 2 : 1;
		// writeFile(fileName);

		return flag;
	}


	@Override
	public MemberVO doSelectOne(MemberVO param) {
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

		String[] memberArr = data.split(",");

		String memberId = memberArr[0];
		String memberName = memberArr[1];
		String password = memberArr[2];
		String email = memberArr[3];
		String birthDate = memberArr[4];
		String regDt = memberArr[5];
		String roleName = memberArr[6];
		int balance = Integer.parseInt(memberArr[7]);

		out = new MemberVO(memberId, memberName, password, email, birthDate, regDt, roleName);
		out.setBalance(balance);

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
		return members.size();
	}

	public int writeFile(String path) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
			for (MemberVO member : members) {
				bw.write(member.getMemberId() + "," + member.getMemberName() + "," + member.getPassword() + ","
						+ member.getEmail() + "," + member.getBirthDate() + "," + member.getRegDt() + ","
						+ member.getRoleName() + "," + member.getBalance() + "\n");
			}
		} catch (IOException e) {
			System.out.println("IOException during writing: " + e.getMessage());
		}
		return members.size();
	}

	public int doLogin(MemberVO param) {
		int flag = 0;

		for (MemberVO vo : members) {
			if (vo.getMemberId().equals(param.getMemberId()) && vo.getPassword().equals(param.getPassword())) {
				if (vo.getRoleName().equals("admin")) {
					flag = 2;
					return flag;
				} else {
					flag = 1;
					return flag;
				}
			}
		}
		return flag;
	}

	public MemberVO doFindId(MemberVO param) {
		MemberVO outVO = null;

		for (MemberVO vo : members) {
			if (vo.getEmail().equals(param.getEmail()) && vo.getMemberName().equals(param.getMemberName())) {
				outVO = vo;
				break;
			}
		}
		return outVO;
	}

	public MemberVO doFindPassWord(MemberVO param) {
		MemberVO outVO = null;

		for (MemberVO vo : members) {
			if (vo.getMemberId().equals(param.getMemberId()) && vo.getMemberName().equals(param.getMemberName())
					&& vo.getEmail().equals(param.getEmail()) && vo.getBirthDate().equals(param.getBirthDate())) {
				outVO = vo;
				break;
			}
		}

		return outVO;
	}

	public MemberVO findMemberId(String email, String memberName) {
		for (MemberVO vo : members) {
			if (vo.getEmail().equals(email) && vo.getMemberName().equals(memberName)) {
				return vo;
			}
		}
		return null;
	}

	public MemberVO findMemberPw(String memberId, String email, String memberName, String birthDate) {
		for (MemberVO vo : members) {
			if (vo.getMemberId().equals(memberId) && vo.getEmail().equals(email)
					&& vo.getMemberName().equals(memberName) && vo.getBirthDate().equals(birthDate)) {
				return vo;
			}
		}
		return null;
	}
}