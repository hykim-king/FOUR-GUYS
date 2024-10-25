package com.pcwk.ehr.screening;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.WorkDiv;
import com.pcwk.ehr.reservation.ReservationDao;
import com.pcwk.ehr.reservation.ReservationVO;

public class ScreeningDao implements WorkDiv<ScreeningVO> {

	private final String fileName = "screening.csv";
	public static HashMap<Integer, ScreeningVO> screenings = new HashMap<Integer, ScreeningVO>();

	public ScreeningDao() {
		super();
		readFile(fileName);

	}

	private boolean isExistsScreening(ScreeningVO screening) {
		boolean flag = false;

		for (ScreeningVO vo : screenings.values()) {
			if (vo.equals(screening)) {
				flag = true;
				return flag;
			}
		}

		return flag;
	}

	public void reSort() {
		ReservationDao dao = new ReservationDao();
		HashMap<Integer, ScreeningVO> newMap = new HashMap<>();
		int newKey = 1;
		for (int key : screenings.keySet()) {
			newMap.put(newKey, screenings.get(key));
			newMap.get(newKey).setScreeningCode(newKey);

			for (ReservationVO vo : ReservationDao.reserves) {
				if (vo.getScreeningCode() == key) {
					vo.setScreeningCode(newKey);
				}
			}
			newKey++;
		}
		screenings = newMap;
		writeFile(fileName);
		dao.writeFile("reservings.csv");
	}

	@Override
	public int readFile(String path) {
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String data = "";

			while ((data = br.readLine()) != null) {
				ScreeningVO outVO = stringToScreening(data);
				screenings.put(outVO.getScreeningCode(), outVO);
			}

		} catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
		}
		return screenings.size();
	}

	public ScreeningVO stringToScreening(String data) {
		ScreeningVO out = null;

		String ScreeningStr = data;

		String[] ScreeningArr = ScreeningStr.split("@");

		int screeningCode = Integer.parseInt(ScreeningArr[0]);
		int movieCode = Integer.parseInt(ScreeningArr[1]);
		LocalDate screeningDate = LocalDate.parse(ScreeningArr[2]);
		LocalTime screeningStartTime = LocalTime.parse(ScreeningArr[3]);
		String theaterName = ScreeningArr[4];

		out = new ScreeningVO(screeningCode, movieCode, screeningDate, screeningStartTime, theaterName);
		return out;
	}

	@Override
	public int writeFile(String path) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
			for (ScreeningVO vo : screenings.values()) {
				bw.write(vo.getScreeningCode() + "@" + vo.getMovieCode() + "@" + vo.getScreeningDate() + "@"
						+ vo.getScreeningStartTime() + "@" + vo.getTheater().getTheaterName() + "\n");
			}
		} catch (IOException e) {
			System.out.println("IOException during writing: " + e.getMessage());
		}

		return screenings.size();
	}

	public int doDelete(int key) {
		// 상영 시간표 목록에서 상영 시간표를 찾고 삭제
		int flag = 0;

		flag = screenings.remove(key, screenings.get(key)) == true ? 1 : 0;
		if (flag == 1)
			reSort();

		return flag;
	}

	@Override
	public int doSave(ScreeningVO param) {
		int flag = 1;

		if (isExistsScreening(param) == true) {
			flag = 2;
			return flag;
		}

		ScreeningVO result = screenings.put(screenings.size() + 1, param);

		if (result != null) {
			flag = 0;
			return flag;
		}

		writeFile(fileName);

		return flag;
	}

	@Override
	public int doUpdate(ScreeningVO param) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int doDelete(ScreeningVO param) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ScreeningVO doSelectOne(ScreeningVO param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ScreeningVO> doRetrieve(DTO param) {
		// TODO Auto-generated method stub
		return null;
	}

}
