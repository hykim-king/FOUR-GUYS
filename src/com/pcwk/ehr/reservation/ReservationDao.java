package com.pcwk.ehr.reservation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.WorkDiv;
import com.pcwk.ehr.member.MemberVO;
import com.pcwk.ehr.movie.MovieVO;

public class ReservationDao implements WorkDiv<ReservationVO> {

	private final String fileName = "reserving.csv";
	public static List<ReservationVO> reserves = new ArrayList<ReservationVO>();

	public ReservationDao() {
		super();
		readFile(fileName);
	}
	
	public void displayList() {
		if (reserves.size() > 0) {
			int i = 1;
			System.out.println("┌────────────────────────┐");
			System.out.println("│      예매 테이블 정보       │");
			System.out.println("└────────────────────────┘");
			for (ReservationVO vo : reserves) {
				System.out.println(i + ". " + vo);
				i++;
			}
		} else {
			System.out.println("예매정보가 없습니다.");
		}
	}

	@Override
	public int readFile(String path) {
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String data = "";

			while ((data = br.readLine()) != null) {
				ReservationVO outVO = stringToReservation(data);
				reserves.add(outVO);
			}

		} catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
		}
		return reserves.size();
	}

	public ReservationVO stringToReservation(String data) {
		ReservationVO out = null;

		String reserveStr = data;

		String[] reserveArr = reserveStr.split("@");

		int screeningCode = Integer.parseInt(reserveArr[1]);
		String id = reserveArr[2];
		int pax = Integer.parseInt(reserveArr[3]);
		int adult = Integer.parseInt(reserveArr[4]);
		int teenager = Integer.parseInt(reserveArr[5]);
		String[] seats = reserveArr[6].split(",");
		int totalPrice = Integer.parseInt(reserveArr[7]);

		out = new ReservationVO(screeningCode, id, adult, teenager, seats, totalPrice);
		return out;
	}

	@Override
	public int writeFile(String path) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
			for (ReservationVO vo : reserves) {
				bw.write(vo.getReserveCode() + "@" + vo.getScreeningCode() + "@" + vo.getId() + "@" + vo.getPax() + "@"
						+ vo.getAdult() + "@" + vo.getTeenager() + "@" + vo.getSeatsTostring() + "@"
						+ vo.getTotalPrice() + "\n");
			}
		} catch (IOException e) {
			System.out.println("IOException during writing: " + e.getMessage());
		}

		return reserves.size();
	}

	@Override
	public int doSave(ReservationVO param) {
		int flag = 1;

		boolean result = reserves.add(param);

		if (result == false) {
			flag = 0;
			return flag;
		}

		writeFile(fileName);

		return flag;
	}

	@Override
	public int doUpdate(ReservationVO param) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int doDelete(ReservationVO param) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ReservationVO doSelectOne(ReservationVO param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReservationVO> doRetrieve(DTO param) {
		// TODO Auto-generated method stub
		return null;
	}

}
