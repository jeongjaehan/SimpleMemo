package kr.kakaruto.service;

import java.util.ArrayList;


import kr.kakaruto.dao.MemoDao;
import kr.kakaruto.domain.Memo;

public class MemoService {
	private MemoDao memoDao;
	
	public MemoService() {
		this.memoDao = new MemoDao();
	}
	public ArrayList<Memo> getMemoList(Memo pMemo){
		return memoDao.getMemoList(pMemo);
	}
}
