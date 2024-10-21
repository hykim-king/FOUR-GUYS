package com.pcwk.ehr.movie;

import com.pcwk.ehr.member.MemberDao;
import com.pcwk.ehr.member.MemberDaoMain;
import com.pcwk.ehr.member.MemberVO;

public class MovieDaoMain {
	
	MovieVO movie01 = null;
	MovieVO movie02 = null;
	MovieVO movie03 = null;
	MovieDao dao = null;
	
	public MovieDaoMain() {
		dao = new MovieDao();
		movie01 = new MovieVO("","Venom","Action","10/10/2024","USA",110,15,"James","Tom Hadi",8.6);
		movie02 = new MovieVO("","Venom","Action","10/10/2024","USA",110,15,"James","Tom Hadi",8.6);
	}
	
	public void doSave() {
		System.out.println("영화 등록");
		int flag = dao.doSave(movie01);
		
		if (flag == 2) {
			System.out.println(movie01.getMovieId() + "중복되었습니다.");
		} else if (flag == 0) {
			System.out.println(movie01.getMovieId() + "등록 실패");
		} else {
			System.out.println("************************************");
			System.out.println(movie01.getMovieId() + "등록 성공");
			System.out.println("************************************");
		}
		
		dao.displayList(MovieDao.movies);
	}
	
	public void doDelete() {
		System.out.println("영화 삭제");
		int flag = dao.doDelete(movie01);
		
		if (1 == flag) {
			System.out.println(movie01.getMovieId() + "삭제 성공");
		} else {
			System.out.println(movie01.getMovieId() + "삭제 실패");
		}
		dao.displayList(MovieDao.movies);
	}
	
	public MovieVO doSelectOne() {
		System.out.println("영화 조회");
		
		MovieVO outVO = dao.doSelectOne(movie01);
		
		if (null == outVO) {
			System.out.println(movie01.getMovieId() + "영화가 존재하지 않습니다.");
		} else {
			System.out.println("************************************");
			System.out.println("조회 성공\n" + outVO);
			System.out.println("************************************");
		}
		return outVO;
	}
	
	
	public static void main(String[] args) {
		MovieDaoMain main = new MovieDaoMain();
		
		main.doSelectOne();
		
	}
}