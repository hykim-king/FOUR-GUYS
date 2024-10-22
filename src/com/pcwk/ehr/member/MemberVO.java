package com.pcwk.ehr.member;

import java.util.Objects;

import com.pcwk.ehr.cmn.DTO;

public class MemberVO extends DTO {
	private String memberId;   // 회원ID<PK>
	private String memberName; // 이름
	private String password;   // 비번
	private String email;      // 이메일
	private String birthDate;  // 생년월일
	private String regDt;      // 가입일
	private String roleName;   // 권한명
	
	public MemberVO() {
		super();
	}

	public MemberVO(String memberId, String memberName, String password, String email, String birthDate,
			String regDt, String roleName) {
		super();
		this.memberId = memberId;
		this.memberName = memberName;
		this.password = password;
		this.email = email;
		this.birthDate = birthDate;
		this.regDt = regDt;
		this.roleName = roleName;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(memberId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MemberVO other = (MemberVO) obj;
		return Objects.equals(memberId, other.memberId);
	}

	@Override
	public String toString() {
		return "MemberVO [memberId=" + memberId + ", memberName=" + memberName + ", password=" + password + ", email="
				+ email + ", birthDate=" + birthDate + ", regDt=" + regDt + ", roleName="
				+ roleName + "]";
	}
}