package com.pcwk.ehr.member;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.WorkDiv;

public class MemberDao implements WorkDiv<MemberVO> {

    private final String fileName = "member.csv";

    public static List<MemberVO> members = new ArrayList<MemberVO>();

    public MemberDao() {
        super();
        int count = readFile(fileName);
    }

    public void displayList(List<MemberVO> list) {
        if (list.size() > 0) {
            System.out.println("┌────────────────────────┐");
            System.out.println("│        회원 정보         │");
            System.out.println("└────────────────────────┘");
            for (MemberVO vo : list) {
                System.out.println(vo);
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

        boolean check = this.members.add(param);
        flag = check == true ? 1 : 0;

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
        int flag = 0;

        flag = members.remove(param) == true ? 1 : 0;
        writeFile(fileName);

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

        out = new MemberVO(memberId, memberName, password, email, birthDate, regDt, roleName);

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

        displayList(members);
        return members.size();
    }

    public void writeFile(String path) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (MemberVO member : members) {
                bw.write(member.getMemberId() + "," +
                         member.getMemberName() + "," +
                         member.getPassword() + "," +
                         member.getEmail() + "," +
                         member.getBirthDate() + "," +
                         member.getRegDt() + "," +
                         member.getRoleName() + "\n");
            }
        } catch (IOException e) {
            System.out.println("IOException during writing: " + e.getMessage());
        }
    }

    public MemberVO login(String memberId, String password) {
        for (MemberVO vo : members) {
            if (vo.getMemberId().equals(memberId) && vo.getPassword().equals(password)) {
                return vo;
            }
        }
        return null;
    }

    public MemberVO findMemberIdByEmail(String email, String memberName) {
        for (MemberVO vo : members) {
            if (vo.getEmail().equals(email) && vo.getMemberName().equals(memberName)) {
                return vo;
            }
        }
        return null;
    }
    
    public MemberVO findMemberPwByEmail(String memberId, String email, String memberName, String birthDate) {
        for (MemberVO vo : members) {
            if (vo.getMemberId().equals(memberId) && vo.getEmail().equals(email) && vo.getMemberName().equals(memberName) && vo.getBirthDate().equals(birthDate)) {
                return vo;
            }
        }
        return null;
    }
}