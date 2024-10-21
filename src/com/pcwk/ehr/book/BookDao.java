package com.pcwk.ehr.book;

import java.util.List;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.WorkDiv;

public class BookDao implements WorkDiv<BookVO>{

	@Override
	public int readFile(String path) {
		return 0;
	}

	@Override
	public int doSave(BookVO param) {
		return 0;
	}

	@Override
	public int doUpdate(BookVO param) {
		return 0;
	}

	@Override
	public int doDelete(BookVO param) {
		return 0;
	}

	@Override
	public BookVO doSelectOne(BookVO param) {
		return null;
	}

	@Override
	public List<BookVO> doRetrieve(DTO param) {
		return null;
	}
}